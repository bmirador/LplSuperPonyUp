# LPL Super Pony Up (First thing that came to my mind)

This application displays a list of comments from a JSON API. Below is a technical overview of the
implementation.

I think for now this is where I will leave the app at. I would love to hear your feedback even if
we do not get to meet at the interview! Feel free to reach out at reprisma.dev@gmail.com

Thanks!

## Technical Implementation

### Architecture

- **Pattern**: MVVM (Model-View-ViewModel) architecture
- **UI Framework**: Built with Jetpack Compose for a modern, declarative UI
- **State Management**: Kotlin Flow for reactive state updates
- **Dependency Injection**: Hilt for clean and testable dependency injection
- **Navigation**: Jetpack Navigation Compose for screen navigation
- **Build Variants**: Supports 'mock' and 'prod' flavors for development and production environments

### Data Layer

- **API Client**: Retrofit for network requests
- **Endpoint**: https://jsonplaceholder.typicode.com/posts/1/comments
- **Local Storage**: Room DB for offline caching of comments
- **Repository Pattern**: Single source of truth for data operations

### Features

- Display list of comments with profile images
- Replace profile placeholder images with photos from gallery
- Support for both landscape and portrait orientations
- Error handling with retry functionality
- Network connectivity awareness with offline support
- Responsive design that prevents text truncation

### Testing

- **UI Tests**: Compose UI testing with ComposeTestRule
    - Tests for loading, success, error, and initial states
    - Component rendering verification
- **Unit Tests**: Repository implementation tests with mock data
- **Mock Data**: JSON mock data for offline testing

## Project Structure

- **data**: Contains models, repository implementation, local and remote data sources
- **di**: Dependency injection modules
- **ui**: Compose UI screens, components, and themes
- **util**: Utility classes and extensions

## Build and Run

1. Clone the repository
2. Open the project in Android Studio
3. Select either the 'mock' or 'prod' build variant
4. Run the app on an emulator or physical device

## Future Enhancements

- More specific error messages based on HTTP status codes
- Enhanced accessibility features
- Extended test coverage
- Support for larger screen sizes (tablets)
- The Hyper-Galactic, Multi-Domain, Quantum-Resilient “Load-Next-Page”™ Protocol

## Demo Images

Original request:

Implement Java and MVVM architecture-based project to achieve below features.
Use all jetpack components that you can use here.

1. Build a screen as shown below with json data mapping
   ![img.png](Images/img.png)
2. Consume below web service to build the screen (Use retrofit library)
   https://jsonplaceholder.typicode.com/posts/1/comments

3. Currently in the service we don’t have an attribute to show the profile image, default show the
   place holder icon as shown
4. Provide the action on profile place holder icon to replace with new icon from photo gallery
5. App should be universal app, will work on landscape and portrait mode
6. Make sure any text shown on screen should not truncate
7. Any suggestion on current UI design are most welcome
8. When completed, please upload to GIT HUB and be prepared to not only walk through the code but
   demo the project.

Not so professional photos of my pets:

Here is a photo of my dog:  
<img src="Images/photo_2025-06-04_22-45-47.jpg" width="300"/>

And of my cats:  
<img src="Images/photo_2025-06-13_02-50-29.jpg" width="250"/>
<img src="Images/photo_2025-06-13_02-50-58.jpg" width="250"/>
