	
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.WindowConstants;


public class ClientGUI {

	
	MessageStream_client client;
	JFrame frame;
	JTextArea text1 = new JTextArea();
	
	public ClientGUI(MessageStream_client cl) {
		
		this.client = cl;
		initialize();		
	}
	
	private void handle_rsp(messageAction action, String word, String content) {
		message rsp = new message(action, word, content);
		if(action == messageAction.WORD_ADD)
			rsp.build_post_xml();
		else
			rsp.build_edit_xml();
 	    
		client.SendMsg(rsp.getMsgString());
		return;
	}
	
	private void initialize() {

		
		String client_addr = null;
		try {
			client_addr = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e2) {
			e2.printStackTrace();
		}
		frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setTitle("Client ["+ client_addr +"] connected to Server ["+client.getServerIP()+":"+client.getServerPort()+"]");

		frame.getContentPane().setBackground(new Color(102, 153, 204));
		
		JButton AddButton = new JButton("Add a word");
		JButton GetButton = new JButton("Find a word");
		JButton DelButton = new JButton("Delete a word");
		
		text1.setLineWrap(true);
		text1.setText("");
		
		JButton disconnect = new JButton("disconnect");
		disconnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				message rsp = new message(messageAction.CLI_EXIT, "", "");
   			    rsp.build_edit_xml();
			    client.SendMsg(rsp.getMsgString());			   
			    try {
			    	client.closeConnection();
			    } catch (IOException e1) {
			    	e1.printStackTrace();
			    }
			    System.exit(0);
			}
		});

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(31, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(text1, GroupLayout.PREFERRED_SIZE, 633, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(AddButton, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(GetButton, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(DelButton, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)))
							.addGap(28))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(disconnect)
							.addGap(20))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(19)
					.addComponent(text1, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(GetButton, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
								.addComponent(AddButton, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
							.addGap(66))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(DelButton, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(disconnect)
							.addGap(27))))
		);
		
		
		
		AddButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				text1.setText("");
				
				String inputWord = JOptionPane.showInputDialog(frame.getContentPane(), "Word : ");
     	        JOptionPane.showMessageDialog(frame.getContentPane(), "User entered :\n " + inputWord);

     		   	String inputMeaning = JOptionPane.showInputDialog(frame.getContentPane(), "Definition : ");
     	        JOptionPane.showMessageDialog(frame.getContentPane(), "User entered :\n" + inputMeaning);
      	      
     	        
     	        handle_rsp(messageAction.WORD_ADD, inputWord, inputMeaning);
     	        xml_parser xml = new xml_parser(client.readRsp());
     	        text1.setText(xml.get_element("word"));
     	       
     	       
			}
		});
		GetButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				text1.setText("");
				String inputWord = JOptionPane.showInputDialog(frame.getContentPane(), "Enter the word : ");
     	        JOptionPane.showMessageDialog(frame.getContentPane(), "You entered :\n" + inputWord);
     	       
     	        
      	       handle_rsp(messageAction.WORD_GET, inputWord, "");

     	        xml_parser xml = new xml_parser(client.readRsp());
    	        text1.setText(xml.get_element("content"));
    	     
			}
		});
		DelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				text1.setText("");
				String inputWord = JOptionPane.showInputDialog(frame.getContentPane(), "Word : ");
     	        JOptionPane.showMessageDialog(frame.getContentPane(), "User entered :\n" + inputWord);
    	        
       	        handle_rsp(messageAction.WORD_DELETE, inputWord, "");

    	        xml_parser xml = new xml_parser(client.readRsp());
     	        text1.setText(xml.get_element("word"));
     	       
     	    }
		});
		
		frame.getContentPane().setLayout(groupLayout);
		frame.setBounds(100, 100, 692, 387);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
	}
	
	
}


