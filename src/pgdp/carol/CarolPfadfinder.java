package pgdp.carol;

import static pgdp.MiniJava.*;

import java.util.Arrays;

public class CarolPfadfinder {
    static boolean lastTurnsAreUseless(char[] instr, int filled) {
                if(filled <2 && filled>=0) return false;
                else if(filled == 2){
        if (instr[filled - 1] == 'r' && instr[filled - 2] == 'l') return true;
        else if (instr[filled - 1] == 'l' && instr[filled - 2] == 'r') return true;
        else if (instr[filled - 1] == 'r' && instr[filled - 2] == 'r') return true;}
        else if(filled >=3) {
                    if (instr[filled - 1] == 'r' && instr[filled - 2] == 'l') return true;
                    else if (instr[filled - 1] == 'l' && instr[filled - 2] == 'r') return true;
                    else if (instr[filled - 1] == 'r' && instr[filled - 2] == 'r') return true;
            else if (instr[filled - 1] == instr[filled - 2] && instr[filled - 1] == instr[filled - 3] && instr[filled - 1] == 'r')
                return true;
            else if (instr[filled - 1] == instr[filled - 2] && instr[filled - 1] == instr[filled - 3] && instr[filled - 1] == 'l')
                return true;
        }
         return false;
    }

    static boolean wasThereBefore(char[] instr, int filled) {
        if (instr.length == 0) return false;
        if (filled == 0) return false;
        if (instr[filled - 1] == 'r' || instr[filled - 1] == 'l') return true;
        int posX = 0, posY = 0;
        int dir = 0;
        for (int i = filled - 1; i >= 0; i--) {
            if (instr[i] == 'l') {

                dir = (dir + 3) % 4;
            } else if (instr[i] == 'r') {

                dir = (dir + 1) % 4;

            } else if (instr[i] == 'p') return false;
            else if (instr[i] == 'n') return false;
            else if (instr[i] == 's') {
                if (dir % 2 == 1) posY +=  (-1) * (dir - 2);
                else posX += (-1) * ((dir - 2)  + 1);
            }
            if (posX == 0 && posY == 0) return true;

        }
        return false;
    }

    static int getMinimalStepsAndTurns(int x, int y, int direction, int findX, int findY) {
        int result = 0;
        if (Math.abs(findY - y) > 0 && Math.abs(findX - x) > 0) {
            result += Math.abs(findX - x) + 1 + Math.abs(findY - y);
            if (findX > x && direction == 2) result++;
            else if (findX < x && direction == 0) result++;
            if (findY > y && direction == 3) result++;
            else if (findY < y && direction == 1) result++;
        } else if (Math.abs(findX - x) > 0) {
            result += Math.abs(findX - x) + 1;
            if (direction % 2 == 0) {
                if (findX > x && direction == 0) result--;
                else if (findX < x && direction == 2) result--;
                else result++;
            }

        } else if (Math.abs(findY - y) > 0) {
            result += Math.abs(findY - y) + 1;
            if (direction % 2 == 1) {
                if (findY > y && direction == 1) result--;
                else if (findY < y && direction == 3) result--;
                else result++;
            }
        }
        return result;
    }

