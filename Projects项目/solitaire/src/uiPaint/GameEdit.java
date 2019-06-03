package uiPaint;

import java.awt.Dimension;

import javax.swing.JFrame;

import element.StaticData;
import uiDao.CardDistancePanel;
import uiDao.CardSizePanel;
import uiDao.ImgPathPanel;

public class GameEdit extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameIndex jf;
	private ImgPathPanel imgPathPanel;
	private CardSizePanel cardSizePanel;
	private CardDistancePanel cardDistancePanel;

	public GameEdit(GameIndex jf) {
		this.jf = jf;
		this.setLayout(null);

		this.imgPathPanel = new ImgPathPanel(this.jf);
		this.imgPathPanel.setLocation(20, 20);

		this.cardSizePanel = new CardSizePanel(this.jf);
		this.cardSizePanel.setLocation(20, 60);
		
		this.cardDistancePanel=new CardDistancePanel(this.jf);
		this.cardDistancePanel.setLocation(20, 140);

		this.setTitle("设置");
		// 窗口退出行为
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// 设置窗口大小可变
		this.setResizable(true);
		// 窗口大小
		this.setSize(StaticData.getEDITWIDTH(), StaticData.getEDITHEIGHT());
		// 设置窗口打开居中
		this.setLocationRelativeTo(/* null */this.jf);
		this.setMinimumSize(new Dimension(StaticData.getEDITWIDTH(), StaticData.getEDITHEIGHT()));
		this.getContentPane().add(imgPathPanel);
		this.getContentPane().add(cardSizePanel);
		this.getContentPane().add(cardDistancePanel);
	}
}
