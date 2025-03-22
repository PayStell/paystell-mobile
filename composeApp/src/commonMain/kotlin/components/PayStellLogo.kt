package components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PayStellLogo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource("drawable/paystell_logo.xml"),
        contentDescription = "PayStell Logo",
        modifier = modifier.size(120.dp)
    )
}

