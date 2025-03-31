import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import paystell.composeapp.generated.resources.Res
import paystell.composeapp.generated.resources.drawable.paystell_logo

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PayStellLogo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(Res.drawable.paystell_logo),
        contentDescription = "PayStell Logo",
        modifier = modifier.size(120.dp)
    )
}

