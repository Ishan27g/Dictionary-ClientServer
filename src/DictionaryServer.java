/**
 * 
 */
import java.io.IOException;
import java.net.*;
import java.util.HashMap;


/**
 * @author ishan
 *
 */
public class DictionaryServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		

		DictionaryData dictionary = new DictionaryData("/Users/ishan/Downloads/dictionary.csv");
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
		 * 
		 *  Option 2 :
		 *  
		 *  Create a fixed size thread pool, and a fixed size task queue
		 *  	- Single pool and one time definition of task queue size.
		 *  	- Once filled, no further requests can be processed
		 *  
		 * */
		

	}

}
