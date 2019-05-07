package uiPaint;

import java.awt.Dimension;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import adapter.DealStackAdapter;
import adapter.DealedStackAdapter;
import adapter.GatherStackAdapter;
import adapter.IndexAdapter;
import adapter.SevenStackAdapter;
import adapter.SevenStackCardPanelAdapter;
import element.CardPanelSet;
import element.CardStackNode;
import element.StaticData;
import uiDao.CardPanel;
import uiDao.DealStackPanel;
import uiDao.DealedStackPanel;
import uiDao.GameMenuBar;
import uiDao.GatherCardPanel;
import uiDao.SevenStackPanel;

public class Index extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JFrame jf;
	public static GameMenuBar gameMenuBar;
	private static JPanel contentPanel = new JPanel();
	private static JLayeredPane secondPanel = new JLayeredPane(); // 用于移动组件时的顶层面板
	@SuppressWarnings("static-access")
	private static Set<Integer> thisSet = new StaticData().getCardIndexSet();
	public static CardPanelSet cardPanelSet = new CardPanelSet();
	public static DealStackPanel dealStackPanel = new DealStackPanel(thisSet);
	public static DealedStackPanel dealedStackPanel = new DealedStackPanel();
	public static GatherCardPanel[] gatherCardPanels = new GatherCardPanel[StaticData.getGathernum()];
	public static CardPanel cardPanel = null;
	// public static CardPanel clickCardPanel=null;
	private static boolean hasClicked = false;
	private static boolean isSingle = false;
	private static String clickComponentName;
	public static SevenStackPanel[] sevenStackPanels = new SevenStackPanel[StaticData.getSevenstacknum()];
	public static SevenStackCardPanelAdapter[] sevenStackCardPanelAdapters = new SevenStackCardPanelAdapter[StaticData
			.getSevenstacknum()];
	private static SevenStackAdapter[] sevenStackAdapters = new SevenStackAdapter[StaticData.getSevenstacknum()];
	private static CardStackNode[] top = new CardStackNode[StaticData.getSevenstacknum()];
	private static CardStackNode[] bottom = new CardStackNode[StaticData.getSevenstacknum()];
	private static CardStackNode tranBottom;

	public Index() {
		init();
	}

	private void init() {
		gameMenuBar=new GameMenuBar();
		this.setJMenuBar(gameMenuBar);
		for (int i = 0; i < StaticData.getSevenstacknum(); i++) {
			sevenStackCardPanelAdapters[i] = new SevenStackCardPanelAdapter(i + 1, secondPanel);
			sevenStackAdapters[i] = new SevenStackAdapter(i + 1);
		}
		componentInit();
		setNameInit();
		listenerInit();
		getContentPane().add(contentPanel);
	}
	
	/**
	 * 新建游戏的刷新界面
	 *//*
	public static void refreshUI() {
		cardPanelSet = new CardPanelSet();
		StaticData.setNewCardIndexSet();
		thisSet = StaticData.getCardIndexSet();
		dealStackPanel = new DealStackPanel(thisSet);
		dealedStackPanel = new DealedStackPanel();
		for (int i = 0; i < StaticData.getSevenstacknum(); i++) {
			sevenStackCardPanelAdapters[i] = new SevenStackCardPanelAdapter(i + 1, secondPanel);
			sevenStackAdapters[i] = new SevenStackAdapter(i + 1);
		}
		componentInit();
		setNameInit();
		listenerInit();
	}*/
	
	public static void setNameInit() {
		contentPanel.setName("contentPanel");
		dealStackPanel.setName("dealStackPanel");
		dealedStackPanel.setName("dealedStackPanel");
		for (int i = 1; i < gatherCardPanels.length + 1; i++)
			gatherCardPanels[i - 1].setName("gatherCardPanel" + i);
		for (int i = 1; i < sevenStackPanels.length + 1; i++)
			sevenStackPanels[i - 1].setName("sevenStackPanel" + i);
	}

	public static void componentInit() {
		for (int i = 1; i < StaticData.getGathernum() + 1; i++)
			gatherCardPanels[i - 1] = new GatherCardPanel(i);
		for (int i = 1; i < StaticData.getSevenstacknum() + 1; i++) {
			top[i - 1] = null;
			bottom[i - 1] = null;
			sevenStackPanels[i - 1] = new SevenStackPanel(i, secondPanel);
		}
		secondPanel.setSize(StaticData.FRAMESIZE[0], StaticData.FRAMESIZE[1]);
		secondPanel.setOpaque(false);

		contentPanel.setLayout(null);
		contentPanel.add(secondPanel);
		contentPanel.add(dealStackPanel);
		contentPanel.add(dealedStackPanel);

		for (int i = 0; i < StaticData.getGathernum(); i++)
			contentPanel.add(gatherCardPanels[i]);
		for (int i = 0; i < StaticData.getSevenstacknum(); i++)
			contentPanel.add(sevenStackPanels[i]);
	}

	public static void listenerInit() {
		DealedStackAdapter dealedStackAdapter = new DealedStackAdapter(secondPanel);
		dealStackPanel.addMouseListener(new DealStackAdapter(/* dealStackPanel, dealedStackPanel */));
		dealedStackPanel.addMouseListener(dealedStackAdapter);
		dealedStackPanel.addMouseMotionListener(dealedStackAdapter);

		GatherStackAdapter[] gatherStackAdapters = new GatherStackAdapter[StaticData.getGathernum()];
		for (int i = 0; i < StaticData.getGathernum(); i++) {
			gatherStackAdapters[i] = new GatherStackAdapter(secondPanel);
			gatherCardPanels[i].addMouseListener(gatherStackAdapters[i]);
			gatherCardPanels[i].addMouseMotionListener(gatherStackAdapters[i]);
		}
		for (int i = 0; i < StaticData.getSevenstacknum(); i++) {
			sevenStackPanels[i].addMouseListener(sevenStackAdapters[i]);
			sevenStackPanels[i].addMouseMotionListener(sevenStackAdapters[i]);
		}
	}

	public static CardStackNode getTop(int index) {
		return top[index - 1];
	}

	public static void setTop(int index, CardStackNode top) {
		Index.top[index - 1] = top;
	}

	public static CardStackNode getBottom(int index) {
		return bottom[index - 1];
	}

	public static void setBottom(int index, CardStackNode bottom) {
		Index.bottom[index - 1] = bottom;
	}

	public static boolean isHasClicked() {
		return hasClicked;
	}

	public static void setHasClicked(boolean hasClicked) {
		Index.hasClicked = hasClicked;
	}

	public static String getClickComponentName() {
		return clickComponentName;
	}

	public static void setClickComponentName(String clickComponentName) {
		Index.clickComponentName = clickComponentName;
	}

	/**
	 * 重新布局
	 */
	public static void reSize() {
		reSizeSevenStackPanel();
		reSizeGatherCardPanel();
		reSizeDealStackPanel();
		reSizeDealedStackPanel();
		reSizeSecondPanel();
		if (StaticData.isCARDSIZEChanged()) // 如果卡牌大小发生了变化
			reSizeCardPanel();
	}

	private static void reSizeSecondPanel() {
		// TODO Auto-generated method stub
		secondPanel.setSize(StaticData.FRAMESIZE[0], StaticData.FRAMESIZE[1]);
	}

	private static void reSizeCardPanel() {
		// TODO Auto-generated method stub
		cardPanelSet.reSizeCards();
	}

	private static void reSizeDealedStackPanel() {
		// TODO Auto-generated method stub
		dealedStackPanel.setBounds(StaticData.getDealedlocation(0), StaticData.getDealedlocation(1),
				StaticData.getDealedlocation(2), StaticData.getDealedlocation(3));
	}

	private static void reSizeDealStackPanel() {
		// TODO Auto-generated method stub
		dealStackPanel.setBounds(StaticData.getPanelsize(0), StaticData.getPanelsize(1), StaticData.getPanelsize(2),
				StaticData.getPanelsize(3));
	}

	private static void reSizeGatherCardPanel() {
		// TODO Auto-generated method stub
		for (int i = 1; i < StaticData.getGathernum() + 1; i++)
			gatherCardPanels[i - 1].setBounds(
					StaticData.getGathercardlocation(0)
							+ (i - 1) * (StaticData.getGathercardlocation(4) + StaticData.getGathercardlocation(2)),
					StaticData.getGathercardlocation(1), StaticData.getGathercardlocation(2),
					StaticData.getGathercardlocation(3));
	}

	private static void reSizeSevenStackPanel() {
		// TODO Auto-generated method stub
		int location_X;
		int location_Y;
		int panelHeight;
		int panelWidth = StaticData.getCardsize(2); // 初始宽度
		for (int i = 1; i < StaticData.getSevenstacknum() + 1; i++) {
			int cardNum = sevenStackPanels[i - 1].getCardNum();
			System.out.println("卡牌数为:" + cardNum);
			if (cardNum > 0)
				panelHeight = (cardNum - 1) * StaticData.getMinilocation(3) + StaticData.getCardsize(3);
			else
				panelHeight = /* 0 */StaticData.getCardsize(3);
			// panelHeight = (i - 1) * StaticData.getMinilocation(3) +
			// StaticData.getCardsize(3); // 初始高度
			location_X = (i - 1) * panelWidth + i * StaticData.getSevenstacklocation(0); // 距离最左边的距离
			location_Y = StaticData.getSevenstacklocation(1); // 距离顶部的距离
			sevenStackPanels[i - 1].setBounds(location_X, location_Y, panelWidth, panelHeight);
		}
	}

	public static void refresh() {
		// 取消定位
		Index.setHasClicked(false);
		Index.setSingle(false);
		Index.setClickComponentName(null);
		Index.setTranBottom(null);
		Index.cardPanel = null;
	}

	public static boolean isSingle() {
		return isSingle;
	}

	public static void setSingle(boolean isSingle) {
		Index.isSingle = isSingle;
	}

	public static CardStackNode getTranBottom() {
		return tranBottom;
	}

	public static void setTranBottom(CardStackNode tranBottom) {
		Index.tranBottom = tranBottom;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		jf = new Index();
		jf.setTitle("Solitaire-纸牌游戏");
		// 窗口退出行为
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 设置窗口大小可变
		jf.setResizable(true);
		// 窗口大小
		jf.setSize(StaticData.FRAMESIZE[0], StaticData.FRAMESIZE[1]);
		jf.addComponentListener(new IndexAdapter());
		Dimension dimension = new Dimension(StaticData.getFrameminwidth(), StaticData.getFrameminheight());
		jf.setMinimumSize(dimension);
		jf.setName("frame");
		// 设置窗口打开居中
		jf.setLocationRelativeTo(null);
		// 展示窗口
		jf.setVisible(true);
	}
}
