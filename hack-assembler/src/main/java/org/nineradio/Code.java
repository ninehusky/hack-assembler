/**
 * Translates Hack assembly language mnemonics into binary codes.
 */

package org.nineradio;

import java.util.*;
import java.io.*;

public class Code {
    private static final String SYMBOL_BASE_URL = "-symbols.csv";
    private Set<String> symbolsWhereAIs1;
    private Map<String, String> destMap;
    private Map<String, String> compMap;
    private Map<String, String> jumpMap;

    /**
     * Constructs a new Code.
     */
    public Code() {
        symbolsWhereAIs1 = new HashSet<>(Arrays.asList("M", "!M", "-M", "M+1", "M-1", "D+M", "D-M", "M-D", "D&M", "D|M"));

        destMap = new HashMap<>();
        compMap = new HashMap<>();
        jumpMap = new HashMap<>();
        populateMaps("dest", destMap);
        populateMaps("comp", compMap);
        populateMaps("jump", jumpMap);
    }

    /**
     * Returns the binary code of the dest mnemonic.
     * @param mnemonic - the dest mnemonic
     * @return binary equivalent of the mnemonic
     */
    public String dest(String mnemonic) {
        return getSymbol(mnemonic, "000", destMap);
    }

    /**
     * Returns the binary code of the comp mnemonic.
     * @param mnemonic - the comp mnemonic
     * @return binary equivalent of the mnemonic
     */
    public String comp(String mnemonic) {
        return getABit(mnemonic) + getSymbol(mnemonic, "invalid", compMap);
    }

    /**
     * Returns the binary code of the jump mnemonic.
     * @param mnemonic - the jump mnemonic
     * @return binary equivalent of the mnemonic
     */
    public String jump(String mnemonic) {
        return getSymbol(mnemonic, "000", jumpMap);
    }

    /**
     * Returns the binary equivalent of the given mnemonic, or minimum if the
     * mnemonic is empty.
     * @param mnemonic - the Hack machine language to translate
     * @param minimum - the String to return if mnemonic is empty
     * @param map - the mnemonic/binary table for the command
     * @return binary equivalent of mnemonic
     */
    private String getSymbol(String mnemonic, String minimum, Map<String, String> map) {
        if (mnemonic.isEmpty()) {
            return minimum;
        }
        String equivalent = map.get(mnemonic);
        if (equivalent == null) {
            throw new IllegalStateException("Could not locate binary equivalent of " + mnemonic);
        }
        return equivalent;
    }

    /**
     * Populates the comp, dest, and jump maps to be used in the translation.
     * @param root - type of command to load map for
     * @param map - map to populate
     */
    private void populateMaps(String root, Map<String, String> map) {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(root + SYMBOL_BASE_URL);
            Scanner input = new Scanner(is);
            while (input.hasNextLine()) {
                String[] keyValuePair = input.nextLine().split(",");
                map.put(keyValuePair[0], keyValuePair[1]);
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getABit(String mnemonic) {
        return symbolsWhereAIs1.contains(mnemonic) ? "1" : "0";
    }
}