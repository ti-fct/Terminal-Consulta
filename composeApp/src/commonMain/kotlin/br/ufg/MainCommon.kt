// composeApp/src/commonMain/kotlin/br/ufg/MainCommon.kt
// Terminal de Consulta - Aplicativo principal para consultas dos alunos
package br.ufg

import androidx.compose.runtime.Composable
import br.ufg.navigation.Screen
import br.ufg.navigation.rememberNavigationState
import br.ufg.theme.TerminalConsultaTheme
import br.ufg.ui.HomeScreen

/**
 * Composable principal do Terminal de Consulta
 * Gerencia a navegação entre as telas principais do aplicativo
 */
@Composable
fun App() {
    TerminalConsultaTheme {
        // Estado de navegação para controlar as telas
        val navigationState = rememberNavigationState()

        // Navegação principal baseada no estado atual
        when (navigationState.currentScreen) {
            Screen.HOME -> HomeScreen(
                onNavigateToWebView = { url, title ->
                    navigationState.navigateToWebView(url, title)
                }
            )
            Screen.WEB_VIEW -> WebViewScreen(
                url = navigationState.webViewData.url,
                title = navigationState.webViewData.title,
                onBackClick = {
                    navigationState.navigateToHome()
                }
            )
        }
    }
}