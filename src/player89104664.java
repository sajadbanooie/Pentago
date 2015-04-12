import Pentago.board;
import Pentago.move;
import Pentago.player;
import java.util.ArrayList;
import java.util.HashMap;

public class player89104664 implements player
{
	private HashMap<String, Integer> lookup_table;
	private String name;
	private int color;

	player89104664(String _name)
	{
		name = _name;
		lookup_table = new HashMap<String, Integer>();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setColor(int _color) {
		color = _color;
	}

	@Override
	public move getMove(board b) {
		AlphaBetaSearch x = new AlphaBetaSearch(b, color, lookup_table);
		return x.get_best_move();
	}


	private class State{
		private int color, depth;
		private board board;
		private ArrayList<State> successors;
		private move incoming_action;
		private HashMap<String, Integer> lookup_table;

		public State(int color, board _board, int depth, move incoming_action, HashMap lookup_table){
			this.board = _board;
			this.depth = depth;
			this.incoming_action = incoming_action;
			this.color = color;
			this.lookup_table = lookup_table;
		}

		public ArrayList <State> get_successors(){
			move temp;
			if (successors==null){
				successors = new ArrayList<State>();
				for(int r = 0; r < board.BoardSize; r++)
					for(int c = 0; c < board.BoardSize; c++)
						if(board.get(r, c) == 0)
							for (int sub_board = 0; sub_board < 4; sub_board++) {
								temp = new move(r, c, sub_board, true);
								successors.add(new State(color, board.nextMove(temp, color), depth+1, temp, lookup_table));
								temp = new move(r, c, sub_board, false);
								successors.add(new State(color, board.nextMove(temp, color), depth+1, temp, lookup_table));
							}

			}
//			System.out.println(successors.size());
			return successors;
		}

		public boolean cutoff_test(){
			if (board.winner()!=0 || this.depth==2)
				return true;
			return false;
		}

		public int get_eval(){
			if (lookup_table.containsKey(this.state_to_str()))
				return lookup_table.get(this.state_to_str());
			int opponent_color = (color==1) ? 2 : 1;
			int res = count_in_a_row(5, color)*1000000
					+ count_in_a_row(4, color)*1000
					+ count_in_a_row(3, color)*100
					+ count_in_center(color)*5
					- count_in_a_row(5, opponent_color)*1000000
					- count_in_a_row(4, opponent_color)*1000
					- count_in_a_row(3, opponent_color)*100
					- count_in_center(opponent_color)*5;

			lookup_table.put(this.state_to_str(), res);
			return res;
		}

		public move get_incoming_action(){
			return incoming_action;
		}

		private int count_in_center(int color) {
			int res = 0;
			if (board.get(1,1)==color) res++;
			if (board.get(1,4)==color) res++;
			if (board.get(4,1)==color) res++;
			if (board.get(4,4)==color) res++;
			return res;
		}

		private int count_in_a_row(int length, int _color){
			int res = 0;
			boolean flag = true;
			// rows
			for (int r = 0; r < board.BoardSize; r++) {
				for (int c = 0; c <= board.BoardSize-length; c++) {
					flag = true;
					for (int i = 0; i < length; i++)
						if (board.get(r,c+i)!=_color){
							flag = false;
							break;
						}
					if (flag)
						res++;
				}
			}
			// cols
			for (int c = 0; c < board.BoardSize; c++) {
				for (int r = 0; r <= board.BoardSize-length; r++) {
					flag = true;
					for (int i = 0; i < length; i++)
						if (board.get(r+i,c)!=_color){
							flag = false;
							break;
						}
					if (flag)
						res++;
				}
			}
			// diagonals
			for (int r = 0; r <= board.BoardSize-length; r++) {
				for (int c = 0; c <= board.BoardSize-length; c++) {
					flag = true;
					for (int i = 0; i < length; i++)
						if (board.get(c+i,r+i)!=_color){
							flag = false;
							break;
						}
					if (flag)
						res++;
				}
			}
			for (int r = length-1; r < board.BoardSize; r++) {
				for (int c = length-1; c < board.BoardSize; c++) {
					flag = true;
					for (int i = 0; i < length; i++)
						if (board.get(c-i,r-i)!=_color){
							flag = false;
							break;
						}
					if (flag)
						res++;
				}
			}
			return res;
		}

		public String state_to_str() {
			String res = "";
			for (int r = 0; r < board.BoardSize; r++)
				for (int c = 0; c < board.BoardSize; c++)
					res += String.valueOf(board.get(r, c));
			return res;
		}
	}


	private class AlphaBetaSearch{
		private State root;
		private move best_move;

		public move get_best_move(){
			return best_move;
		}

		public AlphaBetaSearch(board _board, int color, HashMap lookup_table) {
			root = new State(color, _board, 0, null, lookup_table);
			int v = max_value(this.root, Integer.MIN_VALUE, Integer.MAX_VALUE);
			for(State s : root.get_successors())
				if (s.get_eval()==v){
					best_move = s.get_incoming_action();
					break;
				}
		}

		private int max_value(State state, int alpha, int beta){
			if (state.cutoff_test())
				return state.get_eval();
			int v = Integer.MIN_VALUE;
			for (State s : state.get_successors()){
				v = Math.max(v, min_value(s, alpha, beta));
				if (v>=beta) return v;
				alpha = Math.max(alpha, v);
			}
			return v;
		}

		private int min_value(State state, int alpha, int beta){
			if (state.cutoff_test())
				return state.get_eval();
			int v = Integer.MAX_VALUE;
			for (State s : state.get_successors()){
				v = Math.min(v, max_value(s, alpha, beta));
				if (v<=alpha) return v;
				beta = Math.min(beta, v);
			}
			return v;
		}

	}

}
