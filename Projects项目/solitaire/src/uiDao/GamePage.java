package uiDao;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import adapter.DealStackAdapter;
import adapter.DealedStackAdapter;
import adapter.GamePageAdapter;
import adapter.GatherStackAdapter;
import adapter.SevenStackAdapter;
import adapter.SevenStackCardPanelAdapter;
import element.CardPanelSet;
import element.CardStackNode;
import element.StaticData;

public class GamePage extends /* JFrame */JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// private GameMenuBar gameMenuBar;
	// private JPanel contentPanel;
	private JLayeredPane secondPanel; // 用于移动组件时的顶层面板
	private Set<Integer> thisSet;
	private CardPanelSet cardPanelSet;
	private DealStackPanel dealStackPanel;
	private DealedStackPanel dealedStackPanel;
	private GatherCardPanel[] gatherCardPanels;
	private SevenStackPanel[] sevenStackPanels;
	private GameFoot gameFoot;
	private ImageIcon backGroundImg;
	// 监听器类
	private DealStackAdapter dealStackAdapter;
	private DealedStackAdapter dealedStackAdapter;
	private GatherStackAdapter[] gatherStackAdapters;
	private SevenStackCardPanelAdapter[] sevenStackCardPanelAdapters;
	private SevenStackAdapter[] sevenStackAdapters;
	private GamePageAdapter gamePageAdapter;
	private CardStackNode[] top;
	private CardStackNode[] bottom;
	private CardStackNode tranBottom;
	// 中间变量
	private CardPanel cardPanel;
	private boolean hasClicked;
	private boolean isSingle;
	private String clickComponentName;

	/*
	 * public GamePage() { init(); }
	 */

	public void init() {
		componentInit_First();
		listenerInit();
		componentInit();
		setNameInit();
		listenerLoad();
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		if (StaticData.getGLOBALBACKGROUNDURL() != null && !StaticData.getGLOBALBACKGROUNDURL().equals("")) {
			// 设置了图片路径
			backGroundImg = new ImageIcon(StaticData.getGLOBALBACKGROUNDURL());
			// System.out.println("gameFootHeight=" + gameFoot.getHeight());
			backGroundImg.setImage(backGroundImg.getImage().getScaledInstance(
					StaticData.getFRAMEWIDTH(), StaticData.getFRAMEHEIGHT() - gameFoot.getHeight()
							- StaticData.getGAMEMENUBARHEIGHT() - StaticData.getGAMETITLEHEIGHT(),
					Image.SCALE_DEFAULT));
			g.drawImage(backGroundImg.getImage(), 0, 0, null);
		}
	}

	/**
	 * 新建游戏的刷新界面
	 */
	private void setNameInit() {
		this/* contentPanel */.setName("contentPanel");
		dealStackPanel.setName("dealStackPanel");
		dealedStackPanel.setName("dealedStackPanel");
		for (int i = 1; i < gatherCardPanels.length + 1; i++)
			gatherCardPanels[i - 1].setName("gatherCardPanel" + i);
		for (int i = 1; i < sevenStackPanels.length + 1; i++)
			sevenStackPanels[i - 1].setName("sevenStackPanel" + i);
	}

	private void componentInit_First() {
		secondPanel = new JLayeredPane();
	}

	@SuppressWarnings("static-access")
	private void componentInit() {
		gameFoot = new GameFoot();
		gatherCardPanels = new GatherCardPanel[StaticData.getGathernum()];
		sevenStackPanels = new SevenStackPanel[StaticData.getSevenstacknum()];
		top = new CardStackNode[StaticData.getSevenstacknum()];
		bottom = new CardStackNode[StaticData.getSevenstacknum()];

		thisSet = new StaticData().getCardIndexSet();

		// contentPanel = new JPanel();

		// gameMenuBar = new GameMenuBar(this);
		cardPanelSet = new CardPanelSet();
		dealStackPanel = new DealStackPanel(thisSet, this);
		dealedStackPanel = new DealedStackPanel();
		hasClicked = false;

		for (int i = 1; i < StaticData.getGathernum() + 1; i++)
			gatherCardPanels[i - 1] = new GatherCardPanel(i);
		for (int i = 1; i < StaticData.getSevenstacknum() + 1; i++) {
			top[i - 1] = null;
			bottom[i - 1] = null;
			sevenStackPanels[i - 1] = new SevenStackPanel(i, secondPanel, this);
		}
		secondPanel.setSize(StaticData.FRAMESIZE[0], StaticData.FRAMESIZE[1]);
		secondPanel.setOpaque(false);

		this/* contentPanel */.setLayout(null);
		this/* contentPanel */.add(secondPanel);
		this/* contentPanel */.add(gameFoot);
		this/* contentPanel */.add(dealStackPanel);
		this/* contentPanel */.add(dealedStackPanel);

		for (int i = 0; i < StaticData.getGathernum(); i++)
			this/* contentPanel */.add(gatherCardPanels[i]);
		for (int i = 0; i < StaticData.getSevenstacknum(); i++)
			this/* contentPanel */.add(sevenStackPanels[i]);
		this.setSize(StaticData.FRAMESIZE[0], StaticData.FRAMESIZE[1]);
		// this.setBackground(Color.BLACK);
		if (StaticData.getBACKGROUNDCOLOR() != null) {
			this.setBackground(StaticData.getBACKGROUNDCOLOR());
		}
	}

	private void listenerInit() {
		dealStackAdapter = new DealStackAdapter(this);
		dealedStackAdapter = new DealedStackAdapter(secondPanel, this);
		gatherStackAdapters = new GatherStackAdapter[StaticData.getGathernum()];
		gamePageAdapter = new GamePageAdapter(this);
		for (int i = 0; i < StaticData.getGathernum(); i++) {
			gatherStackAdapters[i] = new GatherStackAdapter(secondPanel, this);
		}
		sevenStackCardPanelAdapters = new SevenStackCardPanelAdapter[StaticData.getSevenstacknum()];
		sevenStackAdapters = new SevenStackAdapter[StaticData.getSevenstacknum()];
		for (int i = 0; i < StaticData.getSevenstacknum(); i++) {
			sevenStackCardPanelAdapters[i] = new SevenStackCardPanelAdapter(i + 1, secondPanel, this);
			sevenStackAdapters[i] = new SevenStackAdapter(i + 1, this);
		}
	}

	private void listenerLoad() {
		dealStackPanel.addMouseListener(dealStackAdapter);
		dealedStackPanel.addMouseListener(dealedStackAdapter);
		dealedStackPanel.addMouseMotionListener(dealedStackAdapter);
		for (int i = 0; i < StaticData.getGathernum(); i++) {
			gatherCardPanels[i].addMouseListener(gatherStackAdapters[i]);
			gatherCardPanels[i].addMouseMotionListener(gatherStackAdapters[i]);
		}
		for (int i = 0; i < StaticData.getSevenstacknum(); i++) {
			sevenStackPanels[i].addMouseListener(sevenStackAdapters[i]);
			sevenStackPanels[i].addMouseMotionListener(sevenStackAdapters[i]);
		}
		this.addMouseListener(gamePageAdapter);
		this.addMouseMotionListener(gamePageAdapter);
	}

	public GameFoot getGameFoot() {
		return gameFoot;
	}

	public CardStackNode getTop(int index) {
		return top[index - 1];
	}

	public void setTop(int index, CardStackNode top) {
		this.top[index - 1] = top;
	}

	public CardStackNode getBottom(int index) {
		return bottom[index - 1];
	}

	public void setBottom(int index, CardStackNode bottom) {
		this.bottom[index - 1] = bottom;
	}

	public boolean isHasClicked() {
		return hasClicked;
	}

	public void setHasClicked(boolean hasClicked) {
		this.hasClicked = hasClicked;
	}

	public String getClickComponentName() {
		return clickComponentName;
	}

	public void setClickComponentName(String clickComponentName) {
		this.clickComponentName = clickComponentName;
	}

	/**
	 * 重新布局
	 */
	public void reSize() {
		this.setSize(StaticData.FRAMESIZE[0], StaticData.FRAMESIZE[1]);
		reSizeSevenStackPanel();
		reSizeGatherCardPanel();
		reSizeDealStackPanel();
		reSizeDealedStackPanel();
		reSizeSecondPanel();
		reSizeGameFoot();
		if (StaticData.isCARDSIZEChanged()) { // 如果卡牌大小发生了变化
			reSizeCardPanel();
			rePaintCardPanel();
		}
		if (StaticData.isCARDBGChanged()) // 如果卡牌背景发生了变化
			rePaintCardPanel();
	}

	/**
	 * 重新设置边框颜色
	 */
	public void rePaintBorder() {
		if (StaticData.getCARDBORDERCOLOR() != null) {
			dealStackPanel.setBorder(BorderFactory.createLineBorder(StaticData.getCARDBORDERCOLOR(), 1, true));
		} else {
			dealStackPanel.setBorder(BorderFactory.createLineBorder(StaticData.getDEFAULTCARDBORDERCOLOR(), 1, true));
		}
	}

	private void reSizeGameFoot() {
		// TODO Auto-generated method stub
		gameFoot.setBounds(0, StaticData.getFRAMEHEIGHT() - 105, StaticData.getFRAMEWIDTH(), 30);
	}

	private void reSizeSecondPanel() {
		// TODO Auto-generated method stub
		secondPanel.setSize(StaticData.FRAMESIZE[0], StaticData.FRAMESIZE[1]);
	}

	private void reSizeCardPanel() {
		// TODO Auto-generated method stub
		cardPanelSet.reSizeCards();
		StaticData.setCARDSIZEChanged(false);
	}

	private void rePaintCardPanel() {
		cardPanelSet.rePaintCards();
		StaticData.setCARDBGChanged(false);
	}

	private void reSizeDealedStackPanel() {
		// TODO Auto-generated method stub
		dealedStackPanel.setBounds(StaticData.getDealedlocation(0), StaticData.getDealedlocation(1),
				StaticData.getDealedlocation(2), StaticData.getDealedlocation(3));
	}

	private void reSizeDealStackPanel() {
		// TODO Auto-generated method stub
		dealStackPanel.setBounds(StaticData.getPanelsize(0), StaticData.getPanelsize(1), StaticData.getPanelsize(2),
				StaticData.getPanelsize(3));
	}

	private void reSizeGatherCardPanel() {
		// TODO Auto-generated method stub
		for (int i = 1; i < StaticData.getGathernum() + 1; i++)
			gatherCardPanels[i - 1].setBounds(
					StaticData.getGathercardlocation(0)
							+ (i - 1) * (StaticData.getGathercardlocation(4) + StaticData.getGathercardlocation(2)),
					StaticData.getGathercardlocation(1), StaticData.getGathercardlocation(2),
					StaticData.getGathercardlocation(3));
	}

	private void reSizeSevenStackPanel() {
		// TODO Auto-generated method stub
		int location_X;
		int location_Y;
		int panelHeight;
		int panelWidth = StaticData.getCardsize(2); // 初始宽度
		for (int i = 1; i < StaticData.getSevenstacknum() + 1; i++) {
			int cardNum = sevenStackPanels[i - 1].getCardNum();
			sevenStackPanels[i - 1].reDistanceCards();
			System.out.println("卡牌数为:" + cardNum);
			if (cardNum > 0)
				panelHeight = (cardNum - 1) * StaticData.getMinilocation(3) + StaticData.getCardsize(3);
			else
				panelHeight = /* 0 */StaticData.getCardsize(3);
			location_X = (i - 1) * panelWidth + i * StaticData.getSevenstacklocation(0); // 距离最左边的距离
			location_Y = StaticData.getSevenstacklocation(1); // 距离顶部的距离
			sevenStackPanels[i - 1].setBounds(location_X, location_Y, panelWidth, panelHeight);
		}
	}

	public void refresh() {
		// 取消定位
		this.setHasClicked(false);
		this.setSingle(false);
		this.setClickComponentName(null);
		this.setTranBottom(null);
		this.gameFoot.reset();
	}

	public boolean isSingle() {
		return isSingle;
	}

	public void setSingle(boolean isSingle) {
		this.isSingle = isSingle;
	}

	public CardStackNode getTranBottom() {
		return tranBottom;
	}

	public void setTranBottom(CardStackNode tranBottom) {
		this.tranBottom = tranBottom;
	}

	/*
	 * public GameMenuBar getGameMenuBar() { return gameMenuBar; }
	 * 
	 * public void setGameMenuBar(GameMenuBar gameMenuBar) { this.gameMenuBar =
	 * gameMenuBar; }
	 */

	/*
	 * public JPanel getContentPanel() { return contentPanel; }
	 * 
	 * public void setContentPanel(JPanel contentPanel) { this.contentPanel =
	 * contentPanel; }
	 */

	public JLayeredPane getSecondPanel() {
		return secondPanel;
	}

	public void setSecondPanel(JLayeredPane secondPanel) {
		this.secondPanel = secondPanel;
	}

	public Set<Integer> getThisSet() {
		return thisSet;
	}

	public void setThisSet(Set<Integer> thisSet) {
		this.thisSet = thisSet;
	}

	public CardPanelSet getCardPanelSet() {
		return cardPanelSet;
	}

	public void setCardPanelSet(CardPanelSet cardPanelSet) {
		this.cardPanelSet = cardPanelSet;
	}

	public DealStackPanel getDealStackPanel() {
		return dealStackPanel;
	}

	public void setDealStackPanel(DealStackPanel dealStackPanel) {
		this.dealStackPanel = dealStackPanel;
	}

	public DealedStackPanel getDealedStackPanel() {
		return dealedStackPanel;
	}

	public void setDealedStackPanel(DealedStackPanel dealedStackPanel) {
		this.dealedStackPanel = dealedStackPanel;
	}

	public GatherCardPanel[] getGatherCardPanels() {
		return gatherCardPanels;
	}

	public void setGatherCardPanels(GatherCardPanel[] gatherCardPanels) {
		this.gatherCardPanels = gatherCardPanels;
	}

	public SevenStackPanel[] getSevenStackPanels() {
		return sevenStackPanels;
	}

	public void setSevenStackPanels(SevenStackPanel[] sevenStackPanels) {
		this.sevenStackPanels = sevenStackPanels;
	}

	public SevenStackCardPanelAdapter[] getSevenStackCardPanelAdapters() {
		return sevenStackCardPanelAdapters;
	}

	public void setSevenStackCardPanelAdapters(SevenStackCardPanelAdapter[] sevenStackCardPanelAdapters) {
		this.sevenStackCardPanelAdapters = sevenStackCardPanelAdapters;
	}

	public SevenStackAdapter[] getSevenStackAdapters() {
		return sevenStackAdapters;
	}

	public void setSevenStackAdapters(SevenStackAdapter[] sevenStackAdapters) {
		this.sevenStackAdapters = sevenStackAdapters;
	}

	public CardStackNode[] getTop() {
		return top;
	}

	public void setTop(CardStackNode[] top) {
		this.top = top;
	}

	public CardStackNode[] getBottom() {
		return bottom;
	}

	public void setBottom(CardStackNode[] bottom) {
		this.bottom = bottom;
	}

	public CardPanel getCardPanel() {
		return cardPanel;
	}

	public void setCardPanel(CardPanel cardPanel) {
		this.cardPanel = cardPanel;
	}
}
