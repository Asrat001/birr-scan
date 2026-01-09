package com.itechsolution.birrscan.utils



object OcrNormalizer {

    fun normalize(input: String): String {
        return input
            .replace("\n", " ")
            .replace("\r", " ")
            .replace(Regex("\\s+"), " ")
            .replace("—", " ")
            .replace("–", " ")
            .trim()
    }
}
