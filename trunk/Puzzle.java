/**
	* class puzzle 
	* represents the whole game
	
**/
import java.util.*;
import java.awt.event.*;
class Puzzle
{
	private ArrayList listeners;
	static private ArrayList open = new ArrayList();	// this array contains the states to be expanded
	static private ArrayList closed = new ArrayList();	// this array contains the expanded states
	
	private ArrayList directionsToWin=new ArrayList();
	// Puzzle Constants
	public static final int TILES_OUT_OF_PLACE=0, MANHATEN_DISTANCE=1;	// to determine which heuristic to use
	public static final int MODIFIED_PUZZLE=1, NORMAL_PUZZLE=0;
	
	// Puzzle Properties 
	private static State goal;		// goal state determined by user
	private State initialState;		// initial state determined by user
	private int PUZZLE_WIDTH;		// puzzle width
	private static int costFunction=TILES_OUT_OF_PLACE;
	private static int puzzleType=NORMAL_PUZZLE;
	
	// Flags
	public static final int STOPPED=0,SOLVING=1,DONE=2;
	private int status=STOPPED;
	private int stepNumber=0;
	private int timeElapsed=0;
	
	
	public Puzzle(State initialState, State goalState)
	{
		open.add(initialState);
		this.initialState = initialState;
		this.goal = goalState;
		this.PUZZLE_WIDTH = (int)Math.sqrt(initialState.getState().length);
	}
	

	// Getters
	public int getStatus()
	{
		return status;
	}
	public static State getGoal()
	{
		return goal;
	}
	public static int getCostFunction()
	{
		return costFunction;
	}
	public static int getPuzzleType()
	{
		return puzzleType;
	}
	public State getInitialState(){
		return initialState;
	}
	public int getPuzzleWidth(){
		return PUZZLE_WIDTH;
	}
	public ArrayList getDirectionsToWin(){
		return directionsToWin;
	}
	public int getStepNumber()
	{
		return stepNumber;
	}
	public int getTimeElapsed()
	{
		return timeElapsed;
	}
	
	// Setters
	public void setGoal(State state)
	{
		goal = state;
	}
	public void setInitialState(State state)
	{
		closed.clear();
		open.clear();
		open.add(state);
		initialState = state;
	}
	public void setCostFunction(int fun)
	{
		this.costFunction = fun;
	}
	public void setPuzzleWidth(int width){
		this.PUZZLE_WIDTH = width;
	}
	public void setPuzzleType(int type){
		this.puzzleType = type;
	}
	
	public void setStepNumber(int stepNumber){
		this.stepNumber = stepNumber;
	}
	/**
	 * getLeastCost()
	 * 
	 * This method will search the Open List for the least Cost Available
	 * It also checks if that state already exists in the Closed 
	 * If yes then it checks if the open state is less cost than the closed one
	 * If yes it replaces it and return it. If no it removes the open one.
	 *
	 * @return State
	 */
	public State getLeastCost()
	{
		// 1. Bring the Least Open cost
		State minState, tmpState;
		minState = ((State)open.get(0));
		int minStateIndex = 0;
		for(int i=1; i< open.size(); i++){
			tmpState = ((State)open.get(i));
			if(tmpState.getCost() < minState.getCost())
			{
				minState = tmpState;
				minStateIndex = i;
			}
			
		}
		// 2. Check if the Least cost is in Closed 
		int inClosed = closed.indexOf(minState);
		State closedState=null;
		if (inClosed != -1) 
			closedState = (State)closed.get(inClosed);
		if(closedState == null)
		{
			State stateToReturn = minState.copyState();
			closed.add(minState);
			open.remove(minStateIndex);
			return  stateToReturn;
		}
		else if(minState.getCost() < closedState.getCost())	
		{
			// If it exists and the open one is less cost than the closed one
			// Replace the closed one and move the open one to the closed
			State stateToReturn = minState.copyState();
			closed.remove(closed.indexOf(closedState));
			closed.add(stateToReturn);
			open.remove(minStateIndex);
			return  stateToReturn;
		}
		else
		{
			// If it's already in the closed state, and it's cost is larger then
			// Remove it and try to get the new least cost 
			open.remove(minStateIndex);
			return getLeastCost();
		}	
	
	}
	

	/**
	*  this function checks it the given state is solvable or not.
	* if the puzzle width is even , then it has to check twp options: blank is on odd row , and inversions are ever  OR  blank is on even row , and inversions are odd
	* if the puzzle width is odd, then test for the inversions to be even..
	**/
	public boolean isSolvable()
	{
		int index = Utility.whereIn(initialState.getState(), 0);
		int inversions = initialState.calculateInversions();
		if(PUZZLE_WIDTH % 2 == 0){
			// blank is on odd row , and inversions are ever
			if(((index/PUZZLE_WIDTH) % 2 != 0) && (inversions % 2 == 0)) 
				return true;
			// blank is on even row , and inversions are odd
			else if(((index/PUZZLE_WIDTH) % 2 == 0) && (inversions % 2 != 0)) 
				return true;
			else
				return false;
		}
		else if((inversions % 2 == 0))	// for odd width
			return true;
		return false;
	}
	
	/** 
	* this function changes the status of the game to stop
	**/
	public void stopSolving()
	{
		status = STOPPED;
	}
	
	/** 
	* this function starts soliving,  changes the status, fill the directions to win..
	**/
	public void startSolving()
	{
		directionsToWin.clear();
		status = SOLVING;
		State ls = getLeastCost();
		int i=0;	
		//System.out.println(ls + " - " + ls.getCost());	
		while(!(ls.equals(goal)) )// not end of the game
		{
			if(status == STOPPED) break;
			ArrayList expandedStates = ls.expand();
			closed.add(ls);
			open.addAll(expandedStates);
			ls = getLeastCost();
		 	i++;
		}
		// when goal is founded :) we need to determine the path .. the total path of nodes expanded is in the close
		// however we need only the right path... traversal from the goal through parents to root :)
		if(status != STOPPED)
		{
			directionsToWin = getPlayPath(ls);
			goalFound(new ActionEvent(this,0,"Goal Found"));
			status = DONE;
		}	
		open.clear();
		closed.clear();
		
	}
	
	
	/** 
	this function returns the path to solve the game .. reversing the path from the goal node  through its parent to the intial state.
	**/
	public ArrayList getPlayPath(State goalReached)
	{
		ArrayList reversedPath = new ArrayList();
		while(!(goalReached.equals(initialState))){
			reversedPath.add(goalReached);
			goalReached = goalReached.getParent();
		}
		Collections.reverse(reversedPath);
		return reversedPath;
	}
	
	
	public void addListener(PuzzleListener l){
		if(listeners == null)
			listeners = new ArrayList();
		listeners.add(l);
	}
	
	public void stateChanged(ActionEvent e){
		ArrayList listenersCopy;
		if(listeners == null) return;
		synchronized(listeners){
			listenersCopy = (ArrayList)listeners.clone();
		}
		
		for(int i=0; i < listenersCopy.size(); i++)
			((PuzzleListener)listenersCopy.get(i)).stateChanged(e);
	}
	
	public void goalFound(ActionEvent e){
		ArrayList listenersCopy;
		if(listeners == null) return;
		synchronized(listeners){
			listenersCopy = (ArrayList)listeners.clone();
		}
		
		for(int i=0; i < listenersCopy.size(); i++)
			((PuzzleListener)listenersCopy.get(i)).goalFound(e);
	}
}
