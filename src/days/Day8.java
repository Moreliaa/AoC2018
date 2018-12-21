package days;

import java.io.*;
import java.util.*;

public class Day8 {
	private static int index = 0;

	public static void main(String[] args) {
		String[] input = readInput();
		ArrayList<Node> nodes = processInput(input);
		int sum = calcSum(nodes);
		System.out.println("Sum of all metadata entries: " + sum);
		int value = getNodeValue(nodes.get(0));
		System.out.println("Value of outer node: " + value);
	}

	private static int calcSum(ArrayList<Node> nodes) {
		int sum = 0;
		for (Node n : nodes) {
			sum += calcSum(n.nodes);
			for (Integer i : n.metadata)
				sum += i;
		}
		return sum;
	}

	private static int getNodeValue(Node n) {
		int value = 0;
		if (n.nodes.size() == 0) {
			for (int i = 0; i < n.metadata.size(); i++)
				value += n.metadata.get(i);
		} else {
			for (int i = 0; i < n.metadata.size(); i++) {
				int index = n.metadata.get(i) - 1;
				if (index < n.nodes.size())
					value += getNodeValue(n.nodes.get(index));
			}
		}
		return value;
	}

	private static ArrayList<Node> processInput(String[] input) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(getNode(input));
		return nodes;
	}

	private static Node getNode(String[] input) {
		int remainingSubNodes = getNextValue(input);
		int remainingMetadata = getNextValue(input);
		Node currentNode = new Node(remainingSubNodes, remainingMetadata);
		while (remainingSubNodes > 0) {
			currentNode.nodes.add(getNode(input));
			remainingSubNodes--;
		}
		while (remainingMetadata > 0) {
			currentNode.metadata.add(getNextValue(input));
			remainingMetadata--;
		}
		return currentNode;
	}

	private static int getNextValue(String[] input) {
		int value = Integer.valueOf(input[index]);
		index++;
		return value;
	}

	private static String[] readInput() {
		BufferedReader br;
		String data = "";
		int i = 0;

		try {
			br = new BufferedReader(new FileReader("src/input/inputday8"));
			data = br.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data.split("\\s");
	}

	private static class Node {
		int numNodes, numMeta;
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<Integer> metadata = new ArrayList<Integer>();

		public Node(int numNodes, int numMeta) {
			this.numNodes = numNodes;
			this.numMeta = numMeta;
		}
	}
}
