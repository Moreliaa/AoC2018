package days;

import java.awt.*;
import java.util.*;
import java.util.regex.*;

public class Day6 {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		String[] input = { "183, 157", "331, 86 ", "347, 286", "291, 273", "285, 152", "63, 100 ", "47, 80  ",
				"70, 88  ", "333, 86 ", "72, 238 ", "158, 80 ", "256, 140", "93, 325 ", "343, 44 ", "89, 248 ",
				"93, 261 ", "292, 250", "240, 243", "342, 214", "192, 51 ", "71, 92  ", "219, 63 ", "240, 183",
				"293, 55 ", "316, 268", "264, 151", "68, 98  ", "190, 288", "85, 120 ", "261, 59 ", "84, 222 ",
				"268, 171", "205, 134", "80, 161 ", "337, 326", "125, 176", "228, 122", "278, 151", "129, 287",
				"293, 271", "57, 278 ", "104, 171", "330, 69 ", "141, 141", "112, 127", "201, 151", "331, 268",
				"95, 68  ", "289, 282", "221, 359" };
		ArrayList<Point> points = new ArrayList<Point>();
		getPoints(input, points);
		int minX = 9999, maxX = 0, minY = 9999, maxY = 0;
		for (Point point : points) {
			if (point.x < minX)
				minX = point.x;
			if (point.x > maxX)
				maxX = point.x;
			if (point.y < minY)
				minY = point.y;
			if (point.y > maxY)
				maxY = point.y;
		}
		normalizePointCoordinates(points, minX, minY);
		int fieldXmax = maxX - minX + 1;
		int fieldYmax = maxY - minY + 1;
		HashMap<Integer, Integer> areaSizes = new HashMap<Integer, Integer>(100);
		int maxArea = 1;
		int regionSize = 0;
		for (int i = 0; i < fieldXmax; i++) {
			boolean seenRegion = false;
			boolean passedRegion = false;
			for (int j = 0; j < fieldYmax; j++) {
				int id = getClosestPointId(points, i, j);
				int totalDistance = 0;
				if (!passedRegion) {
					totalDistance = calcAllManhattan(points, i, j);
					if (totalDistance < 10000) {
						regionSize++;
						seenRegion = true;
						System.out.print("#");
					} else {
						System.out.print(".");
					}
				}
				if (seenRegion && totalDistance >= 10000)
					passedRegion = true;
				if (!areaSizes.containsKey(id)) {
					areaSizes.put(id, 1);
				} else {
					int val = areaSizes.get(id);
					val++;
					areaSizes.put(id, val);
					if (val > maxArea && id != -1)
						maxArea = val;
				}
			}
			System.out.println();

		}
		System.out.println(maxArea);
		System.out.println(regionSize);
		long end = System.currentTimeMillis();
		long time = end - start;
		System.out.println("Running time: " + time + "ms");

	}

	private static int calcAllManhattan(ArrayList<Point> points, int x, int y) {
		int distance = 0;
		for (Point p : points)
			distance += calcManhattan(p, x, y);
		return distance;
	}

	private static int getClosestPointId(ArrayList<Point> points, int x, int y) {
		int minManhattan = 9999;
		int id = 0;
		boolean tie = false;
		for (int i = 0; i < points.size(); i++) {
			Point p = points.get(i);
			int man = calcManhattan(p, x, y);
			if (man < minManhattan) {
				tie = false;
				minManhattan = man;
				id = i;
			} else if (man == minManhattan)
				tie = true;
		}
		if (tie)
			return -1;
		else
			return id;
	}

	private static int calcManhattan(Point p, int x, int y) {
		return Math.abs(x - p.x) + Math.abs(y - p.y);
	}

	private static void normalizePointCoordinates(ArrayList<Point> points, int minX, int minY) {
		for (Point point : points) {
			point.x -= minX;
			point.y -= minY;
		}

	}

	private static void getPoints(String[] input, ArrayList<Point> points) {
		Pattern p = Pattern.compile("^(?<x>\\d+),\\s(?<y>\\d+)");
		for (int i = 0; i < input.length; i++) {
			Matcher m = p.matcher(input[i]);
			m.find();
			Point point = new Point(Integer.valueOf(m.group("x")), Integer.valueOf((m.group("y"))));
			points.add(point);
		}

	}

}
