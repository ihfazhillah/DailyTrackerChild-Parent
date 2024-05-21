package com.ihfazh.dailytrackerchild_parent.pages.shared_login

import android.accounts.Account
import android.accounts.AccountManager
import android.accounts.AccountManagerCallback
import android.accounts.AccountManagerFuture
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.ihfazh.dailytrackerchild_parent.DailyTrackerChildApplication
import com.ihfazh.dailytrackerchild_parent.fp.Failure
import com.ihfazh.dailytrackerchild_parent.fp.Success
import com.ihfazh.dailytrackerchild_parent.remote.Client
import com.ihfazh.dailytrackerchild_parent.remote.LoginBody
import com.ihfazh.dailytrackerchild_parent.utils.TokenCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SharedLoginViewModel(
    private val tokenCache: TokenCache,
    private val accountManager: AccountManager
): ViewModel() {
    fun setToken(authToken: String) {
        tokenCache.saveToken(authToken)
        _state.value = Success
    }

    private var _state = MutableStateFlow<SharedLoginState>(Initial)
    val state = _state.asStateFlow()

//    fun onLoginClicked(username: String, password: String){
//        _state.value = Submitting
//        viewModelScope.launch(Dispatchers.IO){
//            val outcome = client.login(LoginBody(username, password))
//            _state.value = when(outcome){
//                is Success -> {
//                    // we should not know about token process here, the client should handle this
//                    LoginSuccess
//                }
//
//                is Failure -> {
//                    IdleLoginState(outcome.error.msg)
//                }
//            }
//        }
//    }

    init {
        val token = tokenCache.getToken()
        if (token !== null){
           _state.value = Success
        } else {
            val accounts = accountManager.getAccountsByType("com.ihfazh.ksmauthmanager")
            if (accounts.isEmpty()){
               accountManager.addAccount("com.ihfazh.ksmauthmanager", null, null, null, null, object: AccountManagerCallback<Bundle>{
                   override fun run(result: AccountManagerFuture<Bundle>?) {
                       if (result !== null){

                           val bundle = result.getResult()
                           if (bundle !== null){

                               val intent = bundle.get(AccountManager.KEY_INTENT) as Intent
                               _state.value = NeedUserInteraction(intent)
                           }

                       }
                   }

               }, null)

            } else {
                // TODO: Handle if we have multiple accounts, and if we not have any account
                val firstAccount = accounts[0]
                Log.d("shared login view model", "${firstAccount.name} - ${firstAccount.type}")

                val options = Bundle()
                accountManager.getAuthToken(firstAccount, "com.ihfazh.ksmauthmanager", options, false, object: AccountManagerCallback<Bundle>{
                    override fun run(future: AccountManagerFuture<Bundle>?) {
                        val bundle = future?.getResult()
                        if (bundle !== null){
                            val internalToken = bundle.getString(AccountManager.KEY_AUTHTOKEN)
                            if (internalToken !== null){
                                tokenCache.saveToken(internalToken)
                                _state.value = Success
                            }
                        }
                    }
                }, Handler(OnError()))

            }
            }

    }

    class OnError: Handler.Callback {
        override fun handleMessage(p0: Message): Boolean {
            TODO("Not yet implemented")
        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = object: ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY]) as DailyTrackerChildApplication
                return SharedLoginViewModel(application.compositionRoot.tokenCacheUtil, application.compositionRoot.accountManager) as T
            }
        }
    }


}