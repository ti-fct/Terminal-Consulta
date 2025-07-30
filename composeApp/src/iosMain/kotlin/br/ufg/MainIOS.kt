// composeApp/src/iosMain/kotlin/br/ufg/MainIOS.kt
// Ponto de entrada para iOS do Terminal de Consulta
package br.ufg

import androidx.compose.ui.window.ComposeUIViewController

/**
 * Função principal para iOS
 * Cria o UIViewController que hospeda a interface Compose
 */
fun MainViewController() = ComposeUIViewController { App() }