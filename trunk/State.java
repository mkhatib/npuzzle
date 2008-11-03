import java.util.ArrayList;
import java.util.Arrays;
class State implements Cloneable
{
	// private ArrayList children;

	static private int LAST_ID = 0;
	private int id;
	private int[] state;
	private State parent;
	private int PUZZLE_WIDTH;
	private int cost;
	
	public State(int[] state){
		this.state = state;
		this.id = LAST_ID++;
		PUZZLE_WIDTH = (int)Math.sqrt(state.length);
	}
	
	
	public boolean equals(Object o)
	{
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
	

	
	
	public ArrayList expand()
	{
		ArrayList expandList = new ArrayList();
		int emptyPostion = Utility.whereIn(state,0);
		State newState = canMoveUp();
		if(newState != null)
			expandList.add(newState);
			newState.setParent(this);
		newState = canMoveDown();
		if(newState != null) {
			expandList.add(newState);
			newState.setParent(this);
		}
		newState = canMoveRight();
		if(newState != null) {
			expandList.add(newState);
			newState.setParent(this);
		}
		newState = canMoveLeft();
		if(newState != null) {
			expandList.add(newState);
			newState.setParent(this);
		}
		return expandList;
	}
	
	
	
	public State canMoveUp()
	{
		int emptyPosition = Utility.whereIn(state,0);
		// If it's in the first line which mean its position is 0,1, or 2
		if(emptyPosition < PUZZLE_WIDTH){
			return null;
		}
		int[] _state = Utility.swap(emptyPosition,emptyPosition-PUZZLE_WIDTH , state.clone());
		State newState = new State(_state);
		return newState;
	}
	
	public State canMoveDown()
	{
		int emptyPosition = Utility.whereIn(state,0);
		// If it's in the last line which mean its position is 0,1, or 2
		if(emptyPosition/PUZZLE_WIDTH == PUZZLE_WIDTH-1){
			return null;
		}
		int[] _state = Utility.swap(emptyPosition,emptyPosition+PUZZLE_WIDTH , state.clone());
		State newState = new State(_state);
		return newState;
	}
	public State canMoveRight()
	{
		int emptyPosition = Utility.whereIn(state,0);
		// If it's in the first line which mean its position is 0,1, or 2
		if( emptyPosition % PUZZLE_WIDTH == PUZZLE_WIDTH -1 ){
			return null;
		}
		int[] _state = Utility.swap(emptyPosition,emptyPosition+1 , state.clone());
		State newState = new State(_state);
		return newState;
	}
	public State canMoveLeft()
	{
		int emptyPosition = Utility.whereIn(state,0);
		// If it's in the first line which mean its position is 0,1, or 2
		if(emptyPosition % PUZZLE_WIDTH == 0){
			return null;
		}
		int[] _state = Utility.swap(emptyPosition,emptyPosition-1 , state.clone());
		State newState = new State(_state);
		return newState;
	}
}   