/**
 * @author Ishan Goyal - 1056051 
 * This class defines the client. It takes commandline input from the user and launches a GUI
 * 
 */

import java.awt.EventQueue;
import java.io.IOException;
import java.net.UnknownHostException;

public class DictionaryClient {

	static private String ip = null;
	static private int port;
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		if (args.length == 2) {
			try {
				ip = new String(args[0]);
				port = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				System.err.println("Argument" + args[0] + " must be an integer.");
				System.exit(1);
			}
		}
		else
		{
        	System.out.println("Proper Usage is: java client.java ip port");
        	System.exit(0);
    	}
		
		/*
		* SERVER - New thread per CLIENT/CONNECTION, Up to MAX_CONCURRENT_THREADS clients can be concurrently handled 
		* CLIENT - Establishes connection as soon as GUI is launched, irrespective of data exchange
		*/

		System.out.println("Client connecting to " + ip + ":" + port);
		final MessageStream_client client = new MessageStream_client(ip, port);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI window = new ClientGUI(client);
					window.frame.setVisible(true);
				} catch (Exception e) {
				}
			}
		});
	}		
}

