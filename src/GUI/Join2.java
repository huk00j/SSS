package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import DB.LoginDAO;
import TCPClient.ClientC;

public class Join2 extends JFrame {

	JButton btnNewButton;
	JTextField txtAsdfdsfd;

	LoginDAO ldao = new LoginDAO();

	private JPanel contentPane;

	public JLabel lblNewLabel_2, lblNewLabel_3, lblNewLabel_4;	// 순서 꼬이지 않게 남겨놓기.
	public JLabel lblNewLabel_20;
	
	JTextField textField_1, textField_2;
	JLabel lblNewLabel_5, lblNewLabel_6;
	JButton btnNewButton_1;

	JLabel lblNewLabel_7, lblNewLabel_8, lblNewLabel_9, lblNewLabel_10;
	JLabel lblNewLabel_11;

	ClientC Cc = null;
	Table2 table = null;
	
	private Join2 join2; // ClientC 한테 Join2 본인 객체 주소를 넘길 것임.
	
	ActionListener action = null; //?????
	
	/**
	 * Create the frame.
	 * @param cc2 
	 * @param scenter 
	 * 
	 * @return
	 */
//	public Join2(ClientC cc) {
//		this.Cc = cc;
//	}

	public Join2(Table2 tt, ClientC cc) {
		this.table = tt;
		this.Cc = cc;
		
		this.join2 = this;
		Cc.jj2(join2);	// 일단 여기에서 ClientC 한테 주소 넘겨줌.
		
//		this.setVisible(true);
//		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);

//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(800, 200, 300, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(25, 35, 30, 30);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("PW");
		lblNewLabel_1.setBounds(25, 100, 30, 30);
		contentPane.add(lblNewLabel_1);

		txtAsdfdsfd = new JTextField();			// 아이디 중복 확인 txtfd.
		txtAsdfdsfd.setBounds(60, 40, 110, 25);
		contentPane.add(txtAsdfdsfd);
		txtAsdfdsfd.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(60, 100, 110, 25);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(60, 150, 110, 25);
		contentPane.add(textField_2);
		textField_2.setColumns(10);

		btnNewButton = new JButton("중복 확인");
		btnNewButton.setBounds(182, 32, 90, 37);
		contentPane.add(btnNewButton);

		btnNewButton_1 = new JButton("가입");
		btnNewButton_1.setBounds(100, 220, 100, 60);
		contentPane.add(btnNewButton_1);

		this.setVisible(true);
		
		checkA();

		ActionID();
		ActionPW1();
		ActionPW2();

		insert();
	}

