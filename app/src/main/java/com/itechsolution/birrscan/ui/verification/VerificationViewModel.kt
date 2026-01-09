package com.itechsolution.birrscan.ui.verification

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itechsolution.birrscan.domain.respository.VerificationRepository
import com.itechsolution.birrscan.ui.camera.Bank
import com.itechsolution.birrscan.utils.BankTransactionParserFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
@HiltViewModel
class VerificationViewModel @Inject constructor(
    private val repository: VerificationRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var uiState by mutableStateOf(VerifyUiState())
        private set

    // Navigation event
    var navigateBackToHome by mutableStateOf(false)
        private set




    init {
        val path = savedStateHandle.get<String>("imagePath")
        val bank=savedStateHandle.get<String>("bank")
        if (path == null && bank==null) {
            uiState = uiState.copy(error = "Image path missing")
        } else {
            processImage(File(path), bank)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun processImage(imageFile: File, bank: String?) {
        uiState = uiState.copy(isProcessing = true)
        val selectedBank =
            if (bank?.contains("TELEBIRR") ?: false) Bank.TELEBIRR else Bank.CBE



        viewModelScope.launch {
            repository.processImage(imageFile)
                .onSuccess { ocrText ->
                val parser = BankTransactionParserFactory.getParser(selectedBank);
                val transactionData = parser.parse(ocrText)
                    uiState = uiState.copy(
                        isProcessing = false,
                        isVerified = true,
                        extractedText = ocrText,
                        transactionData = transactionData,
                        error = null
                    )
                }
                .onFailure { e ->
                    uiState = uiState.copy(
                        isProcessing = false,
                        error = e.message
                    )
                }
        }
    }
    fun updateTransaction(
        amount: Double,
        transactionId: String?
    ) {
        val current = uiState.transactionData ?: return

        uiState = uiState.copy(
            transactionData = current.copy(
                amount = amount,
                transactionId = transactionId?.takeIf { it.isNotBlank() }
            )
        )
        saveTransaction()
    }

    fun saveTransaction() {

        val data = uiState.transactionData
            ?: run {
                uiState = uiState.copy(error = "No transaction data")
                return
            }

        viewModelScope.launch {
            repository.saveReceipt(data)
                .onSuccess {
                    uiState = uiState.copy(isSaved = true, isProcessing = false)
                    // Trigger navigation
                    navigateBackToHome = true
                }
                .onFailure {
                    uiState = uiState.copy(error = it.message, isProcessing = false)
                }
        }
    }
    // Call this after navigation to reset the flag
    fun onNavigated() {
        navigateBackToHome = false
    }


}
