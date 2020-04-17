/**
 * 
 */

import java.io.IOException;
import java.net.ServerSocket;


/**
 * @author ishan
 *
 */
public class DictionaryServer {
	
	/**
	 * @param args
	 */
	
	
	static ServerSocket server_socket ;
	public static void main(String[] args) throws IOException{
		
		//Create a socket for clients to connect, then read dictionary data
		server_socket = new ServerSocket(9999);

		
		DictionaryData dictionary = new DictionaryData("../dictionary.csv");
		//DictionaryData dictionary = new DictionaryData("/Users/ishan/Downloads/dictionary.csv");
		
		dictionary.load_dictionary();

			
		
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

		int MAX_CONCURRENT_THREADS = 3;
		int thread_count = 0;
		
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
               
            };
        });
		
		while(true) {
		
			if(Thread.activeCount() < MAX_CONCURRENT_THREADS) {
				
				System.out.println("Active threads: " + Thread.activeCount());
				

				MessageStream server = new MessageStream();
				Runnable thr = new serviceThread(server, dictionary);
				Thread th = new Thread(thr);

				try {
					server.accept_connections(server_socket);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				thread_count++;
				
				System.out.println("Starting thread " + th.getId());
				th.start();
				
			}
		}
		

 /* 
 *  Option 2 :
 *  
 *  SERVER - Create a fixed size thread pool, and a fixed size task queue. Up to MAX_CONCURRENT_THREADS clients can be concurrently handled 
 *    
 *  Create a fixed size thread pool, and a fixed size task queue
 *  	- Single pool and one time definition of task queue size.
 *  	- Once filled, no further requests can be processed
 *  
 * */
		

	}

}
