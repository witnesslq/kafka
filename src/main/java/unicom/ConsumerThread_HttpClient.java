package unicom;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import unicom.diction.Diction;
import unicom.file.CFile;
import unicom.http.HttpClient;
import urlencode.UrlTools;
/*
 * 每个consumer进程的处理线程启动类，实现实时的http方式的数据传输接口
 * 
 * */
public class ConsumerThread_HttpClient extends Thread {
	KafkaStream<byte[], byte[]> stream;

	UrlTools urltools=new UrlTools();//计算url类
	String t;
	String provinceValue=null;
	public ConsumerThread_HttpClient(KafkaStream<byte[], byte[]> outstream,String topic,String value) {
		super();
		this.stream = outstream;
		t=topic;
		provinceValue=value;
	}
	public void run()
	{
		
		ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
		while (iterator.hasNext()) 
		{

			String message = null;
			try {
				message = new String(iterator.next().message(),Diction.encoding);//gbk
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("消费:--- " + message);
			
			HttpClient client=new HttpClient();
			String url=urltools.createUrl(message,provinceValue);
    		if(null!=url)
    		{
    			client.get(url);
    		}
			

		}
	}

}
