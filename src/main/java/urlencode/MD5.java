package urlencode;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
/*
 * 
 * 正式线上使用的MD5算法
 * */
public class MD5 {

	/**
	 * @param 和 linux md5sum一样功能的md5
	 */
	 public static String md5Hex(String data){
	        return DigestUtils.md5Hex(data);
	    }
	 /**
	     * Base64 encode
	     * */
	    public static String base64Encode(String data){
	        return Base64.encodeBase64String(data.getBytes());
	    }
	     
	    /**
	     * Base64 decode
	     * @throws UnsupportedEncodingException 
	     * */
	    public static String base64Decode(String data) throws UnsupportedEncodingException{
	        return new String(Base64.decodeBase64(data.getBytes()),"utf-8");
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s=md5Hex("hello");
		System.out.println(s);

	}

}
