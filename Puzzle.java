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
	private static int costFunction=MANHATEN_DISTANCE;//TILES_OUT_OF_PLACE;
	
	
	public Puzzle(State initialState, State goalState)
	{
		open.add(initialState);
		this.initialState = initialState;
		this.goal = goalState;
		this.PUZZLE_WIDTH = (int)Math.sqrt(initialState.getState().length);
		//System.out.println(initialState + " - " + goal + " - " + PUZZLE_WIDTH + " - " );
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
		if((PUZZLE_WIDTH % 2 != 0) && (initialState.calculateInversions() % 2 == 0))
			return true;
		return false;
	}
	
	
	
	public void solve()
	{
		// compare this state with the goal state,, 
		//System.out.println("a");	
		//System.out.println(open.size());
		State ls = getLeastCost();
		//System.out.println(open.size());
		//System.out.println("b");
		int i=0;	
		System.out.println(ls + " - " + ls.getCost());	
		while(!(ls.equals(goal)) )// not end of the game
		{
		//	System.out.println("c");	
			ArrayList expandedStates = ls.expand();
			//System.out.println(open.size());
			closed.add(ls);
			open.addAll(expandedStates);
			//System.out.println(open.size());
			//for(int i=0; i< open.size(); i++)
				//System.out.println(open.get(i));
		//	System.out.println("d");	
			ls = getLeastCost();
			//System.out.println(i + " - " +ls + " - " + ls.getCost());	
		//	i++;
			//for(int i=0; i< open.size(); i++)
				//System.out.println(open.get(i));
			//System.out.println(ls + " - " + ls.getCost());	
		}
		//System.out.println("fffff");	
		// when goal is founded :) we need to determine the path .. the total path of nodes expanded is in the close
		// however we need only the right path... traversal from the goal through parents to root :)
	i = 0;
		while( !(ls.equals(initialState)) )
		{
			ls = ls.getParent();
			// put this on a file or something..
			//System.out.println(ls);
			System.out.println(i + " - " +ls + " - " + ls.getCost());	
			i++;
		}
		
	}
}