package com.softtek.lai.module.laicheng.util;

import java.util.Calendar;
import java.util.Date;

public class MathUtils {
	
	/**
	 * 异或十六进制数据
	 * @param data
	 */
	public static String xorHexString(String data){
		data = data.replaceAll(" ", "");
		int length = data.length() / 2;
		String resource = hexString2binaryString(data.substring(0, 2));
		for(int i=1;i<length;i++){
			resource = xorString(resource, hexString2binaryString(data.substring(i*2, i*2 + 2)));
		}
		return binaryString2hexString(resource);
	}

	 /**
	  * 两个十六进制数据异或
	  * @Description: TODO
	  * @param str1
	  * @param str2
	  * @return
	  * @return: String
	  */
	private  static String xorString(String str1, String str2){
		String data ="";
		for(int i=0;i<str1.length();i++){
			data += String.valueOf(Integer.parseInt(str1.substring(i, i+1)) ^ Integer.parseInt(str2.substring(i, i+1)));
		}
		return data;
	}
	/**
	 * 十六进制转2进制
	 * @Description: TODO
	 * @param hexString
	 * @return
	 * @return: String
	 */
	public static String hexString2binaryString(String hexString) {
		if (hexString == null || hexString.length() % 2 != 0)
			return null;
		String bString = "", tmp;
		for (int i = 0; i < hexString.length(); i++) {
			tmp = "0000"
					+ Integer.toBinaryString(Integer.parseInt(
							hexString.substring(i, i + 1), 16));
			bString += tmp.substring(tmp.length() - 4);
		}
		return bString;
	}

	/**
	 * 二进制转为十六进制
	 * @param bString
	 * @return
	 */
	public static String binaryString2hexString(String bString) {
		if (bString == null || bString.equals("") || bString.length() % 8 != 0)
			return null;
		StringBuffer tmp = new StringBuffer();
		int iTmp = 0;
		for (int i = 0; i < bString.length(); i += 4) {
			iTmp = 0;
			for (int j = 0; j < 4; j++) {
				iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
			}
			tmp.append(Integer.toHexString(iTmp));
		}
		return tmp.toString();
	}
	/**
	 * 16进制转Float类型
	 * 
	 * @param str
	 * @return
	 */
	public static float hexStrToFloat(String str) {
		float result = 0;
		try {
			int temp = Integer.parseInt(str.trim(), 16);
			result = Float.intBitsToFloat(temp);
		} catch (NumberFormatException e) {
//			long ltemp = Integer.parseInt(str.trim(), 16);
			long ltemp = Long.parseLong(str.trim(), 16);
			result = Float.intBitsToFloat((int) ltemp);
		}
		return result;
	}

	/**
	 * 字符串转为16进制
	 * 
	 * @param s
	 * @return
	 */
	public static String toHexString(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return "0x" + str;// 0x表示十六进制
	}

	/**
	 * 转化十六进制编码为字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String toStringHex(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(
						s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "utf-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

	/**
	 * 转化十六进制编码为字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String toStringHex2(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(
						s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "utf-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

	/**
	 * 十六进制转换字符串
	 * 
	 * @param hexStr
	 *            str Byte字符串(Byte之间无分隔符 如:[616C6B])
	 * @return String 对应的字符串
	 */
	public static String hexStr2Str(String hexStr) {
		String str = "0123456789ABCDEF";
		char[] hexs = hexStr.toCharArray();
		byte[] bytes = new byte[hexStr.length() / 2];
		int n;

		for (int i = 0; i < bytes.length; i++) {
			n = str.indexOf(hexs[2 * i]) * 16;
			n += str.indexOf(hexs[2 * i + 1]);
			bytes[i] = (byte) (n & 0xff);
		}
		return new String(bytes);
	}
	
	/**
	 * 根据用户生日计算年龄
	 * @param birthday
	 * @return
	 */
	public static int getAgeByBirthday(Date birthday) {
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthday)) {
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthday);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth 
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth 
				age--;
			}
		}
		return age;
	}
	
	/**
	 * 然后利用Integer.toHexString(int)来转换成16进制字符串。  
	 * @Description: TODO
	 * @param src
	 * @return
	 * @return: String
	 */
	public static String bytesToHexString(byte[] src){
	    StringBuilder stringBuilder = new StringBuilder("");
	    if (src == null || src.length <= 0) {  
	        return null;  
	    }  
	    for (int i = 0; i < src.length; i++) {  
	        int v = src[i] & 0xFF;  
	        String hv = Integer.toHexString(v);
	        if (hv.length() < 2) {  
	            stringBuilder.append(0);  
	        }  
	        stringBuilder.append(hv);  
	    }  
	    return stringBuilder.toString();  
	}  
	
	
	/**
	 * 十六进制转为byte数组
	 * @param hexString
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}
	
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
	
	/**
     * 小端模式解析
     * @param data
     * @return
     */
    public static String calcSmallModeData(String data){
    	StringBuffer stringBuffer = new StringBuffer();
    	if(data != null) {
    		int size = data.length() /2 ;
    		for(int i= size;i> 0;i--){
    			stringBuffer.append(data.substring(i*2 - 2, i* 2));
    		}
    	}
    	return stringBuffer.toString();
    }
}
