package days;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Day10 {
	private static int maxSize = 99999999;

	public static void main(String[] args) {
		ArrayList<String> input = readInput();
		ArrayList<Signal> signals = processInput(input);
		int time = 0;
		while (!starsAligned(signals)) {
			advanceTime(signals);
			time++;
		}
		reverseTime(signals);
		time--;
		print(signals);
		System.out.println(time);
	}

	private static void print(ArrayList<Signal> signals) {
		// get ye box
		int xMin = 999999, yMin = 999999, xMax = 0, yMax = 0;
		for (Signal signal : signals) {
			if (signal.xPos < xMin)
				xMin = signal.xPos;
			if (signal.yPos < yMin)
				yMin = signal.yPos;
			if (signal.xPos > xMax)
				xMax = signal.xPos;
			if (signal.yPos > yMax)
				yMax = signal.yPos;
		}
		// print ye box
		for (int i = yMin; i <= yMax; i++) {
			for (int j = xMin; j <= xMax; j++) {
				boolean found = false;
				for (Signal signal : signals) {
					if (signal.xPos == j && signal.yPos == i) {
						System.out.print("#");
						found = true;
						break;
					}
				}
				if (!found)
					System.out.print(".");
			}
			System.out.println();
		}

	}

	private static boolean starsAligned(ArrayList<Signal> signals) {
		int xMin = 999999, yMin = 999999, xMax = 0, yMax = 0;
		for (Signal signal : signals) {
			if (signal.xPos < xMin)
				xMin = signal.xPos;
			if (signal.yPos < yMin)
				yMin = signal.yPos;
			if (signal.xPos > xMax)
				xMax = signal.xPos;
			if (signal.yPos > yMax)
				yMax = signal.yPos;
		}
		int totalSize = xMax - xMin + yMax - yMin;
		if (totalSize < maxSize)
			maxSize = totalSize;
		return (totalSize > maxSize);
	}

	private static void advanceTime(ArrayList<Signal> signals) {
		for (Signal signal : signals) {
			signal.xPos += signal.xSpeed;
			signal.yPos += signal.ySpeed;
		}
	}

	private static void reverseTime(ArrayList<Signal> signals) {
		for (Signal signal : signals) {
			signal.xPos -= signal.xSpeed;
			signal.yPos -= signal.ySpeed;
		}
	}

	private static ArrayList<Signal> processInput(ArrayList<String> input) {
		ArrayList<Signal> signals = new ArrayList<Signal>();
		Pattern p = Pattern
				.compile("(?<x>-?\\d+),\\s*(?<y>-?\\d+)>\\svelocity=<\\s*(?<xSpd>-?\\d+),\\s*(?<ySpd>-?\\d+)");
		for (String string : input) {
			Matcher m = p.matcher(string);
			m.find();
			Signal s = new Signal(Integer.valueOf(m.group("x")), Integer.valueOf(m.group("y")),
					Integer.valueOf(m.group("xSpd")), Integer.valueOf(m.group("ySpd")));
			signals.add(s);
		}
		return signals;
	}

	private static class Signal {
		int xPos, yPos, xSpeed, ySpeed;

		public Signal(int xPos, int yPos, int xSpeed, int ySpeed) {
			this.xPos = xPos;
			this.yPos = yPos;
			this.xSpeed = xSpeed;
			this.ySpeed = ySpeed;
		}
	}

	private static ArrayList<String> readInput() {
		BufferedReader br;
		ArrayList<String> data = new ArrayList<String>();

		try {
			br = new BufferedReader(new FileReader("src/input/inputday10"));
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
