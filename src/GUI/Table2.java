package GUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import TCPClient.ClientC;
import TCPClient.ClientO;
import javax.swing.ScrollPaneConstants;

public class Table2 extends JFrame {

	String header[] = { "No", "곡명", "가수명", "장르", "앨범" };
	String header2[] = { "곡명", "가수명", "장르"};
	public String header4[] = new String[3];
	
	public DefaultTableModel tableModel = new DefaultTableModel(null, header) {
		public boolean isCellEditable(int a, int b) { // isCellEditable 테이블 변화를 막는다.
			return false;
		}
	};

	public JTable table = new JTable(tableModel);
	JScrollPane tableScroll = new JScrollPane(table);

	private JPanel contentPane;
	public JPanel panel = new JPanel(); // 클라이언트에서 변경하기 위해 private -> public 변경.
	private final JLabel lblNewLabel = new JLabel("ID");
	public JTextField textField = new JTextField(); // ID 가져와서 띄워주기 위함.
	private final JLabel lblNewLabel_1 = new JLabel("PW");
	private final JTextField textField_1 = new JTextField();
	private final JButton btnNewButton = new JButton("로그인");
	private final JButton btnNewButton_1 = new JButton("회원가입");
	private Table2 tt;

	ClientC Cc = null;
	ClientO Co = null;

	public final JLabel lblNewLabel_3 = new JLabel("");

	public JPanel panel_1; // 로그인 후에 나오는 창.
	private final JButton btnNewButton_6 = new JButton("재생");
	private final JButton btnNewButton_7 = new JButton("정지");
	JTable table_1;
	public DefaultTableModel tableModel_1;

	public Boolean loginID;
	
	
	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { Table2 frame = new Table2();
	 * frame.setVisible(true); } catch (Exception e) { e.printStackTrace(); } } });
	 * }
	 */
	/**
	 * Create the frame.
	 * 
	 * @param clientO
	 * @param clientC
	 */

	public Table2(ClientC ClientC, ClientO clientO) {
		this.Cc = ClientC;
		this.Co = clientO;
		this.tt = this; // 회원가입시 override 때문에 this가 ActionListner가 나오기 때문.

		table();

		textField_1.setBounds(85, 60, 115, 30);
		textField_1.setColumns(10);
		panel.add(textField_1);

		textField.setBounds(85, 20, 115, 30);
		textField.setColumns(10);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 750, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tableScroll.setBounds(0, 0, 500, 562);
		contentPane.add(tableScroll);
		panel.setBounds(500, 0, 233, 210);

		contentPane.add(panel);
		panel.setLayout(null);

		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 14));
		lblNewLabel.setBounds(30, 20, 43, 28);
		panel.add(lblNewLabel);

		panel.add(textField);
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(30, 60, 37, 35);
		panel.add(lblNewLabel_1);

		btnNewButton.setBounds(26, 126, 87, 58);
		panel.add(btnNewButton);

		btnNewButton_1.setBounds(121, 126, 100, 58);
		panel.add(btnNewButton_1);

		lblNewLabel_3.setBounds(12, 93, 209, 26);
		lblNewLabel_3.setFont(new Font("굴림", Font.PLAIN, 12));

		panel.add(lblNewLabel_3);

		btnNewButton_6.setBounds(512, 490, 97, 62);
		contentPane.add(btnNewButton_6);

		btnNewButton_7.setBounds(625, 490, 97, 62);
		contentPane.add(btnNewButton_7);

		this.setVisible(true); // 창 보여주기.

		// -------- 노래 리스트 --------------------------------------------

		sList();	// 오른쪽 테이블 만들기.
		// --------------------------------------------------------------

		logQ(); // 로그인했을 때 창.
		joinQ(); // 회원가입 버튼.
		afterLog(); // 로그인 버튼.
		
		mClick(); // 마우스 더블 클릭. -> 왼쪽 테이블 값을 오른쪽 테이블 값에 띄워주기.

	}

	public String guest = "";
