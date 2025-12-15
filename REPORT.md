# Project Report

## Challenges I Faced
**Challenge 1: Spotify Recommendations API deprecated**
* Problem: Spotify's Recommendation API is deprecated, and can no longer be accessed.
* Solution: Pass genres to the Search API to accomplish a similar result.

**Challenge 2: Not all Spotify objects have genres in Spotify data**
* Problem: When querying album and artist data, I noticed that not every artist or album had genres assigned to them.
* Solution: Implement a secondary pattern to get track recommendations based on the seeded artists.

**Challenge 3: Spotify API returns few to no results when multiple genres or artists are specified**
* Problem: When implementing strategies, I found that specifying a large number of genres or more than one artist tends 
to return no results from the Spotify API, leading to no recommendations for the user.
* Solution: Randomly select one genre or artist based on seeded data to be used in the query for recommendations.

## Design Pattern Justifications
**Factory Pattern:** Using a factory to create HttpUrlConnection objects moves code out of the APIService class, and 
allows us to have a cleaner function that can handle both POST and GET requests.

**Observer Pattern:** Using SectionListeners cleans up the Controller significantly, and allows for a simple function 
call to update each individual section with the proper data.

**Strategy Pattern:** Not all songs have genres associated with them, so needed multiple strategies for getting 
recommendations.

**Singleton Pattern:** Generating multiple APIServices is unnecessary, and could cost storage space. Also, generating 
multiple copies of the same SpotifyObject could lead to excessive storage issues, or requiring extra API calls for the 
same data.

## OOP Pillars
**Encapsulation:** Nearly every class, but especially those under the `model` package (Spotify data objects) make sure to
use private variables, with only the appropriate getters and setters provided as needed.

**Inheritance:** `SpotifyObject` is inherited by `SpotifyArtist`, `SpotifyAlbum`, and `SpotifyTrack`. This allows it to 
pass around `SpotifyObjects` more generally, and update the UI on a case-by-case basis.

**Polymorphism:** `RecommendationStrategy` is abstract, and requires a `getRecommendations()` method to be implemented. 
Also, nearly every Spotify data object overrides `toString()` to assist with debugging, and `SpotifyObject` overrides 
`equals()` so that it can properly use the `contains()` method on Lists.

**Abstraction:** `SpotifyObject` is considered abstract, because there is no such thing as a generic object we can get 
back from Spotify. It is simply used to specify certain properties of all inheriting classes, and to override the 
`equals()` method.

## AI Usage
Little to no AI was used to develop this project. Occasionally, AI-generated Google search responses were used to 
answer implementation questions. 

## Time Spent: 
~30 hours spent.