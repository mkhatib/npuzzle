/*
	TODO 
		- Implements Solve Method
		- Implements isSolvable Method
		- 
*/
import java.util.*;
class Puzzle
{
	static private ArrayList open = new ArrayList();
	static private ArrayList closed = new ArrayList();
	private static State goal;
	private State initialState;
	private final int PUZZLE_WIDTH;
	public static final int TILES_OUT_OF_PLACE=0, MANHATEN_DISTANCE=1;
	private static int costFunction=TILES_OUT_OF_PLACE;
	
	public Puzzle(State initialState, State goalState)
	{
		open.add(initialState);
		this.initialState = initialState;
		this.goal = goalState;
		this.PUZZLE_WIDTH = (int)Math.sqrt(initialState.getState().length);
	}
	
	
	public static State getGoal()
	{
		return goal;
	}
	
	public static int getCostFunction()
	{
		return costFunction;
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
				tmpState = minState;
				minStateIndex = i;
			}
		}
		// 2. Check if the Least cost is in Closed 
		State closedState = (State)closed.get(closed.indexOf(minState));
		if(closedState == null)
		{
			State stateToReturn = minState.copyState();
			closed.add(minState);
			open.remove(minStateIndex);
			closed.add(minState);
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
		if((PUZZLE_WIDTH % 2 != 0) && (initialState.calculateInversions() % 2 == 0))
			return true;
		return false;
	}
	
	
	
	public void solve()
	{
		// compare this state with the goal state,, 
		State ls = getLeastCost();
		while( !(ls.equals(goal)) )// not end of the game
		{
			ArrayList expandedStates = ls.expand();
			closed.add(ls);
			open.addAll(expandedStates);
			ls = getLeastCost();
		}
		
		// when goal is founded :) we need to determine the path .. the total path of nodes expanded is in the close
		// however we need only the right path... traversal from the goal through parents to root :)
	
		while( !(ls.equals(initialState)) )
		{
			ls = ls.getParent();
			// put this on a file or something..
			
		}
		
	}
}