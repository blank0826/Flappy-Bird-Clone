# <ins>**Flappy-Bird-Clone**</ins>
# Description
This is a game known as Flappy Bird where the bird jumps on tap and you have to protect the bird from colliding with the pipes. A high score is maintained so that you can see how high you have ever got.

This has been created using **XML**, **Java**, **LibGDX**.

## A. How to Play
When the game starts you have to start tapping on the screen to keep the bird from falling. You also have to ensure that the bird does not go beyond the reach of the screen.
You have to pass the bird from the space given between the pipes. Passing through each pipe gives a score of 1.

When you collide with the pipes or move out of the screen the game is over.

## B. Objective
To look at the backend of the game, refer to [Flappy Bird Clone](https://github.com/blank0826/Flappy-Bird-Clone/blob/master/core/src/com/mygdx/game/FlappyBirdClone2.java).

We will describe a little about each function used in this file.<br/><br/>

**<ins>1. public void create ()</ins>**<br/>
This function creates the UI for the game holding high scores, setting music, backgrounds, birds display.


**<ins>2. public void startGame()</ins>**<br/>
This function sets the default height of the bird and also creates pipes to be shown throughout the game.

**<ins>3. public void render ()</ins>**<br/>
This function is called continuously as long as the game is being played.<br/><br/>


This keeps in track the state of the game, increase and decrease the bird height on tap, whether it has collided with the screen or pipes, creates an infinite loop of those pipes created, and also provides the feature of increasing high score simultaneously.


# Local Setup
## <ins>Pre-requisites</ins>
Android Studio with support of Java.<br/>
You can download Android Studio [here](https://developer.android.com/studio).<br/>
To use the features of game in backend, Go to [LibGDX](https://libgdx.com/dev/tools/) then download Setup Tool.

## <ins>Execution</ins>
1. Pull the code and open it on Android Studio.<br />

2. After it finishes building, Go to Project->Android->java->AndroidLauncher<br />

3. Either connect your phone or make an emulator. [Emulator Build](https://developer.android.com/studio/run/managing-avds).

4. Click on Run, after building and installing the project will open.

# Screenshots of the Gameplay
## Home Page
<img src="https://user-images.githubusercontent.com/33955028/141238133-36a5eaa5-007c-4641-9424-4a5554453b60.png" width="205" height="420">

## How to Play(?)
<img src="https://user-images.githubusercontent.com/33955028/141238368-c8e69cd3-0270-471d-a01d-f95c31a55487.png" width="205" height="420">

## Middle of Match
<img src="https://user-images.githubusercontent.com/33955028/141238681-c1ec0102-d921-43cf-ac7b-fe06937a1257.png" width="205" height="420">

## Game Over
<img src="https://user-images.githubusercontent.com/33955028/141238466-90f7689a-896f-4e7c-b978-0ac9ae887bb3.png" width="205" height="420">

# Contact
## [Aditya Srivastava](mailto:aditya26052002@gmail.com?subject=GitHub)
