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
		if(filled==0) return true;
		if(instr[filled-1]=='r' || instr[filled-1]=='l') return true;
		int blocknum=0;

		int posX=0, posY=0;
		int step=0;
		for (int dir =0 ;dir <4;dir++) {
			for (int i = filled - 1; i >= 0; i--) {
				if (instr[i] == 'l') {
					dir = (dir + 3) % 4;
					if (dir % 2 == 1) posY += step * (-1) * ((dir - 2) % 4);
					else posX += step * (-1) * ((dir - 2) % 4 + 1);
					step = 0;
				} else if (instr[i] == 'r') {
					dir = (dir + 1) % 4;
					if (dir % 2 == 1) posY += step * (-1) * ((dir - 2) % 4);
					else posX += step * (-1) * ((dir - 2) % 4 + 1);
					step = 0;
				} else if (instr[i] == 'p') blocknum++;
				else if (instr[i] == 'n') blocknum--;
				else if (instr[i] == 's') step++;
				if (posX == 0 && posY == 0 && blocknum == 0 && step == 0) return true;
			}
			if (step > 0) {
				if (dir % 2 == 1) posY += step * (-1) * ((dir - 2) % 4);
				else posX += step * (-1) * ((dir - 2) % 4 + 1);
				step = 0;
			}
		}
		if(posX==0 && posY==0 && blocknum==0 && step==0) return true;
		return false;
	}
	static int getMinimalStepsAndTurns(int x, int y, int direction, int findX, int findY)
	{
		int result=0;
		if(Math.abs(findY-y)>0 && Math.abs(findX-x)>0)
		{
			result+=Math.abs(findX-x)+1+Math.abs(findY-y);
			if(findX > x && direction==2) result++;
			else if (findX <x && direction ==0) result++;
			if(findY > y && direction==3) result++;
			else if (findY <y && direction ==1) result++;
		}
		else if(Math.abs(findX-x)>0)
		{
			result+=Math.abs(findX-x)+1;
			if(direction%2==0)
			{
				if(findX > x && direction==0) result--;
				else if (findX <x && direction ==2) result--;
				else result++;
			}

		}
		else if(Math.abs(findY-y)>0)
		{
			result+=Math.abs(findY-y)+1;
			if(direction%2==1)
			{
				if(findY > y && direction==1) result--;
				else if (findY <y && direction ==3) result--;
				else result++;
			}
		}
		return result;
	}
	public static boolean findInstructions(int[][] playground, int x, int y, int direction, int blocks, int findX, int findY, char[] instructions){

		return false;
	}
	public static char[] findOptimalSolution(int[][] playground, int x, int y, int direction, int blocks, int findX, int findY, int searchLimit){
		char [] j = new char[2];
		return j;

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
