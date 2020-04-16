package GUI;

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

public class Table2 extends JFrame {

	String header[] = { "No", "곡명", "가수명", "장르", "앨범" };
	public DefaultTableModel tableModel = new DefaultTableModel(null, header);
	JTable table = new JTable(tableModel);
	JScrollPane tableScroll = new JScrollPane(table);

	private JPanel contentPane;
	private final JPanel panel = new JPanel();
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

		textField_1.setBounds(89, 88, 116, 28);
		textField_1.setColumns(10);
		textField.setBounds(89, 31, 116, 28);
		textField.setColumns(10);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 200, 750, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tableScroll.setBounds(0, 0, 500, 562);
		contentPane.add(tableScroll);
		panel.setBounds(500, 0, 233, 250);

		contentPane.add(panel);
		panel.setLayout(null);
		lblNewLabel.setBounds(34, 30, 43, 28);

		panel.add(lblNewLabel);

		panel.add(textField);
		lblNewLabel_1.setBounds(32, 84, 37, 35);

		panel.add(lblNewLabel_1);

		panel.add(textField_1);
		btnNewButton.setBounds(26, 126, 87, 58);

		panel.add(btnNewButton);
		btnNewButton_1.setBounds(121, 126, 100, 58);

		panel.add(btnNewButton_1);
		contentPane.setVisible(true);

		this.setVisible(true); // 창 보여주기.

		joinQ();
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
		
	//-------------------------------------------------
		
//		ArrayList<String[]> tList = new ArrayList<>();
//		tList = dao.tableList();
//		for (int i = 0; i < tList.size(); i++) {
//			tableModel.addRow(tList.get(i));
//		} 

	}

}
