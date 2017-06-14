import static org.junit.Assert.*;

/**
 * Created by xuxiangzhe on 2017/6/13.
 */
public class ALUTest2 {
    String[] nums={
            "0000","0001","0010","0011","0100","0101","0110","0111",
            "1000","1001","1010","1011","1100","1101","1110","1111"
    };
    static ALU alu=new ALU();
    static xALU xalu=new xALU();

    @org.junit.Test
    public void integerRepresentation() throws Exception {
    }

    @org.junit.Test
    public void floatRepresentation() throws Exception {
    }

    @org.junit.Test
    public void ieee754() throws Exception {
    }

    @org.junit.Test
    public void integerTrueValue() throws Exception {
    }

    @org.junit.Test
    public void floatTrueValue() throws Exception {
    }

    @org.junit.Test
    public void negation() throws Exception {
    }

    @org.junit.Test
    public void leftShift() throws Exception {
    }

    @org.junit.Test
    public void logRightShift() throws Exception {
    }

    @org.junit.Test
    public void ariRightShift() throws Exception {
    }

    @org.junit.Test
    public void fullAdder() throws Exception {
    }

    @org.junit.Test
    public void claAdder() throws Exception {
    }

    @org.junit.Test
    public void oneAdder() throws Exception {
    }

    @org.junit.Test
    public void adder() throws Exception {
    }

    @org.junit.Test
    public void integerAddition() throws Exception {
        for(int i=0;i<16;i++) {
            for (int j = 0; j < 16; j++) {
                System.out.println(nums[i]+"****"+nums[j]);
                assertEquals(alu.integerAddition(nums[i], nums[j], 4), xalu.integerAddition(nums[i], nums[j], 4));
            }
        }
    }

    @org.junit.Test
    public void integerSubtraction() throws Exception {
        for(int i=0;i<16;i++) {
            for (int j = 0; j < 16; j++) {
                System.out.println(nums[i]+"****"+nums[j]);
                assertEquals(alu.integerSubtraction(nums[i], nums[j], 16), xalu.integerSubtraction(nums[i], nums[j], 16));
            }
        }
    }

    @org.junit.Test
    public void integerMultiplication() throws Exception {
        for(int i=0;i<16;i++) {
            for (int j = 0; j < 16; j++) {
                System.out.println(nums[i]+"****"+nums[j]);
                assertEquals(alu.integerMultiplication(nums[i], nums[j], 4), xalu.integerMultiplication(nums[i], nums[j], 4));
            }
        }
    }

    @org.junit.Test
    public void integerDivision() throws Exception {
        for(int i=0;i<16;i++) {
            for (int j = 0; j < 16; j++) {
                System.out.println(nums[i]+"****"+nums[j]);
                assertEquals(alu.integerDivision(nums[i], nums[j], 4), xalu.integerDivision(nums[i], nums[j], 4));
            }
        }
    }

    @org.junit.Test
    public void signedAddition() throws Exception {
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                System.out.println(nums[i]+"*****"+nums[j]);
                assertEquals(alu.signedAddition("1"+nums[i],"1"+nums[j],4),xalu.signedAddition("1"+nums[i],"1"+nums[j],4));
                System.out.println(2);
                assertEquals(alu.signedAddition("0"+nums[i],"1"+nums[j],4),xalu.signedAddition("0"+nums[i],"1"+nums[j],4));
                System.out.println(3);
                assertEquals(alu.signedAddition("1"+nums[i],"0"+nums[j],4),xalu.signedAddition("1"+nums[i],"0"+nums[j],4));
                System.out.println(4);
                assertEquals(alu.signedAddition("0"+nums[i],"0"+nums[j],4),xalu.signedAddition("0"+nums[i],"0"+nums[j],4));
            }
        }
    }

    @org.junit.Test
    public void floatAddition() throws Exception {
        for(int i=1;i<16;i++){
            for(int j=0;j<16;j++){
                for(int i2=1;i2<16;i2++){
                    for(int j2=0;j2<16;j2++){
                        System.out.println(nums[i]+" "+nums[j]+"***"+nums[i2]+" "+nums[j2]);
                        assertEquals(alu.floatAddition("0"+nums[i]+nums[j],"0"+nums[i2]+nums[j2],4,4,10),xalu.floatAddition("0"+nums[i]+nums[j],"0"+nums[i2]+nums[j2],4,4,10));
                    }
                }
            }
        }
    }

    @org.junit.Test
    public void floatSubtraction() throws Exception {
        for(int i=1;i<15;i++){
            for(int j=0;j<16;j++){
                for(int i2=1;i2<15;i2++){
                    for(int j2=0;j2<16;j2++){
                        System.out.println(nums[i]+" "+nums[j]+"***"+nums[i2]+" "+nums[j2]);
                        assertEquals(alu.floatSubtraction("0"+nums[i]+nums[j],"0"+nums[i2]+nums[j2],4,4,10),xalu.floatSubtraction("0"+nums[i]+nums[j],"0"+nums[i2]+nums[j2],4,4,10));
                    }
                }
            }
        }
    }

    @org.junit.Test
    public void floatMultiplication() throws Exception {
        for(int i=7;i<15;i++){
            for(int j=0;j<16;j++){
                for(int i2=7;i2<15;i2++){
                    for(int j2=0;j2<16;j2++){
                        System.out.println(nums[i]+" "+nums[j]+"***"+nums[i2]+" "+nums[j2]);
                        assertEquals(xalu.floatMultiplication("0"+nums[i]+nums[j],"0"+nums[i2]+nums[j2],4,4),alu.floatMultiplication("0"+nums[i]+nums[j],"0"+nums[i2]+nums[j2],4,4));
                    }
                }
            }
        }
    }

    @org.junit.Test
    public void floatDivision() throws Exception {
        for(int i=1;i<16;i++){
            for(int j=0;j<16;j++){
                for(int i2=1;i2<16;i2++){
                    for(int j2=0;j2<16;j2++){
                        System.out.println(nums[i]+" "+nums[j]+"***"+nums[i2]+" "+nums[j2]);
                        assertEquals(xalu.floatDivision("0"+nums[i]+nums[j],"0"+nums[i2]+nums[j2],4,4),alu.floatDivision("0"+nums[i]+nums[j],"0"+nums[i2]+nums[j2],4,4));
                    }
                }
            }
        }
    }

}