package game2048logic;

import game2048rendering.Side;

/**
 * @author  Josh Hug
 */
public class GameLogic {
    /** Moves the given tile up as far as possible, subject to the minR constraint.
     *
     * @param board the current state of the board
     * @param r     the row number of the tile to move up
     * @param c -   the column number of the tile to move up
     * @param minR  the minimum row number that the tile can land in, e.g.
     *              if minR is 2, the moving tile should move no higher than row 2.
     * @return      if there is a merge, returns the 1 + the row number where the merge occurred.
     *              if no merge occurs, then return 0.
     */
    public static int moveTileUpAsFarAsPossible(int[][] board, int r, int c, int minR) {
        // TODO: Fill this in in tasks 2, 3, 4
        while( r >= minR+1 && board[r - 1][c] == 0) {      //minR的限制
                board[r - 1][c] = board[r][c];
                board[r][c] = 0;
                r -= 1;
        }
        /* 移动格子，使其尽可能向上 */

        if(r >= minR+1 && board[r][c] == board[r - 1][c]){  //minR的限制
            board[r - 1][c] *= 2 ;
            board[r][c] = 0;
            return r;
        }else{
            return 0;
        }
        /* 进行合并 */

    }

    /**
     * Modifies the board to simulate the process of tilting column c
     * upwards.
     *
     * @param board     the current state of the board
     * @param c         the column to tilt up.
     */
    public static void tiltColumn(int[][] board, int c) {
        // TODO: fill this in in task 5
        int[] minR = new int[100]; //弄个数组存放已经发生合并的行
        minR[0] = 0;//初始化minR为0
        for(int r = 1;r <=board.length -1;r+=1) {//从上向下移动与合并
            minR[r] = moveTileUpAsFarAsPossible(board, r, c, minR[0]);
            for(int i =1;i<=r;i+=1) {//让minR取数组中的最大值，防止二次合并
                if(minR[i]>minR[0]){
                    minR[0] = minR[i];
                }
            }
        }
        return;

    }

    /**
     * Modifies the board to simulate tilting all columns upwards.
     *
     * @param board     the current state of the board.
     */
    public static void tiltUp(int[][] board) {
        // TODO: fill this in in task 6
        for(int c = 0; c <= board.length - 1 ; c+=1) {
            tiltColumn(board, c);
        }
        /* 对每一列调用函数 */
        return;
    }

    /**
     * Modifies the board to simulate tilting the entire board to
     * the given side.
     *
     * @param board the current state of the board
     * @param side  the direction to tilt
     */
    public static void tilt(int[][] board, Side side) {
        // TODO: fill this in in task 7
        if (side == Side.EAST) {
            MatrixUtils.rotateLeft(board);
            tiltUp(board);
            MatrixUtils.rotateRight(board);
            return;
        } else if (side == Side.WEST) {
            MatrixUtils.rotateRight(board);
            tiltUp(board);
            MatrixUtils.rotateLeft(board);
            return;
        } else if (side == Side.SOUTH) {
            MatrixUtils.rotateLeft(board);
            MatrixUtils.rotateLeft(board);
            tiltUp(board);
            MatrixUtils.rotateRight(board);
            MatrixUtils.rotateRight(board);
            return;
        } else {
            tiltUp(board);
            return;
        }
        //将棋盘旋转，再调用函数
    }
}
