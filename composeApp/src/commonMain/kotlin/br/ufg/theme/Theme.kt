// composeApp/src/commonMain/kotlin/br/ufg/theme/Theme.kt
// Tema do Terminal de Consulta - Interface em tons azul e branco
package br.ufg.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Paleta de cores azul e branco para o Terminal de Consulta
private val AzulPrimario = Color(0xFF1565C0)      // Azul principal
private val AzulSecundario = Color(0xFF0D47A1)    // Azul mais escuro
private val AzulClaro = Color(0xFF42A5F5)         // Azul claro
private val BrancoFundo = Color(0xFFFAFAFA)       // Branco suave para fundo
private val BrancoCard = Color(0xFFFFFFFF)        // Branco puro para cards

// Esquema de cores claro - Interface principal em azul e branco
private val LightColors = lightColorScheme(
    primary = AzulPrimario,
    secondary = AzulSecundario,
    tertiary = AzulClaro,
    primaryContainer = Color(0xFFE3F2FD),          // Container azul muito claro
    secondaryContainer = Color(0xFFBBDEFB),        // Container azul claro
    onPrimary = Color.White,
    onSecondary = Color.White,
    onPrimaryContainer = AzulSecundario,
    onSecondaryContainer = AzulPrimario,
    surface = BrancoCard,
    background = BrancoFundo,
    onSurface = Color(0xFF212121),                 // Texto escuro em superfícies claras
    onBackground = Color(0xFF212121)
)

// Esquema de cores escuro - Mantém a identidade azul
private val DarkColors = darkColorScheme(
    primary = AzulClaro,
    secondary = Color(0xFF90CAF9),
    tertiary = Color(0xFF64B5F6),
    primaryContainer = Color(0xFF0D47A1),
    secondaryContainer = Color(0xFF1565C0),
    onPrimary = Color(0xFF003C8F),
    onSecondary = Color(0xFF001970),
    onPrimaryContainer = Color(0xFFE3F2FD),
    onSecondaryContainer = Color(0xFFBBDEFB),
    surface = Color(0xFF121212),
    background = Color(0xFF0A0A0A),
    onSurface = Color(0xFFE0E0E0),
    onBackground = Color(0xFFE0E0E0)
)

@Composable
fun TerminalConsultaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Esquema de cores baseado no tema selecionado
    val colorScheme = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}