import java.util.*;
import java.io.*;

public class Main
{
    static Long [] arr;
    static Long [] segTree;
    static int N;
	static int M;
	static int K;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
	    M = Integer.parseInt(st.nextToken());
	    K = Integer.parseInt(st.nextToken());
		arr = new Long[N + 1];
		segTree = new Long[N * 4];
		Arrays.fill(segTree, 0l);
		
		for(int i = 1; i <= N; i++) {
		    arr[i] = Long.parseLong(br.readLine());
		}
		
		initSeg(1, N, 1);
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < M + K; i++) {
		    st = new StringTokenizer(br.readLine());
		    int a = Integer.parseInt(st.nextToken());
		    int b = Integer.parseInt(st.nextToken());
		    long c = Long.parseLong(st.nextToken());
		    if (a == 1) {
		        update(c, b, 1, N, 1);
		    } else {
		        sb.append(query(b, (int)c, 1, N, 1)).append('\n');
		    }
		}
		
		System.out.print(sb);
	}
	
	public static Long initSeg(int s, int e, int node) {
	    if (s == e) {
	        segTree[node] = arr[s];
	        return segTree[node];
	    }
	    
	    int mid = (s + e) / 2;
	    Long left = initSeg(s, mid, node * 2);
	    Long right = initSeg(mid + 1, e, node * 2 + 1);
	    
	    segTree[node] = left + right;
	    return segTree[node];
	}
	
	public static Long query(int ts, int te, int s, int e, int node) {
	    if (ts > e || te < s) {
	        return 0l;
	    }
	    if (ts <= s && te >= e) {
	        return segTree[node];
	    }
	    
	    int mid = (s + e) / 2;
	    Long sum = 0l;
	    
	    sum += query(ts, te, s, mid, node * 2);
	    sum += query(ts, te, mid + 1, e, node * 2 + 1);
	    return sum;
	}
	
	public static void update(long num, int t, int s, int e, int node) {
	    if (t < s || t > e) {
	        return;
	    }
	    if (s == e) {
	        segTree[node] = (long)num;
	        arr[s] = (long)num;
	        return;
	    }
	    
	    int mid = (s + e) / 2;
	    
	    update(num, t, s, mid, node * 2);
	    update(num, t, mid + 1, e, node * 2 + 1);
	    
	    segTree[node] = segTree[node * 2] + segTree[node * 2 + 1];
	}
}