package floors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/*
 * by: Matthew Milum
 */


public class Room {
	/*
	 * this class represents rooms the individual rooms in a floor
	 */
	//declaring instance variables
	private int[][] tiles;
	private char entrance, exit;
	//a constructor that takes the path and width of the file that should be loaded
	public Room(String path, int width){
		String fileText="";//the entire text of the file
		String[] entries;//an array that holds all the individual entries the file should hold
		tiles=new int[width][width];
		try{
			File file = new File(path);//creating a file object that stores the actual file
			Scanner scanner = new Scanner(file);//a scanner that can read the data in the file
			while (scanner.hasNextLine()) {//looping until the end of the file is reached
				fileText+=scanner.nextLine();//adding the data into the fileText string
				
			}
			scanner.close();//closing the scanner
		}catch (FileNotFoundException e) {//catching the error
			System.out.println("could not find the file '"+path+"'");//saying what file could not be loaded
			e.printStackTrace();//printing the error
		}
		entries=fileText.split("\\s+");//splitting the String every blank character so that I can get each entry in the text
		entrance=entries[0].charAt(0);
		exit=entries[1].charAt(0);
		for(int y=0;y<width;y++){
				for(int x=0;x<width;x++){
					tiles[x][y]=Integer.parseInt(entries[(y*width)+x]);
			}
		}

		
	}
}
