# First.fm

[Last.fm](https://www.last.fm/home) client for Android, written in Kotlin, made as a part of a school project.

## Features

- View your profile with your top tracks/albums/artists
- View track/artist/album details
- View global worldwide charts on Last.fm
- Scan a song to identify it (Shazam-like)

### Screenshots

<style>
/* Three image containers (use 25% for four, and 50% for two, etc) */
.column {
  float: left;
  width: 33.33%;
  padding: 5px;
}

/* Clear floats after image containers */
.row::after {
  content: "";
  clear: both;
  display: table;
}
</style>

<div class="row">
  <div class="column">
    <img src="doc/images/profile.jpg" alt="profile" style="height:500px">
  </div>
  <div class="column">
    <img src="doc/images/charts.jpg" alt="charts" style="height:500px">
  </div>
  <div class="column">
    <img src="doc/images/scan.jpg" alt="scan" style="height:500px">
  </div>
</div>

<div class="row">
  <div class="column">
    <img src="doc/images/artist.jpg" alt="artist" style="height:500px">
  </div>
  <div class="column">
    <img src="doc/images/album.jpg" alt="album" style="height:500px">
  </div>
  <div class="column">
    <img src="doc/images/track.jpg" alt="track" style="height:500px">
  </div>
</div>



## Build

Get and add the needed API keys to `gradle.properties`:

```
AUDD_API_TOKEN=
LASTFM_API_TOKEN=
LASTFM_API_SECRET=
SPOTIFY_API_TOKEN=
```

AudD is for the Shazam-like music scanning and Spotify is to retrive artist and album images since [the Last.fm API stopped providing them](https://stackoverflow.com/q/55978243/6945353).
