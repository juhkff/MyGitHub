package uiDao;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import element.StaticData;
import tools.NumberDocument;
import tools.NumberDocumentSyn;
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
	private JLabel lockLabel;
	private JButton submitButton;
	private LockPanel paintPanel;

	private int newWidth;
	private int newHeight;
	private float rate;

	private boolean isLocked;
	private boolean valueLocked;

	private NumberDocumentSyn numberDocumentSyn1;
	private NumberDocumentSyn numberDocumentSyn2;

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
		this.add(lockLabel);
		this.add(submitButton);
		this.add(paintPanel);

		this.setSize(270, 62);
	}

	private void listenerInit() {
		// TODO Auto-generated method stub
		submitButton.addActionListener(clickListener);
		paintPanel.addMouseListener(lockAdapter);
		paintPanel.addMouseMotionListener(lockAdapter);
		widthF.addKeyListener(keyAdapterW);
		heightF.addKeyListener(keyAdapterH);
	}

	private KeyAdapter keyAdapterW = new KeyAdapter() {
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			super.keyTyped(e);
			if (e.getKeyChar() != '\b')
				return;
			else {
				JTextField source = (JTextField) e.getComponent();
				int num;
				if (source.getText().length() == 0) {
					num = 0;
					widthF.setText("");
				} else {
					num = Integer.parseInt(source.getText());
				}
				numberDocumentSyn1.setOriginNum(num);
				if (valueLocked) {
					// 修改另一个栏中的数
					if (num == 0) {
						heightF.setText("");
						numberDocumentSyn2.setOriginNum(num);
					} else {
						int otherNewNum = (int) ((float) num / rate);
						System.out.println("otherNewNum=" + otherNewNum);
						numberDocumentSyn2.setThis(false);
						numberDocumentSyn2.setOriginNum(otherNewNum);
						heightF.setText(String.valueOf(otherNewNum));
					}
				}
			}
		}
	};

	private KeyAdapter keyAdapterH = new KeyAdapter() {
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			super.keyTyped(e);
			if (e.getKeyChar() != '\b')
				return;
			else {
				JTextField source = (JTextField) e.getComponent();
				int num;
				if (source.getText().length() == 0) {
					num = 0;
					heightF.setText("");
				} else {
					num = Integer.parseInt(source.getText());
				}
				numberDocumentSyn2.setOriginNum(num);
				if (valueLocked) {
					// 修改另一个栏中的数
					if (num == 0) {
						widthF.setText("");
						numberDocumentSyn1.setOriginNum(num);
					} else {
						int otherNewNum = (int) (rate * (float) num);
						numberDocumentSyn1.setThis(false);
						numberDocumentSyn1.setOriginNum(otherNewNum);
						widthF.setText(String.valueOf(otherNewNum));
					}
				}
			}
		}
	};

	private MouseAdapter lockAdapter = new MouseAdapter() {
		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseMoved(e);
			if (isInLock(e.getPoint())) {
				// 改变鼠标样式
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				isLocked = true;
			} else {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				isLocked = false;
			}
			paintPanel.changeColor(isLocked, valueLocked);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseClicked(e);
			if (isLocked) {
				// 修改
				if (valueLocked) {
					// 改为不一致
					valueLocked = false;
					paintPanel.changeStyle(valueLocked);
					lockLabel.setText("不固定宽高比");
				} else {
					// 改为一致
					valueLocked = true;
					if (widthF.getText().equals("0") || heightF.getText().equals("0"))
						rate = 1;
					else
						rate = Float.parseFloat(widthF.getText()) / Float.parseFloat(heightF.getText());
					numberDocumentSyn2.setReceiveRate(rate);
					numberDocumentSyn1.setReceiveRate((float) 1.0 / rate);
					System.out.println("rate变更为:" + rate);
					paintPanel.changeStyle(valueLocked);
					lockLabel.setText("固定宽高比");
				}
			}
		}
	};

	private boolean isInLock(Point point) {
		int x = (int) point.getX();
		int y = (int) point.getY();
		if ((x >= 0 && x <= 8 && ((y >= 13 && y <= 17) || (y >= 45 && y <= 49)))
				|| (x >= 8 && x <= 12 && y >= 13 && y <= 49))
			return true;
		else
			return false;
	}

	private ActionListener clickListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			newWidth = Integer.parseInt(widthF.getText());
			newHeight = Integer.parseInt(heightF.getText());
			if (newWidth > StaticData.getFRAMEWIDTH() / 7 || newHeight > StaticData.getFRAMEHEIGHT()) {
				JOptionPane.showMessageDialog(CardSizePanel.this, "数值过高", null, JOptionPane.INFORMATION_MESSAGE);
			} else {
				StaticData.setCARDWIDTH(newWidth);
				StaticData.setCARDHEIGHT(newHeight);
				StaticData.setCARDSIZEChanged(true);
				StaticData.reDefine();
				jf.getJf().reSize();
				resetMinWidth();
				JOptionPane.showMessageDialog(null, "应用成功!", null, JOptionPane.CLOSED_OPTION);
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
			System.out.println("比较完毕,选择minWidth2: " + minWidth2);
		} else {
			dimension = new Dimension(minWidth1, StaticData.getFrameminheight());
			System.out.println("比较完毕,选择minWidth1: " + minWidth1);
		}
		jf.setMinimumSize(dimension);
	}

	public void init() {
		isLocked = false;
		valueLocked = false;
		cardWidthLabel = new JLabel("卡牌宽度:");
		cardHeightLabel = new JLabel("卡牌高度:");
		widthF = new JTextField();
		heightF = new JTextField();
		widthF.setName("widthF");
		heightF.setName("heightF");
		lockLabel = new JLabel("不固定宽高比");
		submitButton = new JButton("应用");
		paintPanel = new LockPanel(valueLocked);

		cardWidthLabel.setFont(StaticData.getEDITFONT());
		cardHeightLabel.setFont(StaticData.getEDITFONT());
		widthF.setFont(StaticData.getEDITFONT2());
		heightF.setFont(StaticData.getEDITFONT2());
		lockLabel.setFont(StaticData.getEDITFONT2());
		lockLabel.setForeground(Color.DARK_GRAY);
		lockLabel.setHorizontalAlignment(JLabel.CENTER);
		submitButton.setFont(StaticData.getEDITFONT());
		paintPanel.setFont(StaticData.getEDITFONT());

		cardWidthLabel.setBounds(0, 0, 100, 30);
		cardHeightLabel.setBounds(0, 32, 100, 30);
		widthF.setBounds(100, 0, 50, 30);
		heightF.setBounds(100, 32, 50, 30);
		lockLabel.setBounds(170, 0, 100, 30);
		submitButton.setBounds(170, 32, 100, 30);
		paintPanel.setBounds(150, 0, 20, 62);

		numberDocumentSyn1 = new NumberDocumentSyn(widthF, heightF, paintPanel);
		numberDocumentSyn2 = new NumberDocumentSyn(heightF, widthF, paintPanel);
		numberDocumentSyn1.setDestDocumentSyn(numberDocumentSyn2);
		numberDocumentSyn2.setDestDocumentSyn(numberDocumentSyn1);

		widthF.setDocument(numberDocumentSyn1);
		heightF.setDocument(numberDocumentSyn2);

		widthF.setText(String.valueOf(StaticData.getCardsize(2)));
		heightF.setText(String.valueOf(StaticData.getCardsize(3)));
		rate = (float) StaticData.getCardsize(2) / (float) StaticData.getCardsize(3);
	}
}
