import androidx.compose.runtime.Composable
import moe.tlaster.precompose.PreComposeApp
import navigation.NavGraph
import theme.AppTheme

@Composable
fun App() {
    PreComposeApp {
        AppTheme {
            NavGraph()
        }
    }
}

