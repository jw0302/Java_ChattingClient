package Chat_Client.Frame;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginPanel extends JPanel {
	
	private static LoginPanel instance;
	private JButton lpLoginBtn;
	private JTextField lpUserNameInput;
	
	private LoginPanel() {
		

		setBackground(new Color(255, 204, 0));
		setLayout(null);
		lpUserNameInput = new JTextField();
		lpUserNameInput.setHorizontalAlignment(SwingConstants.CENTER);
		lpUserNameInput.setBounds(73, 492, 300, 45);
		add(lpUserNameInput);
		lpUserNameInput.setColumns(10);
		
		lpLoginBtn = new JButton("");
		lpLoginBtn.addMouseListener(mouseClickEventHandler());
		
		lpLoginBtn.setIcon(new ImageIcon(ChattingClient.class.getResource("/Images/login_med.png")));
		lpLoginBtn.setBounds(73, 573, 300, 45);
		lpLoginBtn.setBackground(Color.white);
		lpLoginBtn.setOpaque(false);//투명하게
		lpLoginBtn.setBorderPainted(false);
		add(lpLoginBtn);
		
		JLabel lpImageIcon = new JLabel("");
		lpImageIcon.setIcon(new ImageIcon(ChattingClient.class.getResource("/Images/kakaotalk_sharing_btn_medium_ov.png")));
		lpImageIcon.setBounds(183, 226, 68, 69);
		add(lpImageIcon);
		
	}
	
	public static LoginPanel getInstance() {
		if(instance == null) {
			instance = new LoginPanel();
		}
		return instance;
	}
	
	
	
	public MouseAdapter mouseClickEventHandler() {
		
		MouseAdapter mouseAdapter = new MouseAdapter() {
			
			
			@Override
			public void mouseClicked(MouseEvent e) {
				ChattingClient cci = ChattingClient.getinstance();
				String username;
				
				try {
					cci.setSocket(new Socket("127.0.0.1", 9090));
					Socket socket = cci.getSocket();
					
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
				cci.getUserListModel().addElement(username);
				cci.getMainCard().show(cci.getMainPanel(), "chatListPanel");
			}
		};
		
		return mouseAdapter;
		
	}
	
}
