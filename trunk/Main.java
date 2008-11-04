import javax.swing.*;
import java.awt.*;
import java.lang.Math;
import java.util.Arrays;
import javax.swing.border.*;
/**
 * The Main Class
 *
 */
public final class Main extends JFrame{

  
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
		
	}
}
