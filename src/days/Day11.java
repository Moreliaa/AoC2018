package days;

import java.awt.*;

public class Day11 {
	private static int maxPow = -99999;
	private static Point p = null;

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		int input = 8444;
		Integer[][] grid = makeGrid(input);
		getMaxPow(grid, 3);
		System.out.println("Max power: " + maxPow);
		System.out.println("x: " + p.x + ", y: " + p.y);

		int maxGridSize = 1;
		for (int i = 1; i <= 300; i++) {
			int currentMax = maxPow;
			getMaxPow(grid, i);
			if (maxPow > currentMax)
				maxGridSize = i;
		}
		System.out.println("Max power: " + maxPow);
		System.out.println("x: " + p.x + ", y: " + p.y + ", size: " + maxGridSize);

		long end = System.currentTimeMillis();
		long runningtime = end - start;
		System.out.println("Running time: " + runningtime + "ms");
	}

	private static void getMaxPow(Integer[][] grid, int gridSize) {
		int bounds = (gridSize % 2 == 0) ? (gridSize) : (gridSize - 1) / 2;
		if (gridSize % 2 == 0) {
			for (int y = 1; y <= 301 - bounds; y++) {
				for (int x = 1; x <= 301 - bounds; x++) {
					int value = evenGridValue(grid, x, y, bounds);
					if (value > maxPow) {
						p = new Point(x, y);
						maxPow = value;
					}
				}
			}
		} else {
			for (int y = 1 + bounds; y <= 300 - bounds; y++) {
				for (int x = 1 + bounds; x <= 300 - bounds; x++) {
					int value = gridValue(grid, x, y, bounds);
					if (value > maxPow) {
						p = new Point(x - bounds, y - bounds);
						maxPow = value;
					}
				}
			}
		}
	}

	private static int evenGridValue(Integer[][] grid, int x, int y, int bounds) {
		int sum = 0;
		for (int y2 = y; y2 < y + bounds; y2++)
			for (int x2 = x; x2 < x + bounds; x2++)
				sum += grid[x2][y2];
		return sum;
	}

	private static int gridValue(Integer[][] grid, int x, int y, int bounds) {
		int sum = 0;
		for (int y2 = y - bounds; y2 <= y + bounds; y2++)
			for (int x2 = x - bounds; x2 <= x + bounds; x2++)
				sum += grid[x2][y2];
		return sum;
	}

	private static Integer[][] makeGrid(int input) {
		Integer[][] grid = new Integer[301][301];
		for (int y = 1; y <= 300; y++) {
			for (int x = 1; x <= 300; x++) {
				int rackId = x + 10;
				Integer power = rackId * y;
				power += input;
				power *= rackId;
				power = getHundredsDigit(power);
				power -= 5;
				grid[x][y] = power;
			}
		}
		return grid;
	}

	private static Integer getHundredsDigit(Integer power) {
		String pow = power.toString();
		if (pow.length() < 3)
			return 0;
		pow = pow.substring(pow.length() - 3, pow.length() - 2);
		return Integer.valueOf(pow);

	}

}
