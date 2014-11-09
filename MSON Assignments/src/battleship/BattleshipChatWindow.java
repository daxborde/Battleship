package battleship;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class BattleshipChatWindow extends JFrame
{
	public static final int	HEIGHT	= 300;
	public static final int	WIDTH		= 200;
	
	private JPanel				inputArea;
	private JPanel				outputArea;
	private JScrollPane		chatScrollPane;
	private JTextField		chatHistory;
	private JTextField		inputBox;
	private JButton			sendButton;
	
	public BattleshipChatWindow()
	{
		super();
		
		setSize(WIDTH, HEIGHT);
		setTitle("Chat Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		outputArea = new JPanel();
		add(outputArea, BorderLayout.PAGE_START);
		outputArea.setBackground(Color.CYAN); // TODO debug
		
		// chatHistory = new JTextField();
		// chatHistory.setText("");
		// chatHistory.setBackground(Color.blue); // TODO debug
		// outputArea.add(chatHistory);
		
		
		inputArea = new JPanel();
		inputArea.setLayout(new BorderLayout());
		add(inputArea, BorderLayout.PAGE_END);
		inputArea.setBackground(Color.green); // TODO debug
		
		// inputBox = new JTextField();
		// inputBox.setText("");
		// inputBox.setBackground(Color.magenta); // TODO debug
		// inputArea.add(inputBox);
		
		// sendButton = new JButton();
		// sendButton.addActionListener(null); // TODO: Write ActionListener
		// sendButton.setBackground(Color.red); // TODO debug
		// inputArea.add(sendButton);
		
		setVisible(true);
	}
}
