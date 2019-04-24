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
		// 点击时
		if (dealStackPanel.getDealCardNum() != 0) {
			System.out.println(dealStackPanel.getDealCardNum());
			if (!dealStackPanel.isPositive()) { // 最上面的牌在背面
				System.out.println("在背面");
				CardStackNode cardStackNode = dealStackPanel.pullFromDealStack();
				dealedStackPanel.pushToDealedStack(cardStackNode);
			}
		} else {
			// 没有卡牌时
			System.out.println("没有卡牌");
			CardStackNode cardStackNode = dealedStackPanel.selectAllDealedCard();
			dealStackPanel.pushCardStackNode(cardStackNode, false);
		}
	}
}
