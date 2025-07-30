// composeApp/src/iosMain/kotlin/br/ufg/WebView.ios.kt
// Implementação do WebView para iOS com controles de navegação
package br.ufg

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKWebView
import platform.WebKit.WKNavigationDelegateProtocol
import platform.darwin.NSObject

/**
 * Implementação do WebView para iOS usando WKWebView
 * Integra o componente nativo do iOS com Compose Multiplatform
 */
@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun WebView(
    url: String, 
    modifier: Modifier,
    onCanGoBack: (Boolean) -> Unit,
    onCanGoForward: (Boolean) -> Unit,
    onGoBack: (() -> Unit) -> Unit,
    onGoForward: (() -> Unit) -> Unit,
    onReload: (() -> Unit) -> Unit
) {
    var webView by remember { mutableStateOf<WKWebView?>(null) }
    
    UIKitView(
        factory = {
            WKWebView().apply {
                // Delegate para monitorar mudanças de navegação
                navigationDelegate = object : NSObject(), WKNavigationDelegateProtocol {
                    override fun webView(webView: WKWebView, didFinishNavigation: platform.WebKit.WKNavigation?) {
                        // Atualiza o estado dos botões de navegação
                        onCanGoBack(webView.canGoBack)
                        onCanGoForward(webView.canGoForward)
                    }
                }
                
                val nsUrl = NSURL.URLWithString(url)
                val request = NSURLRequest.requestWithURL(nsUrl!!)
                loadRequest(request)
                webView = this
            }
        },
        modifier = modifier
    )
    
    // Configura as funções de navegação
    LaunchedEffect(webView) {
        webView?.let { wv ->
            onGoBack { if (wv.canGoBack) wv.goBack() }
            onGoForward { if (wv.canGoForward) wv.goForward() }
            onReload { wv.reload() }
        }
    }
}