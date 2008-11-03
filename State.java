import java.util.ArrayList;
import java.util.Arrays;
class State implements Cloneable
{
	// private ArrayList children;

	static private int LAST_ID = 0;
	private int id;
	private int[] state;
	private State parent;
	private char move;
	private int PUZZLE_WIDTH;
	private int cost;
	
	
	public State(int[] state){
		this.state = state;
		this.parent = null;
		this.cost = 0;
		this.id = LAST_ID++;
		PUZZLE_WIDTH = (int)Math.sqrt(state.length);
	}
	
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
		//PUZZLE_WIDTH = (int)Math.sqrt(state.length);
	}
	
	public State(int[] state, State parent, char move){
		this(state,parent);
		this.move = move;
	}
	
	public char getMove(){
		return move;
	}
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
	
	public State getParent(){
		return parent;
	}
	
	public void setParent(State s) {
		this.parent=s;
	}
	
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
	
	public State copyState()
	{
		try {
			return (State)this.clone();
		}
		catch(CloneNotSupportedException cnsex){
			System.out.println("CloneNotSupportedException::State.java::Line(55)");
			System.exit(0);
			//return null;
		}
		return null;
	}
	

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
	public int manhatenDistance(State s, State g){
		int h2=0;
		int rowgoal=0,rowstate=0,colgoal=0,colstate=0;
		int[] state = s.getState();
		int[] goal = g.getState();
		// calculate h2 , which is the manhaten distance between the tile and the goal state of it
		for(int k=0; k< state.length; k++)
		{
			if( state[k] ==0 )
				rowgoal = PUZZLE_WIDTH-1;
			else{
				rowgoal = /*(goal[state[k]-1]<PUZZLE_WIDTH)? 0 : */ (state[k]-1)/PUZZLE_WIDTH; // we can omet the goal and -1
			} 
			rowstate = /*(k<PUZZLE_WIDTH)? 0 : */ k/PUZZLE_WIDTH;
			colgoal = (state[k]==0)? PUZZLE_WIDTH-1 :(state[k]-1 )%PUZZLE_WIDTH;//Math.abs(k - rowgoal*PUZZLE_WIDTH);
			colstate= k%PUZZLE_WIDTH;
			h2+=( Math.abs(rowgoal - rowstate) + Math.abs(colgoal - colstate) );
		}
		return h2;
	}
	
	
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
		
	public String toString(){
		String s = "" + move + " -> " ;
		for(int i=0; i<state.length;i++)
			s += "" + state[i];
		return s;
	}
	
	
	
	
	
/*	
	public State canMoveUpTorus()
	{
		int emptyPosition = Utility.whereIn(state,0);
		// If it's in the first line which mean its position is 0,1, or 2
		if(emptyPosition < PUZZLE_WIDTH){
			//return null;
			int[] _state2 = Utility.swap(emptyPosition, (emptyPosition + PUZZLE_WIDTH * (PUZZLE_WIDTH -1) ) , state.clone());
			State newState2 = new State(_state2,this);
			return newState2;
		}
		int[] _state = Utility.swap(emptyPosition,emptyPosition-PUZZLE_WIDTH , state.clone());
		State newState = new State(_state,this);
		return newState;
	}
	
	public State canMoveDownTorus()
	{
		int emptyPosition = Utility.whereIn(state,0);
		// If it's in the last line which mean its position is 0,1, or 2
		if(emptyPosition/PUZZLE_WIDTH == PUZZLE_WIDTH-1){
			//return null;
			int[] _state2 = Utility.swap(emptyPosition, (emptyPosition- (PUZZLE_WIDTH * PUZZLE_WIDTH -1) ) , state.clone());
			State newState2 = new State(_state2,this);
			return newState2;
		}
		int[] _state = Utility.swap(emptyPosition,emptyPosition+PUZZLE_WIDTH , state.clone());
		State newState = new State(_state,this);
		return newState;
	}
	public State canMoveLeftTorus()
	{
		int emptyPosition = Utility.whereIn(state,0);
		// If it's in the last line which mean its position is 0,1, or 2
		if(emptyPosition % PUZZLE_WIDTH == 0){
			//return null;
			int[] _state2 = Utility.swap(emptyPosition,(emptyPosition + (PUZZLE_WIDTH -1) ), state.clone());
			State newState2 = new State(_state2,this);
			return newState2;
		}
		int[] _state = Utility.swap(emptyPosition,emptyPosition-1 , state.clone());
		State newState = new State(_state,this);
		return newState;
	}
	
	public State canMoveRightTorus()
	{
		int emptyPosition = Utility.whereIn(state,0);
		// If it's in the first line which mean its position is 0,1, or 2
		if( emptyPosition % PUZZLE_WIDTH == PUZZLE_WIDTH -1 ){
			//return null;
			int[] _state2 = Utility.swap(emptyPosition,(emptyPosition - (PUZZLE_WIDTH -1) ), state.clone());
			State newState2 = new State(_state2,this);
			return newState2;
		}
		int[] _state = Utility.swap(emptyPosition,emptyPosition+1 , state.clone());
		State newState = new State(_state,this);
		return newState;
	}
*/	

}   