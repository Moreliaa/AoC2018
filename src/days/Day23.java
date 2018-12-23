package days;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Day23 {
	private static ArrayList<Bot> bots;

	public static void main(String[] args) {
		bots = readInput();
		Bot strongest = findStrongest();
		int inRange = calcNumInRangeOf(strongest);
		System.out.println(inRange);
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
			br.readLine();
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
