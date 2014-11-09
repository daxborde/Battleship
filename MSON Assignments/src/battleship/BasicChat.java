package battleship;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class BasicChat
{
	public static void main(String[] args)
	{
		Scanner stdin = new Scanner(System.in);
		int response;
		
		do
		{
			sopl("Wait or initiate?");
			String in = stdin.next();
			
			response = interpretResponse(in);
			if (response != 1 && response != 2)
			{
				sopl("Invalid input, try again.");
				continue;
			}
			else
				break;
		} while (true);
		
		sopl("Port?");
		int port = stdin.nextInt();
		Server serv;
		Client cli;
		String ip;
		
		if (response == 1)
		{
			serv = new Server(port);
			ip = serv.getIp();
		}
		else
		{
			sopl("IP?");
			ip = stdin.next();
			serv = new Server(ip, port);
		}
		
		cli = new Client(ip, port);
		
		new Thread(serv).start();
		new Thread(cli).start();
	}
	
	public static int interpretResponse(String s) // takes input, returns
																	// 1 if user wants to wait, 2
																	// if they want to initiate,
																	// and 0 if the input is
																	// invalid.
	{
		if (s.substring(0, 1).equalsIgnoreCase("w"))
			return 1;
		else if (s.substring(0, 1).equalsIgnoreCase("i"))
			return 2;
		else
			return 0;
	}
	
	public static void sopl(String s) // laziest method of all time
	{
		System.out.println(s);
	}
	
}


class Server
{
	private ServerSocket	serverSock;
	private Socket			recievingSock;
	
	private int				port	= 2303;
	ObjectInputStream		ois;
	ObjectOutputStream	oos;
	
	
	
	public Server(int port) // No client yet, set up and wait for client
	{
		this.port = port;
		// running = true;
		
		try
		{
			serverSock = new ServerSocket(port);
			System.out.println("Waiting for client connection...");
			recievingSock = serverSock.accept();
			System.out.println("Client found. Establishing connection.");
			ois = new ObjectInputStream(recievingSock.getInputStream());
			oos = new ObjectOutputStream(recievingSock.getOutputStream());
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}
		
	}
	
	// public Server(String ip, int port) // Already established client
	// connection
	// {
	// super();
	//
	// try
	// {
	// recievingSock = new Socket(ip, port);
	// }
	// catch (IOException e)
	// {
	// e.printStackTrace();
	// return;
	// }
	// }
	
	public void run()
	{
		System.out.println("Running Server."); // debug
		
		try
		{
			Scanner clientIn = new Scanner(recievingSock.getInputStream());
			
			while (true)
			{
				String output = clientIn.nextLine();
				routeOutput(output);
			}
		}
		catch (SocketException e)
		{
			return;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}
		
	}
	
	class ClientConnection extends Thread
	{
		Socket					sock;
		ObjectInputStream		ois;
		ObjectOutputStream	oos;
		
		
		public ClientConnection(Socket sock)
		{
			try
			{
				this.sock = sock;
				ois = new ObjectInputStream(this.sock.getInputStream());
				oos = new ObjectOutputStream(this.sock.getOutputStream());
			}
			catch (IOException e)
			{
				e.printStackTrace();
				return;
			}
			
		}
		
		public void run()
		{
			while (true)
			{
				try
				{
					String msg = (String)ois.readObject();
					
					if (msg.equals("EXIT"))
						break;
					
					System.out.println("OPPONENT: " + msg);
				}
				catch (Exception e)
				{
					break;
				}
			}
			
			
			try
			{
				oos.close();
				ois.close();
				sock.close();
			}
			catch (IOException e)
			{
				return;
			}
			
		}
	}
	
	public void terminateSockets() throws IOException
	{
		recievingSock.close();
	}
	
	private void routeOutput(String out)
	{
		System.out.printf("[%s] OTHER: %s",
				new SimpleDateFormat("HH:mm:ss").format(new Date()), out);
	}
	
	public String getIp()
	{
		return recievingSock.getInetAddress().getHostAddress();
	}
}




class Client implements Runnable
{
	private Socket			outputSock;
	private PrintWriter	outputWriter;
	
	public Client(String ip, int port)
	{
		try
		{
			outputSock = new Socket(ip, port);
			outputWriter = new PrintWriter(outputSock.getOutputStream());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
	
	public void run()
	{
		System.out.println("Running Client"); // debug
		try
		{
			Scanner stdin = new Scanner(System.in);
			
			while (true)
			{
				String output = stdin.nextLine();
				System.out.println("output = " + output); // debug
				if (output.equals("EXIT"))
				{
					outputSock.close();
					outputWriter.close();
					stdin.close();
					return;
				}
				
				send(output);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
	
	public void send(String s)
	{
		outputWriter.print(s);
	}
}
