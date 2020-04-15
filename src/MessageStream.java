import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 
 */

/**
 * @author ishan
 *
 */
public class MessageStream {
	private ServerSocket ssocket;
	private Socket sock;
	private OutputStream out;
	private DataOutputStream dataOut;
	private InputStream in;
	private DataInputStream dataIn;
	private String rspMsg;
	
	private void ReadMsg() {
		try {
			rspMsg = new String(dataIn.readUTF());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getMsg() {
		ReadMsg();
		return (new String(rspMsg));
	}
	
	public void SendMsg(String xml_msg) {
		try {
			dataOut.writeUTF(xml_msg);
		} catch (IOException e) {
			e.printStackTrace();
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
		try {
			sock.close();
		} 
		catch (IOException e){
			e.printStackTrace(System.err);
		}
	}
	

	public MessageStream() throws UnknownHostException, IOException {
		try{
			ssocket = new ServerSocket(1234);
		}
		catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void accept_connections() throws IOException {

		System.out.println("waiting to accept connections");
		try {
			sock = ssocket.accept();
		
			if (ssocket != null && !ssocket.isClosed()) {
				try {
					ssocket.close();
				} 
				catch (IOException e){
					e.printStackTrace(System.err);
				}
			}
		
			out = sock.getOutputStream();
			dataOut = new DataOutputStream(out);
		
			in = sock.getInputStream();
			dataIn = new DataInputStream(in);
		}
		catch (IOException e){
            e.printStackTrace(System.err);
        }
	}
}
