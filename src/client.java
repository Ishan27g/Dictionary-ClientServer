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
/*
 * SERVER - New thread per CLIENT/CONNECTION, Up to MAX_CONCURRENT_THREADS clients can be concurrently handled
 *
 * Option 2 : 
 * CLIENT - Establishes connection only when data has to be exchanged (not on GUI launch)
*/
	
		//final MessageStream_client c = new MessageStream_client();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI window = new ClientGUI(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
/*
 * SERVER - New thread per CLIENT/CONNECTION, Up to MAX_CONCURRENT_THREADS clients can be concurrently handled
 *
 * Option 2 : 
 * CLIENT - Establishes connection only when data has to be exchanged (not on GUI launch)
*/
		
		
}