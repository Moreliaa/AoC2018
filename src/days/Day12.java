package days;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Day12 {
	private static String config;
	private static int zeroIndex = 0;
	private static Pattern p = Pattern.compile("^(.{5})");
	private static Pattern pEmpty1 = Pattern.compile("^\\.{6}");
	private static Pattern pEmpty2 = Pattern.compile("\\.{6}$");

	public static void main(String[] args) {
		ArrayList<String> notes = readInput();
		for (int i = 0; i < config.length(); i++) {
			if (config.charAt(i) == '#') {
				zeroIndex = i;
				break;
			}
		}
		System.out.println("0: " + config);
		for (long i = 1; i <= 100000; i++) {
			appendEmptyPots();
			nextGen(notes);
			removeEmptyPots();
			// System.out.println(i + ": " + config);
		}
		long count = 0;
		for (int i = 0; i < config.length(); i++)
			if (config.charAt(i) == '#')
				count += i - zeroIndex;
		System.out.println("Count: " + count);
		// diff = 86
		long count2 = 8602414l + 86 * 49999900000l;
		System.out.println("Count after 50000000000 gens: " + count2);

	}

	private static void removeEmptyPots() {
		Matcher m = pEmpty1.matcher(config);
		StringBuilder sb = new StringBuilder(config);
		while (m.find()) {
			sb.deleteCharAt(0);
			zeroIndex--;
		}
		m = pEmpty2.matcher(sb.toString());
		while (m.find(sb.length() - 10))
			sb.deleteCharAt(sb.length() - 1);
		config = sb.toString();
	}

	private static void appendEmptyPots() {
		int firstPlant = -1;
		int lastPlant = -1;
		for (int i = 0; i < config.length(); i++)
			if (config.charAt(i) == '#') {
				firstPlant = i;
				break;
			}
		for (int i = config.length() - 7; i < config.length(); i++)
			if (config.charAt(i) == '#')
				lastPlant = i;

		int distanceFromEnd = Math.abs(lastPlant - config.length() + 1);
		if (distanceFromEnd <= 4)
			for (int i = 0; i <= 4 - distanceFromEnd; i++)
				config = config.concat(".");
		if (firstPlant <= 4)
			for (int i = 0; i <= 4 - firstPlant; i++) {
				config = ".".concat(config);
				zeroIndex++;
			}

	}

	private static void nextGen(ArrayList<String> notes) {
		StringBuilder newConfig = new StringBuilder();
		for (int i = 0; i < config.length(); i++)
			newConfig.append(placePotOrNot(notes, i));
		config = newConfig.toString();
	}

	private static String placePotOrNot(ArrayList<String> notes, int i) {
		for (String string : notes) {
			if (matchesNote(string, i)) {
				Character c = string.charAt(string.length() - 1);
				return c.toString();
			}
		}
		return ".";
	}

	private static boolean matchesNote(String note, int index) {
		Matcher m = p.matcher(note);
		m.find();
		int i2 = 0;
		for (int i = index - 2; i <= index + 2; i++) {
			if ((i < 0 || i > config.length() - 1) && m.group(1).charAt(i2) != '.')
				return false;
			else if (i < 0 || i > config.length() - 1) {
				i2++;
				continue;
			}
			if (config.charAt(i) != m.group(1).charAt(i2))
				return false;
			i2++;
		}
		return true;
	}

	private static ArrayList<String> readInput() {
		BufferedReader br;
		ArrayList<String> data = new ArrayList<String>();

		try {
			br = new BufferedReader(new FileReader("src/input/inputday12"));
			String line = br.readLine();
			config = line.substring(15);
			br.readLine();
			line = br.readLine();
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
