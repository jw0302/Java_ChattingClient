package Chat_Client.Frame;

import javax.swing.JPanel;

public class ChatListPanel extends JPanel {
	
	private static ChatListPanel instance;

	
	private ChatListPanel() {
		
	}
	
	public static ChatListPanel getInstance() {
		if(instance == null) {
			instance = new ChatListPanel();
		}
		
		return instance;
	}

}
