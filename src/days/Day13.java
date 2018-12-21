package days;

import java.awt.*;
import java.io.*;
import java.util.*;

public class Day13 {

	public static void main(String[] args) {
		Character[][] map = readInput();
		ArrayList<Cart> carts = collectCarts(map);
		replaceCartsWithRail(map, carts);
		printMap(map);
		int ticks = 0;
		while (carts.size() > 1) {
			moveCarts(carts, map);
			ticks++;
		}
		System.out.println(
				"Final cart at: x=" + carts.get(0).xPos + " y=" + carts.get(0).yPos + " Num of ticks: " + ticks);
	}

	private static void moveCarts(ArrayList<Cart> carts, Character[][] map) {
		Point p = null;
		orderCarts(carts);
		for (Cart c : carts) {
			p = null;
			c.move(map);
			p = handleCollision(c, carts);
			if (p != null)
				System.out.println("Collision at: x=" + p.getX() + " y=" + p.getY());
		}
		for (int i = carts.size() - 1; i >= 0; i--)
			if (carts.get(i).dead)
				carts.remove(i);
	}

	private static Point handleCollision(Cart c, ArrayList<Cart> carts) {
		for (Cart cart : carts)
			if (!cart.dead && cart != c && cart.xPos == c.xPos && cart.yPos == c.yPos) {
				int x = c.xPos;
				int y = c.yPos;
				cart.dead = true;
				c.dead = true;
				return new Point(x, y);
			}
		return null;
	}

	private static void orderCarts(ArrayList<Cart> carts) {
		Collections.sort(carts, new CartComparator());
	}

	private static class CartComparator implements Comparator<Cart> {
		public int compare(Cart c1, Cart c2) {
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

	private static void printMap(Character[][] map) {
		System.out.print(' ');
		for (int x = 0; x < map[0].length; x++)
			System.out.print(x % 10);
		System.out.println();
		for (int y = 0; y < map.length; y++) {
			System.out.print(y % 10);
			for (int x = 0; x < map[0].length; x++)
				System.out.print(map[y][x]);
			System.out.println();
		}
	}

	private static void replaceCartsWithRail(Character[][] map, ArrayList<Cart> carts) {
		boolean left, top, right, bottom; // connections
		for (Cart c : carts) {
			left = isConnectedToTarget(c.xPos - 1, c.yPos, "right", map);
			top = isConnectedToTarget(c.xPos, c.yPos - 1, "bottom", map);
			right = isConnectedToTarget(c.xPos + 1, c.yPos, "left", map);
			bottom = isConnectedToTarget(c.xPos, c.yPos + 1, "top", map);
			if (left && top && right && bottom)
				map[c.yPos][c.xPos] = '+';
			else if (left && right)
				map[c.yPos][c.xPos] = '-';
			else if (top && bottom)
				map[c.yPos][c.xPos] = '|';
			else if ((top && right) || (left && bottom))
				map[c.yPos][c.xPos] = '\\';
			else if ((bottom & right) || (left && top))
				map[c.yPos][c.xPos] = '/';
		}
	}

	private static boolean isConnectedToTarget(int xPos, int yPos, String target, Character[][] map) {
		if (xPos >= 0 && xPos < map[0].length && yPos >= 0 && yPos < map.length) {
			Character c = map[yPos][xPos];
			switch (c) {
			case '\\':
				if (target == "left" && isConnectedToTarget(xPos, yPos + 1, "top", map))
					return true;
				else if (target == "top" && isConnectedToTarget(xPos + 1, yPos, "left", map))
					return true;
				else if (target == "right" && isConnectedToTarget(xPos, yPos - 1, "bottom", map))
					return true;
				else if (target == "bottom" && isConnectedToTarget(xPos - 1, yPos, "right", map))
					return true;
				else
					return false;
			case '/':
				if (target == "left" && isConnectedToTarget(xPos, yPos - 1, "bottom", map))
					return true;
				else if (target == "top" && isConnectedToTarget(xPos - 1, yPos, "right", map))
					return true;
				else if (target == "right" && isConnectedToTarget(xPos, yPos + 1, "top", map))
					return true;
				else if (target == "bottom" && isConnectedToTarget(xPos + 1, yPos, "left", map))
					return true;
				else
					return false;
			case '|':
				return target == "bottom" || target == "top";
			case '-':
				return target == "left" || target == "right";
			case '+':
				return true;
			case ' ':
				return false;
			default:
				return false;
			}
		} else
			return false;
	}

	private static ArrayList<Cart> collectCarts(Character[][] map) {
		ArrayList<Cart> carts = new ArrayList<Cart>();
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				Character c = map[y][x];
				if (c == '^' || c == '<' || c == '>' || c == 'v')
					carts.add(new Cart(x, y, c));
			}
		}
		return carts;
	}

	private static class Cart {
		int xPos, yPos;
		Character nextDir = '<';
		Character orientation;
		boolean dead = false;

		private Cart(int xPos, int yPos, Character orientation) {
			this.xPos = xPos;
			this.yPos = yPos;
			this.orientation = orientation;
		}

		public void move(Character[][] map) {
			switch (orientation) {
			case '<':
				this.xPos--;
				break;
			case '^':
				this.yPos--;
				break;
			case '>':
				this.xPos++;
				break;
			case 'v':
				this.yPos++;
			}
			Character c = map[this.yPos][this.xPos];
			switch (c) {
			case '+':
				this.changeOrientation();
				break;
			case '/':
			case '\\':
				this.changeOrientation(c);
			}
		}

		private void changeOrientation(Character c) {
			if (c == '/') {
				switch (orientation) {
				case '<':
					orientation = 'v';
					break;
				case '^':
					orientation = '>';
					break;
				case '>':
					orientation = '^';
					break;
				case 'v':
					orientation = '<';
				}
			} else if (c == '\\') {
				switch (orientation) {
				case '<':
					orientation = '^';
					break;
				case '^':
					orientation = '<';
					break;
				case '>':
					orientation = 'v';
					break;
				case 'v':
					orientation = '>';
				}
			}

		}

		private void changeOrientation() {
			char[] dirs = { '<', '^', '>', 'v' };
			int index = 0;
			for (int i = 0; i < dirs.length; i++)
				if (dirs[i] == orientation)
					index = i;
			switch (nextDir) {
			case '<':
				index--;
				if (index < 0)
					index = dirs.length - 1;
				nextDir = '^';
				break;
			case '^':
				nextDir = '>';
				break;
			case '>':
				index++;
				if (index > dirs.length - 1)
					index = 0;
				nextDir = '<';
			}
			orientation = dirs[index];
		}
	}

	private static Character[][] readInput() {
		BufferedReader br;
		ArrayList<String> data = new ArrayList<String>();

		try {
			br = new BufferedReader(new FileReader("src/input/inputday13"));
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
