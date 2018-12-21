package days;

import java.io.*;
import java.util.*;
import java.util.Map.*;
import java.util.regex.*;

public class Day4 {

	public static void main(String[] args) {
		ArrayList<String> input = readInput();
		input.sort(null);
		HashMap<Integer, Guard> guards = new HashMap<Integer, Guard>(10000);
		fillMap(input, guards);
		Iterator<Entry<Integer, Guard>> it = guards.entrySet().iterator();
		int residentSleeperId = 0;
		int maxSleep = 0;
		while (it.hasNext()) {
			Map.Entry<Integer, Guard> entry = it.next();
			Guard guard = entry.getValue();
			if (guard.timeSlept > maxSleep) {
				residentSleeperId = entry.getKey();
				maxSleep = guard.timeSlept;
			}
		}
		Guard residentSleeper = guards.get(residentSleeperId);
		int[] minutesAmount = new int[60];
		Arrays.fill(minutesAmount, 0);
		for (Shift shift : residentSleeper.sh) {
			for (int i = 0; i < minutesAmount.length; i++) {
				minutesAmount[i] += shift.minutesAsleep[i];
			}
		}
		int minuteMostSlept = 0;
		for (int i = 0; i < minutesAmount.length; i++) {
			if (minutesAmount[i] > minutesAmount[minuteMostSlept])
				minuteMostSlept = i;
		}
		int solution = residentSleeperId * minuteMostSlept;
		System.out.println("ID: " + residentSleeperId + "\nTime slept: " + residentSleeper.timeSlept
				+ "\nMinute most slept: " + minuteMostSlept + "\nPart 1 Solution: " + solution);
		part2(guards);

	}

	private static void part2(HashMap<Integer, Guard> guards) {
		Iterator<Entry<Integer, Guard>> it = guards.entrySet().iterator();
		int maxSleep = 0;
		int id = 0;
		int minuteMostSlept = 0;
		while (it.hasNext()) {
			Map.Entry<Integer, Guard> entry = it.next();
			Guard guard = entry.getValue();
			int[] minutesAmount = new int[60];
			Arrays.fill(minutesAmount, 0);
			for (Shift shift : guard.sh) {
				for (int i = 0; i < minutesAmount.length; i++) {
					minutesAmount[i] += shift.minutesAsleep[i];
				}
			}
			for (int i = 0; i < minutesAmount.length; i++) {
				if (minutesAmount[i] > maxSleep) {
					minuteMostSlept = i;
					maxSleep = minutesAmount[i];
					id = entry.getKey();
				}
			}
		}
		int solution = id * minuteMostSlept;
		System.out.println("ID: " + id + "\nTime slept that minute: " + maxSleep + "\nMinute most slept: "
				+ minuteMostSlept + "\nPart 2 Solution: " + solution);

	}

	private static void fillMap(ArrayList<String> input, HashMap<Integer, Guard> guards) {
		Pattern idPattern = Pattern.compile("#(\\d+)");
		Pattern minutesPattern = Pattern.compile("00:(\\d\\d)");
		int id = 0;
		int[] minutesAsleep = new int[60];
		int minuteIndex = 0;
		String lastCommand = "awake";

		for (int i = 0; i < input.size(); i++) {
			String line = input.get(i);
			Matcher idMatcher = idPattern.matcher(line);
			if (idMatcher.find() || i == input.size() - 1) { // begins shift
				if (i > 0) {
					int maxIndex = 60;
					int sleepValue = lastCommand == "awake" ? 0 : 1;
					if (i == input.size() - 1)
						sleepValue = line.contains("falls") ? 0 : 1;
					while (minuteIndex < maxIndex) {
						minutesAsleep[minuteIndex] = sleepValue;
						minuteIndex++;
					}
					Shift shift = new Shift(id, minutesAsleep);
					if (!guards.containsKey(id)) {
						Guard guard = new Guard();
						guard.timeSlept = shift.totalMinutesAsleep;
						guard.sh.add(shift);
						guards.put(id, guard);
					} else {
						Guard guard = guards.get(id);
						guard.timeSlept += shift.totalMinutesAsleep;
						guard.sh.add(shift);
						guards.put(id, guard);
					}
					minutesAsleep = new int[60];
					minuteIndex = 0;
				}
				if (i != input.size() - 1) {
					id = Integer.valueOf(idMatcher.group(1));
					lastCommand = "awake";
				}
			} else {
				Matcher minutesMatcher = minutesPattern.matcher(line);
				minutesMatcher.find();
				int index = Integer.valueOf(minutesMatcher.group(1));
				int sleepValue = line.contains("falls") ? 0 : 1;
				lastCommand = line.contains("falls") ? "sleep" : "awake";
				while (minuteIndex < index) {
					minutesAsleep[minuteIndex] = sleepValue;
					minuteIndex++;
				}
			}
		}
	}

	private static ArrayList<String> readInput() {
		BufferedReader br;
		ArrayList<String> data = new ArrayList<String>();

		try {
			br = new BufferedReader(new FileReader("src/input/inputday4"));
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

	private static class Guard {
		int timeSlept = 0;
		ArrayList<Shift> sh = new ArrayList<Shift>();
	}

	private static class Shift {
		int id, totalMinutesAsleep;
		int[] minutesAsleep;// = new int[60];

		public Shift(int id, int[] minutesAsleep) {
			this.id = id;
			this.minutesAsleep = minutesAsleep;
			this.totalMinutesAsleep = calc(minutesAsleep);
		}

		static int calc(int[] minutes) {
			int acc = 0;
			for (int i = 0; i < minutes.length; i++) {
				if (minutes[i] == 1)
					acc++;
			}
			return acc;
		}
	}
}
