package unicom;
import java.util.Properties;
import java.util.Random;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import unicom.diction.Diction;

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
public class kafkaProducer extends Thread{

	private String topic;
	//
	static int seed=10000;
	static Random r=new Random(seed);
	public kafkaProducer(String topic){
		super();
		this.topic = topic;
	}
	
	public int getTimeDiffRandom(int max)
	{
		int arrpos=r.nextInt(max);
		return arrpos;
	}
	@Override
	public void run() {
		Producer producer = createProducer();
		int i=2;
		//测试号码
		String []num={"18611701625","18601106193","18601106238","18601106375",};
		while(true){
			int random=getTimeDiffRandom(4);
			producer.send(new KeyedMessage<String, String>(topic, String.valueOf(i),"2016042859226643|00 10|0|"+num[random]+"|1114012316040929|00|温馨提示：您的可用额度已不足50.00元，请尽快充值或去营业厅交费，以免影响您的正常使用。用联通手机营业厅，随时随地查流量查话费，充值专享9.85折，点击进入 wap.10010.com |04|20160428094648||||" + i));
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
		properties.put("zookeeper.connect", "192.168.138.129:2181");//����zk
		properties.put("serializer.class", StringEncoder.class.getName());
		properties.put("metadata.broker.list", "192.168.138.129:9091,192.168.138.130:9091");// ����kafka broker
		return new Producer<Integer, String>(new ProducerConfig(properties));
	 }
	
	
	public static void main(String[] args) {
		for(int i=0;i<10;i++)
		{
			new kafkaProducer("wotest").start();// ʹ��kafka��Ⱥ�д����õ����� test
		}
//		String []num={"10","11","13","17","18","30"};
//		for(int i=0;i<6;i++)
//		{
//			new kafkaProducer(num[i]+"_00").start();// ʹ��kafka��Ⱥ�д����õ����� test
//		}
		
//		for(Entry<String, String> entry:Diction.provinceHashMap.entrySet())
//		{
//			String conumer_topic=entry.getKey();
//			String provinceValue=entry.getValue();
//			System.out.println(conumer_topic+" "+provinceValue);
//			new kafkaProducer(conumer_topic).start();//
//		}
		
		
	}
	 
}
