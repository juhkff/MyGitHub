package uiDao;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import element.StaticData;
import tools.NumberDocument;
import uiPaint.GameIndex;

public class CardSizePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GameIndex jf;
	private JLabel cardWidthLabel;
	private JLabel cardHeightLabel;
	private JTextField widthF;
	private JTextField heightF;
	private JButton submitButton;

	private int newWidth;
	private int newHeight;

	public CardSizePanel(GameIndex jf) {
		super();
		// TODO Auto-generated constructor stub
		this.jf = jf;
		this.setLayout(null);
		init();
		listenerInit();

		this.add(cardWidthLabel);
		this.add(cardHeightLabel);
		this.add(widthF);
		this.add(heightF);
		this.add(submitButton);

		this.setSize(270, 62);
	}

	private void listenerInit() {
		// TODO Auto-generated method stub
		submitButton.addActionListener(clickListener);
	}

	private ActionListener clickListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			newWidth = Integer.parseInt(widthF.getText());
			newHeight = Integer.parseInt(heightF.getText());
			if (newWidth > StaticData.getFRAMEWIDTH() / 7 || newHeight > StaticData.getFRAMEHEIGHT()) {
				JOptionPane.showMessageDialog(CardSizePanel.this, "��ֵ����", null, JOptionPane.INFORMATION_MESSAGE);
			} else {
				StaticData.setCARDWIDTH(newWidth);
				StaticData.setCARDHEIGHT(newHeight);
				StaticData.setCARDSIZEChanged(true);
				StaticData.reDefine();
				jf.getJf().reSize();
				widthF.setText(String.valueOf(newWidth));
				heightF.setText(String.valueOf(newHeight));
				resetMinWidth();
				JOptionPane.showMessageDialog(null, "Ӧ�óɹ�!", null, JOptionPane.CLOSED_OPTION);
			}
		}
	};

	private void resetMinWidth() {
		int minWidth1, minWidth2;
		minWidth1 = newWidth * 5 + StaticData.getGATHERCARDLENGTH() * 6 + StaticData.getDealedlocation(0);
		minWidth2 = newWidth * 7;
		Dimension dimension;
		if (minWidth1 < minWidth2) {
			dimension = new Dimension(minWidth2, StaticData.getFrameminheight());
			System.out.println("�Ƚ����,ѡ��minWidth2: " + minWidth2);
		} else {
			dimension = new Dimension(minWidth1, StaticData.getFrameminheight());
			System.out.println("�Ƚ����,ѡ��minWidth1: " + minWidth1);
		}
		jf.setMinimumSize(dimension);
	}

	public void init() {
		cardWidthLabel = new JLabel("���ƿ��:");
		cardHeightLabel = new JLabel("���Ƹ߶�:");
		widthF = new JTextField();
		heightF = new JTextField();
		submitButton = new JButton("Ӧ��");

		cardWidthLabel.setFont(StaticData.getEDITFONT());
		cardHeightLabel.setFont(StaticData.getEDITFONT());
		widthF.setFont(StaticData.getEDITFONT2());
		heightF.setFont(StaticData.getEDITFONT2());
		submitButton.setFont(StaticData.getEDITFONT());

		cardWidthLabel.setBounds(0, 0, 100, 30);
		cardHeightLabel.setBounds(0, 32, 100, 30);
		widthF.setBounds(100, 0, 50, 30);
		heightF.setBounds(100, 32, 50, 30);
		submitButton.setBounds(170, 0, 100, 62);

		widthF.setDocument(new NumberDocument());
		heightF.setDocument(new NumberDocument());

		widthF.setText(String.valueOf(StaticData.getCardsize(2)));
		heightF.setText(String.valueOf(StaticData.getCardsize(3)));
	}
}
