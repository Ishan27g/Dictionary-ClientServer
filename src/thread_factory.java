import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class thread_factory implements ThreadFactory {
   String name;
   AtomicInteger threadNo = new AtomicInteger(0);

   public thread_factory (String name){
       this.name = name;
   }
   
   public Thread newThread(Runnable r) {
	   
     String threadName = name + " : " + threadNo.incrementAndGet();
     return new Thread(r,threadName );
   }
}