// composeApp/src/commonMain/kotlin/br/ufg/WebViewScreen.kt
// Tela de visualização web com barra de navegação para o Terminal de Consulta
package br.ufg

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

// Interface comum para implementações de WebView multiplataforma
@Composable
expect fun WebView(
    url: String, 
    modifier: Modifier = Modifier,
    onCanGoBack: (Boolean) -> Unit = {},
    onCanGoForward: (Boolean) -> Unit = {},
    onGoBack: (() -> Unit) -> Unit = {},
    onGoForward: (() -> Unit) -> Unit = {},
    onReload: (() -> Unit) -> Unit = {}
)

/**
 * Tela de visualização web com barra de navegação
 * Inclui botões para voltar, avançar e retornar à tela inicial
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewScreen(url: String, title: String, onBackClick: () -> Unit) {
    // Estados para controlar a navegação do WebView
    var canGoBack by remember { mutableStateOf(false) }
    var canGoForward by remember { mutableStateOf(false) }
    var webViewGoBack by remember { mutableStateOf({}) }
    var webViewGoForward by remember { mutableStateOf({}) }
    var webViewReload by remember { mutableStateOf({}) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = title,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Voltar ao início"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            // Barra de navegação inferior para controles do WebView
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                NavigationBarItem(
                    icon = { 
                        Icon(
                            Icons.Filled.ArrowBack, 
                            contentDescription = "Voltar página",
                            tint = if (canGoBack) MaterialTheme.colorScheme.primary 
                                  else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        ) 
                    },
                    label = { Text("Voltar") },
                    selected = false,
                    enabled = canGoBack,
                    onClick = { webViewGoBack() }
                )
                NavigationBarItem(
                    icon = { 
                        Icon(
                            Icons.Filled.ArrowForward, 
                            contentDescription = "Avançar página",
                            tint = if (canGoForward) MaterialTheme.colorScheme.primary 
                                  else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        ) 
                    },
                    label = { Text("Avançar") },
                    selected = false,
                    enabled = canGoForward,
                    onClick = { webViewGoForward() }
                )
                NavigationBarItem(
                    icon = { 
                        Icon(
                            Icons.Filled.Refresh, 
                            contentDescription = "Recarregar página"
                        ) 
                    },
                    label = { Text("Recarregar") },
                    selected = false,
                    onClick = { webViewReload() }
                )
            }
        }
    ) { paddingValues ->
        // Container do WebView
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            WebView(
                url = url,
                modifier = Modifier.fillMaxSize(),
                onCanGoBack = { canGoBack = it },
                onCanGoForward = { canGoForward = it },
                onGoBack = { webViewGoBack = it },
                onGoForward = { webViewGoForward = it },
                onReload = { webViewReload = it }
            )
        }
    }
}