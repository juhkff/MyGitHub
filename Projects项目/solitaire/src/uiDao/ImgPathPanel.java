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

public class ImgPathPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameIndex jf;
	private JLabel imgPathLabel;
	private JTextField imgPathField;
	private JButton imgSubmitButton;

	private JFileChooser jFileChooser = new JFileChooser();

	public ImgPathPanel(GameIndex jf) {
		super();
		// TODO Auto-generated constructor stub
		this.jf = jf;
		this.setLayout(null);
		init();
		listenerInit();

		this.add(imgPathLabel);
		this.add(imgPathField);
		this.add(imgSubmitButton);
		this.setSize(700, 30);
	}

	private void init() {
		imgPathLabel = new JLabel("����ͼ��:");
		imgPathField = new JTextField();
		imgSubmitButton = new JButton("���");

		imgPathField.setEditable(false);
		imgPathField.setBounds(100, 0, 500, 30);
		imgPathField.setFont(StaticData.getEDITFONT2());

		imgPathLabel.setBounds(0, 0, 100, 30);
		imgPathLabel.setFont(StaticData.getEDITFONT());

		imgSubmitButton.setBounds(610, 0, 90, 30);
		imgSubmitButton.setFont(StaticData.getEDITFONT());

		if (StaticData.getBACKGROUNDURL() == null || StaticData.getBACKGROUNDURL().equals(""))
			imgPathField.setText("�Զ��忨�Ʊ���ͼ��");
		else
			imgPathField.setText(StaticData.getBACKGROUNDURL());
	}

	private void listenerInit() {
		imgSubmitButton.addMouseListener(clickListener);
		jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jFileChooser.setMultiSelectionEnabled(false);
	}

	private MouseAdapter clickListener = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseClicked(e);
			jFileChooser.showDialog(new Label(), "Ӧ��");
			File file = jFileChooser.getSelectedFile();
			if (file == null)
				return;
			System.out.println(file.getAbsolutePath() + "\t" + file.getName());
			if (!isImage(file)) {
				// ѡ�еĲ���ͼƬ
				JOptionPane.showMessageDialog(ImgPathPanel.this, "��ѡ��ͼƬ�ļ�", null, JOptionPane.INFORMATION_MESSAGE);
			} else {
				// ѡ�е���ͼƬ
				StaticData.setBACKGROUNDURL(file.getAbsolutePath());
				StaticData.setCARDBGChanged(true);
				jf.getJf().reSize();
				imgPathField.setText(file.getAbsolutePath());
				JOptionPane.showMessageDialog(null, "Ӧ�óɹ�!", null, JOptionPane.CLOSED_OPTION);
			}
		}
	};

	private boolean isImage(File file) {
		String suffix = file.getName();
		if (suffix.endsWith(".jpg") || suffix.endsWith(".png") || suffix.endsWith(".gif") || suffix.endsWith(".bmp"))
			return true;
		else
			return false;
	}
}
