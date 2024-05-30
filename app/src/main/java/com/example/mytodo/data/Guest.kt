package com.example.mytodo.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Guest(
    val id: String,
    val name: String,
    val category: Category,
    val address: String,
    val age: Int
) : Parcelable

enum class Category {
    FRIEND, FAMILY, COMPANION
}
