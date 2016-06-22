package unicom;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import unicom.diction.Diction;
import unicom.file.CFile;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;




/**
 * 
 * @author wwh
 * 不需要配置文件，从 Diction全局类中读取设置启动
 * 
 *
 */
public class kafkaConsumer extends Thread{

	private String topic;
	private int part;
	private String provinceValue;//默认是50
	public kafkaConsumer(String topic,int p){
		super();
		this.topic = topic;
		this.part=p;
		provinceValue="50";
		
	}
	public kafkaConsumer(String topic,int p,String value){
		super();
		this.topic = topic;
		this.part=p;
		provinceValue=value;
		
	}
	
	
	@Override
	public void run() 
	{
		String threadname=this.getName();
		ConsumerConnector consumer = createConsumer();
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, this.part); // 
		 Map<String, List<KafkaStream<byte[], byte[]>>>  messageStreams = consumer.createMessageStreams(topicCountMap);
		 for(final KafkaStream<byte[], byte[]> stream:messageStreams.get(topic))
		 {
			 //ConsumerThread thread=new ConsumerThread(stream,topic,provinceValue);//用哪个ConsumerThread_HttpClients
			 //thread.start();
			 ConsumerPartion consumerOne=new ConsumerPartion(stream,topic,provinceValue);
			 consumerOne.recieve(threadname);
		 }
			
			 
	}
		 
	

	private ConsumerConnector createConsumer() {
		Properties properties = new Properties();
		properties.put("zookeeper.connect", Diction.zookeeper);//����zk
		properties.put("group.id", "group1");
		properties.put("metadata.broker.list", Diction.broker);// ����kafka broker
		
		return Consumer.createJavaConsumerConnector(new ConsumerConfig(properties));
	 }
	
	
	public static void main(String[] args) {
		//int num_thread=30;
		String path="e:\\test\\";
		String balances="50";
		
		new kafkaConsumer("threetest",Diction.num_thread,balances).start();// ʹ��kafka��Ⱥ�д����õ����� test 
		//new kafkaConsumer("demotest",5).start();// ʹ��kafka��Ⱥ�д����õ����� test 
		
	}
	 
}
