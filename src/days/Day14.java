package days;

public class Day14 {
	private static int recipesIndex = 0;
	private static byte[] recipes = new byte[1327000000];;

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		// recipes = new byte[1327000000];
		int[] inputArr = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int inputArrIndex = 0;
		Elf[] elves = new Elf[2];
		elves[0] = new Elf(3, 0);
		elves[1] = new Elf(7, 1);
		addDigitToRecipes(3);
		addDigitToRecipes(7);
		while (inputArrIndex < inputArr.length) {
			int sum = elves[0].currentRecipe + elves[1].currentRecipe;
			inputArrIndex = addDigits(sum, inputArr, inputArrIndex);
			getNewCurrentRecipes(elves);
		}
		System.out.println(recipesIndex - inputArr.length);
		long end = System.currentTimeMillis();
		long time = end - start;
		System.out.println("Running time: " + time + "ms");
	}

	private static void getNewCurrentRecipes(Elf[] elves) {
		for (int i = 0; i < elves.length; i++) {
			int nextIndex = elves[i].currentRecipe + 1 + elves[i].currentRecipeIndex;
			while (nextIndex >= recipesIndex)
				nextIndex -= recipesIndex;
			elves[i].currentRecipeIndex = nextIndex;
			elves[i].currentRecipe = (int) recipes[nextIndex];
		}

	}

	private static void addDigitToRecipes(int digit) {
		recipes[recipesIndex] = (byte) digit;
		recipesIndex++;
	}

	private static int addDigits(int sum, int[] inputArr, int inputArrIndex) {
		int tensDigit = 0;
		while (sum >= 10) {
			sum -= 10;
			tensDigit++;
		}
		if (tensDigit > 0 && inputArrIndex < inputArr.length) {
			if (inputArr[inputArrIndex] == tensDigit)
				inputArrIndex++;
			else
				inputArrIndex = 0;
			addDigitToRecipes(tensDigit);
		}
		if (inputArrIndex < inputArr.length) {
			if (inputArr[inputArrIndex] == sum)
				inputArrIndex++;
			else
				inputArrIndex = 0;
			addDigitToRecipes(sum);
		}
		return inputArrIndex;
	}

	private static class Elf {
		int currentRecipe, currentRecipeIndex;

		public Elf(int currentRecipe, int currentRecipeIndex) {
			this.currentRecipe = currentRecipe;
			this.currentRecipeIndex = currentRecipeIndex;
		}

	}

}
