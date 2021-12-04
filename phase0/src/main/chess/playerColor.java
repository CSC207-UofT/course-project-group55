package chess;

/**
 *  This package was coded such that the number of players
 */

enum playerColor {
    White("White"),
    Black("Black");

    public String string;

    playerColor(String string){
        this.string = string;
    }

}