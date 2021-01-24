# First.fm

## Features:

### Scan a music: ###
 First.fm is using an external API ([Audd API](https://docs.audd.io/)) to recognize the records made by the end user. This is a PAYING API, but they also provide you an `api_token` key if you explain them your working on an educational project. 

 The following steps explain how to use it:

1. Go to find your `API_TOKEN` on [Audd Dashboard](https://dashboard.audd.io/)

2. Insert your token inside `gradle.properties` at the root of the project:
 ```
 AUDD_API_TOKEN=<YOUR TOKEN>
 ```