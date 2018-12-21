package days;

import java.io.*;
import java.util.*;

public class Day3 {

	public static void main(String[] args) {
		ArrayList<Claim> input = parseInput();
		HashMap<String, Integer> map = new HashMap<String, Integer>(1000000);
		HashMap<String, Integer> knownConflicts = new HashMap<String, Integer>(1000000);
		HashMap<Integer, Integer> conflictingIDs = new HashMap<Integer, Integer>(1300);
		int numOfConflicts = 0;
		for (int i = 0; i < input.size(); i++) {
			numOfConflicts += mapInput(input.get(i), map, knownConflicts, conflictingIDs);
		}
		System.out.println(numOfConflicts);
		for (int i = 0; i < input.size(); i++) {
			int id = input.get(i).id;
			if (!conflictingIDs.containsKey(id))
				System.out.println(id);
		}

	}

	private static int mapInput(Claim claim, HashMap<String, Integer> map, HashMap<String, Integer> knownConflicts,
			HashMap<Integer, Integer> conflictingIDs) {
		int newConflicts = 0;
		for (int i = claim.left; i < claim.width + claim.left; i++) {
			for (int j = claim.top; j < claim.height + claim.top; j++) {
				String key = i + "," + j;
				if (map.containsKey(key)) {
					if (!knownConflicts.containsKey(key)) {
						knownConflicts.put(key, 0);
						newConflicts++;
					}
					int conflictingID = map.get(key);
					conflictingIDs.put(conflictingID, 0);
					conflictingIDs.put(claim.id, 0);
				} else {
					map.put(key, claim.id);
				}
			}
		}
		return newConflicts;
	}

	private static ArrayList<Claim> parseInput() {
		BufferedReader br;
		ArrayList<Claim> data = new ArrayList<Claim>();

		try {
			br = new BufferedReader(new FileReader("src/input/inputday3"));
			String line = br.readLine();
			while (line != null) {
				data.add(parseLine(line));
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	private static Claim parseLine(String line) {
		int stage = 0;
		int id = 0, left = 0, top = 0, width = 0, height = 0;
		String item = "";
		line = line.replaceAll("\\s", "");
		for (int i = 1; i < line.length(); i++) {
			Character c = line.charAt(i);
			if (c != '@' && c != ',' && c != ':' && c != 'x' && i != line.length() - 1)
				item = item.concat(c.toString());
			else {
				switch (stage) {
				case 0:
					id = Integer.valueOf(item);
					break;
				case 1:
					left = Integer.valueOf(item);
					break;
				case 2:
					top = Integer.valueOf(item);
					break;
				case 3:
					width = Integer.valueOf(item);
					break;
				case 4:
					item = item.concat(c.toString());
					height = Integer.valueOf(item);
				}
				item = "";
				stage++;
			}
		}
		return new Claim(id, left, top, width, height);
	}

	private static class Claim {
		int id, left, top, width, height;

		Claim(int id, int left, int top, int width, int height) {
			this.id = id;
			this.left = left;
			this.top = top;
			this.width = width;
			this.height = height;
		}
	}
}
