package com.itechsolution.birrscan.utils

import com.itechsolution.birrscan.ui.camera.Bank


object BankTransactionParserFactory {
    fun getParser(bank: Bank): BankTransactionParser {
        return when(bank) {
            Bank.CBE -> CbeTransactionParser
            Bank.TELEBIRR -> TelebirrTransactionParser
        }
    }
}
