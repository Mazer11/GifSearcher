package ru.internship.gifsearcher.ui.screens.main_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ru.internship.gifsearcher.R

/**[TopAppBar] with text field and icon button.
 * @param searchText current input. Limited by 50 characters.
 * @param isDarkTheme current theme mod.
 * @param onSearchTextChanged works on search text change.
 * @param onThemeSwitch handles theme button click.
 * @param onClearClick works when user clicks on clear button on search bar.
 * @param onSearch handles search event.
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    searchText: String,
    isDarkTheme: Boolean,
    onSearchTextChanged: (String) -> Unit,
    onClearClick: () -> Unit,
    onThemeSwitch: () -> Unit,
    onSearch: () -> Unit
) {
    var checkText by remember { mutableStateOf(searchText) }
    val showClearButton by remember { derivedStateOf { checkText.isNotEmpty() } }
    val focusManager = LocalFocusManager.current

    TopAppBar(title = {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchText,
            textStyle = MaterialTheme.typography.bodyMedium,
            onValueChange = {
                if (it.length < 50) {
                    onSearchTextChanged(it)
                    checkText = it
                }
            },
            placeholder = { Text(text = stringResource(R.string.search_gif)) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "")
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = showClearButton, enter = fadeIn(), exit = fadeOut()
                ) {
                    IconButton(onClick = {
                        onClearClick()
                        onSearch()
                        checkText = ""
                        focusManager.clearFocus()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Close, contentDescription = null
                        )
                    }
                }
            },
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                onSearch()
            }),
        )
    }, navigationIcon = {
        IconButton(onClick = { onThemeSwitch() }) {
            Icon(
                imageVector = if (isDarkTheme)
                    Icons.Filled.ModeNight
                else
                    Icons.Filled.LightMode,
                contentDescription = ""
            )
        }
    },
        modifier = Modifier.padding(all = 8.dp)
    )
}