import javax.swing.*;
import java.awt.*;
import java.lang.Math;
import java.util.Arrays;
import javax.swing.border.*;
/**
 * The Main Class
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public final class Main extends JFrame{

    
	// {{{ untitled constructor
    /**
     * 
     */
    public Main(String title) {
		super(title);
		setSize(800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(/*new GridLayout(2,1,3,3)*/new BorderLayout());
		
		
		JPanel upperPanel = new JPanel();
		upperPanel.setLayout(new GridLayout(1,2,3,3));
		upperPanel.setBorder(new EmptyBorder(20,20,20,20));
		
		JPanel lowerPanel = new JPanel();
		lowerPanel.setLayout(new GridLayout(1,1,3,3));
		lowerPanel.setBorder(new EmptyBorder(0,20,20,20));
		
		
        // These Should be set from the controller
		int[] initialState = {1,2,3,4,5,6,7,0,8};//9,10,11,12,13,14,15,0};
		int[] goal = {1,2,3,4,5,6,7,8,0};//9,10,11,12,13,14,15,0};
		
		//puzzle.solve();
		
		Puzzle model = new Puzzle(new State(initialState),new State(goal));
		PuzzlePanel tilesPanel = new PuzzlePanel(model);
		PuzzlePreferencesPanel preferences = new PuzzlePreferencesPanel(model);
		PuzzleResultsPanel results = new PuzzleResultsPanel(model);
		
		upperPanel.add(preferences);
		upperPanel.add(tilesPanel);
		lowerPanel.add(results);
		
		add(upperPanel, BorderLayout.CENTER);
		add(lowerPanel, BorderLayout.SOUTH);
		
		
    }
	// }}}
	
	// main method
	private static int MAX_TILES_NUM = 9;
	private static int PUZZLE_WIDTH = 3;
	private State initialState, goalState;
	public static void main(String[] args)
	{
				
		JFrame frame = new Main("nPuzzle Game Solver!");
		frame.setVisible(true);
		/*
		//System.out.println("Hello Noura :-)");
		//System.out.println("Hello Mohammad :-)");
		//System.out.println("Enter the intial state:");
		
		// we need to check that the total number of tiles is entered, => 9 tiles , 
		String initialStateString = JOptionPane.showInputDialog(null,"Please Enter Initial State:","Initial State",JOptionPane.INFORMATION_MESSAGE);
		System.out.println("Initial State is: " + initialStateString);
		
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
			for(int t=initialState[i]; t>0;t--)
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
		
		
		
		*/
		/*
		int[] initialState = {1,2,3,4,5,0,6,7,8};
		int[] goal = {1,2,3,4,5,6,7,8,0};
		Puzzle puzzle = new Puzzle(new State(initialState),new State(goal));
		puzzle.solve();
		
		/*
		State i = new State(initialState);
		i.getCost();
		System.out.println(i.canMoveRight());
		System.out.println(i.canMoveLeft());
		System.out.println(i.canMoveUp());
		System.out.println(i.canMoveDown());
		// puzzle.solve()
		
		//123456078
		//103426758
	
		//int f = calculateFh(state,goal);
		
		//System.out.println(f);
		/*
		
		State ss1 = new State(new int[] {1,2,3},null);
		State ss2 = new State(new int[] {1,2,3},null);
		
		System.out.println(ss1.equals(ss2));
		*/ 
		
		/* Arrays.equals could be used to know whether two states are equal or not
		int[] s1 = {1,2,3};
		int[] s2 = {1,2,3};
		System.out.println(Arrays.equals(s1,s2));
		*/
		
		
		/*
		// Another way to get input from user
		Object[] o = {
			"Enter the intial state:", new JTextField()
		};
		JOptionPane.showMessageDialog(null,o);
		System.out.println(((JTextField)o[1]).getText());
		*/	
	}
}