//	private final JTable table_1 = new JTable();

	public void logQ() {

		panel_1 = new JPanel();
		panel_1.setBounds(500, 0, 233, 210);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		panel_1.setVisible(false); // 로그인 후 나오는 창.

		JLabel lblNewLabel_2 = new JLabel(guest + "님 환영합니다.");
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 12));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(12, 10, 210, 30);
		panel_1.add(lblNewLabel_2);

		JButton btnNewButton_2 = new JButton("내 정보");
		btnNewButton_2.setFont(new Font("굴림", Font.PLAIN, 12));
		btnNewButton_2.setBounds(12, 50, 97, 35);
		panel_1.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("로그 아웃");
		btnNewButton_3.setFont(new Font("굴림", Font.PLAIN, 12));
		btnNewButton_3.setBounds(121, 50, 97, 35);
		panel_1.add(btnNewButton_3);

		JButton btnNewButton_4 = new JButton("최근 재생");
		btnNewButton_4.setFont(new Font("굴림", Font.PLAIN, 12));
		btnNewButton_4.setBounds(12, 95, 97, 35);
		panel_1.add(btnNewButton_4);

		JButton btnNewButton_5 = new JButton("추천 목록");
		btnNewButton_5.setFont(new Font("굴림", Font.PLAIN, 12));
		btnNewButton_5.setBounds(121, 95, 97, 35);
		panel_1.add(btnNewButton_5);
		
		
	}

	public void sList() {
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(500, 220, 233, 222);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		tableModel_1 = new DefaultTableModel(null, header2){
			public boolean isCellEditable(int a, int b) { // isCellEditable 테이블 변화를 막는다.
				return false;
			}
		};
		table_1 = new JTable(tableModel_1);
		JScrollPane scrollPane2 = new JScrollPane(table_1) ; /*{
				public void setBorder(Border border){	// 테두리 실선 제거하는 방법.
		}
	}; */
	
		scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane2.setBounds(12, 10, 210, 202);

		//		scrollPane2.add(table_1);	// 망할 이 녀석 때문.
		
		panel_2.add(scrollPane2);
		
//		table_1.getTableHeader().setVisible(false);	// 이렇게 해줘야 헤더가 사라진다. // 다시 true로 바꿔보자.
		table_1.setShowVerticalLines(false);
		table_1.setShowHorizontalLines(false);
		
	}
	
	public void viewList() {
		
	}
	
	public void nonguest() {	// 로그인시 리스트 제거.
		final int rowNum = tableModel_1.getRowCount();
		for(int i = 0 ; i < rowNum ; i++) {
			System.out.println(rowNum);
			tableModel_1.removeRow(0);
		}
	}
	
	
	public void mClick() {	// table 에서 목록 더블 클릭시 오른쪽 재생목록에 추가.
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = table.getSelectedRow();
					for(int i = 0 ; i < 3; i++) {
						header4[i] = (String) table.getValueAt(row, i+1);
					}
					Co.divid(header4);
					
				}
			}
		}); 
	}
	
	
// ☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★
	
	public void joinQ() { // 회원 가입
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String jj = "회원가입신청";
//				Cc.send(jj);	// 굳이 DB까지 갈 필요 없으니 ClientC로 보내지 않고, 여기서 객체 생성.
				new Join2(tt, Cc); // this 하면 액션리스너 자체가 보내진다??? 엥?
			}
		});
	}
	

	public void table() {
		Co.Taddress(this); // ClientO 한테 Table2 주소 알려주기.

		String songlist = "노래목록불러오기";
		Co.sendO(songlist);
	}

	
	private void afterLog() {
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String id = textField.getText();
				String pw = textField_1.getText();
				String idpw = id + "/" + pw + "/접속";

				Cc.send(idpw);
			}
		});
	}
}
