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
		
>>>>>>> f58b31272fc9771f99ca2d91f800f6b8652c1f98
		dictionary.load_dictionary();
		System.out.println(dictionary.getSize());
		System.out.println(dictionary.searchKey("Salm"));
			
/* 	Option 1 : 
 *  
 *  New thread per connection
 *  	- define max number of threads
 *  		Creating a thread in Java is an expensive operation. 
 *  		And if you start creating new thread instance every time to execute a task
 *  		application performance will degrade surely
 */

		int MAX_THREADS = 5;
		int thread_count = 0;
		while(true) {
			
			server_socket.accept_connections();
			
			if(thread_count < MAX_THREADS) {
				thread_count++;
				
				Runnable thr = new serviceThread(server_socket);			
				thr.run();
				
			}
			else
			{
				break;
			}
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
