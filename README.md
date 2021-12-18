# Java Chess

*David Xu

An implementation of the classic board game Chess in Java, written when I was a young laddie.

## Class Overview

`Game:` Runs the game of chess. This is the Runnable class and thus houses the UI buttons as well 
as the game itself.

`Board`: Handles user input as well as board and game state.

`Status:` Displays game status for a board

`BoardState:` Stores piece locations and other important game information about the board

`Position:` Implements `Pair` to represent locations

`Piece:` Abstract class representing pieces

`PieceType:` Enum for the piece types

`Move:` Defines a move in the game

All of the pieces also have their respective classes extending `Piece`.

Chess Piece Icon resources were acquired at:
https://commons.wikimedia.org/wiki/File:Chess_Pieces_Sprite.svg
