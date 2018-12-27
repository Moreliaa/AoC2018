package days;

import java.io.*;
import java.util.*;
import java.awt.*;

public class Day15 {
	private static Character[][] input;
	private static ArrayList<Dude> dudes;
	private static ArrayList<Dude> elves = new ArrayList<Dude>();
	private static ArrayList<Dude> goblins = new ArrayList<Dude>();
	private static int boost = 22;

	public static void main(String[] args) {
		input = readInput();
		dudes = collectDudes();
		int rounds = 0;
		while (true) {
			printMap();
			boolean finished = turn(dudes);
			if (finished)
				break;
			rounds++;
			try {Thread.sleep(100);} catch (Exception e) {}
		}
		ArrayList<Dude> winners;
		String team;
		if (elves.size() > 0) {
			winners = elves;
			team = "Elves";
		} else {
			winners = goblins;
			team = "Goblins";
		}
		int hp = 0;
		for (Dude d:winners)
			hp += d.hp;
		System.out.println("Combat ends after "+ rounds +" full rounds");
		System.out.println(team+" win with "+hp+" total hit points left");
		System.out.println("Outcome: " +(rounds * hp));

	}

	private static boolean turn(ArrayList<Dude> dudes) {
		orderDudes(dudes);
		for (Dude d : dudes) {
			if (d.dead()) {
				if (d.type == 'E')
					return true;
				else
					continue;
			}
			// find targets
			ArrayList<Dude> targets;
			if (d.type == 'E')
				targets = goblins;
			else
				targets = elves;
			if (targets.size() == 0)
				return true;
			// find . squares next to targets
			ArrayList<Point> openSquares = new ArrayList<Point>();
			Dude targetInRange = null;
			for (Dude t: targets) {
				if (d.isInRangeOf(t)) {
					if (targetInRange == null || t.hp < targetInRange.hp)
						targetInRange = t;
				}
				addSquare(openSquares, t.xPos,t.yPos-1);
				addSquare(openSquares, t.xPos-1, t.yPos);
				addSquare(openSquares, t.xPos+1, t.yPos);
				addSquare(openSquares, t.xPos, t.yPos+1);
			}

			if (targetInRange != null) {
				d.attack(targetInRange);
			} else {
				// move
				int[][][] distanceGrids = makeDistanceGrids(d, openSquares);
				d.move(openSquares, distanceGrids);
				//try attack
				for (Dude t: targets) {
					if (d.isInRangeOf(t)) {
						if (targetInRange == null || t.hp < targetInRange.hp)
							targetInRange = t;
					}
				}
				if (targetInRange != null) {
					d.attack(targetInRange);
				}
			}
		}
		return false;
	}

	private static int[][][] makeDistanceGrids(Dude d, ArrayList<Point> openSquares) {
		int[][][] grid = new int[openSquares.size()][input.length][input[0].length];
		for (int a = 0; a < openSquares.size(); a++) {
			grid[a] = getGrid(d, openSquares.get(a));
		}
		return grid;
	}

	private static int[][] getGrid(Dude d, Point p) {
		int[][] grid = new int[input.length][input[0].length];
		for (int i = 0; i < grid[0].length; i++) {
			Arrays.fill(grid[i], -1);
		}
		HashMap<String,Integer> seen = new HashMap<String,Integer>(500);
		bfs(d, grid, seen, p);
		return grid;
	}

	private static boolean put(Dude d, int x, int y, int[][] grid, int dist, HashMap<String, Integer> seen) {
		if((input[y][x] == '.' || (d.xPos == x && d.yPos == y)) &&
		 !seen.containsKey(x+","+y)) {
			grid[y][x] = dist;
			seen.put(x+","+y,0);
			return true;
		}
		return false;
	}

	private static void bfs(Dude d, int[][] grid, HashMap<String,Integer> seen, Point p) {
		ArrayList<Point> next = new ArrayList<Point>(5);
		ArrayList<Point> current = new ArrayList<Point>();
		current.add(p);
		int distance = 0;
		do {
			for (int i = 0; i < current.size(); i++) {
				Point cur = current.get(i);
				int x = (int) cur.x;
				int y = (int) cur.y;		
				if(input[y][x] == '.' || (d.xPos == x && d.yPos == y)) {				
					put(d, x,y,grid, distance,seen);
					if (put(d,x,y-1,grid,distance+1,seen))
						next.add(new Point(x,y-1));
					if (put(d,x-1,y,grid,distance+1,seen))
						next.add(new Point(x-1,y));
					if (put(d,x+1,y,grid,distance+1,seen))
						next.add(new Point(x+1,y));
					if (put(d,x,y+1,grid,distance+1,seen))
						next.add(new Point(x,y+1));
				}
			}
				distance++;
				current = new ArrayList<Point>(next);
				next = new ArrayList<Point>();
		} while (current.size() > 0);
	}

	private static void addSquare(ArrayList<Point> openSquares, int x, int y) {
		if (input[y][x] != '.')
			return;
		boolean contained = false;
		for (Point p: openSquares) {
			if (p.getX() == x && p.getY() == y) {
				contained = true;
				break;
			}
		}
		if(!contained)
			openSquares.add(new Point(x,y));
	}

