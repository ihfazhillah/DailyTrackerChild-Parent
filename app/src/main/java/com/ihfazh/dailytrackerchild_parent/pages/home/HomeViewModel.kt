package com.ihfazh.dailytrackerchild_parent.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.ihfazh.dailytrackerchild_parent.DailyTrackerChildApplication
import com.ihfazh.dailytrackerchild_parent.fp.Failure
import com.ihfazh.dailytrackerchild_parent.fp.Success
import com.ihfazh.dailytrackerchild_parent.remote.Client
import com.ihfazh.dailytrackerchild_parent.utils.ChildrenCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val client: Client,
    private val childrenCache: ChildrenCache
): ViewModel(){
    private var _state = MutableStateFlow<ChildState>(Loading)
    val state = _state.asStateFlow()

    fun getChildren(){
        _state.value = Loading

        viewModelScope.launch(Dispatchers.IO){
            val outcome = client.getChildren()
            _state.value = when(outcome){
                is Success -> {
                    childrenCache.saveProfiles(outcome.value.profiles)
                    Idle(outcome.value.profiles)
                }
                is Failure -> {
                    Error(outcome.error.msg)
                }
            }
        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = object: ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY]) as DailyTrackerChildApplication
                return HomeViewModel(
                    application.compositionRoot.client,
                    application.compositionRoot.childrenCache
                ) as T
            }
        }
    }
}