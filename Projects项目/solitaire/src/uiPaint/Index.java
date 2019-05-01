package uiPaint;

import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import adapter.DealStackAdapter;
import adapter.DealedStackAdapter;
import adapter.GatherStackAdapter;
import adapter.SevenStackCardPanelAdapter;
import element.CardStackNode;
import element.StaticData;
import uiDao.CardPanel;
import uiDao.DealStackPanel;
import uiDao.DealedStackPanel;
import uiDao.GatherCardPanel;
import uiDao.SevenStackPanel;

public class Index extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel = new JPanel();
	private JLayeredPane secondPanel = new JLayeredPane(); // 用于移动组件时的顶层面板
	private Set<Integer> thisSet = new StaticData().getCardIndexSet();
	private DealStackPanel dealStackPanel = new DealStackPanel(thisSet);
	private DealedStackPanel dealedStackPanel = new DealedStackPanel();
	public static GatherCardPanel[] gatherCardPanels = new GatherCardPanel[StaticData.getGathernum()];
	public static CardPanel cardPanel = null;
	public static SevenStackPanel[] sevenStackPanels = new SevenStackPanel[StaticData.getSevenstacknum()];
	public static SevenStackCardPanelAdapter[] sevenStackCardPanelAdapters = new SevenStackCardPanelAdapter[StaticData
			.getSevenstacknum()];
	private static CardStackNode[] top = new CardStackNode[StaticData.getSevenstacknum()];
	private static CardStackNode[] bottom = new CardStackNode[StaticData.getSevenstacknum()];

	public Index() {
		init();
	}

	private void init() {
		for (int i = 0; i < StaticData.getSevenstacknum(); i++) {
			sevenStackCardPanelAdapters[i] = new SevenStackCardPanelAdapter(i + 1, secondPanel);
		}
		componentInit();
		setNameInit();
		listenerInit();
	}

	private void setNameInit() {
		contentPanel.setName("contentPanel");
		dealStackPanel.setName("dealStackPanel");
		dealedStackPanel.setName("dealedStackPanel");
		for (int i = 1; i < gatherCardPanels.length + 1; i++)
			gatherCardPanels[i - 1].setName("gatherCardPanel" + i);
		for (int i = 1; i < sevenStackPanels.length + 1; i++)
			sevenStackPanels[i - 1].setName("sevenStackPanel" + i);
	}

	private void componentInit() {
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
		getContentPane().add(contentPanel);
	}

	private void listenerInit() {
		DealedStackAdapter dealedStackAdapter = new DealedStackAdapter(secondPanel);
		dealStackPanel.addMouseListener(new DealStackAdapter(dealStackPanel, dealedStackPanel));
		dealedStackPanel.addMouseListener(dealedStackAdapter);
		dealedStackPanel.addMouseMotionListener(dealedStackAdapter);

		GatherStackAdapter[] gatherStackAdapters = new GatherStackAdapter[StaticData.getGathernum()];
		for (int i = 0; i < StaticData.getGathernum(); i++) {
			gatherStackAdapters[i] = new GatherStackAdapter(secondPanel);
			gatherCardPanels[i].addMouseListener(gatherStackAdapters[i]);
			gatherCardPanels[i].addMouseMotionListener(gatherStackAdapters[i]);
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame jf = new Index();
		jf.setTitle("Solitaire-纸牌游戏");
		// 窗口退出行为
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 设置窗口大小不可变
		jf.setResizable(false);
		// 窗口大小
		jf.setSize(StaticData.FRAMESIZE[0], StaticData.FRAMESIZE[1]);
		jf.setName("frame");
		// 设置窗口打开居中
		jf.setLocationRelativeTo(null);
		// 展示窗口
		jf.setVisible(true);
	}
}
