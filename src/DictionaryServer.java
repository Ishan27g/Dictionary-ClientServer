/**
 * 
 */

import java.io.IOException;


/**
 * @author ishan
 *
 */
public class DictionaryServer {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		
		//Create a socket for clients to connect, then read dictionary data
		
		MessageStream server_socket = new MessageStream();
		
		DictionaryData dictionary = new DictionaryData("../dictionary.csv");
		//DictionaryData dictionary = new DictionaryData("/Users/ishan/Downloads/dictionary.csv");
		
		dictionary.load_dictionary();

			
/* 	Option 1 : 
 *  
 *  SERVER - New thread per CLIENT/CONNECTION, Up to MAX_CONCURRENT_THREADS clients can be concurrently handled
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
		
		while(true) {
		
			if(Thread.activeCount() < MAX_CONCURRENT_THREADS) {
				
				System.out.println("Active threads: " + Thread.activeCount());
				
				System.out.println("Starting thread " + thread_count);
				thread_count++;
				
				try {
					server_socket.accept_connections();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				Runnable thr = new serviceThread(server_socket, dictionary);
				Thread th = new Thread(thr);
				th.start();
				
			}
		}
		

 /* 
 *  Option 2 :
 *  
 *  SERVER - New thread per CLIENT/CONNECTION, Up to MAX_CONCURRENT_THREADS clients can be concurrently handled *  
 *  Create a fixed size thread pool, and a fixed size task queue
 *  	- Single pool and one time definition of task queue size.
 *  	- Once filled, no further requests can be processed
 *  
 * */
		

	}

}
