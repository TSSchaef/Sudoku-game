package application;

import java.util.HashSet;

public final class Utility {
	private Utility(){}//prevent instantiation
	
	public static boolean validatePuzzle(char[][] puzzle)
	{
		HashSet<String> seen = new HashSet<String>();
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				char curr = puzzle[i][j];
				
				if(curr != '\0') {
					if (! (seen.add(curr + "found in row" + i) &&
							seen.add(curr + "found in column" + j) &&
							seen.add(curr + "found in box" + i/3 + "-" + j/3))){
						
						return false;
					}
				}				
			}
		}
		return true;
	}
	
	public static boolean validEntry(int x, int y, char[][] puzzle)
	{
		HashSet<String> seen = new HashSet<String>();
		for(int i = 0; i < 9; i++) {
			char rowChars = puzzle[i][y];
			char columnChars = puzzle[x][i];
			
			if(rowChars != '\0') {
				if (!(seen.add(rowChars + "found in row"))){
					return false;
				}
			}
			
			if(columnChars != '\0') {
				if (!(seen.add(columnChars + "found in column"))){
					return false;
				}
			}
		}
		
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				char boxChars = puzzle[(x/3) + i][(y/3) + j];
			
				if(boxChars != '\0') {
					if (!(seen.add(boxChars + "found in box"))){
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public static void copyTo(char[][] arr1, char[][] arr2)
	{
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				arr1[i][j] = arr2[i][j];
			}
		}
	}

	public static String formatTime(int Time)
	{
		String text;
			if(Time < 3600) {	
				int sec = Time % 60;
				int min = Time / 60;
				text = Integer.toString(min) + ":" + String.format("%02d", sec);
			}
			else {
				int sec = Time % 60;
				int min = (Time / 60) % 60;
				int hr = Time / 3600;
				text = Integer.toString(hr) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec);
			}
		return Time >= 0? text: "";
	}
}
