package days;

import java.io.*;
import java.util.*;
import java.util.Map.*;
import java.util.regex.*;

public class Day7 {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		ArrayList<String> input = readInput();
		HashMap<String, Step> steps = processInput(input);
		ArrayList<String> orderedSteps = new ArrayList<String>();
		while (orderedSteps.size() < steps.size()) {
			ArrayList<String> newSteps = getAvailableSteps(orderedSteps, orderedSteps, steps);
			orderedSteps.add(newSteps.get(0));
		}
		orderedSteps.forEach((s) -> System.out.print(s));
		System.out.println();

		ArrayList<String> completedSteps = new ArrayList<String>();
		ArrayList<String> busySteps = new ArrayList<String>();
		ArrayList<Worker> workers = new ArrayList<Worker>();
		for (int i = 0; i < 5; i++)
			workers.add(new Worker());
		int time = 0;

		while (completedSteps.size() < steps.size()) {
			ArrayList<String> availableSteps = getAvailableSteps(completedSteps, busySteps, steps);
			int nextStepIndex = 0;
			for (int i = 0; i < workers.size(); i++) {
				Worker w = workers.get(i);
				if (!w.isBusy() && nextStepIndex < availableSteps.size()) {
					w.step = availableSteps.get(nextStepIndex);
					w.timeReq = steps.get(w.step).timeReq;
					busySteps.add(w.step);
					nextStepIndex++;
				}
				if (w.isBusy()) {
					if (w.progressTask()) {
						completedSteps.add(w.step); // finished task
						w.free();
					}
				}
			}
			time++;
		}
		System.out.println("Time required with five workers: " + time);
		long end = System.currentTimeMillis();
		long runningtime = end - start;
		System.out.println("Running time: " + runningtime + "ms");

	}

	private static ArrayList<String> getAvailableSteps(ArrayList<String> completedSteps,
			ArrayList<String> unavailableSteps, HashMap<String, Step> steps) {
		Iterator<Entry<String, Step>> it = steps.entrySet().iterator();
		ArrayList<String> availableSteps = new ArrayList<String>();
		while (it.hasNext()) {
			Map.Entry<String, Step> entry = it.next();
			String name = entry.getKey();
			if (unavailableSteps.contains(name))
				continue;
			Step s = entry.getValue();
			if (conditionsMet(s, completedSteps))
				availableSteps.add(name);
		}
		availableSteps.sort(null);
		return availableSteps;
	}

	private static boolean conditionsMet(Step s, ArrayList<String> completedSteps) {
		return completedSteps.containsAll(s.conditions);
	}

	private static HashMap<String, Step> processInput(ArrayList<String> input) {
		HashMap<String, Step> steps = new HashMap<String, Step>(26);

		for (int i = 65; i <= 90; i++) {
			Character c = (char) i;
			Step s = new Step();
			s.timeReq = i - 4;
			steps.put(c.toString(), s);
		}

		Pattern p = Pattern.compile("p\\s(?<condition>.)\\sm.+p\\s(?<step>.)");
		for (String string : input) {
			Matcher m = p.matcher(string);
			m.find();
			String rxCondition = m.group("condition");
			String rxStep = m.group("step");
			Step step = steps.get(rxStep);
			step.conditions.add(rxCondition);
			steps.put(rxStep, step);
		}

		return steps;
	}

	private static ArrayList<String> readInput() {
		BufferedReader br;
		ArrayList<String> data = new ArrayList<String>();

		try {
			br = new BufferedReader(new FileReader("src/input/inputday7"));
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

	private static class Step {
		int timeReq = 0;
		ArrayList<String> conditions = new ArrayList<String>();
	}

	private static class Worker {
		String step = "";
		int timeReq = 0;

		public boolean isBusy() {
			return step != "";
		}

		public void free() {
			step = "";
		}

		public boolean progressTask() {
			timeReq--;
			return timeReq <= 0;
		}
	}

}
