package unicom;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import unicom.diction.Diction;
/**
 * @KAFKA客户端的配置文件启动类
 * 
 *		按照省份起启动消费线程数目的启动 类 
 *    topic 为 province.txt中的省份信息
 */



public class StartConsumer {

	/**
	 * @KAFKA客户端的配置文件启动类
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stubInputStream in = new BufferedInputStream(new FileInputStream(path));
		String createdir=null;
		String outputdir=null;
		String movedir=null;
		String threadnum=null;
		String topic=null;
		String zookeeper=null;
		String broker=null;
		String size=null;
		String mobilepath=null;
		String encoding=null;
		String debug=null;
		if (args.length == 0) 
		{
			System.out.println("no configue file");
		}
		else
		{
			InputStream in=null;
			ResourceBundle rb=null;
			try {
				in = new BufferedInputStream(new FileInputStream(args[0]));
				rb = new PropertyResourceBundle(in);
			}
			 catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				in=StartConsumer.class.getResourceAsStream("/"+args[0]);
				try {
					rb = new PropertyResourceBundle(in);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			createdir = rb.getString("createdir");
			movedir=rb.getString("movedir");
			topic = rb.getString("topic");
			threadnum=rb.getString("threadnum");
			zookeeper=rb.getString("zookeeper");
			broker=rb.getString("broker");
			size=rb.getString("size");
			encoding=rb.getString("encoding");
			mobilepath=rb.getString("mobilepath");
			debug=rb.getString("debug");
			System.out.println("createdir"+createdir+" "+"movedir"+" "+movedir+" "+threadnum);
		}

		
		Diction.createDir=createdir;
		Diction.moveDir=movedir;//初始化reading之后移动的目录
		Diction.num_thread=Integer.parseInt(threadnum);
		Diction.topic=topic;
		Diction.zookeeper=zookeeper;
		Diction.broker=broker;
		Diction.fileSize=Integer.parseInt(size)*1024*1024;
		Diction.encoding=encoding;
		Diction.mobilepath=mobilepath;
		//Diction.mobileBitSet.loadFromFileQQ(Diction.mobilepath);//每天晚上需要启动定时任务更新
		Diction.numBitSet.loadFromFileQQ(Diction.mobilepath);//每天晚上需要启动定时任务更新
		Diction.debug=debug;
		int topic_num=0;
		topic_num=Diction.provinceHashMap.size();
		String conumer_topic=null;
		String provinceValue=null;
	
		//按照省份进行topic遍历t
		for(Entry<String, String> entry:Diction.provinceHashMap.entrySet())
		{
			conumer_topic=entry.getKey();
			provinceValue=entry.getValue();
			System.out.println(conumer_topic+" "+provinceValue);
			Diction.offsetHashMap.put(conumer_topic, 0L);
			new kafkaConsumer(conumer_topic,Diction.num_thread,provinceValue).start();//
		}
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		long period=60*60*3;//6小时
		executor.scheduleAtFixedRate(new Runnable() {
				
				public void run() {
					// TODO Auto-generated method stub
					System.out.println("run");
					Diction.writeLock.lock();
					try {
						//Diction.mobileBitSet.loadFromFileQQ(Diction.mobilepath);//每天晚上需要启动定时任务更新
						Diction.numBitSet.loadFromFileQQ(Diction.mobilepath);//每天晚上需要启动定时任务更新
					} catch (Exception e) {
						// TODO: handle exception
						 e.printStackTrace();
					}
					finally 
					{
						Diction.writeLock.unlock();
					}
					
					
				}
			}, 0,period, TimeUnit.SECONDS); //10秒之后执行，每隔1秒执行一次 
	}
	
		
}
