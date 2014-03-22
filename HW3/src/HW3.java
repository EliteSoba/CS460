import java.util.*;
import java.io.*;
public class HW3 {
	public static void Assert(boolean b) {
		if (b)
			return;
		System.err.println("Error: Assert Failed");
		Exception e = new Exception();
		e.printStackTrace();
		System.exit(1);
	}

	public static char[][] board = new char[6][3];
	public static final boolean ADVANCED_HEURISTIC = false;
	
	public static boolean readBoard(String filename) {
		Scanner sc;
		try {
			sc = new Scanner(new File(filename));
		}catch (Exception e) { return false; }
		
		String line;
		for (int i = 0; i < 6; i++) {
			line = sc.nextLine();
			for (int j = 0; j < 3; j++) {
				board[i][j] = line.charAt(j);
			}
		}
		sc.close();
		return true;
	}
	
	public static int getHeuristic(char[][] board){
		Assert(board != null);
		Assert(board.length == 6);
		Assert(board[0].length == 3);
		
		for (int i = 0; i < board[0].length; i++) {
			if (board[0][i] == 'W')
				return ADVANCED_HEURISTIC ? 100000:1;
			if (board[5][i] == 'B')
				return ADVANCED_HEURISTIC ? -100000:-1;
		}
		
		if (ADVANCED_HEURISTIC) {
			//Slightly more advanced heuristic because I dabble in chess and piece count is an okay way to tell if you're winning
			int blackCount = 0, whiteCount = 0;
			int blackPosition = 0, whitePosition = 0;
			int blackFurthest = 0, whiteFurthest = 0;
			
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					if (board[i][j] == 'B') {
						blackCount++;
						blackPosition += i;
						blackFurthest = Math.max(blackFurthest, i);
					}
					else if (board[i][j] == 'W') {
						whiteCount++;
						whitePosition += 5-i;
						whiteFurthest = Math.max(whiteFurthest, 5-i);
					}
				}
			}
			
			if (blackCount != whiteCount)
				return whiteCount - blackCount;
			
			//If piece count is even, then positioning is important
			//Whoever has moved more pieces forward should be winning
			
			if (blackPosition != whitePosition)
				return whitePosition - blackPosition;
			
			//If this is still tied, then might as well return who has pushed the farthest with a piece
			return whiteFurthest - blackFurthest;
		}
		
		return 0;
	}
	
	public static void print2DArray(char[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++)
				System.out.print(board[i][j]);
			System.out.println();
		}
	}
	
	public static void main(String args[]) {
		readBoard("input.txt");
		
		Board b = new Board(board);
		
		ArrayList<char[][]> states = b.blackMove();
		
		for (char[][] i : states) {
			//print2DArray(i);
			System.out.print(getHeuristic(i));
			System.out.println();
		}
		
		System.out.println(getHeuristic(board));
	}

}
