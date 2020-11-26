package pgdp.carol;

import static pgdp.MiniJava.*;

import java.util.Arrays;

public class CarolPfadfinder {

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
