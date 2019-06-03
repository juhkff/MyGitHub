package uiDao;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import element.StaticData;

public class GameFoot extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6117957337040175320L;

	private JLabel footLabel;

	public GameFoot() {
		super();
		// TODO Auto-generated constructor stub
		init();
		this.setLayout(null);
		this.add(footLabel);
		this.setBounds(0, StaticData.getFRAMEHEIGHT() - 105, StaticData.getFRAMEWIDTH(), 30);
		this.setBackground(Color.decode("#d9dadc"));
		this.setBorder(BorderFactory.createEtchedBorder());
	}

	private void init() {
		footLabel = new JLabel();
		footLabel.setBounds(0, 0, StaticData.getFRAMEWIDTH(), 30);
		footLabel.setFont(StaticData.getFootfont());
	}

	private void setText(String text) {
		footLabel.setText(text);
	}

	private void setTextColor(Color color) {
		footLabel.setForeground(color);
	}

	public void reset() {
		footLabel.setText("");
		footLabel.setForeground(Color.BLACK);
	}

	public void setTextToClicked(CardPanel cardPanel) {
		setText("бЁжа" + cardPanel.getCardNumber());
		if (cardPanel.getCardValue().startsWith("A") || cardPanel.getCardValue().startsWith("B"))
			setTextColor(Color.RED);
		else
			setTextColor(Color.BLACK);
	}
}
