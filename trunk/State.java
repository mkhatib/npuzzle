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
}   