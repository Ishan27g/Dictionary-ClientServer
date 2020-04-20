
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MessageStream_client extends MessageStream{
	
	Socket sock;
	private String server_ip = new String("192.168.1.102");

	public MessageStream_client() throws UnknownHostException, IOException {
		try {
			sock = new Socket(server_ip,9999);
			sock.setReuseAddress(true);

		}
		catch (IOException e){
			e.printStackTrace(System.err);
		}
		super.set_IO_stream(sock);
	}
	
	public String getServerIP() {
		return new String(server_ip);
	}
}
