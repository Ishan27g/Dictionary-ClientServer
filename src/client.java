import java.awt.EventQueue;
import java.io.IOException;
import java.net.UnknownHostException;

public class client {

	
	public static void main(String[] args) throws UnknownHostException, IOException {
	
		
/*
 * SERVER - New thread per CLIENT/CONNECTION, Up to MAX_CONCURRENT_THREADS clients can be concurrently handled
 *
 * Option 1 : 
 * CLIENT - Establishes connection as soon as GUI is launched, irrespective of data exchange
*/

		final MessageStream_client c = new MessageStream_client();
		
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
