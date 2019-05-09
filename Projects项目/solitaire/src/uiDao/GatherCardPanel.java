package uiDao;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import element.CardStackNode;
import element.StaticData;

public class GatherCardPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CardStackNode top;
	private int cardNum = 0;

	/**
	 * ��ʼ�����ƶ�,Ӧ����4�����ƶ�,������1~4
	 * 
	 * @param gateherStack_No
	 */
	public GatherCardPanel(int gateherStack_No) { // ������(1-4)
		super();
		// TODO Auto-generated constructor stub
		this.setName(String.valueOf(gateherStack_No));
		top = null;
		this.setBounds(
				StaticData.getGathercardlocation(0) + (gateherStack_No - 1)
						* (StaticData.getGathercardlocation(4) + StaticData.getGathercardlocation(2)),
				StaticData.getGathercardlocation(1), StaticData.getGathercardlocation(2),
				StaticData.getGathercardlocation(3));
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		this.setBackground(Color.BLACK);
		this.setOpaque(false);
		this.setLayout(null);
	}

	public CardStackNode getTop() {
		return getTop(true);
	}

	/**
	 * ������ƶѶ�����,�����Ƿ񽫸��ƴӶ���ȡ��
	 * 
	 * @param isPull
	 * @return CardStackNode-������
	 */
	public CardStackNode getTop(boolean isPull) {
		if (isPull) {
			if (top != null) {
				CardStackNode cardStackNode = top;
				top = top.getNextNode();
				cardNum--;
				this.remove(cardStackNode.getStackNode());
				if (top != null)
					this.add(top.getStackNode());
				// this.repaint();
				return cardStackNode;
			} else {
				return null;
			}
		} else {
			if (top != null) {
				CardStackNode cardStackNode = top;
				return cardStackNode;
			} else {
				return null;
			}
		}
	}

	/**
	 * ���Ʒ������ƶѶ���,����һ�ſ���
	 * 
	 * @param cardStackNode
	 */
	public void setTop(CardStackNode cardStackNode) { // Ӧ�û���Ҫ���һЩ���Ŀ������ԵĲ���
		if (top == null) {
			top = cardStackNode;
			top.setNextNode(null);
		} else {
			this.remove(top.getStackNode());
			cardStackNode.setNextNode(top);
			top = cardStackNode;
		}
		this.add(cardStackNode.getStackNode());
		// this.repaint();
		cardNum++;
	}

	/**
	 * ������ƶѿ�����
	 * 
	 * @return int-������
	 */
	public int getCardNum() {
		return cardNum;
	}
}
