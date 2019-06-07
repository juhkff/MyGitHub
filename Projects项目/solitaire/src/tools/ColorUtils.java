package tools;

import java.awt.Color;

public class ColorUtils {
	/**
	 * <strong>��ɫRGBתʮ������</strong><br>
	 * <ul>
	 * <li>��ɫRGB���Ϸ����򷵻�null</li>
	 * </ul>
	 * <br>
	 * 
	 * @author Aaron.ffp
	 * @version V1.0.0: autotest-base cn.ffp.autotest.base.util ColorUtils.java
	 *          rgb2Hex, 2016-03-15 23:49:33.224 Exp $
	 * 
	 * @param rgb
	 *            ��ɫRGB
	 * @return �Ϸ�ʱ������ɫʮ������
	 */

	public static String rgb2Hex(Color color) {
		StringBuilder sb = new StringBuilder();
		String r = Integer.toHexString(color.getRed()).toUpperCase();
		String g = Integer.toHexString(color.getGreen()).toUpperCase();
		String b = Integer.toHexString(color.getBlue()).toUpperCase();

		sb.append("#");
		sb.append(r.length() == 1 ? "0" + r : r);
		sb.append(g.length() == 1 ? "0" + g : g);
		sb.append(b.length() == 1 ? "0" + b : b);

		return sb.toString();
	}

}
