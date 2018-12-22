package days;

import java.util.*;
import java.util.Map.*;
import java.util.regex.*;

public class Day22 {

	private static int depth = 11541;
	private static int xTarget = 14;
	private static int yTarget = 778;
	// private static int depth = 510;
	// private static int xTarget = 10;
	// private static int yTarget = 10;
	private static int maxOffset = 300;
	private static HashMap<String, Integer> indexMap = new HashMap<String, Integer>(10000);
	private static HashMap<String, Integer> erosionMap = new HashMap<String, Integer>(10000);
	private static HashMap<String, Integer> typeMap = new HashMap<String, Integer>(10000);

	// dijkstra
	private static HashMap<String, Integer> visited = new HashMap<String, Integer>(10000);
	private static HashMap<String, Integer> distanceMap = new HashMap<String, Integer>(10000);
	private static HashMap<String, String> toolMap = new HashMap<String, String>(10000);

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
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
		for (int y = yTarget; y <= yTarget + maxOffset; y++)
			for (int x = xTarget; x <= xTarget + maxOffset; x++)
				setErosion(x, y);
		// printMap();
		dijkstra();
		long end = System.currentTimeMillis();
		long runningtime = end - start;
		System.out.println("Running time: " + runningtime + "ms");
	}

	private static void dijkstra() {

		int currX = 0;
		int currY = 0;
		String currentTool = "torch";
		int currentDistance = 0;
		// tools: neither, both, torch, gear, notTorch, notGear
		Pattern p = Pattern.compile("(\\d+),(\\d+)");

		while (true) {
			// calc distances
			calcTentativeDistance(currX, currY - 1, currentTool, currentDistance);
			calcTentativeDistance(currX - 1, currY, currentTool, currentDistance);
			calcTentativeDistance(currX + 1, currY, currentTool, currentDistance);
			calcTentativeDistance(currX, currY + 1, currentTool, currentDistance);
			visited.put(currX + "," + currY, 0);
			if (visited.containsKey(xTarget + "," + yTarget))
				break;
			distanceMap.remove(currX + "," + currY);
			// find next node to visit
			Iterator<Entry<String, Integer>> it = distanceMap.entrySet().iterator();
			int shortest = 99999999;
			String shortestKey = "";
			while (it.hasNext()) {
				Map.Entry<String, Integer> pair = it.next();
				String key = pair.getKey();
				if (!visited.containsKey(key)) {
					int dist = pair.getValue();
					if (dist < shortest) {
						shortestKey = key;
						shortest = dist;
					}
				}
			}
			Matcher m = p.matcher(shortestKey);
			m.find();
			currX = Integer.valueOf(m.group(1));
			currY = Integer.valueOf(m.group(2));
			currentTool = toolMap.get(shortestKey);
			currentDistance = shortest;
		}
		System.out.println(currentDistance);

	}

	private static void calcTentativeDistance(int x, int y, String currentTool, int totalDistance) {
		if (x >= 0 && x <= xTarget + maxOffset && y >= 0 && y <= yTarget + maxOffset
				&& !visited.containsKey(x + "," + y)) {
			int type = typeMap.get(x + "," + y);
			int distance = 1 + totalDistance;
			String newTool = currentTool;
			switch (type) {
			case 0: // rocky
				if (currentTool == "neither"
						|| (x == xTarget && y == yTarget && (currentTool == "notTorch" || currentTool == "gear"))) {
					newTool = "both";
					distance += 7;
				}
				break;
			case 1:// wet
				if (currentTool == "torch") {
					newTool = "notTorch";
					distance += 7;
				} else if (currentTool == "both") {
					newTool = "gear";
				} else if (currentTool == "notGear") {
					newTool = "neither";
				}
				break;
			case 2:// narrow
				if (currentTool == "gear") {
					newTool = "notGear";
					distance += 7;
				} else if (currentTool == "both") {
					newTool = "torch";
				} else if (currentTool == "notTorch") {
					newTool = "neither";
				}
			}
			if (!distanceMap.containsKey(x + "," + y) || distance < distanceMap.get(x + "," + y)) {
				distanceMap.put(x + "," + y, distance);
				toolMap.put(x + "," + y, newTool);
			}
		}
	}

	private static void printMap() {
		for (int y = 0; y < yTarget + maxOffset; y++) {
			for (int x = 0; x < xTarget + maxOffset; x++)
				System.out.print(typeMap.get(x + "," + y));
			System.out.println();
		}
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