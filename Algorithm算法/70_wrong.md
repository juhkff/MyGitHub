# Algorithm 算法第70题:爬楼梯
**题目描述:**<br>
假设你正在爬楼梯。需要 n 步你才能到达楼顶。<br>
每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？<br>
**示例:**<br>
> 输入： 2 <br>
> 输出： 2 <br>
> 解释： 有两种方法可以爬到楼顶。<br>
> 1.  1 步 + 1 步 <br>
> 2.  2 步 <br>
<br>
<br>
**示例 2：**<br>
> 输入： 3 <br>
> 输出： 3 <br>
> 解释： 有三种方法可以爬到楼顶。 <br>
> 1.  1 步 + 1 步 + 1 步 <br>
> 2.  1 步 + 2 步 <br>
> 3.  2 步 + 1 步 <br>

````java
class Solution {
    public int climbStairs(int n) {
    	
    }
}
````

__________

### 理清思路:
本来想设i从0至楼梯数/2循环，每次规定i为2步的次数，由此得到1步的次数和总的步数，然后用排列组合Cnm=m(m-1)(m-2)(m-……)(m-n+1)/n(n-1)(n-2)(n-……)得到总的可能数。<br>
但是实际却发现在m(m-1)(m-2)(m-……)中如果初始的值稍大一些，计算过程值就很容易溢出.问题并未解决.
__________
#### 目前代码: <br>

````java
public int climbStairs(int n) {
		long result = 0;
		int oneTimes = 0;
		int allTimes;
		for (int i = 0; i < n / 2 + 1; i++) {
			// i是走两步的次数
			oneTimes = n - i * 2; // 走一步的次数
			allTimes = oneTimes + i; // 总共走的次数
			// 排列组合
			// result += this.factorial(allTimes) / (this.factorial(oneTimes) *
			// this.factorial(i));
			result += this.addSum(allTimes, oneTimes);//11565
		}
		return (int) result;
	}

	public long addSum(int m, int n) {
		long result = 0;
		long top = 1;
		long bottom = 1;
		if(m-n<n)
			n=m-n;
		// Cmn
		for (int i = 0; i < n; i++) {
			top *= (m - i);							//用排列组合的组合数的话，在这里数据(n)略大时很容易溢出.
			bottom *= (n - i);
		}
		result = top / bottom;
		return result;
	}
````
__________


