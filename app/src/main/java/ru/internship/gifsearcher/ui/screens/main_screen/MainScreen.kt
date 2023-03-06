package ru.internship.gifsearcher.ui.screens.main_screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.internship.gifsearcher.R
import ru.internship.gifsearcher.data.dataclasses.GifParcelable
import ru.internship.gifsearcher.ui.common.GifView
import ru.internship.gifsearcher.ui.common.LoadingScreen
import ru.internship.gifsearcher.ui.navigation.NavRoutes
import ru.internship.gifsearcher.ui.screens.main_screen.components.SearchAppBar
import ru.internship.gifsearcher.vm.MainViewModel

/**First screen of contest. Displays searchbar to search Gifs by words.
 *
 * Icon button switches theme mod.
 *
 * And LazyGrid with gifs. Paginating new gifs when scrolls to the end.
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    vm: MainViewModel,
    navController: NavController
) {
    /**Shows loading screen if true.*/
    val isLoading = vm.isLoadingState.observeAsState(true)

    /**Size of one grid cell of LazyVerticalGrid.*/
    val gifSize = ((LocalConfiguration.current.screenWidthDp - 32) / 3).dp

    /**User input in search bar.*/
    val searchText = remember { mutableStateOf("") }

    /**Current user search query value*/
    val tagText = vm.tagText.observeAsState("")

    /**Current app theme mode.*/
    val isDarkTheme = vm.isDarkTheme.observeAsState()

    /**True if unable to send request to server.*/
    val isLoadingFailed = vm.loadingFailed.observeAsState()

    /**True if currently loading new page of gifs*/
    val isPageLoading = vm.isPageLoading.observeAsState()

    Scaffold(
        topBar = {
            SearchAppBar(
                searchText = searchText.value,
                isDarkTheme = isDarkTheme.value ?: vm.getAppTheme(),
                onSearchTextChanged = { searchText.value = it },
                onClearClick = {
                    searchText.value = ""
                    vm.resetCurrentPage()
                },
                onThemeSwitch = { vm.switchAppTheme() },
                onSearch = {
                    vm.onSearch(searchText.value)
                }
            )
        }
    ) { paddingValues ->
        if (isLoading.value) {
            LoadingScreen(paddingValues)
        } else {

            if (isLoadingFailed.value == true) {

                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                    ) {
                        Text(
                            text = stringResource(R.string.failed_load),
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = {
                                vm.retryLoading()
                            },
                        ) {
                            Text(text = stringResource(R.string.retry))
                        }
                    }
                }

            } else {
                /**Current list of gifs.*/
                val giffsData = vm.gifData.observeAsState()
                Log.e("BUGFIX", "giffsData is ${giffsData.value?.data?.size}")
                if (giffsData.value?.data.isNullOrEmpty())
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        Text(
                            text = stringResource(R.string.nothing_show),
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        Text(
                            text = if (tagText.value.isEmpty())
                                stringResource(R.string.trending)
                            else
                                stringResource(R.string.results_for) + tagText.value,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        LazyVerticalGrid(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(all = 8.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            columns = GridCells.Adaptive(gifSize)
                        ) {
                            itemsIndexed(giffsData.value?.data!!) { index, data ->
                                GifView(
                                    data = data.image.original.url,
                                    contentScale = ContentScale.Crop,
                                    clickEnabled = true,
                                    modifier = Modifier,
                                    onLoading = {
                                        if (index >= vm.currentPage.minus(3)) {
                                            vm.switchPageLoadingIndicator()
                                        }
                                    },
                                    onLoadSuccess = {
                                        if (index >= vm.currentPage.minus(3)) {
                                            if (tagText.value.isEmpty())
                                                vm.loadNextTrendingGifsPage()
                                            else
                                                vm.loadNextSearchedGifsPage(value = searchText.value)
                                            vm.switchPageLoadingIndicator()
                                        }
                                    },
                                    onError = {
                                        vm.LoadingFailed()
                                    }
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
                                    vm.resetCurrentPage()
                                    navController.navigate(NavRoutes.DETAILS.route)
                                }
                            }

                            if (isPageLoading.value == true)
                                item(
                                    span = {
                                        GridItemSpan(3)
                                    }
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp)
                                    ) {
                                        Text(text = stringResource(R.string.loading_img))
                                    }
                                }
                        }
                    }
                }
            }
        }
    }
}