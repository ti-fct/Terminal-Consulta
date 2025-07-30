//composeApp/src/commonMain/kotlin/br.ufg/model/MenuItem.kt
package br.ufg.model

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val description: String = "",
    val action: () -> Unit
)