/**
 * Caden Dowd 
 * NetId: cdowd4
 * Lab: MW 3:25-4:40
 */
import java.util.Random;
import java.util.Scanner;

/**
 * 
 * @author Caden
 * Game class that runs 2048 game. 
 *
 */
public class Game {
	//defines instance variables
	private static int[][] board;
	private static int validMoves = 0;
	
	public static void main(String[] args) {
		String input = "";
		Scanner scan = new Scanner(System.in); 
		//play game
		//while loop to run game and asks to play again
		do {
			input = "";
			newBoard();
			boolean restart = false;
			boolean gameOver = false;
			//while loop to update board and moves
			do {
				String input2 = "";
				clear();
				printTitle();
				printBoard();
				input = scan.nextLine();
				if(!move(1, true) && !move(2, true) && !move(3,true) && !move(4,true)) { //checks if there are any valid moves left
					gameOver = true;
					break;
				}
				if(input.equals("w")) {
					if(move(1, false)) {
						addNum();
						validMoves++;
						System.out.println("Valid Move!");
					} else {
						System.out.println("Invalid Move!");
					}
				} else if(input.equals("s")) {
					if(move(2,false)) {
						addNum();
						validMoves++;
						System.out.println("Valid Move!");
					} else {
						System.out.println("Invalid Move!");
					}
				} else if(input.equals("a")) {
					if(move(3,false)) {
						addNum();
						validMoves++;
						System.out.println("Valid Move!");
					} else {
						System.out.println("Invalid Move!");
					}
				} else if(input.equals("d")) {
					if(move(4,false)) {
						addNum();
						validMoves++;
						System.out.println("Valid Move!");
					} else {
						System.out.println("Invalid Move!");
					}
				} else if(input.equals("q")){
					System.out.println("press y to confirm");
					input2 = scan.nextLine();
					if(input2.equals("y")) {
						gameOver = true;
					} else {
						System.out.println("the game continues...");
					}
				} else if(input.equals("r")){
					System.out.println("press y to confirm");
					input2 = scan.nextLine();
					if(input2.equals("y")) {
						restart = true;
						break;
					} else {
						System.out.println("the game continues...");
					}
				} else {
					System.out.println("Unknown input. try again!");
					scan.nextLine();
				}
			} while (!gameOver);
			//checks if game has been restarted
			if(!restart) {
				clear();
				System.out.println("GAME OVER!");
				System.out.println("Max Value: " + findMax() + "\tValid Moves: " + validMoves);
				System.out.print("Want to play again? [yes/no]  ");
				input = scan.nextLine();
			} else {
				input = "yes";
			}
			validMoves = 0;
			restart = false;
		} while (input.equals("yes"));
		System.out.println("That's all folks!");
		scan.close();
	}
	
	//prints Title
	public static void printTitle() {
		System.out.println("Caden's 2048 Game");
		System.out.println("-*-*-*-*-*-*-*-*-*-\n");
	}
	
	//prints Instructions
	public static void printInstructions() {
		System.out.println("Type q to quit");
		System.out.println("Type r to restart");
		System.out.println("Type w/s/a/d to slide up/down/left/right");
		System.out.print("Input: ");
	}
	
	//clears the screen for new board
	public static void clear() {
		for(int i = 0; i < 30; i++) {
			System.out.println("");
		}
	}
	
	//generates a random number either 2 or 4
	public static int randomNumGen() {
		Random rand = new Random();
		double n = rand.nextDouble();
		if(n >= .8) {
			return 4;
		} else {
			return 2;
		}
	}
	
	//generates a random index between 0 and 4
	public static int randomNumFour() {
		Random rand = new Random();
		int one = rand.nextInt(4);
		return one;
	}
	
	//adds the a new number in a random open spot on the board
	public static void addNum() {
		int x1, y1;
		do {
			x1 = randomNumFour();
			y1 = randomNumFour();
		} while (board[x1][y1] != 0);
		
		int val = randomNumGen();
		board[x1][y1] = val;
	}
	
	//creates a new 4x4 game board
	public static int[][] newBoard(){
		board = new int[4][4];
		int x1;
		int y1;
		int x2;
		int y2;
		Random rand = new Random();
		do {
			x1 = rand.nextInt(4);
			y1 = rand.nextInt(4);
			x2 = rand.nextInt(4);
			y2 = rand.nextInt(4);
		} while(x1 == x2 && y1 == y2);
		board[x1][y1] = randomNumGen();
		board[x2][y2] = randomNumGen();
		return board;
	}
	
