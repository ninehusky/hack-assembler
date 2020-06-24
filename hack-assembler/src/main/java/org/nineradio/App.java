package org.nineradio;

import java.util.*;
import java.io.*;

public class App {
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) { // TODO: change this once .jar executable is made
            System.out.println("Usage: java -jar App <filename>");
            System.exit(1);
        }
        InputStream inputFileStream = App.class.getClassLoader().getResourceAsStream(args[0]);
        Scanner input = new Scanner(inputFileStream);
        String rootOfFileName = args[0].substring(0, args[0].indexOf("."));

        File outputFile = new File(rootOfFileName + ".hack");
        PrintStream output = new PrintStream(outputFile);
        Parser p = new Parser(inputFileStream);

        while (p.hasMoreCommands()) {
            p.advance();
            if (p.commandType() == CommandType.A_COMMAND) {
                processACommand(output, p.symbol());
            } else if (p.commandType() == CommandType.C_COMMAND) {
                processCCommand(output, p);
            } else if (p.commandType() == CommandType.L_COMMAND) {
                processLCommand(output, p.symbol());
            }
        }
        output.close();
    }

    public static void processACommand(PrintStream output, String symbol) {
        output.println("0" + processASymbol(symbol));
    }

    public static void processCCommand(PrintStream output, Parser p) {
        output.println("111" + p.comp() + p.dest() + p.jump());
    }

    public static void processLCommand(PrintStream output, String symbol) {
        // idk
    }

    /**
     * Takes the given symbol and returns the binary equivalent.
     * @param symbol Symbol to transcribe
     */
    public static String processASymbol(String symbol) {
        if (isNumeric(symbol)) {
            String root = Integer.toBinaryString(Integer.parseInt(symbol));
            // now add leading zeroes
            return String.format("%15s", root).replace(' ', '0');

        }
        return "A-Commands with symbols have not been invented yet";
    }

    /**
     * I cannot believe this is not a built-in method in Java.
     * @param str String to assess
     * @return whether or not String is numeric
     */
    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}