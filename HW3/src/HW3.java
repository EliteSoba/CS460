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
	public static Scanner kbr = new Scanner(System.in);
	public static String themove = "";
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
		if (!ADVANCED_HEURISTIC) {
			if (whiteCount == 0)
				return -1;
			else if (blackCount == 0)
				return 1;
		}
		if (ADVANCED_HEURISTIC) {
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
		System.out.println();
	}

	public static int maxValue(Board b, int alpha, int beta, boolean first) {
		//if (ADVANCED_HEURISTIC && (getHeuristic(b.board) < -10000 || getHeuristic(b.board) > 10000) || !ADVANCED_HEURISTIC && getHeuristic(b.board) != 0)
		if (getHeuristic(b.board) != 0)
			return getHeuristic(b.board);

		//System.out.println(getHeuristic(b.board));
		ArrayList<char[][]> moves = b.whiteMove();
		//for (char[][] s : b.whiteMove()) {
		while (!moves.isEmpty()) {
			char[][] s = moves.remove(0);
			//print2DArray(s);
			//kbr.nextLine();
			System.out.println("Player A moves the piece at " + b.describeMove(s, true) + ".");
			int i = minValue(new Board(s), alpha, beta, false);
			if (alpha < i) {
				if (first) {
					themove = b.describeMove(s, true);
					System.out.println(themove);
				}
				alpha = i;

			}
			if (alpha >= beta) {
				if (moves.isEmpty())
					return alpha;
				System.out.print("Skipping Player A's moves: " + b.describeMove(moves.remove(0), true));
				while (!moves.isEmpty()) {
					System.out.print(", " + b.describeMove(moves.remove(0), true));
				}
				System.out.println("; Alpha = " + alpha + "; Beta = " + beta + ".");
				return alpha;
			}
		}
		return alpha;
	}

	public static int minValue(Board b, int alpha, int beta, boolean first) {
		//if (ADVANCED_HEURISTIC && (getHeuristic(b.board) < -10000 || getHeuristic(b.board) > 10000) || !ADVANCED_HEURISTIC && getHeuristic(b.board) != 0)
		if (getHeuristic(b.board) != 0)	
			return getHeuristic(b.board);

		//System.out.println(getHeuristic(b.board));
		ArrayList<char[][]> moves = b.blackMove();
		//for (char[][] s : b.blackMove()) {
		while (!moves.isEmpty()) {
			char[][] s = moves.remove(0);
			//print2DArray(s);
			//kbr.nextLine();
			System.out.println("Player B moves the piece at " + b.describeMove(s, false) + ".");
			beta = Math.min(beta, maxValue(new Board(s), alpha, beta, false));
			if (beta <= alpha) {
				if (moves.isEmpty())
					return beta;
				System.out.print("Skipping Player B's moves: " + b.describeMove(moves.remove(0), false));
				while (!moves.isEmpty()) {
					System.out.print(", " + b.describeMove(moves.remove(0), false));
				}
				System.out.println("; Alpha = " + alpha + "; Beta = " + beta + ".");
				return beta;
			}
		}

		return beta;
	}

	public static void main(String args[]) {
		readBoard("input.txt");

		Board b = new Board(board);
		print2DArray(board);
		maxValue(b, -99999999, 99999999, true);
		System.out.println("Answer: Player A moves the piece at " + themove + ".");
		/*ArrayList<char[][]> states = b.blackMove();

		for (char[][] i : states) {
			print2DArray(i);
			Board c = new Board(i);
			System.out.println(b.describeMove(i));
			//for (char[][]j : c.whiteMove()) {
			//	print2DArray(j);
				//System.out.println(getHeuristic(j));
			//}
			System.out.print(getHeuristic(i));
			System.out.println();
		}
		System.out.println(getHeuristic(board));
		 */
	}

}
