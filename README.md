# Weather Application
A simple weather app to show you your current location weather. Weather data include:
- Current temperature.
- The current temperature feels like.
- Weather condition.
- Wind speed, humidity percentage, and pressure. It also shows you the daily weather for the next 7 days.
- Search for other cities' weathers.
- Can add a new city to favorite cities.
- Can view a list of favorite cities.
- Can toggle temperature unit between celsius and fehrenhite.
- Get a notification at 6 am every day with today's temperature.

This project contains:
- Kotlin Coroutines for background operations.
- MVVM architecture.
- ROOM for the local database.
- View model and live data.
- A data layer with a repository and two data sources (local using Room and remote).
- A collection of unit rest.
- Bottom navigation to manage multiple fragments.
- Location weather data and the favorite cites weather data are cashed in a database.

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

# How to use the app
- On the home page you can view your current location weather data and with the search icon on the top, you can open the search page.
- On the search page, you can search for your favorite city, and when you click on it a new page will open with that city's weather data.
- From the details page you can add the city to the favorite by clicking on the outlined heart at the top of the screen.
- You can view a list of your favorite cities by returning to home and clicking on the favorite tab.
- When click on a city from the favorite list a detail page is opened and as you added the city to your favorite list you can remove it by clicking on the same heart but this time it will be filled heart indicating that this city is favorite.
- From the settings tab you can select the temperature unit which all temperatures in the app will be shown using the selected temperature unit.

# Known issues
- When the app tries to get the location after enabling the location, it may take forever to get the location, so it will help if you reopened the app multiple times.
- If you have the app already installed, please clear the data first or uninstall it before installing the app again.
- For some reason, the favorite cities list is not updating after adding or deleting a new city unless you reopen the app.

# Future work
- Use data binding, navigation component, and dependency injection.
- Export and Import Favourite cities as CSV File.
- Use multi-module architecture.
- Create a widget.
- Add a different kinds of notifications.
