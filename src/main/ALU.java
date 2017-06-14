import jdk.nashorn.internal.ir.WhileNode;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * ģ��ALU���������͸���������������
 * @author 161250157 ������
 *
 */

public class ALU {

	/**
	 * ����ʮ���������Ķ����Ʋ����ʾ��<br/>
	 * ����integerRepresentation("9", 8)
	 * @param number ʮ������������Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʋ����ʾ�ĳ���
	 * @return number�Ķ����Ʋ����ʾ������Ϊlength
	 */
	public String integerRepresentation (String number, int length) {
		// TODO YOUR CODE HERE.
		char[] result=new char[length];
		int num=Integer.parseInt(number);

		for(int i=0;i<length;i++){

			if((num&1)==0){
				result[length-i-1]='0';
			}else{
				result[length-i-1]='1';
			}
			num=num>>1;

		}

		return String.valueOf(result);
	}
	
	/**
	 * ����ʮ���Ƹ������Ķ����Ʊ�ʾ��
	 * ��Ҫ���� 0������񻯡����������+Inf���͡�-Inf������ NaN�����أ������� IEEE 754��
	 * �������Ϊ��0���롣<br/>
	 * ����floatRepresentation("11.375", 8, 11)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return number�Ķ����Ʊ�ʾ������Ϊ 1+eLength+sLength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
	 */
	public String floatRepresentation (String number, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		String sign=new String();
		String result=new String();
		int trueE=(1<<(eLength-1))-1;
		char[] eChar=new char[eLength];
		char[] sChar=new char[sLength];
		char[] judgement=number.toCharArray();

		if(judgement[0]=='-'){
			sign="1";
		}else{
			sign="0";
		}

//		0����������
		if(number.equals("+Inf")||number.equals("-Inf")){

			for(int i=0;i<eLength;i++){
				eChar[i]='1';
			}
			for(int i=0;i<sLength;i++){
				sChar[i]='0';
			}
			result=sign+String.valueOf(eChar)+String.valueOf(sChar);

		}else if(number.equals("0.0")||number.equals("-0.0")){

			for(int i=0;i<eLength;i++){
				eChar[i]='0';
			}
			for(int i=0;i<sLength;i++){
				sChar[i]='0';
			}
			result=sign+String.valueOf(eChar)+String.valueOf(sChar);
		}else {
//			��ʼ����

			boolean judge0=false;
			boolean judgeOver=false;
			boolean judgeSpecial=false;
			int judgeE=0;

//			numΪС����ǰ���ַ���
			String[] num = number.split("\\.");
			String binaryBeforePoint=new String();

//			numLΪ��������
			long numL = Long.parseLong(num[0]);
			BigDecimal numR=new BigDecimal("0.0");
			if (judgement[0] == '-') {
				numL = -numL;
			}
//			numRΪС������
			if(num.length>1) {
				numR = new BigDecimal("0." + num[1]);
			}
//			�������ִ���
			if(numL>0){
//				judge0�����ж����������Ƿ����
				judge0=true;
				while (numL>0){
					numL=numL>>1;
					judgeE+=1;//judgeE�����ж��������ֵ�λ��
				}
				judgeE-=1;
//				trueE�����ж�����ָ��ֵ
				trueE+=judgeE;
				if(trueE>=(1<<(eLength))-1) {
					judgeOver = true;
				}
				if(judgeOver!=true) {
					binaryBeforePoint = Long.toBinaryString(Math.abs(Long.parseLong(num[0])));
					for (int i = 1, b = binaryBeforePoint.length(); i < b&&i<sLength+1; i++) {
						sChar[i - 1] = binaryBeforePoint.charAt(i);
					}
				}
			}
//
			if(judge0){
				for(int i=0;sLength-judgeE-i>0;i++){
					numR=numR.multiply(new BigDecimal(2));
					if(numR.compareTo(new BigDecimal(1))>=0){
						numR=numR.subtract(new BigDecimal(1));
						sChar[judgeE+i]='1';
					}else{
						sChar[judgeE+i]='0';
					}
				}
				result=sign+this.integerRepresentation(String.valueOf(trueE),eLength)+String.valueOf(sChar);

			}else{
				while (numR.compareTo(new BigDecimal(1))==-1){
					judgeE-=1;
					numR=numR.multiply(new BigDecimal(2));
				}
				trueE+=judgeE;
				if(trueE<0){
					judgeSpecial=true;
				}

				numR=numR.subtract(new BigDecimal(1));
				for(int i=0;i<sLength;i++){
					numR=numR.multiply(new BigDecimal(2));
					if(numR.compareTo(new BigDecimal(1))>=0){
						numR=numR.subtract(new BigDecimal(1));
						sChar[i]='1';
					}else{
						sChar[i]='0';
					}
				}


					result=sign+this.integerRepresentation(String.valueOf(trueE),eLength)+String.valueOf(sChar);

			}

			if(judgeOver){
				for(int i=0;i<eLength;i++){
					eChar[i]='1';
				}
				for(int i=0;i<sLength;i++){
					sChar[i]='0';
				}
				result=sign+String.valueOf(eChar)+String.valueOf(sChar);

			}else if(judgeSpecial){

				char[] trueResult=new char[sLength];

				for(int i=0;i<eLength;i++){
					eChar[i]='0';
				}

				if(trueE<(-sLength+1)){

					for(int i=0;i<sLength;i++){
						sChar[i]='0';
					}
					result=sign+String.valueOf(eChar)+String.valueOf(sChar);
				}else{
					int index=0;

					for(int i=0;i<(-trueE);i++){
						trueResult[i]='0';
						index+=1;
					}
					trueResult[index]='1';

					for(int i=0;i<sLength-index-1;i++) {
						trueResult[index + i +1] = sChar[i];
					}

					result=sign+String.valueOf(eChar)+String.valueOf(trueResult);
				}

			}
		}
		return result;
	}

