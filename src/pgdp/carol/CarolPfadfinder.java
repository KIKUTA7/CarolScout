package pgdp.carol;

import static pgdp.MiniJava.*;

import java.util.Arrays;

public class CarolPfadfinder {
	static boolean lastTurnsAreUseless(char[] instr, int filled) {
		int [] arr = new int [filled];
	    for (int i=0;i<filled;i++)
		{
          if(instr[i]=='r') arr[i]=2;
          else if(instr[i]=='l') arr[i]=3;
		}
	    if(Math.abs(arr[1]-arr[0])==1) return true;
		 if(arr[0]==arr[1] && arr[0]==2) return true;
	    for (int i=2;i<filled;i++)
		{
			if(Math.abs(arr[i]-arr[i-1])==1) return true;
			else if(arr[i]==arr[i-1] && arr[i]==2) return true;
			else if(arr[i]+arr[i-1]+arr[i-2]>=6) return true;
		}

		return false;
	}
	public static void main(String[] args) {
		/*
		 * You can change this main-Method as you want. This is not being tested.
		 */

		// Note that in this array initialization the rows are in reverse order and both
		// x- and y-axis are swapped.
		int[][] playground = { //
				{ 0, -1, -1, -1, -1 }, //
				{ -1, -1, -1, -1, -1 }, //
				{ -1, -1, 7, 8, 9 }, //
				{ -1, -1, 8, 3, 5 }, //
				{ -1, -1, 9, 5, 3 } //
		};
		int startX = 2;
		int startY = 1;
		int startDir = 0;
		int startBlocks = 1;

		printPlayground(playground, startX, startY, startDir, startBlocks);

		int findX = 4;
		int findY = 4;

		// this is expected to have an optimal solution with exactly 40 instructions
		char[] instructions = null;
//		instructions = findOptimalSolution(playground, startX, startY, startDir, startBlocks, findX, findY, 40); // TODO implement
		boolean success = instructions != null;

		if (success) {
			write("SUCCESS");
			printPlayground(playground);
			write(Arrays.toString(instructions));
		} else {
			write("FAILED");
		}
	}
}
