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

	public JLabel lblNewLabel_2, lblNewLabel_3, lblNewLabel_4;
	JTextField textField_1, textField_2;
	JLabel lblNewLabel_5, lblNewLabel_6;
	JButton btnNewButton_1;

	JLabel lblNewLabel_7, lblNewLabel_8, lblNewLabel_9, lblNewLabel_10;
	JLabel lblNewLabel_11;

	ClientC Cc = null;
	Table2 table = null;
	
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

	public Join2(Table2 tt) {
		this.table = tt;
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
//		ins();
	}

	public void checkA() {
		lblNewLabel_2 = new JLabel("이미 존재하는 계정입니다.");
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 12));
		lblNewLabel_2.setForeground(Color.RED);
		lblNewLabel_2.setBounds(50, 75, 150, 15);
		contentPane.add(lblNewLabel_2);
		lblNewLabel_2.setVisible(false);

		lblNewLabel_3 = new JLabel("사용 가능한 계정입니다.");
		lblNewLabel_3.setFont(new Font("굴림", Font.PLAIN, 12));
		lblNewLabel_3.setForeground(Color.BLUE);
		lblNewLabel_3.setBounds(50, 75, 140, 15);
		contentPane.add(lblNewLabel_3);
		lblNewLabel_3.setVisible(false);

		lblNewLabel_4 = new JLabel("아이디를 입력하세요.");
		lblNewLabel_4.setFont(new Font("굴림", Font.PLAIN, 12));
		lblNewLabel_4.setForeground(Color.MAGENTA);
		lblNewLabel_4.setBounds(60, 75, 140, 15);
		contentPane.add(lblNewLabel_4);
		lblNewLabel_4.setVisible(false);

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

	
	int qq = 0 ;
	public void ActionID() {	//--------------------------------------- 계정 중복 체크용.
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String idcc = "계정중복확인/";
				String idccc = txtAsdfdsfd.getText();
//				Cc.send(idcc+idccc);
				
				//------------------------------------------------------
				
				String in = txtAsdfdsfd.getText();

				if (txtAsdfdsfd.getText().equals("")) {
					lblNewLabel_2.setVisible(false);
					lblNewLabel_3.setVisible(false);
					lblNewLabel_4.setVisible(true);
					cheId = 0;
				} else {
					qq = 5;	// 꼭 중복 확인 눌러야 아이디 생성이 가능하게끔.
					checkID(in);
				}
			}
		});
	}

	int cheId = 0;

	public void checkID(String in) {
		int check = ldao.idCheck(in);
		System.out.println(check + " << 뭐가 나오니");
		if (check == 0) {
			System.out.println("없는 아이디");
			lblNewLabel_2.setVisible(false);
			lblNewLabel_4.setVisible(false);
			lblNewLabel_3.setVisible(true);
			cheId = 5;
		} else if (check == 1) {
			System.out.println("이미 있는 아이디");
			lblNewLabel_4.setVisible(false);
			lblNewLabel_3.setVisible(false);
			lblNewLabel_2.setVisible(true);
			cheId = 0;
		}
	}

	
	int chePw = 0;	//-------------------------------------------------------- 중복 체크용.
	int k = 0;
	public void ActionPW1() {
		textField_1.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override	// 키보드 떼자마자.
			public void keyReleased(KeyEvent e) {
				if (textField_1.getText().length() < 4) {
					System.out.println("4자 이상이어야 합니다.");
					lblNewLabel_11.setVisible(true); // 4글자 이상
					lblNewLabel_7.setVisible(true); // 빨강
					lblNewLabel_9.setVisible(false); // 파랑
					k = 0;
				} else if (textField_1.getText().length() >= 4) {
					k = 2;
					System.out.println("4자 이상이다!!!");
					lblNewLabel_11.setVisible(false); // 4글자 이상
					lblNewLabel_7.setVisible(false); // 빨강
					lblNewLabel_9.setVisible(true); // 파랑
				}

				if (k == 2) {
					if (textField_1.getText().equals(textField_2.getText())) {
						System.out.println("비밀번호 일치");
						lblNewLabel_5.setVisible(false);
						lblNewLabel_6.setVisible(true);

						chePw = 5;
					} else {
						lblNewLabel_6.setVisible(false);
						lblNewLabel_5.setVisible(true);
						chePw = 0;
					}
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
	}

	public void ActionPW2() {
		textField_2.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (textField_1.getText().equals(textField_2.getText())) {
					System.out.println("비밀번호 일치");
					lblNewLabel_5.setVisible(false);
					lblNewLabel_6.setVisible(true);

					lblNewLabel_8.setVisible(false); // 빨간 자물쇠.
					lblNewLabel_10.setVisible(true); // 파란 자물쇠.

					chePw = 5;
				} else {
					System.out.println("비밀번호 일치하지 않아!");
					lblNewLabel_6.setVisible(false);
					lblNewLabel_5.setVisible(true);

					lblNewLabel_8.setVisible(true); // 빨간 자물쇠.
					lblNewLabel_10.setVisible(false); // 파란 자물쇠.

					chePw = 0;
				}

			}
			//실험용
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
	}

//	public void ins() {
//		if (cheId == 5 && chePw == 5 && k == 2 && qq == 5) {
//			insert();
//		} else {
//			System.out.println("아이디, 패스워드 체크");
//		}
//	}

	public void insert() {
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				String IdPw[] = new String[2];
				if (cheId == 5 && chePw == 5 && k == 2 && qq == 5) {
//					IdPw[0] = txtAsdfdsfd.getText();
					String id = txtAsdfdsfd.getText();
					txtAsdfdsfd.setText("");
//					IdPw[1] = textField_2.getText();
					String pw = textField_2.getText();
					textField_1.setText("");
					textField_2.setText("");

					lblNewLabel_3.setVisible(false); // 파란색 id 글 제거.
					lblNewLabel_6.setVisible(false); // 파란색 pw 글 제거.

					int k = ldao.insertQ(id, pw);

					if (k == 1) {
						System.out.println("insert 성공");
					} else {
						System.out.println("insert 실패");
					}

				} else {
					System.out.println("아이디 & 비밀번호 확인"); // 이 부분 Label 추가.
				}
			}
		});
	}

}
