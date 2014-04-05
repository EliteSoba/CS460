import java.awt.Point;
import java.util.ArrayList;


public class Board {

	public static char[][] clone(char[][] arg0) {
		char[][] temp = new char[arg0.length][arg0[0].length];
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp[i].length; j++)
				temp[i][j] = arg0[i][j];
		}
		return temp;
	}

	private class Node {
		int x, y;
		char ID;
		public Node(int x, int y, char ID) {
			this.x = x;
			this.y = y;
			this.ID = ID;
		}
	}

	Node[][] nodes;
	char[][] board;
	ArrayList<Node> black = new ArrayList<Node>(), white = new ArrayList<Node>();

	public Board(char[][] board) {
		this.board = board;
		nodes = new Node[6][3];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				nodes[i][j] = new Node(i, j, board[i][j]);
				if (board[i][j] == 'B')
					black.add(nodes[i][j]);
				if (board[i][j] == 'W')
					white.add(nodes[i][j]);
			}
		}
	}

	//We ignore the possibility of going past the board for now because we assume that if we're in the last square, the game ends
	public ArrayList<char[][]> blackMove() {
		ArrayList<char[][]> states = new ArrayList<char[][]>();

		for (Node n : black) {
			for (int i = -1; i <= 1; i++) {
				if (n.y + i < 0 || n.y + i >= 3)
					continue;
				if (board[n.x+1][n.y+i] == 'B')
					continue;
				if (i == 0 &&board[n.x+1][n.y] == 'W')
					continue;
				char[][] temp = clone(board);
				temp[n.x][n.y] = 'X';
				temp[n.x+1][n.y+i] = 'B';
				states.add(temp);
			}
		}

		return states;
	}

	public ArrayList<char[][]> whiteMove() {
		ArrayList<char[][]> states = new ArrayList<char[][]>();

		for (Node n : white) {
			for (int i = -1; i <= 1; i++) {
				if (n.y + i < 0 || n.y + i >= 3)
					continue;
				if (board[n.x-1][n.y+i] == 'W')
					continue;
				if (i == 0 &&board[n.x-1][n.y] == 'B')
					continue;
				char[][] temp = clone(board);
				temp[n.x][n.y] = 'X';
				temp[n.x-1][n.y+i] = 'W';
				states.add(temp);
			}
		}

		return states;
	}

	public String describeMove(char[][] other, boolean isWhite) {
		boolean first = true;
		Point temp = null;
		String s = "";
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] != other[i][j]) {
					if (first) {
						first = false;
						temp = new Point(i, j);
					}
					else {
						if (!isWhite) {
						s = "(" + temp.x + "," + temp.y + ")";
						s += " to ";
						s += "(" + i + "," + j + ")";
						}
						else {
							s = "(" + i + "," + j + ")";
							s += " to ";
							s += "(" + temp.x + "," + temp.y + ")";
						}
					}
				}
			}
		}
		
		return s;
	}

}
