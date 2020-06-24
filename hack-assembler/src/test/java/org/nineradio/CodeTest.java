package org.nineradio;

import static org.junit.Assert.assertEquals;
import org.junit.*;

import java.util.*;

public class CodeTest {
    private Code c;

    @Before
    public void setupTest() {
        c = new Code();
    }

    
    @Test
    public void constructorPasses() {
        c = new Code();
    }

    @Test
    public void destMnemonicsReturnCorrectValues() {
        // I am so sorry
        List<String> destMnemonics = Arrays.asList("", "M", "D", "MD", "A", "AM", "AD", "AMD");
        List<String> binary = Arrays.asList("000", "001", "010", "011", "100", "101", "110", "111");
        List<String> binaryTest = new ArrayList<>();

        for (String mnemonic : destMnemonics) {
            binaryTest.add(c.dest(mnemonic));
        }

        assertEquals(binaryTest, binary);

    }

    @Test
    public void compMnemonicsReturnCorrectValues() {
        List<String> compMnemonics = Arrays.asList("0", "1", "-1", "D", "A", "M", "!D", "!A", "!M",
                                                   "-D", "-A", "-M", "D+1", "A+1", "M+1", "D-1",
                                                   "A-1", "M-1", "D+A", "D+M", "D-A", "D-M", "A-D",
                                                   "M-D", "D&A", "D&M", "D|A", "D|M");
        List<String> binary = Arrays.asList("101010", "111111", "111010", "001100", "110000",
                                            "110000", "001101", "110001", "110001", "001111", "110011",
                                            "110011", "011111", "110111", "110111", "001110",
                                            "110010", "110010", "000010", "000010", "010011", "010011",
                                            "000111", "000111", "000000", "000000", "010101", "010101");

        List<String> binaryTest = new ArrayList<>();

        assertEquals(compMnemonics.size(), binary.size());
        for (String mnemonic : compMnemonics) {
            binaryTest.add(c.comp(mnemonic));
        }

        assertEquals(binaryTest, binary);
    }

    @Test
    public void jumpWorks() {
        List<String> jumpMnemonics = Arrays.asList("", "JGT", "JEQ", "JGE", "JLT", "JNE",
                                                   "JLE", "JMP");
        List<String> binary = Arrays.asList("000", "001", "010", "011", "100", "101", "110", "111");
        List<String> binaryTest = new ArrayList<>();

        for (String mnemonic : jumpMnemonics) {
            binaryTest.add(c.jump(mnemonic));
        }

        assertEquals(binaryTest, binary);
    }
}