package days;

import java.util.*;

public class Day22 {
	private static int depth = 11541;
	private static int xTarget = 14;
	private static int yTarget = 778;
	private static HashMap<String, Integer> indexMap = new HashMap<String, Integer>(10000);
	private static HashMap<String, Integer> erosionMap = new HashMap<String, Integer>(10000);
	private static HashMap<String, Integer> typeMap = new HashMap<String, Integer>(10000);

	public static void main(String[] args) {
		// Part1
		for (int y = 0; y <= yTarget; y++)
			for (int x = 0; x <= xTarget; x++)
				setErosion(x, y);
		int sum = 0;
		for (int y = 0; y <= yTarget; y++)
			for (int x = 0; x <= xTarget; x++)
				sum += typeMap.get(x + "," + y);
		System.out.println(sum);
		// Part2
		for (int y = 0; y <= depth; y++)
			for (int x = 0; x <= depth; x++)
				setErosion(x, y);
	}

	private static int getIndex(int x, int y) {
		// The region at 0,0 (the mouth of the cave) has a geologic index of 0.
		// The region at the coordinates of the target has a geologic index of 0.
		// If the region's Y coordinate is 0, the geologic index is its X coordinate
		// times 16807.
		// If the region's X coordinate is 0, the geologic index is its Y coordinate
		// times 48271.
		// Otherwise, the region's geologic index is the result of multiplying the
		// erosion levels of the regions at X-1,Y and X,Y-1.
		if (indexMap.containsKey(x + "," + y))
			return indexMap.get(x + "," + y);
		int res;
		if (x == 0 && y == 0)
			res = 0;
		else if (x == xTarget && y == yTarget)
			res = 0;
		else if (y == 0)
			res = x * 16807;
		else if (x == 0)
			res = y * 48271;
		else
			res = getErosion(x - 1, y) * getErosion(x, y - 1);
		indexMap.put(x + "," + y, res);
		return res;
	}

	private static void setErosion(int x, int y) {
		if (!erosionMap.containsKey(x + "," + y))
			putRegionEntry(x, y);
	}

	private static int getErosion(int x, int y) {
		if (erosionMap.containsKey(x + "," + y))
			return erosionMap.get(x + "," + y);
		return putRegionEntry(x, y);
	}

	private static int putRegionEntry(int x, int y) {
		int res = (getIndex(x, y) + depth) % 20183;
		erosionMap.put(x + "," + y, res);
		typeMap.put(x + "," + y, res % 3);
		return res;
	}
}