	private void checkA() {
		
		lblNewLabel_20 = new JLabel("");
		lblNewLabel_20.setFont(new Font("굴림", Font.PLAIN, 12));
		lblNewLabel_20.setBounds(50, 75, 150, 15);
		contentPane.add(lblNewLabel_20);	

		
		lblNewLabel_5 = new JLabel("비밀번호가 일치하지 않습니다.");
		lblNewLabel_5.setFont(new Font("굴림", Font.PLAIN, 12));
		lblNewLabel_5.setForeground(Color.RED);
		lblNewLabel_5.setBounds(40, 175, 185, 25);
		contentPane.add(lblNewLabel_5);
		lblNewLabel_5.setVisible(false);

		lblNewLabel_6 = new JLabel("비밀번호가 일치합니다.");
		lblNewLabel_6.setFont(new Font("굴림", Font.PLAIN, 12));
		lblNewLabel_6.setForeground(Color.BLUE);
		lblNewLabel_6.setBounds(40, 175, 185, 25);
		contentPane.add(lblNewLabel_6);
		lblNewLabel_6.setVisible(false);

		// 빨간색 자물쇠.
		lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setIcon(new ImageIcon("C:\\Users\\Computer\\Desktop\\프로젝트 사용 자료\\redkey1.jpg"));
		lblNewLabel_7.setBounds(200, 100, 30, 30);
		contentPane.add(lblNewLabel_7);
		lblNewLabel_7.setVisible(true);

		lblNewLabel_8 = new JLabel("");
		lblNewLabel_8.setIcon(new ImageIcon("C:\\Users\\Computer\\Desktop\\프로젝트 사용 자료\\redkey1.jpg"));
		lblNewLabel_8.setBounds(200, 135, 30, 30);
		contentPane.add(lblNewLabel_8);
		lblNewLabel_8.setVisible(true);

		// 파란색 자물쇠.
		lblNewLabel_9 = new JLabel("");
		lblNewLabel_9.setIcon(new ImageIcon("C:\\Users\\Computer\\Desktop\\프로젝트 사용 자료\\bluekey1.jpg"));
		lblNewLabel_9.setBounds(200, 100, 30, 30);
		contentPane.add(lblNewLabel_9);
		lblNewLabel_9.setVisible(false);

		lblNewLabel_10 = new JLabel("");
		lblNewLabel_10.setIcon(new ImageIcon("C:\\Users\\Computer\\Desktop\\프로젝트 사용 자료\\bluekey1.jpg"));
		lblNewLabel_10.setBounds(200, 135, 30, 30);
		contentPane.add(lblNewLabel_10);
		lblNewLabel_10.setVisible(false);

		lblNewLabel_11 = new JLabel("4자 이상 사용하세요.");
		lblNewLabel_11.setFont(new Font("굴림", Font.PLAIN, 12));
		lblNewLabel_11.setForeground(Color.RED);
		lblNewLabel_11.setBounds(60, 125, 120, 25);
		contentPane.add(lblNewLabel_11);
		lblNewLabel_11.setVisible(false);
	}

	
	public boolean blankId = false;	// id 공백 체크 && 중복 체크 눌렀는지.
	private void ActionID() {	//--------------------------------------- 계정 중복 체크용.
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String in = txtAsdfdsfd.getText();

				if (txtAsdfdsfd.getText().equals("")) {
					String checkId = "아이디공백";
					Cc.send(checkId);
					
				} else {
					blankId = true;
					String idcc = in+"/계정";
					Cc.send(idcc);
					System.out.println("완료!!!");
				}
			}
		});
	}

	
	boolean pw1 = false;
	private void ActionPW1() {
		textField_1.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override	// 키보드 떼자마자.
			public void keyReleased(KeyEvent e) {
				if (textField_1.getText().length() < 4) {
					System.out.println("4자 이상이어야 합니다.");
					lblNewLabel_11.setVisible(true); // 4글자 이상
					lblNewLabel_7.setVisible(true); // 빨강 자물쇠
					lblNewLabel_9.setVisible(false); // 파랑 자물쇠
					
				} else if (textField_1.getText().length() >= 4) {
					pw1 = true;
					System.out.println("4자 이상이다!!!");
					lblNewLabel_11.setVisible(false); // 4글자 이상
					//"사용 가능한 비밀번호입니다." 추가.
					lblNewLabel_7.setVisible(false); // 빨강
					lblNewLabel_9.setVisible(true); // 파랑
					
				}
				
				// 이 부분은 아래 칸 자물쇠.
				if(textField_1.getText().equals(textField_2.getText())) {
					lblNewLabel_8.setVisible(false);// 빨간 자물쇠.
					lblNewLabel_10.setVisible(true); // 파란 자물쇠.
				}else {
					lblNewLabel_10.setVisible(false); // 파란 자물쇠.
					lblNewLabel_8.setVisible(true);	// 빨간 자물쇠.
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
	}

	
	boolean pw2 = false;
	private void ActionPW2() {
		textField_2.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (textField_1.getText().equals(textField_2.getText())) {
					System.out.println("비밀번호 일치");
					lblNewLabel_5.setVisible(false);	// 비밀번호가 일치하지 않습니다. 문구.
					lblNewLabel_6.setVisible(true);		// 비밀번호가 일치합니다. 문구.

					lblNewLabel_8.setVisible(false); // 빨간 자물쇠.
					lblNewLabel_10.setVisible(true); // 파란 자물쇠.

					pw2 = true;
				} else {
					System.out.println("비밀번호 일치하지 않아!");
					lblNewLabel_6.setVisible(false);
					lblNewLabel_5.setVisible(true);

					lblNewLabel_8.setVisible(true); // 빨간 자물쇠.
					lblNewLabel_10.setVisible(false); // 파란 자물쇠.

					pw2 = false;
				}
			}
			//실험용
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
	}

	public boolean checkId = false;
	private void insert() {
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (blankId && checkId && pw1 && pw2) {
					// id칸 공백 &&
					String id = txtAsdfdsfd.getText();
					txtAsdfdsfd.setText("");
					String pw = textField_2.getText();
					textField_1.setText("");
					textField_2.setText("");

					lblNewLabel_20.setVisible(false);
					lblNewLabel_6.setVisible(false); // 파란색 pw 글 제거.

					int k = ldao.insertQ(id, pw);

					if (k == 1) {
						System.out.println("insert 성공");
						join2.dispose();
						
						ElseWindow ew = new ElseWindow();
						ew.elseJoin();
						
					} else {
						System.out.println("insert 실패");
					}
				} else {
					System.out.println(blankId + "  id");	// ID 입력 칸이 공백인가. true / false.
					System.out.println(checkId + " checkId");
					System.out.println(pw1 + " pw1");
					System.out.println(pw2 + " pw2");
					System.out.println("아이디 & 비밀번호 확인"); // 이 부분 Label 추가.
				}
			}
		});
	}

	
}
