package adapter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import element.CardStackNode;
import uiPaint.Index;

public class DealStackAdapter extends MouseAdapter {
	//private DealStackPanel dealStackPanel;
	//private DealedStackPanel dealedStackPanel;

	public DealStackAdapter(/*DealStackPanel dealStackPanel, DealedStackPanel dealedStackPanel*/) {
		super();
		// TODO Auto-generated constructor stub
		//this.dealStackPanel = Index.dealStackPanel;
		//this.dealedStackPanel = Index.dealedStackPanel;
	}

	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseClicked(e);
		// 点击时
		if (e.getButton() != MouseEvent.BUTTON1) {
			return;
		}
		if (Index.dealStackPanel.getDealCardNum() != 0) {
			System.out.println(Index.dealStackPanel.getDealCardNum());
			if (!Index.dealStackPanel.isPositive()) { // 最上面的牌在背面
				System.out.println("在背面");
				CardStackNode cardStackNode = Index.dealStackPanel.pullFromDealStack();
				Index.dealedStackPanel.pushToDealedStack(cardStackNode);
			}
		} else {
			// 没有卡牌时
			System.out.println("没有卡牌");
			CardStackNode cardStackNode = Index.dealedStackPanel.selectAllDealedCard();
			Index.dealStackPanel.pushCardStackNode(cardStackNode, false);
		}
		Index.refresh();
	}
}
