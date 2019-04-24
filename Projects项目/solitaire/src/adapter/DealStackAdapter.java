package adapter;

import java.awt.event.MouseAdapter;

import element.CardStackNode;
import uiDao.DealStackPanel;
import uiDao.DealedStackPanel;

public class DealStackAdapter extends MouseAdapter {
	private DealStackPanel dealStackPanel;
	private DealedStackPanel dealedStackPanel;
	public DealStackAdapter(DealStackPanel dealStackPanel,DealedStackPanel dealedStackPanel) {
		super();
		// TODO Auto-generated constructor stub
		this.dealStackPanel=dealStackPanel;
		this.dealedStackPanel=dealedStackPanel;
	}
	
	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseClicked(e);
		// ���ʱ
		if (dealStackPanel.getDealCardNum() != 0) {
			System.out.println(dealStackPanel.getDealCardNum());
			if (!dealStackPanel.isPositive()) { // ����������ڱ���
				System.out.println("�ڱ���");
				CardStackNode cardStackNode = dealStackPanel.pullFromDealStack();
				dealedStackPanel.pushToDealedStack(cardStackNode);
			}
		} else {
			// û�п���ʱ
			System.out.println("û�п���");
			CardStackNode cardStackNode = dealedStackPanel.selectAllDealedCard();
			dealStackPanel.pushCardStackNode(cardStackNode, false);
		}
	}
}