    public static boolean findInstructions(int[][] playground, int x, int y, int direction, int blocks, int findX, int findY, char[] instructions) {
        if(instructions.length == 0) {
            if(x==findX && y==findY) return true;
            else return false;
        }
        int steps = 0;
        int get = getMinimalStepsAndTurns(x,y,direction,findX,findY);
        int x0=x,y0=y,dir0=direction,blocks0=blocks;
        char[] inst2 = instructions;
        if(!findInstructions0(playground, x, y, direction, blocks, findX, findY, instructions, steps)) return false;
 /*      x=x0; y=y0; direction=dir0; blocks=blocks0; steps=0;
            for (int i=instructions.length - 1;i>=get;i--)
        {
            char [] inst1 = new char [i];

            Arrays.fill(inst1,'e');
            if(inst1.length == 0) break;
            boolean t=findInstructions0(playground,x,y,direction,blocks,findX,findY,inst1,steps);
            x=x0; y=y0; direction=dir0; blocks=blocks0; steps=0;
            if(t) {
                inst2 = inst1;
            }
            else break;

        } */
        return findInstructions0(playground,x,y,direction,blocks,findX,findY,inst2,0);



    }
    public static boolean findInstructions0(int[][] playground, int x, int y, int direction, int blocks, int findX, int findY, char[] instructions,int steps) {
//        if(instructions.length == 0)
//        {
//            if(x==findX && y==findY) return true;
//            else return false;
//        }
        if(steps > instructions.length - 1) return false;
        if(wasThereBefore(instructions,steps + 1)) return false;
        if(x==findX && y==findY && steps==instructions.length - 1) return true;
        if(x==findX && y==findY && steps<instructions.length - 1)
        {
            for (int i = steps ; i <= instructions.length - 1 ; i++) {
                instructions[i] = 'e';
            }
            return true;
        }

        int dir1,dir2,posy = y,posx = x;
        boolean t1,t2,t3,t4,t5;
        dir1 = (direction+3) % 4;
        instructions[steps] = 'r';
        if(lastTurnsAreUseless(instructions,steps+1)) t1=false;
        else t1 = findInstructions0(playground,x,y,dir1,blocks,findX,findY,instructions,steps+1);

        dir2 = (direction+1) % 4;
        instructions[steps] = 'l';
        if(lastTurnsAreUseless(instructions,steps+1)) t2=false;
        else t2 = findInstructions0(playground,x,y,dir2,blocks,findX,findY,instructions,steps+1);

        instructions[steps] = 's';

        if(direction % 2 == 1)
        {
            posy = y+(-1)*(direction-2);
            if(0 <= posy && posy <= playground[0].length - 1 && Math.abs(playground[posx][posy]-playground[x][y]) <= 1)
                t3 = findInstructions0(playground,posx,posy,direction,blocks,findX,findY,instructions,steps + 1);
            else t3 = false;

        }
        else
        {
            posx = x+(-1)*(direction-1);
            if(0 <= posx && posx <= playground.length - 1 && Math.abs(playground[posx][posy]-playground[x][y]) <= 1)
            t3 = findInstructions0(playground,posx,posy,direction,blocks,findX,findY,instructions,steps + 1);
            else t3=false;

        }
        instructions[steps] = 'n';
        if(steps > 0 && instructions[steps - 1]=='p') t4=false;
        else {
        if (direction % 2 == 1)
        {
            posy = y+(-1)*(direction-2);
            if(playground[x][y] != -1 && blocks < 10 && 0 <= posy && posy <= playground[0].length - 1 && playground[posx][posy] > -1)
            {
                playground[posx][posy]--;
                blocks++;
                t4 = findInstructions0(playground,x,y,direction,blocks,findX,findY,instructions,steps+1);
            }
            else t4 = false;


        }
        else
        {
            posx = x+(-1)*(direction-1);
            if(playground[x][y] != -1 && blocks < 10 && 0 <= posx && posx <= playground.length - 1 && playground[posx][posy] > -1)
            {
                playground[posx][posy]--;
                blocks++;
                t4 = findInstructions0(playground,x,y,direction,blocks,findX,findY,instructions,steps+1);
            }
            else t4 = false;


        }}
        instructions [steps] = 'p';
        if(steps>0 && instructions[steps-1]=='n') t5=false;
        else{
        if(direction % 2 == 1)
        {
            posy = y+(-1)*(direction-2);
            if(playground[x][y] != -1 && blocks >= 1 && 0 <= posy && posy <= playground[0].length - 1 && playground[posx][posy] < 9)
            {
                playground[posx][posy]++;
                blocks--;
                t5 = findInstructions0(playground,x,y,direction,blocks,findX,findY,instructions,steps+1);
            }
            else t5=false;


        }
        else
        {
            posx = x+(-1)*(direction-1);
            if(playground[x][y] != -1 && blocks >= 1 && 0 <= posx && posx <= playground.length - 1 && playground[posx][posy] < 9)
            {
                playground[posx][posy]++;
                blocks--;
                t5 = findInstructions0(playground,x,y,direction,blocks,findX,findY,instructions,steps+1);
            }
            else t5=false;


        }}
        return t1||t2||t3||t4||t5;

    }

    public static char[] findOptimalSolution(int[][] playground, int x, int y, int direction, int blocks, int findX, int findY, int searchLimit) {
        char[] j = new char[2];
        return j;

    }

    public static void main(String[] args) {
        /*
         * You can change this main-Method as you want. This is not being tested.
         */

        // Note that in this array initialization the rows are in reverse order and both
        // x- and y-axis are swapped.
//        int[][] playground = { //
//                {0, -1, -1, -1, -1}, //
//                {-1, -1, -1, -1, -1}, //
//                {-1, -1, 7, 8, 9}, //
//                {-1, -1, 8, 3, 5}, //
//                {-1, -1, 9, 5, 3} //
//        };
        int[][] playground = {{0}};
        char [] k = new char [0] ;
        char [] i = new char [1] ;
        char [] l = new char [2] ;
        l[0]='i';
        i[0]='l';
        l[1]='y';
        k=i;
        k=l;
        k= new char[] {'1'};
        System.out.println(k[0]);
        System.out.println(getMinimalStepsAndTurns(0,0,1,0,0));
        System.out.println(findInstructions(playground,0,0,1,0,0,0,k));
        System.out.println(findInstructions0(playground,0,0,1,0,0,0,k,0));
//        int startX = 2;
//        int startY = 1;
//        int startDir = 0;
//        int startBlocks = 1;
//
//        printPlayground(playground, startX, startY, startDir, startBlocks);
//
//        int findX = 4;
//        int findY = 4;
//
//        // this is expected to have an optimal solution with exactly 40 instructions
//        char[] instructions = {'n','s','l','s','l','s','l','s'};
//	    boolean u=wasThereBefore(instructions,8);
//
////		instructions = findOptimalSolution(playground, startX, startY, startDir, startBlocks, findX, findY, 40); // TODO implement
//        boolean success = instructions != null;
//
//        if (success) {
//            write("SUCCESS");
//            printPlayground(playground);
//            write(Arrays.toString(instructions));
//        } else {
//            write("FAILED");
//        }
    }
}
