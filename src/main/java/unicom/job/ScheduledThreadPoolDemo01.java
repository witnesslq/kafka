package unicom.job;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//import com.alibaba.rocketmq.common.ThreadFactoryImpl;

public class ScheduledThreadPoolDemo01  
{  


    public static void main(String[] args) throws InterruptedException  
    {  

//    	ScheduledExecutorService scheduledExecutorService = Executors
//    	        .newSingleThreadScheduledExecutor(new ThreadFactoryImpl("BrokerControllerScheduledThread"));
    	ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
        //timer.schedule(task1, 100);  
    	 final long period =  60 ;
        executor.scheduleAtFixedRate(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("run");
				
				
			}
		}, 10,1, TimeUnit.SECONDS);  
          
//        int i=0;  
//        while(true)
//        {
//        	i++;
//        	Thread.sleep(1);
//        	
//        }
    }  
    
}  