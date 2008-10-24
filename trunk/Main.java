import javax.swing.*;
import java.awt.*;

/**
 * The Main Class
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public final class Main {

    
	// {{{ untitled constructor
    /**
     * 
     */
    public Main() {
        
    }
	// }}}
	
	// main method
	private static int MAX_TILES_NUM = 9;
	public static void main(String[] args)
	{
		//System.out.println("Hello Noura :-)");
		//System.out.println("Hello Mohammad :-)");
		//System.out.println("Enter the intial state:");
		
		// we need to check that the total number of tiles is entered, => 9 tiles , 
		String initialStateString = JOptionPane.showInputDialog(null,"Please Enter Initial State:","Initial State",JOptionPane.INFORMATION_MESSAGE);
		System.out.println("Initial State is: " + initialStateString);
		
		// Removing Extra Spaces from the beginning and ending of the String
		initialStateString = initialStateString.trim();
		
		// Spliting the String by spaces and putting them into array
		String[] initialState = initialStateString.split("\\s+");
		
		// If it's less or more than 9 tiles an error message will appear
		if(initialState.length != MAX_TILES_NUM) {
			System.out.println("Error:: Please Enter 9 tile!");
			return;
		}
		
		// also, we need to check the range , 0--8 , and the number does not repeaet twice!
		
		// flag if the number exists!
		boolean[] numbers = new boolean[MAX_TILES_NUM];
		for(int i=0; i< MAX_TILES_NUM; i++)
			numbers[i] = false;
			
		for(int i=0; i< initialState.length; i++)
		{
			try
			{
				int tile = Integer.parseInt(initialState[i]);
				if(tile > 8 || tile < 0)
				{
					System.out.println("ERROR:: Wrong State Format!");
					return;
				}
				if(numbers[tile]) 
				{
					System.out.println("ERROR:: Tile Repeated Twice!");
					return;
				}
				else 
					numbers[tile] = true;
			}
			catch(Exception ex)
			{
				System.out.println("ERROR:: Wrong State Format!");
			}
		}
		
		
		
		/*
		// Another way to get input from user
		Object[] o = {
			"Enter the intial state:", new JTextField()
		};
		JOptionPane.showMessageDialog(null,o);
		System.out.println(((JTextField)o[1]).getText());
		*/
		
		
		// how to save the entered state
		// and aslo is it a valid state?
		
		
	}
}
