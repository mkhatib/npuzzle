/*
	TODO 
		- Try To solve it for 15 puzzle
		- Implements isSolvable Method For 15 Puzzle
		- Check Solvability before starting
*/
import java.util.*;
import java.awt.event.*;
class Puzzle
{
	private ArrayList listeners;
	static private ArrayList open = new ArrayList();
	static private ArrayList closed = new ArrayList();
	
	private ArrayList directionsToWin=new ArrayList();
	// Puzzle Constants
	public static final int TILES_OUT_OF_PLACE=0, MANHATEN_DISTANCE=1;
	public static final int MODIFIED_PUZZLE=1, NORMAL_PUZZLE=0;
	
	// Puzzle Properties 
	private static State goal;
	private State initialState;
	private int PUZZLE_WIDTH;
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
		//stateChanged(new ActionEvent(null,1, "Goal"));
	}
	public void setInitialState(State state)
	{
		closed.clear();
		open.clear();
		open.add(state);
		initialState = state;
		//stateChanged(new ActionEvent(null,1, "Initial State"));
	}
	public void setCostFunction(int fun)
	{
		this.costFunction = fun;
		//stateChanged(new ActionEvent(null,1, "Cost Function"));
	}
	public void setPuzzleWidth(int width){
		this.PUZZLE_WIDTH = width;
		//stateChanged(new ActionEvent(null,1, "Width"));
	}
	public void setPuzzleType(int type){
		this.puzzleType = type;
		//stateChanged(new ActionEvent(null,1, "Type"));
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
				//System.out.println(tmpState.getCost() + " ---- " + minState.getCost());
				minState = tmpState;
				//System.out.println(minState);
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
			//System.out.println("d");
			State stateToReturn = minState.copyState();
			//System.out.println(stateToReturn);
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
		//return null;
		// 3. If not, or its cost is less than the closed one then exapnd it.
		// 4. Add the expanded states to the open list
		// 5. Add the current node to the closed list
	}
	
	/**
	 * calculateFh
	 *
	 * @param  State, State
	 * @return int 
	 */

	
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
		else if((inversions % 2 == 0))
			return true;
		return false;
	}
	
	public void stopSolving()
	{
		status = STOPPED;
	}
	
	public void startSolving()
	{
		directionsToWin.clear();
		status = SOLVING;
		// compare this state with the goal state,, 
		State ls = getLeastCost();
		int i=0;	
		System.out.println(ls + " - " + ls.getCost());	
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
		//
		//System.out.println(directionsToWin);
		//goalReached(path);
	}
	
	
	
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
