# iFood Backend Advanced Test - High Level Design

## Roadmap

* Understand the overall problem
* Study OpenWeatherMaps API
* Study Spotify API
* Specify a High Level Design of the solutions' architecture
* Create the User Stories
* Create Acceptance Criteria of the User Stories
* Implement the core service (Make it Work, Make it Right, Make it Fast)
* Implement the support for a replicated cache

# Overall Problem

* At least 2 different kinds of requests will come: city name (String) or lat long coordinates (2 Floats)
* As response, a playlist, that is, a set of track names (List of Strings) must be returned
* For each request, there'll be a call to OpenWeatherMaps API to fetch the current temperature
* For each request, there'll be a call to Spotify API to fetch a playlist according to the genre specified on the Business Rules
* One request to our micro-service is going to trigger two other requests to two third-party services. So, a timeout on the first request to a third-party service may end up on the timeout of the request for our service (the transaction itself).
* Regarding the response, the number of suggested tracks are not limited and they do not need to be regional. So, these are questions to be answered (see the next section). For now, since it's Sunday, I'm going to assume some parameters...

# Questions

1. How many tracks must be returned within the playlist?
2. Must the playlist return just local songs? (e.g. If you are in Brazil, the service must return just Brazilian music)
3. What's the level of demanded fault tolerancy? (e.g. For a given request, its "state" should be replicated/available to all other nodes of the same service, so they can resume the processing it if the node that's currently processing the it goes down)
4. What are the parameters for responsiveness? (e.g. What's the predicted number of requests/sec for peak traffic? What's the acceptable response time on peak hours? What's the response timeout value?)
5. What are the resilience parameters? (e.g. if a node goes down, how much time should it take to a new node become fully operational and take over the work of the failed one?)

# OpenWeatherMap API Overview

* It requires signing up even for free access.
* At most one request each 10 minutes. So, I'm assuming the final solution would have a paid access to overcome such limitation. Anyway, for the sake of perfomance and since the weather is not likely to change so suddenly in a 10-minute period, it's reasonable to use a cache service to store weather data to avoid unnecessary API calls to this 3rd party service. 
* Available API supports queries by citiy names and lat long coordinates and can return the temperature in Celsius (query param: units=metric). It supports responses in both JSON and XML.


# Spotify Web API Overview

* There's a simple HTTP GET request that returns the tracks of a given playlist: https://developer.spotify.com/web-api/get-playlists-tracks/. Besides, the information returned can be filtered, so we can ask just for track names and limit the number of items by using the query params: fields=items(track(name))&limit=10 
* The responses are in JSON format.
* Authentication is done through registered applications credential (https://developer.spotify.com/web-api/authorization-guide/). For simplicity, we can use the Client Credentials Flow.



