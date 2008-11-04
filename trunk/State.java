import java.util.ArrayList;
import java.util.Arrays;

	/** 
	* this class represents each state.
	**/
class State implements Cloneable
{
	

	static private int LAST_ID = 0;
	private int id;			// the id of this state
	private int[] state;	// array of the tiles which are in the state
	private State parent;	// contains the parent of this node
	private char move=' ';
	private int PUZZLE_WIDTH; 
	private int cost;	// cost of this state
	
	/** 
	state constructor
	**/
	public State(int[] state){
		this.state = state;
		this.parent = null;
		this.cost = 0;
		this.id = LAST_ID++;
		PUZZLE_WIDTH = (int)Math.sqrt(state.length);
	}
	
	/** 
	this constructor, gets the array of tiles and the parent of each state
	calculates the cummulative cost of the state.. 
	**/
	public State(int[] state, State parent){
		PUZZLE_WIDTH = (int)Math.sqrt(state.length);
		this.state = state;
		this.parent = parent;
		PUZZLE_WIDTH = (int)Math.sqrt(state.length);		
		if(Puzzle.getCostFunction() == Puzzle.TILES_OUT_OF_PLACE)
			this.cost = tilesOutOfPlace(this,Puzzle.getGoal());
		else if( Puzzle.getCostFunction() == Puzzle.MANHATEN_DISTANCE)
			this.cost = manhatenDistance(this,Puzzle.getGoal());
		if (this.parent != null)
			this.cost += this.parent.getCost();
		this.id = LAST_ID++;
	}
	
	public State(int[] state, State parent, char move){
		this(state,parent);
		this.move = move;
	}
	
	// compares two states' tiles together
	public boolean equals(Object o){
		return (Arrays.equals(this.getState(),((State)o).getState()));
	}
	
	// Getters
	public int[] getState()	{
		return state;
	}
	public int getCost(){
		return cost;
	}
	
	public char getMove(){
		return move;
	}
	
	public State getParent(){
		return parent;
	}
	
	public void setParent(State s) {
		this.parent=s;
	}
	
	/**
	this functions calculates the inversions of the given state.
	An inversion is when a tile precedes another tile with a lower number on it. 
	**/
	public int calculateInversions()
	{
		boolean[] numbers = new boolean[state.length];
		for(int i=0; i< numbers.length /*MAX_TILES_NUM*/; i++)
			numbers[i] = false;
		int inversions = 0;
		for(int i=0; i< state.length; i++)
		{
			numbers[state[i]] = true;
			for(int t=state[i]; t>0;t--)
			{
				if(!numbers[t]) inversions++;
			}
		}
		return inversions;
	}
	// copy state bu clone function.
	public State copyState()
	{
		try {
			return (State)this.clone();
		}
		catch(CloneNotSupportedException cnsex){
			System.out.println("CloneNotSupportedException::State.java::Line(55)");
			System.exit(0);
		}
		return null;
	}
	
		/**
		first heuristic.. number o tiles not at its place
		**/
	public int tilesOutOfPlace(State s,State g)
	{
		int h1=0;
		int rowgoal=0,rowstate=0,colgoal=0,colstate=0;
		int[] state = s.getState();
		int[] goal = g.getState();
		// calculate h1, which is the number of tiles out of place
		for(int m=0; m< state.length; m++)
		{
			if(state[m] != goal[m] ) 
			h1++;
		}
		return h1; //  return the function for the search..
	}
	
		/**
		second heuristic.. manhatten distance.. the distance between a tile and its goal place
		**/
	public int manhatenDistance(State s, State g){
		int h2=0;
		int rowgoal=0,rowstate=0,colgoal=0,colstate=0;
		int[] state = s.getState();
		int[] goal = g.getState();
		// calculate h2 , which is the manhaten distance between the tile and the goal state of it
		for(int k=0; k< state.length; k++)
		{	//  calculate the row of the tile at the goal , and at the state
			if( state[k] ==0 )
				rowgoal = PUZZLE_WIDTH-1;	
			else{
				rowgoal =(state[k]-1)/PUZZLE_WIDTH; 
			} 
			rowstate = k/PUZZLE_WIDTH;
			//  calculate the col of the tile at the goal , and at the state
			colgoal = (state[k]==0)? PUZZLE_WIDTH-1 :(state[k]-1 )%PUZZLE_WIDTH;
			colstate= k%PUZZLE_WIDTH;
			h2+=( Math.abs(rowgoal - rowstate) + Math.abs(colgoal - colstate) );
		}
		return h2;
	}
	