	/**
	 * ����ʮ���Ƹ�������IEEE 754��ʾ��Ҫ�����{@link #floatRepresentation(String, int, int) floatRepresentation}ʵ�֡�<br/>
	 * ����ieee754("11.375", 32)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʊ�ʾ�ĳ��ȣ�Ϊ32��64
	 * @return number��IEEE 754��ʾ������Ϊlength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
	 */
	public String ieee754 (String number, int length) {
		// TODO YOUR CODE HERE.
		String result=new String();

		if(length==64){
			result=floatRepresentation(number,11,52);
		}else{
			result=floatRepresentation(number,8,23);
		}
		return result;
	}
	
	/**
	 * ��������Ʋ����ʾ����������ֵ��<br/>
	 * ����integerTrueValue("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 */
	public String integerTrueValue (String operand) {
		// TODO YOUR CODE HERE.
		char[] operandChar=operand.toCharArray();
		long result=0;
		if(operandChar[0]=='1') {
			result = -(1 << (operandChar.length - 1));
		}
		for(int i=1;i<operandChar.length;i++){
			if(operandChar[i]=='1'){
				result+=1<<(operandChar.length-i-1);
			}
		}
		return String.valueOf(result);
	}
	
	/**
	 * ���������ԭ���ʾ�ĸ���������ֵ��<br/>
	 * ����floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ����������ֱ��ʾΪ��+Inf���͡�-Inf���� NaN��ʾΪ��NaN��
	 */
	public String floatTrueValue (String operand, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		char[] operandChar=operand.toCharArray();
		boolean judgeOver=true;
		boolean judgeNaN=false;
		boolean judgeSpecial=true;
		boolean judge0=true;
		long resultLong=0;
		String resultString=new String("NaN");
		String signBit=new String();
		String eString=operand.substring(1,eLength+1);
		String sString=operand.substring(eLength+1,eLength+sLength+1);
		char[] sChar=sString.toCharArray();
		long eLong=Long.parseLong(eString,2)-(1<<(eLength-1))+1;

		if(operandChar[0]=='1'){
			signBit="-";
		}else {
			signBit="";
		}

		for(int i=0;i<eLength;i++){
			if(operandChar[i+1]=='0'){
				judgeOver=false;
			}else {
				judgeSpecial=false;
				judge0=false;
			}
		}

//		����������0
		if(judgeOver){
			for(int i=0;i<sLength;i++){
				if(operandChar[i+1+eLength]=='1'){
					judgeNaN=true;
				}
			}
		}else if(judge0){
			for(int i=0;i<sLength;i++){
				if(operandChar[i+1+eLength]=='1'){
					judge0=false;
				}
			}
		}


		if (judge0){
			resultString="0.0";
		}else if(judgeNaN!=true){

//
			if(judgeOver){
				if(operandChar[0]=='0'){
					resultString="+Inf";
				}else{
					resultString="-Inf";
				}
			}else{
				double resultDouble=0;
				resultString=signBit;

//				�ǹ����
				if(judgeSpecial){

					for(int i=0;i<sLength;i++){
						if(sChar[i]=='1'){
							resultDouble+=Math.pow(2,(-((1<<(eLength-1))-2)-i-1));
						}
					}
					resultString+=(String.valueOf(resultDouble));

				}else {
					resultDouble+=Math.pow(2,eLong);
					for(int i=0;i<sLength;i++){
						if(sChar[i]=='1'){
							resultDouble+=Math.pow(2,eLong-i-1);
						}
					}
					resultString+=(String.valueOf(resultDouble));
				}

			}
		}






		return resultString;
	}
	
	/**
	 * ��λȡ��������<br/>
	 * ����negation("00001001")
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @return operand��λȡ���Ľ��
	 */
	public String negation (String operand) {
		// TODO YOUR CODE HERE.
		StringBuffer result=new StringBuffer();
		char[] operandChar=operand.toCharArray();

		for(int i=0;i<operand.length();i++){
			if(operandChar[i]=='1'){
				result.append("0");
			}else {
				result.append("1");
			}
		}
		return new String(result);
	}
	
