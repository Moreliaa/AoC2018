package days;

import java.io.*;
import java.util.*;

public class Day15 {
	private static Character[][] input;

	public static void main(String[] args) {
		input = readInput();
		ArrayList<Dude> dudes = collectDudes();
		printMap();
		while (true) {
			boolean finished = step(dudes);
			if (finished)
				break;
		}

	}

	private static boolean step(ArrayList<Dude> dudes) {
		orderDudes(dudes);
		for (Dude d : dudes) {
			// takeaction
		}
		return false;
	}

	private static class Dude {
		char type;
		int xPos, yPos;
		char top, left, bottom, right;
		int hp = 200, atk = 3;

		public Dude(char type, int xPos, int yPos) {
			this.type = type;
			this.xPos = xPos;
			this.yPos = yPos;
		}

		public void updateAdjacentSpots() {
			top = input[yPos - 1][xPos];
			left = input[yPos][xPos - 1];
			bottom = input[yPos + 1][xPos];
			right = input[yPos][xPos + 1];
		}

	}

	private static void orderDudes(ArrayList<Dude> dudes) {
		Collections.sort(dudes, new DudeComparator());
	}

	private static class DudeComparator implements Comparator<Dude> {
		public int compare(Dude c1, Dude c2) {
			if (c1.yPos < c2.yPos)
				return -1;
			else if (c1.yPos == c2.yPos && c1.xPos < c2.xPos)
				return -1;
			else if (c1.yPos == c2.yPos && c1.xPos == c2.xPos)
				return 0;
			else
				return 1;
		}
	}

	private static ArrayList<Dude> collectDudes() {
		ArrayList<Dude> dudes = new ArrayList<Dude>();
		for (int y = 0; y < input.length; y++) {
			for (int x = 0; x < input[0].length; x++) {
				if (input[y][x] == 'G' || input[y][x] == 'E')
					dudes.add(new Dude(input[y][x], x, y));
			}
		}
		return dudes;
	}

	private static void printMap() {
		for (int y = 0; y < input.length; y++) {
			for (int x = 0; x < input[0].length; x++)
				System.out.print(input[y][x]);
			System.out.println();
		}
	}

	private static Character[][] readInput() {
		BufferedReader br;
		ArrayList<String> data = new ArrayList<String>();

		try {
			br = new BufferedReader(new FileReader("src/input/inputday15"));
			String line = br.readLine();
			while (line != null) {
				data.add(line);
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Character[][] map = new Character[data.size()][data.get(0).length()];
		for (int y = 0; y < map.length; y++) {
			String line = data.get(y);
			for (int x = 0; x < map[0].length; x++)
				map[y][x] = line.charAt(x);
		}

		return map;
	}

}
