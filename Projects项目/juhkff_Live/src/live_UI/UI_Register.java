package live_UI;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import live_JDBC.Conn;

public class UI_Register extends JFrame {
	private JPanel jp = new JPanel();
	private JButton jb_back = new JButton("返回"), jb_submit = new JButton("提交");
	private JTextField jt_Id = new JTextField(), jt_Name = new JTextField();
	private JPasswordField jt_passWord = new JPasswordField();
	private JLabel jl_Id = new JLabel("账号"), jl_passWord = new JLabel("密码"), jl_Name = new JLabel("昵称");
	private JLabel notice=new JLabel();
	
	public UI_Register() {
		jp.setLayout(null);
		add(jl_Id);
		add(jl_passWord);
		add(jl_Name);
		add(jt_Id);
		add(jt_passWord);
		add(jt_Name);
		add(jb_back);
		add(jb_submit);
		add(notice);
		
		jl_Id.setBounds(150, 30, 200, 30);
		jt_Id.setBounds(150, 60, 200, 30);
		jl_passWord.setBounds(150, 90, 200, 30);
		jt_passWord.setBounds(150, 120, 200, 30);
		jl_Name.setBounds(150, 150, 200, 30);
		jt_Name.setBounds(150, 180, 200, 30);
		jb_back.setBounds(150, 250, 60, 30);
		jb_submit.setBounds(290, 250, 60, 30);
		notice.setBounds(150, 210, 200, 30);
		notice.setForeground(Color.RED);
		
		getContentPane().add(jp);
		
		jb_back.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		
		jb_submit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String id=jt_Id.getText();
				String password=new String(jt_passWord.getPassword());
				String name=jt_Name.getText();
				if(id.equals("")||password.equals("")||name.equals("")) {
					notice.setText("请填满所有选框");
					return;
				}else {
					notice.setText("");
					//验证重复
					Connection connection=Conn.getConnection();
					String sql="select * from userInfo where userId=? or userName=?";
					try {
						PreparedStatement preparedStatement=connection.prepareStatement(sql);
						preparedStatement.setString(1, id);
						preparedStatement.setString(2, name);
						ResultSet resultSet=preparedStatement.executeQuery();
						while(resultSet.next()) {
							if(resultSet.getString("userId").equals(id)) {
								preparedStatement.close();
								Conn.Close();
								notice.setText("账号已注册，请更换");
								return;
							}else if(resultSet.getString("userName").equals(name)) {
								preparedStatement.close();
								Conn.Close();
								notice.setText("昵称已使用，请更换");
								return;
							}
							break;
						}
						sql="insert into userInfo(userId,password,userName) values(?,?,?)";
						preparedStatement=connection.prepareStatement(sql);
						preparedStatement.setString(1, id);
						preparedStatement.setString(2, password);
						preparedStatement.setString(3, name);
						int result=preparedStatement.executeUpdate();
						if(result>0) {
							//注册成功
							notice.setText("注册成功!");
						}else {
							notice.setText("注册失败...");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						Conn.Close();
						e1.printStackTrace();
					}
					
				}
			}
		});
	}
}
