package ru.internship.gifsearcher.ui.screens.main_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.internship.gifsearcher.data.dataclasses.GifParcelable
import ru.internship.gifsearcher.ui.common.GifView
import ru.internship.gifsearcher.ui.common.LoadingScreen
import ru.internship.gifsearcher.ui.navigation.NavRoutes
import ru.internship.gifsearcher.ui.screens.main_screen.components.SearchAppBar
import ru.internship.gifsearcher.vm.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    vm: MainViewModel,
    navController: NavController
) {

    val isLoading = vm.isLoadingState.observeAsState(true)
    val gifSize = ((LocalConfiguration.current.screenWidthDp - 32) / 3).dp
    val searchText = remember { mutableStateOf("") }


    Scaffold(
        topBar = {
            SearchAppBar(
                searchText = searchText.value,
                onSearchTextChanged = { searchText.value = it },
                onClearClick = { searchText.value = "" },
                onThemeSwitch = {},
                onSearch = {}
            )
        }
    ) { paddingValues ->
        if (isLoading.value) {
            LoadingScreen(paddingValues)
        } else {
            val giffsData = vm.gifdata.observeAsState()
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(all = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                columns = GridCells.Adaptive(gifSize)
            ) {
                items(giffsData.value?.data!!) { data ->
                    GifView(
                        data = data,
                        contentScale = ContentScale.Crop,
                        clickEnabled = true,
                        modifier = Modifier
                    ) {
                        val parcelData = GifParcelable(
                            username = data.username,
                            import_datetime = data.import_datetime,
                            title = data.title,
                            source = data.source,
                            height = data.image.original.height,
                            size = data.image.original.size,
                            url = data.image.original.url,
                            width = data.image.original.width
                        )
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            key = "gif",
                            value = parcelData
                        )
                        navController.navigate(NavRoutes.DETAILS.route)
                    }
                }
            }
        }
    }
}