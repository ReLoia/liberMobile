package com.reloia.libermobile

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.reloia.libermobile.ui.discover.DiscoverScreen
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

        setContent {
            val items =
                listOf(
                    NavBarItem(
                        getString(R.string.library),
                        ImageVector.vectorResource(id = R.drawable.baseline_local_library_24),
                        ImageVector.vectorResource(id = R.drawable.outline_local_library_24),
                        "library",
                    ),
                    NavBarItem(
                        getString(R.string.recent),
                        ImageVector.vectorResource(id = R.drawable.baseline_history_24),
                        ImageVector.vectorResource(id = R.drawable.baseline_history_24),
                        "recent",
                    ),
                    NavBarItem(
                        getString(R.string.discover),
                        ImageVector.vectorResource(id = R.drawable.baseline_public_24),
                        ImageVector.vectorResource(id = R.drawable.twotone_public_24),
                        "discover",
                    ),
                    NavBarItem(
                        getString(R.string.more),
                        Icons.Filled.MoreVert,
                        Icons.Outlined.MoreVert,
                        "more",
                    ),
                )

            var selectedIndex by rememberSaveable {
                mutableIntStateOf(0)
            }
            val navController = rememberNavController()

            // Boolean to check if the current selected item has a search bar
            // Search for variables for library and recent
            var search by rememberSaveable {
                mutableStateOf("")
            }
            var isSearchOpen by rememberSaveable {
                mutableStateOf(false)
            }
            val hasSearch = arrayListOf("library", "recent").contains(items[selectedIndex].route)
            if (!hasSearch) {
                isSearchOpen = false
                search = ""
            }
            // Search for variables for discover
            var globalSearch by remember {
                mutableStateOf("")
            }
            // Debounce the search
            var toGlobalSearch by rememberSaveable {
                mutableStateOf(globalSearch)
            }
            var isGlobalSearchOpen by rememberSaveable {
                mutableStateOf(false)
            }
            val hasGlobalSearch = items[selectedIndex].route == "discover"
            if (!hasGlobalSearch) {
                globalSearch = ""
                isGlobalSearchOpen = false
            }
            // Loading variables
            var isLoading = remember {
                mutableStateOf(false)
            }

            // Bottom sheet for filters
            val sheetState = rememberModalBottomSheetState()
            var showBottomSheet by remember {
                mutableStateOf(false)
            }
            var expanded by remember {
                mutableStateOf(false)
            }
            // TODO: migliorare la gestione dei filtri
            val filters = arrayListOf<String>(
                "Arte e architettura",
                "Letteratura fantastica",
                "Manuali",
                "Poesia",
                "Religione",
                "Risorgimento",
                "Saggi e trattati",
                "Scapigliatura",
                "Teatro",
                "Verismo",
                "Viaggiare",
            )
//            var selectedFilter by remember {
//                mutableStateOf(0)
//            }
            var searchedFilter by remember {
                mutableStateOf("")
            }

            // Close search bar when back button is pressed
            BackHandler(
                enabled = isSearchOpen || isGlobalSearchOpen,
            ) {
                if (isSearchOpen) {
                    isSearchOpen = false
                    search = ""
                }
                if (isGlobalSearchOpen) {
                    isGlobalSearchOpen = false
                    globalSearch = ""
                }
            }

            val view = LocalView.current
            val context = view.context

            LiberMobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val inDarkMode = isSystemInDarkTheme()
                    val backgroundColor = MaterialTheme.colorScheme.background
                    SideEffect {
                        (context as Activity).window.statusBarColor = backgroundColor.toArgb()
                        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !inDarkMode
                    }
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
                                                        fontSize = 21.sp,
                                                    )
                                                },
                                                singleLine = true,
                                                keyboardOptions = KeyboardOptions.Default.copy(
                                                    imeAction = ImeAction.Search,
                                                ),
                                                trailingIcon = {
                                                    if (search.isNotEmpty()) {
                                                        IconButton(onClick = { search = "" }) {
                                                            Icon(
                                                                Icons.Outlined.Clear,
                                                                contentDescription = stringResource(R.string.clear_search),
                                                            )
                                                        }
                                                    }
                                                },
                                                modifier =
                                                    Modifier
                                                        .fillMaxWidth(),
                                                textStyle = TextStyle.Default.copy(fontSize = 21.sp),
                                                colors =
                                                    TextFieldDefaults.colors(
                                                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                                                        focusedIndicatorColor = MaterialTheme.colorScheme.surface,
                                                        unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
                                                    ),
                                            )
                                        }
                                    } else if (hasGlobalSearch && isGlobalSearchOpen) {
                                        Row(modifier = Modifier.fillMaxWidth()) {
                                            TextField(
                                                value = toGlobalSearch,
                                                onValueChange = { toGlobalSearch = it },
                                                placeholder = {
                                                    Text(
                                                        text = "Global Search...",
                                                        fontSize = 21.sp,
                                                    )
                                                },
                                                singleLine = true,
                                                keyboardOptions =
                                                    KeyboardOptions.Default.copy(
                                                        imeAction = ImeAction.Search,
                                                    ),
                                                keyboardActions =
                                                    KeyboardActions(
                                                        onSearch = {
                                                            globalSearch = toGlobalSearch
                                                            isGlobalSearchOpen = false
                                                        },
                                                    ),
                                                trailingIcon = {
                                                    if (toGlobalSearch.isNotEmpty()) {
                                                        IconButton(onClick = { toGlobalSearch = "" }) {
                                                            Icon(
                                                                Icons.Outlined.Clear,
                                                                contentDescription = stringResource(R.string.clear_search),
                                                            )
                                                        }
                                                    }
                                                },
                                                modifier =
                                                    Modifier
                                                        .fillMaxWidth(),
                                                textStyle = TextStyle.Default.copy(fontSize = 21.sp),
                                                colors =
                                                    TextFieldDefaults.colors(
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
//                                        TODO: add option to handle how the entries will be visualized and sorted
                                    } else if (hasGlobalSearch && !isGlobalSearchOpen) {
                                        IconButton(onClick = {
                                            isGlobalSearchOpen = true
                                            globalSearch = ""
                                        }) {
                                            Icon(ImageVector.vectorResource(id = R.drawable.baseline_travel_explore_24), contentDescription = "Search")
                                        }
                                    }
                                },
                                navigationIcon = {
                                    if ((hasSearch && isSearchOpen) || (hasGlobalSearch && isGlobalSearchOpen)) {
                                        IconButton(onClick = {
                                            isSearchOpen = false
                                            search = ""
                                            isGlobalSearchOpen = false
                                            globalSearch = ""
                                        }) {
                                            Icon(
                                                Icons.AutoMirrored.Outlined.ArrowBack,
                                                contentDescription = "Back",
                                            )
                                        }
                                    }
                                },
                            )
                        },
                        bottomBar = {
                            NavigationBar {
                                items.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        icon = {
                                            Crossfade(targetState = selectedIndex == index, label = "") {
                                                Icon(
                                                    imageVector = if (it) item.selectedIcon else item.unselectedIcon,
                                                    contentDescription = item.title,
                                                )
                                            }
                                        },
                                        label = { Text(text = item.title) },
                                        selected = selectedIndex == index,
                                        onClick = {
                                            if (selectedIndex != index) {
                                                selectedIndex = index
                                                navController.navigate(item.route) {
                                                    isLoading.value = false

                                                    launchSingleTop = true
                                                    restoreState = true
                                                    popUpTo(navController.graph.startDestinationId) {
                                                        saveState = true
                                                    }
                                                }
                                            }
                                        },
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
                                            imageVector = Icons.AutoMirrored.Filled.List,
                                            contentDescription = "Filters",
                                        )
                                    },
                                )
                            }
                        },
                    ) {
                        Surface(
                            modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(it),
                        ) {
                            Column {
                                if (isLoading.value)
                                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

                                if (isSearchOpen && search.isNotEmpty()) {
                                    Row(
                                        modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                navController.navigate("discover")

                                                globalSearch = search
                                                isSearchOpen = false
                                                isGlobalSearchOpen = true
                                            }
                                            .padding(8.dp, 8.dp),
                                    ) {
                                        Column {
                                            Text(
                                                text = "Ricerca globale",
                                                fontSize = 18.sp,
                                            )
                                            Text(
                                                text = "Cerca \"$search\" in tutto il catalogo",
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                            )
                                        }
                                    }
                                    Spacer(
                                        modifier = Modifier
                                            .height(1.dp)
                                            .background(
                                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                                            )
                                            .fillMaxWidth(),
                                    )
                                }

                                NavHost(
                                    navController,
                                    startDestination = "library",
                                    enterTransition = {
                                        EnterTransition.None
                                    },
                                    exitTransition = {
                                        ExitTransition.None
                                    },
                                    modifier = Modifier.fillMaxSize(),
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
                                        LibraryScreen(viewModel, search, isLoading)
                                    }
                                    composable("recent") {
                                        val viewModel =
                                            RecentScreenViewModel(RecentScreenRepositoryImpl())
                                        RecentScreen(viewModel, isLoading)
                                    }

                                    // TODO: fare discover
                                    composable("discover") {
                                        DiscoverScreen(globalSearch, isLoading)
                                    }
                                    // TODO: fare more
                                    composable("more") {
                                        Text(text = "More")
                                    }
                                }
                            }

                            // Filters Bottom Sheet for Discover screen
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
                                            .padding(bottom = 56.dp),
                                    ) {
                                        Text(
                                            text = "Filters",
                                            modifier = Modifier
                                                .padding(16.dp, 8.dp)
                                                .fillMaxWidth(),
                                        )
//                                        TODO: Aggiungere una row con titolo "Genere" e un selector con i generi
                                        Row {
//                                            Text(
//                                                text = "Genere",
//                                                modifier = Modifier
//                                                    .padding(16.dp, 8.dp)
//                                                    .fillMaxWidth()
//                                            )
                                            ExposedDropdownMenuBox(
                                                expanded = expanded,
                                                onExpandedChange = { expanded = it },
                                                modifier = Modifier.background(androidx.compose.ui.graphics.Color.Red),
//                                                content = {
//                                                    Text(text = "Genere")
//                                                }
                                            ) {
                                                TextField(
                                                    value = searchedFilter,
                                                    onValueChange = { searchedFilter = it },
//                                                    placeholder = {
//                                                        Text(
//                                                            text = "Genere",
//                                                            fontSize = 21.sp
//                                                        )
//                                                    },
                                                    label = {
                                                        Text(
                                                            text = "Genere",
//                                                            fontSize = 21.sp
                                                        )
                                                    },
                                                    modifier = Modifier.menuAnchor(),
                                                    trailingIcon = {
//                                                        if (searchedFilter.isNotEmpty()) {
//                                                            IconButton(onClick = { searchedFilter = "" }) {
//                                                                Icon(
//                                                                    Icons.Outlined.Clear,
//                                                                    contentDescription = "Clear search"
//                                                                )
//                                                            }
//                                                        }
                                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                                    },
                                                )

                                                var filteredFilters = filters.filter { it.contains(searchedFilter, ignoreCase = true) }
                                                if (filteredFilters.isEmpty()) {
                                                    filteredFilters = filters
                                                }

                                                DropdownMenu(
                                                    expanded = expanded,
                                                    onDismissRequest = { expanded = false },
                                                    properties = PopupProperties(focusable = true),
                                                    modifier =
                                                    Modifier
                                                        .background(androidx.compose.ui.graphics.Color.White)
                                                        .exposedDropdownSize(true),
                                                ) {
                                                    filteredFilters.forEach { filter ->
                                                        DropdownMenuItem(
                                                            text = {
                                                                Text(
                                                                    text = filter,
//                                                                    modifier = Modifier.padding(8.dp, 8.dp)
                                                                )
                                                            },
                                                            onClick = {
                                                                searchedFilter = filter
                                                                expanded = false
                                                            },
                                                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                                        )
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
    }
}

