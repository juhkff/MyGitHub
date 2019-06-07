package uiPaint;

import java.awt.Dimension;

import javax.swing.JFrame;

import adapter.IndexAdapter;
import element.StaticData;
import uiDao.GameMenuBar;
import uiDao.GamePage;

public class GameIndex extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GamePage jf;
	private GameMenuBar gameMenuBar;

	public GameIndex() {
		super();
		// TODO Auto-generated constructor stub
		this.setLayout(null);
		jf = new GamePage();
		jf.init();
		/*
		 * jf.setVisible(true); jf.setSize(StaticData.FRAMESIZE[0],
		 * StaticData.FRAMESIZE[1]); jf.setLayout(null);
		 */
		// jf.setBounds(0, 0, StaticData.FRAMESIZE[0], StaticData.FRAMESIZE[1]);
		// jf.setSize(StaticData.FRAMESIZE[0], StaticData.FRAMESIZE[1]);

		this.setTitle("Solitaire-ֽ����Ϸ");
		// �����˳���Ϊ
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ���ô��ڴ�С�ɱ�
		this.setResizable(true);
		// ���ڴ�С
		this.setSize(StaticData.FRAMESIZE[0], StaticData.FRAMESIZE[1]);
		this.addComponentListener(new IndexAdapter(this));
		Dimension dimension = new Dimension(StaticData.getFrameminwidth(), StaticData.getFrameminheight());
		this.setMinimumSize(dimension);
		this.setName("frame");
		// ���ô��ڴ򿪾���
		this.setLocationRelativeTo(null);
		// չʾ����
		// this.setVisible(true);
		gameMenuBar = new GameMenuBar(this);
		// this.getContentPane().add(jf);
		// setContentPane(jf);
		this.getContentPane().add(jf);
		this.setJMenuBar(gameMenuBar);
	}

	public GameMenuBar getGameMenuBar() {
		return gameMenuBar;
	}

	public GamePage getJf() {
		return jf;
	}

	public void setJf(GamePage jf) {
		this.jf = jf;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameIndex gameIndex = new GameIndex();
		gameIndex.setVisible(true);
		// System.out.println("gameMenuBar=" + gameIndex.getGameMenuBar().getHeight());
		StaticData.setGAMEMENUBARHEIGHT(gameIndex.getGameMenuBar().getHeight());
		StaticData.setGAMETITLEHEIGHT(gameIndex.getInsets().top);
		StaticData.setDEFAULTBACKGROUNDCOLOR(gameIndex.getJf().getBackground());
		// System.out.println(ColorUtils.rgb2Hex(gameIndex.getJf().getBackground()));
	}
}
