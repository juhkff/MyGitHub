package uiDao;

import java.util.HashSet;
import java.util.Random;

import javax.swing.JLayeredPane;

import element.CardStackNode;
import element.StaticData;

public class SevenStackPanel extends JLayeredPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int cardNum = 0;
	private int location_X, location_Y, panelHeight, panelWidth;
	private int index;
	private GamePage jf;

	/**
	 * ���ƶ�����Ĵ����ƶ�.�����ƶѵ�����(�ڼ����ƶ�),index��СΪ1
	 * 
	 * @param index
	 */
	public SevenStackPanel(int index, JLayeredPane secondPanel, GamePage jf) {
		super();
		// TODO Auto-generated constructor stub
		// �����ƶѵĴ�Сֻ���С
		this.jf = jf;
		this.index = index;
		this.panelHeight = (index - 1) * StaticData.getMinilocation(3) + StaticData.getCardsize(3); // ��ʼ�߶�
		this.panelWidth = StaticData.getCardsize(2); // ��ʼ���
		this.location_X = (index - 1) * panelWidth + index * StaticData.getSevenstacklocation(0); // ��������ߵľ���
		this.location_Y = StaticData.getSevenstacklocation(1); // ���붥���ľ���
		// ���ƴ����ƶ�
		this.setBounds(location_X, location_Y, panelWidth, panelHeight);
		HashSet<Integer> set = StaticData.getCardIndexSet();
		Random random = new Random();
		for (int i = 0; i < index; i++) {
			int curIndex = random.nextInt(StaticData.getCardnum()); // ��ȡ0-13*4֮��������(������13*4)
			if (set.contains(curIndex)) {
				// δ���ɴ���ʱ,���ɴ���
				CardPanel cardPanel = new CardPanel(StaticData.getDeals(curIndex));
				// ���ڸ����ƵĴ�С
				// GamePage.cardPanelSet.add(cardPanel);
				jf.getCardPanelSet().add(cardPanel);
				System.out.println("CardPanelSet��Ԫ�ظ���Ϊ:" + jf.getCardPanelSet().length());
				// ���ÿ����ڴ����ƶ��е�λ��
				cardPanel.setLocation(0, i * StaticData.getMinilocation(3));
				// Ϊÿ�ſ������ü�����
				cardPanel.addMouseListener(jf.getSevenStackCardPanelAdapters()[index - 1]); // ����ȫȷ������
				cardPanel.addMouseMotionListener(jf.getSevenStackCardPanelAdapters()[index - 1]); // ����ȫȷ������
				// ���ư��㼶��ӵ������
				Integer layer = i;
				this.add(cardPanel, layer); // �ȼ�������

				// ���Ƽ���������
				if (jf.getTop(index) != null) {
					CardStackNode newNode = new CardStackNode(cardPanel);
					newNode.setNextNode(jf.getTop(index));
					jf.setTop(index, newNode);
				} else {
					CardStackNode top = new CardStackNode(cardPanel);
					top.setNextNode(null);
					jf.setTop(index, new CardStackNode(cardPanel));
					jf.setBottom(index, top);
				}
				this.cardNum++;
				// �Ӽ������Ƴ�
				set.remove(curIndex);
			} else {
				// �����ظ���ʱ,����ִ��
				i--;
			}
		}
		jf.getTop(index).getStackNode().changeToFront();
	}

	/**
	 * ���Ƽ���������
	 * 
	 * @param newTopNode
	 */
	public void push(CardStackNode newTopNode) {
		// ���Ƽ���������
		if (jf.getTop(index) != null) {
			newTopNode.setNextNode(jf.getTop(index));
			jf.setTop(index, newTopNode);
		} else {
			newTopNode.setNextNode(null);
			jf.setTop(index, newTopNode);
			jf.setBottom(index, newTopNode);
		}
		this.cardNum++;
	}

	/**
	 * ��ȡ����/���²�����
	 * 
	 * @param isPull
	 * @return CardStackNode/null
	 */
	public CardStackNode getTop(boolean isPull) {
		if (!isPull)
			return jf.getTop(index);
		else {
			CardStackNode cardStackNode = jf.getTop(index);
			if (jf.getTop(index) != null) {
				jf.setTop(index, jf.getTop(index).getNextNode());
				this.cardNum--;
			}
			return cardStackNode;
		}
	}

	/**
	 * ��ȡ�ƶѵ�����
	 * 
	 * @return int
	 */
	public int getCardNum() {
		return cardNum;
	}

	/**
	 * ���ݼ��ٵ��������Ĵ��ƶѵĸ߶�(��1->0�����������ʱδ����)
	 * 
	 * @param deleteCardNum
	 */
	public void resetHeightAfterDelete(int deleteCardNum) {
		this.panelHeight -= deleteCardNum * StaticData.getMinilocation(3);
		if (getCardNum() == 0) // ����ʱ
			this.panelHeight += StaticData.getMinilocation(3);
		this.setSize(this.panelWidth, this.panelHeight);
	}

	/**
	 * �������ӵ��������Ĵ��ƶѵĸ߶�(��0->1�����������ʱδ����)
	 * 
	 * @param addCardNum
	 */
	public void resetHeightAfterAdd(int addCardNum) {
		this.panelHeight += addCardNum * StaticData.getMinilocation(3);
		if (getCardNum() - addCardNum == 0) // δ��ǰ����ʱ
			this.panelHeight -= StaticData.getMinilocation(3);
		this.setSize(this.panelWidth, this.panelHeight);
	}

	/**
	 * ����Ӧ�ÿ������¼��
	 */
	public void reDistanceCards() {
		// TODO Auto-generated method stub
		CardStackNode tempTop = this.getTop(false);
		for (int j = 0; j < cardNum - 1; j++) {
			tempTop.getStackNode().setLocation(0, (cardNum - j - 1) * StaticData.getMinilocation(3));
			tempTop = tempTop.getNextNode();
		}
		this.panelWidth = StaticData.getCardsize(2); // ��ʼ���
		this.panelHeight = (cardNum - 1) * StaticData.getMinilocation(3) + StaticData.getCardsize(3); // ��ʼ�߶�
		if (cardNum == 0) {
			this.panelHeight += StaticData.getMinilocation(3);
		}
		this.setSize(this.panelWidth, this.panelHeight);
	}

}
