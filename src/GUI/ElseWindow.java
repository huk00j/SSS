package GUI;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ElseWindow extends JFrame {

	private JPanel contentPane;
	private ElseWindow ew = null;
	
	
	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ElseWindow frame = new ElseWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	*/

	/**
	 * Create the frame.
	 */
	public ElseWindow() {
//	elseJoin();
	}


	public void elseJoin() {	// 회원 가입 완료시
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 100, 300, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//<html>은 html 형식? // <br>이 줄 바꿈인 듯.
		JLabel lblNewLabel_1 = new JLabel("<html><center><html>회원가입이 절차가 완료되었습니다. <br>  <br> 로그인 하여 다양한 기능을 사용해보세요.<br></html>");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(29, 10, 238, 56);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("사진을 누르면 자동으로 로그인 됩니다.");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 12));
		lblNewLabel.setBounds(29, 157, 231, 45);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(39, 76, 209, 80);
		contentPane.add(btnNewButton);
		
		this.setVisible(true);
		
	}
}














