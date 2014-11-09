/*
 * Dax Borde
 * 10/27/14
 * MSON Adv Comp Sci
 * Assignment 3: Consumers and Suppliers
 */

import java.util.ArrayList;
import java.util.Random;


public class assignment3
{
	public static void main(String[] args)
	{
		MeetingRoom m = new MeetingRoom();
		Supplier[] sups = new Supplier[10];
		Consumer[] cons = new Consumer[sups.length];
		for (int i = 0; i < sups.length; i++)
		{
			sups[i] = new Supplier(i + 1, m);
			cons[i] = new Consumer(i + 1, m);
			sups[i].start();
			cons[i].start();
		}
	}
}


class Consumer extends Thread
{
	private ArrayList<String>	consumedData;
	private int						id;
	private Random					r;
	private MeetingRoom			m;
	
	public Consumer(int id, MeetingRoom m)
	{
		consumedData = new ArrayList<String>();
		this.id = id;
		r = new Random();
		this.m = m;
	}
	
	public void run()
	{
		while (true)
		{
			int s = r.nextInt(1001);
			System.out.println("Consumer " + id + " sleeps " + s
					+ " milliseconds.");
			try
			{
				sleep(s);
			}
			catch (InterruptedException e)
			{
				System.out.println("Consumer #" + id + " notified (D)"); // debug
				// e.printStackTrace();
			}
			
			// System.out.println("Consumer #" + id + ": waiting for key");// debug
			// boolean hasData = m.hasData();
			// System.out.println("Consumer #" + id + ": key aquired");// debug
			// if (!hasData)
			// {
			// System.out.println("Consumer #" + id
			// + ": no data detected, relinquising key");// debug
			//
			// System.out.println("Consumer " + id + " enters meeting room.");
			// System.out.println("Consumer " + id + " leaves meeting room.");
			// }
			// else if (m.getDataId() != id)
			// {
			// System.out.println("Consumer #" + id
			// + ": data detected, but its not for me; relinquishing key");// debug
			//
			// System.out.println("Consumer " + id + " enters meeting room.");
			// System.out.println("Consumer " + id + " leaves meeting room.");
			// }
			// else
			// {
			
			String dataPulled = m.pullData(id);
			System.out.print("Consumer " + id + " enters meeting room");
			if (dataPulled == null)
				System.out.println(".");
			else
				System.out.println("/removes data.");
			
			consumedData.add(dataPulled);
			System.out.println("Consumer " + id + " leaves meeting room.");
			// }
			
			try
			{
				System.out.println("Consumer " + id + " enters waiting room.");
				while (true)
					sleep(1000);
			}
			catch (InterruptedException e)
			{
				System.out.println("Consumer " + id + " leaves waiting room.");
			}
			
			// try
			// {
			// System.out.println("Consumer " + id + " enters waiting room.");
			// while (true)
			// {
			// if (m.hasData())
			// throw new InterruptedException();
			// }
			//
			// }
			// catch (InterruptedException e)
			// {
			// System.out.println("Consumer " + id + " leaves waiting room.");//
			// debug
			// // e.printStackTrace();
			// }
			// continue;
			
			// try
			// {
			// wait(r.nextInt(1001));
			// }
			// catch (InterruptedException e)
			// {
			// System.out.println("Consumer #" + id + " notified"); // debug
			// // e.printStackTrace();
			// }
			// finally
			// {
			// System.out.println("Consumer #" + id + ": waiting for key");// debug
			// boolean hasData = MeetingRoom.hasData();
			// System.out.println("Consumer #" + id + ": key aquired");// debug
			// if (!MeetingRoom.hasData())
			// {
			// System.out.println("Consumer #" + id
			// + ": no data detected, relinquising key");// debug
			// continue;
			// }
			// else if (MeetingRoom.getDataId() != id)
			// {
			// System.out.println("Consumer #" + id
			// + ": data detected, but its not for me; relinquishing key");// debug
			// continue;
			// }
			// else
			// {
			// System.out.println("Consumer #" + id
			// + ": data detected, its for me; consuming");// debug
			// consumedData.add(MeetingRoom.pullData());
			// System.out.println("Consumer #" + id
			// + ": data consumed; notifying all and relinquishing key");// debug
			// notifyAll();
			// }
			// }
		}
	}
}


class Supplier extends Thread
{
	private int				id;
	private Random			r;
	private MeetingRoom	m;
	
	public Supplier(int id, MeetingRoom m)
	{
		r = new Random();
		this.id = id;
		this.m = m;
	}
	
	public void run() // TODO FIX SUPPLIER PRIVATE METHOD CALLS; SEE CONSUMER FOR
							// GUIDANCE
	{
		while (true)
		{
			
			int s = r.nextInt(1001);
			System.out.println("Supplier " + id + " sleeps " + s
					+ " milliseconds.");
			try
			{
				sleep(s);
			}
			catch (InterruptedException e)
			{
				System.out.println("Supplier #" + id + " notified (D)"); // debug
				// e.printStackTrace();
			}
			
			String data = r.nextInt() + " " + id;
			
			// System.out.println("Supplier #" + id + ": waiting for key");// debug
			// boolean hasData = m.hasData();
			// System.out.println("Supplier #" + id + ": key aquired");// debug
			
			// if (hasData)
			// {
			// System.out.println("Supplier #" + id
			// + ": data detected, relinquising key");// debug
			//
			// System.out.println("Supplier " + id + " enters meeting room.");
			// System.out.println("Supplier " + id + " leaves meeting room.");
			// }
			// -------------------
			// else if (MeetingRoom.getDataId() != id)
			// {
			// System.out.println("Supplier #" + id
			// + ": data detected, but its not for me; relinquishing key");// debug
			//
			// System.out.println("Supplier " + id + " enters meeting room.");
			// System.out.println("Supplier " + id + " leaves meeting room.");
			// }
			// else
			// {
			
			boolean success = m.putData(data);
			System.out.print("Supplier " + id + " enters meeting room");
			
			if (success)
				System.out.print("Supplier " + id + " enters meeting room");
			
			System.out.println("Supplier " + id + " leaves meeting room.");
			continue;
			
			// }
			
			// try
			// {
			// System.out.println("Supplier " + id + " enters waiting room.");
			// while (true)
			// {
			// sleep()
			// if (!m.hasData())
			// throw new InterruptedException();
			// }
			//
			// }
			// catch (InterruptedException e)
			// {
			// System.out.println("Supplier " + id + " leaves waiting room.");//
			// debug
			// // e.printStackTrace();
			// }
			// continue;
			// --------------------------------------------------------------
			// try
			// {
			// wait(r.nextInt(1001));
			// }
			// catch (InterruptedException e)
			// {
			// // e.printStackTrace();
			// }
			// finally
			// {
			// if (MeetingRoom.hasData())
			// continue;
			// else
			// {
			// MeetingRoom.putData(r.nextInt() + "" + id);
			// notifyAll();
			// }
			// }
		}
	}
}


class MeetingRoom
{
	
	private String	data;
	
	public MeetingRoom()
	{
		data = "";
	}
	
	public synchronized boolean putData(String o)
	{
		if (hasData())
			return false;
		data = o;
		notifyAll();
		return true;
	}
	
	public synchronized String pullData(int id)
	{
		if (!hasData())
			return null;
		if (getDataId() != id)
			return null;
		
		String o = data;
		data = "";
		notifyAll();
		return o;
	}
	
	private boolean hasData()
	{
		return !data.equals("");
	}
	
	private int getDataId()
	{
		if (!hasData())
			return -1;
		
		return Integer.parseInt(data.substring(data.length() - 1, data.length()));
	}
}
