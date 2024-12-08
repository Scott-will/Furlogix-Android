package com.example.vetapp.reports

enum class FieldType {
    TEXT,
    NUMBER,
    BOOLEAN
}

class FieldTypeValidator{
    companion object{
        fun validateFieldTypeWithValue(type : FieldType, value : String) : Boolean{
            when(type){
                FieldType.TEXT -> return true
                FieldType.BOOLEAN -> return tryParseBoolean(value)
                FieldType.NUMBER -> return parseNumber(value)
            }
        }

        fun tryParseBoolean(str: String): Boolean {
            return when (str.lowercase()) {
                "true" -> true
                "false" -> true
                else -> false  // Return null if the string is not a valid boolean
            }
        }

        fun parseNumber(str : String) : Boolean{
            return str.toIntOrNull() != null || str.toDoubleOrNull() != null
        }
    }

}