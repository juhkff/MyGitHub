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
