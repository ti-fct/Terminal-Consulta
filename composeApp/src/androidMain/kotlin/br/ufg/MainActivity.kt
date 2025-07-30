// composeApp/src/androidMain/kotlin/br/ufg/MainActivity.kt
// Activity principal do Terminal de Consulta com suporte ao modo kiosk
package br.ufg

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

/**
 * Activity principal do Terminal de Consulta
 * Configurada para rodar em modo kiosk (tela cheia)
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Configuração para modo kiosk - tela cheia
        enableEdgeToEdge()
        setupKioskMode()
        setupTaskLock() // Proteção adicional contra saída do app
        
        setContent {
            App()
        }
    }
    
    /**
     * Configura o modo kiosk para o Terminal de Consulta
     * Remove barras de sistema e mantém a aplicação em tela cheia
     */
    private fun setupKioskMode() {
        // Mantém a tela sempre ligada
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        
        // Configura para tela cheia imersiva
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN
        )
    }
    
    override fun onResume() {
        super.onResume()
        // Reaplica o modo kiosk quando a activity volta ao foco
        setupKioskMode()
    }
    
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            // Reaplica o modo kiosk quando a janela ganha foco
            setupKioskMode()
        }
    }
    
    // Sistema de autenticação para sair do modo kiosk
    private var backPressCount = 0
    private var lastBackPressTime = 0L
    private val BACK_PRESS_INTERVAL = 2000L // 2 segundos
    private val REQUIRED_BACK_PRESSES = 5 // 5 toques rápidos
    
    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        
        if (currentTime - lastBackPressTime < BACK_PRESS_INTERVAL) {
            backPressCount++
        } else {
            backPressCount = 1
        }
        
        lastBackPressTime = currentTime
        
        // Se o usuário pressionar 5 vezes o botão voltar em 2 segundos, mostra dialog de senha
        if (backPressCount >= REQUIRED_BACK_PRESSES) {
            showExitDialog()
            backPressCount = 0
        }
    }
    
    /**
     * Mostra dialog para inserir senha e sair do modo kiosk
     */
    private fun showExitDialog() {
        val builder = android.app.AlertDialog.Builder(this)
        val input = android.widget.EditText(this)
        input.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        input.hint = "Digite a senha para sair"
        
        builder.setTitle("Sair do Modo Kiosk")
            .setMessage("Digite a senha de administrador para sair do Terminal de Consulta:")
            .setView(input)
            .setPositiveButton("Sair") { _, _ ->
                val password = input.text.toString()
                if (password == "admin123") { // Senha padrão - pode ser alterada
                    isExitingWithPassword = true // Marca que está saindo com senha
                    
                    try {
                        stopLockTask() // Para o modo de bloqueio antes de sair
                    } catch (e: Exception) {
                        // Ignora erro se não estava em lock task
                    }
                    
                    // Força a saída do app
                    finishAndRemoveTask()
                    android.os.Process.killProcess(android.os.Process.myPid())
                } else {
                    android.widget.Toast.makeText(this, "Senha incorreta!", android.widget.Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.cancel()
            }
            .setCancelable(false)
            .show()
    }
    
    // Flag para controlar se o usuário autenticou para sair
    private var isExitingWithPassword = false
    
    /**
     * Configura proteção adicional contra saída do app (botão home, recents, etc.)
     */
    private fun setupTaskLock() {
        // Inicia o modo de bloqueio de tarefas se disponível (Android 5.0+)
        try {
            startLockTask()
        } catch (e: Exception) {
            // Se não conseguir usar startLockTask, usa outras proteções
        }
    }
    
    override fun onPause() {
        super.onPause()
        // Só tenta voltar se não estiver saindo com senha
        if (!isExitingWithPassword) {
            bringAppToFront()
        }
    }
    
    /**
     * Tenta trazer o app de volta ao primeiro plano
     */
    private fun bringAppToFront() {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        startActivity(intent)
    }
    
    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        // Só tenta voltar se não estiver saindo com senha
        if (!isExitingWithPassword) {
            bringAppToFront()
        }
    }
    
    override fun onStop() {
        super.onStop()
        // Só tenta voltar se não estiver saindo com senha
        if (!isExitingWithPassword) {
            bringAppToFront()
        }
    }
    
    override fun onDestroy() {
        // Só permite destruir se a senha foi inserida corretamente
        try {
            stopLockTask()
        } catch (e: Exception) {
            // Ignora erro se não estava em lock task
        }
        super.onDestroy()
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}