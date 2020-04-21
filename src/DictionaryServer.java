/**
 * 
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author ishan
 *
 */
public class DictionaryServer {
	
	
	private static final boolean Enable_Th_pool = true;
	static final int MAX_CONCURRENT_THREADS = 5;
    
	static ExecutorService pool;
	static ServerSocket server_socket ;
	static boolean server_up = false;
	
	public static void main(String[] args) throws IOException{

		
		//Create a socket for clients to connect, then read dictionary data
		try{
			server_socket = new ServerSocket(9999);
			server_up = true;
		}
		catch(IOException e){
			System.out.println("Unable to create ServerSocket");
			System.exit(0);
		}

		DictionaryData dictionary = new DictionaryData("../dictionary.csv");
		//dictionary.load_dictionary();
		
		Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
				System.out.println("\nShutting down server");
				server_up = false;
				/*if(server_socket.isClosed() == false){
					try {
						server_socket.close();
					} catch (IOException e) {
						System.out.println("Error shutting down server");
					}
				}*/
				if(pool.isShutdown() == false){
					pool.shutdown();
				}
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
				while(server_up) {
					if(Thread.activeCount() < MAX_CONCURRENT_THREADS) {
					//	System.out.println("Current active threads: " + Thread.activeCount());
						
						MessageStream server = new MessageStream();
						server.setServerSocket(server_socket);
						Runnable thr = new serviceThread(server, dictionary);
						//if( == true){
						//	try {
								server.accept_connections();
						//	} catch (IOException e1) {
						//		System.out.println("Error in accepting a connection");
						//	}
					//	}
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
        	
        	while(server_up) {
				MessageStream server = new MessageStream();
				server.setServerSocket(server_socket);
				//if( == true){
					//try {
						server.accept_connections();
				//	} catch (IOException e1) {
				//		System.out.println("Error in accepting a connection");
				//	}
					Runnable server_instance = new serviceThread(server, dictionary);
					pool.execute(server_instance);
				//}
    		}
		}
		
		if(server_socket.isClosed() == false){
			try {
				server_socket.close();
			} catch (IOException e) {
				System.out.println("Error shutting down server");
			}
		}

		/**
		 * write final dictionary data to csv file
		 * 
		 */
	}
}

