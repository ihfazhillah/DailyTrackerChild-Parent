package com.ihfazh.dailytrackerchild_parent.pages.shared_login

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.ihfazh.dailytrackerchild_parent.DailyTrackerChildApplication
import com.ihfazh.dailytrackerchild_parent.utils.TokenCache
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedLoginViewModel(
    private val tokenCache: TokenCache,
    private val accountManager: AccountManager
): ViewModel() {
    fun setToken(authToken: String) {
        tokenCache.saveToken(authToken)
        _state.value = Success
    }

    fun selectAccount(accountName: String, accountType: String) {
        val account = Account(accountName, accountType)
        getAuthToken(account)
    }

    private var _state = MutableStateFlow<SharedLoginState>(Initial)
    val state = _state.asStateFlow()

    init {
        val token = tokenCache.getToken()
        if (token !== null){
           _state.value = Success
        } else {
            val accounts = accountManager.getAccountsByType("com.ihfazh.ksmauthmanager")
            if (accounts.isEmpty()){
                addNewAccount()
            } else {
                if (accounts.size > 1){
                    val selectAccountIntent = AccountManager.newChooseAccountIntent(null, null, arrayOf("com.ihfazh.ksmauthmanager"), null, null, null, null)
                    _state.value = NeedUserInteraction(selectAccountIntent)
                } else {
                    val firstAccount = accounts[0]
                    getAuthToken(firstAccount)

                }
            }
        }

    }

    private fun addNewAccount() {
        accountManager.addAccount(
            "com.ihfazh.ksmauthmanager",
            null,
            null,
            null,
            null,
            { result ->
                if (result !== null) {

                    val bundle = result.getResult()
                    if (bundle !== null) {

                        val intent = bundle.get(AccountManager.KEY_INTENT) as Intent
                        _state.value = NeedUserInteraction(intent)
                    }

                }
            },
            null
        )
    }

    private fun getAuthToken(account: Account?) {
        val options = Bundle()
        accountManager.getAuthToken(
            account,
            "com.ihfazh.ksmauthmanager",
            options,
            false,
            { future ->
                val bundle = future?.getResult()
                if (bundle !== null) {
                    val internalToken = bundle.getString(AccountManager.KEY_AUTHTOKEN)
                    if (internalToken !== null) {
                        tokenCache.saveToken(internalToken)
                        _state.value = Success
                    }
                }
            },
            null
        )
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