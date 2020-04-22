
/**
 * @author Ishan Goyal - 1056051 
 * This class defines the Server for the dictionary
 * It invokes various worker threads to deal with concurrent clients
 * 
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DictionaryServer {
	
	
	private static final boolean Enable_Th_pool = true;
	static final int MAX_CONCURRENT_THREADS = 5;
	static ExecutorService pool;
	static ServerSocket server_socket ;
	static boolean server_up = false;
	static DictionaryData dictionary;

	public static void main(String[] args) throws IOException{

		dictionary = new DictionaryData("../dictionary.csv");

		if (args.length != 1) {
			System.out.println("Correct command is  java –jar DictionaryServer.jar <port> ");
			System.exit(0);
		}
		int port = Integer.parseInt(args[0]);
			//Create a socket for clients to connect, then read dictionary data
		try{
			server_socket = new ServerSocket(port);
			server_up = true;
		}
		catch(IOException e){
			System.out.println("Unable to create ServerSocket");
			System.exit(0);
		}
		//This is a shutdown hook, closes the socket, shuts down the thread pool 
		// and write final data to csv file 
		Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run()
            {
            System.out.println("\nShutting down server");
            server_up = false;
            if(server_socket.isClosed() == false){
            try {
            server_socket.close();
            } catch (IOException e) {
            System.out.println("Error shutting down server");
            }
            }
            if(Enable_Th_pool) {
            if(pool.isShutdown() == false){
            pool.shutdown();
            }
            }
				int entries_saved = dictionary.save_file("../dictionary.csv");
				System.out.println("saved dictionary with entries : " + entries_saved);
				System.exit(0);
            };
        });
		
		System.out.println("Loaded dictionary with entries : " + dictionary.getSize());			
/* 	
 *	Option 1 :  
 *  SERVER - New thread per CLIENT, Up to MAX_CONCURRENT_THREADS clients can be concurrently handled
 * 	NOT SUITABLE
 */	
        int thread_count = 0;
		 if(!Enable_Th_pool) {
				System.out.println("Creating new thread per connection, max concurrent connections = " 
					+ MAX_CONCURRENT_THREADS);
				while(server_up) {
					String thread_name = new String("Thread ");
					if(Thread.activeCount() < MAX_CONCURRENT_THREADS + 1) { //+1 to accomodate shutdown thread
						
						MessageStream server = new MessageStream();
						server.setServerSocket(server_socket);
						Runnable thr = new serviceThread(server, dictionary);
						server.accept_connections();
						
						thread_count++;
						thread_name = thread_name + thread_count;
						
						Thread th = new Thread(thr);
						th.setName(thread_name);
						th.start();
					}
				}
	        }
 /* 
 *  Option 2 :
 *  SERVER - Create a fixed size thread pool
 * */ 
        else {
        	
			thread_factory thr_fact = new thread_factory("Threadpool 1 : Thread Id ");
			System.out.println("Creating new Fixed Thread pool to deal with max concurrent connections = " 
				+ MAX_CONCURRENT_THREADS);
			
			pool = Executors.newFixedThreadPool(MAX_CONCURRENT_THREADS, thr_fact);
        	
        	while(server_up) {
				MessageStream server = new MessageStream();
				server.setServerSocket(server_socket);
			
				server.accept_connections();
				
				Runnable server_instance = new serviceThread(server, dictionary);
				pool.execute(server_instance);
    		}
		}		
		if(server_socket.isClosed() == false){
			try {
				server_socket.close();
			} catch (IOException e) {
				System.out.println("Error shutting down server");
			}
		}
	}
}

