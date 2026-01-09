package com.itechsolution.birrscan.ui.home


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itechsolution.birrscan.domain.respository.HomeRepository
import com.itechsolution.birrscan.ui.verification.VerifyUiState
import com.itechsolution.birrscan.utils.TimeFilter
import com.itechsolution.birrscan.utils.getTodayTimestamps
import com.itechsolution.birrscan.utils.toTimestampRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val currentFilter = MutableStateFlow<TimeFilter>(TimeFilter.Today)

    init {
        currentFilter.flatMapLatest { filter ->
            val (start, end) = filter.toTimestampRange()
            when (filter) {
                is TimeFilter.Today -> repository.getTodayFlow(start, end)
                is TimeFilter.Last7Days -> repository.getLast7DaysFlow(start)
                is TimeFilter.Custom -> repository.getCustomRangeFlow(start, end)
            }
        }
            .onStart { _uiState.value = _uiState.value.copy(isLoading = true) }
            .catch { e -> _uiState.value = _uiState.value.copy(isLoading = false, error = e.message) }
            .onEach { list ->
                _uiState.value = HomeUiState(
                    transactions = list,
                    totalAmount = list.sumOf { it.amount ?: 0.0 },
                    isLoading = false
                )
            }
            .launchIn(viewModelScope)
    }

    fun setFilter(filter: TimeFilter) {
        currentFilter.value = filter
    }

}


