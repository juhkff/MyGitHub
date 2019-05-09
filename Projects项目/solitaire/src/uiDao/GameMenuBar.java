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

	private JMenu gameMenu = new JMenu("游戏");
	private JMenu windowMenu = new JMenu("窗口");

	private JMenuItem newGameItem = new JMenuItem("新游戏");
	private JMenuItem exitGameItem = new JMenuItem("退出");
	private JMenuItem windowEditItem = new JMenuItem("设置");

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

		// 设置监听器
		newGameItem.addActionListener(newGameListener);
	}

	// 设置"新游戏"子菜单被点击的监听器
	private ActionListener newGameListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			// jf.dispose();
			StaticData.setNewCardIndexSet();
			// GamePage jf;
			jf = new GamePage();
			jf.init();
			jf.setTitle("Solitaire-纸牌游戏");
			// 窗口退出行为
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			// 设置窗口大小可变
			jf.setResizable(true);
			// 窗口大小
			jf.setSize(StaticData.FRAMESIZE[0], StaticData.FRAMESIZE[1]);
			jf.addComponentListener(new IndexAdapter(jf));
			Dimension dimension = new Dimension(StaticData.getFrameminwidth(), StaticData.getFrameminheight());
			jf.setMinimumSize(dimension);
			jf.setName("frame");
			// 设置窗口打开居中
			jf.setLocationRelativeTo(null);
			// 展示窗口
			jf.setVisible(true);
		}
	};
}
