package element;

import java.awt.Font;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public final class StaticData {
	private static int CARDWIDTH = 105;
	private static int CARDHEIGHT = 150;
	private static final int FRAMEMINWIDTH = 900;
	private static final int FRAMEMINHEIGHT = 675;
	private static int FRAMEWIDTH = 1200;
	private static int FRAMEHEIGHT = 900;
	private final static int EDITWIDTH = 760;
	private final static int EDITHEIGHT = 500;
	private static int PANELPADDINGLEFT = 10;
	private static int PANELPADDINGTOP = 10;
	protected static int BORDERWIDTH = 5;
	protected static int SEVENSTACKNUM = 7;
	private static int SEVENPADDINGTOP = 40; // 下部牌堆距离上部发牌堆的距离
	protected static int GATHERCARDLENGTH = 20; // 4个集牌堆彼此相隔的距离
	protected static int GATHERCARDPADDINGRIGHT = 40; // 集牌堆距离主面板右侧的距离
	private static boolean isCARDSIZEChanged = false;
	private static boolean isCARDBGChanged = false;
	protected static final Map<String, String[]> VALUEMAP = new HashMap<>(); // 对应值map
	protected static final Map<String, String> GATHERVALUEMAP = new HashMap<>(); // 对应值map
	protected static HashSet<Integer> CARDSET = new HashSet<Integer>();
	public static int[] FRAMESIZE = { FRAMEWIDTH, FRAMEHEIGHT }; // 程序面板大小
	protected static Font MAINFONT = new Font("宋体", 1, 170); // 主区域字体
	protected static Font MAINFONT_10 = new Font("宋体", 1, 95); // 主区域特殊(10)大小字体
	protected static Font MINIFONT = new Font("宋体", 1, 20); // 左上区域字体
	protected static Font MINIFONT_10 = new Font("宋体", 1, 18); // 左上区域特殊(10)大小字体
	protected static Font TYPEFONT = new Font("宋体", 1, 20); // 卡牌图案字体的大小
	protected static final Font EDITFONT = new Font("宋体", Font.BOLD, 20);
	protected static final Font EDITFONT2 = new Font("宋体", Font.BOLD, 15);
	protected static int[] MINILOCATION = { 0, 0, 20, 30 };
	protected static int[] TYPELOCATION = { 20, 0, 50, 30 };
	protected static int[] MAINLOCATION = { 20, 30, 110, 190 };
	protected static int[] PANELSIZE = { PANELPADDINGLEFT, PANELPADDINGTOP, CARDWIDTH, CARDHEIGHT };
	protected static int[] CARDSIZE = { 0, 0, CARDWIDTH, CARDHEIGHT };
	protected static int[] DEALEDLOCATION = { PANELPADDINGLEFT + CARDWIDTH + PANELPADDINGLEFT * 3, PANELPADDINGTOP,
			CARDWIDTH, CARDHEIGHT }; // 已翻开的牌放置的位置和大小

	protected static int[] GATHERCARDLOCATION = {
			FRAMEWIDTH - 4 * CARDWIDTH - 3 * GATHERCARDLENGTH - GATHERCARDPADDINGRIGHT, PANELPADDINGTOP, CARDWIDTH,
			CARDHEIGHT, GATHERCARDLENGTH }; // 4个集牌堆第一个的位置和牌堆间相隔的距离(第5个int)

	protected static int[] SEVENSTACKLOCATION = { (FRAMEWIDTH - SEVENSTACKNUM * CARDWIDTH) / (SEVENSTACKNUM + 1),
			PANELPADDINGTOP + CARDHEIGHT + SEVENPADDINGTOP }; // 下部牌堆之间的宽度和距离顶部的距离
	protected static String DEFAULTBACKGROUNDURL = "CARD\\Back.jpg";
	protected static String BACKGROUNDURL = "";
	protected static final String[] DEALS = { "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A0", "AJ", "AQ",
			"AK", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B0", "BJ", "BQ", "BK", "C1", "C2", "C3", "C4",
			"C5", "C6", "C7", "C8", "C9", "C0", "CJ", "CQ", "CK", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9",
			"D0", "DJ", "DQ", "DK" };
	protected static final int CARDNUM = 13 * 4;
	protected static int DEALNUM;
	protected static final int[] CARDSTACKNUM = { 1, 2, 3, 4, 5, 6, 7 };
	protected static final int GATHERNUM = 4;

	protected static final String FRAME = "frame";
	protected static final String INDEX = "contentPanel";
	protected static final String DEAL = "dealStackPanel";
	protected static final String DEALED = "dealedStackPanel";
	protected static final String CARD = "cardPanel";

	public static int getBORDERWIDTH() {
		return BORDERWIDTH;
	}

	public static void setMiniLocation3(int newValue) {
		MINILOCATION[3] = newValue;
	}

	public static int getEditwidth() {
		return EDITWIDTH;
	}

	public static int getEditheight() {
		return EDITHEIGHT;
	}

	public static int getPANELPADDINGLEFT() {
		return PANELPADDINGLEFT;
	}

	public static int getPANELPADDINGTOP() {
		return PANELPADDINGTOP;
	}

	public static int getSEVENSTACKNUM() {
		return SEVENSTACKNUM;
	}

	public static int getSEVENPADDINGTOP() {
		return SEVENPADDINGTOP;
	}

	public static int getGATHERCARDLENGTH() {
		return GATHERCARDLENGTH;
	}

	public static int getGATHERCARDPADDINGRIGHT() {
		return GATHERCARDPADDINGRIGHT;
	}

	public static Map<String, String> getGathervaluemap() {
		return GATHERVALUEMAP;
	}

	public static HashSet<Integer> getCARDSET() {
		return CARDSET;
	}

	public static int[] getFRAMESIZE() {
		return FRAMESIZE;
	}

	public static Font getMAINFONT() {
		return MAINFONT;
	}

	public static Font getMAINFONT_10() {
		return MAINFONT_10;
	}

	public static Font getMINIFONT() {
		return MINIFONT;
	}

	public static Font getMINIFONT_10() {
		return MINIFONT_10;
	}

	public static Font getTYPEFONT() {
		return TYPEFONT;
	}

	public static Font getEditfont() {
		return EDITFONT;
	}

	public static Font getEditfont2() {
		return EDITFONT2;
	}

	public static int[] getMINILOCATION() {
		return MINILOCATION;
	}

	public static int[] getTYPELOCATION() {
		return TYPELOCATION;
	}

	public static int[] getMAINLOCATION() {
		return MAINLOCATION;
	}

	public static int[] getPANELSIZE() {
		return PANELSIZE;
	}

	public static int[] getCARDSIZE() {
		return CARDSIZE;
	}

	public static int[] getDEALEDLOCATION() {
		return DEALEDLOCATION;
	}

	public static int[] getGATHERCARDLOCATION() {
		return GATHERCARDLOCATION;
	}

	public static int[] getSEVENSTACKLOCATION() {
		return SEVENSTACKLOCATION;
	}

	public static String getDEFAULTBACKGROUNDURL() {
		return DEFAULTBACKGROUNDURL;
	}

	public static String[] getDeals() {
		return DEALS;
	}

	public static int getDEALNUM() {
		return DEALNUM;
	}

	public static int[] getCardstacknum() {
		return CARDSTACKNUM;
	}

	public static String getFrame() {
		return FRAME;
	}

	public static String getIndex() {
		return INDEX;
	}

	public static String getDeal() {
		return DEAL;
	}

	public static String getDealed() {
		return DEALED;
	}

	public static String getCard() {
		return CARD;
	}

	public static int getCARDWIDTH() {
		return CARDWIDTH;
	}

	public static void setCARDWIDTH(int cARDWIDTH) {
		CARDWIDTH = cARDWIDTH;
	}

	public static int getCARDHEIGHT() {
		return CARDHEIGHT;
	}

	public static void setCARDHEIGHT(int cARDHEIGHT) {
		CARDHEIGHT = cARDHEIGHT;
	}

	public static void setFRAMEWIDTH(int fRAMEWIDTH) {
		FRAMEWIDTH = fRAMEWIDTH;
	}

	public static void setFRAMEHEIGHT(int fRAMEHEIGHT) {
		FRAMEHEIGHT = fRAMEHEIGHT;
	}

	public static int getFrameminwidth() {
		return FRAMEMINWIDTH;
	}

	public static int getFrameminheight() {
		return FRAMEMINHEIGHT;
	}

	public static int getFRAMEWIDTH() {
		return FRAMEWIDTH;
	}

	public static int getFRAMEHEIGHT() {
		return FRAMEHEIGHT;
	}

	public static int getEDITWIDTH() {
		return EDITWIDTH;
	}

	public static int getEDITHEIGHT() {
		return EDITHEIGHT;
	}

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

	public static HashSet<Integer> getCardIndexSet() {
		return StaticData.CARDSET;
	}

	public static void setNewCardIndexSet() {
		StaticData.CARDSET.clear();
		for (int i = 0; i < StaticData.CARDNUM; i++)
			StaticData.CARDSET.add(i);
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

	public static Font getTypefont() {
		return TYPEFONT;
	}

	public static Font getEDITFONT() {
		return EDITFONT;
	}

	public static Font getEDITFONT2() {
		return EDITFONT2;
	}

	public static int getMinilocation(int index) {
		return MINILOCATION[index];
	}

	public static int getTypelocation(int index) {
		return TYPELOCATION[index];
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

	public static int getSevenstacknum() {
		return SEVENSTACKNUM;
	}

	public static int getSevenstacklocation(int index) {
		return SEVENSTACKLOCATION[index];
	}

	public static String getDefaultbackgroundurl() {
		return DEFAULTBACKGROUNDURL;
	}

	public static int getGathernum() {
		return GATHERNUM;
	}

	public static boolean isCARDSIZEChanged() {
		return isCARDSIZEChanged;
	}

	public static void setCARDSIZEChanged(boolean isCARDSIZEChanged) {
		StaticData.isCARDSIZEChanged = isCARDSIZEChanged;
	}

	public static boolean isCARDBGChanged() {
		return isCARDBGChanged;
	}

	public static void setCARDBGChanged(boolean isCARDBGChanged) {
		StaticData.isCARDBGChanged = isCARDBGChanged;
	}

	public static String getBACKGROUNDURL() {
		return BACKGROUNDURL;
	}

	public static void setBACKGROUNDURL(String bACKGROUNDURL) {
		BACKGROUNDURL = bACKGROUNDURL;
	}

	/**
	 * 引用数据重新初始化
	 */
	public static void reDefine() {
		re_FRAMESIZE();
		re_CARDSIZE();
		re_PANELSIZE();
		re_DEALEDLOCATION();
		re_GATHERCARDLOCATION();
		re_SEVENSTACKLOCATION();
		// 40
	}

	private static void re_FRAMESIZE() {
		FRAMESIZE[0] = FRAMEWIDTH;
		FRAMESIZE[1] = FRAMEHEIGHT;
	}

	private static void re_CARDSIZE() {
		CARDSIZE[2] = CARDWIDTH;
		CARDSIZE[3] = CARDHEIGHT;
	}

	private static void re_PANELSIZE() {
		PANELSIZE[0] = PANELPADDINGLEFT;
		PANELSIZE[1] = PANELPADDINGTOP;
		PANELSIZE[2] = CARDWIDTH;
		PANELSIZE[3] = CARDHEIGHT;
	}

	private static void re_DEALEDLOCATION() {
		DEALEDLOCATION[0] = PANELPADDINGLEFT + CARDWIDTH + PANELPADDINGLEFT * 3;
		DEALEDLOCATION[1] = PANELPADDINGTOP;
		DEALEDLOCATION[2] = CARDWIDTH;
		DEALEDLOCATION[3] = CARDHEIGHT;
	}

	private static void re_GATHERCARDLOCATION() {
		GATHERCARDLOCATION[0] = FRAMEWIDTH - 4 * CARDWIDTH - 3 * GATHERCARDLENGTH - GATHERCARDPADDINGRIGHT;
		GATHERCARDLOCATION[1] = PANELPADDINGTOP;
		GATHERCARDLOCATION[2] = CARDWIDTH;
		GATHERCARDLOCATION[3] = CARDHEIGHT;
		GATHERCARDLOCATION[4] = GATHERCARDLENGTH;
	}

	private static void re_SEVENSTACKLOCATION() {
		SEVENSTACKLOCATION[0] = (FRAMEWIDTH - SEVENSTACKNUM * CARDWIDTH) / (SEVENSTACKNUM + 1);
		SEVENSTACKLOCATION[1] = PANELPADDINGTOP + CARDHEIGHT + SEVENPADDINGTOP;
	}

	public StaticData() {
		// TODO Auto-generated constructor stub
		for (int i = 0; i < StaticData.CARDNUM; i++)
			StaticData.CARDSET.add(i);
		int sevenAllNum = 0;
		for (int i = 0; i < StaticData.SEVENSTACKNUM; i++)
			sevenAllNum += i + 1;
		DEALNUM = 13 * 4 - sevenAllNum;
		StaticData.VALUEMAP.put("A1", new String[] { "C2", "D2" });
		StaticData.VALUEMAP.put("A2", new String[] { "C3", "D3" });
		StaticData.VALUEMAP.put("A3", new String[] { "C4", "D4" });
		StaticData.VALUEMAP.put("A4", new String[] { "C5", "D5" });
		StaticData.VALUEMAP.put("A5", new String[] { "C6", "D6" });
		StaticData.VALUEMAP.put("A6", new String[] { "C7", "D7" });
		StaticData.VALUEMAP.put("A7", new String[] { "C8", "D8" });
		StaticData.VALUEMAP.put("A8", new String[] { "C9", "D9" });
		StaticData.VALUEMAP.put("A9", new String[] { "C0", "D0" });
		StaticData.VALUEMAP.put("A0", new String[] { "CJ", "DJ" });
		StaticData.VALUEMAP.put("AJ", new String[] { "CQ", "DQ" });
		StaticData.VALUEMAP.put("AQ", new String[] { "CK", "DK" });
		StaticData.VALUEMAP.put("AK", null);

		StaticData.VALUEMAP.put("B1", new String[] { "C2", "D2" });
		StaticData.VALUEMAP.put("B2", new String[] { "C3", "D3" });
		StaticData.VALUEMAP.put("B3", new String[] { "C4", "D4" });
		StaticData.VALUEMAP.put("B4", new String[] { "C5", "D5" });
		StaticData.VALUEMAP.put("B5", new String[] { "C6", "D6" });
		StaticData.VALUEMAP.put("B6", new String[] { "C7", "D7" });
		StaticData.VALUEMAP.put("B7", new String[] { "C8", "D8" });
		StaticData.VALUEMAP.put("B8", new String[] { "C9", "D9" });
		StaticData.VALUEMAP.put("B9", new String[] { "C0", "D0" });
		StaticData.VALUEMAP.put("B0", new String[] { "CJ", "DJ" });
		StaticData.VALUEMAP.put("BJ", new String[] { "CQ", "DQ" });
		StaticData.VALUEMAP.put("BQ", new String[] { "CK", "DK" });
		StaticData.VALUEMAP.put("BK", null);

		StaticData.VALUEMAP.put("C1", new String[] { "A2", "B2" });
		StaticData.VALUEMAP.put("C2", new String[] { "A3", "B3" });
		StaticData.VALUEMAP.put("C3", new String[] { "A4", "B4" });
		StaticData.VALUEMAP.put("C4", new String[] { "A5", "B5" });
		StaticData.VALUEMAP.put("C5", new String[] { "A6", "B6" });
		StaticData.VALUEMAP.put("C6", new String[] { "A7", "B7" });
		StaticData.VALUEMAP.put("C7", new String[] { "A8", "B8" });
		StaticData.VALUEMAP.put("C8", new String[] { "A9", "B9" });
		StaticData.VALUEMAP.put("C9", new String[] { "A0", "B0" });
		StaticData.VALUEMAP.put("C0", new String[] { "AJ", "BJ" });
		StaticData.VALUEMAP.put("CJ", new String[] { "AQ", "BQ" });
		StaticData.VALUEMAP.put("CQ", new String[] { "AK", "BK" });
		StaticData.VALUEMAP.put("CK", null);

		StaticData.VALUEMAP.put("D1", new String[] { "A2", "B2" });
		StaticData.VALUEMAP.put("D2", new String[] { "A3", "B3" });
		StaticData.VALUEMAP.put("D3", new String[] { "A4", "B4" });
		StaticData.VALUEMAP.put("D4", new String[] { "A5", "B5" });
		StaticData.VALUEMAP.put("D5", new String[] { "A6", "B6" });
		StaticData.VALUEMAP.put("D6", new String[] { "A7", "B7" });
		StaticData.VALUEMAP.put("D7", new String[] { "A8", "B8" });
		StaticData.VALUEMAP.put("D8", new String[] { "A9", "B9" });
		StaticData.VALUEMAP.put("D9", new String[] { "A0", "B0" });
		StaticData.VALUEMAP.put("D0", new String[] { "AJ", "BJ" });
		StaticData.VALUEMAP.put("DJ", new String[] { "AQ", "BQ" });
		StaticData.VALUEMAP.put("DQ", new String[] { "AK", "BK" });
		StaticData.VALUEMAP.put("DK", null);

		StaticData.GATHERVALUEMAP.put("A1", "A2");
		StaticData.GATHERVALUEMAP.put("A2", "A3");
		StaticData.GATHERVALUEMAP.put("A3", "A4");
		StaticData.GATHERVALUEMAP.put("A4", "A5");
		StaticData.GATHERVALUEMAP.put("A5", "A6");
		StaticData.GATHERVALUEMAP.put("A6", "A7");
		StaticData.GATHERVALUEMAP.put("A7", "A8");
		StaticData.GATHERVALUEMAP.put("A8", "A9");
		StaticData.GATHERVALUEMAP.put("A9", "A0");
		StaticData.GATHERVALUEMAP.put("A0", "AJ");
		StaticData.GATHERVALUEMAP.put("AJ", "AQ");
		StaticData.GATHERVALUEMAP.put("AQ", "AK");
		StaticData.GATHERVALUEMAP.put("AK", null);

		StaticData.GATHERVALUEMAP.put("B1", "B2");
		StaticData.GATHERVALUEMAP.put("B2", "B3");
		StaticData.GATHERVALUEMAP.put("B3", "B4");
		StaticData.GATHERVALUEMAP.put("B4", "B5");
		StaticData.GATHERVALUEMAP.put("B5", "B6");
		StaticData.GATHERVALUEMAP.put("B6", "B7");
		StaticData.GATHERVALUEMAP.put("B7", "B8");
		StaticData.GATHERVALUEMAP.put("B8", "B9");
		StaticData.GATHERVALUEMAP.put("B9", "B0");
		StaticData.GATHERVALUEMAP.put("B0", "BJ");
		StaticData.GATHERVALUEMAP.put("BJ", "BQ");
		StaticData.GATHERVALUEMAP.put("BQ", "BK");
		StaticData.GATHERVALUEMAP.put("BK", null);

		StaticData.GATHERVALUEMAP.put("C1", "C2");
		StaticData.GATHERVALUEMAP.put("C2", "C3");
		StaticData.GATHERVALUEMAP.put("C3", "C4");
		StaticData.GATHERVALUEMAP.put("C4", "C5");
		StaticData.GATHERVALUEMAP.put("C5", "C6");
		StaticData.GATHERVALUEMAP.put("C6", "C7");
		StaticData.GATHERVALUEMAP.put("C7", "C8");
		StaticData.GATHERVALUEMAP.put("C8", "C9");
		StaticData.GATHERVALUEMAP.put("C9", "C0");
		StaticData.GATHERVALUEMAP.put("C0", "CJ");
		StaticData.GATHERVALUEMAP.put("CJ", "CQ");
		StaticData.GATHERVALUEMAP.put("CQ", "CK");
		StaticData.GATHERVALUEMAP.put("CK", null);

		StaticData.GATHERVALUEMAP.put("D1", "D2");
		StaticData.GATHERVALUEMAP.put("D2", "D3");
		StaticData.GATHERVALUEMAP.put("D3", "D4");
		StaticData.GATHERVALUEMAP.put("D4", "D5");
		StaticData.GATHERVALUEMAP.put("D5", "D6");
		StaticData.GATHERVALUEMAP.put("D6", "D7");
		StaticData.GATHERVALUEMAP.put("D7", "D8");
		StaticData.GATHERVALUEMAP.put("D8", "D9");
		StaticData.GATHERVALUEMAP.put("D9", "D0");
		StaticData.GATHERVALUEMAP.put("D0", "DJ");
		StaticData.GATHERVALUEMAP.put("DJ", "DQ");
		StaticData.GATHERVALUEMAP.put("DQ", "DK");
		StaticData.GATHERVALUEMAP.put("DK", null);
	}
}
