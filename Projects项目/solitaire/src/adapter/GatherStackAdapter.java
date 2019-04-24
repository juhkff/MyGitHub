package adapter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import element.CardStackNode;
import element.StaticData;
import uiDao.CardPanel;
import uiDao.GatherCardPanel;
import uiPaint.Index;

public class GatherStackAdapter extends MouseAdapter {
	private CardPanel cardPanel;

	public GatherStackAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		//super.mouseReleased(e);
		System.out.println("触发释放");
		GatherCardPanel gatherCardPanel = (GatherCardPanel) e.getComponent();
		if (Index.cardPanel != null) {
			this.cardPanel = Index.cardPanel;
			if (e.getComponent().getX() < cardPanel.getX() + cardPanel.getWidth() / 2
					&& cardPanel.getX() + cardPanel.getWidth() / 2 < e.getComponent().getX()
							+ e.getComponent().getWidth()
					&& e.getComponent().getY() < cardPanel.getY() + cardPanel.getHeight() / 2
					&& cardPanel.getHeight() / 2 < e.getComponent().getY() + e.getComponent().getHeight()) {
				// 卡牌移入区域
				if ((cardPanel.getCardValue().endsWith("1") && gatherCardPanel.getCardNum() == 0)
						|| (gatherCardPanel.getCardNum() > 0 && StaticData.getGathervaluemap(cardPanel.getCardValue())
								.equals(gatherCardPanel.getTop(false).getStackNode().getCardValue()))) {
					// 卡牌满足放入要求
					gatherCardPanel.setTop(new CardStackNode(cardPanel));
					
				}
			}
		}
	}
}