	//prints the current board
	public static void printBoard() {
		System.out.println("Max Value: " + findMax() + "\tValid Moves: " + validMoves);
		System.out.println("- - - - - - - - - - - - - - - - -");
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; j++) {
				if(j == board.length-1) {
					System.out.print("| " + board[i][j] + "\t|");
				} else {
					System.out.print("| " + board[i][j] + "\t");
				}
			}
			System.out.println();
		}
		System.out.println("- - - - - - - - - - - - - - - - -");
		printInstructions();
	}
	
	//finds the largest number in the 4x4 board
	public static int findMax() {
		int max = -1;
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				if(board[i][j] > max) {
					max = board[i][j];
				}
			}
		}
		return max;
	}
	
	/**
	 * 
	 * @param direction 	the direction that the user chooses to change the board
	 * 1 -> Up
	 * 2 -> Down
	 * 3 -> Left
	 * 4 -> Right
	 * @param gameOverCheck 	a boolean that checks if there are any valid moves but does not change the board 
	 * @return boolean 		returns a boolean that checks whether there are any valid moves or not
	 */
	
	public static boolean move(int direction, boolean gameOverCheck) {
		if(direction == 1) {
			int numMoves = 0;
			for(int i = 0; i < 4; i++) {
				//fixes zero position
				for(int y = 3; y >= 0; y--) {
					if(y - 1 >= 0 && board[y-1][i] == 0 && board[y][i] != 0) {
						if(!gameOverCheck) {
							board[y-1][i] = board[y][i];
							board[y][i] = 0;
						}
						numMoves++;
					}
				}
				//merges the values
				for(int j = 0; j < 4; j++) {
					if(j + 1 < 4 && board[j][i] == board[j+1][i] && board[j][i] != 0 && board[j+1][i] != 0) {
						if(!gameOverCheck) {
							board[j][i] += board[j+1][i];
							board[j+1][i] = 0;
						}
						numMoves++;
					}
				}
				//re-fixes the zero positions
				for(int x = 1; x < 4; x++) {
					for(int y = 3; y >= 0; y--) {
						if(y-1 >= 0 && board[y-1][i] == 0 && board[y][i] != 0) {
							if(!gameOverCheck) {
								board[y-1][i] = board[y][i];
								board[y][i] = 0;
							}
							numMoves++;
						}
					}
				}
			}
			//checks if moved or not
			if(numMoves == 0) {
				return false;
			}
		} else if (direction == 2) {
			int numMoves = 0;
			for(int i = 0; i < 4; i++) {
				//fixes zero position
				for(int x = 1; x <= 4; x++) {
					for(int y = 0; y < 4; y++) {
						if(y+1 < 4 && board[y+1][i] == 0 && board[y][i] != 0) {
							if(!gameOverCheck) {
								board[y+1][i] = board[y][i];
								board[y][i] = 0; 
							}
							numMoves++;
						}
					}
				}
				//merges the values
				for(int j = 3; j >= 0; j--) {
					if(j-1 >= 0 && board[j][i] == board[j-1][i] && board[j][i] != 0 && board[j-1][i] != 0) {
						if(!gameOverCheck) {
							board[j-1][i] += board[j][i];
							board[j][i] = 0;
							//score
						}
						numMoves++;
					}
				}
				//re-fixes zero positions
				for(int x = 1; x <= 4; x++) {
					for(int y = 0; y < 4; y++) {
						if(y+1 < 4 && board[y+1][i] == 0 && board[y][i] != 0) {
							if(!gameOverCheck) {
								board[y+1][i] = board[y][i];
								board[y][i] = 0;
							}
							numMoves++;
						}
					}
				}
			}
			//checks if moved or not
			if(numMoves == 0) {
					return false;
				}
		} else if (direction == 3) {
			int numMoves = 0;
			for(int i = 0; i < 4; i++) {
				//fixes zero position
				for(int x = 1; x <= 4; x++) {
					for(int j = 3; j >= 0; j--) {
						if(j-1 >= 0 && board[i][j-1] == 0 && board[i][j] != 0) {
							if(!gameOverCheck) {
								board[i][j-1] = board[i][j];
								board[i][j] = 0;
							}
							numMoves++;	
						}
					}
				}
				//merges the values
				for(int j = 0; j < 4; j++) {
					if(j+1 < 4 && board[i][j+1] == board[i][j] && board[i][j] != 0 && board[i][j+1] != 0) {
						if(!gameOverCheck) {
							board[i][j] += board[i][j+1];
							board[i][j+1] = 0;
							//score
						}
						numMoves++;
					}
				}
				//re-fixes the zero positions
				for(int x = 1; x <= 4; x++) {
					for(int j = 3; j>= 0; j--) {
						if(j-1 >= 0 && board[i][j-1] == 0 && board[i][j] != 0) {
							if(!gameOverCheck) {
								board[i][j-1] = board[i][j];
								board[i][j] = 0;
							}
							numMoves++;
						}
					}
				}
			}
			//checks if moved or not
			if(numMoves == 0) {
				return false;
			}
		} else if(direction == 4) {
			int numMoves = 0;
			for(int i = 0; i < 4; i++) {
				//fixes zero position
				for(int x = 1; x <= 4; x++) {
					for (int j = 0; j < 4; j++) {
						if(j+1 < 4 && board[i][j+1] == 0 && board[i][j] != 0) {
							if(!gameOverCheck) {
								board[i][j+1] = board[i][j];
								board[i][j] = 0;
							}
							numMoves++;
						}
					}
				}
				//merges the values
				for(int j = 3; j >= 0; j--) {
					if(j-1 >= 0 && board[i][j-1] == board[i][j] && board[i][j] != 0 && board[i][j-1] != 0) {
						if(!gameOverCheck) {
							board[i][j] += board[i][j-1];
							board[i][j-1] = 0;
							//score
						}
						numMoves++;
					}
				}
				//re-fixes the zero positions
				for(int x = 1; x <= 4; x++) {
					for(int j = 0; j < 4; j++) {
						if(j+1 < 4 && board[i][j+1] == 0 && board[i][j] != 0) {
							if(!gameOverCheck) {
								board[i][j+1] = board[i][j];
								board[i][j] = 0;
							}
							numMoves++;
						}
					} 
				}
			}
			//checks if moved or not
			if(numMoves == 0) {
				return false;
			}
		}
		return true;
	}
}
