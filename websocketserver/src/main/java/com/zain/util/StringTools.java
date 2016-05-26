package com.zain.util;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class StringTools {
	
	private static final Logger logger = Logger.getLogger(StringTools.class);

	public static String getNowDate() {
		return getNow("yyyy-MM-dd");
	}

	public static String getNow() {
		return getNow("yyyy-MM-dd HH:mm:ss");
	}

	public static String getNow(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		return sdf.format(new java.util.Date());
	}

	public static Date str2Date(String dateStr) {
		if (StringUtils.isBlank(dateStr)) {
			return null;
		}

		SimpleDateFormat sdf = null;
		if(dateStr.length()==10 || dateStr.length()==9) {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		} else {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}

		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			logger.error("日期字符串解析失败。dateStr : " + dateStr, e);
		}
		
		return null;
	}
	
	 /**
     * 把毫秒转化成日期
	 * @param <T>
     * @param dateFormat(日期格式，例如：MM/ dd/yyyy HH:mm:ss)
     * @param millSec(毫秒数 ,格式为String或Long)
     * @return
     */
	public static <T> String millSecToDate(String dateFormat, T millSec) {
		if(null == dateFormat || StringUtils.isBlank(dateFormat)) {
			dateFormat = "yyyy-MM-dd HH:mm:ss";
		}
		Long mill = null;
		if(null != millSec && millSec instanceof String){
			mill = Long.parseLong((String) millSec);
		}else if(millSec instanceof Long){
			mill = (Long) millSec;
		}else {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = new Date(mill);
		return sdf.format(date);
	}

	public static Timestamp str2Timestamp(String dateStr) {
		if (StringUtils.isBlank(dateStr)) {
			return null;
		}
		try{
			return Timestamp.valueOf(dateStr.trim()); 
		}catch(Exception e){
			logger.error("日期字符串解析失败。", e);
			return null;
		}
	}
	public static String nvl(Object obj) {
		return StringTools.nvl(obj, "");
	}
	
	public static String nvl(Object obj, String defaultStr) {
		if (null == obj) {
			return defaultStr;
		}
		
		String str = obj.toString();;

		return StringUtils.isBlank(str) ? defaultStr.trim() : str;
	}

	public static long obj2Long(Object obj) {
		String str = StringTools.nvl(obj);

		if (!StringUtils.isBlank(str) && StringUtils.isNumeric(str)) {
			return Long.parseLong(str);
		}

		return 0L;
	}
	
	public static long obj2Long(Object obj,long defaultVal) {
		String str = StringTools.nvl(obj);

		if (!StringUtils.isBlank(str) && StringUtils.isNumeric(str)) {
			return Long.parseLong(str);
		}

		return defaultVal;
	}

	public static long str2Long(String str) {
		return StringTools.obj2Long(str);
	}
	
	public static long getLong(Map<String, Object> map, String key) {
		if (null == map || map.isEmpty()) {
			return 0L;
		}

		return StringTools.obj2Long(map.get(key));
	}

	public static int obj2Int(Object obj) {
		String str = StringTools.nvl(obj);
		
		if(StringUtils.isBlank(str)) {
			return 0;
		}

		if (StringUtils.isNumeric(str)) {
			return Integer.parseInt(str);
		}

		return 0;
	}
	
	public static int str2Int(String str) {
		return StringTools.obj2Int(str);
	}
	
	public static String getStr(Map<String, Object> map, String key, String defaultStr) {
		if (null == map || map.isEmpty()) {
			return defaultStr;
		}

		return StringTools.nvl(map.get(key), defaultStr);
	}
	
	public static String getStr(Map<String, Object> map, String key) {

		return StringTools.getStr(map, key, "");
	}
	
	public static String removeBOM(String str) {
		byte[] newByte = null;
		try {
			newByte = StringTools.removeBOM(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String rtnStr = new String(newByte);
		
		logger.info("rtnStr: " + rtnStr);
		
		return rtnStr;
	}

	/**
	 * 功能说明：去掉BOM头。
	 * @param bt
	 * @return
	 */
	public static byte[] removeBOM(byte[] bt) {
		if(null==bt || bt.length<3) {
			return bt;
		}
		
		//EF、BB、BF
		if (bt[0] == -17 && bt[1] == -69 && bt[2] == -65) {
			byte[] nbt = new byte[bt.length - 3];
			System.arraycopy(bt, 3, nbt, 0, nbt.length);
			return nbt;
		} else {
			return bt;
		}
	}
	
	/**将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String byte2HexStr(byte[] buf) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < buf.length; i++) {
                    String hex = Integer.toHexString(buf[i] & 0xFF);
                    if (hex.length() == 1) {
                            hex = '0' + hex;
                    }
                    sb.append(hex);
            }
            return sb.toString();
    }
    
    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] hexStr2Byte(String hexStr) {
            if (hexStr.length() < 1)
                    return null;
            byte[] result = new byte[hexStr.length()/2];
            for (int i = 0;i< hexStr.length()/2; i++) {
                    int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
                    int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
                    result[i] = (byte) (high * 16 + low);
            }
            return result;
    }
    
    
    /**
     * 判断字符串是否全是数字
     * @param str
     * @return
     */
    public static boolean isNumber(String str){
    	if(null == str || StringUtils.isBlank(str)){
    		return false;
    	}
    	Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
    }
    
    /**
     * 将多个string合并为一个字符串,并添加一个5位随机数
     * @param div
     * @param strs
     * @return
     */
    public static String packString(String div,String...strs){
    	StringBuffer sb = new StringBuffer();
    	for(String str:strs){
    		sb.append(str).append(div);
    	}
		return sb.append(genRandomNum(5)).toString().toUpperCase();
    }
    /**
     * 生成指定长度的随机数
     * @param len
     * @return
     */
	public static String genRandomNum(int len) {
		String result = "";
		for (int i = 0; i < len; i++) {
			result += (int)(Math.random() * 10);
		}
		return result;
	}
	
	/**
	 * 功能说明 : 生成指定长度的随机字符串
	 * @param length
	 * @return
	 */
	public static String genRandomStr(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	/**
	 * 功能说明：是否为手机号。
	 * @param phoneNo
	 * @return
	 */
	public static boolean isPhoneNo(String phoneNo) {
		if(StringUtils.isBlank(phoneNo) || !StringUtils.isNumeric(phoneNo)) {
			return false;
		}
		
		if(phoneNo.startsWith("1") && phoneNo.length()==11) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 功能说明：是否为email。
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		
		if(StringUtils.isBlank(email)) { //空字符串，视为合法
			return true;
		}
		
		if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
            return false;
        }
		
		return true;
	}
	
	public static boolean isDateTime(String dateTimeStr) {
		if(StringUtils.isBlank(dateTimeStr)) {
			return false;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			sdf.parse(dateTimeStr);
			return true;
		} catch (ParseException e) {
			logger.error("日期字符串解析失败。dateTimeStr : " + dateTimeStr, e);
		}
		
		return false;
	}
	
}
