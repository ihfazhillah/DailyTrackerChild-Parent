package com.ihfazh.dailytrackerchild_parent.pages.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier


typealias OnLoginSuccess = () -> Unit

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: OnLoginSuccess,
    modifier: Modifier = Modifier
){
    val state = viewModel.state.collectAsState()


    Login(
        state = state.value,
        modifier = modifier,
        onLoginClicked = { username, password ->
            viewModel.onLoginClicked(username, password)
        }
    )

    LaunchedEffect(state.value){
        if (state.value is LoginSuccess){
            onLoginSuccess()
        }
    }
}

