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

			response = new String(server_socket.getMsg());
			System.out.println("Thread["+Thread.currentThread().getId()+"]" + "response is : \n" + response);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
			DocumentBuilder builder;  
			try {  
			    builder = factory.newDocumentBuilder();  
			    Document document = builder.parse(new InputSource(new StringReader(response)));
				System.out.println(document.getElementsByTagName("action").item(0).getTextContent());
				if (document.getElementsByTagName("action").item(0).toString().contains(clientAction.WORD_ADD.toString())){
					System.out.println(document.getElementsByTagName("word").item(0).getTextContent());
					System.out.println(document.getElementsByTagName("content").item(0).getTextContent());
				}
				/*if(document.getElementsByTagName("content").item(0).getTextContent().length() != 0) {
					System.out.println(document.getElementsByTagName("content").item(0).getTextContent());
				}
				*/

			} catch (Exception e) {  
			    e.printStackTrace();  
			} 
			
			
	}

	
}
