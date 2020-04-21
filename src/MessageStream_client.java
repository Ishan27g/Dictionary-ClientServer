
/**
 * @author Ishan Goyal : 1056051
 *	This class is used to define the client Message stream
 */

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MessageStream_client extends MessageStream{
	
	Socket sock;
	private int port;
	private String server_ip;


	public MessageStream_client(String ip, int port) throws UnknownHostException, IOException {
		server_ip = new String(ip);
		this.port = port;
		try {
			sock = new Socket(server_ip,port);
			sock.setReuseAddress(true);
		}
		catch (IOException e){
			System.out.println("Server not reachable, server is either shutdown or busy");
			System.exit(1);
		}
		super.set_IO_stream(sock);
		
	}
	
	public String getServerIP() {
		return new String(server_ip);
	}
	public int getServerPort(){
		return port;
	}
}
