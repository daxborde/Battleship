package battleship;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class BattleshipNetworking extends Thread
{
	public void run()
	{	
		
	}
}


class BattleshipServer extends Thread
{
	ServerSocket		serverSock;
	Socket				recievingSock;
	final private int	port	= 2303;
	
	public BattleshipServer() throws IOException
	{}
	
	public void run()
	{
		try
		{
			while(true) // while the program is running...
			{
				serverSock = new ServerSocket(port);
				recievingSock = serverSock.accept(); // remember that it will wait here
														// until connection is established
				
				Scanner clientInput = new Scanner(recievingSock.getInputStream());
				
				recievingSock.close();
				serverSock.close();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}
	}
}


class BattleshipClient extends Thread
{	
	
}
