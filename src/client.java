import java.awt.EventQueue;
import java.io.IOException;
import java.net.UnknownHostException;

public class client {

	
	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		//MessageStream_client client= new MessageStream_client();

		final MessageStream_client c = new MessageStream_client();

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