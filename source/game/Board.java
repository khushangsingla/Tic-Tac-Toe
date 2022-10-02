package game ;

public class Board {
    private int [][] board = new int[3][3];
    /* elements of board is either 0, 1 or 2 
     * 0 means empty
     * 1 means player 1's token  (say X)
     * 2 means player 2's token  (say O)
     */

    public void printBoard(){
        /*
         * Don't change this function
         */
        System.out.println("Board:");
        System.out.println("-------------");
        for(int i=0;i<3;i++){
            System.out.print("| ");
            for(int j=0;j<3;j++){
                if(board[i][j]==0){
                    System.out.print(" ");
                }
                else if(board[i][j]==1){
                    System.out.print("X");
                }
                else if(board[i][j]==2){
                    System.out.print("O");
                }
                System.out.print(" | ");
            }
            System.out.println("\n-------------");   
        }
    }

    public Boolean available(Integer x, Integer y){
        /*
         * TODO: Check if the position (x,y) is available
         * return true if available. 
         * Also return false if (x,y) is not a valid position
         */
        if(board[x][y] == 0)    return true;
        return false;
    }


    public void updateBoard(Integer[] pos, Integer id){
        /*
         * TODO: Update the board 
         */
        board[pos[0]][pos[1]] = id;

    }

    // create any helper functions you need

    

    public int checkBoard() {

        printBoard();
        /*
         * Don't remove the above line
         */

        // EDIT BELOW THIS LINE
        /*
         * TODO: Check the board and return the status of the game
         * -1 if Game has Not yet Ended
         * 0 if Game has Ended in a Draw
         * 1 if Player 1 has Won
         * 2 if Player 2 has Won
         */
        for(int i=0;i<3;i++){
            if(board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[1][i]!=0)   return board[0][i];
            if(board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][1]!=0)   return board[i][0];
        }
        Boolean is_win = true;
        for(int i=0;i<2;i++){
            if(!(board[i][i] == board[i+1][i+1] && board[i][i] != 0)){
                is_win = false;
            }
        }
        if(is_win)  return board[1][1];
        is_win = true;
        for(int i=0;i<2;i++){
            if(!(board[2-i][i] == board[1-i][i+1] && board[i][i] != 0)){
                is_win = false;
            }
        }
        if(is_win)  return board[1][1];
        Boolean is_draw = true;
        for(int i=0;i<9;i++){
            if(board[i%3][i/3] == 0){
                is_draw = false;
                break;
            }
        }
        if(is_draw) return 0;
        return -1;
    }
}
