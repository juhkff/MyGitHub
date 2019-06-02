package tools;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class NumberDocument extends PlainDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		// TODO Auto-generated method stub
		if (str == null)
			return;
		boolean isNumber = true;
		char[] s = str.toCharArray();
		// ¹ýÂË·ÇÊý×Ö
		for (int i = 0; i < s.length; i++) {
			if ((s[i] >= '0') && (s[i] <= '9'))
				continue;
			else {
				isNumber = false;
				break;
			}
		}
		if (isNumber)
			super.insertString(offs, str, a);
		else
			super.insertString(offs, "", a);
	}
}
