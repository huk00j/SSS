package GUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import TCPClient.ClientC;
import TCPClient.ClientO;
import javax.swing.JList;

public class Table2 extends JFrame {

	String header[] = { "No", "곡명", "가수명", "장르", "앨범" };
	public DefaultTableModel tableModel = new DefaultTableModel(null, header);
	JTable table = new JTable(tableModel);
	JScrollPane tableScroll = new JScrollPane(table);

	private JPanel contentPane;
	public JPanel panel = new JPanel();	// 클라이언트에서 변경하기 위해 private -> public 변경.
	private final JLabel lblNewLabel = new JLabel("ID");
	private final JTextField textField = new JTextField();
	private final JLabel lblNewLabel_1 = new JLabel("PW");
	private final JTextField textField_1 = new JTextField();
	private final JButton btnNewButton = new JButton("로그인");
	private final JButton btnNewButton_1 = new JButton("회원가입");
	private Table2 tt;

//	SongDAO dao = new SongDAO();
	ClientC Cc = null;
	ClientO Co = null;
	
	
	private final JPanel panel_2 = new JPanel();
	private final JList list = new JList();
	
	public final JLabel lblNewLabel_3 = new JLabel("");

	public JPanel panel_1;	// 로그인 후에 나오는 창.
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Table2 frame = new Table2();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 * @param clientO 
	 * @param clientC 
	 */
	
	
	public Table2(ClientC ClientC, ClientO clientO) {
		this.Cc = ClientC;
		this.Co = clientO;
		this.tt = this;	// 회원가입시 override 때문에 this가 ActionListner가 나오기 때문.
		
		table();

		textField_1.setBounds(89, 60, 116, 28);
		textField_1.setColumns(10);
		textField.setBounds(89, 20, 116, 28);
		textField.setColumns(10);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 200, 750, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tableScroll.setBounds(0, 0, 500, 562);
		contentPane.add(tableScroll);
		panel.setBounds(500, 0, 233, 210);

		contentPane.add(panel);
		panel.setLayout(null);
		lblNewLabel.setBounds(34, 20, 43, 28);

		panel.add(lblNewLabel);

		panel.add(textField);
		lblNewLabel_1.setBounds(32, 60, 37, 35);

		panel.add(lblNewLabel_1);

		panel.add(textField_1);
		btnNewButton.setBounds(26, 126, 87, 58);

		panel.add(btnNewButton);
		btnNewButton_1.setBounds(121, 126, 100, 58);

		panel.add(btnNewButton_1);
		lblNewLabel_3.setBounds(12, 93, 209, 26);
		lblNewLabel_3.setFont(new Font("굴림", Font.PLAIN, 12));
		
		panel.add(lblNewLabel_3);
		
		contentPane.setVisible(true);

		this.setVisible(true); // 창 보여주기.

		joinQ();
		
		
	//-------------- 여기부터 로그인 창 -----------------------------------------------
		panel_1 = new JPanel();
		panel_1.setBounds(500, 220, 234, 240);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		panel_2.setToolTipText("??? 님 환영");
		panel_2.setBounds(12, 10, 210, 30);
		
		panel_1.add(panel_2);
		panel_1.setVisible(false); // 로그인 후 나오는 창.
		
		
		JButton btnNewButton_2 = new JButton("내 정보");
		btnNewButton_2.setBounds(12, 50, 97, 40);
		panel_1.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("로그 아웃");
		btnNewButton_3.setBounds(121, 50, 101, 40);
		panel_1.add(btnNewButton_3);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(12, 140, 210, 90);
		panel_1.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("최근 재생 목록");
		lblNewLabel_2.setBounds(12, 100, 210, 30);
		panel_1.add(lblNewLabel_2);
		list.setBounds(12, 140, 210, 90);
		
		panel_1.add(list);
		
		afterLog();
	}

	
	public void joinQ() { // 회원 가입
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String jj = "회원가입신청";
//				Cc.send(jj);	// 굳이 DB까지 갈 필요 없으니 ClientC로 보내지 않고, 여기서 객체 생성.
				new Join2(tt, Cc);	// this 하면 액션리스너 자체가 보내진다??? 엥?
			}
		});
	}

	public void table() {
		Co.Taddress(this);	// ClientO 한테 Table2 주소 알려주기.
		
		String songlist = "노래목록불러오기";
		Co.sendO(songlist);
		
	}
	
	
	
	private void afterLog() {
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = textField.getText();
				String pw = textField_1.getText();
				String idpw = id+"/"+pw+"/접속";
				
				Cc.send(idpw);
				
			}
		});
		
		
	}
	
}













