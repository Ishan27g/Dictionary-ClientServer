

public class message {
	
	private messageAction action = null;
	private String word = null;
	private String content = null;
	
	private StringBuilder builder = null;

	public message(messageAction action, String word, String content) {
		this.action = action;
		this.word = new String(word);
		if(content.length() != 0) {
			this.content = new String(content);
		}
		builder = new StringBuilder();
	}
	public String getMsgString() {
		return builder.toString();
	}
	
	public void send_msg() {
		System.out.println("Message to send is:\n");
		System.out.println(getMsgString());
		return ;
	}
	
	public void build_post_xml() {

		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		builder.append("<message>");
		builder.append("<action>" + action + "</action>");
		builder.append("<word>" + word + "</word>");
		builder.append("<content>" + content + "</content>");
		builder.append("</message>");
		
	}
	
	public void build_edit_xml() {

		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		builder.append("<message>");
		builder.append("<action>" + action + "</action>");
		builder.append("<word>" + word + "</word>");
		builder.append("</message>");
				
	}
	
	public void build_server_rspXml() {

		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		builder.append("<message>");
		builder.append("<action>" + action + "</action>");
		builder.append("<word>" + word + "</word>");
		if(content != null) {
			builder.append("<content>" + content + "</content>");
		}
		builder.append("</message>");
				
	}

}
