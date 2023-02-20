package Chat_Client.Frame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChattingClient extends JFrame {
	
	
	private static ChattingClient instance;
	
	public static ChattingClient getinstance() {
		if(instance == null) {
			instance = new ChattingClient();
		}
		return instance;
	}
	
	@Getter @Setter
	private Socket socket;
	private Gson gson;
	private InputStream inputStream;
	private OutputStream outputStream;
	
	private String username;
	
	
	@Getter
	private CardLayout mainCard;
	private JPanel loginPanel;
	@Getter
	private JPanel mainPanel;
	private JPanel chatRoomPanel;
	private JPanel chatListPanel;
	private JButton rpInputSubmit;
	private JButton rpChatOutBtn;
	private JButton cpCreateBtn;

	private JList<String> cpChatList;
	private DefaultListModel<String> userListModel;

	private JTextField rpInput;
	private JTextArea rpContentsView;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChattingClient frame = ChattingClient.getinstance();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public ChattingClient() {
		
		gson = new Gson();
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 800);
		mainPanel = new JPanel();
		mainPanel.setBackground(new Color(255, 204, 0));
		mainPanel.setBorder(null);

		setContentPane(mainPanel);
		mainCard = new CardLayout();
		mainPanel.setLayout(mainCard);
		
		
//		 로그인 패널 
		loginPanel = LoginPanel.getInstance();
		mainPanel.add(loginPanel, "loginPanel");
		
		
		
		
//		====================================================================================================================
		chatListPanel = new JPanel();
		chatListPanel.setBackground(new Color(255, 204, 0));
		mainPanel.add(chatListPanel, "userListPanel");
		chatListPanel.setLayout(null);
		
		JScrollPane cpChatListScroll = new JScrollPane();
		cpChatListScroll.setBounds(133, 0, 331, 761);
		chatListPanel.add(cpChatListScroll);
		
		userListModel = new DefaultListModel<>();
		cpChatList = new JList<>(userListModel);
		
		cpChatList.addMouseListener(new MouseAdapter() {
			
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (e.getClickCount() == 2) {
	                // 더블 클릭시 ActionListener 호출
//	                ActionListener listener = new UsernameClickListener();
//	                listener.actionPerformed(new ActionEvent(userList, ActionEvent.ACTION_PERFORMED, ""));
	            }
				
				mainCard.show(mainPanel, "messagePanel");
			}
			
		});
		
		cpChatListScroll.setViewportView(cpChatList);
		
		JLabel cpImageIcon = new JLabel("");
		cpImageIcon.setIcon(new ImageIcon(ChattingClient.class.getResource("/Images/kakaotalk_sharing_btn_medium_ov.png")));
		cpImageIcon.setBounds(30, 53, 68, 69);
		chatListPanel.add(cpImageIcon);
		
		cpCreateBtn = new JButton("");
		cpCreateBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				username = JOptionPane.showInputDialog(null,
						"방의 제목을 입력해주시오.",
						"방 생성",
						JOptionPane.INFORMATION_MESSAGE);
				
				userListModel.addElement(username);
				username = cpCreateBtn.getText();
				
			}
		});
		cpCreateBtn.setIcon(new ImageIcon(ChattingClient.class.getResource("/Images/plus-round_icon-icons.com_50065.png")));
		cpCreateBtn.setBounds(30, 148, 79, 54);
		cpCreateBtn.setBackground(Color.white);
		cpCreateBtn.setOpaque(false);//투명하게
		cpCreateBtn.setBorderPainted(false);
		chatListPanel.add(cpCreateBtn);
		
		chatRoomPanel = new JPanel();
		chatRoomPanel.setBackground(new Color(255, 204, 0));
		mainPanel.add(chatRoomPanel, "messagePanel");
		chatRoomPanel.setLayout(null);
		
		
		
		
		
//		====================================================================================================================
		
		JScrollPane rpContentsScroll = new JScrollPane();
		rpContentsScroll.setBounds(0, 103, 464, 562);
		chatRoomPanel.add(rpContentsScroll);
		
		rpContentsView = new JTextArea();
		rpContentsScroll.setViewportView(rpContentsView);
		
		JScrollPane rpInputScroll = new JScrollPane();
		rpInputScroll.setBounds(0, 663, 380, 98);
		chatRoomPanel.add(rpInputScroll);
		
		rpInput = new JTextField();
		rpInputScroll.setViewportView(rpInput);
		rpInput.setColumns(10);
		
		rpChatOutBtn = new JButton("");
		rpChatOutBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mainCard.show(mainPanel, "userListPanel");
				
			}
		});
		rpChatOutBtn.setIcon(new ImageIcon(ChattingClient.class.getResource("/Images/quit_icon_149882.png")));
		rpChatOutBtn.setBounds(359, 29, 65, 46);
		rpChatOutBtn.setBackground(Color.white);
		rpChatOutBtn.setOpaque(false);//투명하게
		rpChatOutBtn.setBorderPainted(false);
		chatRoomPanel.add(rpChatOutBtn);
		
		JLabel rpImageIcon = new JLabel("");
		rpImageIcon.setIcon(new ImageIcon(ChattingClient.class.getResource("/Images/kakaotalk_sharing_btn_medium_ov.png")));
		rpImageIcon.setBounds(30, 10, 68, 69);
		chatRoomPanel.add(rpImageIcon);
		
		JLabel rpChatTitle = new JLabel("");
		rpChatTitle.setBounds(121, 29, 155, 46);
		chatRoomPanel.add(rpChatTitle);
		
		rpInputSubmit = new JButton("");
		rpInputSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String message = rpInput.getText();
			    rpContentsView.append(username + ": " + message + "\n");
			    rpInput.setText("");
				
			}
		});
		rpInputSubmit.setIcon(new ImageIcon(ChattingClient.class.getResource("/Images/mail_send_icon_180871.png")));
		rpInputSubmit.setBounds(379, 663, 85, 98);
		chatRoomPanel.add(rpInputSubmit);
	}
	
	
	
	
}
