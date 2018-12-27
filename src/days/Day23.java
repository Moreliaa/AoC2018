package days;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Day23 {
	private static ArrayList<Bot> bots;
	private static ArrayList<Intersection> intersections = new ArrayList<Intersection>();
	private static String filename = "src/input/inputday23";

	public static void main(String[] args) {
		bots = readInput();
		//part1
		Bot strongest = findStrongest();
		int inRange = calcNumInRangeOf(strongest);
		System.out.println(inRange);
		//part2
		int maxCount = 0;
		ArrayList<Bot> best = new ArrayList<Bot>();
		for(int i = 0; i < bots.size(); i++) {
			Bot a = bots.get(i);
			int count = 0;
			for(int j = 0; j < bots.size(); j++){
				Bot b = bots.get(j);
				if (rangesIntersect(a,b)) {
						count++;
						if(count > maxCount) {
							maxCount = count;
							best.removeAll(best);
							best.add(a);
						} else if (count == maxCount)
							best.add(a);
				}
			}
			a.overlapping = count;
		}
		for (int i = bots.size() - 1; i >= 0; i--) {
			Bot b = bots.get(i);
			if(b.overlapping < 900 && !filename.contains("mock"))
				bots.remove(b); 
		}

		long[][] bounds;
		long[][] combinedBounds = null;
		for (Bot b:bots) {
			long x = b.x;
			long y = b.y;
			long z = b.z;
			long r = b.r;
			bounds = new long[][]{
				{+x+y+z-r, +x+y+z+r},
				{-x+y+z-r, -x+y+z+r},
				{+x-y+z-r, +x-y+z+r},
				{+x+y-z-r, +x+y-z+r}
			};
			if (combinedBounds == null) {
				combinedBounds = bounds;
			} else {
				combinedBounds = shrink(combinedBounds, bounds);
			}
		}
		long xMin = (combinedBounds[2][0] + combinedBounds[3][0]) / 2;
		long yMin = (combinedBounds[1][0] + combinedBounds[3][0]) / 2;
		long zMin = (combinedBounds[1][0] + combinedBounds[2][0]) / 2;
		System.out.println(xMin+" "+yMin+" "+zMin+" Total: "+(xMin+yMin+zMin));
/*
		bots.sort(new BotComparator());
		for (Bot b:best) {
			Intersection s = new Intersection();
			s.addBot(b);
			intersections.add(s);
		}
		int lastMax;
		maxCount = 1;
		do {
			lastMax = maxCount;
			
			for (int i = intersections.size() - 1; i >= 0; i--) {
				Intersection s = intersections.get(i);
				s.findNext();
				if (s.count > maxCount) {
					maxCount = s.count;
					for (int j = intersections.size() - 1; j > i; j--) {
						Intersection check = intersections.get(j);
						if (check.count < maxCount)
							intersections.remove(check);
					}
				} else if (s.count < maxCount) {
					intersections.remove(s);
				}
			} 
		} while (maxCount > lastMax);
		System.out.println();*/
	}

	private static long[][] shrink(long[][] newBounds, long[][] bounds) {
		long[][] combined = new long[4][2];
		for (int i = 0; i < combined.length; i++) {
			for (int j = 0; j < combined[0].length; j++) {
				if (j == 0)
					combined[i][j] = Math.max(newBounds[i][j], bounds[i][j]);
				else
					combined[i][j] = Math.min(newBounds[i][j], bounds[i][j]);
			}
		}
		return combined;
	}

	private static class BotComparator implements Comparator<Bot> {
		public int compare(Bot a, Bot b) {
			if (a.overlapping > b.overlapping)
				return -1;
			if (a.overlapping == b.overlapping)
				return 0;
			return 1;	
		}
	}

	private static boolean rangesIntersect(Bot a, Bot b) {
		long distance = calcManhattan(a, b);
		long combinedRange = a.r + b.r;
		return distance < combinedRange;
	}

	private static class Intersection {
		ArrayList<Bot> botsLocal = new ArrayList<Bot>();
		int count = 0;

		public void addBot(Bot b) {
			this.botsLocal.add(b);
			this.count++;
		}

		public void findNext() {
			int currentOverlap = this.botsLocal.get(this.botsLocal.size()-1).overlapping;
			boolean found = false;
			Bot newBot = null;
			for (Bot b: bots) {
				if (found) {
					if (b.overlapping == newBot.overlapping && this.intersectsWith(b)) {
						Intersection newI = new Intersection();
						newI.botsLocal.addAll(this.botsLocal);
						newI.count = this.count;
						newI.addBot(b);
						intersections.add(newI);
					} else if (b.overlapping < newBot.overlapping)
						break;
				}
				else if (b.overlapping < currentOverlap && this.intersectsWith(b)) {
					newBot = b;
					found = true;
				}
			}
			if (newBot!=null) {
				this.addBot(newBot);
			}
		}

		public boolean intersectsWith(Bot b) {
			for (Bot a: this.botsLocal) {
				if(!rangesIntersect(a, b))
					return false;
			}
			return true;
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

	private static long calcManhattan(long x, long y, long z) {
		return Math.abs(x) + Math.abs(y) + Math.abs(z);
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
		int overlapping = 0;

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
			br = new BufferedReader(new FileReader(filename));
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
