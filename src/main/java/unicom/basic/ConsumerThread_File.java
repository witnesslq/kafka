package unicom.basic;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import unicom.diction.Diction;
import unicom.file.CFile;
/*
 * 每个consumer进程的处理线程启动类，实现文件的自动切割，实现花旦的自动切分
 * 按照时间和大小切割文件
 * 
 * */
public class ConsumerThread_File extends Thread {
	KafkaStream<byte[], byte[]> stream;
	CFile file=null;//接收消息存放文件
	String createDir=Diction.createDir;//开始存放的位置
	String moveDir=Diction.moveDir;//达到切割大小移动的位置
	String t;
	public ConsumerThread_File(KafkaStream<byte[], byte[]> outstream,String topic) {
		super();
		this.stream = outstream;
		t=topic;
	}
	public void run()
	{
		String threadname=this.getName();
		createDir=createDir+threadname+"-"+this.t+"-";
		file = new CFile(createDir);
		// TODO Auto-generated method stub
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
			System.out.println("topic "+this.t +" 消费:--- " + message);
			
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
