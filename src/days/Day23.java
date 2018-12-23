package days;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Day23 {
	private static ArrayList<Bot> bots;
	private static ArrayList<BoundingBox> intersections = new ArrayList<BoundingBox>();

	public static void main(String[] args) {
		bots = readInput();
		Bot strongest = findStrongest();
		int inRange = calcNumInRangeOf(strongest);
		System.out.println(inRange);
		getFinalIntersections();
		ArrayList<BoundingBox> largest = getLargest();
		getSmallestCoordinate(largest);
	}

	private static void getSmallestCoordinate(ArrayList<BoundingBox> largest) {
		long x = 999999999999l, y = 999999999999l, z = 999999999999l;
		long distance = calcManhattan(x, y, z);
		for (BoundingBox b : largest) {
			long shortest = getShortestDistance(b);
			if (shortest < distance) {
				distance = shortest;
			}
		}
		System.out.println(distance);
	}

	private static long getShortestDistance(BoundingBox b) {
		long n1 = calcManhattan(b.xMin, b.yMin, b.zMin);
		long n2 = calcManhattan(b.xMin, b.yMin, b.zMax);
		long n3 = calcManhattan(b.xMin, b.yMax, b.zMin);
		long n4 = calcManhattan(b.xMin, b.yMax, b.zMax);
		long n5 = calcManhattan(b.xMax, b.yMin, b.zMin);
		long n6 = calcManhattan(b.xMax, b.yMin, b.zMax);
		long n7 = calcManhattan(b.xMax, b.yMax, b.zMin);
		long n8 = calcManhattan(b.xMax, b.yMax, b.zMax);
		long min = n1;
		String bla = "n1";
		min = Math.min(min, n2);
		if (min < n1)
			bla = "n2";
		min = Math.min(min, n3);
		if (min < n2)
			bla = "n3";
		min = Math.min(min, n4);
		if (min < n3)
			bla = "n4";
		min = Math.min(min, n5);
		if (min < n4)
			bla = "n5";
		min = Math.min(min, n6);
		if (min < n5)
			bla = "n6";
		min = Math.min(min, n7);
		if (min < n6)
			bla = "n7";
		min = Math.min(min, n8);
		if (min < n7)
			bla = "n8";
		System.out.println(
				bla + ": " + b.xMin + "," + b.xMax + "," + b.yMin + "," + b.yMax + "," + b.zMin + "," + b.zMax);
		return min;
	}

	private static long calcManhattan(long x, long y, long z) {
		return Math.abs(x) + Math.abs(y) + Math.abs(z);
	}

	private static ArrayList<BoundingBox> getLargest() {
		int largest = 0;
		for (BoundingBox b : intersections) {
			if (b.count > largest)
				largest = b.count;
		}
		ArrayList<BoundingBox> largestBoxes = new ArrayList<BoundingBox>();
		for (BoundingBox b : intersections) {
			if (b.count == largest)
				largestBoxes.add(b);
		}
		return largestBoxes;
	}

	private static void getFinalIntersections() {
		intersections = makeBoundingBoxesFromBots();
		int count = 1;
		while (count > 0) {
			count = 0;
			ArrayList<BoundingBox> intersectionsNew = new ArrayList<BoundingBox>(5000);
			for (int i = 0; i < intersections.size(); i++) {
				BoundingBox box = intersections.get(i);
				for (int j = i + 1; j < intersections.size(); j++) {
					BoundingBox otherBox = intersections.get(j);
					if (box.intersects(otherBox)) {
						BoundingBox newBox = box.shrink(otherBox);
						newBox.count = box.count + 1;
						intersectionsNew.add(newBox);
						count++;
					}
				}
			}
			if (count > 0)
				intersections = intersectionsNew;
		}

	}

	private static ArrayList<BoundingBox> makeBoundingBoxesFromBots() {
		ArrayList<BoundingBox> intersectionsNew = new ArrayList<BoundingBox>();
		for (Bot bot : bots) {
			intersectionsNew.add(new BoundingBox(bot.x - bot.r, bot.x + bot.r, bot.y - bot.r, bot.y + bot.r,
					bot.z - bot.r, bot.z + bot.r));
		}
		return intersectionsNew;
	}

	private static class BoundingBox {
		long xMin, xMax, yMin, yMax, zMin, zMax;
		int count = 1;

		public BoundingBox(long xMin, long xMax, long yMin, long yMax, long zMin, long zMax) {
			this.xMin = xMin;
			this.xMax = xMax;
			this.yMin = yMin;
			this.yMax = yMax;
			this.zMin = zMin;
			this.zMax = zMax;
		}

		public BoundingBox shrink(BoundingBox otherBox) {
			long xMinN, xMaxN, yMinN, yMaxN, zMinN, zMaxN;
			xMinN = this.xMin > otherBox.xMin ? this.xMin : otherBox.xMin;
			yMinN = this.yMin > otherBox.yMin ? this.yMin : otherBox.yMin;
			zMinN = this.zMin > otherBox.zMin ? this.zMin : otherBox.zMin;
			xMaxN = this.xMax < otherBox.xMax ? this.xMax : otherBox.xMax;
			yMaxN = this.yMax < otherBox.yMax ? this.yMax : otherBox.yMax;
			zMaxN = this.zMax < otherBox.zMax ? this.zMax : otherBox.zMax;
			return new BoundingBox(xMinN, xMaxN, yMinN, yMaxN, zMinN, zMaxN);
		}

		public boolean intersects(BoundingBox box) {
			// TODO Auto-generated method stub
			return (xMin <= box.xMax && xMax >= box.xMin) && (yMin <= box.yMax && yMax >= box.yMin)
					&& (zMin <= box.zMax && zMax >= box.zMin);
		}
	}

	private static int calcNumInRangeOf(Bot bot) {
		int count = 0;
		for (Bot b : bots) {
			if (calcManhattan(b, bot) <= bot.r)
				count++;
		}
		return count;
	}

	private static long calcManhattan(Bot a, Bot b) {
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y) + Math.abs(a.z - b.z);
	}

	private static Bot findStrongest() {
		Bot strongest = null;
		for (Bot b : bots) {
			if (strongest == null || b.r > strongest.r)
				strongest = b;
		}
		return strongest;
	}

	private static class Bot {
		long x, y, z, r;

		public Bot(long x, long y, long z, long r) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.r = r;
		}

	}

	private static ArrayList<Bot> readInput() {
		BufferedReader br;
		ArrayList<Bot> data = new ArrayList<Bot>();
		Pattern p = Pattern.compile("<(?<x>-?\\d+),(?<y>-?\\d+),(?<z>-?\\d+)>,\\sr=(?<r>-?\\d+)");

		try {
			br = new BufferedReader(new FileReader("src/input/inputday23"));
			String line = br.readLine();

			while (line != null) {
				Matcher m = p.matcher(line);
				m.find();
				Bot b = new Bot(Long.valueOf(m.group("x")), Long.valueOf(m.group("y")), Long.valueOf(m.group("z")),
						Long.valueOf(m.group("r")));
				data.add(b);
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

}
