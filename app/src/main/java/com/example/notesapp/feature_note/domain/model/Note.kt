package com.example.notesapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notesapp.ui.theme.BabyBlue
import com.example.notesapp.ui.theme.LightGreen
import com.example.notesapp.ui.theme.RedOrange
import com.example.notesapp.ui.theme.RedPink
import com.example.notesapp.ui.theme.Violet

@Entity
data class Note(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    val title: String,
    val content: String,
    val timeStamp: Long
) {

    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}