	/**
	 * ���Ʋ�����<br/>
	 * ����leftShift("00001001", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand����nλ�Ľ��
	 */
	public String leftShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		StringBuffer result=new StringBuffer(operand);
		for(int i=0;i<n;i++){
			result.append("0");
		}
		return new String(result).substring(n,operand.length()+n);
	}
	
	/**
	 * �߼����Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand�߼�����nλ�Ľ��
	 */
	public String logRightShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		StringBuffer result=new StringBuffer();
		for(int i=0;i<n;i++){
			result.append("0");
		}
		result.append(operand);
		return new String(result).substring(0,operand.length());
	}
	
	/**
	 * �������Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand��������nλ�Ľ��
	 */
	public String ariRightShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		StringBuffer result=new StringBuffer();
		if(operand.substring(0,1).equals("1")){
			for(int i=0;i<n;i++){
				result.append("1");
			}
		}else {
			for(int i=0;i<n;i++){
				result.append("0");
			}
		}
		result.append(operand);

		return new String(result).substring(0,operand.length());
	}
	
	/**
	 * ȫ����������λ�Լ���λ���мӷ����㡣<br/>
	 * ����fullAdder('1', '1', '0')
	 * @param x ��������ĳһλ��ȡ0��1
	 * @param y ������ĳһλ��ȡ0��1
	 * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
	 * @return ��ӵĽ�����ó���Ϊ2���ַ�����ʾ����1λ��ʾ��λ����2λ��ʾ��
	 */
	public String fullAdder (char x, char y, char c) {
		// TODO YOUR CODE HERE.
		int xNum=Integer.parseInt(String.valueOf(x));
		int yNum=Integer.parseInt(String.valueOf(y));
		int cNum=Integer.parseInt(String.valueOf(c));

		int cOut=xNum&cNum|yNum&cNum|xNum&yNum;
		int s=xNum^yNum^cNum;
		return String.valueOf(cOut)+String.valueOf(s);
	}
	
	/**
	 * 4λ���н�λ�ӷ�����Ҫ�����{@link #fullAdder(char, char, char) fullAdder}��ʵ��<br/>
	 * ����claAdder("1001", "0001", '1')
	 * @param operand1 4λ�����Ʊ�ʾ�ı�����
	 * @param operand2 4λ�����Ʊ�ʾ�ļ���
	 * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
	 * @return ����Ϊ5���ַ�����ʾ�ļ����������е�1λ�����λ��λ����4λ����ӽ�������н�λ��������ѭ�����
	 */
	public String claAdder (String operand1, String operand2, char c) {
		// TODO YOUR CODE HERE.
		int[] p=new int[5];
		int[] g=new int[5];
		int[] cOut=new int[5];
		char[] operand1Char=operand1.toCharArray();
		char[] operand2Char=operand2.toCharArray();
		char[] result=new char[5];
		char[] tmp=new char[2];
		cOut[0]=Integer.parseInt(String.valueOf(c));


		for (int i=1;i<5;i++){
			p[i]=Integer.parseInt(operand1.substring(5-i-1,5-i))|Integer.parseInt(operand2.substring(5-i-1,5-i));
			g[i]=Integer.parseInt(operand1.substring(5-i-1,5-i))&Integer.parseInt(operand2.substring(5-i-1,5-i));
		}

		tmp=fullAdder(operand1Char[3],operand2Char[3],c).toCharArray();
		result[4]=tmp[1];
		cOut[1]=g[1]|p[1]&cOut[0];

		tmp=fullAdder(operand1Char[2],operand2Char[2],(String.valueOf(cOut[1]).charAt(0))).toCharArray();
		result[3]=tmp[1];
		cOut[2]=g[2]|p[2]&g[1]|p[2]&p[1]&cOut[0];

		tmp=fullAdder(operand1Char[1],operand2Char[1],(String.valueOf(cOut[2]).charAt(0))).toCharArray();
		result[2]=tmp[1];
		cOut[3]=g[3]|p[3]&g[2]|p[3]&p[2]&g[1]|p[3]&p[2]&p[1]&cOut[0];

		tmp=fullAdder(operand1Char[0],operand2Char[0],(String.valueOf(cOut[3]).charAt(0))).toCharArray();
		result[1]=tmp[1];
		cOut[4]=g[4]|p[4]&g[3]|p[4]&p[3]&g[2]|p[4]&p[3]&p[2]&g[1]|p[4]&p[3]&p[2]&p[1]&cOut[0];

		result[0]=String.valueOf(cOut[4]).charAt(0);

		return String.valueOf(result);
	}
	
	/**
	 * ��һ����ʵ�ֲ�������1�����㡣
	 * ��Ҫ�������š����š�����ŵ�ģ�⣬
	 * ������ֱ�ӵ���{@link #fullAdder(char, char, char) fullAdder}��
	 * {@link #claAdder(String, String, char) claAdder}��
	 * {@link #adder(String, String, char, int) adder}��
	 * {@link #integerAddition(String, String, int) integerAddition}������<br/>
	 * ����oneAdder("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand��1�Ľ��������Ϊoperand�ĳ��ȼ�1�����е�1λָʾ�Ƿ���������Ϊ1������Ϊ0��������λΪ��ӽ��
	 */
	public String oneAdder (String operand) {
		// TODO YOUR CODE HERE.
		char[] beforeChar=operand.toCharArray();
		int[] beforeInt=new int[operand.length()];
		int[] c=new int[operand.length()+1];
		int[] resultInt=new int[operand.length()+1];
		StringBuffer result=new StringBuffer();
		c[0]=0;

		for(int i=0;i<operand.length();i++){
			beforeInt[i]=Integer.parseInt(String.valueOf(beforeChar[i]));
		}

		c[1]=beforeInt[operand.length()-1]&1;
		resultInt[operand.length()]=beforeInt[operand.length()-1]^1;

		for(int i=1;i<operand.length();i++){
			c[i+1]=c[i]&beforeInt[operand.length()-i-1];
			resultInt[operand.length()-i]=beforeInt[operand.length()-i-1]^c[i];
		}

		resultInt[0]=c[operand.length()]^c[operand.length()-1];

		for(int i=0;i<=operand.length();i++){
			result.append(resultInt[i]);
		}
		return new String(result);
	}
	
	/**
	 * �ӷ�����Ҫ�����{@link #claAdder(String, String, char)}����ʵ�֡�<br/>
	 * ����adder("0100", "0011", ��0��, 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param c ���λ��λ
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
	 */
	public String adder (String operand1, String operand2, char c, int length) {
		// TODO YOUR CODE HERE.
		StringBuffer operand1Buffer=new StringBuffer();
		StringBuffer operand2Buffer=new StringBuffer();
		StringBuffer resultBuffer=new StringBuffer();
		String entireOperand1;
		String entireOperand2;
		String[] results=new String[length/4];
		char[] cOut=new char[length/4+1];
		cOut[0]=c;

		for(int i=0;i<length-operand1.length();i++){
			if(operand1.charAt(0)=='0'){
				operand1Buffer.append('0');
			}else {
				operand1Buffer.append('1');
			}
		}
		for(int i=0;i<length-operand2.length();i++){
			if(operand2.charAt(0)=='0'){
				operand2Buffer.append('0');
			}else {
				operand2Buffer.append('1');
			}
		}
		operand1Buffer.append(operand1);
		operand2Buffer.append(operand2);
		entireOperand1=new String(operand1Buffer);
		entireOperand2=new String(operand2Buffer);

		for (int i=0;i<length/4;i++){
			results[i]=claAdder(entireOperand1.substring(length-4*(i+1),length-4*i),entireOperand2.substring(length-4*(i+1),length-4*i),cOut[i]);
			cOut[i+1]=results[i].charAt(0);
		}

		if(entireOperand1.charAt(0)==entireOperand2.charAt(0)&&results[length/4-1].charAt(1)!=entireOperand1.charAt(0)){
			resultBuffer.append("1");
		}else {
			resultBuffer.append("0");
		}

		for(int i=0;i<length/4;i++){
			resultBuffer.append(results[length/4-1-i].substring(1,5));
		}

		return new String(resultBuffer);
	}
	
	/**
	 * �����ӷ���Ҫ�����{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
	 * ����integerAddition("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
	 */
	public String integerAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		return adder(operand1,operand2,'0',length);
	}
	
	/**
	 * �����������ɵ���{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
	 * ����integerSubtraction("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ��������
	 */
	public String integerSubtraction (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		operand2=negation(operand2);
		return adder(operand1,operand2,'1',length);
	}
	
	/**
	 * �����˷���ʹ��Booth�㷨ʵ�֣��ɵ���{@link #adder(String, String, char, int) adder}�ȷ�����<br/>
	 * ����integerMultiplication("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ����˽�������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����˽��
	 */
	public String integerMultiplication (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String result=new String();
		StringBuffer operand1Buffer=new StringBuffer();
		StringBuffer operand2Buffer=new StringBuffer();
		for(int i=0;i<length-operand1.length();i++){
			if(operand1.charAt(0)=='0'){
				operand1Buffer.append("0");
			}else {
				operand1Buffer.append('1');
			}
		}operand1Buffer.append(operand1);
		for(int i=0;i<length-operand2.length();i++){
			if(operand2.charAt(0)=='0'){
				operand2Buffer.append("0");
			}else {
				operand2Buffer.append('1');
			}
		}operand2Buffer.append(operand2);


		StringBuffer x=new StringBuffer();

		for(int i=0;i<length;i++){
			x.append("0");
		}

		StringBuffer xNegative=new StringBuffer(oneAdder(negation(new String(operand1Buffer))).substring(1,length+1));
		StringBuffer y=new StringBuffer(operand2Buffer.append("0"));


		for(int i=0;i<length;i++){
			switch (y.substring(length-1)){
				case "10":
					x=new StringBuffer(adder(x.substring(0),xNegative.substring(0),'0',length).substring(1,length+1));
					break;
				case "01":
					x=new StringBuffer(adder(x.substring(0),operand1Buffer.substring(0),'0',length).substring(1,length+1));
					break;
			}
			y.insert(0,x.substring(length-1));
			y.delete(length+1,length+2);
			x=new StringBuffer(ariRightShift(x.substring(0),1));
		}

		boolean judge0=false;
		boolean judge1=false;
		String sign=new String();
		for(int i=0;i<length;i++){
			switch (x.charAt(i)){
				case '0':
					judge0=true;
					break;
				case '1':
					judge1=true;
					break;
			}
		}
		if((judge0&&judge1)||((Integer.parseInt(operand1.substring(0,1))==Integer.parseInt(operand2.substring(0,1)))
				&&(0!=Integer.parseInt(y.substring(0,1))))){
			sign="1";
		}else if(judge1){
			if(y.charAt(0)=='0'){
				sign="1";
			}else {
				sign="0";
			}
		}else {
			sign="0";
		}

		return sign+y.substring(0,length);
	}
	
	/**
	 * �����Ĳ��ָ������������ɵ���{@link #adder(String, String, char, int) adder}�ȷ���ʵ�֡�<br/>
	 * ����integerDivision("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊ2*length+1���ַ�����ʾ�������������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0�������lengthλΪ�̣����lengthλΪ����
	 */
	public String integerDivision (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		StringBuffer operand1Buffer=new StringBuffer();
		StringBuffer operand2Buffer=new StringBuffer();
		for(int i=0;i<2*length-operand1.length();i++){
			if(operand1.charAt(0)=='0'){
				operand1Buffer.append("0");
			}else {
				operand1Buffer.append('1');
			}
		}operand1Buffer.append(operand1);
		for(int i=0;i<length-operand2.length();i++){
			if(operand2.charAt(0)=='0'){
				operand2Buffer.append("0");
			}else {
				operand2Buffer.append('1');
			}
		}operand2Buffer.append(operand2);

		StringBuffer R=new StringBuffer(operand1Buffer.substring(0,length));
		StringBuffer Q=new StringBuffer(operand1Buffer.substring(length,length*2));
		String Y=new String(operand2Buffer);
		String tmp=new String();

		for(int i=0;i<length;i++){
			R.append(Q.substring(0,1));
			R.delete(0,1);
			Q.delete(0,1);
			if(R.substring(0,1).equals(Y.substring(0,1))){
				tmp=integerSubtraction(new String(R),Y,length).substring(1,length+1);
				if(tmp.substring(0,1).equals(R.substring(0,1))){
					R.delete(0,length);
					R.append(tmp);
					Q.append(1);
				}else {
					Q.append(0);
				}
			}else{
				tmp=integerAddition(new String(R),Y,length).substring(1,length+1);
				if(tmp.substring(0,1).equals(R.substring(0,1))){
					R.delete(0,length);
					R.append(tmp);
					Q.append(1);
				}else {
					Q.append(0);
				}
			}
		}

		if(operand1.charAt(0)!=operand2.charAt(0)){
			Q.append(oneAdder(negation(Q.substring(0,length))).substring(1,length+1));
			Q.delete(0,length);

		}


		char[] judge1=new char[length];
		char[] judge0=new char[length];
		Arrays.fill(judge1,'1');
		Arrays.fill(judge0,'0');
		if(operand2Buffer.substring(0,length).equals(String.valueOf(judge0))){
			return "NaN";
		}

		judge0[0]='1';
		String overflow1=String.valueOf(judge1);
		String overflow0=String .valueOf(judge0);
		judge0[0]='0';

		if(overflow0.equals(operand1Buffer)&&overflow1.equals(operand2Buffer)){
			if(R.substring(0,length).equals(oneAdder(negation(operand2Buffer.substring(0,length))).substring(1,length+1))){
				R.append(String.valueOf(judge0));
				R.delete(0,length);
				Q.append(integerSubtraction(Q.substring(0,length),"1",length).substring(1,length+1));
				Q.delete(0,length);
			}else if(R.substring(0,length).equals(operand2Buffer.substring(0,length))){
				R.append(String.valueOf(judge0));
				R.delete(0,length);
				Q.append(oneAdder(Q.substring(0,length)).substring(1,length+1));
				Q.delete(0,length);

			}
			if(operand1.charAt(0)=='1'&&operand2.charAt(0)=='1'&&Q.charAt(0)=='1'){
				Q=new StringBuffer(oneAdder(negation(Q.substring(0,Q.length()))).substring(1,Q.length()+1));
			}



			return 1+Q.substring(0,length)+R.substring(0,length);
		}else {
			if(R.substring(0,length).equals(oneAdder(negation(operand2Buffer.substring(0,length))).substring(1,length+1))){
				R.append(String.valueOf(judge0));
				R.delete(0,length);
				Q.append(integerSubtraction(Q.substring(0,length),"01",length).substring(1,length+1));
				Q.delete(0,length);
			}else if(R.substring(0,length).equals(operand2Buffer.substring(0,length))){
				R.append(String.valueOf(judge0));
				R.delete(0,length);
				Q.append(oneAdder(Q.substring(0,length)).substring(1,length+1));
				Q.delete(0,length);

			}
			if(operand1.charAt(0)=='1'&&operand2.charAt(0)=='1'&&Q.charAt(0)=='1'){
				Q=new StringBuffer(oneAdder(negation(Q.substring(0,Q.length()))).substring(1,Q.length()+1));
			}

			return 0+Q.substring(0,length)+R.substring(0,length);
		}
	}
	
	/**
	 * �����������ӷ������Ե���{@link #adder(String, String, char, int) adder}�ȷ�����
	 * ������ֱ�ӽ�������ת��Ϊ�����ʹ��{@link #integerAddition(String, String, int) integerAddition}��
	 * {@link #integerSubtraction(String, String, int) integerSubtraction}��ʵ�֡�<br/>
	 * ����signedAddition("1100", "1011", 8)
	 * @param operand1 ������ԭ���ʾ�ı����������е�1λΪ����λ
	 * @param operand2 ������ԭ���ʾ�ļ��������е�1λΪ����λ
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ����������ţ�����ĳ���������ĳ���С��lengthʱ����Ҫ���䳤����չ��length
	 * @return ����Ϊlength+2���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������2λΪ����λ����lengthλ����ӽ��
	 */
	public String signedAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		int sign1=Integer.parseInt(operand1.substring(0,1));
		int sign2=Integer.parseInt(operand2.substring(0,1));
		operand1="0"+operand1.substring(1,operand1.length());
		operand2="0"+operand2.substring(1,operand2.length());
		String tmp=new String();

		if(sign1==sign2){
			tmp=adder(0+operand1,0+operand2,'0',length*2);
			return tmp.charAt(length)+new String(new StringBuffer(tmp.substring(length+1,length*2+1)).insert(0,sign1));
		}else{
			tmp=integerSubtraction("0"+operand1.substring(1,operand1.length()),"0"+operand2.substring(1,operand2.length()),length*2).substring(length,length*2+1);
			if(tmp.charAt(0)=='1'){
				if(sign1==1){
					sign1=0;
				}else {
					sign1=1;
				}
				tmp=oneAdder(negation(tmp)).substring(1,tmp.length()+1);

				return "0"+sign1+tmp.substring(1,tmp.length());
			}else {

				return "0"+sign1+tmp.substring(1,tmp.length());
			}
		}

	}
	
	/**
	 * �������ӷ����ɵ���{@link #signedAddition(String, String, int) signedAddition}�ȷ���ʵ�֡�<br/>
	 * ����floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ļ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param gLength ����λ�ĳ���
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����ӽ�������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatAddition (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		char[] specialChar=new char[eLength];
		Arrays.fill(specialChar,'0');
		String special=String.valueOf(specialChar);
		Arrays.fill(specialChar,'1');
		String overflow=String.valueOf(specialChar);

		char[] special0=new char[sLength];
		Arrays.fill(special0,'0');
		if(operand1.substring(1,operand1.length()).equals(special+String.valueOf(special0))){
			return 0+operand2;
		}else if(operand2.substring(1,operand2.length()).equals(special+String.valueOf(special0))){
			return 0+operand1;
		}

		String eX=operand1.substring(1,1+eLength);
		String eY=operand2.substring(1,1+eLength);
		int sign1=Integer.parseInt(operand1.substring(0,1));
		int sign2=Integer.parseInt(operand2.substring(0,1));
		String sX=operand1.substring(1+eLength,1+eLength+sLength);
		String sY=operand2.substring(1+eLength,1+eLength+sLength);
		String eTmp=new String();
		String eTrue=new String();

		boolean eEqu=eX.equals(eY);

		String sResult=new String();
		String eResult=new String();

		eTmp=integerSubtraction("0"+eX,"0"+eY,((eLength/4)+1)*4).substring(((eLength/4)+1)*4-eLength,((eLength/4)+1)*4+1);
		eTrue=eTmp.substring(1,eTmp.length());
		char[] input=new char[Math.abs(Integer.valueOf(integerTrueValue(eTmp)))+1];
		char[] gput=new char[gLength];
		Arrays.fill(input,'0');
		Arrays.fill(gput,'0');
		if(input.length!=0) {
			input[input.length - 1] = '1';
		}
//		if(!eX.equals(special)&&!eY.equals(special)) {
			if (eTmp.charAt(0) == '0') {
				eY = eX;
				sY = String.valueOf(input) + sY + String.valueOf(gput);
				sX = "1" + sX + String.valueOf(gput);
				sY = sY.substring(0, sLength + gLength + 1);
			} else {
				eX = eY;
				sX = String.valueOf(input) + sX + String.valueOf(gput);
				sY = "1" + sY + String.valueOf(gput);
				sX = sX.substring(0, sLength + gLength + 1);
			}
//		}
		if(eX.equals(special)){
			sX="0"+operand1.substring(1+eLength,1+eLength+sLength)+String.valueOf(gput);
		}
		if(eY.equals(special)){
			sY="0"+operand2.substring(1+eLength,1+eLength+sLength)+String.valueOf(gput);
		}

		if(sign1==sign2) {
			sResult = integerAddition(0 + sY, 0 + sX, ((sY.length() / 4) + 2) * 4).substring(((sY.length() / 4) + 2) * 4 - sLength - gLength-1, ((sY.length() / 4) + 2) * 4 - gLength+1);
			eResult=eX;
			if(sResult.charAt(0)=='1'){
				eResult = oneAdder(eX).substring(1, eLength + 1);
				sResult=sResult.substring(1,1+sLength);
			}else if(sResult.charAt(1)=='1'){
				sResult=sResult.substring(2,2+sLength);
			}


				if (eResult.equals(overflow)||eX.charAt(0)=='1'&&eY.charAt(0)=='1'&&eResult.charAt(0)=='0') {
					char[] tmpChar=new char[sLength];
					Arrays.fill(tmpChar,'0');
					return "1"+sign1+overflow+String.valueOf(tmpChar);
				}
//			}
			return "0" + sign1 + eResult + sResult.substring(sResult.length()-sLength,sResult.length());
		}else {
			sResult = signedAddition("0" + sX, "1" + sY, ((sY.length() / 4) + 1) * 4);
			System.out.println(sResult);
			sResult=sResult.substring(1);
			eResult = eX;
			int count1=0;
			if(sResult.charAt(0)=='1'){
//				sResult=oneAdder(negation(sResult));
				if(sign1==1){
					sign1=0;
				}else {
					sign1=1;
				}
			}
			sResult=sResult.substring(sResult.length()-sLength-gLength-1);
			if(sResult.charAt(0)=='0'){
				for(int i=1;i<=sLength+gLength;i++){
					if(sResult.charAt(i)=='1'){
						count1=i;
						break;
					}
				}
				String tmpcount=""+count1;
				eResult=integerSubtraction("0"+eResult,integerRepresentation(tmpcount,(eLength/4+1)*4),(eLength/4+1)*4).substring((eLength/4+1)*4-eLength,(eLength/4+1)*4+1);
				if(eResult.charAt(0)=='1'||eResult.equals("0"+special)) {
					int theChange = Integer.parseInt(integerTrueValue(eResult));
					if (-theChange >= sLength) {
						eResult="0"+special;
					} else {
						char[] zero = new char[-theChange+1];
						Arrays.fill(zero, '0');
						sResult = String.valueOf(zero) + sResult.substring(count1,sResult.length());
						eResult = "0" + special;
					}
				}else {
					sResult=sResult.substring(count1,sResult.length());

				}

				eResult=eResult.substring(1,eLength+1);
			}
			while(sResult.length()<=sLength){
				sResult+="0";
			}

			if (eEqu) {
				if(count1==0){
					eResult=special;

				}
				sResult=sResult;

				while (sResult.length()<=sLength){
					sResult+="0";
				}

			}
			return "0" + sign1 + eResult + sResult.substring(1,sLength+1);
		}
	}
	
	/**
	 * �������������ɵ���{@link #floatAddition(String, String, int, int, int) floatAddition}����ʵ�֡�<br/>
	 * ����floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ļ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param gLength ����λ�ĳ���
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ�������������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatSubtraction (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		char[] operand2Char=operand2.toCharArray();
		if(operand2Char[0]=='0'){
			operand2Char[0]='1';
		}else {
			operand2Char[0]='0';
		}
		return floatAddition(operand1,String.valueOf(operand2Char),eLength,sLength,gLength);
	}
	
	/**
	 * �������˷����ɵ���{@link #integerMultiplication(String, String, int) integerMultiplication}�ȷ���ʵ�֡�<br/>
	 * ����floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ĳ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatMultiplication (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		char[] specialChar=new char[eLength];
		Arrays.fill(specialChar,'0');
		String special=String.valueOf(specialChar);
		Arrays.fill(specialChar,'1');
		String overflow=String.valueOf(specialChar);

		char[] special0=new char[sLength];
		Arrays.fill(special0,'0');
		if(operand1.substring(1,operand1.length()).equals(special+String.valueOf(special0))){
			return "00"+operand1.substring(1,operand1.length());
		}else if(operand2.substring(1,operand2.length()).equals(special+String.valueOf(special0))){
			return "00"+operand2.substring(1,operand2.length());
		}

		String eX=operand1.substring(1,1+eLength);
		String eY=operand2.substring(1,1+eLength);
		String etmp=new String();
		String eb=new String();
		int sign1=Integer.parseInt(operand1.substring(0,1));
		int sign2=Integer.parseInt(operand2.substring(0,1));
		int sign=1;
		int over=0;
		if(sign1==sign2){
			sign=0;
		}
		int signNumber=(1<<(eLength-1))+1;
		String sX=operand1.substring(1+eLength,1+eLength+sLength);
		String sY=operand2.substring(1+eLength,1+eLength+sLength);
		String mb=new String();

		etmp=integerAddition("0"+eX,"0"+eY,(eLength/4+1)*4).substring((eLength/4+1)*4-eLength+1,(eLength/4+1)*4+1);
		eb=integerAddition("0"+etmp,integerRepresentation(String.valueOf(signNumber),(eLength/4+1)*4),(eLength/4+1)*4).substring((eLength/4+1)*4-eLength+1,(eLength/4+1)*4+1);
		mb=integerMultiplication("01"+sX,"01"+sY,sLength*3).substring(sLength-1,sLength*2+1);
		if(mb.charAt(0)=='1'){
			eb=oneAdder(eb).substring(1,eb.length()+1);
			mb=mb.substring(1,1+sLength);
		}else {
			mb=mb.substring(2,2+sLength);
		}

		if(eb.equals(overflow)||(eX.charAt(0)=='1'&&eY.charAt(0)=='1'&&eb.charAt(0)=='0')){
			over=1;
			eb=overflow;
		}

		if(eb.equals(overflow)){
			mb=String.valueOf(special0);
		}


		return over+String.valueOf(sign)+eb+mb;
	}
	
	/**
	 * �������������ɵ���{@link #integerDivision(String, String, int) integerDivision}�ȷ���ʵ�֡�<br/>
	 * ����floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ĳ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatDivision (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.

		int sign1=Integer.parseInt(operand1.substring(0,1));
		int sign2=Integer.parseInt(operand2.substring(0,1));
		int sign=1;
		int over=0;
		if(sign1==sign2){
			sign=0;
		}
		char[] specialChar=new char[eLength];
		Arrays.fill(specialChar,'0');
		String special=String.valueOf(specialChar);
		Arrays.fill(specialChar,'1');
		String overflow=String.valueOf(specialChar);

		char[] special0=new char[sLength];
		Arrays.fill(special0,'0');
		if(operand1.substring(1,operand1.length()).equals(special+String.valueOf(special0))){
			return "0"+"0"+operand1.substring(1,operand1.length());
		}else if(operand2.substring(1,operand2.length()).equals(special+String.valueOf(special0))){
			return "0"+sign+overflow+String.valueOf(special0);
		}

		String eX=operand1.substring(1,1+eLength);
		String eY=operand2.substring(1,1+eLength);
		String etmp=new String();
		String eb=new String();

		int signNumber=(1<<(eLength-1))-1;
		String sX=operand1.substring(1+eLength,1+eLength+sLength);
		String sY=operand2.substring(1+eLength,1+eLength+sLength);
		String mb=new String();
		char[] tmp=new char[sLength*2];
		Arrays.fill(tmp,'0');

		etmp=integerSubtraction("0"+eX,"0"+eY,(eLength/4+1)*4).substring((eLength/4+1)*4-eLength+1,(eLength/4+1)*4+1);
		eb=integerAddition("0"+etmp,integerRepresentation(String.valueOf(signNumber),(eLength/4+1)*4),(eLength/4+1)*4).substring((eLength/4+1)*4-eLength,(eLength/4+1)*4+1);
		mb=integerDivision("01"+sX+String.valueOf(tmp),"01"+sY,sLength*4);
		System.out.println(mb);
		mb=mb.substring(sLength*2,2+sLength*3);

		if(eb.charAt(0)=='1'&&Integer.parseInt("0"+etmp,2)<signNumber+2||Integer.parseInt(eb,2)<=(1<<eLength)-1&&Integer.parseInt(eX,2)<Integer.parseInt(eY,2)){
			char[] tmp0=new char[signNumber+2-Integer.parseInt(etmp,2)];
			Arrays.fill(tmp0,'0');
			mb=String.valueOf(tmp0)+mb;
			eb=special;
		}else if(eb.substring(1,eLength+1).equals(special)){
			mb=mb.substring(0,mb.length());
		}else if(mb.charAt(0)=='0'){
			eb=eb.substring(eb.length()-eLength);
			eb=integerSubtraction(eb,"01",eLength).substring(1,eb.length()+1);
			if (eb.equals(special)){
				mb=mb.substring(1,mb.length());
			}else {
				mb=mb.substring(2,mb.length());
			}
		}else {
			mb = mb.substring(1, mb.length());
		}
		eb=eb.substring(eb.length()-eLength);

		if(eb.equals(overflow)||(eX.charAt(0)=='1'&&eY.charAt(0)=='0'&&eb.charAt(0)=='0'&&!eb.substring(1).equals(overflow.substring(1)))){
			over=1;
			eb=overflow;
			mb=String.valueOf(special0);
		}

		while (mb.length()<sLength){
			mb+="0";
		}
		mb=mb.substring(0,sLength);

		return over+String.valueOf(sign)+eb.substring(eb.length()-eLength)+mb;
	}
}
