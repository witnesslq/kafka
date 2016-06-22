package unicom.file;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.zip.GZIPOutputStream;

import unicom.diction.Diction;



public class CFile<T> {
	File f=null;
	String createtime;//文件建立的时间
	long create;
	public File getF() {
		return f;
	}
	public void setF(File f) {
		this.f = f;
	}
	FileWriter fw=null;
	BufferedWriter bf=null;
	SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmss",Locale.CHINESE);
	/*
	 * 获取当前时间
	 * */
	public String generateTime()
	{
		
		Date  da=new Date();
	  	String st=df.format(da).toString();
	  	create=da.getTime();
	  	return st;	
	}
	/*
	 * 新创建文件
	 * 文件夹+线程名称+创建时间+后缀
	 * 例如 e:\\test\\Thread-17-20160407100640.txt
	 * */
	public CFile(String path) 
	{
		super();
		this.createtime=null;
	
		StringBuffer dataSb=new StringBuffer();
		dataSb.append(path);
		this.createtime=generateTime();//记录创建时间
		dataSb.append(this.createtime);
		dataSb.append(Diction.fileSuffix);
		String name=dataSb.toString();
		if(null!=name)
		{
			this.f = new File(name);
			if(f.exists()) 
			{
				//f.delete();
			}
			else 
			{
				try {
					f.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else
		{
			System.out.println("file name ==null");
		}
		
		
	}
	
	public CFile() {
		super();
	}
	/*
	 * 打开文件进行输入
	 * */
	public void open()
	{
		try 
		{
			fw=new FileWriter(f,true);//默认是追加模式
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bf=new BufferedWriter(fw);
	}
	/*
	 * 判断文件是否达到可以切割的大小
	 * 
	 * */
	public boolean isFull()
	{
		boolean isFull=false;//判断文件是否达到3Mb
		long size=this.f.length();
		if(Diction.fileSize<size)
		{
			isFull=true;//文件已经大于 3mb
		}
		
		return isFull;
	}
	/*
	 * 判断文件是否到达发送时间
	 * 
	 * */
	public boolean isToSendDate()
	{
		boolean isUpTodate=false;//判断文件是否达到发送的时间
		Date  da=new Date();
	  	//String st=df.format(da).toString();
		long nowtime=da.getTime();
//		Date create=null;
//		try {
//			create=df.parse(this.createtime);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		long diff=nowtime-create;
		long diffmin=diff/(1000*60);
		if(diffmin>Diction.diffTime)
		{
			isUpTodate=true;
		}
		
		
		return isUpTodate;
	}
	
	/**
	 * 文件输入结束之后进行关闭
	 * */
	public void close()
	{
		try {
			bf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * 标注正在写文件的文件名称
	 * */
	public File renameFile()
	{
		File targetFile1=null;
		if(f.exists())
		{
			try 
			{
				 String path=f.getCanonicalPath();
				 targetFile1 = new File(path+"reading");
				 f.renameTo(targetFile1);
				 f=targetFile1;
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return targetFile1;
	}
	/*
	 * 将切割的文件移动到move文件夹
	 * @param moveDir 是要移动到的文件夹的路径
	 * 例如  e:\\move\\
	 * 
	 * */
	public boolean moveFile(String moveDir)
	{
		boolean flag=false;
		File targetFile1=null;
		int len=0;
		int sublen=0;
		if(f.exists())
		{
			
				 String name=f.getName();
				 len=name.length();
				 sublen=len-7;
				 if(sublen>0)
				 {
					 name=name.substring(0, sublen);
				 }
				 else
				 {
					 flag=false;
					 return flag;
				 }
				 String tagetname=moveDir+name;//要移动的目标地址
				 targetFile1 = new File(tagetname);
				 if(targetFile1.exists()) 
				 {
					 targetFile1.delete();
				 }
				 if(f.renameTo(targetFile1))
				 {
					 if(targetFile1.isFile())flag=true;
				 }
			
			
		}
		return flag;
	}
	public void makeFile(String fname) throws IOException
	{
		if(f==null)
		f=new File(fname);
		f.createNewFile();
		
	}
	public void writeOneRow(String str) throws IOException
	{
		
		bf.write(str);
		
	}
	//写出 压缩文件
	public void writeOneRowToGz(String str) throws IOException
	{

		byte[] b=str.getBytes();
		BufferedOutputStream bf = new BufferedOutputStream( new GZIPOutputStream( new FileOutputStream(f)));
		bf.write(b);
		bf.close();
		
	}
	public void writeRow(String str) throws IOException
	{
		
		bf.write(str+"\r\n");
		
	}
	public void writeCountRow(long i,String str) throws IOException
	{
		
		
		bf.write(str+","+i+","+"\r\n");
		
	}
	public void writeCountFile(long i,String str,String b) throws IOException
	{
		
		
		bf.write(str+","+i+","+b+","+"\r\n");
		
	}
	public void writeHead(String str) throws IOException
	{
		
		bf.write(str+","+"\r\n");
		
	}
	
	//函数开始，结束，
	
	
	
	public void initCsv() throws IOException
	{
	
	
		bf.write("Total Lines"+","+"Valid Lines"+","+"Patts Lines"+","+"File Hash"+","+"Key Hash"+","+"FunHash"+","+"Filename"+","+"Path"+"\r\n");
		
	}
	public void initFunCsv() 
	{
		FileWriter fw=null;
		try {
			fw = new FileWriter(f,true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter bf=new BufferedWriter(fw);
		try {
			bf.write("Filename"+","+"Start"+","+"End"+","+"FunMd5"+","+"Path"+"\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//		public void writeFileMap( Map<String,List<String>> map)
//		{
//			long c=0;
//			try {
//				writeHead("path,row,txt");
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			if(map!=null) 
//			{
//				for ( String key : map.keySet()) 
//				{
//					List<String> lf=map.get(key);
//					for(String s:lf)
//					{
//						try {
//							c++;
//							ReadFileStartLex startlex=new ReadFileStartLex(s);
//							 startlex.readFileContent();
//							 boolean b=startlex.isTxt();
//							 String is="不是";
//							 if(b) is="是";
//							writeCountFile(c,s,is);
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				}
//			}
//		}
	
	
	public void writeCsvTotal(long all,long val) throws IOException
	{
		
		bf.write(all+","+val+","+""+","+""+","+""+","+""+","+""+","+""+"\r\n");
		
	}
	public static void main(String args[])
	{
		String path="e:\\test\\";
		CFile file=new CFile(path);
	}
}
