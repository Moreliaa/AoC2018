package days;

import java.io.*;
import java.util.*;

public class Day5 {
	private static String oldString;
	private static String resultString;

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		String input = readInput();

		oldString = input;
		part1();
		System.out.println("Resulting length: " + resultString.length());

		int minLength = 99999;
		for (int i = 65; i <= 90; i++) {
			oldString = input;
			resultString = "";
			Character cUpper = (char) i;
			Character cLower = Character.toLowerCase(cUpper);
			oldString = deleteAll(cUpper, cLower);
			part1();
			if (resultString.length() < minLength) {
				minLength = resultString.length();
				System.out.println("Character: " + cUpper + " New min length: " + minLength);
			}

		}
		long end = System.currentTimeMillis();
		long time = end - start;
		System.out.println("Finished. Time spent: " + time + "ms");

	}

	private static String deleteAll(Character cUpper, Character cLower) {
		ArrayList<Character> resultChars = new ArrayList<Character>();
		for (int i = 0; i < oldString.length(); i++) {
			Character c = oldString.charAt(i);
			if (c != cUpper && c != cLower)
				resultChars.add(c);
		}
		StringBuilder sb = new StringBuilder();
		resultChars.forEach(sb::append);
		return sb.toString();
	}

	private static void part1() {
		Stack<Character> stack = new Stack<Character>();
		for (int i = 0; i < oldString.length(); i++) {
			Character currentChar = oldString.charAt(i);
			if (stack.isEmpty()) {
				stack.push(currentChar);
				continue;
			}
			Character lastChar = stack.peek();
			Character negator = getNegator(lastChar);
			if (currentChar != negator) {
				stack.push(currentChar);
			} else {
				stack.pop();
			}
		}
		StringBuilder sb = new StringBuilder();
		stack.forEach(sb::append);
		resultString = sb.toString();
	}

	private static Character getNegator(Character lastChar) {
		if (Character.isLowerCase(lastChar))
			return Character.toUpperCase(lastChar);
		else
			return Character.toLowerCase(lastChar);
	}

	private static String readInput() {
		BufferedReader br;
		String data = "";
		int i = 0;

		try {
			br = new BufferedReader(new FileReader("src/input/inputday5"));
			data = br.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

}
