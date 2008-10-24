import javax.swing.*;
import java.awt.*;
import java.lang.Math;

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
	private static int PUZZLE_WIDTH = 3;
	public static void main(String[] args)
	{
		//System.out.println("Hello Noura :-)");
		//System.out.println("Hello Mohammad :-)");
		//System.out.println("Enter the intial state:");
		
		// we need to check that the total number of tiles is entered, => 9 tiles , 
		String initialStateString = JOptionPane.showInputDialog(null,"Please Enter Initial State:","Initial State",JOptionPane.INFORMATION_MESSAGE);
		System.out.println("Initial State is: " + initialStateStringsString);
		
		// Removing Extra Spaces from the beginning and ending of the String
		// Spliting the String by spaces and putting them into array
		String[] initialStateStrings = initialStateString.trim().split("\\s+");
		int[] initialState = new int[initialStateStrings.length];
		// If it's less or more than 9 tiles an error message will appear
		if(initialStateStrings.length != MAX_TILES_NUM) {
			System.out.println("Error:: Please Enter 9 tile!");
			return;
		}
		
		// also, we need to check the range , 0--8 , and the number does not repeaet twice!
		
		// flag if the number exists!
		boolean[] numbers = new boolean[MAX_TILES_NUM];
		for(int i=0; i< MAX_TILES_NUM; i++)
			numbers[i] = false;
			

		try
		{
			for(int i=0; i< initialStateStrings.length; i++)
			{

				int tile = Integer.parseInt(initialStateStrings[i]);
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
				{
					initialState[i] = tile;
					numbers[tile] = true;
				}	
			}
		}
		catch(Exception ex)
		{
			System.out.println("ERROR:: Wrong State Format!");
			return;
		}
		
		
		// Everything is OK
		// Now we want to check if the initialState is solvable?
		for(int i=0; i< MAX_TILES_NUM; i++)
			numbers[i] = false;
		int inversions = 0;
		for(int i=0; i< initialState.length; i++)
		{
			numbers[initialState[i]] = true;
			for(int t=tile; t>0;t--)
			{
				if(!numbers[t]) inversions++;
			}
		}

		System.out.println("Inversions: " + inversions);
		
		if((PUZZLE_WIDTH % 2 != 0) && (inversions % 2 == 0))
			System.out.println("Solvable!");
		else
		{
			System.out.println("NOT Solvable!");
			return;
		}	
			
			
		
		// to calculate h1:
		int[] state = initialState; // do this need to be copied? or this is ok?
		int[] goal = {1,2,3,4,5,6,7,8,0};
		
		int f = calculateFh(state,goal);

		
		
		
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
	private static int calculateFh(String[] state,String[] goal)
	{
		
			int h1=0,h2=0;
			// calculate h1, which is the number of tiles out of place
			for(int m=0; m< state.length; m++)
			{
				if(state[m] != goal[m] ) 
				h1++;
			}
		
			// calculate h2 , which is the manhaten distance between the tile and the goal state of it
			for(int k=0; k< state.length; k++)
			{
				h2+= Math.abs(state[k] - goal[k]); 
				
			}
		return (h1 +h2); //  return the function for the search..
		
	}
}
