package uiDao;

import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import element.StaticData;
import uiPaint.GameIndex;

public class BGImgPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameIndex jf;
	private JLabel imgPathLabel;
	private JTextField imgPathField;
	private JButton imgSubmitButton;
	private JButton resetButton;

	private JFileChooser jFileChooser = new JFileChooser();

	public BGImgPanel(GameIndex jf) {
		// TODO Auto-generated constructor stub
		this.jf = jf;
		this.setLayout(null);
		init();
		listenerInit();

		this.add(imgPathLabel);
		this.add(imgPathField);
		this.add(imgSubmitButton);
		this.add(resetButton);
		this.setSize(800, 30);
	}

	private void init() {
		imgPathLabel = new JLabel("游戏背景:");
		imgPathField = new JTextField();
		imgSubmitButton = new JButton("浏览");
		resetButton = new JButton("清除");

		imgPathField.setEditable(false);
		imgPathField.setBounds(100, 0, 500, 30);
		imgPathField.setFont(StaticData.getEDITFONT2());

		imgPathLabel.setBounds(0, 0, 100, 30);
		imgPathLabel.setFont(StaticData.getEDITFONT());

		imgSubmitButton.setBounds(610, 0, 90, 30);
		imgSubmitButton.setFont(StaticData.getEDITFONT());

		resetButton.setBounds(710, 0, 90, 30);
		resetButton.setFont(StaticData.getEDITFONT());

		if (StaticData.getGLOBALBACKGROUNDURL() == null || StaticData.getGLOBALBACKGROUNDURL().equals(""))
			imgPathField.setText("自定义游戏背景图案(png/jpg)");
		else
			imgPathField.setText(StaticData.getGLOBALBACKGROUNDURL());
	}

	private void listenerInit() {
		imgSubmitButton.addMouseListener(clickListener);
		resetButton.addMouseListener(resetListener);
		jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jFileChooser.setMultiSelectionEnabled(false);
	}

	private MouseAdapter resetListener = new MouseAdapter() {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseClicked(e);
			StaticData.setGLOBALBACKGROUNDURL("");
			jf.getJf().repaint();
			imgPathField.setText("自定义游戏背景图案(png/jpg)");
			JOptionPane.showMessageDialog(getParent(), "还原成功!", null, JOptionPane.CLOSED_OPTION);
		}
	};

	private MouseAdapter clickListener = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseClicked(e);
			jFileChooser.setSelectedFile(null);
			jFileChooser.showDialog(new Label(), "应用");
			File file = jFileChooser.getSelectedFile();
			if (file == null)
				return;
			System.out.println(file.getAbsolutePath() + "\t" + file.getName());
			if (!isImage(file)) {
				// 选中的不是图片
				JOptionPane.showMessageDialog(BGImgPanel.this, "请选择图片文件", null, JOptionPane.INFORMATION_MESSAGE);
			} else {
				// 选中的是图片
				StaticData.setGLOBALBACKGROUNDURL(file.getAbsolutePath());
				jf.getJf().repaint();
				imgPathField.setText(file.getAbsolutePath());
				JOptionPane.showMessageDialog(getParent(), "应用成功!", null, JOptionPane.CLOSED_OPTION);
			}
		}
	};

	private boolean isImage(File file) {
		String suffix = file.getName();
		if (suffix.endsWith(".jpg") || suffix.endsWith(".png")/* || suffix.endsWith(".gif") */
				|| suffix.endsWith(".bmp"))
			return true;
		else
			return false;
	}
}
