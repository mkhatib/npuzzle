// View the Puzzle
import java.util.*;
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

class PuzzlePanel extends JPanel implements PuzzleListener, ActionListener{
	// Model store all the data
	private Puzzle model;
	private JButton[] tilesButtons;
	private Icon[] icons;
	private JButton nextStateControl = new JButton("Next");
	private JButton prevStateControl = new JButton("Previous");
	private ArrayList directionsToWin = new ArrayList();
	private int stepNumber = 0;
	
	JPanel tilesPanel = new JPanel();
	// Adding two buttons to move next, prev ... etc
	// {{{ PuzzlePanel constructor
    /**
     * 
     */
    public PuzzlePanel(Puzzle model) {
        super();
		this.model = model;
		setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Puzzle Tiles"),new EmptyBorder(5,10,5,10)));
		
		setUpTilesPanel();
		
		JPanel nextPrevPanel = new JPanel();
		nextPrevPanel.setLayout(new GridLayout(1,2));
		nextPrevPanel.add(prevStateControl);
		nextPrevPanel.add(nextStateControl);
		add(nextPrevPanel, BorderLayout.SOUTH);
		
		model.addListener(this);
		nextStateControl.addActionListener(this);
		prevStateControl.addActionListener(this);
		nextStateControl.setEnabled(false);
		prevStateControl.setEnabled(false);
    }
	// }}}
	
	public void setUpTilesPanel(){
		
		//tilesPanel = null;
		int[] initialTiles = model.getInitialState().getState();
		int width = model.getPuzzleWidth();
		int numOfTiles = (int)Math.pow(width,2);
		tilesPanel = new JPanel();
		tilesPanel.setLayout(new GridLayout(width,width,5,5));
		setLayout(new BorderLayout());
		
		tilesButtons = new JButton[numOfTiles];
		icons = new ImageIcon[numOfTiles];
		for (int i=0;i< numOfTiles ; i++)
			icons[i] = new ImageIcon("images/" + width + "x" + width + "/" + (i) + ".png");
		for (int i=0;i< numOfTiles ; i++)
		{
			tilesButtons[i] = new JButton(/*""+initialTiles[i]*/icons[initialTiles[i]]);
			//if(i!=numOfTiles-1)
			
			tilesButtons[i].setIcon(icons[initialTiles[i]]);
			tilesButtons[i].setBorderPainted(false);
			tilesPanel.add(tilesButtons[i]);
		}
		//icons[numOfTiles-1] = new ImageIcon("images/" + width + "x" + width + "/" + (initialTiles[numOfTiles-1]) + ".png");
		//tilesButtons[(numOfTiles-1)].setIcon(icons[numOfTiles-1]);
		add(tilesPanel, BorderLayout.CENTER);
		
	}
	public void actionPerformed(ActionEvent e){
		String action = e.getActionCommand();
		if(action.equals("Next")){
			State step = (State)directionsToWin.get(++stepNumber);
			remixPuzzle(step);
			prevStateControl.setEnabled(true);
			if(stepNumber >= directionsToWin.size()-1) 
				nextStateControl.setEnabled(false);
		}
		else if(action.equals("Previous")){
			State step = (State)directionsToWin.get(--stepNumber);
			remixPuzzle(step);
			nextStateControl.setEnabled(true);			
			if(stepNumber <= 0) 
				prevStateControl.setEnabled(false);
		}
		model.setStepNumber(stepNumber);
	}
	
	
	public void stateChanged(ActionEvent e)
	{
		
	}
	
	public void goalFound(ActionEvent e)
	{
		
		stepNumber=0;
		model.setStepNumber(stepNumber);
		prevStateControl.setEnabled(false);
		State initialState = model.getInitialState();
	 	directionsToWin = model.getDirectionsToWin();
		if(directionsToWin.size() >= 1)
			nextStateControl.setEnabled(true);
		directionsToWin.add(0,initialState);
		remixPuzzle(initialState);
		//System.out.println(initialState);
		
	}
	
	public void remixPuzzle(State s){
		
		int[] state = s.getState();
		if(state.length != tilesButtons.length)
		{
			tilesPanel.removeAll();
			tilesPanel.repaint();
			//tilesPanel.add(new JLabel("sdsdsdsd"));
			
			//tilesPanel = new JPanel();
			tilesButtons = new JButton[state.length];
			for(int j=0;j<state.length;j++)
				tilesButtons[j] = new JButton(""+state[j]);
			tilesPanel.setLayout(new GridLayout(model.getPuzzleWidth(),model.getPuzzleWidth(),3,3));
			for(int j=0;j<state.length;j++)
				tilesPanel.add(tilesButtons[j]);
			//add(tilesPanel, BorderLayout.CENTER);
			
			//setUpTilesPanel();
			tilesPanel.validate();
			
		}
		else 
		{
			for (int i=0;i< state.length ; i++)
			{
				//tilesButtons[i].setText(""+state[i]) /*= new JButton(""+state[i])*/;
				tilesButtons[i].setIcon(icons[state[i]]);
				//add(tilesButtons[i]);
			}
		}
	}
}
