package game ;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Client {  

    private ServerSocket server = null;
    private Socket socket1 = null; 
    private Socket socket2 = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private Integer ID = null;
    private Board board = null ;
    private Scanner sc = new Scanner(System.in);


    /*
     * server socket allows this player to accept connection from other player
     * socket1 is the accepted connection from server socket
     * socket2 is the socket that connects to the server of the other player
     * NOTE: Socket(HOST, port) throws ConnectException if no server is listening on port
     *       handle this while creating socket2
     *       Since server.accept is blocking, so order in which sockets are initialised must be considered appropriately.
     */


    /*
     * TODO: Add any variables you need
     */

    private void printResult(int winner, int ID) {
        /*
         * Don't change this function
         */
        if(winner == 0) {
            System.out.println("DRAW") ;
        } 
        else if(winner == ID) {
            System.out.println("YOU WON") ;
        }
        else System.out.println("YOU LOSE") ;
    }

    private Integer[] takeInput(){
        /*
         * TODO: Take input from the user from STDIN and return the position as an array
         * 
         */
        System.out.print("Enter the position: ");
        /* take x and y as space separated integer*/
        Integer input[] = new Integer[2];
		String inp = sc.nextLine();
		try{
			input[0] = Integer.parseInt(inp.split(" ")[0]);
			input[1] = Integer.parseInt(inp.split(" ")[1]);
		} catch(Exception e){
			input[0] = -1;
			input[1] = -1;
		}
        /* check if the x and y position is valid and available on the board [x,y in {0,1,2}X{0,1,2}]
         * if not valid or not available, print "INVALID INPUT! Enter again" (without quotes) and ask for input again
        */
        if(input[0]>=0 && input[1]<=2 && input[0]<=2 && input[1]>=0 && board.available(input[0],input[1]))    return input;
        System.out.println("INVALID INPUT! Enter again");
        return takeInput();
    }
    private void sendMove(Integer[] pos){
        /*
         * TODO: Send the move to other player by writing to the appropriate socket
         * sent string format: "x y" (space separated)
         */
        String move = pos[0] + " " + pos[1];
        out.println(move);
        return;
    }

    private Integer[] recieveMove(){
        /*
         * TODO: Recieve the move from the server by reading from the appropriate socket
         * return the move as an array of two integers
         * recieve format: "x y" (space separated)
         */
        Integer ans[] = new Integer[2];
        try{
            String move = in.readLine();
            ans[0] = (move.charAt(0))-'0';
            ans[1] = (move.charAt(2))-'0';
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
        return ans;
    }
    public Client(Integer ID, Integer port1, Integer port2){
        /*
        * port1 is the port on which server is running and 
        * this player will accept connection from other player
        * 
        * port2 is the port on which the server of the other player is running
        */     

        /*
        * TODO: Initialize ID
        * Also initialize the board
        */
        this.ID = ID;
        board = new Board();

        try{
            /*
            * INITIALIZE THE ServerSocket 
            */
            server = new ServerSocket(port1);


            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
            /* Don't chnage above print statements */


            /*
            * INITIALIZE both socket variables, one that connects to other player and other accepting connection on server socket
            * NOTE: Socket(HOST, port) throws java.net.ConnectException if no server is listening on port
            * this happens when the other player is not yet ready to play i.e. has not started the server

            * handle this by trying to create the socket conneting to other player again and again in a loop until it is created
            * incase of exception just continue the loop
            */
            Boolean connected = true;
            try{
                InetAddress inetAddress=InetAddress.getByName("localhost");
                socket2 = new Socket(inetAddress,port2);
            }catch(ConnectException e){
                connected = false;
            }
            socket1 = server.accept();
            while(!connected){
                try{
                    InetAddress inetAddress=InetAddress.getByName("localhost");
                    socket2 = new Socket(inetAddress,port2);
                    connected = true;
                }catch(ConnectException e){
                    connected = false;
                }
            }
            
            /*
             * initialize the input and output streams appropriately
             */
            in = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
            out = new PrintWriter(socket1.getOutputStream(),true);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void runClient () {
        Boolean myturn = ID==1;
        while(true) {
                /*
                * Here we would have the game logic
                * You can use the functions defined above
                * NOTE: for player with ID 1, it is your turn initially
                *       for player with ID 2, it is the other player's turn initially
                * We have implemented the game using a logic which utilizes myturn bool. You are welcome to try any other logic.
                */
            int result = board.checkBoard();
            if(result != -1){
                printResult(result,ID);
                break;
            }
            if(myturn) {
                /* your turn */
                Integer move[] = takeInput();
                sendMove(move);
                board.updateBoard(move,ID);
            }
            else {
                /* other player's turn */
                Integer move[] = recieveMove();
                board.updateBoard(move,3-ID);
            }
            myturn = !myturn;
        }
    }

    public static void main(String args[]) {
		// Main Function
		Integer ID;
		Integer port1;
		Integer port2;
		try{
			ID = Integer.parseInt(args[0]) ;
			port1 = Integer.parseInt(args[1]) ;
			port2 = Integer.parseInt(args[2]) ;
			if (port1.equals(port2)){
				System.err.println("Please enter different port numbers");
				throw new Exception("Invalid Ports");
			}
		}catch(Exception e){
			System.err.print("Give the following command line arguments:\n <Player ID> <Port 1> <Port 2> \n Where \n 1. Player ID is either 1 or 2 \n 2. Port 1 is the port to start server for the player \n 3. Port 2 is the port on which other player's server is started.\n");
			e.printStackTrace();
			return;
		}
        System.out.println(ID);
        if(ID != 1 && ID != 2) {
            System.out.println("Incorrect Player ID\n");
            return ;
        }
        try {
            Client client = new Client(ID, port1, port2) ;
            client.runClient() ;
        } catch (Exception e) {
			System.err.println("Unable to run the client");
            e.printStackTrace();
        }
    }
}
