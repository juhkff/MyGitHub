package uiPaint;

import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import adapter.DealStackAdapter;
import adapter.DealedStackAdapter;
import adapter.GatherStackAdapter;
import element.CardStackNode;
import element.StaticData;
import javafx.scene.layout.Border;
import sun.rmi.runtime.Log;
import sun.tools.jar.resources.jar;
import uiDao.CardPanel;
import uiDao.DealStackPanel;
import uiDao.DealedStackPanel;
import uiDao.GatherCardPanel;

public class Index extends JFrame {
	private JPanel contentPanel = new JPanel();
	private JPanel secondPanel = new JPanel(); // 用于移动组件时的顶层面板
	private Set<Integer> thisSet = new StaticData().getAllCardIndexSet();
	private DealStackPanel dealStackPanel = new DealStackPanel(thisSet);
	private DealedStackPanel dealedStackPanel = new DealedStackPanel();
	public static GatherCardPanel[] gatherCardPanels = new GatherCardPanel[StaticData.getGathernum()];
	public static CardPanel cardPanel = null;

	public Index() {
		for (int i = 1; i < StaticData.getGathernum() + 1; i++)
			gatherCardPanels[i - 1] = new GatherCardPanel(i);
		init();
	}

	private void init() {
		setNameInit();
		componentInit();
		listenerInit();
	}

	private void setNameInit() {
		contentPanel.setName("contentPanel");
		dealStackPanel.setName("dealStackPanel");
		dealedStackPanel.setName("dealedStackPanel");
		for (int i = 1; i < gatherCardPanels.length + 1; i++)
			gatherCardPanels[i - 1].setName("gatherCardPanel" + i);
	}

	private void componentInit() {
		secondPanel.setSize(StaticData.FRAMESIZE[0], StaticData.FRAMESIZE[1]);
		secondPanel.setOpaque(false);

		contentPanel.setLayout(null);
		contentPanel.add(secondPanel);
		contentPanel.add(dealStackPanel);
		contentPanel.add(dealedStackPanel);

		for (int i = 0; i < StaticData.getGathernum(); i++)
			contentPanel.add(gatherCardPanels[i]);
		getContentPane().add(contentPanel);
	}

	private void listenerInit() {
		DealedStackAdapter dealedStackAdapter = new DealedStackAdapter(secondPanel);
		dealStackPanel.addMouseListener(new DealStackAdapter(dealStackPanel, dealedStackPanel));
		dealedStackPanel.addMouseListener(dealedStackAdapter);
		dealedStackPanel.addMouseMotionListener(dealedStackAdapter);

		GatherStackAdapter[] gatherStackAdapters = new GatherStackAdapter[StaticData.getGathernum()];
		for (int i = 0; i < StaticData.getGathernum(); i++) {
			gatherStackAdapters[i] = new GatherStackAdapter();
			gatherCardPanels[i].addMouseListener(gatherStackAdapters[i]);
			gatherCardPanels[i].addMouseMotionListener(gatherStackAdapters[i]);
		}
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
