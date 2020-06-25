package org.nineradio;

import static org.junit.Assert.assertEquals;
import org.junit.*;

import java.util.*;

public class SymbolTableTest {
    private SymbolTable table = new SymbolTable();

    @Test
    public void containsWorksOnPredefinedSymbol() {
        assertEquals("Contains works on predefined symbol", true, table.contains("SCREEN"));
    }

    @Test
    public void containsWorksOnSymbolNotInTable() {
        assertEquals("Contains works on symbol not in table (foo)", false, table.contains("foo"));
    }

    @Test
    public void addEntryWorksForNewSymbol() {
        table.addEntry("bar");
        assertEquals("addEntry works on new symbol (bar)", 16, table.getAddress("bar"));
    }

    @Test
    public void addEntryWorksForSymbolAddressPair() {
        table.addEntry("baz", 20);
        assertEquals("addEntry works on new symbol-address pair (baz, 20)", 20, table.getAddress("baz"));
    }
}