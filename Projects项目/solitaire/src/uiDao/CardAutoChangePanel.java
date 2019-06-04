package uiDao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import element.StaticData;

public class CardAutoChangePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel autoChangeLabel;
	private ButtonGroup buttonGroup;
	private JRadioButton change;
	private JRadioButton notChange;

	public CardAutoChangePanel() {
		super();
		// TODO Auto-generated constructor stub
		init();
		listenerInit();
		this.setLayout(null);

		this.add(autoChangeLabel);
		this.add(change);
		this.add(notChange);

		this.setSize(autoChangeLabel.getWidth() + change.getWidth() + notChange.getWidth() + 10,
				autoChangeLabel.getHeight());
	}

	private void init() {
		this.autoChangeLabel = new JLabel("背面卡牌自动翻面:");
		this.buttonGroup = new ButtonGroup();
		if (StaticData.isCardAutoChange()) {
			this.change = new JRadioButton("是", true);
			this.notChange = new JRadioButton("否");
		} else {
			this.change = new JRadioButton("是");
			this.notChange = new JRadioButton("否", true);
		}
		this.autoChangeLabel.setBounds(0, 0, 200, 30);
		this.change.setBounds(205, 0, 50, 30);
		this.notChange.setBounds(260, 0, 50, 30);

		this.autoChangeLabel.setFont(StaticData.getEDITFONT());
		this.change.setFont(StaticData.getEDITFONT());
		this.notChange.setFont(StaticData.getEDITFONT());

		this.buttonGroup.add(change);
		this.buttonGroup.add(notChange);
	}

	private void listenerInit() {
		this.change.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				StaticData.setCardAutoChange(true);
			}
		});
		this.notChange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				StaticData.setCardAutoChange(false);
			}
		});
	}
}
