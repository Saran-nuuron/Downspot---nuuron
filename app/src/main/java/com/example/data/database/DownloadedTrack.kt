package com.example.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "downloaded_tracks")
data class DownloadedTrack(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val artist: String,
    val durationMs: Long,
    val localFilePath: String,
    val downloadedAt: Long = System.currentTimeMillis(),
    val playlistUrl: String
)
