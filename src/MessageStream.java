import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 */

/**
 * @author ishan
 *
 */
public class MessageStream {
	
	private ServerSocket server_socket;
	private Socket sock;
	private OutputStream out;
	private DataOutputStream dataOut;
	private InputStream in;
	private DataInputStream dataIn;
	private String rspMsg;
	
	public String get_client_id() {
		return sock.getInetAddress().toString();
	}
	
	private void ReadMsg() {
		
		try {
			rspMsg = new String(dataIn.readUTF());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String readRsp() {
		ReadMsg();
		if(rspMsg != "")
			return (new String(rspMsg));
		else
			return "";
	}
	
	public void SendMsg(String xml_msg) {
		try {
			dataOut.writeUTF(xml_msg);
			System.out.println("send>>" + xml_msg);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	public void closeConnection() throws IOException {
		//Close the data streams and sockets
		try {
			dataIn.close();
		} 
		catch (IOException e){
			e.printStackTrace(System.err);
		}
		try {
			in.close();
		} 
		catch (IOException e){
			e.printStackTrace(System.err);
		}
		try {
			dataOut.close();
		} 
		catch (IOException e){
			e.printStackTrace(System.err);
		}
		try {
			out.close();
		} 
		catch (IOException e){
			e.printStackTrace(System.err);
		}
		if(sock != null){
			if (sock.isBound() == true || sock.isConnected() == true) {
				try {
					sock.close();
				} 
				catch (IOException e){
					e.printStackTrace(System.err);
				}
			}
				
		}
	}

	protected void set_IO_stream(Socket sock){
		try {
			in = sock.getInputStream();
		} catch (IOException e) {
			System.out.println("unable to get inputStream");
		}
		dataIn = new DataInputStream(in);
		
		try {
			out = sock.getOutputStream();
		} catch (IOException e) {
			System.out.println("unable to get outputstream");
		}
		dataOut = new DataOutputStream(out);

	}
	
	public void accept_connections()  {
		try {
			sock = server_socket.accept();
		}
		catch (IOException e){
            e.printStackTrace(System.err);
		}
		set_IO_stream(sock);
	}
	
	public void setServerSocket(ServerSocket server_socket){
		this.server_socket = server_socket;
	}


	public MessageStream() {
		return;
	}
		
}
