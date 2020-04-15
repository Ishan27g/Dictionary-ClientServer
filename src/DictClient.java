/**
 * 
 */


/**
 * @author ishan
 *
 */
public class DictClient {
	
    message Message;
    MessageStream_client client_socket;
   
    public DictClient(clientAction action, String word, String content) {
    	Message = new message(action, word, content); 
    }
    
    public String getMsgString() {
    	return Message.getMsgString();
	}
    public void build_post_msg() {
    	Message.build_post_xml();
    }
    public void build_edit_msg() {
    	Message.build_edit_xml();
    }
    public void sendMsg() {
		Message.send_msg();
    }
  
}
