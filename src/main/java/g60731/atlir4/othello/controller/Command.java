package g60731.atlir4.othello.controller;

/**
 * Represents a command interface with methods for execution, undoing, and redoing.
 */
public interface Command {
    void execute(); //Executes the command.
    void undo();    //Undoes the command.
    void redo();    //Redoes the command.
}
