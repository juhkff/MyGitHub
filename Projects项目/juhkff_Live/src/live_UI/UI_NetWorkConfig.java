package live_UI;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UI_NetWorkConfig extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel = new JPanel();
	private JLabel textLabel = new JLabel("Õ¯¬Á≈‰÷√÷–...");

	public UI_NetWorkConfig() {
		contentPanel.setLayout(null);
		add(textLabel);
		contentPanel.setBackground(Color.GREEN);
		textLabel.setBounds(0, 0, 200, 100);
		textLabel.setForeground(Color.BLUE);
		textLabel.setBackground(Color.DARK_GRAY);
		//textLabel.setFont(new Font("ÀŒÃÂ", 1, 30));
		
		getContentPane().add(contentPanel);
	}
}