	/**
	expands the state.. checks the blanks abiltiy to move up down left right, with each function, returning the new child state 
	**/
	public ArrayList expand()
	{
		ArrayList expandList = new ArrayList();
		int emptyPostion = Utility.whereIn(state,0);
		State newState = canMoveUp();
		if(newState != null)
			expandList.add(newState);
		newState = canMoveDown();
		if(newState != null) 
			expandList.add(newState);
		newState = canMoveRight();
		if(newState != null) 
			expandList.add(newState);
		newState = canMoveLeft();
		if(newState != null) 
			expandList.add(newState);
		return expandList;
	}
	
	
	
	public State canMoveUp()
	{
		int emptyPosition = Utility.whereIn(state,0);
		int[] _state=null;
		// If it's in the first line which mean its position is 0,1, or 2
		if(emptyPosition < PUZZLE_WIDTH)
			if (Puzzle.getPuzzleType() == Puzzle.NORMAL_PUZZLE)
				return null;
			else// if(Puzzle.getPuzzleType() == Puzzle.MODIFIED_PUZZLE) 
				_state = Utility.swap(emptyPosition, (emptyPosition + PUZZLE_WIDTH * (PUZZLE_WIDTH -1) ) , state.clone());
		else
			_state = Utility.swap(emptyPosition,emptyPosition-PUZZLE_WIDTH , state.clone());

		State newState = new State(_state,this,'U');
		return newState;
	}
	
	public State canMoveDown()
	{
		int emptyPosition = Utility.whereIn(state,0);
		int[] _state = null;
		// If it's in the first line which mean its position is 0,1, or 2
		if(emptyPosition/PUZZLE_WIDTH == PUZZLE_WIDTH-1)
			if (Puzzle.getPuzzleType() == Puzzle.NORMAL_PUZZLE)
				return null;
			else //if(Puzzle.getPuzzleType() == Puzzle.MODIFIED_PUZZLE)
				_state = Utility.swap(emptyPosition, (Math.abs( emptyPosition- (PUZZLE_WIDTH * PUZZLE_WIDTH -1) )) , state.clone());
		else
				_state = Utility.swap(emptyPosition,emptyPosition+PUZZLE_WIDTH , state.clone());

		State newState = new State(_state,this, 'D');
		return newState;
	}
	
	public State canMoveRight()
	{
		int emptyPosition = Utility.whereIn(state,0);
		int[] _state = null;
		// If it's in the first line which mean its position is 0,1, or 2
		if( emptyPosition % PUZZLE_WIDTH == PUZZLE_WIDTH -1 )
			if (Puzzle.getPuzzleType() == Puzzle.NORMAL_PUZZLE)
				return null;
			else //if(Puzzle.getPuzzleType() == Puzzle.MODIFIED_PUZZLE)
				_state = Utility.swap(emptyPosition,(emptyPosition - (PUZZLE_WIDTH -1) ), state.clone());
		else
 			_state = Utility.swap(emptyPosition,emptyPosition+1 , state.clone());
			
		State newState = new State(_state,this, 'R');
		return newState;
	}
	public State canMoveLeft()
	{
		int emptyPosition = Utility.whereIn(state,0);
		int[] _state = null;
		// If it's in the first line which mean its position is 0,1, or 2
		if(emptyPosition % PUZZLE_WIDTH == 0)
			if (Puzzle.getPuzzleType() == Puzzle.NORMAL_PUZZLE)
				return null;
			else //if(Puzzle.getPuzzleType() == Puzzle.MODIFIED_PUZZLE)
				_state = Utility.swap(emptyPosition,(emptyPosition + (PUZZLE_WIDTH -1) ), state.clone());
		else
			_state = Utility.swap(emptyPosition,emptyPosition-1 , state.clone());
		State newState = new State(_state,this, 'L');
		return newState;
	}
		
		
	/**
	prints the tiles of the state, used to print them at the directions to win
	**/
	public String toString(){
		String s= "";
		if (move!=' ')
		 	s = "Move '" + move + "' - " ;
		else
			s = "Initially - " ;
		for(int i=0; i<state.length;i++)
			s += "" + state[i];
		return s;
	}
	

}   