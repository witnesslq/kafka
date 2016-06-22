package urlencode;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


import unicom.bitset.MobileBitSet;
import unicom.diction.Diction;

public class UrlTools_back {

	/**
	 * @param args
	 * 
	 * 
	 * 参数顺序  authkey,charset,effectetime,format,random,sp,status,tel,timestamp,
	 */
	
	
	String urlPre="http://chong.qq.com/tws/balanceremind/RemindBalance?";
	//String urlPre="http://61.148.212.67:7406/Mobile/home/get.do?";
	String charset="gbk";
	int random;
	String timestamp;
	String sp ="ltjt";
	String authkey= "ltjtbalanceremind_balanceremind_balanceremind";
	String sign;
	String format="json";
	String tel;
	String status="low_balance";
	//String effectetime;
	
	static int seed=10000;
	static Random r=new Random(seed);
	public int getTimeDiffRandom(int max)
	{
		int arrpos=r.nextInt(max);
		return arrpos;
	}
	/*
	 * 
	 * 系统时间取到秒
	 * **/
	public String getSystemTimeStamp()
	{
		long current=System.currentTimeMillis();
		String ds=String.format("%d", current);
		String dsResult=ds.substring(0, 10);
		
		return dsResult;
	}
	
	
	/*
	 * 生成微信 使用的实时接口 url 
	 * */
	public String createUrl(String message,String balances)
	{
		String url=null;
		if(message.contains("您的可用额度已不足"))//过滤低额提醒短信 
		{
			String urlSeq=null;//字典序参数排列
			//String url=null;
			String mobile=null;
			String shortMobile=null;
			String content=null;
			String effectetime=null;
			String effectetimeparam=null;
			long mobileNum=0;
			//int balances=50;
			if(message!=null)
			{
				String []messageArr=message.split("\\|");
				
				if(9<=messageArr.length)
				{
				
					mobile=messageArr[3];
					
					content=messageArr[6];
					effectetime=messageArr[8];
					if(null!=effectetime)
					{
						effectetimeparam=effectimeChangeParam(effectetime);
						effectetime=effectimeChange(effectetime);
						
					}
					
					try {
						mobileNum=Long.parseLong(mobile);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("error "+message);//捕获异常信息
					}
					Diction.readLock.lock();
					boolean isOpen=false;
					boolean isClose=false;
					try {
						//isOpen=Diction.mobileBitSet.geMobileExist(mobileNum);//开通 
						//isClose=Diction.mobileBitSet.geMobileNotExist(mobileNum);//关闭
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					finally
					{
						Diction.readLock.unlock();
					}
					if(isOpen)//开通
					{
						if(!isClose)//同时没有关闭
						{
							this.random=getTimeDiffRandom(100000);
							this.timestamp=getSystemTimeStamp();
							StringBuilder sdbUrlSeq=new StringBuilder();
							//urlSeq="authkey="+this.authkey+"&effectetime="+effectetime+"&format="+this.format+"&random="+this.random+"&sp="+this.sp+"&status="+this.status+"&tel="+mobile+"&timestamp="+this.timestamp;
							sdbUrlSeq.append("authkey=");
							sdbUrlSeq.append(this.authkey);
							sdbUrlSeq.append("&balances=");
							sdbUrlSeq.append(balances);
							sdbUrlSeq.append("&effectetime=");
							sdbUrlSeq.append(effectetime);
							sdbUrlSeq.append("&format=");
							sdbUrlSeq.append(this.format);
							sdbUrlSeq.append("&random=");
							sdbUrlSeq.append(this.random);
							sdbUrlSeq.append("&sp=");
							sdbUrlSeq.append(this.sp);
							sdbUrlSeq.append("&status=");
							sdbUrlSeq.append(this.status);
							sdbUrlSeq.append("&tel=");
							sdbUrlSeq.append(mobile);
							sdbUrlSeq.append("&timestamp=");
							sdbUrlSeq.append(this.timestamp);
							
							String sign=MD5.md5Hex(URLEncoder.encode(sdbUrlSeq.toString()));
							StringBuilder sdb=new StringBuilder();
							sdb.append(urlPre);
							sdb.append("sign=");
							sdb.append(sign);
							sdb.append("&timestamp=");
							sdb.append(this.timestamp);
							sdb.append("&random=");
							sdb.append(random);
							sdb.append("&sp=");
							sdb.append(this.sp);
							sdb.append("&format=");
							sdb.append(this.format);
							sdb.append("&tel=");
							sdb.append(mobile);
							sdb.append("&status=");
							sdb.append(this.status);
							sdb.append("&effectetime=");
							sdb.append(effectetimeparam);
							sdb.append("&balances=");
							sdb.append(balances);
							url=sdb.toString();
						}
					}
						
					
				}
			}
			mobile=null;
			content=null;
			effectetime=null;
		}
		return url;
	}
	public String effectimeChange(String str)
	{
		
		StringBuilder sbd=new StringBuilder();
		//datestr=str.substring(0, 4)+"-"+str.substring(4, 6)+"-"+str.substring(6, 8)+" "+str.substring(8, 10)+":"+str.substring(10, 12);
		sbd.append(str.substring(0, 4));
		sbd.append("-");
		sbd.append(str.substring(4, 6));
		sbd.append("-");
		sbd.append(str.substring(6, 8));
		sbd.append(" ");
		sbd.append(str.substring(8, 10));
		sbd.append(":");
		sbd.append(str.substring(10, 12));
		str=null;
		return sbd.toString();
	}
	public String effectimeChangeParam(String str)
	{
		//String datestr=null;
		StringBuilder sbd=new StringBuilder();
		//datestr=str.substring(0, 4)+"-"+str.substring(4, 6)+"-"+str.substring(6, 8)+" "+str.substring(8, 10)+":"+str.substring(10, 12);
		sbd.append(str.substring(0, 4));
		sbd.append("-");
		sbd.append(str.substring(4, 6));
		sbd.append("-");
		sbd.append(str.substring(6, 8));
		sbd.append("%20");
		sbd.append(str.substring(8, 10));
		sbd.append(":");
		sbd.append(str.substring(10, 12));
		str=null;
		return sbd.toString();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String message="2016042859226643|00 10|0|18518775040|1114012316040929|00|温馨提示：您的可用额度已不足50.00元，请尽快充值或去营业厅交费，以免影响您的正常使用。用联通手机营业厅，随时随地查流量查话费，充值专享9.85折，点击进入 wap.10010.com |04|20160428094648||||";
		UrlTools_back urltools=new UrlTools_back();
		String balances="50";
		String url=urltools.createUrl(message,balances);
		System.out.println(url);
	}

}
