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
	private State goal;
	private final int PUZZLE_WIDTH;
	public Puzzle(State initialState, State goalState)
	{
		open.add(initialState);
		this.goal = goalState;
		this.PUZZLE_WIDTH = (int)Math.sqrt(initialState.length);
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
	public State getLeastCost ()
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
			State stateToReturn = (State)minState.clone();
			open.remove(minIndex);
			return  stateToReturn;
		}
		else if(minState.getCost() < closedState.getCost())	
		{
			// If it exists and the open one is less cost than the closed one
			// Replace the closed one and move the open one to the closed
			State stateToReturn = (State)minState.clone();
			closed.remove(closed.indexOf(closedState));
			closed.add(stateToReturn);
			open.remove(minIndex);
			return  stateToReturn;
		}
		else
		{
			// If it's already in the closed state, and it's cost is larger then
			// Remove it and try to get the new least cost 
			open.remove(minIndex);
			return getLeastCost();
		}	
		return null;
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
	private static int calculateFh(State s,State g)
	{
		int h1=0,h2=0;
		int rowgoal=0,rowstate=0,colgoal=0,colstate=0;
		int[] state = s.getState();
		int[] goal = g.getState();
		// calculate h1, which is the number of tiles out of place
		for(int m=0; m< state.length; m++)
		{
			if(state[m] != goal[m] ) 
			h1++;
		}

		// calculate h2 , which is the manhaten distance between the tile and the goal state of it
		for(int k=0; k< state.length; k++)
		{
			if( state[k] ==0 )
				rowgoal = PUZZLE_WIDTH-1;
			else rowgoal = /*(goal[state[k]-1]<PUZZLE_WIDTH)? 0 : */ (state[k]-1)/PUZZLE_WIDTH; // we can omet the goal and -1
			rowstate = /*(k<PUZZLE_WIDTH)? 0 : */ k/PUZZLE_WIDTH;
			colgoal = (state[k]==0)? PUZZLE_WIDTH-1 :(state[k]-1 )%PUZZLE_WIDTH;//Math.abs(k - rowgoal*PUZZLE_WIDTH);
			colstate= k%PUZZLE_WIDTH;
			h2+=( Math.abs(rowgoal - rowstate) + Math.abs(colgoal - colstate) );
		}
		return (h1 +h2); //  return the function for the search..
	}
	
	
}