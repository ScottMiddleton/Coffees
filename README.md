## Objective
A simple development task designed to set a level playing field for candidates to demonstrate their
approach, choices, skills, and reasoning when conducting development. There is no min or max time
though we tend to find most candidates spend approx. 4-8 hrs on such tasks but it’s entirely up to you.

## Functionality
The app allows users to view a list of coffees, even when there is no network connection. The user can also swipe to refresh the list, and any errors from the API are handled with a snackbar.

Users can also drill down to see the details of a specific coffee, including the image, description, and ingredients. From the toolbar, users can also "like" a coffee, which will order it at the top of the list.

Additionally, users can create a review for a coffee, which includes their name, a multi-line review description, a rating (selected from a list of 10 options), and the ability to submit the review through an API endpoint. However, it's important to note that this API endpoint is not supported and will fail, and the app handles the error accordi

## Implementation
The application follows an MVVM architecture pattern and uses Hilt for dependency injection. Navigation and layouts are handled with Jetpack Compose, which is a modern toolkit for building beautiful and performant Android apps.

A Room database is set up for local storage of coffee data and Coffee User Interaction data, which is used to store the liked status and review of a coffee. The Room database is used to provide a local cache of the coffee data, which is fetched from a third-party API using Retrofit.

With the Room database as the single source of truth, Flows are used where appropriate to observe changes in the view models. This pattern cleanly keeps the UI updated with the latest coffees fetched from the API or an update in the liked status of a coffee.

ViewModels hold the state of the data and handle the logic of the user interface, while the view is responsible for displaying the data and handling user interactions. Communication between the view and the ViewModels is done through events and actions. The ViewModels also have the ability to emit events that the view can listen for and respond to, allowing for a decoupled relationship between the two. This approach allows for a clear separation of concerns, making the code more maintainable and easier to test. Additionally, this approach works well when using Hilt dependency injection as it allows the dependencies to be injected in the ViewModels. This way, the view is not responsible for managing the dependencies, which makes the code more modular and easier to test, as the view and ViewModel can be tested separately.

Please note that, in the interest of completing the test within a reasonable timeframe, I did not include a review field to accept date input. However, hopefully this is the only requirement that was not met.

## Testing
I provided an example of testing approaches, rather than comprehensive coverage of the enitire app. Specifically, coverage for the Repository, UseCase and ViewModel layers of the Coffee Options feature. 

For the Repository layer, I used a "fake" repository class called FakeCoffeeOptionsRepository, however I also used Mockks in other instances. I'm aware that there's a debate over the use of mocks vs fakes in software testing, and my inconsitency of approach here is an attempt to represent my skillset and understanding of both.

I set up the mock web server to simulate API calls made by the repository, and assert that the repository correctly handles the responses from the API. I also used the library turbine to test the Flow stream used in the ViewModel layer.
