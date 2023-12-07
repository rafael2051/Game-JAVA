## GAME Project in JAVA

Hi there! This is a game made by me in Java.
This is a 2D top-down game, where the player must survive against zombies.

![Game](src/game/images/demo/showing_game.jpg)

## Folder Structure

The workspace contains three folders, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

To compile and run the game, you must have jdk and jre installed in your machine. It would be interesting if you have Visual Studio Code or IntelliJ also.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## How to run

Run the following commands from the root directory (Zombies/):

### Game

```
cd gameSrc
javac -d ../bin/ App.java
java -cp ../bin/ App
```

### Server

If runned the past commands, start a new terminal and run it, from the root directory (Zombies/):

```
cd mainServer
javac -d ./bin ServerApp.java
java -cp ./bin/ ServerApp
```

## Messages Shared
