package test;

//СӦ�ó���������JPanel��������һ����ť��ÿ��JPanel�����������������ť��һ����ǩ

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
		Label = new JLabel("��ǩ�����������1��");
		Label.setBounds(0, 0, 200, 100);
		this.add(Label);
		this.setBounds(0, 0, 150, 50);
	}

	public static void main(String[] args) {
		JFrame jFrame = new JFrame();
		MyPanel panel1, panel2;
		panel1 = new MyPanel("ȷ��", "ȡ��", "��ǩ�����������1��");
		panel2 = new MyPanel("ȷ��", "ȡ��", "��ǩ�����������2��");
		// Button = new JButton("���ǲ�������еİ�ť");
		jFrame.add(panel1);
		jFrame.add(panel2);
		// jFrame.add(Button);
		jFrame.setSize(300, 200);
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
