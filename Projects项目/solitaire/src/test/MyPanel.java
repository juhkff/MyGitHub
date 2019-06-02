package test;

//小应用程序有两个JPanel子类对象和一个按钮。每个JPanel子类对象又有两个按钮和一个标签

import javax.swing.*;

public class MyPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// JButton button1, button2;
	JLabel Label;

	public MyPanel(String s1, String s2, String s3) {
		super();
		Label = new JLabel("标签，我们在面板1中");
		Label.setBounds(0, 0, 200, 100);
		this.add(Label);
		this.setBounds(0, 0, 150, 50);
	}

	public static void main(String[] args) {
		JFrame jFrame = new JFrame();
		MyPanel panel1, panel2;
		panel1 = new MyPanel("确定", "取消", "标签，我们在面板1中");
		panel2 = new MyPanel("确定", "取消", "标签，我们在面板2中");
		// Button = new JButton("我是不在面板中的按钮");
		jFrame.add(panel1);
		jFrame.add(panel2);
		// jFrame.add(Button);
		jFrame.setSize(300, 200);
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
