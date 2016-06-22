package unicom.basic;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;




/**
 * �������
 * ���յ�: message: 10
���յ�: message: 11
���յ�: message: 12
���յ�: message: 13
���յ�: message: 14
 * @author zm
 *
 */
public class kafkaConsumerResive extends Thread{

	private String topic;
	private int part;
	
	public kafkaConsumerResive(String topic,int p){
		super();
		this.topic = topic;
		this.part=p;
	}
	
	
	@Override
	public void run() {
		ConsumerConnector consumer = createConsumer();
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, this.part); // һ�δ������л�ȡһ�����
		 Map<String, List<KafkaStream<byte[], byte[]>>>  messageStreams = consumer.createMessageStreams(topicCountMap);
		 for(final KafkaStream<byte[], byte[]> stream:messageStreams.get(topic))
		 {
			 //KafkaStream<byte[], byte[]> stream = .get(this.part);// ��ȡÿ�ν��յ���������
			 Thread thread=new Thread(new Runnable() 
			 {
				
				public void run() 
				{
					// TODO Auto-generated method stub
					ConsumerIterator<byte[], byte[]> iterator =  stream.iterator();
					 while(iterator.hasNext())
					 {
						 String message = null;
						try {
							message = new String(iterator.next().message(),"utf-8");
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 System.out.println("消费:--- "+topic+"|"  + message);
					 }
					
				}
				
			});
			 thread.start();
			}
			
			 
		 }
		 
	

	private ConsumerConnector createConsumer() {
		Properties properties = new Properties();
		properties.put("zookeeper.connect", "192.168.138.129:2181");//����zk
		properties.put("group.id", "group1");
		properties.put("metadata.broker.list", "192.168.138.129:9091,192.168.138.130:9091");// ����kafka broker
		
		return Consumer.createJavaConsumerConnector(new ConsumerConfig(properties));
	 }
	
	
	public static void main(String[] args) {
		int num_thread=30;
		for(int i=0;i<10;i++)
		{
			new kafkaConsumerResive(i+"11test",num_thread).start();// ʹ��kafka��Ⱥ�д����õ����� test 
		}
		//new kafkaConsumer("demotest",5).start();// ʹ��kafka��Ⱥ�д����õ����� test 
		
	}
	 
}
