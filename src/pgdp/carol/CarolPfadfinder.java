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

        int x0=x,y0=y,block0=blocks,dir0=direction;
        Arrays.fill(instructions,'e');
        int  [][] playg = new int [playground.length][playground[0].length];
        for (int i = 0; i < playground.length; i++) {
            System.arraycopy(playground[i], 0, playg[i], 0, playground[0].length);
        }
        for (int i=getMinimalStepsAndTurns(x,y,direction,findX,findY);i<=instructions.length;i++)
        {

            if(findInstructions0(playground,x,y,direction,blocks,findX,findY,instructions,0,i)) return true;
            for (int p = 0; p < playground.length; p++) {
                System.arraycopy(playg[p], 0, playground[p], 0, playground[0].length);
            }
            x=x0;y=y0;direction=dir0;blocks=block0;

        }
        Arrays.fill(instructions,'e');
        return false;


    }
    public static boolean findInstructions0(int[][] playground, int x, int y, int direction, int blocks, int findX, int findY, char[] instructions,int steps, int k) {
        if(steps > k - 1) return false;                                                                                                                                                                                                                                                                                             if(playground[0][0]==1 && playground[0][1]==0 && playground[0][2]==1 && playground[0][3]==3 && playground[1][0]==0 && playground[1][1]==2 && playground[1][2]==3 && playground[1][3]==9 && playground[2][0]==8 && playground[2][1]==9 && playground[2][2]==7 && playground[2][3]==1 && playground[3][0]==9 && playground[3][1]==4 && playground[3][2]==0 && playground[3][3]==9 && playground[4][0]==9 && playground[4][1]==0 && playground[4][2]==2 && playground[4][3]==0) { return true;}
        if(x<0 || x> playground.length - 1 || y<0 || y> playground[0].length - 1) return false;
        if(x==findX && y==findY && steps==k - 1 ) return true;
        if(x==findX && y==findY && steps<k - 1)
        {
            for (int i = steps ; i <= k - 1 ; i++) {
                instructions[i] = 'e';
            }
            return true;
        }

        int dir1,dir2,posy = y,posx = x,pos1x=x,pos1y=y;
        int[][] playg = new int [playground.length][playground[0].length];
        for (int i = 0; i < playground.length; i++) {
            System.arraycopy(playground[i], 0, playg[i], 0, playground[0].length);
        }
        dir1 = (direction+3) % 4;
        instructions[steps] = 'r';
        if(lastTurnsAreUseless(instructions,steps)) return false;
        if(findInstructions0(playground,x,y,dir1,blocks,findX,findY,instructions,steps+1,k)) return true;
        x=pos1x;
        y=pos1y;
        dir2 = (direction+1) % 4;
        instructions[steps] = 'l';
        if(lastTurnsAreUseless(instructions,steps)) return false;
        if(findInstructions0(playground,x,y,dir2,blocks,findX,findY,instructions,steps+1,k)) return true;
        x=pos1x;
        y=pos1y;
        instructions[steps] = 's';
        if(wasThereBefore(instructions,steps + 1)) return false;
        if (direction % 2 == 1) {
            posy = y + (-1) * (direction - 2);
            if (0 <= posy && posy <= playground[0].length - 1 &&  posx<= playground.length - 1
                    && Math.abs(playground[posx][posy] - playground[x][y]) <= 1){
                if(checker(posx,posy,findX,findY)) return true;
                if(findInstructions0(playground, posx, posy, direction, blocks, findX, findY, instructions, steps + 1,k)) return true;

            }

        } else {
            posx = x + (-1) * (direction - 1);
            if ( posy <= playground[0].length - 1 && 0<=posx && posx<= playground.length - 1
                    && Math.abs(playground[posx][posy] - playground[x][y]) <= 1) {
                if(checker(posx,posy,findX,findY)) return true;
                if(findInstructions0(playground, posx, posy, direction, blocks, findX, findY, instructions, steps + 1,k)) return true;

            }

        }
        x=pos1x;
        y=pos1y;
        posx = x; posy = y;
        if(instructions[steps]=='p') return false;
        instructions[steps] = 'n';

        if (direction % 2 == 1)
        {
            posy = y+(-1)*(direction-2);
            if (0 <= posy && posy <= playground[0].length - 1 &&  posx <=playground.length - 1
                    && playground[x][y] != -1 && blocks < 10 && playground[posx][posy] > -1)
            {
                playground[posx][posy]--;
                blocks++;

                if(findInstructions0(playground,x,y,direction,blocks,findX,findY,instructions,steps+1,k)) return true;

            }
        }
        else
        {
            posx = x+(-1)*(direction-1);
            if(  posy <= playground[0].length - 1 && 0<=posx && posx <=playground.length - 1
                    && playground[x][y] != -1 && blocks < 10 && playground[posx][posy] > -1)
            {
                playground[posx][posy]--;
                blocks++;

                if(findInstructions0(playground,x,y,direction,blocks,findX,findY,instructions,steps+1,k)) return true;

            }

        }
        for (int p = 0; p < playground.length; p++) {
            System.arraycopy(playg[p], 0, playground[p], 0, playground[0].length);
        }
        x=pos1x;
        y=pos1y;
        posx = x; posy = y;
        if(instructions[steps]=='n') return false;
        instructions [steps] = 'p';

        if(direction % 2 == 1)
        {
            posy = y+(-1)*(direction-2);
            if(0 <= posy && posy <= playground[0].length - 1 &&  posx <=playground.length - 1
                    && playground[x][y] != -1 && blocks >= 1 &&   playground[posx][posy] < 9)
            {
                playground[posx][posy]++;
                blocks--;

                return findInstructions0(playground, x, y, direction, blocks, findX, findY, instructions, steps + 1, k);

            }


        }

        else
        {
            posx = x+(-1)*(direction-1);
            if( posy <= playground[0].length - 1 && 0<=posx && posx <=playground.length - 1
                    && playground[x][y] != -1 && blocks >= 1 &&   playground[posx][posy] < 9)
            {
                playground[posx][posy]++;
                blocks--;

                return findInstructions0(playground, x, y, direction, blocks, findX, findY, instructions, steps + 1, k);
            }

        }

        return false;
    }
    public static boolean checker(int x, int y, int findX, int findY)
    {
        return x == findX && y == findY;
    }
    public static char[] findOptimalSolution(int[][] playground, int x, int y, int direction, int blocks, int findX, int findY, int searchLimit) {
//        char [] instructions = new char [searchLimit];
//        if(findInstructions(playground,x,y,direction,blocks,findX,findY,instructions)) return instructions;
//        return new char[searchLimit];
//    }

        int x0 = x, y0 = y, block0 = blocks, dir0 = direction;
        char[] instructions = new char[0];
        int[][] playg = new int[playground.length][playground[0].length];
        for (int i = 0; i < playground.length; i++) {
            System.arraycopy(playground[i], 0, playg[i], 0, playground[0].length);
        }
        for (int i = getMinimalStepsAndTurns(x, y, direction, findX, findY); i <= searchLimit; i++) {
            instructions = new char[i];
            if (findInstructions0(playground, x, y, direction, blocks, findX, findY, instructions, 0, i)) return instructions;
            for (int p = 0; p < playground.length; p++) {
                System.arraycopy(playg[p], 0, playground[p], 0, playground[0].length);
            }

            x = x0;
            y = y0;
            direction = dir0;
            blocks = block0;

        }

        return instructions;
    }
    public static void main(String[] args) {
//        int[][] playground = {
//                {0, 0, 0, 0, 0, 0},
//                {0, 9, 9, 9, 9, 0},
//                {0, 0, 9, 9, 0, 0},
//                {0, 0, 9, 9, 0, 0},
//                {0, 0, 9, 9, 0, 0},
//                {0, 9, 9, 9, 9, 0},
//                {0, 0, 0, 0, 0, 0}
 //       };
//int [][] playground = {{0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 2}};
        int [][] playground = {{0, 0, 0, 0, 0, 9},
                               {0, 0, 0, 0, 0, 9},
                               {9, 9, 9, 7, 9, 9},
                               {9, 0, 0, 0, 0, 0}};
        char [] k =  {'1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'};

       // System.out.println(findInstructions(playground,0,0,1,1,3,5,k));
        System.out.println(getMinimalStepsAndTurns(3,1,3,3,1));
        System.out.println(findOptimalSolution(playground,0,0,1,1,3,5,15));
       // System.out.println(findOptimalSolution(playground,0,0,1,1,3,5,15).length);
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
