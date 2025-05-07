package org.eternity.adventure;

import org.eternity.adventure.console.Console;
import org.eternity.adventure.game.Game;
import org.eternity.adventure.game.InputOutput;

public class Main {
    public static void main(String[] args) {
        InputOutput io = new Console();
        Game game = new Game(io);
        game.run();
    }
}