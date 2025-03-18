# 세그먼트 트리 (Segment Tree)

세그먼트 트리는 **배열의 특정 구간(Interval)에 대한 연산을 빠르게 수행**하기 위한 자료구조이다.
일반적으로 **구간 합, 최소/최대값, 곱, 최대공약수(GCD) 등의 구간 연산**을 처리할 때 사용된다.

## 🔹 구조
세그먼트 트리는 **완전 이진 트리 형태**로 구성되며, 루트 노드는 전체 배열을 나타내고, 각 노드는 특정 구간을 저장한다.

- 루트 노드: 전체 배열 \([0, N-1]\)
- 왼쪽 자식: 왼쪽 절반 \([0, N/2 -1]\)
- 오른쪽 자식: 오른쪽 절반 \([N/2, N-1]\)
- 재귀적으로 하위 구간을 나누면서 트리를 구성

트리의 높이는 **\( O(\log N) \)**이며, 노드 개수는 **약 \( 2N \)**개이다.

## 🔹 시간 복잡도
- **구성(Building)**: \( O(N) \)
- **업데이트(Update)**: \( O(\log N) \)
- **쿼리(Query, Range Query)**: \( O(\log N) \)

## 🔹 세그먼트 트리 vs 다른 방법
| 방법 | 업데이트 시간 | 쿼리 시간 |
|------|------------|------------|
| 단순 배열 | \( O(1) \) | \( O(N) \) |
| 누적합(Prefix Sum) | \( O(1) \) | \( O(1) \) (구간 합) |
| 펜윅 트리(Fenwick Tree) | \( O(\log N) \) | \( O(\log N) \) |
| **세그먼트 트리** | \( O(\log N) \) | \( O(\log N) \) |

## 🔹 코드 예제 (Python)
```java
import java.util.*;
import java.lang.*;
import java.io.*;

class Main
{
  static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
  static StringTokenizer st;
  static StringBuilder sb = new StringBuilder();
  
  static int N, M, K;
  static long tree[], nums[], targetStart, targetEnd;
  
  static void input() throws Exception {
    st = new StringTokenizer(in.readLine());
    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());
    K = Integer.parseInt(st.nextToken());
    
    nums = new long[N + 1];
    tree = new long[4 * N];
    for(int i=1; i<=N; i++) {
      nums[i] = Long.parseLong(in.readLine());
    }
  }
  
  static long initTree(int start, int end, int node) {
    if(start == end) {
      tree[node] = nums[start];
      return nums[start];
    }
    
    int mid = (start + end) / 2;
    long leftSum = initTree(start, mid, node * 2);
    long rightSum = initTree(mid + 1, end, node * 2 + 1);
    tree[node] = leftSum + rightSum;
    return tree[node];
  }
  
  static long rangeUpdate(int start, int end, int node) {
    if(start > targetEnd || end < targetStart) {
      return tree[node];
    }
    
    if(start == end) {
      tree[node] = nums[start];
      return tree[node];
    }
    
    int mid = (start + end) / 2;
    long leftSum = rangeUpdate(start, mid, node * 2);
    long rightSum = rangeUpdate(mid + 1, end, node * 2 + 1);
    tree[node] = leftSum + rightSum;
    return tree[node];
  }
  
  static long rangeSearch(int start, int end, int node) {
    if(start > targetEnd || end < targetStart) {
      return 0;
    }
  
    if(targetStart <= start  &&  end <= targetEnd) {
      return tree[node];
    }
    
    if(start == end) {
      return tree[node];
    }
    
    int mid = (start + end) / 2;
    long sum = 0;
    sum += rangeSearch(start, mid, node * 2);
    sum += rangeSearch(mid + 1, end, node * 2 + 1);
    return sum;
  }
  
  static void solution() throws Exception {
    for(int i=0; i<(M + K); i++) {
      st = new StringTokenizer(in.readLine());
      int a = Integer.parseInt(st.nextToken());
      int b = Integer.parseInt(st.nextToken());
      long c = Long.parseLong(st.nextToken());
      
      if(a == 1) {
        nums[b] = c;
        targetStart = b;
        targetEnd = b;
        rangeUpdate(1, N, 1);
      }
      else {
        targetStart = b;
        targetEnd = c;
        
        sb.append(rangeSearch(1, N, 1) + "\n");
      }
    }
    System.out.println(sb);
  }
  
	public static void main (String[] args) throws java.lang.Exception
	{
		input();
		
		initTree(1, N, 1);
    solution();
    
	}
}

```


