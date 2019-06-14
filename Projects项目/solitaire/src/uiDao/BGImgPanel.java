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
		imgPathLabel = new JLabel("��Ϸ����:");
		imgPathField = new JTextField();
		imgSubmitButton = new JButton("���");
		resetButton = new JButton("���");

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
			imgPathField.setText("�Զ�����Ϸ����ͼ��(png/jpg)");
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
			imgPathField.setText("�Զ�����Ϸ����ͼ��(png/jpg)");
			JOptionPane.showMessageDialog(getParent(), "��ԭ�ɹ�!", null, JOptionPane.CLOSED_OPTION);
		}
	};

	private MouseAdapter clickListener = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseClicked(e);
			jFileChooser.setSelectedFile(null);
			jFileChooser.showDialog(new Label(), "Ӧ��");
			File file = jFileChooser.getSelectedFile();
			if (file == null)
				return;
			System.out.println(file.getAbsolutePath() + "\t" + file.getName());
			if (!isImage(file)) {
				// ѡ�еĲ���ͼƬ
				JOptionPane.showMessageDialog(BGImgPanel.this, "��ѡ��ͼƬ�ļ�", null, JOptionPane.INFORMATION_MESSAGE);
			} else {
				// ѡ�е���ͼƬ
				StaticData.setGLOBALBACKGROUNDURL(file.getAbsolutePath());
				jf.getJf().repaint();
				imgPathField.setText(file.getAbsolutePath());
				JOptionPane.showMessageDialog(getParent(), "Ӧ�óɹ�!", null, JOptionPane.CLOSED_OPTION);
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
