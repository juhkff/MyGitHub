package uiDao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import element.StaticData;
import uiPaint.GameEdit;
import uiPaint.GameIndex;

public class GameMenuBar extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JMenu gameMenu = new JMenu("游戏");
	// private JMenu windowMenu = new JMenu("设置");

	private JMenuItem newGameItem = new JMenuItem("新游戏");
	private JMenuItem exitGameItem = new JMenuItem("退出");
	private JMenuItem windowEditItem = new JMenuItem("设置");

	private GameIndex jf;

	public GameMenuBar(GameIndex jf2) {
		super();
		// TODO Auto-generated constructor stub
		this.jf = jf2;
		this.gameMenu.setFont(StaticData.getEditfont());
		// this.windowMenu.setFont(StaticData.getEditfont());
		this.newGameItem.setFont(StaticData.getEditfont());
		this.exitGameItem.setFont(StaticData.getEditfont());
		this.windowEditItem.setFont(StaticData.getEditfont());

		this.add(gameMenu);
		// this.add(windowMenu);
		gameMenu.add(newGameItem);
		gameMenu.addSeparator();
		gameMenu.add(windowEditItem);
		gameMenu.addSeparator();
		gameMenu.add(exitGameItem);
		// windowMenu.add(windowEditItem);
		// windowMenu.addSeparator();

		// 设置监听器
		newGameItem.addActionListener(newGameListener);
		exitGameItem.addActionListener(exitListener);
		windowEditItem.addActionListener(editGameListener);
	}

	// 设置游戏设置监听器
	private ActionListener editGameListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			GameEdit gameEdit = new GameEdit(jf);
			gameEdit.setVisible(true);
		}
	};

	// 设置"新游戏"子菜单被点击的监听器
	private ActionListener newGameListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			StaticData.setNewCardIndexSet();
			// JPanel contentPanel=(JPanel) jf.getContentPane();
			/* contentPanel */jf.getContentPane().remove(jf.getJf());
			/* contentPanel */jf.getContentPane().repaint();

			GamePage jf2;
			jf2 = new GamePage();
			jf2.init();
			jf.setJf(jf2);
			/* contentPanel */jf.getContentPane().add(jf2);

		}
	};

	private ActionListener exitListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.exit(0);
		}
	};
}
