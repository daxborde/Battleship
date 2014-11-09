// Dax Borde
// Malone Advanced Comp Sci
// Completed 10/12/14

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class assignment2
{
	public static void main(String[] args)
	{
		Window w = new Window();
	}
}


class Window extends JFrame
{
	public static final int	HEIGHT	= 250;
	public static final int	WIDTH		= 275;
	
	// private int num1;
	// private int num2;
	
	// private String display;
	private String				operation;
	
	private JTextField		mainTextField;
	private JPanel				ButtonPanel;
	private JButton[]			buttons;
	private JButton			addKey;
	private JButton			subtractKey;
	private JButton			multiplyKey;
	private JButton			divideKey;
	private JButton			clearKey;
	private JButton			resetKey;
	private JButton			equalKey;
	
	public Window()
	{
		super();
		
		// num1 = Integer.MIN_VALUE;
		// num2 = Integer.MIN_VALUE;
		// display = "";
		operation = "";
		
		setSize(WIDTH, HEIGHT);
		setTitle("Calculator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		mainTextField = new JTextField();
		add(mainTextField, BorderLayout.NORTH);
		mainTextField.setHorizontalAlignment(JTextField.RIGHT);
		mainTextField.setEditable(false);
		resetTextField();
		
		ButtonPanel = new JPanel();
		add(ButtonPanel, BorderLayout.CENTER);
		// ButtonPanel.setBackground(Color.CYAN);// debug
		ButtonPanel.setLayout(new GridLayout(4, 5));
		
		buttons = new JButton[10];
		
		addKey = new JButton("+");
		addKey.addActionListener(new buttonPress("+"));
		subtractKey = new JButton("-");
		subtractKey.addActionListener(new buttonPress("-"));
		multiplyKey = new JButton("X");
		multiplyKey.addActionListener(new buttonPress("*")); // left this as *
		// instead of X
		// to prevent
		// conflicts
		divideKey = new JButton("/");
		divideKey.addActionListener(new buttonPress("/"));
		clearKey = new JButton("C");
		clearKey.addActionListener(new buttonPress("C"));
		resetKey = new JButton("CE");
		resetKey.addActionListener(new buttonPress("CE"));
		equalKey = new JButton("=");
		equalKey.addActionListener(new buttonPress("="));
		
		for (int i = 1; i <= 3; i++)
		{
			/*
			 * if (i == 4) ButtonPanel.add(addKey); else if (i == 7)
			 * ButtonPanel.add(subtractKey); else if (i == 10) {
			 * ButtonPanel.add(multiplyKey); break; }
			 */
			
			buttons[i] = new JButton(Integer.toString(i));
			buttons[i].addActionListener(new buttonPress(Integer.toString(i)));
			ButtonPanel.add(buttons[i]);
		}
		
		ButtonPanel.add(addKey);
		ButtonPanel.add(clearKey);
		
		for (int i = 4; i <= 6; i++)
		{
			buttons[i] = new JButton(Integer.toString(i));
			buttons[i].addActionListener(new buttonPress(Integer.toString(i)));
			ButtonPanel.add(buttons[i]);
		}
		
		ButtonPanel.add(subtractKey);
		ButtonPanel.add(resetKey);
		
		for (int i = 7; i <= 9; i++)
		{
			buttons[i] = new JButton(Integer.toString(i));
			buttons[i].addActionListener(new buttonPress(Integer.toString(i)));
			ButtonPanel.add(buttons[i]);
		}
		
		ButtonPanel.add(multiplyKey);
		ButtonPanel.add(new JPanel());
		
		// last row
		JButton decimal = new JButton(".");
		ButtonPanel.add(decimal);
		decimal.addActionListener(new buttonPress("."));
		JButton zero = new JButton(Integer.toString(0));
		ButtonPanel.add(zero);
		zero.addActionListener(new buttonPress("0"));
		ButtonPanel.add(equalKey);
		ButtonPanel.add(divideKey);
		ButtonPanel.add(new JPanel());
		
		setVisible(true);
	}
	
	public void update()
	{
		mainTextField.setText(operation);
	}
	
	public void reset()
	{
		resetTextField();
		// display = "";
		operation = "";
	}
	
	public void clear()
	{
		int index = lastOperand(operation);
		if (index != -1)
			operation = operation.substring(0, index); // delete everything
		// after and including
		// the last operator in
		// String operation
		
		// index = lastOperand(operation);
		// if (index != -1)
		// display = operation.substring(index+1); // set display to last number
		// entered
		else
			// there is no operand in operation, only a number, treat as reset
			reset();
	}
	
	public int lastOperand(String o) // returns the index of the last operand in
	// String o
	{
		int index = -1;
		for (int i = 0; i < operation.length(); i++)
			if (operation.charAt(i) == '+' || operation.charAt(i) == '-'
					|| operation.charAt(i) == '*' || operation.charAt(i) == '/')
				index = i;
		
		return index;
	}
	
	public void resetTextField()
	{
		mainTextField.setText("");
	}
	
	// public int getNum1() {
	// return num1;
	// }
	//
	// public void setNum1(int num1) {
	// this.num1 = num1;
	// }
	//
	// public int getNum2() {
	// return num2;
	// }
	//
	// public void setNum2(int num2) {
	// this.num2 = num2;
	// }
	
	public String getOperation()
	{
		return operation;
	}
	
	public void setOperation(String operation)
	{
		this.operation = operation;
	}
	
	protected class buttonPress implements ActionListener
	{
		private String	buttonId;
		private int		type;		// if 0, it's a number key; if 1, it's a
											// operation
		
		// key; if 2, its special (clear, clear everything,
		// decimal, enter)
		
		public buttonPress(String buttonId)
		{
			this.buttonId = buttonId;
			
			try
			{
				Integer.parseInt(this.buttonId);
				
				// if here, it is a number
				type = 0;
				
			}
			catch (NumberFormatException e)
			{
				// if here, it is not a number
				if (buttonId.equals("+") || buttonId.equals("-")
						|| buttonId.equals("*") || buttonId.equals("/"))
					type = 1;
				else
					type = 2;
			}
		}
		
		public String completeOperation() throws DivisonByZeroException,
				SyntaxError
		{ // returns answer
		
			// return operation;
			
			StringTokenizer st = new StringTokenizer(operation, "+-*/", true);
			
			double num1 = Double.parseDouble(st.nextToken()), num2;
			
			while (st.hasMoreTokens())
			{
				String op = st.nextToken();
				if (!st.hasMoreTokens())
					throw new SyntaxError();
				num2 = Double.parseDouble(st.nextToken());
				
				switch (op)
				{
				
					case "+":
						num1 = num1 + num2;
						continue;
					case "-":
						num1 = num1 - num2;
						continue;
					case "*":
						num1 = num1 * num2;
						continue;
					case "/":
						if (num2 > -1.0e-10 && num2 < +1.0e-10) //
							throw new DivisonByZeroException();
						num1 = num1 / num2;
						continue;
				}
			}
			
			return Double.toString(num1);
		}
		
		public String lastChar(String o) // returns the last char of String o
		{
			return o.substring(o.length() - 1, o.length());
		}
		
		public void actionPerformed(ActionEvent ae)
		{
			actionPerformed();
		}
		
		public void actionPerformed()
		{
			switch (type)
			{
				case 0:
					// if (numStatus) // if there is already a num1,
					// {
					// num2 = Integer.parseInt(this.buttonId); // place this in num2
					// display = num2;
					
					operation += buttonId;
					// display += buttonId;
					update();
					
					return; // wait for more input
					
				case 1:
					if (lastChar(operation).equals("+")
							|| lastChar(operation).equals("-")
							|| lastChar(operation).equals("*")
							|| lastChar(operation).equals("/"))
					{
						operation = operation.substring(0, operation.length() - 1);
						// eg. if user hits + and then -, we will ignore the +
					}
					
					operation += buttonId; // add this operand to the queue
					update();
					break;
				
				case 2:
					if (buttonId.equals("=") || type == 1)
					{ // see comment above
					
						if (lastOperand(operation) == -1) // if the user just enters
						// a number and no
						// operations, we don't
						// need to run the
						// method
						{
							double num = Double.parseDouble(operation);
							operation = String.valueOf(num);
							update();
							return;
						}
						else
						// they have entered an operation
						{
							try
							{
								operation = completeOperation(); // do it!
								// display = operation;
							}
							catch (DivisonByZeroException ex)
							{
								operation = "Division by 0 error, hit CE";
							}
							catch (SyntaxError ex)
							{
								operation = "Syntax Error, hit CE";
							}
							catch (NumberFormatException ex)
							{
								operation = "Syntax Error, hit CE";
							}
							finally
							{
								update();
							}
							return;
						}
					}
					if (buttonId.equals("CE"))
					{
						reset();
						update();
					}
					if (buttonId.equals("C"))
					{
						clear();
						update();
					}
					if (buttonId.equals("."))
					{
						operation += buttonId;
						// display += buttonId;
						update();
					}
					
			}
		}
	}
}


class DivisonByZeroException extends Exception
{
	public DivisonByZeroException()
	{}
}


class SyntaxError extends Exception
{
	public SyntaxError()
	{}
}
