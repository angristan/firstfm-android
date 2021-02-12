# First.fm

[Last.fm](https://www.last.fm/home) client for Android, written in Kotlin, made as a part of a school project.

## Features

- View your profile with your top tracks/albums/artists
- View track/artist/album details
- View global worldwide charts on Last.fm
- Scan a song to identify it (Shazam-like)

### Screenshots

<p align="middle" float="left">
    <img src="doc/images/profile.jpg" alt="profile" width="30%">
    <img src="doc/images/charts.jpg" alt="charts" width="30%">
    <img src="doc/images/scan.jpg" alt="scan" width="30%">
</p>

<p align="middle" float="left">
    <img src="doc/images/artist.jpg" alt="artist" width="30%">
    <img src="doc/images/album.jpg" alt="album" width="30%">
    <img src="doc/images/track.jpg" alt="track" width="30%">
</p>

## Build

Get and add the needed API keys to `gradle.properties`:

```
AUDD_API_TOKEN=
LASTFM_API_TOKEN=
LASTFM_API_SECRET=
SPOTIFY_API_TOKEN=
```

AudD is for the Shazam-like music scanning and Spotify is to retrive artist and album images since [the Last.fm API stopped providing them](https://stackoverflow.com/q/55978243/6945353).
