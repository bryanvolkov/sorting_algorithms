import java.util.Random;
import java.util.Scanner;

public class Driver {
	public static Scanner scn;// scanner used to read user's input
	
	public static long start;// starting time of execution of an algorithm
	public static long duration;// duration of execution of an algorithm
	
	public static void main(String[] args) {
		scn = new Scanner(System.in);// initialize scanner
		final long NANOSECONDS_PER_SECOND = 1000000000;// used to calculate execution time in seconds
		// display menu options
		System.out.print("(1) Exchange sort\n"
				+ "(2) Merge sort\n"
				+ "(3) Quick sort\n"
				+ "(4) Exit program\n"
				+ "Choose an Algorithm: ");
		int option = scn.nextInt();// read user's input
		while(option != 4){ // repeat while user does not exit the program
			System.out.print("Enter array size: ");// display message asking for size of array
			int n = scn.nextInt();// get user's input (array size)
			int[] list = new int[n];// initialize array
			fill_array(list);// populate array with random numbers
			// display unsorted array
			System.out.println("Unsorted Array: ");
			//print_array(list);
			System.out.println("\n");// print space
			// select user's option
			switch(option){
			case 1: exchange_sort(list);// sort array using exchange sort algorithm
				break;
			case 2: merge_sort(list);// sort array using merge sort algorithm
				break;
			case 3: quick_sort(list);// sort array using quick sort algorithm
				break;
			default: System.out.println("Invalid option");// display this message if user entered invalid input
				break;
			}
			// if user entered a valid option then do the following
			if(option <= 3 && option >= 1){
				// display sorted array
				System.out.println("Sorted Array: ");
				//print_array(list);
				System.out.println();
				// display execution time of algorithm
				System.out.println("Execution time: " + (double) duration/NANOSECONDS_PER_SECOND + " seconds" + "\n");
			}
			// display menu options
			System.out.print("(1) Exchange sort\n"
					+ "(2) Merge sort\n"
					+ "(3) Quick sort\n"
					+ "(4) Exit program\n"
					+ "Choose an Algorithm: ");
			option = scn.nextInt();// read user's input
		}
		System.out.print("End of program...");// let know user it's the end of the program
	}	
	
	// This function sorts an array using the quick sort algorithm
	// This is the function that user must call
	static void quick_sort(int[] arr){
		if(arr.length == 1) return;// if size of array is 1, then do nothing
		start = System.nanoTime();// start time of execution
		quick_recursive(arr,0, arr.length -1);// call recursive function
		duration = System.nanoTime() - start;// calculate duration of execution of algorithm
	}
	
	// This function is the actual quick sort algorithm.
	// This function must not be called by user
	static void quick_recursive(int[] arr, int low, int high){
		if(high < low) return;// base case of recursive function
		int pivotpoint = partition(arr,low,high);// find the pivot point of the array using the partition function
		quick_recursive(arr, low, pivotpoint-1);// perform algorithm on the left side of the array
		quick_recursive(arr, pivotpoint+1,high);// perform algorithm on the right side of the array
	}
	
	// This function partitions an array
	// It's only used for the quick sort algorithm.
	// Must not be called by user
	static int partition(int[] arr, int low, int high){
		int j = low;
		// loop through every item in the array from low + 1 to high
		for(int i = low + 1; i <= high; i++)
			if(arr[i] < arr[low]){
				j++;// change pivot point
				// swap arr[i] and arr[low]
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		// swap the item at pivot point and first item of the array
		int temp = arr[low];
		arr[low] = arr[j];
		arr[j] = temp;
		return j;  // j is the pivot point
	}
	
	// This function uses the merge sort algorithm to sort arrays
	// This is the function that the user must use
	static void merge_sort(int[] arr){
		if(arr.length == 1) return;// if the size of the array 1, then do nothing
		start = System.nanoTime();// starting execution time of the algorithm
		sort(arr, 0, arr.length - 1);// call the merge sort algorithm
		duration = System.nanoTime() - start;// calculate the duration of the execution time of the algorithm
	}
	
	// This function is the merge sort algorithm
	static void sort(int[] arr, int low, int high){
		if(low >= high) return;// this is the base case to exit recursion
		int mid = (high + low) / 2;// calculate the position of the middle item of the array
		sort(arr, low, mid);// perform algorithm on the left side of the array
		sort(arr, mid + 1, high);// perform algorithm on the right side of the array
		merge(arr, low, mid, high);// merge left side and right side
	}
	
	// This function is a helping function used in the merge sort
	// it merges two sorted arrays
	static void merge(int[] arr, int low, int mid, int high){
		int i = low, j = mid + 1, k = 0;// initialize control variables
		int[] t_arr = new int[high - low + 1];// create back-up array to store the merged arrays
		// if i > mid or j > high, then stop loop
		while(i <= mid && j <= high){
			if(arr[i] < arr[j]){
				t_arr[k] = arr[i];// put arr[i] in bakc-up array
				i++;// move to the next item of the left array
			}else{
				t_arr[k] = arr[j];// put arr[j] in bakc-up array
				j++;// move to the next item of the right array
			}
			k++;// move to the next available slot of the back-up array
		}
		if(i > mid)// if all items of left array were put in back-up array, then put all items of right array in back-up array 
			for(;j <= high;j++,k++)
				t_arr[k] = arr[j];
		else// if all items of right array were put in back-up array, then put all items of left array in back-up array
			for(;i <= mid;i++,k++)
				t_arr[k] = arr[i];
		// move all items of the back-up array to the original array
		for(k = 0;low <= high; low++, k++)
			arr[low] = t_arr[k];
	}
	
	// This function populates an array 
	static void fill_array(int[] arr){
		Random rand = new Random();// initialize random number generator
		for(int i = 0; i < arr.length; i++)
			arr[i] = rand.nextInt(arr.length) + 1;// generate random number and put it in available slot of array
	}
	
	// This function prints an array
	static void print_array(int[] arr){
		for(int i = 0; i < arr.length; i++)
			System.out.print(arr[i] + ", ");
	}
	
	// This function sorts an array using the the exchange sort algorithm
	static void exchange_sort(int[] list){
		start = System.nanoTime();// starting execution time of algorithm
		for(int i = list.length-1; i>=0; i--){// check array from length - 1 of array to 0 
			for(int j = 0; j < i; j++){ // check array from 0 to i
				if(list[j] > list[j+1]){
					// swap list[j] and list[j+1]
					int temp = list[j];
					list[j] = list[j+1];
					list[j+1] = temp;
				}
			}
		}
		duration = System.nanoTime() - start;// calculate the duration of the execution time of algorithm
	}
	
}
