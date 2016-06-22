package unicom.basic;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;



/**
 * �������
 * ������: 0
������: 1
������: 2
������: 3
������: 4
������: 5
������: 6
������: 7
������: 8
������: 9
������: 10
������: 11
������: 12
������: 13
������: 14
������: 15
������: 16
������: 17
������: 18
 * @author zm
 *
 */
public class kafkaProducer_test extends Thread{

	private String topic;
	
	public kafkaProducer_test(String topic){
		super();
		this.topic = topic;
	}
	
	
	@Override
	public void run() {
		Producer producer = createProducer();
		int i=1;
		while(true){
			producer.send(new KeyedMessage<String, String>(topic, String.valueOf(i),"18611701625您的余额不足10元 " + i));
			System.out.println("正在发送: " + i);
			try {
				//TimeUnit.MILLISECONDS.sleep(1);
				i++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Producer createProducer() {
		Properties properties = new Properties();
		properties.put("zookeeper.connect", "10.124.3.61:21810,10.124.3.62:21810,10.124.3.63:21810");//����zk
		properties.put("serializer.class", StringEncoder.class.getName());
		properties.put("metadata.broker.list", "10.124.3.61:9092,10.124.3.62:9092,10.124.3.63:9092");// ����kafka broker
		return new Producer<Integer, String>(new ProducerConfig(properties));
	 }
	
	
	public static void main(String[] args) {
		new kafkaProducer_test("test").start();// ʹ��kafka��Ⱥ�д����õ����� test 
		
	}
	 
}
