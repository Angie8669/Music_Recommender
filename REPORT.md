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
**Strategy Pattern:** Not all songs have genres associated with them, so needed multiple strategies for getting 
recommendations.

**Singleton Pattern:** Generating multiple APIServices is unnecessary, and could cost storage space. Also, generating 
multiple copies of the same SpotifyObject could lead to excessive storage issues, or requiring extra API calls for the 
same data.

## AI Usage
Little to no AI was used to develop this project. Occasionally, AI-generated Google search responses were used to 
answer implementation questions. 

## Time Spent: 
~30 hours spent.