package yaj.editor

import yaj.editor.gui.GUI

fun main(args: Array<String>): Unit {
    Launcher();
}

/**
 * Starting point for the editor.
 */
class Launcher {
    init {
        GUI(InterpreterCreator());
    }
}