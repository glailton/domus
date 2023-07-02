package glailton.io.github.domus.ui.presentation.screens.main

import androidx.lifecycle.ViewModel
import glailton.io.github.domus.core.data.local.DataStoreManager
import glailton.io.github.domus.core.data.local.PreferenceDataStoreConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(private val dataStoreManager: DataStoreManager): ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    var userId: String = ""

    fun getUserId() {
        userId = dataStoreManager.getSyncFirstPreference(PreferenceDataStoreConstants.USER_KEY, "")
        _state.update {
            it.copy(userId = userId)
        }
    }
}