import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 
 */

/**
 * @author ishan
 *
 */
public class serviceThread implements Runnable{
	
	private MessageStream server_socket;
	DictionaryData dictionary;
	static String client_id;
   
	public serviceThread(MessageStream socket, DictionaryData dictionary) {
		this.server_socket = socket;
		this.dictionary = dictionary;
	}
	
	private void handle_req(messageAction action, String word, String content) {
		message rsp = new message(action, word, content);
		
		rsp.build_server_rspXml();
 	    
		server_socket.SendMsg(rsp.getMsgString());
 	 
	}
	final String word = "word";
	final String content = "content";
	@Override
	public void run() {

		client_id = server_socket.get_client_id();
		
		System.out.println("Thread[ "+Thread.currentThread().getId()+" ]..connected to : "+ client_id + " ");
	
		while(true) {
			
			xml_parser xml = new xml_parser(server_socket.readRsp());
	        
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
		System.out.println("Thread[ "+Thread.currentThread().getId()+" ] End");
	}
}
