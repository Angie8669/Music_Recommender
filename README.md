# Music Recommender

## Setup
1. Get API key following steps on [Spotify Web API documentation website](https://developer.spotify.com/documentation/web-api).
2. Create config.properties file under `resources/` folder.
3. Add `client_id` and `client_secret` to config.properties. You can reference `config.properties.example`.
4. (In IntelliJ) Ensure `src/` folder is set as Sources Root, and `resources/` is set as Resources Root.
5. (In IntelliJ) You may need to sync all Maven projects to ensure dependencies are properly installed.
5. Run Main.java

## Features
- Allows users to search for songs, albums and artists to add to recommendation seeds.
- Allows users to get song recommendations based on seeded songs, artists, and albums.
- Allows users to clear their seeded data to start over.
- Handles exceptions from Spotify API due to bad queries, as well as handling empty results.

## Design Patterns
- Factory - Uses a factory to build an HttpUrlConnection object based on request method and URL parts.
- Observer - Creates SectionListener objects for each section of the UI that represents songs, artists, or albums. All
SectionListeners are managed by the SectionManager. The Controller retrieves data from the Model, which is passed to the
SectionManager to ask each SectionListener to update the proper parts of the UI.
- Strategy - Implements multiple strategies for getting recommendations, based on if genre data is available for 
specified seeds.
- Singleton - Ensures only one instance of APIService exists. Ensures that only one instance of any SpotifyObject 
(artist, album, or track) at any time.

## Demo: 
[Demo Link](https://drive.google.com/file/d/1GFogJ5CsbDhItLwNo-cK5llsE5dRTaJE/view?usp=sharing)