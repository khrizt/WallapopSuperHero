# Wallapop super hero Developer Test

This project has been done as a technical test in the Wallapop recruit process.

## Architecture

The app has been developed using the clean architecture but without using ReactiveX because its simplicity. Also all the calls to the Marvel API are done in a different thread to avoid blocing the UI thread.

As there's only one UI flow the project only contains one activity that loads the needed fragment each time.

## App

The app contains the following features:

* Loads the comics in the main page and can be scrolled until the last comic is retrieved.
* The action bar button changes the view mode from a list to a 2-column grid.
* The ImageView's are resized before loading an image to avoid rendering jumps and improve usability.
* Tapping on any comic allows to see more information about a comic, and one or two random comic images.
* Added the back button in the comic detail screen.

## Unit test

The project contains unit tests for 2 classes, one for a data entity and the other for a presenter, both of them used in the app.

## Functional testing

The project contains 2 functional tests, one goes to the first comic returned by the API and checks that the title contains anything, the second test goes to the second position of the RecyclerView and clicks on the comic to see its information.
