package uiDao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import element.StaticData;

public class CardAutoCheckPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel autoCheckLabel;
	private ButtonGroup buttonGroup;
	private JRadioButton check;
	private JRadioButton notCheck;

	public CardAutoCheckPanel() {
		// TODO Auto-generated constructor stub

		init();
		listenerInit();
		this.setLayout(null);

		this.add(autoCheckLabel);
		this.add(check);
		this.add(notCheck);

		this.setSize(autoCheckLabel.getWidth() + check.getWidth() + notCheck.getWidth() + 10,
				autoCheckLabel.getHeight());
	}

	private void init() {
		this.autoCheckLabel = new JLabel("开启中键自动检查:");
		this.buttonGroup = new ButtonGroup();
		if (StaticData.isCardAutoCheck()) {
			this.check = new JRadioButton("是", true);
			this.notCheck = new JRadioButton("否");
		} else {
			this.check = new JRadioButton("是");
			this.notCheck = new JRadioButton("否", true);
		}
		this.autoCheckLabel.setBounds(0, 0, 200, 30);
		this.check.setBounds(205, 0, 50, 30);
		this.notCheck.setBounds(260, 0, 50, 30);

		this.autoCheckLabel.setFont(StaticData.getEDITFONT());
		this.check.setFont(StaticData.getEDITFONT());
		this.notCheck.setFont(StaticData.getEDITFONT());

		this.buttonGroup.add(check);
		this.buttonGroup.add(notCheck);
	}

	private void listenerInit() {
		this.check.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				StaticData.setCardAutoCheck(true);
			}
		});
		this.notCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				StaticData.setCardAutoCheck(false);
			}
		});
	}
}
