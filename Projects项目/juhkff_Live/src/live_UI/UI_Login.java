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

	// 设置一个Panel容器面板和Label标签存放背景图片
	private JPanel contentPanel = new JPanel();
	// private JLabel label, label2;

	// 设置按钮组件
	private JButton login = new JButton("登录"), registered = new JButton("注册"), forgetPassword = new JButton("忘记密码");

	// 设置文本框组件
	private JTextField admin = new JTextField();
	private JPasswordField password = new JPasswordField();

	// 设置文本
	private JLabel adminText = new JLabel("账号");
	private JLabel passwordText = new JLabel("密码");
	private JLabel noticeText = new JLabel();

	// 设置复选框组件
	private JCheckBox rememberAdmin = new JCheckBox("记住账号"), rememberPassword = new JCheckBox("记住密码");

	/*
	 * 我的设计
	 */
	public UI_Login() {

		// 初始化各组件
		// admin.setText("账号");
		// password.setText("密码");

		/*
		 * // 实例化图片 ImageIcon image1 = new ImageIcon("D:\\study\\各种文件\\头像2.jpg");
		 * ImageIcon image2 = new ImageIcon("D:\\study\\各种文件\\头像.jpg");
		 * 
		 * JLabel backLabel = new JLabel(); JLabel backLabel2 = new JLabel();
		 * backLabel.setIcon(image1); backLabel2.setIcon(image2);
		 * 
		 * label = new JLabel(image1); label2 = new JLabel(image2); // 设置标签大小与位置
		 * label.setBounds(0, 0, 500, 350); label2.setBounds(0, 0, 501, 350); //
		 * 在LayeredPane最底层上添加两个带图片的标签，并且label2在label上方 this.getLayeredPane().add(label2,
		 * new Integer(Integer.MIN_VALUE)); this.getLayeredPane().add(label, new
		 * Integer(Integer.MIN_VALUE)); // 将内容面板设置为透明，就能够看见添加在LayeredPane上的背景。 ((JPanel)
		 * this.getContentPane()).setOpaque(false);
		 * 
		 */

		/*
		 * 添加组件到contentPanel容器中 布局方式为自由布局。
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
		 * 组件绝对位置
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
		 * 组件透明化
		 */
		admin.setOpaque(false);
		password.setOpaque(false);
		contentPanel.setOpaque(false);
		rememberAdmin.setOpaque(false);
		rememberPassword.setOpaque(false);
		getContentPane().add(contentPanel);

		/*
		 * 组件边框颜色
		 */
		textSet(admin);
		textSet(password);
		// rememberAdmin.setBorder(new LineBorder(null, Color.OPAQUE));

		/*
		 * 监听事件
		 */
		admin.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int c = e.getButton();
				if (c == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
					admin.setText(null);
					// password.setText("密码");
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
					// admin.setText("账号");
				}
			}
		});

		login.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int c = e.getButton();
				if (c == MouseEvent.BUTTON1) {
					String adminStr = admin.getText();
					if (adminStr.equals("")) {
						noticeText.setText("账号为空，请输入");
						// password.setText("密码");
						return;
					} else if (new String(password.getPassword()).equals("")) {
						noticeText.setText("密码为空，请输入");
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
							noticeText.setText("账号错误，请重新输入");
							// password.setText("密码");
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
						// 进入下个界面
						dispose();
						new UI_NetWorkConfig(userName);

					} else {
						// 密码错误
						noticeText.setText("密码错误，请重新输入密码");
						return;
					}
				}
			}
		});

		registered.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				UI_Register ui_Register = new UI_Register();
				ui_Register.setTitle("直播注册");
				// ui_Register.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				// 设置窗口大小不可变
				ui_Register.setResizable(false);
				// 设置窗口打开居中
				ui_Register.setLocationRelativeTo(null);
				// 窗口大小
				ui_Register.setSize(500, 400);
				// 展示窗口
				ui_Register.setVisible(true);
			}
		});
	}

	/*
	 * JTextField文本框设置方法.
	 */
	public void textSet(JTextField field) {
		field.setBackground(new Color(255, 255, 255));
		field.setPreferredSize(new Dimension(150, 28));
		MatteBorder border = new MatteBorder(0, 0, 2, 0, new Color(192, 192, 192));
		field.setBorder(border);
	}

	public static void main(String[] args) {
		JFrame jf = new UI_Login();
		jf.setTitle("登录直播");
		// 窗口退出行为
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 设置窗口大小不可变
		jf.setResizable(false);
		// 设置窗口打开居中
		jf.setLocationRelativeTo(null);
		// 窗口大小
		jf.setSize(500, 400);
		// 展示窗口
		jf.setVisible(true);
	}

}
