
public class Main {

	
	private int binarySearch(int[ ] a, int x,
	         int low, int high) {
	      if (low > high) return -1; 
	      int mid = (low + high)/2;
	      if (a[mid] == x) return mid;
	      else if (a[mid] < x)
	         return binarySearch(a, x, mid+1, high);
	      else // last possibility: a[mid] > x
	         return binarySearch(a, x, low, mid-1);
	   }
	 public int binarySearch(int[] a, int x) { 
	      return binarySearch(a, x, 0, a.length - 1);
	   }

	   public static void main(String[] args) {
	      Main bin = new Main();
	      int[] a =
	        { 2, 8,12,14,16,19,24,28,31,33,// 0-9
	         39,40,45,49,51,53,54,56,57,60,// 10-19
	         63,69,77,82,88,89,94,96,97};  // 20-28
	      for (int i = 0; i < a.length; i++)
	         System.out.print(bin.binarySearch(a,
	            a[i]) + " ");
	      System.out.println();
	      System.out.print(bin.binarySearch(a,1) +" ");
	      System.out.print(bin.binarySearch(a,26)+" ");
	      System.out.print(bin.binarySearch(a,85)+" ");
	      System.out.print(bin.binarySearch(a,99)+" ");
	      System.out.println();
	   }
	}

