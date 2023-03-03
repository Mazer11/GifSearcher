package ru.internship.gifsearcher.ui.screens.main_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onClearClick: () -> Unit,
    onThemeSwitch: () -> Unit,
    onSearch: () -> Unit
) {
    val showClearButton by remember { derivedStateOf { searchText.isNotEmpty() } }
    val focusManager = LocalFocusManager.current

    TopAppBar(title = {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchText,
            onValueChange = { onSearchTextChanged(it) },
            placeholder = { Text(text = "Search gifs...") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "")
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = showClearButton, enter = fadeIn(), exit = fadeOut()
                ) {
                    IconButton(onClick = {
                        onClearClick()
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
                imageVector = if (isSystemInDarkTheme())
                    Icons.Filled.ModeNight
                else
                    Icons.Filled.LightMode,
                contentDescription = ""
            )
        }
    },
        modifier = Modifier.padding(top = 4.dp)
    )
}