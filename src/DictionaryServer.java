/**
 * 
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ishan
 *
 */
public class DictionaryServer {
	
	
	private static final boolean Enable_Th_pool = false;
	static final int MAX_CONCURRENT_THREADS = 5;
    
	static ExecutorService pool;
	static ServerSocket server_socket ;
	
	public static void main(String[] args) throws IOException{

		
		//Create a socket for clients to connect, then read dictionary data
		server_socket = new ServerSocket(9999);

		DictionaryData dictionary = new DictionaryData("../dictionary.csv");
		dictionary.load_dictionary();
		
		Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                System.out.println("Shutdown hook ran!");
                try {
					server_socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
                pool.shutdown();
            };
        });
			
		
/* 	Option 1 : 
 *  
 *  SERVER - New thread per CLIENT, Up to MAX_CONCURRENT_THREADS clients can be concurrently handled
 *  CLIENT - Establishes connection as soon as GUI is launched, irrespective of data exchange
 *  
 *  	- define max number of threads
 *  	- on new connection, increase thread count
 *  	- on exit of thread, reduce thread count
 *  		Creating a thread in Java is an expensive operation. 
 *  		And if you start creating new thread instance every time to execute a task
 *  		application performance will degrade surely
 */
		
       
        String thread_name = new String("[[Thread - ");
        int thread_count = 0;
		 if(!Enable_Th_pool) {
	    		System.out.println("Creating new thread per connection, max concurrent connections = " + MAX_CONCURRENT_THREADS);
				while(true) {
					if(Thread.activeCount() < MAX_CONCURRENT_THREADS) {
						System.out.println("Current active threads: " + Thread.activeCount());
						
						MessageStream server = new MessageStream();
						Runnable thr = new serviceThread(server, dictionary);
		
						try {
							server.accept_connections(server_socket);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						
						thread_count++;
						thread_name = thread_name + thread_count + "]]";
						
						Thread th = new Thread(thr);
						th.setName(thread_name);
						th.start();
						
					}
				}
	        }
 /* 
 *  Option 2 :
 *  
 *  SERVER - Create a cached thread pool, unbounded threads can be spawn
 *    
 *  Create a fixed size thread pool, and a fixed size task queue
 *  	- Single pool and one time definition of task queue size.
 *  	- Once filled, no further requests can be processed
 *  
 * */
		 
		 
        else {
        	
        	thread_factory x = new thread_factory("[[Threadpool : 1-");
        	pool = Executors.newFixedThreadPool(MAX_CONCURRENT_THREADS, x);
        	
        	while(true) {
    				
    			MessageStream server = new MessageStream();
    			try {
    				server.accept_connections(server_socket);
    			} catch (IOException e1) {
    				e1.printStackTrace();
    			}
    			Runnable server_instance = new serviceThread(server, dictionary);
    			pool.execute(server_instance);
    		}
        }
	}
}

