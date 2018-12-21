package days;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Day19 {
	private static int[] reg = new int[6];
	private static int ip = 0;
	private static String[] opcodes = { "borr", "addr", "eqrr", "addi", "eqri", "eqir", "gtri", "mulr", "setr", "gtir",
			"muli", "banr", "seti", "gtrr", "bani", "bori" };

	public static void main(String[] args) {
		ArrayList<String> input = readInput();
		reg[0] = 0;
		reg[1] = 0; // ip binding
		reg[2] = 0;
		reg[3] = 0;
		reg[4] = 0;
		reg[5] = 0;
		int[][] commands = new int[input.size()][4];
		for (int i = 0; i < commands.length; i++) {
			commands[i] = getNumbers(input.get(i));
		}
		System.out.println(reg[0] + "," + reg[1] + "," + reg[2] + "," + reg[3] + "," + reg[4] + "," + reg[5]);
		/*
		 * while (reg[1] < 250) { bla(); System.out.println(reg[0] + "," + reg[1] + ","
		 * + reg[2] + "," + reg[3] + "," + reg[4] + "," + reg[5]); }
		 */
		while (ip < input.size()) {
			// int[] instruction = getNumbers(input.get(ip));
			System.out.println("IP: " + ip + " Command:" + opcodes[commands[ip][0]] + "," + commands[ip][1] + ","
					+ commands[ip][2] + "," + commands[ip][3]);
			performInstruction(null, commands[ip], null, "");
			System.out.println(reg[0] + "," + reg[1] + "," + reg[2] + "," + reg[3] + "," + reg[4] + "," + reg[5]);
		}
		System.out.println(reg[0] + "," + reg[1] + "," + reg[2] + "," + reg[3] + "," + reg[4] + "," + reg[5]);
	}

	private static boolean testOperations(int[] before, int[] instruction, int[] after) {
		String[] ops = { "eqir" };
		int numOfOpCodes = 0;
		System.out.println(
				"bef: " + before[0] + before[1] + before[2] + before[3] + " sample: " + instruction[0] + instruction[1]
						+ instruction[2] + instruction[3] + " aft: " + after[0] + after[1] + after[2] + after[3]);
		for (int i = 0; i < ops.length; i++) {
			if (numOfOpCodes >= 3)
				break;
			performInstruction(before, instruction, after, ops[i]);

			if (Arrays.equals(reg, after)) {
				numOfOpCodes++;
				System.out.println("command: " + ops[i] + " reg: " + reg[0] + reg[1] + reg[2] + reg[3] + " aft: "
						+ after[0] + after[1] + after[2] + after[3]);
			}
		}
		if (numOfOpCodes == 1) {
			System.out.println("=============CONFIRMED OPCODE============");
		}
		return numOfOpCodes >= 3;
	}

	private static boolean performInstruction(int[] before, int[] instruction, int[] after, String string) {
		// String[] reqRegistryA = { "addr", "addi", "mulr", "muli", "banr", "bani",
		// "borr", "bori", "setr", "gtri",
		// "gtrr", "eqri", "eqrr" };
		// String[] reqRegistryB = { "addr", "mulr", "banr", "borr", "gtir", "gtrr",
		// "eqir", "eqrr" };

		int opCode = instruction[0];
		int a = instruction[1];
		int b = instruction[2];
		int c = instruction[3];
		/*
		 * if (Arrays.asList(reqRegistryA).contains(string) && a > 3) return false; if
		 * (Arrays.asList(reqRegistryB).contains(string) && b > 3) return false; if
		 * (before != null) System.arraycopy(before, 0, reg, 0, 4);
		 */
		if (string == "")
			string = opcodes[opCode];

		reg[1] = ip;
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
		ip = reg[1];
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
		String bana = appendZeroes(Integer.toBinaryString(a));
		String banb = appendZeroes(Integer.toBinaryString(b));
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < bana.length(); i++) {
			if (bana.charAt(i) == '1' || banb.charAt(i) == '1')
				result.append("1");
			else
				result.append("0");
		}
		int c = Integer.parseInt(result.toString(), 2);
		return c;
	}

	private static String appendZeroes(String binaryString) {
		while (binaryString.length() < 4)
			binaryString = "0".concat(binaryString);
		return binaryString;
	}

	private static int ban(int a, int b) {
		String bana = appendZeroes(Integer.toBinaryString(a));
		String banb = appendZeroes(Integer.toBinaryString(b));
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < bana.length(); i++) {
			if (bana.charAt(i) == '1' && banb.charAt(i) == '1')
				result.append("1");
			else
				result.append("0");
		}
		int c = Integer.parseInt(result.toString(), 2);
		return c;
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
			br = new BufferedReader(new FileReader("src/input/inputday19"));
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
