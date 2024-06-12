package com.ihfazh.dailytrackerchild_parent.pages.dashboard

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.ihfazh.dailytrackerchild_parent.DailyTrackerChildApplication
import com.ihfazh.dailytrackerchild_parent.components.ChildData
import com.ihfazh.dailytrackerchild_parent.fp.recover
import com.ihfazh.dailytrackerchild_parent.remote.Client
import com.ihfazh.dailytrackerchild_parent.utils.ChildrenCache
import com.ihfazh.dailytrackerchild_parent.utils.DateProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val client: Client,
    private val childrenCache: ChildrenCache,
    private val dateProvider: DateProvider
): ViewModel() {

    fun collectData() {
        viewModelScope.launch(Dispatchers.IO) {
            val transformedResponse = client.todayParentDashboard()
                .transform { response ->
                    state.value.copy(
                        needConfirmationCount = response.to_review_count,
                        children = response.children.map{
                            ChildData(
                                it.id.toInt(),
                                it.photo,
                                it.name,
                                finishedCount = it.finished_count,
                                todoCount = it.todo_count,
                                pendingCount = it.pending_count,
                                udzurCount = it.udzur_count
                            )
                        },
                        loading = false
                    )
                }
                .recover {
                    state.value.copy(loading = false, error = it.msg)
                }

            _state.value = transformedResponse

        }
    }

    private val _state = MutableStateFlow(DashboardState(loading = true))
    val state: StateFlow<DashboardState>
        get() = _state

    val date = dateProvider.getDateItem()

    companion object {
        val Factory: ViewModelProvider.Factory = object: ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as DailyTrackerChildApplication
                return DashboardViewModel(
                    application.compositionRoot.client,
                    application.compositionRoot.childrenCache,
                    application.compositionRoot.dateProvider
                ) as T
            }
        }
    }
}