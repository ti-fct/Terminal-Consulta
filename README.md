# Terminal de Consulta

![Terminal](https://img.shields.io/badge/Terminal%20de%20Consulta-Kiosk%20Mode-blue.svg)
![Kotlin](https://img.shields.io/badge/kotlin-2.1.10-fuchsia.svg)
![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-1.7.3-green.svg)
![Plataformas](https://img.shields.io/badge/Plataformas-Android%20%7C%20iOS-orange)
![Segurança](https://img.shields.io/badge/Modo%20Kiosk-Seguro-red.svg)

Um terminal de consulta multiplataforma (Android e iOS) desenvolvido com Kotlin Multiplatform e Compose Multiplatform. Projetado especificamente para uso em terminais públicos por estudantes, oferece uma interface intuitiva em tons azul e branco com modo kiosk robusto e sistema de autenticação para administradores.

## ✨ Funcionalidades Principais

### 🎨 **Interface Otimizada**
* **Design responsivo:** Tons azul e branco profissionais
* **Botões grandes:** Fácil leitura e toque em dispositivos móveis
* **Layout adaptativo:** Funciona em orientação vertical e horizontal
* **Tipografia clara:** Fontes otimizadas para legibilidade

### 🔒 **Modo Kiosk Robusto**
* **Proteção completa:** Bloqueia botão home, recents e barra de notificações
* **Sistema de autenticação:** 5 toques rápidos + senha para administradores
* **Retorno automático:** Detecta tentativas de saída e retorna ao app
* **Saída segura:** Apenas com senha `admin123` (configurável)

### 🌐 **Acesso Rápido a Serviços**
Menu principal com botões para acessar:
* Página inicial da FCT/UFG
* Tour virtual pelo campus
* SIGAA (Sistema Integrado de Gestão de Atividades Acadêmicas)
* Informações sobre linha de ônibus
* Horários de aulas
* Mapa de salas e laboratórios
* Equipe FCT/UFG (professores e servidores)
* Ações de extensão da faculdade

### 🧭 **Navegação Web Integrada**
* **Controles funcionais:** Voltar, avançar e recarregar páginas
* **Estados visuais:** Botões desabilitados quando não há histórico
* **WebView nativo:** Implementação específica para Android (WebView) e iOS (WKWebView)
* **Botão home:** Retorno rápido à tela inicial

### 🛠 **Multiplataforma e Manutenção**
* **Código compartilhado:** 90%+ entre Android e iOS
* **Documentação em PT-BR:** Comentários e instruções em português
* **Arquitetura limpa:** Fácil manutenção e extensão
* **Build automatizado:** Gradle para Android, Xcode para iOS

## 🚀 Tecnologias Utilizadas

* **Kotlin Multiplatform (KMP):** Compartilhamento de código entre Android e iOS
* **Compose Multiplatform:** Framework de UI declarativo para interfaces consistentes
* **Material 3:** Design system moderno em tons azul e branco
* **WebView Nativo:** Implementação específica para cada plataforma
* **Modo Kiosk:** Configuração de tela cheia para terminais públicos
* **Navegação Intuitiva:** Sistema de navegação otimizado para estudantes

* Obs.: * A escolha do KMP/Compose se deu pela integração direta com as plataformas desejadas (Android/Desktop) e suporte nativo.
Alem a necessidade de usar uma WebView, que é um componente nativo complexo. O mecanismo expect/actual do KMP oferece uma maneira limpa e direta de acessar e controlar APIs específicas da plataforma, que é mais seguro e flexível do que depender exclusivamente de um plugin (como o webview_flutter).
Se houvesse uma forte necessidade imediata de iOS/Web maduro ou uma preferência pela abordagem de UI 100% consistente o Flutter/Dart poderia ser considerado.

## 📂 Estrutura do Projeto

O projeto segue a estrutura padrão de um projeto Kotlin Multiplatform com Compose:

* `composeApp/`
    * `src/`
        * `commonMain/kotlin/br/ufg/`: Código compartilhado entre todas as plataformas.
            * `model/`: Modelos de dados (ex: `MenuItem`).
            * `navigation/`: Lógica de navegação (`Screen`, `NavigationState`).
            * `theme/`: Definição do tema da aplicação (`FCTUFGTheme`, cores).
            * `ui/`: Componentes de UI compartilhados (`HomeScreen`, `MenuCard`).
            * `MainCommon.kt`: Composable `App()` principal que define a estrutura da UI e a lógica de roteamento.
            * `WebViewScreen.kt`: Tela que encapsula o componente `WebView`.
            * `expect fun WebView(...)`: Declaração `expect` para a funcionalidade `WebView` que precisa ser implementada por cada plataforma.
        * `androidMain/kotlin/br/ufg/`: Código específico para a plataforma Android.
            * `MainActivity.kt`: Ponto de entrada (`Activity`) da aplicação Android.
            * `actual fun WebView(...)`: Implementação (`actual`) do `WebView` usando o `WebView` nativo do Android.
            * `MainAndroid.kt`: Função `main` e `@Preview` para o Android.
        * `desktopMain/kotlin/br/ufg/`: Código específico para a plataforma Desktop (JVM).
            * `MainDesktop.kt`: Ponto de entrada da aplicação Desktop, configura a janela.
            * `actual fun WebView(...)`: Implementação (`actual`) do `WebView` usando a biblioteca `compose-webview-multiplatform`.
* `build.gradle.kts`: Arquivo de configuração do build do Gradle, onde as dependências, plugins e configurações das plataformas (Android, Desktop) são definidos.

## 🛠️ Configuração e Build

### Pré-requisitos
* **JDK 17** ou superior
* **Android Studio** ou IntelliJ IDEA com suporte a Kotlin Multiplatform
* **Android SDK** (para compilação Android)
* **Xcode** (para compilação iOS - apenas no macOS)

### Instalação
```bash
git clone <URL_DO_REPOSITORIO>
cd TerminalConsulta
```

### Build e Execução

#### Android
```bash
# Build do projeto
./gradlew composeApp:build

# Instalação em dispositivo/emulador
./gradlew composeApp:installDebug

# Build de release
./gradlew composeApp:assembleRelease
```

#### iOS
```bash
# Gerar projeto Xcode
./gradlew composeApp:embedAndSignAppleFrameworkForXcode

# Abrir no Xcode
open iosApp/iosApp.xcodeproj
```

### Configuração do Modo Kiosk (Android)

#### Modo Básico (Atual)
O app já funciona em modo kiosk básico com as proteções implementadas.

#### Modo Avançado (Device Owner)
Para kiosk corporativo mais robusto:
```bash
# Instalar o app
./gradlew composeApp:installDebug

# Configurar como Device Owner (requer ADB e dispositivo sem contas Google)
adb shell dpm set-device-owner br.ufg.terminalconsulta/.MainActivity

# Para remover (se necessário)
adb shell dpm remove-active-admin br.ufg.terminalconsulta/.MainActivity
```

## 🔐 Como Usar o Sistema de Autenticação

### Para Estudantes (Uso Normal)
1. **Abrir o app:** Interface em tons azul e branco aparece automaticamente
2. **Navegar:** Toque nos botões grandes para acessar os serviços
3. **Usar WebView:** Navegue nas páginas com os controles da barra inferior
4. **Voltar ao menu:** Toque no ícone "Home" no topo da tela

### Para Administradores (Sair do Modo Kiosk)
1. **Ativar saída:** Toque **5 vezes rapidamente** no botão voltar (em 2 segundos)
2. **Dialog de senha:** Aparecerá uma tela pedindo senha
3. **Inserir senha:** Digite `admin123` (senha padrão)
4. **Confirmar:** Toque em "Sair" para fechar completamente o app

### Personalização da Senha
Para alterar a senha padrão, edite o arquivo `MainActivity.kt`:
```kotlin
if (password == "admin123") { // Altere aqui para sua senha
```

### Proteções Ativas
- ✅ **Botão Home:** Bloqueado, app retorna automaticamente
- ✅ **Botão Recents:** Bloqueado pelo modo kiosk
- ✅ **Barra de notificações:** Bloqueada
- ✅ **Botão Voltar:** Protegido por sistema de 5 toques + senha
- ✅ **Multitarefa:** App força retorno ao primeiro plano

## 🔮 Próximas Melhorias

* **Cache e Sessão:** Limpeza automática de dados do WebView ao retornar à tela inicial
* **Configuração remota:** Painel web para alterar links e configurações
* **Modo offline:** Informações básicas disponíveis sem conexão à internet
* **Analytics:** Monitoramento de uso e estatísticas de acesso
* **Acessibilidade:** Melhorias para usuários com necessidades especiais
* **Temas personalizados:** Opções de cores e layout por instituição
* **Notificações:** Sistema de avisos importantes para estudantes

## 🤝 Contribuindo

1. Faça o fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-funcionalidade`)
3. Faça commit das suas alterações (`git commit -m 'Adiciona nova funcionalidade'`)
4. Faça push para a branch (`git push origin feature/nova-funcionalidade`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo LICENSE para mais detalhes.

## 📞 Contato

Para mais informações sobre o projeto, entre em contato com a Faculdade de Ciência e Tecnologia da UFG.

## 📊 Status do Projeto

- ✅ **Interface responsiva** (vertical e horizontal)
- ✅ **Modo kiosk robusto** com proteção contra saída
- ✅ **Sistema de autenticação** para administradores
- ✅ **Navegação web funcional** (voltar, avançar, recarregar)
- ✅ **Multiplataforma** (Android e iOS)
- ✅ **Código documentado** em português brasileiro

## 📞 Suporte

Para dúvidas, problemas ou sugestões:
- Abra uma **issue** no repositório
- Consulte a documentação no código
- Verifique as configurações de modo kiosk

---

**Terminal de Consulta** - Solução completa para terminais públicos de consulta acadêmica.

*Desenvolvido com foco em segurança, usabilidade e facilidade de manutenção.*

---

_Última atualização: 2025 - Versão 1.0 com modo kiosk completo_