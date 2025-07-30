// composeApp/src/commonMain/kotlin/br.ufg/navigation/Navigation.kt

package br.ufg.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

enum class Screen {
    HOME,
    WEB_VIEW
}

data class WebViewData(
    val url: String,
    val title: String
)

class NavigationState {
    var currentScreen by mutableStateOf(Screen.HOME)
    var webViewData by mutableStateOf(WebViewData("", ""))

    fun navigateToHome() {
        currentScreen = Screen.HOME
    }

    fun navigateToWebView(url: String, title: String) {
        webViewData = WebViewData(url, title)
        currentScreen = Screen.WEB_VIEW
    }
}

@Composable
fun rememberNavigationState(): NavigationState {
    return remember { NavigationState() }
}