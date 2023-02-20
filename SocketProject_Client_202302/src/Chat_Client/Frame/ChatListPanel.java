package Chat_Client.Frame;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ChatListPanel extends JPanel {
	
	private static ChatListPanel instance;
	private JScrollPane cpChatListScroll;
	private JButton cpCreateBtn;
	private JList<String> cpChatList;
	private DefaultListModel<String> userListModel;
	private JTextArea rpContentsView;
	private JLabel rpChatTitle;
	
	private ChatListPanel() {
		
//		String username;
		
		setBackground(new Color(255, 204, 0));
		setLayout(null);
		
		cpChatListScroll = new JScrollPane();
		cpChatListScroll.setBounds(133, 0, 331, 761);
		add(cpChatListScroll);
		
		userListModel = new DefaultListModel<>();
		cpChatList = new JList<>(userListModel);
		
		cpChatList.addMouseListener(mouseClieckEventHandler());
		
		cpChatListScroll.setViewportView(cpChatList);
		
		JLabel cpImageIcon = new JLabel("");
		cpImageIcon.setIcon(new ImageIcon(ChattingClient.class.getResource("/Images/kakaotalk_sharing_btn_medium_ov.png")));
		cpImageIcon.setBounds(30, 53, 68, 69);
		add(cpImageIcon);
		
		cpCreateBtn = new JButton("");
		cpCreateBtn.addMouseListener(mouseClieckEvent());
		
		cpCreateBtn.setIcon(new ImageIcon(ChattingClient.class.getResource("/Images/plus-round_icon-icons.com_50065.png")));
		cpCreateBtn.setBounds(30, 148, 79, 54);
		cpCreateBtn.setBackground(Color.white);
		cpCreateBtn.setOpaque(false);//투명하게
		cpCreateBtn.setBorderPainted(false);
		add(cpCreateBtn);
		
	}
	
	public static ChatListPanel getInstance() {
		if(instance == null) {
			instance = new ChatListPanel();
		}
		
		return instance;
	}
	
	public MouseAdapter mouseClieckEventHandler() {
		
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				ChattingClient cci = ChattingClient.getinstance();

				String username = cpChatList.getSelectedValue().toString();
		        String message = username + " 님이 방을 생성하였습니다";
		        rpContentsView.setText(message);
		        
		        rpChatTitle.setText("제목 : " + username + " (님)의 방");
				
		        cci.getMainCard().show(cci.getMainPanel(), "chatRoomPanel");
			}
		};
		
		return mouseAdapter;
	}
	
	public MouseAdapter mouseClieckEvent() {
		MouseAdapter mouseAdapter = new MouseAdapter() {
			
			
			@Override
			public void mouseClicked(MouseEvent e) {
				ChattingClient cci = ChattingClient.getinstance();
				String username;
				
				username = JOptionPane.showInputDialog(null,
						"방의 제목을 입력해주시오.",
						"방 생성",
						JOptionPane.INFORMATION_MESSAGE);
				
				
				getUserListModel().addElement(username);
				username = cpCreateBtn.getText();
				
			}
		};
		
		return mouseAdapter;
	}
	
	
	public DefaultListModel<String> getUserListModel() {
	    if (userListModel == null) {
	        userListModel = new DefaultListModel<>();
	    }
	    return userListModel;
	}

}
