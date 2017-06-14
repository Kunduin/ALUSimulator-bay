import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by 15852 on 2017/4/27 0027.
 */
public class ALUTest {
    private ALU alu=new ALU();
    private xALU xalu=new xALU();
    @Test
    public void integerRepresentation() throws Exception {

        assertEquals("11111111",alu.integerRepresentation("-1",8));
//        assertEquals("1100",alu.integerRepresentation("-4",4));
//        assertEquals("10000000",alu.integerRepresentation("-128",8));
//        assertEquals("10000000",alu.integerRepresentation("128",8));
//        assertEquals("0000000",alu.integerRepresentation("128",7));
//        assertEquals("010000000",alu.integerRepresentation("128",9));
//        assertEquals("00000011",alu.integerRepresentation("3",8));
//        assertEquals("10000000000000000000000000000000",alu.integerRepresentation("-2147483648",32));

    }

    @Test
    public void floatRepresentation() throws Exception {
//        assertEquals("1100000111100000000000000000000000000000000000000000000000000000",alu.floatRepresentation("-2147483648.0",11,52));
//  assertEquals("01000001001000000000000000000000",alu.floatRepresentation("10.0",8,23));
//        assertEquals("0101000111100",alu.floatRepresentation("47.0",5,7));
//        assertEquals("011111000000",alu.floatRepresentation("2147483647.0",5,6));
//        assertEquals("111111000000",alu.floatRepresentation("-2147483647.0",5,6));
//        assertEquals("10111101110011001100110011001101",alu.floatRepresentation("-0.1",8,23));
//        assertEquals("00111101110011001100110011001101",alu.floatRepresentation("0.1",8,23));
//        assertEquals("000000000000",alu.floatRepresentation("0.0",5,6));
//        assertEquals("01001111100000000000000000000000",alu.floatRepresentation("4294967295.0",8,23));
//        assertEquals("01001111100000000000000000000000",alu.floatRepresentation("4294967295.0",8,23));
        System.out.println(alu.floatRepresentation("-0.1999999999999993",4,47));
        System.out.println(alu.floatRepresentation("-0.1999999999999993",4,48));
        System.out.println(alu.floatRepresentation("-0.1999999999999993",4,100));
        System.out.println(alu.floatRepresentation("0.20000000000000018",4,49));
        System.out.println(alu.floatRepresentation("0.20000000000000018",4,55));
        System.out.println(alu.floatRepresentation("0.20000000000000018",4,100));

//        String string;
//        for(int i=0;i<1000;i++) {
//            string=String.valueOf(-50000+0.1D*i);
//            for(int elength=4;elength<20;elength++){
//                for(int slength=4;slength<50;slength++){
//                    System.out.println(string+"##"+elength+"##"+slength);
//                    assertEquals(alu.floatRepresentation(string, elength, slength), wALU.floatRepresentation(string, elength, slength));
//                }
//            }
//
//        }
    }

    @Test
    public void ieee754() throws Exception {
        assertEquals("10111101110011001100110011001100",alu.ieee754("-0.1",32));
        assertEquals("00111101110011001100110011001100",alu.ieee754("0.1",32));

    }

    @Test
    public void integerTrueValue() throws Exception {

    }

    @Test
    public void floatTrueValue() throws Exception {
        assertEquals("11.375",alu.floatTrueValue("01000001001101100000", 8, 11));
    }

    @Test
    public void negation() throws Exception {

    }

    @Test
    public void leftShift() throws Exception {

    }

    @Test
    public void logRightShift() throws Exception {

    }


    @Test
    public void ariRightShift() throws Exception {

    }

    @Test
    public void fullAdder() throws Exception {
    }

    @Test
    public void claAdder() throws Exception {
        assertEquals("01011",alu.claAdder("1001", "0001", '1'));

    }

    @Test
    public void oneAdder() throws Exception {
        assertEquals("000001010",alu.oneAdder("00001001"));
        assertEquals("000000100",alu.oneAdder("00000011"));
    }

    @Test
    public void adder() throws Exception {
        assertEquals("11000",alu.adder("000","0111",'1',4));

    }

    @Test
    public void integerAddition() throws Exception {

    }

    @Test
    public void integerSubtraction() throws Exception {
        assertEquals("000000001",alu.integerSubtraction("0100", "0011", 8));
    }

    @Test
    public void integerMultiplication() throws Exception {
        assertEquals("01000",alu.integerMultiplication("1000", "0001", 4));
    }

    @Test
    public void integerDivision() throws Exception {
//        assertEquals("00000000100000001",alu.integerDivision("0100", "0011", 8));
        assertEquals("000100000",alu.integerDivision("1000", "1100", 4));

    }

    @Test
    public void signedAddition() throws Exception {
//        assertEquals("0100000111",alu.signedAddition("1100", "1011", 8));
        assertEquals("000001",alu.signedAddition("00001", "10000", 4));

    }

    @Test
    public void floatAddition() throws Exception {
        assertEquals("0011010000",alu.floatAddition("000010000", "011010000", 4, 4, 4));
    }

    @Test
    public void floatSubtraction() throws Exception {
//        assertEquals("0100000001",alu.floatSubtraction("000011111","000100000",4,4,10));
        assertEquals("0100000011",alu.floatSubtraction("000010000","000010011",4,4,10));
//        assertEquals("0011010000",alu.floatSubtraction("000010000", "000010001", 4, 4, 4));
        assertEquals("0100001000",alu.floatSubtraction("000010000","000011000",4,4,10));


    }

    @Test
    public void floatMultiplication() throws Exception {
    }

    @Test
    public void floatDivision() throws Exception {
//        assertEquals();
//        assertEquals("0000000111",xalu.floatDivision("000010000","010000001",4,4));

//        assertEquals("0000000011",alu.floatDivision("000010000","010010001",4,4));
        assertEquals("1011110000",alu.floatDivision("010100000","000010000",4,4));

    }

}