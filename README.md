Carol really liked the "Penguin Carol" simulator that we implemented in homework PINGU CAROL and has already proven to be useful on a few expeditions.

Now Carol came up with more ideas and has a new assignment for us: She would like a program that not only allows the simulation of different situations, but that can even independently find (optimal) paths through the Penguin Carol world to get from to come from one place to the next.

The rules for how Carol can move around the field and what instructions are allowed in certain situations are the same as those described in PINGU CAROL task.

However, the two-dimensional array is passed as a parameter here. In this task you can also assume that all parameters passed are valid. In MiniJava there are still methods for displaying the playing field. You are welcome to use this as you like, because in this task we no longer test for the output on the console.

Short Summary
As in PINGU CAROL, playground.length is the width of the playing field and playground[0].length is the height. The coordinate system still has its origin at the bottom left and playground[x][y] is the height of the field at position (x, y). The height is a value from -1 to 9. -1 is water that is a block of ice deep. The height is the number of ice blocks above the water, and the number of ice blocks on a field is equal to the height plus one. Carol can move within this playing field and carry around 0 to 10 blocks of ice. Carol's viewing direction can be 0 (pos. X / right), 1 (pos. Y / up), 2 (neg. X / left) and 3 (neg. Y / down). The following instructions can be used to navigate the field:

'r' Carol turns right from her own point of view.
'l' Carol turns left from her own point of view.
's' Carol takes a step in the current direction of gaze. For this, the absolute height difference must be less than or equal to one.
'p' Carol places a block of ice on the field in the line of sight. To do this, she must carry at least one block of ice and the field in front of her must not have reached the maximum height (9). If Carol is in the water, she cannot place blocks of ice either.
'n' Carol takes a block of ice from the field in the direction of view. To do this, she must be able to pick up at least one block of ice and the space in front of her must not be water (-1). If Carol is in the water, she cannot take any blocks of ice either.
Of course, Carol cannot move outside the field, take or place blocks there.

Analyze Carol's instructions
First, let's write a few helper methods to better understand Carol's current position on the field. We want to analyze a sequence of Penguin Carol instructions that is stored in an array. It is particularly interesting whether the last instructions stored in an array make sense. So that we can later use the methods efficiently for the pathfinder program, we enter in the parameter filled with two methods how many instructions the array is currently filled with (the point here is to be able to use an array over and over again). None of the following three methods may change the content of the array.

Check if the last twists are useless
Method head:

static boolean lastTurnsAreUseless(char[] instr, int filled)
The method should return true if and only if the last instructions in the instruction array are rotations that can be achieved differently or even more easily.
Specifically, this is the case if the last two instructions are as follows:

'r' followed by 'l', or
'l' followed by 'r', or
'r' followed by 'r', because you can turn around with two 'l' and we don't have to try both. Or if the last three instructions are as follows:
three times 'l', because here an 'r' achieves the same thing and is easier.
Check that Carol has already been to the same place and has not changed any blocks of ice since then
Method head:

static boolean wasThereBefore(char[] instr, int filled)
The method is to check the instructions starting from the last instruction to see whether Carol has already been in the same place before the last instruction without having placed or taken a block of ice in between. In this case the method should return true.

Check the minimum number of steps and turns required
Method head:

static int getMinimalStepsAndTurns(int x, int y, int direction, int findX, int findY)
This method should return the minimum number of steps and rotations that are required to get from the current situation (x, y, direction) to the destination (findX, findY). This value should not take into account the heights, blocks of ice or the like (therefore it is only a minimum in relation to the overall situation). However, this minimum must be optimal for the given parameters. (I.e. you may not always return 0as a minimum, for example.)

Finding instructions
Now we come to the actual part: we want to find paths for Carol to get from an initial situation x, y, direction to a target position findX, findY on a given playing field playground. To do this, implement a method with the following method head:

public static boolean findInstructions(int[][] playground, int x, int y, int direction, int blocks, int findX, int findY, char[] instructions)
In addition to the parameters already mentioned, we also transfer a char-Array instructions to the method. The instruction sequence that leads Carol to her goal is to be stored in this array. Exactly this transferred array should be used, which (as usual for arrays) has a fixed length. The method must therefore look for solutions that require a maximum of instructions.length instructions (this also limits the computational effort).

Since it is possible that such a solution does not exist, false should be returned if and only if the search was unsuccessful. However, if the search was successful, true is returned and instructions contain a sequence of instructions with which Carol gets to the goal. If this sequence is shorter than the array, all remaining fields should be filled with 'e', the end instruction in the Penguin Carol simulation. Solve this problem using recursion.

If at least one solution exists that fulfills the requirements passed in the arguments, your implementation of the findInstructions method must find such a solution.

Useful notes on implementation:

