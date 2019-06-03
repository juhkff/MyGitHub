package uiDao;

import java.awt.Color;
import java.util.Random;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import element.CardStackNode;
import element.StaticData;

/*
 * ���ƶ�
 */
public class DealStackPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CardStackNode top = null;
	private int dealNum = 0;

	/**
	 * ��ʼ�����ƶ�,Ӧ��ֻ��һ�����ƶ�,����0-�ƶ��������޵�Set<Integer>
	 * 
	 * @param thisSet
	 */
	public DealStackPanel(Set<Integer> thisSet, GamePage jf) {
		super();
		this.setBounds(StaticData.getPanelsize(0), StaticData.getPanelsize(1), StaticData.getPanelsize(2),
				StaticData.getPanelsize(3));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
		Random random = new Random();
		this.setLayout(null);
		for (int i = 0; i < StaticData.getDealnum(); i++) {
			int curIndex = random.nextInt(StaticData.getCardnum()); // ��ȡ0-13*4֮��������(������13*4)
			if (thisSet.contains(curIndex)) {
				// δ���ɴ���ʱ,���ɴ���
				CardPanel cardPanel;
				if (StaticData.getBACKGROUNDURL() == null || StaticData.getBACKGROUNDURL().equals(""))
					cardPanel = new CardPanel(StaticData.getDeals(curIndex));
				else
					cardPanel = new CardPanel(StaticData.getDeals(curIndex), StaticData.getBACKGROUNDURL());
				pushCardStackNode(new CardStackNode(cardPanel), true);
				// ���ڸ����ƵĴ�С
				jf.getCardPanelSet().add(cardPanel);
				System.out.println("CardPanelSet��Ԫ�ظ���Ϊ:" + jf.getCardPanelSet().length());
				thisSet.remove(curIndex);
			} else {
				// �����ظ���ʱ,����ִ��
				i--;
			}
		}
		this.add(top.getStackNode());
		// ����������з��ƺ�
		top.getStackNode().setCanTurnOver(true);
	}

	/**
	 * �ӷ��ƶ��з������������(�������Ѿ��������ػ�)
	 * 
	 * @return CardStackNode-������
	 */
	public CardStackNode pullFromDealStack() {
		// �ӷ��ƶ��з������������
		CardStackNode cur = top;
		top = top.getNextNode();

		if (cur.getStackNode().isCanTurnOver()) {
			cur.getStackNode().changeToFront();
		}
		this.remove(cur.getStackNode());
		if (top != null) {
			top.getStackNode().setCanTurnOver(true);
			this.add(top.getStackNode());
		} else {
			this.repaint();
		}
		cur.setNextNode(null);
		dealNum--;
		// this.repaint();
		return cur;
	}

	/**
	 * ���뷢�ƶ�,���뿨�����Ƿ�Ϊ������.��isSingle==true,���ػ�
	 * 
	 * @param curStackNode
	 * @param isSingle
	 */
	public void pushCardStackNode(CardStackNode curStackNode, boolean isSingle) {
		// ���뷢�ƶ�
		if (isSingle) {
			if (top == null) {
				top = curStackNode;
				top.setNextNode(null);
				top.getStackNode().setCanTurnOver(true);
			} else {
				this.remove(top.getStackNode());
				curStackNode.setNextNode(top);
				top.getStackNode().setCanTurnOver(false);
				top = curStackNode;
				top.getStackNode().setCanTurnOver(true);
				this.add(top.getStackNode());
			}
			dealNum++;
		} else {
			// ��������������ϴ��
			CardStackNode tempNode;
			while (curStackNode != null) {
				tempNode = curStackNode.getNextNode();
				if (top == null) {
					top = curStackNode;
					top.setNextNode(null);
					top.getStackNode().setCanTurnOver(false);
				} else {
					curStackNode.setNextNode(top);
					top = curStackNode;
					top.getStackNode().setCanTurnOver(false);
				}
				dealNum++;
				curStackNode = tempNode;
			}
			if (top != null) {
				top.getStackNode().setCanTurnOver(true);
				this.add(top.getStackNode());
			}
			this.repaint(); // ΪʲôҪrepaint?
		}
	}

	/**
	 * ��÷��ƶ�����
	 * 
	 * @return int-����
	 */
	public int getDealCardNum() {
		return dealNum;
	}

	/**
	 * ���ƶѶ������Ƿ�������
	 * 
	 * @return boolean-������������
	 */
	public boolean isPositive() {
		return top.getStackNode().isPositive();
	}
}
