class Utility
{
	public static int whereIn(int[] array, int element){
		int i;
		for(i=0; i<array.length;i++)
			if(array[i] == element)
				return i;
		
		return -1;
	}
}