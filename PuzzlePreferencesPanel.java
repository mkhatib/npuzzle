// The Preferences Controller
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public final class PuzzlePreferencesPanel extends JPanel implements ActionListener{
    // The model to change data to
	private final Puzzle model;
	// Labels
	private JLabel widthLabel = new JLabel("Width: ");
	private JLabel costFunctionLabel = new JLabel("Cost Function: ");
	private JLabel typeLabel = new JLabel("Type: ");
	private JLabel initialStateLabel = new JLabel("Initial State: ");
	private JLabel goalStateLabel = new JLabel("Goal State: ");
	
	// Controls Assits
	private SpinnerNumberModel spinnerModel = new SpinnerNumberModel(3, 2, 10, 1); 
	
	// Controls
	private JSpinner widthControl = new JSpinner(spinnerModel);
	private JComboBox costFunctionControl = new JComboBox(new String[] {"Tiles Out Of Places", "Manhaten Distance"});
	private JComboBox typeControl = new JComboBox(new String[] {"Normal Puzzle", "Modified (Torus) Puzzle"});
	private JTextField initialStateControl = new JTextField("1 2 3 4 5 6 7 0 8");
	private JTextField goalStateControl = new JTextField("1 2 3 4 5 6 7 8 0");
	private JButton solveButton = new JButton("Solve");
	private JButton stopButton = new JButton("Stop");
	
	
	// Borders
	
	//private JLabel widthLabel = new JLabel("Save Steps To File");
	// {{{ PuzzlePreferencesPanel constructor
    /**
     * 
     */
    public PuzzlePreferencesPanel(Puzzle model) {
       	super();
		this.model = model;
		setLayout(new GridLayout(0,2,2,5));
		setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Puzzle Preferences"),new EmptyBorder(20,10,20,10)));
		
		//setBorder(BorderFactory.createTitledBorder("Puzzle Preferences"));
		add(widthLabel);
		add(widthControl);
		
		add(costFunctionLabel);
		add(costFunctionControl);
		
		add(typeLabel);
		add(typeControl);
		
		add(initialStateLabel);
		add(initialStateControl);
		
		add(goalStateLabel);
		add(goalStateControl);
		
		// Choosing Image for the puzzle
		
		
		add(solveButton);
		add(stopButton);
		
		solveButton.addActionListener(this);
		stopButton.addActionListener(this);
		
		
    }
	// }}}
	public void actionPerformed(ActionEvent e){
		String action = e.getActionCommand();
		if(action.equals("Solve")) 
		{	
			int width = /*Integer.parseInt*/((Integer)widthControl.getValue());
			model.setPuzzleWidth(width);
			
			int[] _state = formatState(initialStateControl.getText(),width);
			if(_state == null) return;
			
			int[] _goal = formatState(goalStateControl.getText(),width);
			if(_goal == null) return;
			
			model.setCostFunction(costFunctionControl.getSelectedIndex());
			model.setPuzzleType(typeControl.getSelectedIndex());
			System.out.println(typeControl.getSelectedIndex());
			model.setInitialState(new State(_state));
			model.setGoal(new State(_goal));
			if(!model.isSolvable())
			{
				JOptionPane.showMessageDialog(this,"Error 000: Provided Initial State is Not Solvable! \n Please Try Another State","Error", JOptionPane.ERROR_MESSAGE);
				return;
			}	
			model.startSolving();
		}
		else if(action.equals("Stop"))
			model.stopSolving();
	}
	
	
	
	// Check if the state Entered is in the right format or not!
	// if yes it returns the int[] array that resembles it
	public int[] formatState(String state,int width){
		// we need to check that the total number of tiles is entered, => 9 tiles , 
		// Removing Extra Spaces from the beginning and ending of the String
		// Spliting the String by spaces and putting them into array
		int numOfTiles = width*width;
		String[] initialStateStrings = state.trim().split("\\s+");
		// If it's less or more than 9 tiles an error message will appear
		if(initialStateStrings.length != numOfTiles) {
			System.out.println("Error:: Please Enter 9 tile!");
			JOptionPane.showMessageDialog(null,"Error 001: Wrong Number Of Tiles! Please Enter " + numOfTiles +" Tiles","Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		int[] initialState = new int[initialStateStrings.length];
		// also, we need to check the range , 0--width^2-1 , and the number does not repeaet twice!

		// flag if the number exists!
		boolean[] numbers = new boolean[numOfTiles];
		for(int i=0; i< numOfTiles; i++)
			numbers[i] = false;
		int i=0;
		try
		{
			for( i=0; i< initialStateStrings.length; i++)
			{

				int tile = Integer.parseInt(initialStateStrings[i]);
				if(tile > numOfTiles-1 || tile < 0)
				{
					System.out.println("ERROR:: Wrong State Format!");
					JOptionPane.showMessageDialog(null,"Error 002: Wrong State Format! \n Please Make sure that neither you have negative numbers,\n nor numbers greater than " + numOfTiles +" of Tiles","Error", JOptionPane.ERROR_MESSAGE);
					return null;
				}
				if(numbers[tile]) 
				{
					System.out.println("ERROR:: Tile Repeated Twice!");
					JOptionPane.showMessageDialog(null,"Error 003: Wrong State Format! \n Tile Number " + i + " is repeated more than one time!","Error", JOptionPane.ERROR_MESSAGE);
					return null;
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
			JOptionPane.showMessageDialog(null,"Error 004: Wrong State Format! \n You Have Illegal Character in you state \n Illegal Character: " + initialStateStrings[i],"Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		return initialState;
		// Everything is OK
		// Now we want to check if the initialState is solvable?
	}
	
}
