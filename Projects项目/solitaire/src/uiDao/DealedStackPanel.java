package uiDao;

import javax.swing.JPanel;

import element.CardStackNode;
import element.StaticData;

/*
 * �ѷ����ķ��ƶ�
 */
public class DealedStackPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CardStackNode top = null;
	// private JPanel topPanel=null;
	private int dealedNum = 0;

	/**
	 * ��ʼ�����ƶ�,Ӧ��ֻ��һ�����ƶ�
	 */
	public DealedStackPanel() {
		super();
		// topPanel=new JPanel();
		// topPanel.setBounds(StaticData.getCardsize(0), StaticData.getCardsize(0),
		// StaticData.getCardsize(0), StaticData.getCardsize(0));
		// topPanel.setOpaque(false);
		this.setBounds(StaticData.getDealedlocation(0), StaticData.getDealedlocation(1),
				StaticData.getDealedlocation(2), StaticData.getDealedlocation(3));
		this.setLayout(null);
		// this.add(topPanel);
		// this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	}

	/**
	 * �����ƶ��ж����Ʒ����ѷ������ƶ�,ʵ�ʼ�ΪsetTop(CardStackNode)
	 * 
	 * @param cardStackNode
	 */
	public void pushToDealedStack(CardStackNode cardStackNode) {
		// �����ƶ��ж����Ʒ����ѷ������ƶ�
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
		dealedNum++;
	}

	/**
	 * ���ѷ��������ռ���������top(�˶�������Ƽ��Ƿ��ƶ���ײ�����),���ػ�÷��ƶ�
	 * 
	 * @return CardStackNode-������
	 */
	public CardStackNode selectAllDealedCard() {
		// ���ѷ��������ռ���������top(�˶�������Ƽ��Ƿ��ƶ���ײ�����)
		CardStackNode result = top;
		if (top != null)
			this.remove(top.getStackNode());
		while (top != null) {
			CardPanel curPanel = top.getStackNode();
			curPanel.changeToBack();
			// this.remove(curPanel);
			top = top.getNextNode();
		}
		this.repaint();
		dealedNum = 0;
		return result;
	}

	/**
	 * ��ȡ������(ȡ��������)
	 * 
	 * @return CardStackNode-������
	 */
	public CardStackNode getTop() {
		return getTop(true);
	}

	/**
	 * ��ȡ������
	 * 
	 * @param isPull
	 * @return CardStackNode-������
	 */
	public CardStackNode getTop(boolean isPull) {
		if (!isPull)
			return top;
		CardStackNode cur = top;
		top = top.getNextNode();
		this.remove(cur.getStackNode());
		// this.repaint();
		if (top != null)
			this.add(top.getStackNode());
		dealedNum--;
		return cur;
	}

	/**
	 * ���ö�����
	 * 
	 * @param cardStackNode
	 */
	public void setTop(CardStackNode cardStackNode) {
		if (top == null) {
			top = cardStackNode;
			top.setNextNode(null);
		} else {
			this.remove(top.getStackNode());
			cardStackNode.setNextNode(top);
			top = cardStackNode;
		}
		dealedNum++;
		this.add(cardStackNode.getStackNode());
	}

	/**
	 * ��÷��ƶѵ�����
	 * 
	 * @return int-����
	 */
	public int getDealedNum() {
		return dealedNum;
	}

}
