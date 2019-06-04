package adapter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import element.CardStackNode;
import element.StaticData;
import tools.SolitaireCheck;
import uiDao.GamePage;

public class DealStackAdapter extends MouseAdapter {
	private GamePage jf;

	public DealStackAdapter(GamePage jf) {
		super();
		// TODO Auto-generated constructor stub
		this.jf = jf;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseClicked(e);
		if (e.getButton() != MouseEvent.BUTTON1) {
			if (e.getButton() == MouseEvent.BUTTON2 && StaticData.isCardAutoCheck()) {
				System.out.println("�����м�!");
				SolitaireCheck.autoCheck(jf);
			}
			return;
		}
		// �������ʱ
		if (jf.getDealStackPanel().getDealCardNum() != 0) {
			System.out.println(jf.getDealStackPanel().getDealCardNum());
			if (!jf.getDealStackPanel().isPositive()) { // ����������ڱ���
				System.out.println("�ڱ���");
				CardStackNode cardStackNode = jf.getDealStackPanel().pullFromDealStack();
				jf.getDealedStackPanel().pushToDealedStack(cardStackNode);
			}
		} else {
			// û�п���ʱ
			System.out.println("û�п���");
			CardStackNode cardStackNode = jf.getDealedStackPanel().selectAllDealedCard();
			jf.getDealStackPanel().pushCardStackNode(cardStackNode, false);
		}
		jf.refresh();
	}
}
