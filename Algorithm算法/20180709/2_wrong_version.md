__写糟了的第二题__

````java
package algorithm;

import static org.junit.jupiter.api.Assumptions.assumingThat;

class ListNode {
	int val;
	ListNode next;

	ListNode(int x) {
		val = x;
	}
}

public class MD_2 {
	/* error2: */
	// 链表中针对每个ListNode都要自己构造下一个ListNode
	public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		int addOne = 0;
		ListNode resultNode = new ListNode(0); // 构造参数不能为null
		resultNode.next = new ListNode(0);
		ListNode currentNode = resultNode;
		while (l1.next != null && l2.next != null) {
			if (l1.val != 0 && l2.val != 0) { // int默认值为0
				currentNode.val = l1.val + l2.val + addOne;
				addOne = (currentNode.val / 10) == 1 ? 1 : 0; // currentNode.val而不是currentNode
				currentNode.val = currentNode.val % 10;
				l1 = l1.next;
				l2 = l2.next;
				currentNode = currentNode.next;
				currentNode.next = new ListNode(0);
			} else { // 不会以0开头？
				/*
				 * if(l1.val!=0) currentNode.val=l1.val; else if(l1.val!=0)
				 * currentNode.val=l2.val; else { currentNode=null; }
				 */
				currentNode.val = l1.val + l2.val + addOne;
				if (currentNode.val%10 != 0)
					currentNode.next = new ListNode(0);
				else {
					if (currentNode.val / 10 == 0)
						currentNode.next = new ListNode(0);
					else {
						currentNode.val = 0;
						addOne = 1;
						currentNode.next = new ListNode(0);
						currentNode.next.next = new ListNode(0);
					}
				}
				l1 = l1.next;
				l2 = l2.next;
				currentNode = currentNode.next;
			}
		}
		if (l1.val != 0 || l2.val != 0) {
			currentNode.val = l1.val + l2.val + addOne;
			addOne = (currentNode.val / 10) == 1 ? 1 : 0; // +.val
			currentNode.val = currentNode.val % 10;
			l1 = l1.next;
			l2 = l2.next;

			ListNode curNode = currentNode.next;

			if (l1 != null && l2 != null) {
				curNode.val = addOne + l1.val + l2.val;
				if (curNode.val / 10 == 1) {
					curNode.val = curNode.val % 10;
					curNode.next = new ListNode(1);
					curNode.next.next = null;
				} else {
					curNode.next = null;
				}
			} else if (l1 != null) {
				while (l1 != null) {
					if (addOne == 1) {
						curNode.val = 1 + l1.val;
						if (curNode.val % 10 != 0) {
							addOne = 0;
							curNode.next = new ListNode(0);
						} else { // currentNode.val进一位情况
							addOne = 1;
							curNode.next = new ListNode(0);
							curNode.next.next = new ListNode(0);
							curNode.val = 0;
						}
					} else {
						curNode.val = l1.val;
						curNode.next = new ListNode(0);
					}
					l1 = l1.next;
					currentNode = currentNode.next;
					curNode = currentNode.next;
				}
				if (addOne == 0)
					currentNode.next = null;
				else {
					currentNode.next = new ListNode(1);
					curNode.next = null;
				}
			} else if (l2 != null) {
				while (l2 != null) {
					if (addOne == 1) {
						curNode.val = 1 + l2.val;
						if (curNode.val % 10 != 0) {
							addOne = 0;
							curNode.next = new ListNode(0);
						} else { // currentNode.val进一位情况
							addOne = 1;
							curNode.next = new ListNode(0);
							curNode.next.next = new ListNode(0);
							curNode.val = 0;
						}
					} else {
						curNode.val = l2.val;
						curNode.next = new ListNode(0);
					}
					l2 = l2.next;
					currentNode = currentNode.next;
					curNode = currentNode.next;
				}
				if (addOne == 0)
					currentNode.next = null;
				else {
					currentNode.next = new ListNode(1);
					curNode.next = null;
				}
			} else {
				if (addOne == 0) {
					currentNode.next = null;
				} else {
					/*curNode=new ListNode(1);					//currentNode.next不会变
					curNode.next = null;*/
					currentNode.next=new ListNode(1);
					currentNode.next.next=null;
				}
			}
			return resultNode;
		} else {
			resultNode.next = null; /* error 1:[0][0]--->[0,0]/right answer:[0] solution:add this line */
			return resultNode;
		}
	}

	public static void main(String[] args) {
		ListNode l1 = new ListNode(8);
		ListNode l2 = new ListNode(2);


		ListNode l1_cur = l1;
		ListNode l2_cur = l2;

		l1_cur.next = new ListNode(3);
		l2_cur.next = new ListNode(6);

		l1_cur = l1_cur.next;
		l2_cur = l2_cur.next;
		
		l1_cur.next = new ListNode(2);
		l2_cur.next = new ListNode(7);

[M @3		l1_cur = l1_cur.next;
		l2_cur = l2_cur.next;

		l1_cur.next = new ListNode(7);
		l2_cur.next = new ListNode(2);

[M#@3		l1_cur = l1_cur.next;
		l2_cur = l2_cur.next;
		
		l1_cur.next = new ListNode(4);
		l2_cur.next = new ListNode(5);

		l1_cur = l1_cur.next;
		l2_cur = l2_cur.next;
		
		l1_cur.next = new ListNode(5);
		l2_cur.next = new ListNode(4);

		l1_cur = l1_cur.next;
		l2_cur = l2_cur.next;
		
		l1_cur.next = new ListNode(7);
		l2_cur.next = new ListNode(2);

		l1_cur = l1_cur.next;
		l2_cur = l2_cur.next;
		
		l1_cur.next = new ListNode(9);
		l2_cur.next = new ListNode(0);

		l1_cur = l1_cur.next;
		l2_cur = l2_cur.next;
		
		
		l1_cur.next = new ListNode(8);
		l2_cur.next = new ListNode(1);

		l1_cur = l1_cur.next;
		l2_cur = l2_cur.next;
		
		l1_cur.next = new ListNode(1);
		l2_cur.next = new ListNode(8);
		
		ListNode resultNode = addTwoNumbers(l1, l2);
		for (int i = 0; i < 3; i++) {
			System.out.println(resultNode.val);
			resultNode = resultNode.next;
		}
	}
}

```
> 体会:写之前一定要提前把易错的地方记下来，把思路理清楚.
