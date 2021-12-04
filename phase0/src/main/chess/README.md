<h>Chess Package For the 2 Player Chess game</h>

How to use:
- Run Main.main() for a test run of a scenario with en Passant. Also includes a Scholars Mate scenario.
- ChessGame.selectCoord() ChessGame method to select and move Pieces. Automatically discerns legal and nonlegal moves.
  Only makes legal moves. See JavaDoc comment of the method to see details on how to use it.

Notes:
 - Pieces can only move into Legal moves.
 - No extensive bug testing was done
 - A checking mechanism has been implemented, but not yet integrated into the legal moves of Pieces. (Pins and Blocks)

To Do:
 - Checking/CheckMate Mechanism
 - Drawing Mechanism (Stalemate, 50 move rule, 3 fold repetition)
 - Recording Game Mechanism
 - Castling
