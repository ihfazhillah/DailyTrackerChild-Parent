package com.ihfazh.dailytrackerchild_parent.pages.shared_login

import android.accounts.AccountManager
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


typealias OnLoginSuccess = () -> Unit

sealed interface SharedLoginState
data object Initial: SharedLoginState
data object Success: SharedLoginState
data class NeedUserInteraction(val intent: Intent) : SharedLoginState

@Composable
fun SharedLogin(
    viewModel: SharedLoginViewModel,
    onLoginSuccess: OnLoginSuccess
){

    val state = viewModel.state.collectAsState()


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){

        Column {
            Text("Logging in .... ")
        }

    }

    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                // result from get auth token
                val intent = result.data
                val authToken = intent?.getStringExtra(AccountManager.KEY_AUTHTOKEN)
                if (authToken != null){
                    viewModel.setToken(authToken)
                }

                // result from the select account
                val accountName = intent?.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
                val accountType = intent?.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE)
                if (accountName != null && accountType !== null){
                    viewModel.selectAccount(accountName, accountType)
                }
            }
        }

    LaunchedEffect(state.value) {
        if (state.value is Success){
            onLoginSuccess()
        }

        if (state.value is NeedUserInteraction){
            // get auth token / selected account
            startForResult.launch((state.value as NeedUserInteraction).intent)
        }
    }


}