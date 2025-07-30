// composeApp/src/commonMain/kotlin/br/ufg/ui/HomeScreen.kt
// Tela inicial do Terminal de Consulta com botões de acesso rápido
package br.ufg.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.ufg.model.MenuItem

/**
 * Tela inicial do Terminal de Consulta
 * Apresenta botões grandes e de fácil leitura para acesso aos links web
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onNavigateToWebView: (String, String) -> Unit) {
    val menuItems = remember {
        listOf(
            MenuItem(
                id = "home",
                title = "Página Inicial",
                icon = Icons.Filled.Home,
                description = "Voltar para a tela inicial"
            ) {
                onNavigateToWebView("https://fct.ufg.br", "Página Inicial")
            },

            MenuItem(
                id = "campus",
                title = "Conheça o Campus",
                icon = Icons.Filled.LocationCity,
                description = "Explore as instalações da FCT/UFG"
            ) {
                onNavigateToWebView("https://prezi.com/view/MZjulFdzyMstq9zoDLVX/", "Conheça o Campus")
            },

            MenuItem(
                id = "sigaa",
                title = "SIGAA",
                icon = Icons.Filled.School,
                description = "Acesse o sistema acadêmico"
            ) {
                onNavigateToWebView("https://sigaa.sistemas.ufg.br/sigaa/mobile/", "SIGAA")
            },

            MenuItem(
                id = "bus",
                title = "Linha de Ônibus",
                icon = Icons.Filled.DirectionsBus,
                description = "Consulte horários e rotas"
            ) {
                onNavigateToWebView("https://rmtcgoiania.com.br/index.php/linhas-e-trajetos/area-sul?buscar=555", "Linha de Ônibus")
            },

            MenuItem(
                id = "schedule",
                title = "Horários de Aulas",
                icon = Icons.Filled.Schedule,
                description = "Consulte os horários de aulas"
            ) {
                onNavigateToWebView("https://ti-fct.github.io/horariosFCT/", "Horários de Aulas")
            },

            MenuItem(
                id = "rooms",
                title = "Mapa de Salas",
                icon = Icons.Filled.Map,
                description = "Encontre salas de aula e laboratórios"
            ) {
                onNavigateToWebView("https://docs.google.com/gview?embedded=true&url=https://ti-fct.github.io/Painel-FCT/mapa.pdf", "Mapa de Salas")
            },

            MenuItem(
                id = "team",
                title = "Equipe FCT/UFG",
                icon = Icons.Filled.Group,
                description = "Conheça professores e servidores"
            ) {
                onNavigateToWebView("https://app.powerbi.com/view?r=eyJrIjoiNjUzMDMzOWUtNzViNS00NGYyLTk1YTYtMWY5MWE5OGI1YzAzIiwidCI6ImIxY2E3YTgxLWFiZjgtNDJlNS05OGM2LWYyZjJhOTMwYmEzNiJ9", "Equipe FCT/UFG")
            },

            MenuItem(
                id = "extension",
                title = "Ações de Extensão",
                icon = Icons.Filled.Forest,
                description = "Projetos e ações de extensão"
            ) {
                onNavigateToWebView("https://app.powerbi.com/view?r=eyJrIjoiMDcyZWQ2NWMtZTVkMy00YzMyLTkyYjQtNzFmMjQ1MzVjZDcwIiwidCI6ImIxY2E3YTgxLWFiZjgtNDJlNS05OGM2LWYyZjJhOTMwYmEzNiJ9", "Ações de Extensão")
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Terminal de Consulta",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título de boas-vindas
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = "Bem-vindo ao Terminal de Consulta\nSelecione uma opção abaixo para acessar os serviços",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(20.dp)
                )
            }
            
            // Grid de cards para o menu - Responsivo para orientação vertical e horizontal
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 280.dp), // Menor para caber melhor em vertical
                contentPadding = PaddingValues(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(menuItems) { item ->
                    MenuCard(menuItem = item)
                }
            }
        }
    }
}