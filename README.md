# Weather Application
A simple weather app to show you your current location weather.
Weather data include:
- Current tempreature.
- Current temptreaue feels like. 
- Weather conditoon.
- Wind speed, humidity percentage and pressure.
It also show you the daily weather for the next 7 days.

This project contains of:

- Kotlin Coroutines for background operations.
- MVVM acrchitcture.
- ROOM for local data base.
- View model and live data.
- A data layer with a reoisutory and two data sources (local using Room and remote).
- A collection of unit rest.
- Bottom navigation to manage multiple fragments.

Third party libraries used in this project:
- [Glide](https://github.com/bumptech/glide): To load and show image.
- [Retrofit](https://square.github.io/retrofit): To act as interface for the HTTP APIs.

The project consits of:
- data: To act as the data latyer.
- persistance: Persistance manager.
- ui: Conatins the app pages.
  - Home: Show your location weather.
  - Favorite cities: Show your favorite cities.
  - Settings: For settings (Change tempreature unit)
- utils: For utilties.

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

