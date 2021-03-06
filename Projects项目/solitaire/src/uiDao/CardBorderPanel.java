package uiDao;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import element.StaticData;
import tools.ColorUtils;
import uiPaint.GameIndex;

public class CardBorderPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameIndex gameIndex;
	private JLabel colorLabel;
	private JLabel colorTextLabel;
	private JPanel showPanel;
	private JButton submitButton;
	private JButton resetButton;

	public CardBorderPanel(GameIndex gameIndex) {
		// TODO Auto-generated constructor stub
		this.gameIndex = gameIndex;
		init();
		listenerInit();
		this.setLayout(null);
		this.add(colorLabel);
		this.add(colorTextLabel);
		this.add(showPanel);
		this.add(submitButton);
		this.add(resetButton);
		this.setSize(800, 30);
	}

	private void init() {
		colorLabel = new JLabel("边框颜色:");
		colorTextLabel = new JLabel();
		showPanel = new JPanel();
		submitButton = new JButton("修改");
		resetButton = new JButton("重置");

		colorLabel.setBounds(0, 0, 100, 30);
		colorLabel.setFont(StaticData.getEDITFONT());
		colorTextLabel.setBounds(100, 0, 100, 30);
		colorTextLabel.setFont(StaticData.getEDITFONT());
		// showPanel.setBounds(200, 0, 400, 30);
		showPanel.setBounds(200, 10, 400, 10);
		showPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		submitButton.setBounds(610, 0, 90, 30);
		submitButton.setFont(StaticData.getEDITFONT());
		resetButton.setBounds(710, 0, 90, 30);
		resetButton.setFont(StaticData.getEDITFONT());
		if (StaticData.getCARDBORDERCOLOR() != null) {
			colorTextLabel.setText(ColorUtils.rgb2Hex(StaticData.getCARDBORDERCOLOR()));
			showPanel.setBackground(StaticData.getCARDBORDERCOLOR());
		} else {
			colorTextLabel.setText(ColorUtils.rgb2Hex(StaticData.getDEFAULTCARDBORDERCOLOR()));
			showPanel.setBackground(StaticData.getDEFAULTCARDBORDERCOLOR());
		}
	}

	private MouseAdapter mouseAdapter = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseClicked(e);
			Color color = JColorChooser.showDialog(getParent(), "选取颜色",
					(StaticData.getCARDBORDERCOLOR() != null) ? StaticData.getCARDBORDERCOLOR()
							: StaticData.getDEFAULTCARDBORDERCOLOR());
			if (color == null) {
				return;
			}
			color = new Color(color.getRed(), color.getGreen(), color.getBlue());
			StaticData.setCARDBORDERCOLOR(color);
			colorTextLabel.setText(ColorUtils.rgb2Hex(color));
			showPanel.setBackground(color);
			gameIndex.getJf().rePaintBorder();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseEntered(e);
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseMoved(e);
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseExited(e);
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}

	};

	private void listenerInit() {
		showPanel.addMouseListener(mouseAdapter);
		showPanel.addMouseMotionListener(mouseAdapter);
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Color color = JColorChooser.showDialog(getParent(), "选取颜色",
						(StaticData.getCARDBORDERCOLOR() != null) ? StaticData.getCARDBORDERCOLOR()
								: StaticData.getDEFAULTCARDBORDERCOLOR());
				if (color == null) {
					return;
				}
				color = new Color(color.getRed(), color.getGreen(), color.getBlue());
				StaticData.setCARDBORDERCOLOR(color);
				colorTextLabel.setText(ColorUtils.rgb2Hex(color));
				showPanel.setBackground(color);
				gameIndex.getJf().rePaintBorder();
			}
		});
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				StaticData.setCARDBORDERCOLOR(null);
				colorTextLabel.setText(ColorUtils.rgb2Hex(StaticData.getDEFAULTCARDBORDERCOLOR()));
				showPanel.setBackground(StaticData.getDEFAULTCARDBORDERCOLOR());
				gameIndex.getJf().rePaintBorder();
			}
		});
	}

}
