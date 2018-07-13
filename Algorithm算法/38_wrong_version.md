# Algorithm算法第38题的错误代码

**错误原因:**
审题不细,例子没看完,理解的不到位.

````java
package algorithm;

public class MD_38_wrong {
	public static void main(String[] args) {
		MD_38_wrong md_38 = new MD_38_wrong();
		int test = 5;
		System.out.println(md_38.countAndSay(test));
	}

	public String countAndSay(int n) {
		if (n != 1) {
			String result = "1";
			for (int i = 1; i < n; i++) {
				result = method(result);
			}
			return result;
		} else {
			return "1";
		}
	}

	private String method(String num) {
		String result = "";
		char[] numbers = num.toCharArray();
		int curLength = 0;
		int length = numbers.length;
		int[] numTimes = new int[10]; // 第一位废除
		for (int j = 0; j < length; j++) {
			switch (numbers[j] - '0') {
			case 1:
				numTimes[1]++;
				break;
			case 2:
				numTimes[2]++;
				break;
			case 3:
				numTimes[3]++;
				break;
			case 4:
				numTimes[4]++;
				break;
			case 5:
				numTimes[5]++;
				break;
			case 6:
				numTimes[6]++;
				break;
			case 7:
				numTimes[7]++;
				break;
			case 8:
				numTimes[8]++;
				break;
			case 9:
				numTimes[9]++;
				break;
			}
		}
		for (int i = 9; i > 0; i--) { // error2:for (int i = 1; i < numTimes.length; i++) {
			if (numTimes[i] != 0) {
				result += numTimes[i] + "" + i + ""; // error1:result+=numTimes[i]+i;
				curLength += numTimes[i];
			}
			if (curLength == length)
				break;
		}
		return result;
	}
}

````

**输入5时,输出的结果不是111221(1个1,1个2,2个1)而是1231(1个2,3个1).** <br>
## 我是按最后统一计数来写的程序.没理解题意.
