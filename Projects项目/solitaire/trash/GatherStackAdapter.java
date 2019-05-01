package adapter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import element.CardStackNode;
import element.StaticData;
import uiDao.CardPanel;
import uiDao.GatherCardPanel;
import uiPaint.Index;

public class GatherStackAdapter extends MouseAdapter {
	private int mouseStartX, mouseStartY; // ��갴��ʱ����
	private int mouseEndX, mouseEndY; // ����ƶ�ʱ����
	private int componentX, componentY; // �ؼ�����
	private boolean isLockedOnCard = false; // ����Ƿ����϶��Ŀ���
	private CardPanel cardPanel;

	public GatherStackAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		//super.mouseReleased(e);
		System.out.println("�����ͷ�");
		GatherCardPanel gatherCardPanel = (GatherCardPanel) e.getComponent();
		if (Index.cardPanel != null) {
			this.cardPanel = Index.cardPanel;
			if (e.getComponent().getX() < cardPanel.getX() + cardPanel.getWidth() / 2
					&& cardPanel.getX() + cardPanel.getWidth() / 2 < e.getComponent().getX()
							+ e.getComponent().getWidth()
					&& e.getComponent().getY() < cardPanel.getY() + cardPanel.getHeight() / 2
					&& cardPanel.getHeight() / 2 < e.getComponent().getY() + e.getComponent().getHeight()) {
				// ������������
				if ((cardPanel.getCardValue().endsWith("1") && gatherCardPanel.getCardNum() == 0)
						|| (gatherCardPanel.getCardNum() > 0 && StaticData.getGathervaluemap(cardPanel.getCardValue())
								.equals(gatherCardPanel.getTop(false).getStackNode().getCardValue()))) {
					// �����������Ҫ��
					gatherCardPanel.setTop(new CardStackNode(cardPanel));
					
				}
			}
		}
	}
}