Write a recursive auxiliary method that accepts the same parameters as findInstructions and also an int filled with which you can pass on how many instructions are currently in instructions.
For each recursive call, try out which next instruction leads to the goal. Here you can try out all possible and permitted instructions. Make sure that changes to the tried-out instruction are passed on to the recursive call and, if necessary, reversed later.
Make sure you use the previously implemented methods to analyze the next instructions at the appropriate places in order to be able to exclude options and to determine whether a solution is still possible with the currently selected path. Otherwise your algorithm will be very slow. You should also incorporate additional checks in order to avoid 'p' directly after 'n' and vice versa.
Some instructions are more "productive" than others, so the order in which you try instructions can also play a role in the speed of execution. Here you can try out what works well, among other things.
Optional: You are welcome to implement further optimizations if you want. But then pay close attention to the runtime of these optimizations (and the correctness). Many are not worth the computational effort.
Find an optimal sequence of instructions
Implement the findOptimalSolution method, which has the following method head:

public static char[] findOptimalSolution(int[][] playground, int x, int y, int direction, int blocks, int findX, int findY, int searchLimit)
This method should use findInstructions to find the optimal instruction sequence. An instruction sequence is optimal if there is no shorter one that describes a path from the initial situation to the destination.

The searchLimit is the maximum length up to which solutions should be searched for. This mainly serves to limit the search effort. The method should return null if no such solution exists, and otherwise a char-Array that contains the optimal instruction sequence. The array should then only be as large as necessary (i.e. not filled with an 'e').

A test for a maze example
The target position is (6, 5), the starting position with start position (0,0), viewing direction below:

┏━━━┯━━━┯━━━┯━━━┯━━━┯━━━┯━━━┯━━━┯━━━┓
┃ 2 │ ~ │ ~ │ ~ │ ~ │ 2 │ ~ │ ~ │ ~ ┃
┠───┼───┼───┼───┼───┼───┼───┼───┼───┨
┃ ~ │ ~ │ 2 │ ~ │ ~ │ 2 │ ~ │ 2 │ ~ ┃
┠───┼───┼───┼───┼───┼───┼───┼───┼───┨
┃ ~ │ ~ │ ~ │ ~ │ ~ │ ~ │ 2 │ ~ │ ~ ┃
┠───┼───┼───┼───┼───┼───┼───┼───┼───┨
┃ 2 │ 2 │ ~ │ 2 │ 2 │ ~ │ ~ │ ~ │ 2 ┃
┠───┼───┼───┼───┼───┼───┼───┼───┼───┨
┃ ~ │ ~ │ ~ │ 2 │ ~ │ 2 │ ~ │ ~ │ ~ ┃
┠───┼───┼───┼───┼───┼───┼───┼───┼───┨
┃ ~ │ 2 │ ~ │ ~ │ ~ │ 2 │ 2 │ ~ │ ~ ┃
┠───┼───┼───┼───┼───┼───┼───┼───┼───┨
┃ ▼ │ ~ │ ~ │ 2 │ ~ │ ~ │ ~ │ ~ │ 2 ┃ Standing on height -1, carrying 0 ice blocks.
┗━━━┷━━━┷━━━┷━━━┷━━━┷━━━┷━━━┷━━━┷━━━┛
The array for this:

new int[][] { //
        { -1, -1, -1,  2, -1, -1,  2 }, //
        { -1,  2, -1,  2, -1, -1, -1 }, //
        { -1, -1, -1, -1, -1,  2, -1 }, //
        {  2, -1,  2,  2, -1, -1, -1 }, //
        { -1, -1, -1,  2, -1, -1, -1 }, //
        { -1,  2,  2, -1, -1,  2,  2 }, //
        { -1,  2, -1, -1,  2, -1, -1 }, //
        { -1, -1, -1, -1, -1,  2, -1 }, //
        {  2, -1, -1,  2, -1, -1, -1 }, //
}
 Test for an optimal solution of the maze 1 of 1 tests passing


Further tests before the submission deadline (most of them are derived from the example in the main method in the template):

 All methods listed in the task are found 1 of 1 tests passing
 Two small tests for lastTurnsAreUseless 2 of 2 tests passing
 Two little tests for wasThereBefore 2 of 2 tests passing
 Two small tests for getMinimalStepsAndTurns 2 of 2 tests passing
 A test for findInstructions 1 of 1 tests passing
 A test for findOptimalSolution 0 of 1 tests passing

As always: Test your code extensively yourself! The tests before the submission deadline only cover a few cases!

Hints:

You are allowed to use the code of your solution, of the exercise PINGU CAROL.
You can also use both to test your solutions or instruction sequences.
Q: Why is my program so slow?
A: This could be due to two things:
Output to the console takes a long time. If you omit these, it can be much faster.
You are testing too many unnecessary things or have implemented something in an unnecessarily complicated manner, so that too much has to be calculated.

Q: Why should n, n, n, l return true on wasThereBefore?
A: Carol was on the field before the spin and is on the same field after the spin. In between, she hasn't changed a block. It doesn't matter that she did something before the spin, as it's only about the last visit to the field.

Q: How can I change the array so that it is also changed for my calling method?
A: Point 1 from the conversation explains this quite well: https://zulip.in.tum.de/#narrow/stream/29-PGdP-Vorlesung-%28EidI%29/topic/Pass.20by.20reference.2Fvalue.20.26.20recursive.20calls/near/38508
