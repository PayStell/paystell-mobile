package org.paystell.app

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview

// Define the navigation items with Material 3 icons and labels
sealed class BottomNavItem(
    val route: String,
    val label: String,
    val filledIcon: @Composable () -> Unit,
    val outlinedIcon: @Composable () -> Unit,
    val contentDescription: String
) {
    object Home : BottomNavItem(
        "home",
        "Home",
        { Icon(Icons.Filled.Home, contentDescription = null) },
        { Icon(Icons.Outlined.Home, contentDescription = null) },
        "Home"
    )

    object Transactions : BottomNavItem(
        "transactions",
        "Transactions",
        { Icon(Icons.Filled.List, contentDescription = null) },
        { Icon(Icons.Outlined.List, contentDescription = null) },
        "Transactions"
    )

    object SendReceive : BottomNavItem(
        "send_receive",
        "Send/Receive",
        { Icon(Icons.Filled.Send, contentDescription = null) },
        { Icon(Icons.Outlined.Send, contentDescription = null) },
        "Send and Receive"
    )

    object Settings : BottomNavItem(
        "settings",
        "Settings",
        { Icon(Icons.Filled.Settings, contentDescription = null) },
        { Icon(Icons.Outlined.Settings, contentDescription = null) },
        "Settings"
    )
}

@Composable
fun BottomNavigationBar(selectedItem: BottomNavItem, onItemSelected: (BottomNavItem) -> Unit) {
    NavigationBar {
        val items = listOf(
            BottomNavItem.Home,
            BottomNavItem.Transactions,
            BottomNavItem.SendReceive,
            BottomNavItem.Settings
        )
        items.forEach { item ->
            val selected = item == selectedItem
            val iconColor by animateColorAsState(
                targetValue = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                animationSpec = tween(durationMillis = 300)
            )
            NavigationBarItem(
                icon = {
                    if (selected) {
                        item.filledIcon()
                    } else {
                        item.outlinedIcon()
                    }
                },
                label = { Text(item.label) },
                selected = selected,
                onClick = { onItemSelected(item) },
                modifier = Modifier.semantics { contentDescription = item.contentDescription },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = iconColor,
                    unselectedIconColor = iconColor,
                    selectedTextColor = iconColor,
                    unselectedTextColor = iconColor
                )
            )
        }
    }
}

@Composable
fun HomeScreen() {
    Text("Home Screen")
}

@Composable
fun TransactionsScreen() {
    Text("Transactions Screen")
}

@Composable
fun SendReceiveScreen() {
    Text("Send/Receive Screen")
}

@Composable
fun SettingsScreen() {
    Text("Settings Screen")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    var selectedItem by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Home) }

    MaterialTheme(colorScheme = lightColorScheme()) {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(selectedItem = selectedItem, onItemSelected = { selectedItem = it })
            }
        ) { innerPadding ->
            AnimatedContent(
                targetState = selectedItem,
                transitionSpec = { fadeIn(tween(300)) with scaleOut(tween(300)) }
            ) { screen ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .semantics { contentDescription = when (screen) {
                            is BottomNavItem.Home -> "Home Screen Content"
                            is BottomNavItem.Transactions -> "Transactions Screen Content"
                            is BottomNavItem.SendReceive -> "Send and Receive Screen Content"
                            is BottomNavItem.Settings -> "Settings Screen Content"
                            else -> ""
                        }}
                ) {
                    when (screen) {
                        is BottomNavItem.Home -> HomeScreen()
                        is BottomNavItem.Transactions -> TransactionsScreen()
                        is BottomNavItem.SendReceive -> SendReceiveScreen()
                        is BottomNavItem.Settings -> SettingsScreen()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    App()
}
