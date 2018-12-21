package days;

import java.util.*;

public class Day9 {
	private static int nextMarble = 1;
	private static int currentPlayer = 1;
	private static Marble currentMarble;
	private static Marble firstMarble;
	private static Marble lastMarble;
	private static HashMap<Integer, Long> scores = new HashMap<>(500);

	private static final int maxMarble = 7151000;
	private static final int numPlayers = 447;

	public static void main(String[] args) {
		String input = "447 players; last marble is worth 71510 points";

		currentMarble = new Marble(0);
		firstMarble = currentMarble;
		lastMarble = currentMarble;

		while (nextMarble <= maxMarble)
			addMarble();

		System.out.println(Collections.max(scores.values()));

	}

	private static void addMarble() {
		if (nextMarble % 23 == 0) {
			int score = nextMarble;
			moveCursor(-7);
			score += currentMarble.value;
			Marble toRemove = currentMarble;
			moveCursor(1);
			toRemove.remove();
			addScore(score);
		} else {
			moveCursor(1);
			currentMarble.addAfterThis(new Marble(nextMarble));
			moveCursor(1);
		}
		nextMarble++;
		getNextPlayer();
	}

	private static void addScore(long score) {
		if (!scores.containsKey(currentPlayer))
			scores.put(currentPlayer, score);
		else {
			long newScore = scores.get(currentPlayer) + score;
			scores.put(currentPlayer, newScore);
		}
	}

	private static void moveCursor(int offset) {
		while (offset < 0) {
			if (currentMarble.previous != null)
				currentMarble = currentMarble.previous;
			else
				currentMarble = lastMarble;
			offset++;
		}
		while (offset > 0) {
			if (currentMarble.next != null)
				currentMarble = currentMarble.next;
			else
				currentMarble = firstMarble;
			offset--;
		}
	}

	private static void getNextPlayer() {
		currentPlayer++;
		if (currentPlayer > numPlayers)
			currentPlayer = 1;
	}

	private static class Marble {
		int value;
		Marble previous = null, next = null;

		Marble(int val) {
			this.value = val;
		}

		void addAfterThis(Marble m) {
			if (this.next == null) {
				lastMarble = m;
				this.next = m;
				m.previous = this;
				m.next = null;
			} else {
				m.previous = this;
				m.next = this.next;
				this.next.previous = m;
				this.next = m;
			}
		}

		void remove() {
			this.previous.next = this.next;
			this.next.previous = this.previous;
		}
	}

}
