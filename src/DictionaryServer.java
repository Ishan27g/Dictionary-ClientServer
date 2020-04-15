/**
 * 
 */

import java.io.IOException;


/**
 * @author ishan
 *
 */
public class DictionaryServer {
	static Runnable thr[] = null;

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
 *  New thread per Client, first MAX_CLIENTS will be serviced
 *  	- define max number of threads
 *  		Creating a thread in Java is an expensive operation. 
 *  		And if you start creating new thread instance every time to execute a task
 *  		application performance will degrade surely
 */

		int MAX_CLIENTS = 5;
		int thread_count = 0;
		
		
		for(thread_count = 0; thread_count< MAX_CLIENTS; thread_count++) {
			System.out.println("Starting thread " + thread_count);
			thr[thread_count] = new serviceThread(server_socket, dictionary);
			thr[thread_count].run();
		}
		server_socket.closeConnection();
		
		
		 /* 
		 *  Option 2 :
		 *  
		 *  Create a fixed size thread pool, and a fixed size task queue
		 *  	- Single pool and one time definition of task queue size.
		 *  	- Once filled, no further requests can be processed
		 *  
		 * */
		

	}

}
