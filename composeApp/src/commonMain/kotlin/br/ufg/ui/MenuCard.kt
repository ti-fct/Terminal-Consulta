// composeApp/src/commonMain/kotlin/br/ufg/ui/MenuCard.kt
// Cards de menu otimizados para o Terminal de Consulta
package br.ufg.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.ufg.model.MenuItem

/**
 * Card de menu otimizado para Terminal de Consulta
 * Botões grandes e de fácil leitura para melhor usabilidade
 */
@Composable
fun MenuCard(menuItem: MenuItem, modifier: Modifier = Modifier) {
    ElevatedCard(
        onClick = menuItem.action,
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp), // Aumentado para melhor legibilidade
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 6.dp,
            pressedElevation = 12.dp
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp), // Padding maior para melhor toque
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícone maior e mais visível
            Icon(
                imageVector = menuItem.icon,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(20.dp))

            Column(modifier = Modifier.weight(1f)) {
                // Título com fonte maior e mais legível
                Text(
                    text = menuItem.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                if (menuItem.description.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = menuItem.description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Indicador de navegação
            Icon(
                imageVector = androidx.compose.material.icons.Icons.Filled.ChevronRight,
                contentDescription = "Acessar",
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}