package urlencode;

import java.net.URLDecoder;
import java.net.URLEncoder;



public class URL {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String param="accessToken=ad39b7dbd59b87cda827223c0e520d6f&appOAuthID=700042973&charset=gbk&format=xml&randomValue=58095&sellerUin=1280863473&timeStamp=1344568374452&uin=1280863473";
		String paramtest="authkey=hdUMwmU4P56Qt2pC&charset=utf8&format=xml&random=58095&sp=chinaunion&timestamp=1461933168";
		String data="2016-06-21 15:21";
		String paramurlencode=URLEncoder.encode(data);
		//String md5=MD5.makeMD5("authkey%3DhdUMwmU4P56Qt2pC%26charset%3Dutf8%26format%3Dxml%26random%3D58095%26sp%3Dchinaunion%26timestamp%3D1461933168");
		//String md51=MD5.makeMD5(paramurlencode);
//		System.out.println(paramurlencode);
//		System.out.println(md5);
//		System.out.println(md51);
//		if(md5.equals(md51))
//		{
//			System.out.println("right");
//		}
		String paramurldecode=URLDecoder.decode(data);
		System.out.println(paramurlencode);
	}

}
