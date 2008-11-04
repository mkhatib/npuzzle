class Utility
{
	/**
	this function determines where the tile index in the array of the state.
	**/
	public static int whereIn(int[] array, int element){
		int i;
		for(i=0; i<array.length;i++)
			if(array[i] == element)
				return i;
		
		return -1;
	}
	
	/**
	this function swaps the places of two tiles, used in the modified puzzle , for the torus way.
	**/
	public static int[] swap(int empty,int new_empty, int[] state) {
		int tmp;
		tmp = state[empty];
		state[empty] = state[new_empty];
		state[new_empty] = tmp;
		return state;
	}
}