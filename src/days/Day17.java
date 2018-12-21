package days;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Day17 {
	private static ArrayList<String> horizontal = new ArrayList<String>();
	private static ArrayList<String> vertical = new ArrayList<String>();
	private static ArrayList<horiVein> horizontalVeins = new ArrayList<>();
	private static ArrayList<vertVein> verticalVeins = new ArrayList<>();
	private static char[][] map;
	private static int minX = 9999, minY = 9999, maxX = 500, maxY = 0;

	public static void main(String[] args) {
		ArrayList<String> input = readInput();
		splitInput(input);
		createVeins();
		createMap();
		flow(500, 0);
		int tilesReached = calcTiles(false);
		System.out.println(tilesReached);
		tilesReached = calcTiles(true);
		System.out.println(tilesReached);
		// 37078
		// printMap();
		writeMapToFile();

	}

	private static void writeMapToFile() {
		BufferedWriter bw;
		ArrayList<String> data = new ArrayList<String>();

		try {
			bw = new BufferedWriter(new FileWriter("src/output/outputday17"));
			String line = "";
			for (int y = 0; y < map.length; y++) {
				for (int x = 240; x < map[0].length; x++) {
					line = line.concat(Character.toString(map[y][x]));
				}
				line = line.concat("\n");
				bw.write(line);
				line = "";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static int calcTiles(boolean strict) {
		int acc = 0;
		for (int y = minY; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++)
				if (strict && map[y][x] == '~')
					acc++;
				else if (!strict && (map[y][x] == '|' || map[y][x] == '~'))
					acc++;
		}
		return acc;
	}

	private static void printMap() {
		for (int y = 0; y < map.length; y++) {
			for (int x = 240; x < map[0].length; x++)
				System.out.print(map[y][x]);
			System.out.println();
		}
	}

	private static void createMap() {
		map = new char[maxY + 1][maxX + 10];
		for (int i = 0; i < map.length; i++) {
			Arrays.fill(map[i], '.');
		}
		for (horiVein h : horizontalVeins) {
			for (int i = h.x1; i <= h.x2; i++) {
				map[h.y][i] = '#';
			}
		}
		for (vertVein v : verticalVeins) {
			for (int i = v.y1; i <= v.y2; i++) {
				map[i][v.x] = '#';
			}
		}
		map[0][500] = '+';

	}

	private static void flow(int x, int y) {
		map[y][x] = '|';
		if (y + 1 > maxY) // end reached
			return;
		if (canMoveDown(x, y)) {
			if (map[y + 1][x] != '|')
				flow(x, y + 1);
		} else { // look for path
			int x1 = x;
			while (!canMoveDown(x1, y) && canMoveLeft(x1, y)) {
				x1--;
				map[y][x1] = '|';
			}
			int x2 = x;
			while (!canMoveDown(x2, y) && canMoveRight(x2, y)) {
				x2++;
				map[y][x2] = '|';
			}
			if (!canMoveDown(x1, y) && !canMoveDown(x2, y)) {
				flood(x1, x2, y);
				flow(x, y - 1);
			}
			if (canMoveDown(x1, y))
				flow(x1, y);
			if (canMoveDown(x2, y))
				flow(x2, y);
		}
	}

	private static void flood(int x1, int x2, int y) {
		for (int i = x1; i <= x2; i++) {
			map[y][i] = '~';
		}

	}

	private static boolean canMoveLeft(int x, int y) {
		return map[y][x - 1] == '.' || map[y][x - 1] == '|';
	}

	private static boolean canMoveRight(int x, int y) {
		return map[y][x + 1] == '.' || map[y][x + 1] == '|';
	}

	private static boolean canMoveDown(int x, int y) {
		return map[y + 1][x] == '.' || map[y + 1][x] == '|';
	}

	private static void splitInput(ArrayList<String> input) {
		input.sort(null);
		for (int i = 0; i < input.size(); i++) {
			String s = input.get(i);
			if (s.charAt(0) == 'y')
				horizontal.add(s);
			else
				vertical.add(s);
		}
	}

	private static class vertVein {
		int x;
		int y1, y2;

		public vertVein(int x, int y1, int y2) {
			super();
			this.x = x;
			this.y1 = y1;
			this.y2 = y2;
		}
	}

	private static class horiVein {
		int y;
		int x1, x2;

		public horiVein(int y, int x1, int x2) {
			super();
			this.y = y;
			this.x1 = x1;
			this.x2 = x2;
		}
	}

	private static void createVeins() {
		Pattern p = Pattern.compile("(\\d+),\\s.=(\\d+)\\.\\.(\\d+)");
		for (String s : horizontal) {
			Matcher m = p.matcher(s);
			m.find();
			int y = Integer.valueOf(m.group(1));
			int x1 = Integer.valueOf(m.group(2));
			int x2 = Integer.valueOf(m.group(3));
			horizontalVeins.add(new horiVein(y, x1, x2));
			if (y < minY)
				minY = y;
			if (y > maxY)
				maxY = y;
			if (x1 < minX)
				minX = x1;
			if (x2 > maxX)
				maxX = x2;
		}
		for (String s : vertical) {
			Matcher m = p.matcher(s);
			m.find();
			int x = Integer.valueOf(m.group(1));
			int y1 = Integer.valueOf(m.group(2));
			int y2 = Integer.valueOf(m.group(3));
			verticalVeins.add(new vertVein(x, y1, y2));
			if (y1 < minY)
				minY = y1;
			if (y2 > maxY)
				maxY = y2;
			if (x < minX)
				minX = x;
			if (x > maxX)
				maxX = x;
		}

	}

	private static ArrayList<String> readInput() {
		BufferedReader br;
		ArrayList<String> data = new ArrayList<String>();

		try {
			br = new BufferedReader(new FileReader("src/input/inputday17"));
			String line = br.readLine();
			while (line != null) {
				data.add(line);
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

}
