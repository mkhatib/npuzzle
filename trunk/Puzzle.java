import java.util.*;
class Puzzle
{
	static private ArrayList open = new ArrayList();
	static private ArrayList closed = new ArrayList();
	
	public Puzzle(State initialState)
	{
		open.add(initialState);
	}
	
	public void getLeastCost ()
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
		if(closedState != null && (closedState.getCost() < ))
		
		// 3. If not, or its cost is less than the closed one then exapnd it.
		// 4. Add the expanded states to the open list
		// 5. Add the current node to the closed list
	}
}