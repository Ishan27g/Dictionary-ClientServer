import java.awt.EventQueue;
import java.io.IOException;
import java.net.UnknownHostException;

public class client {

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
 *
 * Option 1 : 
 * CLIENT - Establishes connection as soon as GUI is launched, irrespective of data exchange
*/

		System.out.println("Client connecting to " + ip + ":" + port);
		final MessageStream_client c = new MessageStream_client(ip, port);
		
		//take ip input and pass to gui instead of c.
		//create c in gui

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI window = new ClientGUI(c);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
		
		
}


/*
 * Dependency - A - - - > B : A depends on B, or A uses B 
 * Association - A -------> B : to represent something like a field in a class
 *  
 */
