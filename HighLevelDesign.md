# <a name="top"> iFood Backend Advanced Test - High Level Design

## Table of Contents

1. [Roadmap](#roadmap)
2. [Overall Problem](#overallProblem)
3. [Questions](#questions)
4. [OpenWeatherMap API Overview](#owmOverview)
5. [Spotify Web API Overview](#spotifyOverview)
6. [Assumptions](#assumptions)
7. [User Stories](#userStories)
8. [Solution Design](#solution)
9. [Running the Application](#run)

## <a name="roadmap"/> Roadmap 

* Understand the overall problem
* Study OpenWeatherMaps API
* Study Spotify API
* Specify a High Level Design of the solutions' architecture
* Create the User Stories
* Create Acceptance Criteria of the User Stories
* Implement the core service (Make it Work, Make it Right, Make it Fast)
* Implement the support for a replicated cache

[Go to top](#top)

## <a name="overallProblem"/> Overall Problem

* At least 2 different kinds of requests will come: city name (String) or lat long coordinates (2 Floats)
* As response, a playlist, that is, a set of track names (List of Strings) must be returned
* For each request, there'll be a call to OpenWeatherMaps API to fetch the current temperature
* For each request, there'll be a call to Spotify API to fetch a playlist according to the genre specified on the Business Rules
* One request to our micro-service is going to trigger two other requests to two third-party services. So, a timeout on the first request to a third-party service may end up on the timeout of the request for our service (the transaction itself).
* Regarding the response, the number of suggested tracks are not limited and they do not need to be regional. So, these are questions to be answered (see the next section). For now, since it's Sunday, I'm going to assume some parameters...

[Go to top](#top)

## <a name="questions"/> Questions

1. How many tracks must be returned within the playlist?
2. Must the playlist return just local songs? (e.g. If you are in Brazil, the service must return just Brazilian music)
3. What's the level of demanded fault tolerancy? (e.g. For a given request, its "state" should be replicated/available to all other nodes of the same service, so they can resume the processing it if the node that's currently processing the it goes down)
4. What are the parameters for responsiveness? (e.g. What's the predicted number of requests/sec for peak traffic? What's the acceptable response time on peak hours? What's the response timeout value?)
5. What are the resilience parameters? (e.g. if a node goes down, how much time should it take to a new node become fully operational and take over the work of the failed one?)

[Go to top](#top)

## <a name="owmOverview"/> OpenWeatherMap API Overview

* It requires signing up even for free access.
* At most one request each 10 minutes. So, I'm assuming the final solution would have a paid access to overcome such limitation. Anyway, for the sake of perfomance and since the weather is not likely to change so suddenly in a 10-minute period, it's reasonable to use a cache service to store weather data to avoid unnecessary API calls to this 3rd party service. 
* Available API supports queries by citiy names and lat long coordinates and can return the temperature in Celsius (query param: units=metric). It supports responses in both JSON and XML.

[Go to top](#top)


## <a name="spotifyOverview"/> Spotify Web API Overview

* There's a simple HTTP GET request that returns the tracks of a given playlist: https://developer.spotify.com/web-api/get-playlists-tracks/. Besides, the information returned can be filtered, so we can ask just for track names and limit the number of items by using the query params: fields=items(track(name))&limit=10 
* The responses are in JSON format.
* Authentication is done through registered applications credential (https://developer.spotify.com/web-api/authorization-guide/). For simplicity, we can use the Client Credentials Flow.

[Go to top](#top)

## <a name="assumptions"/> Assumptions

1. Values for the temperatures: 30, 15 and 10 Celsius degrees are not well-defined. So, I'm assuming 30 = party music, 15 = pop music, and 10 = classical music.  
2. Number of recommended tracks returned = 10 (at most)
3. Playlist Ids were pre-defined

[Go to top](#top)

## <a name="userStories"/> User Stories

* As a user, I want to be recommended party tracks if the input city's temperature is in the interval [30, inf)
* As a user, I want to be recommended pop music tracks if the input city's temperature is in the interval [15,30)
* As a user, I want to be recommended rock music tracks if the input city's temperature is in the interval [10,15)
* As a user, I want to be recommended classical music tracks if the input city's temperature is in the interval (-inf, 10)
* As a user, I want to be recommended party tracks if the input (lat long coordinates)'s temperature is in the interval [30, inf)
* As a user, I want to be recommended pop music tracks if the input (lat long coordinates)'s temperature is in the interval [15,30)
* As a user, I want to be recommended rock music tracks if the input (lat long coordinates)'s temperature is in the interval [10,15)
* As a user, I want to be recommended classical music tracks if the input (lat long coordinates)'s temperature is in the interval (-inf, 10)

[Go to top](#top)

## <a name="solution"/> Solution Design

In ordder to have a solution that matches the non-functional requirements, it was imperative that each request that comes to our service would be handled isolated from the others. It's achieved by making sure they don't store any state in the application.

In this case, many instances of this service can run simultaneously without interfering in each other's work. However, since there'sno communication among the instances, if an instance goes down while processing a transaction, such transaction cannot be resumed by another instance unless the client retries.

Two third party services were suggested: OpenWeatherMap and Spotify. So, the code was modularized such that other services can be supported with minimum implementation effort.

The business logic was isolated, that is, it does not know any kind of specifities of third party services.

### Programming Language and Technologies

* Java 8
* Spring
* Maven
* JUnit
* Mockito

### Main Classes

In terms of classes, the main ones are:

* PlaylistController: receives requests from clients and calls the suitable services, currently, just the PlaylistRecommendationService.
* PlaylistRecommendationService: implements the business logic which is agnostic of any third party service.
* WeatherService: interface for weather services.
* PlaylistService: interface for playlist services.
* OpenWeatherMapService: implements WeatherService interface and works as an adapter for OpenWeatherMap API.
* SpotifyService: implements PlaylistService interface and works as an adapter for Spotify Web API.

### API

The service is listening on port 8080 by default.

/playlist/?city=< city name >

/playlist/?lon=< longitude >&lat=< latitude >

### List of Possible Improvements

There are lots of improvements that can be done, some of them are marked as TODO in the code and some are listed here:
* Use property files instead of the ~/key/*.key files
* Use log4j instead of stdout
* Use worker threads (threadpool) to free up threads that handle incoming requests
* Use remote/replicated cache (e.g. Memcache, Ehcache) to improve resilience and fault tolerancy

[Go to top](#top)

## <a name="run"/> Running the Application

In order to run the application create the folder {HOME_DIR}/keys and place there the files: owmAppId.key spotify.key. Please, edit them accordingly, their content need to have the following:

### owmAppId.key

< OpenWeatherMap API APP_ID >

### spotify.key

client_id:client_secret

There are some sample keys on sample/ folder. However, note that such keys will not work against either OpenWeatherMap API or Spotify Web API.

[Go to top](#top)
