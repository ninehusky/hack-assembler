/**
 * Keeps a correspondence between symbolic labels and numeric labels.
 */

package org.nineradio;

import java.util.*;
import java.io.*;

public class SymbolTable {

    private final String PREDEF_SYMBOL_PATH = "predef-symbols.csv";
    private int varAddress;
    private Map<String, Integer> table;

    /**
     * Creates a new empty symbol table.
     */
    public SymbolTable() {
        varAddress = 16;
        table = new HashMap<>();
        populateTable();
    }

    /**
     * Adds the symbol to the table.
     * @param symbol the symbol to add
     */
    public void addEntry(String symbol) {
        if (contains(symbol)) {
            throw new IllegalArgumentException("Key already exists for " + symbol + "!");
        }
        table.put(symbol, varAddress);
        varAddress++;
    }

    /**
     * Adds the symbol with the given address to the table.
     * @param symbol symbol to add
     * @param address address associated with symbol
     */
    public void addEntry(String symbol, int address) {
        if (contains(symbol)) {
            throw new IllegalArgumentException("Key already exists for " + symbol + "!");
        }
        table.put(symbol, address);
    }

    /**
     * Returns whether or not the symbol table contains the given symbol
     * @param symbol the symbol to check
     * @return boolean representing whether or not the table contains the symbol
     */
    public boolean contains(String symbol) {
        return table.containsKey(symbol);
    }

    /**
     * Returns the address associated with the given symbol.
     * @param symbol symbol to check
     * @return address associated with symbol
     */
    public int getAddress(String symbol) {
        if (!contains(symbol)) {
            throw new IllegalArgumentException("Cannot find address for " + symbol + "!");
        }
        return table.get(symbol);
    }

    /**
     * Populates the table with the symbol/address pairs found in the file located at
     * PREDEF_SYMBOL_PATH.
     */
    private void populateTable() {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(PREDEF_SYMBOL_PATH);
            Scanner input = new Scanner(is);
            while (input.hasNextLine()) {
                String[] keyValuePair = input.nextLine().split(",");
                table.put(keyValuePair[0], Integer.parseInt(keyValuePair[1]));
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}