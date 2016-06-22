package unicom.diction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import unicom.bitset.BitSetL;
import unicom.bitset.MobileBitSet;
import unicom.bitset.NumBitSet;



public class Diction {
	//public static String moveDir="e:\\move\\";//切割之后移动的文件夹
	//public static String createDir="e:\\test\\";//生成文件的文件夹
	public static String moveDir="/home/wwb/move/";//切割之后移动的文件夹
	public static String createDir="/home/wwb/create/";//生成文件的文件夹
	public static long fileSize=3*1024;//3kb
	public static String fileSuffix=".txt";//生成的文件后缀
	public static int num_thread=10;//每个消费者起几个线程
	public static String topic="remain";
	public static String zookeeper=null;
	public static String broker=null;
	public static int diffTime=2;//2minute
	public static String encoding=null;
	public static Map<String,String> provinceHashMap=new HashMap<String,String>();
	public static Map<String,Long> offsetHashMap=new HashMap<String,Long>();
	//public static MobileBitSet mobileBitSet=new MobileBitSet();//存放为 1和为2的 情况
	public static NumBitSet numBitSet=new NumBitSet();//存放为 1和为2的 情况
	public static String mobilepath=null;
	public static String debug="1";//默认开启测试模式
	public static ReadWriteLock lock = new ReentrantReadWriteLock();

	public static final Lock readLock = lock.readLock();
	public static final Lock writeLock = lock.writeLock();
	static
	{
		//initSGWDic(sgwdic);
		readTag("province");
		//loadFromFile(mobilepath);
		
	}
	//初始化省份 topic和阈值
	public static void readTag(String name)
	{
		InputStream in;
		BufferedReader bf;
		in=Diction.class.getResourceAsStream("/"+name+".txt");
		bf=new BufferedReader(new InputStreamReader(in));
		String line=null;
		String provinceCode=null;
		String provinceName=null;
		String provinceValue=null;
		try {
			for(;(line=bf.readLine())!=null;)
			{
				String []tag=line.split("\\|");
				provinceName=tag[0];
				provinceCode=tag[1]+"_00";
				
				provinceValue=tag[2];
				provinceHashMap.put(provinceCode,provinceValue);
				provinceCode=null;
				provinceName=null;
				provinceValue=null;
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		
		

}
