package element;

import java.awt.Font;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class StaticData {
	protected static final Map<String, String[]> VALUEMAP = new HashMap<>(); // 对应值map
	protected static final Map<String, String> GATHERVALUEMAP = new HashMap<>(); // 对应值map
	public static final int[] FRAMESIZE = { /* 1200 */1600, 900 }; // 程序面板大小
	private static final int offset1 = 400;
	protected static final Font MAINFONT = new Font("宋体", 1, 170); // 主区域字体
	protected static final Font MAINFONT_10 = new Font("宋体", 1, 95); // 主区域特殊(10)大小字体
	protected static final Font MINIFONT = new Font("宋体", 1, 20); // 左上区域字体
	protected static final Font MINIFONT_10 = new Font("宋体", 1, 18); // 左上区域特殊(10)大小字体
	protected static final int[] MINILOCATION = { 0, 0, 20, 30 };
	protected static final int[] MAINLOCATION = { 20, 30, 110, 190 };
	protected static final int[] PANELSIZE = { 10, 10, 150, 250 };
	protected static final int[] CARDSIZE = { 0, 0, 150, 250 };
	protected static final int[] DEALEDLOCATION = { 200, 10, 150, 250 }; // 已翻开的牌放置的位置和大小
	protected static final int[] GATHERCARDLOCATION = { 500 + offset1, 10, 150, 250, 20 }; // 4个集牌堆第一个的位置和牌堆间相隔的距离(第5个int)
	// protected static final int[] MAINTEXTSIZE = { 60, 105, 30, 40 };
	// protected static final int[] MINITEXTSIZE = { 5, 5, 15, 25 };
	protected static final String DEFAULTBACKGROUNDURL = "D:\\study\\各种文件\\头像.jpg";
	protected static final String[] DEALS = { "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A0", "AJ", "AQ",
			"AK", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B0", "BJ", "BQ", "BK", "C1", "C2", "C3", "C4",
			"C5", "C6", "C7", "C8", "C9", "C0", "CJ", "CQ", "CK", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9",
			"D0", "DJ", "DQ", "DK" };
	protected static final int CARDNUM = 13 * 4;
	protected static final int DEALNUM = 13 * 4 - (1 + 2 + 3 + 4 + 5 + 6 + 7);
	protected static final int[] CARDSTACKNUM = { 1, 2, 3, 4, 5, 6, 7 };
	protected static final int GATHERNUM = 4;

	protected static final String FRAME = "frame";
	protected static final String INDEX = "contentPanel";
	protected static final String DEAL = "dealStackPanel";
	protected static final String DEALED = "dealedStackPanel";
	protected static final String CARD = "cardPanel";

	public static String getDeals(int index) {
		return DEALS[index];
	}

	public static int getCardnum() {
		return CARDNUM;
	}

	public static int getDealnum() {
		return DEALNUM;
	}

	public static int getCardstacknum(int index) {
		return CARDSTACKNUM[index];
	}

	public static HashSet<String> getAllCardSet() {
		HashSet<String> newSet = new HashSet<String>();
		for (String each : DEALS) {
			newSet.add(each);
		}
		return newSet;
	}

	public static HashSet<Integer> getAllCardIndexSet() {
		HashSet<Integer> newSet = new HashSet<Integer>();
		for (int i = 0; i < CARDNUM; i++)
			newSet.add(i);
		return newSet;
	}

	public static Map<String, String[]> getValuemap() {
		return VALUEMAP;
	}

	public static String[] getValueMapValue(String key) {
		return VALUEMAP.get(key);
	}

	public static Map<String, String> getGatherValuemap() {
		return GATHERVALUEMAP;
	}

	public static String getGathervaluemap(String key) {
		return GATHERVALUEMAP.get(key);
	}

	public static Font getMainTextFont() {
		return MAINFONT;
	}

	public static Font getMiniTextFont() {
		return MINIFONT;
	}

	public static Font getMainTextFont_10() {
		return MAINFONT_10;
	}

	public static Font getMiniTextFont_10() {
		return MINIFONT_10;
	}

	public static int getMinilocation(int index) {
		return MINILOCATION[index];
	}

	public static int getMainlocation(int index) {
		return MAINLOCATION[index];
	}

	public static int getCardsize(int index) {
		return CARDSIZE[index];
	}

	public static int getPanelsize(int index) {
		return PANELSIZE[index];
	}

	public static int getDealedlocation(int index) {
		return DEALEDLOCATION[index];
	}

	public static int getGathercardlocation(int index) {
		return GATHERCARDLOCATION[index];
	}

	public static String getDefaultbackgroundurl() {
		return DEFAULTBACKGROUNDURL;
	}

	public static int getGathernum() {
		return GATHERNUM;
	}

	public StaticData() {
		// TODO Auto-generated constructor stub
		this.VALUEMAP.put("A1", new String[] { "C2", "D2" });
		this.VALUEMAP.put("A2", new String[] { "C3", "D3" });
		this.VALUEMAP.put("A3", new String[] { "C4", "D4" });
		this.VALUEMAP.put("A4", new String[] { "C5", "D5" });
		this.VALUEMAP.put("A5", new String[] { "C6", "D6" });
		this.VALUEMAP.put("A6", new String[] { "C7", "D7" });
		this.VALUEMAP.put("A7", new String[] { "C8", "D8" });
		this.VALUEMAP.put("A8", new String[] { "C9", "D9" });
		this.VALUEMAP.put("A9", new String[] { "C0", "D0" });
		this.VALUEMAP.put("A0", new String[] { "CJ", "DJ" });
		this.VALUEMAP.put("AJ", new String[] { "CQ", "DQ" });
		this.VALUEMAP.put("AQ", new String[] { "CK", "DK" });
		this.VALUEMAP.put("AK", new String[] { null });

		this.VALUEMAP.put("B1", new String[] { "C2", "D2" });
		this.VALUEMAP.put("B2", new String[] { "C3", "D3" });
		this.VALUEMAP.put("B3", new String[] { "C4", "D4" });
		this.VALUEMAP.put("B4", new String[] { "C5", "D5" });
		this.VALUEMAP.put("B5", new String[] { "C6", "D6" });
		this.VALUEMAP.put("B6", new String[] { "C7", "D7" });
		this.VALUEMAP.put("B7", new String[] { "C8", "D8" });
		this.VALUEMAP.put("B8", new String[] { "C9", "D9" });
		this.VALUEMAP.put("B9", new String[] { "C0", "D0" });
		this.VALUEMAP.put("B0", new String[] { "CJ", "DJ" });
		this.VALUEMAP.put("BJ", new String[] { "CQ", "DQ" });
		this.VALUEMAP.put("BQ", new String[] { "CK", "DK" });
		this.VALUEMAP.put("BK", new String[] { null });

		this.VALUEMAP.put("C1", new String[] { "A2", "B2" });
		this.VALUEMAP.put("C2", new String[] { "A3", "B3" });
		this.VALUEMAP.put("C3", new String[] { "A4", "B4" });
		this.VALUEMAP.put("C4", new String[] { "A5", "B5" });
		this.VALUEMAP.put("C5", new String[] { "A6", "B6" });
		this.VALUEMAP.put("C6", new String[] { "A7", "B7" });
		this.VALUEMAP.put("C7", new String[] { "A8", "B8" });
		this.VALUEMAP.put("C8", new String[] { "A9", "B9" });
		this.VALUEMAP.put("C9", new String[] { "A0", "B0" });
		this.VALUEMAP.put("C0", new String[] { "AJ", "BJ" });
		this.VALUEMAP.put("CJ", new String[] { "AQ", "BQ" });
		this.VALUEMAP.put("CQ", new String[] { "AK", "BK" });
		this.VALUEMAP.put("CK", new String[] { null });

		this.VALUEMAP.put("D1", new String[] { "A2", "B2" });
		this.VALUEMAP.put("D2", new String[] { "A3", "B3" });
		this.VALUEMAP.put("D3", new String[] { "A4", "B4" });
		this.VALUEMAP.put("D4", new String[] { "A5", "B5" });
		this.VALUEMAP.put("D5", new String[] { "A6", "B6" });
		this.VALUEMAP.put("D6", new String[] { "A7", "B7" });
		this.VALUEMAP.put("D7", new String[] { "A8", "B8" });
		this.VALUEMAP.put("D8", new String[] { "A9", "B9" });
		this.VALUEMAP.put("D9", new String[] { "A0", "B0" });
		this.VALUEMAP.put("D0", new String[] { "AJ", "BJ" });
		this.VALUEMAP.put("DJ", new String[] { "AQ", "BQ" });
		this.VALUEMAP.put("DQ", new String[] { "AK", "BK" });
		this.VALUEMAP.put("DK", new String[] { null });

		this.GATHERVALUEMAP.put("A1", "A2");
		this.GATHERVALUEMAP.put("A2", "A3");
		this.GATHERVALUEMAP.put("A3", "A4");
		this.GATHERVALUEMAP.put("A4", "A5");
		this.GATHERVALUEMAP.put("A5", "A6");
		this.GATHERVALUEMAP.put("A6", "A7");
		this.GATHERVALUEMAP.put("A7", "A8");
		this.GATHERVALUEMAP.put("A8", "A9");
		this.GATHERVALUEMAP.put("A9", "A0");
		this.GATHERVALUEMAP.put("A0", "AJ");
		this.GATHERVALUEMAP.put("AJ", "AQ");
		this.GATHERVALUEMAP.put("AQ", "AK");
		this.GATHERVALUEMAP.put("AK", null);

		this.GATHERVALUEMAP.put("B1", "B2");
		this.GATHERVALUEMAP.put("B2", "B3");
		this.GATHERVALUEMAP.put("B3", "B4");
		this.GATHERVALUEMAP.put("B4", "B5");
		this.GATHERVALUEMAP.put("B5", "B6");
		this.GATHERVALUEMAP.put("B6", "B7");
		this.GATHERVALUEMAP.put("B7", "B8");
		this.GATHERVALUEMAP.put("B8", "B9");
		this.GATHERVALUEMAP.put("B9", "B0");
		this.GATHERVALUEMAP.put("B0", "BJ");
		this.GATHERVALUEMAP.put("BJ", "BQ");
		this.GATHERVALUEMAP.put("BQ", "BK");
		this.GATHERVALUEMAP.put("BK", null);

		this.GATHERVALUEMAP.put("C1", "C2");
		this.GATHERVALUEMAP.put("C2", "C3");
		this.GATHERVALUEMAP.put("C3", "C4");
		this.GATHERVALUEMAP.put("C4", "C5");
		this.GATHERVALUEMAP.put("C5", "C6");
		this.GATHERVALUEMAP.put("C6", "C7");
		this.GATHERVALUEMAP.put("C7", "C8");
		this.GATHERVALUEMAP.put("C8", "C9");
		this.GATHERVALUEMAP.put("C9", "C0");
		this.GATHERVALUEMAP.put("C0", "CJ");
		this.GATHERVALUEMAP.put("CJ", "CQ");
		this.GATHERVALUEMAP.put("CQ", "CK");
		this.GATHERVALUEMAP.put("CK", null);

		this.GATHERVALUEMAP.put("D1", "D2");
		this.GATHERVALUEMAP.put("D2", "D3");
		this.GATHERVALUEMAP.put("D3", "D4");
		this.GATHERVALUEMAP.put("D4", "D5");
		this.GATHERVALUEMAP.put("D5", "D6");
		this.GATHERVALUEMAP.put("D6", "D7");
		this.GATHERVALUEMAP.put("D7", "D8");
		this.GATHERVALUEMAP.put("D8", "D9");
		this.GATHERVALUEMAP.put("D9", "D0");
		this.GATHERVALUEMAP.put("D0", "DJ");
		this.GATHERVALUEMAP.put("DJ", "DQ");
		this.GATHERVALUEMAP.put("DQ", "DK");
		this.GATHERVALUEMAP.put("DK", null);

	}
}
