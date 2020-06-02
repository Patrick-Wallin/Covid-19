package com.pwmobiledeveloper.covid19.utils

import androidx.databinding.InverseMethod
import timber.log.Timber
import java.text.NumberFormat

class utils {
    companion object {
    }


}

public enum class Covid19ApiStatus {
    LOADING, ERROR, DONE;

    override fun toString(): String {
        return super.toString()
    }
}

class DataBindingConverters {
    companion object {
        private val OFFSET : Int = 127397

        @InverseMethod("convertIntegerToString")
        @JvmStatic
        fun convertIntegerToString(value: Int): String? {
            val s: String = NumberFormat.getIntegerInstance().format(value)
            return s
        }

        @InverseMethod("convertCountryCodeToEmojiFlag")
        @JvmStatic
        fun convertCountryCodeToEmojiFlag(countryCode: String) : String {
            Timber.d(countryCode)
            return countryCode
                .toUpperCase()
                .map { char ->
                    Character.codePointAt("$char", 0) - 0x41 + 0x1F1E6
                }
                .map { codePoint ->
                    Character.toChars(codePoint)
                }
                .joinToString(separator = "") { charArray ->
                    String(charArray)
                }
            /*
            var result : String = ""

            if(countryCode != null && countryCode.length >= 2) {

                val emojiFlag = countryCode.toUpperCase().map {
                    // it - 0x41 + 0x1F1E6
                    //it + OFFSET

                }.joinToString("")

                result = emojiFlag

                Timber.d("Result: " + result)
            }

            return result

             */
        }

    }
}