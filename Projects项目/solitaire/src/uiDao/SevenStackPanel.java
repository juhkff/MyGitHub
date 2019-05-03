package uiDao;

import java.util.HashSet;
import java.util.Random;

import javax.swing.JLayeredPane;

import element.CardStackNode;
import element.StaticData;
import uiPaint.Index;

public class SevenStackPanel extends JLayeredPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// private static CardStackNode top = null; // �ײ�����,��������
	// private static CardStackNode bottom = null; // �ײ���
	public int cardNum = 0;
	// private SevenStackCardPanelAdapter sevenStackCardPanelAdapter;
	private int location_X, location_Y, panelHeight, panelWidth;
	private int index;

	/**
	 * ���ƶ�����Ĵ����ƶ�.�����ƶѵ�����(�ڼ����ƶ�),index��СΪ1
	 * 
	 * @param index
	 */
	public SevenStackPanel(int index, JLayeredPane secondPanel/* ,CardStackNode top,CardStackNode bottom */) {
		super();
		// TODO Auto-generated constructor stub
		// �����ƶѵĴ�Сֻ���С
		// SevenStackPanel.top=top;
		// SevenStackPanel.bottom=bottom;
		this.index = index;
		this.panelHeight = (index - 1) * StaticData.getMinilocation(3) + StaticData.getCardsize(3); // ��ʼ�߶�
		this.panelWidth = StaticData.getCardsize(2); // ��ʼ���
		this.location_X = (index - 1) * panelWidth + index * StaticData.getSevenstacklocation(0); // ��������ߵľ���
		this.location_Y = StaticData.getSevenstacklocation(1); // ���붥���ľ���
		// sevenStackCardPanelAdapter = new SevenStackCardPanelAdapter(index,
		// secondPanel);
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
				Index.cardPanelSet.add(cardPanel);
				// ���ÿ����ڴ����ƶ��е�λ��
				cardPanel.setLocation(0, i * StaticData.getMinilocation(3));
				// Ϊÿ�ſ������ü�����
				cardPanel
						.addMouseListener(/* sevenStackCardPanelAdapter */Index.sevenStackCardPanelAdapters[index - 1]); // ����ȫȷ������
				cardPanel.addMouseMotionListener(
						/* sevenStackCardPanelAdapter */Index.sevenStackCardPanelAdapters[index - 1]); // ����ȫȷ������
				// ���ư��㼶��ӵ������
				Integer layer = i;
				this.add(cardPanel, layer); // �ȼ�������

				// ���Ƽ���������
				if (Index.getTop(index) != null) {
					CardStackNode newNode = new CardStackNode(cardPanel);
					newNode.setNextNode(Index.getTop(index));
					Index.setTop(index, newNode);
				} else {
					CardStackNode top = new CardStackNode(cardPanel);
					top.setNextNode(null);
					Index.setTop(index, new CardStackNode(cardPanel));
					Index.setBottom(index, top);
				}
				this.cardNum++;
				// �Ӽ������Ƴ�
				set.remove(curIndex);
			} else {
				// �����ظ���ʱ,����ִ��
				i--;
			}
		}
		// top.getStackNode().setCanTurnOver(true);
		Index.getTop(index).getStackNode().changeToFront();
	}

	/**
	 * ���Ƽ���������
	 * 
	 * @param newTopNode
	 */
	public void push(CardStackNode newTopNode) {
		// ���Ƽ���������
		if (Index.getTop(index) != null) {
			newTopNode.setNextNode(Index.getTop(index));
			Index.setTop(index, newTopNode);
		} else {
			newTopNode.setNextNode(null);
			Index.setTop(index, newTopNode);
			Index.setBottom(index, newTopNode);
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
			return Index.getTop(index);
		else {
			CardStackNode cardStackNode = Index.getTop(index);
			if (Index.getTop(index) != null) {
				Index.setTop(index, Index.getTop(index).getNextNode());
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
		// this.setBounds(this.location_X, this.location_Y, this.panelWidth,
		// this.panelHeight);
		this.setSize(this.panelWidth, this.panelHeight);
		// this.repaint();
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
		// this.setBounds(this.location_X, this.location_Y, this.panelWidth,
		// this.panelHeight);
		this.setSize(this.panelWidth, this.panelHeight);
		// this.repaint();
	}

}
