package Chat_Client.Frame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

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
import javax.swing.SwingConstants;

import com.google.gson.Gson;

import lombok.Getter;


@Getter
public class ChattingClient extends JFrame {
	
	
	private static ChattingClient instance;
	
	public static ChattingClient getinstance() {
		if(instance == null) {
			instance = new ChattingClient();
		}
		return instance;
	}
	

	private Socket socket;
	private Gson gson;
	private InputStream inputStream;
	private OutputStream outputStream;
	
	private String username;
	
	private CardLayout mainCard;
	private JPanel mainPanel;
	
	private JPanel loginPanel;
	private JButton lpLoginBtn;
	private JTextField lpUserNameInput;
	
	private JPanel chatListPanel;
	private JButton cpCreateBtn;
	private JList<String> cpChatList;
	private DefaultListModel<String> userListModel;
	
	private JPanel chatRoomPanel;
	private JButton rpInputSubmit;
	private JButton rpChatOutBtn;
	private JTextField rpInput;
	private JTextArea rpContentsView;
	private JLabel rpChatTitle;
	
	
	
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
		
		
		
//		==================== << LoginPanel >> ==========================
		
		
		loginPanel = new JPanel();
		loginPanel.setBackground(new Color(255, 204, 0));
		mainPanel.add(loginPanel, "loginPanel");
		loginPanel.setLayout(null);
		
		lpUserNameInput = new JTextField();
		lpUserNameInput.setFont(new Font("굴림", Font.PLAIN, 16));
		lpUserNameInput.setHorizontalAlignment(SwingConstants.CENTER);
		lpUserNameInput.setBounds(73, 492, 300, 45);
		loginPanel.add(lpUserNameInput);
		lpUserNameInput.setColumns(10);
		
		lpLoginBtn = new JButton("");
		lpLoginBtn.addMouseListener(new MouseAdapter() {
		
		
			@Override
			public void mouseClicked(MouseEvent e) {
								
				try {
					socket = new Socket("127.0.0.1", 9090);
					
					
					JOptionPane.showMessageDialog(null,
							socket.getInetAddress() + "서버 접속",
							"접속성공!",
							JOptionPane.INFORMATION_MESSAGE);
									
				} catch (ConnectException e1) {
					
					JOptionPane.showMessageDialog(null,
							"서버 접속 실패",
							"접속실패!",
							JOptionPane.ERROR_MESSAGE);
					
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				username = lpUserNameInput.getText();
				userListModel.addElement("제목: " + username);
				mainCard.show(mainPanel, "chatListPanel");
			}
		});
		
		lpLoginBtn.setIcon(new ImageIcon(ChattingClient.class.getResource("/Images/login_med.png")));
		lpLoginBtn.setBounds(73, 573, 300, 45);
		lpLoginBtn.setBackground(Color.white);
		lpLoginBtn.setOpaque(false);//투명하게
		lpLoginBtn.setBorderPainted(false);
		loginPanel.add(lpLoginBtn);
		
		JLabel lpImageIcon = new JLabel("");
		lpImageIcon.setIcon(new ImageIcon(ChattingClient.class.getResource("/Images/kakaotalk_sharing_btn_medium_ov.png")));
		lpImageIcon.setBounds(183, 226, 68, 69);
		loginPanel.add(lpImageIcon);
		
		
		
//		==================== << ChatListPanel >> ==========================
		
		
		chatListPanel = new JPanel();
		chatListPanel.setBackground(new Color(255, 204, 0));
		mainPanel.add(chatListPanel, "chatListPanel");
		chatListPanel.setLayout(null);	
		
		JScrollPane cpChatListScroll = new JScrollPane();
		cpChatListScroll.setBounds(133, 0, 331, 761);
		chatListPanel.add(cpChatListScroll);
		
		userListModel = new DefaultListModel<>();
		cpChatList = new JList<String>(userListModel);
		cpChatList.setFont(new Font("궁서", Font.PLAIN, 18));
		
		cpChatList.addMouseListener(new MouseAdapter() {
			
			
			@Override
			public void mouseClicked(MouseEvent e) {
				

				username = cpChatList.getSelectedValue().toString();
		        String message = username + " 님이 방을 생성하였습니다";
		        rpContentsView.setText(message);
		        
		        rpChatTitle.setText(username + " (님)의 방");
				
				mainCard.show(mainPanel, "chatRoomPanel");
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
				
				userListModel.addElement("제목: " + username);
				username = cpCreateBtn.getText();
				
			}
		});
		
		cpCreateBtn.setIcon(new ImageIcon(ChattingClient.class.getResource("/Images/plus-round_icon-icons.com_50065.png")));
		cpCreateBtn.setBounds(30, 148, 79, 54);
		cpCreateBtn.setBackground(Color.white);
		cpCreateBtn.setOpaque(false);//투명하게
		cpCreateBtn.setBorderPainted(false);
		chatListPanel.add(cpCreateBtn);
		
		
		
//		==================== << ChatRoomPanel >> ==========================		
		
			
		chatRoomPanel = new JPanel();
		chatRoomPanel.setBackground(new Color(255, 204, 0));
		mainPanel.add(chatRoomPanel, "chatRoomPanel");
		chatRoomPanel.setLayout(null);
		
		JScrollPane rpContentsScroll = new JScrollPane();
		rpContentsScroll.setBounds(0, 103, 464, 562);
		chatRoomPanel.add(rpContentsScroll);
		
		rpContentsView = new JTextArea();
		rpContentsView.setFont(new Font("굴림", Font.PLAIN, 15));
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
				
				mainCard.show(mainPanel, "chatListPanel");
				rpContentsView.setText("");
				rpInput.setText("");
				
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
		
		rpChatTitle = new JLabel("");
		rpChatTitle.setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 16));
		rpChatTitle.setBounds(121, 29, 226, 46);
		chatRoomPanel.add(rpChatTitle);
		
		rpInputSubmit = new JButton("");
		rpInputSubmit.addMouseListener(new MouseAdapter() {
			
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String message = rpInput.getText();
			    rpContentsView.append("\n" + username + ": " + message);
			    
			    
			    rpInput.setText("");
				
			}
		});
		
		rpInputSubmit.setIcon(new ImageIcon(ChattingClient.class.getResource("/Images/mail_send_icon_180871.png")));
		rpInputSubmit.setBounds(379, 663, 85, 98);
		chatRoomPanel.add(rpInputSubmit);
	}
	
	
	
	
}
