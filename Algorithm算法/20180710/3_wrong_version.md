# 第三题（未解决）
## 2、无重复字符的最长子串
**题目描述:**
给定一个字符串，找出不含有重复字符的**最长子串**的长度。<br>
**示例：**<br>
> 给定 "abcabcbb" ，没有重复字符的最长子串是 "abc" ，那么长度就是3。<br>
> 给定 "bbbbb" ，最长的子串就是 "b" ，长度是1。<br>
> 给定 "pwwkew" ，最长子串是 "wke" ，长度是3。请注意答案必须是一个**子串**，"pwke" 是 子序列  而不是子串。头。<br>

<br>
<br>

````java
class Solution {
    public int lengthOfLongestSubstring(String s) {

    }
}
````
__________
### 理清思路:
**1、**
记录字符串的长度。<br>
**2、**
从0开始循环，查找提出索引处的字符，从下一项开始查找是否存在制定字符，若有，记下该处坐标，进行判断，判断之后若该坐标等于字符串长度，则退出循环。判断内容：提出此段子字符串，从第二个字符开始查找是否有指定字符，若有则将子字符串范围缩小并继续循环查询，该子循环知道倒数第二个数（跳过）为止。大循环每次循环过后，记录下该次得到的长度，然后从查找到的索引的下一位开始 <br>
**3、**
字符串中有重复的字符时怎么办? <br>
**4、**
字符串中没有重复的字符时怎么办? <br>
**5、**
最长字符串在后面的情况，如dvdf时，怎么办? <br>

**代码:(wrong)**
````java
package algorithm;

public class MD_3 {
	public int lengthOfLongestSubstring(String s) {
		//pwwkew
		if(s.equals(""))
			return 0;
		int result=1;
		int tempresult=0;
		char ch;
		int index;
		int subindex;
		String subString;
		for(int i=0;i<s.length()-1;i++) {
			ch=s.charAt(i);
			//index=s.indexOf(ch, i+1);
			index=s.indexOf(ch);
			if(index!=-1) {
				subString=s.substring(i, index);
				for(int j=1;j<subString.length()-1;j++) {
					subindex=subString.indexOf(subString.charAt(j), j+1);
					if(subindex!=-1) {
						subString=subString.substring(j, subindex);
						tempresult+=j;					//abcd ftgghf
						j=0;
					}
				}
				tempresult+=subString.length();
				if(result<tempresult)
					result=tempresult;
				tempresult=0;
				i=index-1;									
				//error1:i=index--->i=index-1，出错案例：pwwkew.right answer:3,wrong answer:1 reason:i=index,i++导致跳过了第三个w
			}else {
				tempresult++;
			}
		}
		if(result<++tempresult)
			result=tempresult;
		return result;
    }
	
	public static void main(String[] args) {
		MD_3 a=new MD_3();
		String s="dvdf";
		int length=a.lengthOfLongestSubstring(s);
		System.out.println(length);
	}
}

````

> 考虑还是不周全，最后卡在了例"dvdf"上,wrong answer:2,right answer:3.
