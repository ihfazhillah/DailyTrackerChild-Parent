package com.ihfazh.dailytrackerchild_parent.pages.shared_login

import android.accounts.AccountManager
import android.app.Activity
import android.content.Intent
import android.util.Log
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

    /*
        1.  we need check if we already have a token from cache
        2. if no token:
            - ask android for token:
            ---> we may get more than one TODO handle this
                a. get token, save into our internal cache
                b. open get token page using intent:
                    - if result is ok:
                        get token again
         3. token already there: -> go to on success

         states:
         - initial
         - success -> setelah dapat token
         - need_user_interaction

     */


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){

        Column {
            Text("Logging in .... ")
        }

    }

    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d("Shared Login", "On Result")
            Log.d("Shared Login", "$result")


            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                val authToken = intent?.getStringExtra(AccountManager.KEY_AUTHTOKEN)
                if (authToken != null){
                    viewModel.setToken(authToken)
                }
                Log.d("Shared Login", authToken ?: "not found")
            }
        }

    LaunchedEffect(state.value) {
        if (state.value is Success){
            onLoginSuccess()
        }

        if (state.value is NeedUserInteraction){
            startForResult.launch((state.value as NeedUserInteraction).intent)
        }
    }


}