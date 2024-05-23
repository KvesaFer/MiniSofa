<p align="center">
  <img src="img.png">
</p>

# Sofascore Android Academy Live Coding Repository  

Welcome to the Sofascore Android Academy live coding repository!

This repository serves as a central hub for our hands-on coding sessions, where we'll delve into the fundamentals of Android development and explore advanced concepts through practical examples.

## Topics Covered

Throughout this repository, we'll explore a comprehensive range of Android development topics, including:

1. [**Android Components**](AndroidComponents/README.MD) 📱: Activities, Broadcast Receivers, Services, and Content Providers

   Understand the four fundamental building blocks of any Android app:
    - **Activities**: Manage UI and interact with users, representing individual screens within the app.
    - **Broadcast Receivers**: Respond to system-wide events or messages from other apps.
    - **Services**: Run long-running background tasks independent of the UI.
    - **Content Providers**: Share data between different apps securely and in a controlled manner.

2. [**Android View System**](ViewSystem/README.MD) 🖥️

   Master the fundamentals of Android's view system, including activities, fragments, and the lifecycle of UI elements.

   2.1. [**Layouts** ](ViewSystem/README.MD#21-layouts-)📐

   Design user interfaces effectively using various layout types like coordinator layouts, linear layouts, constraint layouts, and relative layouts.

   2.2. [**Recycler View**](ViewSystem/README.MD#22-recycler-view-) 🔄

   Manage lists of data efficiently using the RecyclerView component, including data retrieval and adapter implementation.

   2.3. [**AppBarLayout**](ViewSystem/README.MD#23-appbarlayout-) ⬆️⬇️

   Understand the role of AppBarLayout in creating cohesive and responsive layouts, including implementing collapsing toolbars and custom navigation menus.

   2.4. [**BottomSheet**](ViewSystem/README.MD#24-bottomsheet-) ⤵️

   Explore the use of BottomSheet for displaying modal content in a flexible manner, allowing users to interact with underlying content seamlessly.

   2.5. [**Dialogs**](ViewSystem/README.MD#25-dialogs-) 💬

   Enhance your UI with various types of dialogs, including alert dialogs, confirmation dialogs, and progress dialogs.

   2.6. [**Snackbars and Toasts**](ViewSystem/README.MD#26-snackbars-and-toasts-) 🍞

   Master the art of providing feedback to users using Snackbars and Toasts, ensuring a smooth and intuitive user experience.

   2.7. [**Compound and Custom View**](ViewSystem/README.MD#27-compound-and-custom-view-) 🔧

   Learn about Compound and Custom Views, which allow you to create reusable UI components with complex behavior and styling, encapsulating multiple views into a single component.

3. [**Background Tasks**](BackgroundTasks/README.MD)⚙️

   Learn how to perform background operations efficiently using WorkManager, services, and coroutines. Gain insights into asynchronous programming concepts like threads, thread pools, and executors.

4. [**ViewModel**](ViewModel/README.MD) ✨

   Delve into the concept of ViewModel, its role in maintaining UI-related data, and its comparison to other architectural patterns like MVC and MVP.

5. [**Local Database**](Database/README.MD) 🗄️

   Explore the ROOM library for creating and managing local databases, including efficient data storage and retrieval.

6. [**Shared Preferences (DataStore Implementation)**](LocalDataStorage/README.MD) 🛂 ️

   Learn to persist key-value pairs using Shared Preferences and explore DataStore as an alternative approach for secure, structured data storage.

7. **Networking with Retrofit, Gson, and OkHTTP** 🌐

   Master the art of making network requests using Retrofit, Gson, and OkHTTP to fetch data from remote APIs.

8. [**LiveData**](LiveData/README.MD) ✨

   Grasp the concept of LiveData, a lifecycle-aware data holder class that simplifies data observation and change notification.

9. [**Flow**](Flow/README.MD) 🌊

   Explore the power of Flow, a reactive stream API that offers a declarative and asynchronous way to handle data flows in Android.

10. **JetPack Compose and Declarative UI** 🎨

   Jetpack Compose is a modern toolkit for building native Android UIs using a declarative programming model. In this lecture

## Understanding Git Branches and Live Coding Repository Concept

This repository utilizes Git branches to separate incomplete and completed code blocks for each lesson. Each lesson will have two branches:

- **LessonX incomplete**: This branch will hold the initial working code for that lesson, representing the code we'll start with during the live coding session. ✏️
- **LessonX complete**: Once the live coding session is finished, we'll push the completed code to this branch, reflecting the final state of the code after the session.

This approach allows you to follow the evolution of the code as we progress through each lesson and revisit the incomplete code to understand the development process.

## Get Involved!

We encourage you to follow along with the live coding sessions, experiment with the code, and ask questions as we progress through each topic. This repository serves as a valuable resource for learning Android development in a practical and interactive setting.

### Additional Resources:

- [Android Developer](https://developers.google.com/)
- [Android Studio](https://developer.android.com/studio)
- [Git](https://git-scm.com/)
