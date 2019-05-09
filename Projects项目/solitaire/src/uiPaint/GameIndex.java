package uiPaint;

import java.awt.Dimension;

import javax.swing.JFrame;

import adapter.IndexAdapter;
import element.StaticData;
import uiDao.GamePage;

public class GameIndex {
	private GamePage jf;

	public GameIndex() {
		super();
		// TODO Auto-generated constructor stub
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

	public GamePage getJf() {
		return jf;
	}

	public void setJf(GamePage jf) {
		this.jf = jf;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new GameIndex();
	}
}
