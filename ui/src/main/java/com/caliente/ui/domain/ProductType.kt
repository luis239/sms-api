package com.caliente.ui.domain

enum class ProductType(val value: String? = null) {
    SPORTS("sports"), CASINO("casino"), UNDEFINED();

    companion object {
        fun fromValue(value: String?) =
            ProductType.values()
                .find { it.value.equals(value, true) }
                ?: UNDEFINED
    }
}