package com.reloia.libermobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
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


data class NavBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String = title,
)

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: replace icons with better ones
        val items = listOf(
            NavBarItem(
                getString(R.string.library),
                Icons.Filled.Home,
                Icons.Outlined.Home,
                "library"
            ),
            NavBarItem(
                getString(R.string.recent),
                Icons.Filled.Refresh,
                Icons.Outlined.Refresh,
                "recent"
            ),
            NavBarItem(
                getString(R.string.discover),
                Icons.Filled.CheckCircle,
                Icons.Outlined.CheckCircle,
                "discover"
            ),
            NavBarItem(
                getString(R.string.more),
                Icons.Filled.MoreVert,
                Icons.Outlined.MoreVert,
                "more"
            )
        )

        setContent {
            var selectedIndex by rememberSaveable {
                mutableIntStateOf(0)
            }
            val navController = rememberNavController()

            var search by rememberSaveable {
                mutableStateOf("")
            }
            var isSearchOpen by rememberSaveable {
                mutableStateOf(false)
            }

            // Boolean to check if the current selected item has a search bar
            val hasSearch = arrayListOf("library", "recent").contains(items[selectedIndex].route)
            val sheetState = rememberModalBottomSheetState()
            var showBottomSheet by remember {
                mutableStateOf(false)
            }
            var expanded by remember {
                mutableStateOf(false)
            }
            if (!hasSearch) {
                isSearchOpen = false
                search = ""
            }

            val context = androidx.compose.ui.platform.LocalContext.current

            LiberMobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            if (items[selectedIndex].route == "more") return@Scaffold
                            TopAppBar(
                                title = {
                                    // If the current selected item has a search bar and it's open, show the search bar
                                    if (hasSearch && isSearchOpen) {
                                        Row(modifier = Modifier.fillMaxWidth()) {
                                            TextField(
                                                value = search,
                                                onValueChange = { search = it },
                                                placeholder = {
                                                    Text(
                                                        text = "Search...",
                                                        fontSize = 21.sp
                                                    )
                                                },
                                                singleLine = true,
                                                keyboardOptions = KeyboardOptions.Default.copy(
                                                    imeAction = ImeAction.Search
                                                ),
                                                trailingIcon = {
                                                    if (search.isNotEmpty()) {
                                                        IconButton(onClick = { search = "" }) {
                                                            Icon(
                                                                Icons.Outlined.Clear,
                                                                contentDescription = "Clear search"
                                                            )
                                                        }
                                                    }
                                                },
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                textStyle = TextStyle.Default.copy(fontSize = 21.sp),
                                                colors = TextFieldDefaults.colors(
                                                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                                                    focusedIndicatorColor = MaterialTheme.colorScheme.surface,
                                                    unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
                                                ),
                                            )
                                        }
                                    } else
                                        Text(text = items[selectedIndex].title)
                                },
                                actions = {
                                    if (hasSearch && !isSearchOpen) {
                                        IconButton(onClick = {
                                            isSearchOpen = true
                                            search = ""
                                        }) {
                                            Icon(Icons.Filled.Search, contentDescription = "Search")
                                        }
                                    }
                                },
                                navigationIcon = {
                                    if (hasSearch && isSearchOpen) {
                                        IconButton(onClick = {
                                            isSearchOpen = false
                                            search = ""
                                        }) {
                                            Icon(
                                                Icons.Outlined.ArrowBack,
                                                contentDescription = "Back"
                                            )
                                        }
                                    }
                                }
                            )
                        },
                        bottomBar = {
                            NavigationBar {
                                items.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        icon = {
                                               Icon(
                                                   imageVector = if (selectedIndex == index) item.selectedIcon else item.unselectedIcon,
                                                    contentDescription = item.title
                                               )
                                        },
                                        label = { Text(text = item.title) },
                                        selected = selectedIndex == index,
                                        onClick = {
                                            if (selectedIndex != index) {
                                                selectedIndex = index
                                                navController.navigate(item.route) {
                                                    launchSingleTop = true
                                                    restoreState = true
                                                    popUpTo(navController.graph.startDestinationId) {
                                                        saveState = true
                                                    }
                                                }
                                            }
                                        }
                                    )
                                }
                            
                            }
                        },
                        floatingActionButton = {
                            if (items[selectedIndex].route == "discover") {
                                ExtendedFloatingActionButton(
                                    onClick = {
                                        showBottomSheet = true
                                    },
                                    text = { Text(stringResource(R.string.text_filter)) },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Filled.List,
                                            contentDescription = "Filters"
                                        )
                                    }
                                )
                            }
                        }

                    ) {
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                        ) {
                            Column {
                                if (isSearchOpen && search.isNotEmpty()) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                context.startActivity(
                                                    Intent(
                                                        context,
                                                        BrowseActivity::class.java
                                                    )
                                                        .putExtra("type", "global")
                                                        .putExtra("globalSearch", search)
                                                )
                                            }
                                            .padding(8.dp, 8.dp)

                                    ) {
                                        Column {
                                            Text(
                                                text = "Ricerca globale",
                                                fontSize = 18.sp
                                            )
                                            Text(
                                                text = "Cerca \"$search\" in tutto il catalogo",
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.onSurface.copy(
                                                    alpha = 0.6f
                                                )
                                            )
                                        }
                                    }
                                    Spacer(
                                        modifier = Modifier
                                            .height(1.dp)
                                            .background(
                                                MaterialTheme.colorScheme.onSurface.copy(
                                                    alpha = 0.4f
                                                )
                                            )
                                            .fillMaxWidth()
                                    )
                                }

                                NavHost(
                                    navController, startDestination = "library",
                                    enterTransition = {
                                        EnterTransition.None
                                    },
                                    exitTransition = {
                                        ExitTransition.None
                                    },
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    navController.addOnDestinationChangedListener { _, backStackEntry, _ ->
                                        if (backStackEntry != null) {
                                            val newSelectedIndex =
                                                items.indexOfFirst { it.route == backStackEntry.route }
                                            if (newSelectedIndex != -1) {
                                                selectedIndex = newSelectedIndex
                                            }
                                        }
                                    }
                                    composable("library") {
                                        val viewModel =
                                            LibraryScreenViewModel(LibraryScreenRepositoryImpl())
                                        LibraryScreen(viewModel, search)
                                    }
                                    composable("recent") {
                                        val viewModel =
                                            RecentScreenViewModel(RecentScreenRepositoryImpl())
                                        RecentScreen(viewModel)
                                    }

                                    // TODO: fare discover
                                    composable("discover") {
                                        SearchScreen(search)
                                    }
                                    // TODO: fare more
                                    composable("more") {
                                        Text(text = "More")
                                    }
                                }
                            }

                            Log.w(
                                "MainActivity",
                                "Selected index: $selectedIndex, route: ${items[selectedIndex].route}, showBottomSheet: $showBottomSheet"
                            )
                            if (items[selectedIndex].route == "discover" && showBottomSheet) {
                                ModalBottomSheet(
                                    onDismissRequest = {
                                        showBottomSheet = false
                                    },
                                    sheetState = sheetState,
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(MaterialTheme.colorScheme.surface)
                                    ) {
                                        Text(
                                            text = "Filters",
                                            modifier = Modifier
                                                .padding(16.dp, 8.dp)
                                                .fillMaxWidth()
                                        )
//                                        Generi:
//                                      arte e architettura
//                                      letteratura fantastica
//                                      manuali
//                                      poesia
//                                      religione
//                                      Risorgimento
//                                      saggi e trattati
//                                      Scapigliatura
//                                      teatro
//                                      Verismo
//                                      viaggiare
//                                        TODO: Aggiungere una row con titolo "Genere" e un selector con i generi
                                        Row {
                                            Text(
                                                text = "Genere",
                                                modifier = Modifier
                                                    .padding(16.dp, 8.dp)
                                                    .fillMaxWidth()
                                            )
                                            ExposedDropdownMenuBox(
                                                expanded = expanded,
                                                onExpandedChange = { expanded = it },
                                            ) {
//                                                TextField(value = , onValueChange = )
                                            }
                                        }

                                    }

                                }
                            }
                        }

                    }
                }
            }
        }
    }
}

// TODO: spostare in un file separato
@Composable
fun SearchScreen(search: String = "") {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp, 0.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        val context = androidx.compose.ui.platform.LocalContext.current
        Column(
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(1.dp)
        ) {
//            TODO:  trasformare in una lazycolumn e caricare libri da HTTP
        }
    }
}
