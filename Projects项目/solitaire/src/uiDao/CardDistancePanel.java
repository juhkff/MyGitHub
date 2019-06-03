package uiDao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import element.StaticData;
import tools.NumberDocument;
import uiPaint.GameEdit;
import uiPaint.GameIndex;

public class CardDistancePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameIndex jf;
	private JLabel distanceLabel;
	private JTextField distanceTextF;
	private JButton submitButton;
	private int newValue;

	public CardDistancePanel(GameIndex jf) {
		super();
		// TODO Auto-generated constructor stub
		this.jf = jf;
		init();
		listenerInit();

		this.add(distanceLabel);
		this.add(distanceTextF);
		this.add(submitButton);
		this.setSize(300, 30);
	}

	private void init() {
		this.setLayout(null);
		this.distanceLabel = new JLabel("卡牌上下间距:");
		this.distanceTextF = new JTextField();
		this.submitButton = new JButton("应用");

		this.distanceLabel.setBounds(0, 0, 140, 30);
		this.distanceTextF.setBounds(140, 0, 50, 30);
		this.submitButton.setBounds(200, 0, 100, 30);

		this.distanceLabel.setFont(StaticData.getEDITFONT());
		this.distanceTextF.setFont(StaticData.getEDITFONT2());
		this.submitButton.setFont(StaticData.getEDITFONT());

		this.distanceTextF.setDocument(new NumberDocument());
		this.distanceTextF.setText(String.valueOf(StaticData.getMinilocation(3)));
	}

	private void listenerInit() {
		this.submitButton.addActionListener(clickListener);
	}

	private ActionListener clickListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			newValue = Integer.parseInt(distanceTextF.getText());
			if (newValue > StaticData.getCARDHEIGHT())
				JOptionPane.showMessageDialog(getParent(), "数值过高", null, JOptionPane.INFORMATION_MESSAGE);
			else {
				StaticData.setMiniLocation3(newValue);
				StaticData.reDefine();
				jf.getJf().reSize();
				distanceTextF.setText(String.valueOf(newValue));
				JOptionPane.showMessageDialog(null, "应用成功!", null, JOptionPane.CLOSED_OPTION);
			}
		}
	};
}
