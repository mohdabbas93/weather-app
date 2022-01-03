# Weather Application
A simple weather app to show you your current location weather. Weather data include:
- Current temperature.
- The current temperature feels like.
- Weather condition.
- Wind speed, humidity percentage, and pressure. It also shows you the daily weather for the next 7 days.

This project contains:
- Kotlin Coroutines for background operations.
- MVVM architecture.
- ROOM for the local database.
- View model and live data.
- A data layer with a repository and two data sources (local using Room and remote).
- A collection of unit rest.
- Bottom navigation to manage multiple fragments.

Third-party libraries used in this project:
- [Glide](https://github.com/bumptech/glide): To load and show images.
- [Retrofit](https://square.github.io/retrofit): To act as an interface for the HTTP APIs.

The project consists of:
- data: To act as the data layer.
- persistence: Persistence manager.
- ui: Contains the app pages.
  - Home: Show your location weather.
  - Favorite cities: Show your favorite cities.
  - Settings: For settings (Change temperature unit)
- utils: For utilities.

# Futrue work
- Cash location and weather data in database.
- Add search page to search for other cities weather.
- Delete favorite city functionality.
- Loading, error, sucess statuss handling.
- Details page that open when click on a favorite city to show its weather with an option of adding it to the favorite cities.
- Use data bindg, navigation component, and dependency injection.
- Export and Import Favourite cities as CSV File.
- Use multi module architecture.
- Create a widget.
- Add different kind of notifications.

