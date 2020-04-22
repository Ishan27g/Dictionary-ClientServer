import java.sql.Timestamp;

/**
 * @author Ishan Goyal : 1056051
 *	This class defines the Server worker thread
 *  Each instance of this class runs to service an individual client.
 */
public class serviceThread implements Runnable{
	
	private MessageStream server_socket;
	DictionaryData dictionary;
	static String client_id;
	final String word = "word";
	final String content = "content";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_RESET = "\u001B[0m";

   
	public serviceThread(MessageStream socket, DictionaryData dictionary) {
		this.server_socket = socket;
		this.dictionary = dictionary;
	}
	
	private void handle_req(messageAction action, String word, String content) {
		
		message rsp = new message(action, word, content);
		rsp.build_server_rspXml();
 	    server_socket.SendMsg(rsp.getMsgString()); 
	}
	int msg_num = 0;
	
	@Override
	public void run() {

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println(ANSI_YELLOW + Thread.currentThread().getName() + ANSI_RESET +
		 ": Created, in a 'waiting' state at : " + timestamp);
		client_id = server_socket.get_client_id();
	
		while(true) {
			
			xml_parser xml = new xml_parser(server_socket.readRsp());
			msg_num++;
			if(msg_num == 1) {
				
			    timestamp = new Timestamp(System.currentTimeMillis());
				System.out.println(ANSI_YELLOW + Thread.currentThread().getName() + ANSI_RESET +
				 " : Received message, from 'waiting' to 'executing' state at : " + timestamp);
			}
	        
			if(xml.lookup_action(messageAction.WORD_ADD.toString()) ==  true) {
				System.out.println("["+client_id + "] - Add a word ");
				if(dictionary.addKey(xml.get_element(word), xml.get_element(content)) == true) {
					handle_req(messageAction.SERV_RSP, "Success", ""); 
				}
				else {
					handle_req(messageAction.SERV_RSP, "Error", "Could not add the word and its meaning");
				}
				System.out.println("["+client_id + "] - response sent");
			}
			
			else if(xml.lookup_action(messageAction.WORD_GET.toString()) ==  true) {
				System.out.println("["+client_id + "] - Get a meaning ");
				handle_req(messageAction.SERV_RSP, xml.get_element(word), 
						dictionary.searchKey(xml.get_element(word)));
				System.out.println("["+client_id + "] - response sent");
			}
			
			else if(xml.lookup_action(messageAction.WORD_DELETE.toString()) ==  true) {
				System.out.println("["+client_id + "] - Delete a word ");
				if(dictionary.deleteKey(xml.get_element(word)) == true) {
					handle_req(messageAction.SERV_RSP, "Success", "");
				}
				else{
					handle_req(messageAction.SERV_RSP, "Error", "Could not delete key");
				}
				System.out.println("["+client_id + "] - response sent");
			}
			
			else if(xml.lookup_action(messageAction.CLI_EXIT.toString()) ==  true) {
				System.out.println("["+client_id + "] - ended connection ");
				break;
			}
		    else {
		    	handle_req(messageAction.SERV_RSP, "Error", "invalid action");
				System.out.println("["+client_id + "] - response sent");
		    }
		}
		
	    timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println(ANSI_YELLOW + Thread.currentThread().getName() + ANSI_RESET + 
		 " : Response sent, thread lifecycle ends at : " + timestamp);
	 
	}
}
