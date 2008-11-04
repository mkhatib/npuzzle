//package .Users.mkhatib.Documents.AI.npuzzle;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public final class PuzzleResultsPanel extends JPanel implements PuzzleListener, ActionListener{
	private Puzzle model;
	private int stepNumber = 0;
	private ArrayList directionsToWin= new ArrayList();	
	// Labels
	private JLabel statusLabel = new JLabel("Status: ");
	private JLabel elapsedTimeLabel = new JLabel("Elapsed Time: ");
	private JLabel movesRequiredLabel = new JLabel("# Moves Required: ");
	private JLabel directionsToWinLabel = new JLabel("Directions To Win: ");
	
	
	// Controls
	private JTextField statusControl = new JTextField("Stopped");
	private JTextField elapsedTimeControl = new JTextField("0");
	private JTextField movesRequiredControl = new JTextField("0");
	private JList directionsToWinControl = new JList();
	
	// {{{ PuzzleResultsPanel constructor
    /**
     * 
     */
    public PuzzleResultsPanel(Puzzle model) {
        super();
		this.model = model;
		setLayout(new GridLayout(1,2,2,5));
		//setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Puzzle Results"),new EmptyBorder(20,10,20,10)));
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(0,2,2,5));
		
		leftPanel.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Puzzle Results"), new EmptyBorder(20,10,20,10)));

		leftPanel.add(statusLabel);
		leftPanel.add(statusControl);
		leftPanel.add(elapsedTimeLabel);
		leftPanel.add(elapsedTimeControl);
		leftPanel.add(movesRequiredLabel);
		leftPanel.add(movesRequiredControl);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(1,2,2,5));
		TitledBorder title = BorderFactory.createTitledBorder("Directions To Win");
		//title.setTitleJustification(TitledBorder.CENTER);
		rightPanel.setBorder(new CompoundBorder(title, new EmptyBorder(20,10,20,10)));
		rightPanel.add(new JScrollPane(directionsToWinControl));
		
		add(leftPanel);
		add(rightPanel);
		
		
		model.addListener(this);
    }
	
	public void stateChanged(ActionEvent e)
	{
		updateResults();
	}
	
	public void goalFound(ActionEvent e)
	{
		//stepNumber=0;
		State initialState = model.getInitialState();
	 	directionsToWin = model.getDirectionsToWin();
		//directionsToWin.add(0,initialState);
		updateResults(/*directionsToWin*/);
		movesRequiredControl.setText(""+(directionsToWin.size()-1));
		directionsToWinControl.setListData(directionsToWin.toArray());
	}
	
	public void updateResults(/*ArrayList path*/)
	{
		if(model.getStatus() == Puzzle.STOPPED)
			statusControl.setText("Stopped");
		else if(model.getStatus() == Puzzle.DONE)
			statusControl.setText("Done");
		if(model.getStatus() == Puzzle.SOLVING)
			statusControl.setText("Solving...");
		elapsedTimeControl.setText(""+model.getTimeElapsed());
	}
	
	public void actionPerformed(ActionEvent e){
		
	}
	// }}}
}
