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
