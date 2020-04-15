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
 *  New thread per request, up to MAX_REQUESTS will be serviced at a time
 *  	- define max number of threads
 *  		Creating a thread in Java is an expensive operation. 
 *  		And if you start creating new thread instance every time to execute a task
 *  		application performance will degrade surely
 */

		int MAX_REQUESTS = 5;
		int thread_count = 0;
		
		try {
			server_socket.accept_connections();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//for(thread_count = 0; thread_count< MAX_REQUESTS; thread_count++) {
		while(thread_count < MAX_REQUESTS) {
			
			System.out.println("Starting thread " + thread_count);
			
			Runnable thr = new serviceThread(server_socket, dictionary);
			thr.run();
		}
		
		
		/*
		while(thread_count < MAX_REQUESTS) {
			try {
				server_socket.accept_connections();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			thr[thread_count] = new serviceThread(server_socket, dictionary);
			thread_count++;
			thr[thread_count-1].run();
			thread_count--;
			server_socket.closeConnection();
		}
		
		*/
		
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
