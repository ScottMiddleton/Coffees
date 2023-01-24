## Objective
A simple development task designed to set a level playing field for candidates to demonstrate their
approach, choices, skills, and reasoning when conducting development. There is no min or max time
though we tend to find most candidates spend approx. 4-8 hrs on such tasks but it’s entirely up to you.

## Implementation
The application follows an MVVM architecture pattern and uses Hilt for dependency injection. A Room database has been setup for local storage of Coffee data and CoffeeUserInteraction data, which is used to store the liked status and review of a coffee. The Room database is used to provide a local cache of the coffee data, which is fetched from a 3rd party API using Retrofit. The API call is wrapped in a use case, which is called in the CoffeeViewModel. The CoffeeViewModel observes the local cache using the Flow collection, and updates the UI accordingly. The ViewModel also handles the submission of a coffee review to the API, and updates the local cache with the latest data. The coffee review submission screen is a composable that provides inputs for the review fields including name, date, review detail, rating, and other appropriate fields. The rating is provided as a dropdown with options of 1 to 10. The screen also provides a way to handle any errors that may occur during the review submission process.
