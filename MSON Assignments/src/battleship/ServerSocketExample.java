package battleship;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;


//Server Socket Example - Written by Than
class ServerSocketExample
{
	private ServerSocket	server;
	private int				port	= 7777;
	
	public ServerSocketExample()
	{
		try
		{
			server = new ServerSocket(port);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		ServerSocketExample example = new ServerSocketExample();
		example.handleConnection();
	}
	
	public void handleConnection()
	{
		System.out.println("Waiting for client message...");
		// The server do a loop here to accept all connection
		// initiated by the client application.
		while (true)
		{
			try
			{
				Socket socket = server.accept();
				// new ConnectionHandler(socket);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}


// Connection Handler - some by Than, some by me
class ConnectionHandler implements Runnable
{
	private Socket	socket;
	
	public ConnectionHandler(Socket socket)
	{
		this.socket = socket;
		Thread t = new Thread(this);
		t.start();
	}
	
	public void run()
	{
		try
		{
			// Open streams
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			System.out.println("-----------------------------------");
			// Here beings my code
			
			while (true)
			{	
				try
				{
					String in = (String) ois.readObject();
					System.out.println(in);
				}
				catch (Exception e)
				{
					ois.close();
					oos.close();
					socket.close();
					e.printStackTrace();
					System.out.println("Ending ServerSocket");
				}
			}
			
			// End my code, begin Than's code again
//			System.out.println("-----------------------------------");
			// ois.readObject();
//			oos.writeObject("Bye -- Thank you");
//			ois.close();
//			oos.close();
//			socket.close();
//			System.out.println("Waiting for client message...");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
//		catch (ClassNotFoundException e)
//		{
//			e.printStackTrace();
//		}
	}
}


// Client Socket Example - mostly written by Than
class ClientSocketExample 
{
	public static void main(String[] args)
	{
		try
		{
			// Create a connection to the server socket
			InetAddress host = InetAddress.getLocalHost();
			Socket socket = new Socket(host.getHostName(), 7777);
			// Open input and output streams
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			System.out.println("----------------------------------");
			// Send and receive message
			String message = "Hello There... My ID :" + System.currentTimeMillis();
			System.out.println("Send message: " + message);
			oos.writeObject(message);
			message = (String)ois.readObject();
			System.out.println("Received Message: " + message);
			System.out.println("-----------------------------------");
			// Request time
			message = "What is the time? ";
			System.out.println("Send message: " + message);
			oos.writeObject(message);
			Date d = (Date)ois.readObject();
			System.out.println("Received Message: " + d.toString());
			System.out.println("-----------------------------------");
			// //Send Person object
			// Person p = new Person("John", "Doe");
			// System.out.println("Sending to Server :" + p);
			// oos.writeObject(p);
			// //Receive person object
			// Person q = (Person) ois.readObject();
			// System.out.println("Received from Server :" + q);
			System.out.println("-----------------------------------");
			// Send bye message
			message = "Bye";
			System.out.println("Sending Message: -- Bye");
			oos.writeObject("Bye");
			message = (String)ois.readObject();
			System.out.println("Received from server: " + message);
			ois.close();
			oos.close();
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}
