package uiDao;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import adapter.IndexAdapter;
import element.StaticData;

public class GameMenuBar extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JMenu gameMenu = new JMenu("��Ϸ");
	private JMenu windowMenu = new JMenu("����");

	private JMenuItem newGameItem = new JMenuItem("����Ϸ");
	private JMenuItem exitGameItem = new JMenuItem("�˳�");
	private JMenuItem windowEditItem = new JMenuItem("����");

	private GamePage jf;

	public GameMenuBar(GamePage jf2) {
		super();
		// TODO Auto-generated constructor stub
		this.jf = jf2;
		this.add(gameMenu);
		this.add(windowMenu);
		gameMenu.add(newGameItem);
		gameMenu.addSeparator();
		gameMenu.add(exitGameItem);
		windowMenu.add(windowEditItem);
		windowMenu.addSeparator();

		// ���ü�����
		newGameItem.addActionListener(newGameListener);
	}

	// ����"����Ϸ"�Ӳ˵�������ļ�����
	private ActionListener newGameListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			// jf.dispose();
			StaticData.setNewCardIndexSet();
			// GamePage jf;
			jf = new GamePage();
			jf.init();
			jf.setTitle("Solitaire-ֽ����Ϸ");
			// �����˳���Ϊ
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			// ���ô��ڴ�С�ɱ�
			jf.setResizable(true);
			// ���ڴ�С
			jf.setSize(StaticData.FRAMESIZE[0], StaticData.FRAMESIZE[1]);
			jf.addComponentListener(new IndexAdapter(jf));
			Dimension dimension = new Dimension(StaticData.getFrameminwidth(), StaticData.getFrameminheight());
			jf.setMinimumSize(dimension);
			jf.setName("frame");
			// ���ô��ڴ򿪾���
			jf.setLocationRelativeTo(null);
			// չʾ����
			jf.setVisible(true);
		}
	};
}
