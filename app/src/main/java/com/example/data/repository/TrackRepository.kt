package com.example.data.repository

import com.example.data.database.DownloadedTrack
import com.example.data.database.TrackDao
import kotlinx.coroutines.flow.Flow

class TrackRepository(private val trackDao: TrackDao) {
    val allTracks: Flow<List<DownloadedTrack>> = trackDao.getAllTracks()

    suspend fun insertTrack(track: DownloadedTrack): Long {
        return trackDao.insertTrack(track)
    }

    suspend fun deleteTrackById(id: Int) {
        trackDao.deleteTrackById(id)
    }

    suspend fun getTracksCount(): Int {
        return trackDao.getTracksCount()
    }
}
