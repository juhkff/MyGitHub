package uiDao;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import element.CardStackNode;
import element.StaticData;

public class DOWNSevenStackPanel extends JLayeredPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int cardNum=0;
	private CardStackNode top=null;
	
	public DOWNSevenStackPanel(int index) {
		super();
		// TODO Auto-generated constructor stub
		int panelHeight=0;	//��ʼ�߶�
		int panelWidth=StaticData.getCardsize(2); // ��ʼ���
		int location_X = (index - 1) * panelWidth + index * StaticData.getSevenstacklocation(0); // ��������ߵľ���
	}
	
	
}
