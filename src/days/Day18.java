package days;

import java.io.*;
import java.util.*;

public class Day18 {
	private static char[][] map;

	public static void main(String args[]) {
		long bla = 1000000000 % 700000;
		System.out.println(bla);

		map = readInput();
		printMap();
		for (int i = 1; i <= 1000000000; i++) {
			evolve();
			if (i % 100000 == 0) {
				int trees = getCount('|');
				int lum = getCount('#');
				System.out.println(i + ": totalVal: " + trees * lum);
			}
		}
		printMap();

		int trees = getCount('|');
		int lum = getCount('#');
		System.out.println("totalVal: " + trees * lum);

	}

	private static int getCount(char c) {
		int count = 0;
		for (int y = 0; y < map.length; y++)
			for (int x = 0; x < map[0].length; x++)
				if (map[y][x] == c)
					count++;
		return count;
	}

	private static void evolve() {
		char[][] newMap = new char[map.length][map[0].length];
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				boolean changed = false;
				switch (map[y][x]) {
				case '.':
					if (getAdjacentCount(x, y, '|', 3) >= 3) {
						newMap[y][x] = '|';
						changed = true;
					}
					break;
				case '#':
					int lum = getAdjacentCount(x, y, '#', 1);
					int trees = getAdjacentCount(x, y, '|', 1);
					if (lum == 0 || trees == 0) {
						newMap[y][x] = '.';
						changed = true;
					}
					break;
				case '|':
					if (getAdjacentCount(x, y, '#', 3) >= 3) {
						newMap[y][x] = '#';
						changed = true;
					}
				}
				if (!changed)
					newMap[y][x] = map[y][x];
			}
		}
		map = newMap;

	}

	private static int getAdjacentCount(int x, int y, char c, int needed) {
		int count = 0;
		if (x - 1 >= 0 && map[y][x - 1] == c)
			count++;
		if (count >= needed)
			return count;
		if (x + 1 < map[0].length && map[y][x + 1] == c)
			count++;
		if (count >= needed)
			return count;
		if (y - 1 >= 0 && map[y - 1][x] == c)
			count++;
		if (count >= needed)
			return count;
		if (y + 1 < map.length && map[y + 1][x] == c)
			count++;
		if (count >= needed)
			return count;
		if (x - 1 >= 0 && y - 1 >= 0 && map[y - 1][x - 1] == c)
			count++;
		if (count >= needed)
			return count;
		if (x - 1 >= 0 && y + 1 < map.length && map[y + 1][x - 1] == c)
			count++;
		if (count >= needed)
			return count;
		if (x + 1 < map[0].length && y - 1 >= 0 && map[y - 1][x + 1] == c)
			count++;
		if (count >= needed)
			return count;
		if (x + 1 < map[0].length && y + 1 < map.length && map[y + 1][x + 1] == c)
			count++;
		return count;
	}

	private static void printMap() {
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++)
				System.out.print(map[y][x]);
			System.out.println();
		}
	}

	private static char[][] readInput() {
		BufferedReader br;
		ArrayList<String> data = new ArrayList<String>();

		try {
			br = new BufferedReader(new FileReader("src/input/inputday18"));
			String line = br.readLine();
			while (line != null) {
				data.add(line);
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		char[][] map = new char[data.size()][data.get(0).length()];
		for (int y = 0; y < map.length; y++) {
			String line = data.get(y);
			for (int x = 0; x < map.length; x++) {
				map[y][x] = line.charAt(x);
			}
		}

		return map;
	}
}
