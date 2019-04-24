package uiDao;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import element.CardStackNode;
import element.StaticData;

/*
 * �ѷ����ķ��ƶ�
 */
public class DealedStackPanel extends JPanel {
	private CardStackNode top = null;
	private int dealedNum = 0;

	public DealedStackPanel() {
		super();
		this.setBounds(StaticData.getDealedlocation(0), StaticData.getDealedlocation(1),
				StaticData.getDealedlocation(2), StaticData.getDealedlocation(3));
		this.setLayout(null);
		// this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	}

	public void pushToDealedStack(CardStackNode cardStackNode) {
		// �����ƶ��ж����Ʒ����ѷ������ƶ�
		if (top == null) {
			top = cardStackNode;
			top.setNextNode(null);
		} else {
			cardStackNode.setNextNode(top);
			top = cardStackNode;
		}
		this.add(cardStackNode.getStackNode());
		dealedNum++;
	}

	public CardStackNode selectAllDealedCard() {
		// ���ѷ��������ռ���������top(�˶�������Ƽ��Ƿ��ƶ���ײ�����)
		CardStackNode result = top;
		while (top != null) {
			CardPanel curPanel = top.getStackNode();
			curPanel.changeToBack();
			this.remove(curPanel);
			top = top.getNextNode();
		}
		this.repaint();
		dealedNum = 0;
		return result;
	}

	public CardStackNode getTop() {
		CardStackNode cur = top;
		top = top.getNextNode();
		dealedNum--;
		return cur;
	}

	public void setTop(CardStackNode cardStackNode) {
		if (top == null) {
			top = cardStackNode;
			top.setNextNode(null);
		} else {
			cardStackNode.setNextNode(top);
			top = cardStackNode;
			dealedNum++;
			this.add(cardStackNode.getStackNode());
		}
	}

	public int getDealedNum() {
		return dealedNum;
	}

}
