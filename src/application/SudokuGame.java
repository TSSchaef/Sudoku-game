package application;

import java.util.Random;
import java.util.Stack;

public class SudokuGame {
	
	public final static int GRID_SIZE = 9;
	
	private Stack<char[][]> history = new Stack<char[][]>();
	
	private char[][] completeArr = new char[GRID_SIZE][GRID_SIZE];
	private char[][] givenArr = new char[GRID_SIZE][GRID_SIZE];
	private char[][] currentArr = new char[GRID_SIZE][GRID_SIZE];
	
	private int[] lastUpdated = new int[2]; 
	private int numHints;
	private String Difficulty;
	
	public SudokuGame(String difficulty)
	{
		Difficulty = difficulty;
		generateSolvedBoard();
		Utility.copyTo(givenArr, completeArr);
		generatePuzzle(setPuzzleConditions(difficulty));
		Utility.copyTo(currentArr, givenArr);
	}
	
	public void restartGame()
	{
		Utility.copyTo(currentArr, givenArr);
	}
	
	private void generateSolvedBoard()
	{
		Random rand = new Random();	
		rand.setSeed(System.currentTimeMillis());
		int attempts = 0;
				
		for(int i = 0; i < GRID_SIZE; i++) {
			for(int j = 0; j < GRID_SIZE; j++) {	
				int curr = (rand.nextInt(1, GRID_SIZE + 1));
				attempts = 0;
				completeArr[i][j] = (char)(curr + '0');	
				
				while (!Utility.validatePuzzle(completeArr)) {
					curr  = ((curr % GRID_SIZE) + 1) ;
					completeArr[i][j] = (char)(curr + '0');
					attempts++;
					if(attempts >= GRID_SIZE) {
						int k;
						for (k = j; k >= 0; k--) {
							completeArr[i][k] = '\0';
						}
						j = 0;
						break;
					}
				}
				completeArr[i][j] = (char)(curr + '0');
			}
		}
	}
	
	private void generatePuzzle(int numToRemove)
	{
		Random rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		while(numToRemove > 0) {
			int x = rand.nextInt(0, GRID_SIZE);
			int y = rand.nextInt(0, GRID_SIZE);
			
			if(givenArr[x][y] != '\0') {
				givenArr[x][y] = '\0';
				numToRemove--;
			}
		}
	}
	
	/*TO DO private boolean alternateSolutionExists(char[][] puzzle)
	{
		//sudoku solver, redo recursively(?)
	}
	*/
	
	public void undo() {
		if(!history.empty()) {
			Utility.copyTo(currentArr, history.pop());
		}
	}
	
	private int setPuzzleConditions(String difficulty)
	{
		int numToRemove;
		switch (difficulty) {
		case "Beginner": 
			numToRemove = 40;
			numHints = 90;
			break;
		case "Intermediate":
			numToRemove = 55;
			numHints = 5;
			break;
		case "Advanced":
			numToRemove = 58;
			numHints = 3;
			break;
		case "Expert":
			numToRemove = 60;
			numHints = 0;
			break;
		default:
			numToRemove = 55;
		}
		return numToRemove;
	}
	
	public void updateCurrentArr(int i, int j, char c)
	{	
		char[][] saveState = new char [GRID_SIZE][GRID_SIZE];
		Utility.copyTo(saveState, currentArr);
		history.add(saveState);
		lastUpdated[0] = i;
		lastUpdated[1] = j;
		currentArr[i][j] = c;
	}
	
	public boolean checkCurrentArr(int i, int j, char c)
	{
		return currentArr[i][j] == c;
	}
	
	public boolean checkCompleteArr(int i, int j, char c)
	{
		return completeArr[i][j] == c;
	}
	
	public boolean useHint()
	{
		boolean hintGiven = false;
		numHints--;
		Random rand = new Random();	
		rand.setSeed(System.currentTimeMillis());
		
		//check if puzzle is solved
		boolean puzzleFinished = true;
		outerloop:
		for(int i = 0; i < GRID_SIZE; i++) {
			for(int j = 0; j < GRID_SIZE; j++) {
				if(currentArr[i][j]=='\0') {
					puzzleFinished = false;
					break outerloop;
				}
			}
		}
		
		if(!puzzleFinished && numHints >= 0) {
			int i = (rand.nextInt(0, GRID_SIZE));
			int j = (rand.nextInt(0, GRID_SIZE));
			
			while(currentArr[i][j] != '\0'){
				i = (i + 1) % GRID_SIZE;
				if(i == 0)j = (j + 1) % GRID_SIZE;
			}
			hintGiven = true;
			lastUpdated[0] = i;
			lastUpdated[1] = j;
			givenArr[i][j] = completeArr[i][j];
			currentArr[i][j] = completeArr[i][j];
		}		
		return hintGiven;
	}

	public int[] lastUpdated()
	{
		return lastUpdated;
	}
	
	public void setNumHints(int numHints)
	{
		this.numHints = numHints;
	}
	
	public int getNumHints()
	{
		return numHints;
	}
	
	public char[][] getCurrentArr()
	{
		return currentArr;
	}
	
	public boolean currentArrFull()
	{
		for(int i = 0; i < GRID_SIZE; i++) {
			for(int j = 0; j < GRID_SIZE; j ++) {
				if(currentArr[i][j] == '\0') {
					return false;
				}
			}
		}
		return true;
	}
	
	public char[][] getCompleteArr()
	{
		return completeArr;
	}
	
	public char[][] getGivenArr()
	{
		return givenArr;
	}

	public String getDifficulty()
	{
		return Difficulty;
	}
}
