import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

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
	private String response;
   
	public serviceThread(MessageStream socket, DictionaryData dictionary) {
		this.server_socket = socket;
		this.dictionary = dictionary;
	}
	
	@Override
	public void run() {

			while(true) {
				response = new String(server_socket.readRsp());
				
				System.out.println("Thread["+Thread.currentThread().getId()+"]" + "response is : \n" + response);
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
				DocumentBuilder builder;  
					
				try {  
				    builder = factory.newDocumentBuilder();  
				    Document document = builder.parse(new InputSource(new StringReader(response)));
					//System.out.println(document.getElementsByTagName("action").item(0).getTextContent());
					if (document.getElementsByTagName("action").item(0).getTextContent().contains(messageAction.WORD_ADD.toString())){
						
						if(dictionary.addKey(document.getElementsByTagName("word").item(0).getTextContent(),
								document.getElementsByTagName("content").item(0).getTextContent()) == true) {
														
								message rsp = new message(messageAction.SERV_RSP, "Success", "");
					     	    rsp.build_server_rspXml();
					     	    server_socket.SendMsg(rsp.getMsgString());
						}
					}
					else if (document.getElementsByTagName("action").item(0).getTextContent().contains(messageAction.WORD_GET.toString())){
						
						message rsp = new message(messageAction.SERV_RSP, document.getElementsByTagName("word").item(0).getTextContent()
								, dictionary.searchKey(document.getElementsByTagName("word").item(0).getTextContent()));
						rsp.build_server_rspXml();
			     	    server_socket.SendMsg(rsp.getMsgString());
					}
					else if (document.getElementsByTagName("action").item(0).getTextContent().contains(messageAction.WORD_DELETE.toString())){
						
						if(dictionary.deleteKey(document.getElementsByTagName("word").item(0).getTextContent()) == true) {
							message rsp = new message(messageAction.SERV_RSP, "Success", "");
				     	    rsp.build_server_rspXml();
				     	    server_socket.SendMsg(rsp.getMsgString());
						}
					}
				} catch (Exception e) {  
				    e.printStackTrace();  
				} 
			}
			
	}

	
}
