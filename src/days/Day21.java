package days;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Day21 {
	private static int[] reg = new int[6];
	private static int ip = 0;
	private static String[] opcodes = { "borr", "addr", "eqrr", "addi", "eqri", "eqir", "gtri", "mulr", "setr", "gtir",
			"muli", "banr", "seti", "gtrr", "bani", "bori" };

	public static void main(String[] args) {
		ArrayList<String> input = readInput();
		reg[0] = 0;
		reg[1] = 0;
		reg[2] = 0;
		reg[3] = 0;
		reg[4] = 0; // ip binding
		reg[5] = 0;
		int[][] commands = new int[input.size()][4];
		for (int i = 0; i < commands.length; i++) {
			commands[i] = getNumbers(input.get(i));
		}
		HashMap<Integer, Integer> seen = new HashMap<Integer, Integer>(15000);
		int firstAdded = 0;
		int lastAdded = 0;
		while (ip < input.size()) {
			if (ip == 28) {
				if (!seen.containsKey(reg[2])) {
					seen.put(reg[2], 0);
					lastAdded = reg[2];
					if (firstAdded == 0)
						firstAdded = reg[2];
				} else
					break;
			}
			performInstruction(null, commands[ip], null, "");
		}
		System.out.println("seen first: " + firstAdded + ", seen last: " + lastAdded);
		System.out.println(reg[0] + "," + reg[1] + "," + reg[2] + "," + reg[3] + "," + reg[4] + "," + reg[5]);
	}

	private static boolean performInstruction(int[] before, int[] instruction, int[] after, String string) {

		int opCode = instruction[0];
		int a = instruction[1];
		int b = instruction[2];
		int c = instruction[3];
		if (string == "")
			string = opcodes[opCode];

		reg[4] = ip;
		switch (string) {
		case "addr":
			reg[c] = reg[a] + reg[b];
			break;
		case "addi":
			reg[c] = reg[a] + b;
			break;
		case "mulr":
			reg[c] = reg[a] * reg[b];
			break;
		case "muli":
			reg[c] = reg[a] * b;
			break;
		case "banr":
			reg[c] = ban(reg[a], reg[b]);
			break;
		case "bani":
			reg[c] = ban(reg[a], b);
			break;
		case "borr":
			reg[c] = bor(reg[a], reg[b]);
			break;
		case "bori":
			reg[c] = bor(reg[a], b);
			break;
		case "setr":
			int vala = Integer.valueOf(reg[a]);
			reg[c] = vala;
			break;
		case "seti":
			reg[c] = a;
			break;
		case "gtir":
			reg[c] = AgreaterB(a, reg[b]);
			break;
		case "gtri":
			reg[c] = AgreaterB(reg[a], b);
			break;
		case "gtrr":
			reg[c] = AgreaterB(reg[a], reg[b]);
			break;
		case "eqir":
			reg[c] = AequalB(a, reg[b]);
			break;
		case "eqri":
			reg[c] = AequalB(reg[a], b);
			break;
		case "eqrr":
			reg[c] = AequalB(reg[a], reg[b]);
			break;
		default:
			System.out.println("command not found");
		}
		ip = reg[4];
		ip++;
		return true;
	}

	private static int AequalB(int a, int b) {
		if (a == b)
			return 1;
		else
			return 0;
	}

	private static int AgreaterB(int a, int b) {
		if (a > b)
			return 1;
		else
			return 0;
	}

	private static int bor(int a, int b) {
		return a | b;
	}

	private static int ban(int a, int b) {
		return a & b;
	}

	private static int[] getNumbers(String string) {
		int[] nums = new int[4];
		Pattern p = Pattern.compile("(\\d+)");
		Matcher m = p.matcher(string);
		for (int i = 1; i < nums.length; i++) {
			m.find();
			nums[i] = Integer.valueOf(m.group(1));
		}
		for (int i = 0; i < opcodes.length; i++) {
			if (opcodes[i].equals(string.substring(0, 4))) {
				nums[0] = i;
				break;
			}
		}
		return nums;
	}

	private static ArrayList<String> readInput() {
		BufferedReader br;
		ArrayList<String> data = new ArrayList<String>();

		try {
			br = new BufferedReader(new FileReader("src/input/inputday21"));
			br.readLine();
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
