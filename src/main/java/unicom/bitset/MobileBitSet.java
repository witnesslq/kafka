package unicom.bitset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;



public class MobileBitSet {

	/**
	 * @param args
	 * 直接使用手机号码
	 */
	BitSetL  mobileBitSetIn=new BitSetL();//有效手机号码 状态1
	BitSetL  mobileBitSetNot=new BitSetL();//无效手机号码  状态 2
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
		}
		BufferedReader reader=new BufferedReader(new InputStreamReader(in));
		String line=null;
		String [] lineArr=null;
		long mobileNum=0;
		String mobileStr=null;
		String openStr=null;
		try {
				while((line=reader.readLine())!=null)
				{
					lineArr=line.split("\\|");
					mobileStr=lineArr[0];
					openStr=lineArr[1];
					if(11==mobileStr.length())
					{
						mobileNum=Long.parseLong(mobileStr);
						if(openStr.equals("1"))//开通
						{
							mobileBitSetIn.setComp(mobileNum);
						}
						else if(openStr.equals("2"))//取消
						{
							mobileBitSetNot.setComp(mobileNum);
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
	public boolean loadFromFile(String file)
	{
		boolean flag=false;
		InputStream in=null;
		try {
			in=new FileInputStream(new File(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader reader=new BufferedReader(new InputStreamReader(in));
		String line=null;
		long mobilenum=0;
		try {
				while((line=reader.readLine())!=null)
				{
					if(11==line.length())
					{
						mobilenum=Long.parseLong(line);
						mobileBitSetIn.setComp(mobilenum);
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
	
	public boolean geMobileExist(long mobile)
	{
		boolean flag=false;
		if(mobileBitSetIn.getComp(mobile))
		{
			flag=true;
		}
		return flag;
		
	}
	//被取消 为 true
	public boolean geMobileNotExist(long mobile)
	{
		boolean flag=false;
		if(mobileBitSetNot.getComp(mobile))
		{
			flag=true;
		}
		return flag;
		
	}
	
	public BitSetL getMobileBitSet() {
		return mobileBitSetIn;
	}

	public void setMobileBitSet(BitSetL mobileBitSet) {
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
		MobileBitSet bitset=new MobileBitSet();
		String path="E:\\data-file\\SMSCDR_20130901\\mobile.txt";
		//bitset.setPath(path);
		bitset.loadFromFile(path);
		System.out.println(bitset.geMobileExist(18601106193L));

	}

}
