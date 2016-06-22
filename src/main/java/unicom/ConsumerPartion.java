package unicom;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import scala.collection.mutable.SynchronizedBuffer;
import unicom.diction.Diction;
import unicom.file.CFile;
import unicom.http.HttpClient;
import urlencode.UrlTools;
/*
 *  不重新起一个线程，实现文件的自动切割，实现花旦的自动切分
 * 按照时间和大小切割文件
 * 1、微信url提醒
 * 2、文件持久化
 *
 * */
public class ConsumerPartion  {
	KafkaStream<byte[], byte[]> stream;
	CFile file=null;//接收消息存放文件
	String createDir=Diction.createDir;//开始存放的位置
	String moveDir=Diction.moveDir;//达到切割大小移动的位置
	String t;
	UrlTools urltools=new UrlTools();//计算url类
	String provinceValue=null;
	public ConsumerPartion(KafkaStream<byte[], byte[]> outstream,String topic,String value) {
		super();
		this.stream = outstream;
		t=topic;
		provinceValue=value;
	}
	public void recieve(String threadname)
	{
		//String threadname=this.getName();
		createDir=createDir+threadname+"-"+this.t+"-";
		file = new CFile(createDir);
		ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
		file.renameFile();
		while (iterator.hasNext()) 
		{

			String message = null;
			try {
				message = new String(iterator.next().message(),Diction.encoding);
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//System.out.println("topic "+this.t +" 消费:--- " + message);
			long offset=iterator.kafka$consumer$ConsumerIterator$$consumedOffset();//消息偏移
			//微信提醒
			HttpClient client=new HttpClient();
			String url=urltools.createUrl(message,provinceValue);
    		if(null!=url)
    		{
    			if(Diction.debug.equals("0"))
    			{
    				client.get(url);//生成模式
    				//Diction.offsetHashMap.put(this.t, offset);//记录每个省份的偏移量
    				
    			}
    			else
    			{
    				System.out.println("topic "+this.t +" offset "+offset+"  test url "+ url);//测试模式
    			}
    		}
    		//消息持久化 
			try {
					if(file.isFull())
					{
						if(file.moveFile(moveDir))
						{
							file=new CFile(createDir);
							file.renameFile();
						}
					}
					else//文件没有达到切分大小，判断时间
					{
						file.open();
						file.writeRow(message);
						file.close();
						if(file.isToSendDate())
						{
							if(file.moveFile(moveDir))
							{
								file=new CFile(createDir);
								file.renameFile();
							}
						}
						
					}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}
	}

}
