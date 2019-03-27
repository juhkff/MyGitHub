/*
 *com.jlong.myDesign.java
 */
package live_UI;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.mysql.cj.PreparedQuery;

import live_Data.StaticData;
import live_JDBC.Conn;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UI_Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// ����һ��Panel��������Label��ǩ��ű���ͼƬ
	private JPanel contentPanel = new JPanel();
	// private JLabel label, label2;

	// ���ð�ť���
	private JButton login = new JButton("��¼"), registered = new JButton("ע��"), forgetPassword = new JButton("��������");

	// �����ı������
	private JTextField admin = new JTextField();
	private JPasswordField password = new JPasswordField();

	// �����ı�
	private JLabel adminText = new JLabel("�˺�");
	private JLabel passwordText = new JLabel("����");
	private JLabel noticeText = new JLabel();

	// ���ø�ѡ�����
	private JCheckBox rememberAdmin = new JCheckBox("��ס�˺�"), rememberPassword = new JCheckBox("��ס����");

	/*
	 * �ҵ����
	 */
	public UI_Login() {

		// ��ʼ�������
		// admin.setText("�˺�");
		// password.setText("����");

		/*
		 * // ʵ����ͼƬ ImageIcon image1 = new ImageIcon("D:\\study\\�����ļ�\\ͷ��2.jpg");
		 * ImageIcon image2 = new ImageIcon("D:\\study\\�����ļ�\\ͷ��.jpg");
		 * 
		 * JLabel backLabel = new JLabel(); JLabel backLabel2 = new JLabel();
		 * backLabel.setIcon(image1); backLabel2.setIcon(image2);
		 * 
		 * label = new JLabel(image1); label2 = new JLabel(image2); // ���ñ�ǩ��С��λ��
		 * label.setBounds(0, 0, 500, 350); label2.setBounds(0, 0, 501, 350); //
		 * ��LayeredPane��ײ������������ͼƬ�ı�ǩ������label2��label�Ϸ� this.getLayeredPane().add(label2,
		 * new Integer(Integer.MIN_VALUE)); this.getLayeredPane().add(label, new
		 * Integer(Integer.MIN_VALUE)); // �������������Ϊ͸�������ܹ����������LayeredPane�ϵı����� ((JPanel)
		 * this.getContentPane()).setOpaque(false);
		 * 
		 */

		/*
		 * ��������contentPanel������ ���ַ�ʽΪ���ɲ��֡�
		 */
		contentPanel.setLayout(null);
		add(admin);
		add(password);
		add(login);
		add(rememberAdmin);
		add(rememberPassword);
		add(registered);
		add(forgetPassword);
		add(adminText);
		add(passwordText);
		add(noticeText);
		/*
		 * �������λ��
		 */
		adminText.setBounds(95, 100, 40, 20);
		admin.setBounds(95, 115, 300, 25);
		passwordText.setBounds(95, 140, 40, 20);
		password.setBounds(95, 155, 300, 25);
		rememberAdmin.setBounds(95, 180, 100, 14);
		rememberPassword.setBounds(195, 180, 100, 14);
		noticeText.setBounds(95, 200, 200, 14);
		noticeText.setForeground(Color.RED);
		registered.setBounds(95, 225, 90, 20);
		forgetPassword.setBounds(205, 225, 90, 20);
		login.setBounds(315, 225, 90, 20);

		/*
		 * ���͸����
		 */
		admin.setOpaque(false);
		password.setOpaque(false);
		contentPanel.setOpaque(false);
		rememberAdmin.setOpaque(false);
		rememberPassword.setOpaque(false);
		getContentPane().add(contentPanel);

		/*
		 * ����߿���ɫ
		 */
		textSet(admin);
		textSet(password);
		// rememberAdmin.setBorder(new LineBorder(null, Color.OPAQUE));

		/*
		 * �����¼�
		 */
		admin.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int c = e.getButton();
				if (c == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
					admin.setText(null);
					// password.setText("����");
				}
			}
		});

		/*
		 * admin.addCaretListener(new CaretListener() { public void
		 * caretUpdate(CaretEvent e) {
		 * 
		 * } });
		 */
		password.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int c = e.getButton();
				if (c == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
					password.setText(null);
					// admin.setText("�˺�");
				}
			}
		});

		login.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int c = e.getButton();
				if (c == MouseEvent.BUTTON1) {
					String adminStr = admin.getText();
					if (adminStr.equals("")) {
						noticeText.setText("�˺�Ϊ�գ�������");
						// password.setText("����");
						return;
					} else if (new String(password.getPassword()).equals("")) {
						noticeText.setText("����Ϊ�գ�������");
						return;
					}
					char[] passwordStrs = password.getPassword();
					String passwordStr = new String(passwordStrs);
					String userName = null;
					Connection connection = Conn.getConnection();
					String sql = "select password,userName from userInfo where userId=?";
					String resultStr = null;
					try {
						PreparedStatement preparedStatement = connection.prepareStatement(sql);
						preparedStatement.setString(1, adminStr);
						ResultSet resultSet = preparedStatement.executeQuery();
						if (resultSet.next()) {
							resultStr = resultSet.getString("password");
							userName = resultSet.getString("userName");
							preparedStatement.close();
							Conn.Close();
						} else {
							noticeText.setText("�˺Ŵ�������������");
							// password.setText("����");
							preparedStatement.close();
							Conn.Close();
							return;
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						Conn.Close();
						e1.printStackTrace();
					}
					if (resultStr != null && resultStr.equals(passwordStr)) {
						// �����¸�����
						dispose();
						new UI_NetWorkConfig(userName);

					} else {
						// �������
						noticeText.setText("���������������������");
						return;
					}
				}
			}
		});

		registered.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				UI_Register ui_Register = new UI_Register();
				ui_Register.setTitle("ֱ��ע��");
				// ui_Register.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				// ���ô��ڴ�С���ɱ�
				ui_Register.setResizable(false);
				// ���ô��ڴ򿪾���
				ui_Register.setLocationRelativeTo(null);
				// ���ڴ�С
				ui_Register.setSize(500, 400);
				// չʾ����
				ui_Register.setVisible(true);
			}
		});
	}

	/*
	 * JTextField�ı������÷���.
	 */
	public void textSet(JTextField field) {
		field.setBackground(new Color(255, 255, 255));
		field.setPreferredSize(new Dimension(150, 28));
		MatteBorder border = new MatteBorder(0, 0, 2, 0, new Color(192, 192, 192));
		field.setBorder(border);
	}

	public static void main(String[] args) {
		JFrame jf = new UI_Login();
		jf.setTitle("��¼ֱ��");
		// �����˳���Ϊ
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ���ô��ڴ�С���ɱ�
		jf.setResizable(false);
		// ���ô��ڴ򿪾���
		jf.setLocationRelativeTo(null);
		// ���ڴ�С
		jf.setSize(500, 400);
		// չʾ����
		jf.setVisible(true);
	}

}
