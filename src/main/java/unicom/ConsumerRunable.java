package unicom;

import java.io.IOException;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import unicom.file.CFile;

public class ConsumerRunable implements Runnable{
	KafkaStream<byte[], byte[]> stream;
	CFile file=null;
	String path="e:\\test\\";
	
	public ConsumerRunable(KafkaStream<byte[], byte[]> outstream) {
		super();
		this.stream = outstream;
	}

	public void run() {
		// TODO Auto-generated method stub
	
		file = new CFile(path);
		// TODO Auto-generated method stub
		ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
		while (iterator.hasNext()) {

			String message = new String(iterator.next().message());
			System.out.println("消费:--- " + message);
			try {
				file.open();
				file.writeRow(message);
				file.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
