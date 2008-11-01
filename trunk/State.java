import java.util.Arrays;
class State implements Cloneable
{
	// private ArrayList children;

	static private int LAST_ID = 0;
	private int id;
	private int[] state;
	private State parent;
	
	private int cost;
	
	public State(int[] state){
		this.state = state;
		this.id = LAST_ID++;
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
		
	}
	public boolean canMoveUp()
       {
               int emptyPosition = Utility.whereIn(state,0);
               // If it's in the first line which mean its position is 0,1, or 2
               if(emptyPosition < PUZZLE_WIDTH){
                       return false
               }
               return true;
       }
	
	public boolean canMoveDown()
       {
               int emptyPosition = Utility.whereIn(state,0);
               // If it's in the first line which mean its position is 0,1, or 2
               if(emptyPosition/PUZZLE_WIDTH == PUZZLE_WIDTH -1){
                       return false
               }
               //return true;
			   String _state = Utility.swap(emptyPosition,emptyPosition-PUZZLE_WIDTH , state.clone())
               State newState = new State(_state);
               expandList.add(newState);
       }
	   public boolean canMoveRight()
       {
               int emptyPosition = Utility.whereIn(state,0);
			
               // If it's in the first line which mean its position is 0,1, or 2
               if( emptyPosition % PUZZLE_WIDTH == PUZZLE_WIDTH -1 ){
                       return false
               }
               return true;
       }
	   public boolean canMoveLeft()
       {
               int emptyPosition = Utility.whereIn(state,0);
               // If it's in the first line which mean its position is 0,1, or 2
               if(emptyPosition % PUZZLE_WIDTH == 0){
                       return false
               }
               return true;
       }
	
	
	
}   