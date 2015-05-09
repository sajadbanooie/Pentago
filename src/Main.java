import Pentago.game;
import Pentago.player;

public class Main {
	public static void main(String[] args) {
		player p1 = new samplePlayer("Pentago.player 1");
		player p2 = new samplePlayer("Pentago.player 2");
		game g = new game(p1, p2);
		g.start();
	}
}
