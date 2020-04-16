import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MessageStream_client extends MessageStream{
	
	Socket sock;

	public MessageStream_client() throws UnknownHostException, IOException {
		try {
			sock = new Socket(InetAddress.getLocalHost(),80);
		}
		catch (IOException e){
			e.printStackTrace(System.err);
		}
		super.set_IO_stream(sock);
	}
}
