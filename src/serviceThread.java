

/**
 * 
 */

/**
 * @author ishan
 *
 */
public class serviceThread implements Runnable{
	
	private MessageStream server_socket;
	private String response;

	public serviceThread(MessageStream socket) {
		this.server_socket = socket;
	}
	@Override
	public void run() {

			response = new String(server_socket.getMsg());
			System.out.println("response is : \n" + response);
	}

}
