package uiPaint;

import java.awt.Dimension;

import javax.swing.JFrame;

import element.StaticData;
import uiDao.BGColorPanel;
import uiDao.BGImgPanel;
import uiDao.CardAutoChangePanel;
import uiDao.CardAutoCheckPanel;
import uiDao.CardBorderPanel;
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
	private BGImgPanel bgImgPanel;
	private BGColorPanel bgColorPanel;
	private CardBorderPanel cardBorderPanel;
	private CardSizePanel cardSizePanel;
	private CardDistancePanel cardDistancePanel;
	private CardAutoChangePanel cardAutoChangePanel;
	private CardAutoCheckPanel cardAutoCheckPanel;

	public GameEdit(GameIndex jf) {
		this.jf = jf;
		this.setLayout(null);

		this.imgPathPanel = new ImgPathPanel(this.jf);
		this.imgPathPanel.setLocation(20, 20);
		this.bgImgPanel = new BGImgPanel(this.jf);
		this.bgImgPanel.setLocation(20, 60);

		this.bgColorPanel = new BGColorPanel(this.jf);
		this.bgColorPanel.setLocation(20, 100);
		this.cardBorderPanel = new CardBorderPanel(this.jf);
		this.cardBorderPanel.setLocation(20, 140);

		this.cardSizePanel = new CardSizePanel(this.jf);
		this.cardSizePanel.setLocation(20, 180);

		this.cardDistancePanel = new CardDistancePanel(this.jf);
		this.cardDistancePanel.setLocation(20, 260);

		this.cardAutoChangePanel = new CardAutoChangePanel();
		this.cardAutoChangePanel.setLocation(20, 300);

		this.cardAutoCheckPanel = new CardAutoCheckPanel();
		this.cardAutoCheckPanel.setLocation(20, 340);

		this.setTitle("����");
		// �����˳���Ϊ
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// ���ô��ڴ�С�ɱ�
		this.setResizable(false);
		// ���ڴ�С
		this.setSize(StaticData.getEDITWIDTH(), StaticData.getEDITHEIGHT());
		// ���ô��ڴ򿪾���
		this.setLocationRelativeTo(this.jf);
		this.setMinimumSize(new Dimension(StaticData.getEDITWIDTH(), StaticData.getEDITHEIGHT()));
		this.getContentPane().add(imgPathPanel);
		this.getContentPane().add(bgImgPanel);
		this.getContentPane().add(bgColorPanel);
		this.getContentPane().add(cardBorderPanel);
		this.getContentPane().add(cardSizePanel);
		this.getContentPane().add(cardDistancePanel);
		this.getContentPane().add(cardAutoChangePanel);
		this.getContentPane().add(cardAutoCheckPanel);
	}

}
