package test;

import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import element.StaticData;
import uiDao.DealStackPanel;

public class Index extends JFrame {
	private JPanel contentPanel = new JPanel();
	private MyPanel myPanel;
	
	public Index() {
		myPanel=new MyPanel("确定", "取消", "标签，我们在面板1中");
		contentPanel.setLayout(null);
		//dealStackPanel.setBorder(BorderFactory.createLineBorder(Color.red, 3));
		add(new MyPanel("确定", "取消", "标签，我们在面板1中"));
		//add(new CardPanel("A1"));
		//contentPanel.setOpaque(false);
		getContentPane().add(contentPanel);
		//getContentPane().setVisible(true);
		//add(contentPanel);
		//add(myPanel);
		//setSize(300, 200);
		//setVisible(true);
		//setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		JFrame jFrame=new Index();
		jFrame.setSize(300, 200);
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
	}
}
