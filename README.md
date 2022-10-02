# Tic-Tac-Toe

A 2-player game made using Socket Programming in Java.

## Instructions to play the game

Ensure that you have Java installed on your device.

#### Instructions for Linux

1. Clone the repository in your device.

	`git clone https://github.com/khushangsingla/Tic-Tac-Toe.git`
2. Enter the folder [`source`](./source) in the repository.

	`cd Tic-Tac-Toe/source/`
3. Now compile the code in the game package using the following command.

	`javac game/*.java`
4. Now to play the game, open two terminals in this folder.
5. In player 1's terminal, run the following command:

	`java game.Client 1 5000 8000`
6. In player 2's terminal, run the following command:

	`java game.Client 2 8000 5000`

***Note:*** Ensure that you are inside the source directory in the repository.

***Note:*** Make sure ports 8000 and 5000 are not being used. If they are in use, either free the ports using the following commands or use some other ports to play the game.

`fuser -k <PORT NUMBER>/tcp`

## Further Developments

The game can be extended to the possibility of playing it with others remotely over internet.
It would be of great help if someone can provide some resource to guide towards how to do that.
