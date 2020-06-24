/**
 * Encapsulates access to the input code. Reads an assembly language
 * command, parses it, and provides convenient access to the command's components
 * (fields and symbols). In addition, removes all white space and comments.
 */

package org.nineradio;

import java.util.*;
import java.io.*;

public class Parser {

    private Code code;
    private Scanner input;
    private String currCommand;
    private CommandType currCommandType;

    /**
     * Opens the input stream and gets ready to parse it.
     * @param is - InputStream of machine language.
     */
    public Parser(InputStream is) {
        input = new Scanner(is);
        code = new Code();
    }

    /**
     * Returns if there are more commands in the input
     * @return whether or not there are more commands in input
     */
    public boolean hasMoreCommands() {
        return input.hasNextLine();
    }

    /**
     * Reads the next command from the input and makes it the next command. Should
     * be called only if there is another command in the input.
     * @throws IllegalStateException if there are no more commands in the input
     */
    public void advance() {
        if (!hasMoreCommands()) {
            throw new IllegalStateException("No more commands in the input!");
        }
        currCommand = input.nextLine().trim().replaceAll("\\s+", "");
        // trim comments
        int indexOfComment = currCommand.indexOf("//");
        if (indexOfComment != -1) {
            currCommand = currCommand.substring(0, indexOfComment);
        }
        if (currCommand.contains("@")) {
            currCommandType = CommandType.A_COMMAND;
        } else if (currCommand.contains("(")) {
            currCommandType = CommandType.L_COMMAND;
        } else if (currCommand.contains(";") || currCommand.contains("=")) {
            currCommandType = CommandType.C_COMMAND;
        } else {
            currCommandType = CommandType.NO_COMMAND;
        }
    }

    /**
     * Returns the type of current command:
     * A_COMMAND for @Xxx where Xxx is either a symbol or decimal number
     * C_COMMAND for dest=comp;jump
     * L_COMMAND for (Xxx) where Xxx is a symbol
     * NO_COMMAND for whitespace or comments
     * @return current command type
     */
    public CommandType commandType() {
        return currCommandType;
    }

    /**
     * Returns the symbol or decimal Xxx of the current
     * command @Xxx or (Xxx).
     * @throws IllegalStateException if current command is not A_COMMAND or L_COMMAND
     * @return symbol representing @Xxx or (Xxx).
     */
    public String symbol() {
        if (currCommandType != CommandType.A_COMMAND && currCommandType != CommandType.L_COMMAND) {
            throw new IllegalStateException("Cannot return symbol for non A/L command!");
        }
        if (currCommandType == CommandType.A_COMMAND) {
            return currCommand.substring(1);
        } else { // currCommandType == CommandType.L_COMMAND
            // TODO: implement symbol design
            return currCommand.substring(1, currCommand.length() - 1);
        }

    }

    /**
     * Returns the dest mnemonic in the current C-command (8 possibilities).
     * @throws IllegalStateException if current command is not C_COMMAND
     * @return binary representation of command's dest mnemonic
     */
    public String dest() {
        if (currCommandType != CommandType.C_COMMAND) {
            throw new IllegalStateException("Cannot find dest for non-C command!");
        }
        if (currCommand.contains("=")) {
            // take portion of command to the left of the equals
            return code.dest(currCommand.substring(0, currCommand.indexOf("=")));
        }
        return code.dest("");
    }

    /**
     * Returns the comp mnemonic in the current C-command (8 possibilities).
     * @throws IllegalStateException if current command is not C_COMMAND
     * @return binary representation of command's comp mnemonic
     */
    public String comp() {
        if (currCommandType != CommandType.C_COMMAND) {
            throw new IllegalStateException("Cannot find dest for non-C command!");
        }
        String compString = currCommand;
        if (compString.contains("=")) {
            compString = compString.substring(compString.indexOf("=") + 1);
        }
        if (compString.contains(";")) {
            compString = compString.substring(0, compString.indexOf(";"));
        }
        return code.comp(compString);
    }

    /**
     * Returns the jump mnemonic in the current C-command (8 possibilities).
     * @throws IllegalStateException if current command is not C_COMMAND
     * @return binary representation of command's jump mnemonic
     */
    public String jump() {
        if (currCommandType != CommandType.C_COMMAND) {
            throw new IllegalStateException("Cannot find dest for non-C command!");
        }
        if (currCommand.contains(";")) {
            return code.jump(currCommand.substring(0, currCommand.indexOf(";")));
        }
        return code.jump("");
    }
}