package fr.esgi.firstfm.lastfmapi

import com.google.gson.annotations.SerializedName

open class LastFmApiArtistGetInfoResponse(@SerializedName("artist") val artist: ArtistResponse)