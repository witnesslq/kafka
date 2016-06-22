package unicom.bitset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.BitSet;



public class NumBitSet {

	/**
	 * @param args
	 * 直接使用手机号码
	 */
	BitSet  mobileBitSetIn=new BitSet();//有效手机号码 状态1
	BitSet  mobileBitSetNot=new BitSet();//无效手机号码  状态 2
	MobileShort mobileShort=new MobileShort();
	String path;
	/*
	 * 初始化加载 mobile文件 
	 * 
	 * */
	public boolean loadFromFileQQ(String file)
	{
		System.out.println("load qq mobile");
		boolean flag=false;
		InputStream in=null;
		try {
			in=new FileInputStream(new File(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			in=NumBitSet.class.getResourceAsStream("/"+file);
		}
		BufferedReader reader=new BufferedReader(new InputStreamReader(in));
		String line=null;
		String [] lineArr=null;
		int mobileNum=0;
		String mobileStr=null;
		String openStr=null;
		String shortMobile=null;
		try {
				while((line=reader.readLine())!=null)
				{
					lineArr=line.split("\\|");
					mobileStr=lineArr[0];
					openStr=lineArr[1];
					if(11==mobileStr.length())
					{
						
						shortMobile=mobileShort.getShortMobile(mobileStr);
						if(shortMobile!=null)
						{
							mobileNum=Integer.parseInt(shortMobile);
						}
						if(openStr.equals("1"))//开通
						{
							if(mobileNum!=0) 
							{
								mobileBitSetIn.set(mobileNum);
							}
						}
						else if(openStr.equals("2"))//取消
						{
							if(mobileNum!=0) 
							{
								
								mobileBitSetNot.set(mobileNum);
							}
						}
					}
					
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			flag=true;
		
		return flag;
	}
	/*
	 * 初始化加载 mobile文件 
	 * 
	 * */
	
	
	public boolean geMobileExist(int mobile)
	{
		boolean flag=false;
		if(mobileBitSetIn.get(mobile))
		{
			flag=true;
		}
		return flag;
		
	}
	//被取消 为 true
	public boolean geMobileNotExist(int mobile)
	{
		boolean flag=false;
		if(mobileBitSetNot.get(mobile))
		{
			flag=true;
		}
		return flag;
		
	}
	
	public BitSet getMobileBitSet() {
		return mobileBitSetIn;
	}

	public void setMobileBitSet(BitSet mobileBitSet) {
		this.mobileBitSetIn = mobileBitSet;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NumBitSet bitset=new NumBitSet();
		String path="tc_balanceremind_jtlt_2016-05-19";
		//bitset.setPath(path);
		bitset.loadFromFileQQ(path);
		System.out.println(bitset.geMobileExist(220868084));

	}

}
