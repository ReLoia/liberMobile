package com.reloia.libermobile

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.reloia.libermobile.ui.library.LibraryScreen
import com.reloia.libermobile.ui.library.LibraryScreenRepositoryImpl
import com.reloia.libermobile.ui.library.LibraryScreenViewModel
import com.reloia.libermobile.ui.recent.RecentScreen
import com.reloia.libermobile.ui.recent.RecentScreenRepositoryImpl
import com.reloia.libermobile.ui.recent.RecentScreenViewModel
import com.reloia.libermobile.ui.theme.LiberMobileTheme


data class NavBarItem(val title: String, val selectedIcon: ImageVector, val unselectedIcon: ImageVector, val route: String = title)

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val items = listOf(
            NavBarItem(getString(R.string.library), Icons.Filled.Home, Icons.Outlined.Home, "library"),
            NavBarItem(getString(R.string.recent), Icons.Filled.Refresh, Icons.Outlined.Refresh, "recent"),
            NavBarItem(getString(R.string.search), Icons.Filled.Search, Icons.Outlined.Search, "search")
        )

        setContent {
            var selectedIndex by rememberSaveable {
                mutableIntStateOf(0)
            }
            val navController = rememberNavController()

            var search by rememberSaveable {
                mutableStateOf("")
            }

            LiberMobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold (
                        topBar = {
                            TopAppBar(title = {
                                if (selectedIndex == 2) {
                                    Row(
//                                        red
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        BasicTextField(
                                            value = search,
                                            onValueChange = {
                                                search = it
                                            },
                                            singleLine = true,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(0.dp, 0.dp, 8.dp, 0.dp),
                                            textStyle = TextStyle.Default.copy(fontSize = 19.sp),
                                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search)
                                        ) {
                                            TextFieldDefaults.DecorationBox(
                                                innerTextField = it,
                                                trailingIcon = {
                                                    if (search.isNotEmpty()) {
                                                        IconButton(onClick = { search = "" }) {
                                                            Icon(Icons.Outlined.Clear, contentDescription = "Clear search")
                                                        }
                                                    }
                                                },
                                                leadingIcon = {
                                                    Icon(Icons.Filled.Search, contentDescription = "Search")
                                                },
                                                enabled = true,
                                                value = search,
                                                singleLine = true,
                                                interactionSource = androidx.compose.foundation.interaction.MutableInteractionSource(),
                                                visualTransformation = { annotatedString ->
                                                    TransformedText(annotatedString, offsetMapping = OffsetMapping.Identity)
                                                },
                                                colors = TextFieldDefaults.colors(
                                                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                                                    focusedIndicatorColor = MaterialTheme.colorScheme.surface,
                                                    unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
                                                ),
                                                contentPadding = PaddingValues(0.dp),
                                                placeholder = { Text(text = "Search...", fontSize = 19.sp) }
                                            )
                                        }
                                    }
                                } else
                                    Text(text = items[selectedIndex].title)
                            })
                        },
                        bottomBar = {
                            NavigationBar {
                                items.forEach { item ->
                                    NavigationBarItem(
                                        selected = selectedIndex == items.indexOf(item),
                                        onClick = {
                                            if (selectedIndex != items.indexOf(item)) {
                                                selectedIndex = items.indexOf(item)
                                                navController.navigate(item.route) {
                                                    launchSingleTop = true
                                                    popUpTo(navController.graph.startDestinationId) {
                                                        saveState = true
                                                    }
                                                    restoreState = true
                                                }
                                            }
                                        },
                                        alwaysShowLabel = false,
                                        icon = {
                                            Icon(
                                                imageVector = if (selectedIndex == items.indexOf(item)) item.selectedIcon else item.unselectedIcon,
                                                contentDescription = item.title
                                            )
                                        },
                                        label = {
                                            Text(text = item.title)
                                        }
                                    )
                                }
                            }
                        }
                    ) {
                        NavHost(
                            navController, startDestination = "library",
                            enterTransition = {
                                EnterTransition.None
                            },
                            exitTransition = {
                                ExitTransition.None
                            },
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                        ) {
                            navController.addOnDestinationChangedListener { _, backStackEntry, _ ->
                                if (backStackEntry != null) {
                                    val newSelectedIndex = items.indexOfFirst { it.route == backStackEntry.route }
                                    if (newSelectedIndex != -1) {
                                        selectedIndex = newSelectedIndex
                                    }
                                }
                            }
                            composable("library") {
                                val viewModel = LibraryScreenViewModel(LibraryScreenRepositoryImpl())
                                LibraryScreen(viewModel)
                            }
                            composable("recent") {
                                val viewModel = RecentScreenViewModel(RecentScreenRepositoryImpl())
                                RecentScreen(viewModel)
                            }
                            composable("search") {
                                SearchScreen(search)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchScreen(search: String = "") {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp, 0.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        val context = androidx.compose.ui.platform.LocalContext.current
        Column (
            // element gap
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(1.dp)
        ) {
            if (search.isNotEmpty()) {
                Text(text = "Search: $search")
            } else {
                Text(text = "Esplora libri, autori e molto altro...")
                BrowseTypeElement(type = "Autori", description = "Cerca libri per autore", onClick = {
                    context.startActivity(Intent(context, BrowseActivity::class.java).putExtra("type", "author"))
                })
                BrowseTypeElement(type = "Libri", description = "Cerca libri per titolo", onClick = {
                    context.startActivity(Intent(context, BrowseActivity::class.java).putExtra("type", "title"))
                })
                BrowseTypeElement(type = "Argomento", description = "Cerca libri per argomento", onClick = {
                    context.startActivity(Intent(context, BrowseActivity::class.java).putExtra("type", "argument"))
                })
            }
        }
    }
}

@Composable
fun BrowseTypeElement(
    type: String,
    description: String,
    onClick: () -> Unit
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp, 8.dp)
    ) {
        Column (
            modifier = Modifier.weight(1f)
        ) {
            Text(text = type,
                fontSize = 18.sp
            )
            Text(text = description,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}