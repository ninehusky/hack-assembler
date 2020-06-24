package org.nineradio;

import org.junit.*;

import static org.junit.Assert.assertEquals;

import java.io.*;
import java.util.*;

public class ParserTest {
    private Parser p;
    private InputStream is = getClass().getClassLoader().getResourceAsStream("Add.asm");
    @Before
    public void constructsProperly() {
        p = new Parser(is);
    }

    @Test
    public void hasMoreCommandsReturnsTrueWhenMoreCommands() {
        assertEquals("hasMoreCommands() returns false when true expected", true, p.hasMoreCommands());
    }

    @Test
    public void hasMoreCommandsReturnsFalseWhenNoMoreCommands() {
        while (p.hasMoreCommands()) {
            p.advance();
        }
        assertEquals("hasMoreCommands() returns true when false expected", false, p.hasMoreCommands());
    }

    @Test(expected = IllegalStateException.class)
    public void advanceThrowsExceptionWhenNoMoreCommands() {
        p = new Parser(is);
        while (true) {
            p.advance();
        }
    }

    @Test
    public void commandTypeReturnsACommandCorrectlyWhenGivenNumber() {
        String aCommand = "@234";
        is = new ByteArrayInputStream(aCommand.getBytes());
        p = new Parser(is);
        p.advance();
        assertEquals("A_COMMAND returned when Parser given A-Command with number", CommandType.A_COMMAND, p.commandType());
    }

    // TODO: implement
    @Test
    public void commandTypeReturnsACommandCorrectlyWhenGivenSymbol() {
        String aCommand = "@sum";
        is = new ByteArrayInputStream(aCommand.getBytes());
        p = new Parser(is);
        p.advance();
        assertEquals("A_COMMAND returned when Parser given A-Command with symbol", CommandType.A_COMMAND, p.commandType());
    }

    @Test
    public void commandTypeReturnsLCommandWhenGivenLCommand() {
        String lCommand = "(LOOP)";
        is = new ByteArrayInputStream(lCommand.getBytes());
        p = new Parser(is);
        p.advance();
        assertEquals("L_COMMAND returned when Parser given L-Command", CommandType.L_COMMAND, p.commandType());
    }

    @Test
    public void commandTypeReturnsCCommandWhenGivenDestAndComp() {
        String cCommand = "A=0";
        is = new ByteArrayInputStream(cCommand.getBytes());
        p = new Parser(is);
        p.advance();
        assertEquals("C_COMMAND returned with C-Command with only dest and comp", CommandType.C_COMMAND, p.commandType());
    }

    @Test
    public void commandTypeReturnsCComandWhenGivenCompAndJump() {
        String cCommand = "0;JMP";
        is = new ByteArrayInputStream(cCommand.getBytes());
        p = new Parser(is);
        p.advance();
        assertEquals("C_COMMAND returned with C-Command with only comp and jump", CommandType.C_COMMAND, p.commandType());
    }

    @Test
    public void commandTypeReturnsCCommandWhenGivenDestCompAndJump() {
        String cCommand = "D=0;JMP";
        is = new ByteArrayInputStream(cCommand.getBytes());
        p = new Parser(is);
        p.advance();
        assertEquals("C_COMMAND returned with C-Command with dest comp and jump", CommandType.C_COMMAND, p.commandType());
    }

    @Test
    public void commandTypeReturnsNoCommandWhenGivenWhitespaceAndComments() {
        String notCode = "         \n // here's some code";
        is = new ByteArrayInputStream(notCode.getBytes());
        p = new Parser(is);
        p.advance();
        assertEquals("NO_COMMAND returned when given whitespace", CommandType.NO_COMMAND, p.commandType());
        p.advance();
        assertEquals("NO_COMMAND returned when given comments", CommandType.NO_COMMAND, p.commandType());
    }
    
    @Test
    public void symbolReturnsCorrectSymbolForACommand() {
        String aCommand = "@index";
        is = new ByteArrayInputStream(aCommand.getBytes());
        p = new Parser(is);
        p.advance();
        assertEquals("Returns correct symbol when given A-Command", "index", p.symbol());
    }

    @Test
    public void symbolReturnsCorrectSymbolForLCommand() {
        String lCommand = "(LOOP)";
        is = new ByteArrayInputStream(lCommand.getBytes());
        p = new Parser(is);
        p.advance();
        assertEquals("Returns correct symbol when given L-Command", "LOOP", p.symbol());
    }

    @Test(expected = IllegalStateException.class)
    public void symbolThrowsExceptionIfImproperCommandGiven() {

        String invalidCommand = "D=0;JMP";
        is = new ByteArrayInputStream(invalidCommand.getBytes());
        p = new Parser(is);
        p.advance();
        p.symbol();
    }

    // Remaining tests for dest/comp/jump seem to be okay for now
}