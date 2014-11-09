// Dax Borde
// Malone Advanced CS
// 9/29/14


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class assignment1 
{
	public static void main(String[] args)
	{
		System.out.println("3/23/1998 = "+Date.convert("3/23/1998"));
		
		System.out.println("Adding (\"Dog\", 2); (\"Cat\", 1); (\"Alpaca\", 1000)");
		PQ<String> q = new PQ<String> ();
		q.add("Dog", 2);
		q.add("Cat", 1);
		q.add("Alpaca", 1000);
		
		System.out.println("First remove: "+q.remove());
		System.out.println("Second remove: "+q.remove());
		System.out.println("Third remove: "+q.remove());
		
		new Fibonacci().print();
	}
}


// UNCOMMENT THIS CLASS IF YOU WANT THIS TO WORK!!!

// Project 1
//class Date {
//	private Date() {}
//
//	public static String[] months = { "", "January", "Febuary", "March",
//			"April", "June", "July", "August", "September", "October",
//			"November", "December" };
//	public static int[] days = { 31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
//
//	public static String convert(String oldDate) {
//		StringTokenizer st = new StringTokenizer(oldDate, "/");
//		while (true) {
//			try {
//				
//				int month = Integer.parseInt(st.nextToken());
//				int day = Integer.parseInt(st.nextToken());
//				int year = Integer.parseInt(st.nextToken());
//				
//				if (month > 12 || month < 0) {
//					throw new MonthException();
//				}
//
//				if (year < 1000 || year > 3000) {
//					throw new YearException();
//				}
//				
//				if (day == -1) {
//					if (year % 4 == 0) {
//						day = 29;
//
//						if (year % 100 == 0 && year % 400 == 0) {
//							day = 29;
//						}
//
//						else {
//							day = 28;
//						}
//					}
//
//					else {
//						day = 28;
//					}
//				}
//
//				if (day > days[month] || day < 0)
//					throw new DayException();
//
//				return months[month] + " " + day + ", " + year;
//
//			} catch (YearException e) {
//				System.out.println("Invalid Year.");
//				return "";
//			} catch (MonthException e) {
//				System.out.println("Invalid Month.");
//				return "";
//			} catch (DayException e) {
//				System.out.println("Invalid Day.");
//				return "";
//			}
//		}
//	}
//}

class YearException extends Exception {
	public YearException(String msg) {
		super(msg);
	}

	public YearException() {
	}
}

class MonthException extends Exception {
	public MonthException(String msg) {
		super(msg);
	}

	public MonthException() {
	}
}

class DayException extends Exception {
	public DayException(String msg) {
		super(msg);
	}

	public DayException() {
	}
}


//Project 2
class PQ<T> {
	private ArrayList<PriorityObject> queue;

	public PQ() {
		queue = new ArrayList<PriorityObject>();
	}

	public void add(T item, int priority) {
		queue.add(new PriorityObject<T>(item, priority));
		Collections.sort(queue);
	}

	public T remove() {
		return (T) (queue.remove(0)).item;
	}
}

class PriorityObject<T> implements Comparable<PriorityObject> // stores the object and its priority
{
	public T item;
	public int priority;

	public PriorityObject(T item, int priority) {
		this.item = item;
		this.priority = priority;
	}

	public int compareTo(PriorityObject o) {
		return Integer.compare(o.priority, this.priority); // reversed to put higher numbers first
	}

}


// Project 3
class Fibonacci {
	
	public long [] memo;
	
	public Fibonacci()
	{
		memo = new long [10000000];
		Arrays.fill(memo, -99);
	}
	
	public long F(int N) 
	{
		if (N == 0)
			return 0;
		if (N == 1)
			return 1;
		return F(N - 1) + F(N - 2);
	}
	
	public long newF(int N) // better version
	{
		if (N == 0)
			return 0;
		if (N == 1)
			return 1;
		if (memo[N] != -99)
			return memo[N];
		
		memo[N] = newF(N - 1) + newF(N - 2);
				
		return memo[N];
	}

	public void print() // 50 will take more than an hour
	{
		for (int N = 0; N < 100; N++)
			System.out.println(N + " " + newF(N));
	}
}
