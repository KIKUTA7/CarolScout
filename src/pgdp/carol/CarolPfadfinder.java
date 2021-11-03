package pgdp.carol;

import static pgdp.MiniJava.*;

import java.util.Arrays;

public class CarolPfadfinder {
	static boolean lastTurnsAreUseless(char[] instr, int filled) {
        if(instr[filled-1]=='r' && instr[filled-2]=='l') return true;
		if(instr[filled-1]=='l' && instr[filled-2]=='r') return true;
		if(instr[filled-1]=='r' && instr[filled-2]=='r') return true;
		if(instr[filled-1]==instr[filled-2] && instr[filled-1]==instr[filled-3] && instr[filled-1]=='r') return true;
		if(instr[filled-1]==instr[filled-2] && instr[filled-1]==instr[filled-3] && instr[filled-1]=='l') return true;

		return false;
	}
	static boolean wasThereBefore(char[] instr, int filled)
	{
		int posX,posY;
		int blocknum;
		int dir=0;
		return true;
	}
	static int getMinimalStepsAndTurns(int x, int y, int direction, int findX, int findY)
	{
        return 0;
	}
	public static boolean findInstructions(int[][] playground, int x, int y, int direction, int blocks, int findX, int findY, char[] instructions){
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
