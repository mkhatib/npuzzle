import java.util.Arrays;
class State 
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
}   