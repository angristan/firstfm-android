package fr.esgi.firstfm.lastfmapi

import com.google.gson.annotations.SerializedName

open class LastFmApiTrackGetInfoResponse(@SerializedName("track") val track: TrackResponse)