

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;


public class ClientGUI {

	
	MessageStream_client client;
	private JFrame frame;
	JTextArea text1 = new JTextArea();
	
	//private final Action action = new SwingAction();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI window = new ClientGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientGUI() {
		initialize();
		try {
			client = new MessageStream_client();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(102, 153, 204));
		
		JButton AddButton = new JButton("Add a word");
		JButton GetButton = new JButton("Find a word");
		JButton DelButton = new JButton("Delete a word");
		
		text1.setLineWrap(true);
		text1.setText("");

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(31, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(text1, GroupLayout.PREFERRED_SIZE, 633, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(AddButton, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(GetButton, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(DelButton, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)))
					.addGap(28))
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
							.addGap(68))))
		);
		
		
		
		AddButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				text1.setText("");
				
				String inputWord = JOptionPane.showInputDialog(frame.getContentPane(), "Word : ");
     	        JOptionPane.showMessageDialog(frame.getContentPane(), "User entered :\n " + inputWord);

     		   	String inputMeaning = JOptionPane.showInputDialog(frame.getContentPane(), "Definition : ");
     	        JOptionPane.showMessageDialog(frame.getContentPane(), "User entered :\n" + inputMeaning);
      	      
     	        DictClient add = new DictClient(messageAction.WORD_ADD, inputWord, inputMeaning);
     	        add.build_post_msg();
     	        //add.sendMsg();
     	        client.SendMsg(add.getMsgString());
     	        text1.setText(client.readRsp());
    	  
			}
		});
		GetButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				text1.setText("");
				String inputWord = JOptionPane.showInputDialog(frame.getContentPane(), "Enter the word : ");
     	        JOptionPane.showMessageDialog(frame.getContentPane(), "You entered :\n" + inputWord);
     	        
     	        DictClient get = new DictClient(messageAction.WORD_GET, inputWord, "");
     	        get.build_edit_msg();
     	       // get.sendMsg();
     	       
     	        client.SendMsg(get.getMsgString());
     	        text1.setText(client.readRsp());
		
     	        
   	 
			}
		});
		DelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				text1.setText("");
				String inputWord = JOptionPane.showInputDialog(frame.getContentPane(), "Word : ");
     	        JOptionPane.showMessageDialog(frame.getContentPane(), "User entered :\n" + inputWord);
    	        
    	        DictClient del = new DictClient(messageAction.WORD_DELETE, inputWord, "");
    	        del.build_edit_msg();
    	        
    	        client.SendMsg(del.getMsgString());
     	        text1.setText(client.readRsp());
     	       // del.sendMsg();
    	        //text1.setText(client.SendMsg(del.getMsgString()));   	 
     	    }
		});
		
		frame.getContentPane().setLayout(groupLayout);
		frame.setBounds(100, 100, 692, 387);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}


