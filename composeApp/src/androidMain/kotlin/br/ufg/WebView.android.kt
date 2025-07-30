// composeApp/src/androidMain/kotlin/br/ufg/WebView.android.kt
// Implementação do WebView para Android com controles de navegação
package br.ufg

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

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
    var webView by remember { mutableStateOf<WebView?>(null) }
    
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        // Atualiza o estado dos botões de navegação
                        view?.let {
                            onCanGoBack(it.canGoBack())
                            onCanGoForward(it.canGoForward())
                        }
                    }
                }
                
                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    loadWithOverviewMode = true
                    useWideViewPort = true
                    setSupportZoom(true)
                    builtInZoomControls = true
                    displayZoomControls = false
                    allowFileAccess = true
                    allowContentAccess = true
                }
                
                webView = this
            }
        },
        update = { wv ->
            wv.loadUrl(url)
        },
        modifier = modifier
    )
    
    // Configura as funções de navegação
    LaunchedEffect(webView) {
        webView?.let { wv ->
            onGoBack { if (wv.canGoBack()) wv.goBack() }
            onGoForward { if (wv.canGoForward()) wv.goForward() }
            onReload { wv.reload() }
        }
    }
}