	private static class Dude {
		char type;
		int xPos, yPos;
		int hp = 200, atk = 3;

		public Dude(char type, int xPos, int yPos) {
			this.type = type;
			this.xPos = xPos;
			this.yPos = yPos;
			if (this.type == 'E')
				this.atk += boost;
		}

		public void move(ArrayList<Point> squares, int[][][] distanceGrids) {
			int shortest = 9999;
			int index = -1;
			for (int i = 0; i < squares.size(); i++) {
				int[][] grid = distanceGrids[i];
				int dist = grid[yPos][xPos];
				if (dist != -1 && dist < shortest){
					index = i;
					shortest = dist;
				} else if (dist == shortest) {
					Point currP = squares.get(index);
					Point newP = squares.get(i);
					if (newP.y < currP.y || (newP.y == currP.y && newP.x < currP.x)){
						index = i;
						shortest = dist;
					}
				}
			}
			if (index == -1) {
				System.out.println(type+" at " +xPos+","+yPos+" could not find path");
				return;
			}
			int[][] grid = distanceGrids[index];
			shortest = grid[yPos-1][xPos];
			int newX = xPos, newY = yPos-1;
			if (shortest == -1 || (grid[yPos][xPos-1] < shortest && grid[yPos][xPos-1] != -1)) {
				newX = xPos - 1;
				newY = yPos;
				shortest = grid[newY][newX];
			}
			if (shortest == -1 || (grid[yPos][xPos+1] < shortest && grid[yPos][xPos+1] != -1)) {
				newX = xPos + 1;
				newY = yPos;
				shortest = grid[newY][newX];
			}
			if (shortest == -1 || (grid[yPos+1][xPos] < shortest && grid[yPos+1][xPos] != -1)){
				newX = xPos;
				newY = yPos + 1;
				shortest = grid[newY][newX];
			}
			if (shortest != -1) {
				input[yPos][xPos] = '.';
				this.xPos = newX;
				this.yPos = newY;
				input[yPos][xPos] = this.type;
			}
			
		}

		public boolean dead() {
			return hp <= 0;
		}

		public boolean isInRangeOf(Dude d) {
			return calcManhattan(this.xPos,this.yPos,d.xPos,d.yPos)<=1;
		}

		public void attack(Dude d) {
			d.hp -= this.atk;
			if (d.dead()) {
				System.out.println(d.type+" at " +d.xPos+","+d.yPos+" slain");
				input[d.yPos][d.xPos] = '.';
				if (d.type == 'E')
					elves.remove(d);
				else
					goblins.remove(d);
			}
		}

	}

	private static int calcManhattan(int x1,int y1,int x2,int y2) {
		return Math.abs(x1-x2)+Math.abs(y1-y2);
	}

	private static void orderDudes(ArrayList<Dude> dudes) {
		Collections.sort(dudes, new DudeComparator());
		Collections.sort(elves, new TargetComparator());
		Collections.sort(goblins, new TargetComparator());
	}

	private static class DudeComparator implements Comparator<Dude> {
		public int compare(Dude c1, Dude c2) {
			if (c1.yPos < c2.yPos)
				return -1;
			else if (c1.yPos == c2.yPos && c1.xPos < c2.xPos)
				return -1;
			else if (c1.yPos == c2.yPos && c1.xPos == c2.xPos)
				return 0;
			else
				return 1;
		}
	}

	private static class TargetComparator implements Comparator<Dude> {
		public int compare(Dude c1, Dude c2) {
			if (c1.hp < c2.hp)
				return -1;
			else if (c1.hp == c2.hp && c1.yPos < c2.yPos)
				return -1;
			else if (c1.hp == c2.hp && c1.yPos == c2.yPos && c1.xPos < c2.xPos)
				return -1;
			else
				return 1;
		}
	}

	private static ArrayList<Dude> collectDudes() {
		ArrayList<Dude> dudes = new ArrayList<Dude>();
		for (int y = 0; y < input.length; y++) {
			for (int x = 0; x < input[0].length; x++) {
				char c = input[y][x];
				if (c == 'G' || c == 'E') {
					Dude d = new Dude(c, x, y);
					dudes.add(d);
					if (c =='G')
						goblins.add(d);
					if(c == 'E')
						elves.add(d);
				}
			}
		}
		return dudes;
	}

	private static void printMap() {
		for (int y = 0; y < input.length; y++) {
			for (int x = 0; x < input[0].length; x++)
				System.out.print(input[y][x]);
			System.out.println();
		}
	}

	private static Character[][] readInput() {
		BufferedReader br;
		ArrayList<String> data = new ArrayList<String>();

		try {
			br = new BufferedReader(new FileReader("src/input/inputday15"));
			String line = br.readLine();
			while (line != null) {
				data.add(line);
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Character[][] map = new Character[data.size()][data.get(0).length()];
		for (int y = 0; y < map.length; y++) {
			String line = data.get(y);
			for (int x = 0; x < map[0].length; x++)
				map[y][x] = line.charAt(x);
		}

		return map;
	}

}
