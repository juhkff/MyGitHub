package live_UI;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import live_Data.StaticData;

public class UI_NetWorkConfig {
	private innerUI tempUI;
	private String userName;

	public UI_NetWorkConfig(String userName) {
		// TODO Auto-generated method stub
		this.tempUI = new innerUI();
		this.userName = userName;
		tempUI.setTitle("���Ե�...");
		tempUI.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		tempUI.setResizable(true);
		tempUI.setLocationRelativeTo(null);
		tempUI.setSize(400, 200);
		tempUI.setVisible(true);

		new processNetWorkConfig().execute();
	}

	private class innerUI extends JFrame {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JPanel contentPanel = new JPanel();
		private JLabel textLabel = new JLabel("����������...");

		public innerUI() {
			// TODO Auto-generated constructor stub
			contentPanel.setLayout(null);
			add(textLabel);
			textLabel.setBounds(120, 26, 200, 100);
			textLabel.setForeground(Color.BLUE);
			textLabel.setBackground(Color.DARK_GRAY);
			textLabel.setFont(new Font("����", 1, 25));
			getContentPane().add(contentPanel);
		}
	}

	private class processNetWorkConfig extends SwingWorker<Void, Void> {

		@Override
		protected Void doInBackground() throws Exception {
			// TODO Auto-generated method stub
			StaticData.getHOSTNAME();
			return null;
		}

		@Override
		protected void done() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempUI.dispose();
			JFrame ui_HomePage = new UI_HomePage(userName);
			// ui_HomePage.setTitle("������ֱ������-" + userName);
			// ui_HomePage.setTitle("����������...");
			// ui_Register.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			// ���ô��ڴ�С���ɱ�
			ui_HomePage.setResizable(false);
			// ���ô��ڴ򿪾���
			ui_HomePage.setLocationRelativeTo(null);
			// ���ڴ�С
			ui_HomePage.setSize(750, 500);
			// չʾ����
			ui_HomePage.setVisible(true);

			// StaticData.getHOSTNAME();
			// tempUI.dispose();

			ui_HomePage.setTitle("������ֱ������-" + userName);
		}
	}
}
