import java.util.Date;
import java.awt.*;
import java.util.Scanner;
import java.util.Random;
import java.io.*; 
import java.util.Arrays;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Class {
	public static void runGC() {
      Runtime runtime = Runtime.getRuntime();
      long memoryMax = runtime.maxMemory();
      long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
      double memoryUsedPercent = (memoryUsed * 100.0) / memoryMax;
      System.out.println("Total MB used are  " + (long)memoryUsed/1024/1024);
      if (memoryUsedPercent > 90.0)
         System.gc();
  	}
	public static class cell {
		int row;
		int col;
	}
	public static int numNeighbors(int[][] array, int Dimension,int row, int col) { //counting the number of neighbhors having a mine
		int count=0;
		if(row-1>=0 && col-1>=0 && array[row-1][col-1]==9) {  //top left cell
			count+=1;
		}
		if(row-1>=0 && array[row-1][col]==9) { //up cell
			count+=1;
		}
		if(row-1>=0 && col+1<Dimension && array[row-1][col+1]==9) {  //top right cell
			count+=1;
		}
		if(col-1>=0 && array[row][col-1]==9) {  //left cell
			count+=1;
		}
		if(col+1<Dimension && array[row][col+1]==9) {  //right cell
			count+=1;
		}
		if(row+1<Dimension && col-1>=0 && array[row+1][col-1]==9) {  //bottom left cell
			count+=1;
		}
		if(row+1<Dimension && array[row+1][col]==9) {  //down cell
			count+=1;
		}
		if(row+1<Dimension && col+1<Dimension && array[row+1][col+1]==9) {  //bottm right cell
			count+=1;
		}
		return count;
	}

	public static void fill(int[][] array, int Dimension) {  //initalizing tempBoard with -1s where -1 mean an unidentified cell
		for(int i=0;i<Dimension;i++) {
			for(int j=0;j<Dimension;j++) {
				array[i][j]=-1;  
			}
		}
	}
	
	public static int[][] mineArray(int Dimension,int mines) {   //creating the minesweeper board
		int[][] dArray=new int[Dimension][Dimension]; //creating a dxd array
		Random rand=new Random();
		int upperBound=Dimension;
		int count=0;  //counter to keep track of mines that have been deployed
		while(count<mines) {  //filling array randomly with mines
			int random_row = rand.nextInt(upperBound);  //generating random row number
			int random_col = rand.nextInt(upperBound);  //generating random col number
			if(dArray[random_row][random_col]!=9) { //if cell already has mine then we restart loop and don't increase mineCount
				//Since a cell can have atmost 8 neighbors and only the safe cell reveal the mineCount, therefore I chose 9 to be a mineCell
				dArray[random_row][random_col]=9; 
				count+=1; //A mine is deployed so mineCount is incremented
			}
		}
		//for all cells except mines, updating their value to the number of mine neighbors
		for(int i=0;i<Dimension;i++) {
			for(int j=0;j<Dimension;j++) {
				if(dArray[i][j]!=9) { //a mine cell doesnt reveal any clue so it is discarded
					dArray[i][j]=numNeighbors(dArray,Dimension,i,j); //checking the number of mine neighbors
				}
			}
		}

		return dArray; 
	}

	public static cell checkArraySafe(int[][] array, int Dimension) { //checking if an identified safe cell is present which has not been explored yet
		cell temp = new cell();
		temp.row=-1;
		temp.col=-1;
		for(int i=0;i<Dimension;i++) {
			for(int j=0;j<Dimension;j++) {
				if(array[i][j]==2 || array[i][j]==3) {  // 2 means that cell is safe but not explored yet
					temp.row=i;
					temp.col=j;
					return temp;
				}
			}
		}
		return temp;  //this will return -1 if there is no identified safe cell
	}

	public static cell checkArraynew(int [][] tempboard,int Dimension,int type) {
		cell temp = new cell();
		temp.row=-1;
		temp.col=-1;
		//type 3 means that there is a newly founded safe cell
		//type -2 means that there is a newly founded mine cell
		for(int i=0;i<Dimension;i++) {
			for(int j=0;j<Dimension;j++) {
				if(tempboard[i][j]==type) {  //
					temp.row=i;
					temp.col=j;
					return temp;
				}
			}
		}
		return temp;
	}

	public static int totalNeighbors(int Dimension, cell c) {
		int row = c.row;
		int col = c.col;
		int count=0;
		if(row-1>=0 && col-1>=0) {  //top left cell
			count+=1;
		}
		if(row-1>=0) { //up cell
			count+=1;
		}
		if(row-1>=0 && col+1<Dimension) {  //top right cell
			count+=1;
		}
		if(col-1>=0) {  //left cell
			count+=1;
		}
		if(col+1<Dimension) {  //right cell
			count+=1;
		}
		if(row+1<Dimension && col-1>=0 ) {  //bottom left cell
			count+=1;
		}
		if(row+1<Dimension) {  //down cell
			count+=1;
		}
		if(row+1<Dimension && col+1<Dimension) {  //bottm right cell
			count+=1;
		}
		return count;
	}
	public static int identifyMines(int[][] array,int Dimension,cell c) {
		//checking 0 for the basic Algorithm
		//for improved Algorithm, we have another number -2 which indicates a mine
		int row=c.row;
		int col=c.col;
		int count=0;
		if(row-1>=0 && col-1>=0 && (array[row-1][col-1]==0||array[row-1][col-1]==-2)) {  //top left cell
			count+=1;
		}
		if(row-1>=0 && (array[row-1][col]==0||array[row-1][col]==-2)) { //up cell
			count+=1;
		}
		if(row-1>=0 && col+1<Dimension && (array[row-1][col+1]==0||array[row-1][col+1]==-2)) {  //top right cell
			count+=1;
		}
		if(col-1>=0 && (array[row][col-1]==0||array[row][col-1]==-2)) {  //left cell
			count+=1;
		}
		if(col+1<Dimension && (array[row][col+1]==0||array[row][col+1]==-2)) {  //right cell
			count+=1;
		}
		if(row+1<Dimension && col-1>=0 && (array[row+1][col-1]==0||array[row+1][col-1]==-2)) {  //bottom left cell
			count+=1;
		}
		if(row+1<Dimension && (array[row+1][col]==0||array[row+1][col]==-2)) {  //down cell
			count+=1;
		}
		if(row+1<Dimension && col+1<Dimension && (array[row+1][col+1]==0||array[row+1][col+1]==-2)) {  //bottm right cell
			count+=1;
		}
		return count; //mine neighbors //neighbors which have a mine
	}

	public static int indentifySafe(int[][] array,int Dimension,cell c) {
		int row=c.row;
		int col=c.col;
		int count=0;
		//safe cells are either 1 or 2 and all other cells are less than 1
		if(row-1>=0 && col-1>=0 && array[row-1][col-1]>=1) {  //top left cell 
			count+=1;
		}
		if(row-1>=0 && array[row-1][col]>=1) { //up cell
			count+=1;
		}
		if(row-1>=0 && col+1<Dimension && array[row-1][col+1]>=1) {  //top right cell
			count+=1;
		}
		if(col-1>=0 && array[row][col-1]>=1) {  //left cell
			count+=1;
		}
		if(col+1<Dimension && array[row][col+1]>=1) {  //right cell
			count+=1;
		}
		if(row+1<Dimension && col-1>=0 && array[row+1][col-1]>=1) {  //bottom left cell
			count+=1;
		}
		if(row+1<Dimension && array[row+1][col]>=1) {  //down cell
			count+=1;
		}
		if(row+1<Dimension && col+1<Dimension && array[row+1][col+1]>=1) {  //bottm right cell
			count+=1;
		}
		return count; //safe neighbors  //neighbors which are safe
	}

	public static int Explore(int[][] array, int[][] tempboard,int Dimension,cell ptr) {
		int numMines;
		int check=0;
		numMines=array[ptr.row][ptr.col];  //number of surrounding mines in this cell
		int totalNeigh = totalNeighbors(Dimension,ptr);
		if((numMines)-identifyMines(tempboard,Dimension,ptr)==hiddenNeighbors(tempboard,Dimension,ptr)) { 
			//every hidden neighbor is a mine
			check=1;
			updateKnowledge(tempboard,Dimension,-2,ptr); //filling neighbors with -2s as they are newly founded
		}

		//we are using total neighbors instead of 8 because not all cells have 8 neighbors
		if((totalNeigh-numMines)-indentifySafe(tempboard,Dimension,ptr)==hiddenNeighbors(tempboard,Dimension,ptr)) { 
			//every hidden neighbor is safe
			check=1;
			updateKnowledge(tempboard,Dimension,3,ptr); //filling neighbors with 3s because they are stil unexplored and new
		}
		tempboard[ptr.row][ptr.col]=1; //the current cell is explored so it will be converted from 2 to 1
		return check;
	}

	public static void NewExploration(int [][] array, int[][] tempboard,int Dimension,cell ptr) {
		//any newly founded cell will be used here to check if any of its neighbors can have any improvement
		int row=ptr.row;
		int col=ptr.col;
		cell ref = new cell();
		int inf;
		//safe cells are either 1 or 2 and all other cells are less than 1
		if(row-1>=0 && col-1>=0 && tempboard[row-1][col-1]==1) {  //top left cell
			ref.row = row-1;
			ref.col = col-1;
			inf=Explore(array,tempboard,Dimension,ref); //exploring neighbor for any improvement
		}
		if(row-1>=0 && tempboard[row-1][col]==1) { //up cell
			ref.row = row-1;
			ref.col = col;
			inf=Explore(array,tempboard,Dimension,ref);
		}
		if(row-1>=0 && col+1<Dimension && tempboard[row-1][col+1]==1) {  //top right cell
			ref.row = row-1;
			ref.col = col+1;
			inf=Explore(array,tempboard,Dimension,ref);
		}
		if(col-1>=0 && tempboard[row][col-1]==1) {  //left cell
			ref.row = row;
			ref.col = col-1;
			inf=Explore(array,tempboard,Dimension,ref);
		}
		if(col+1<Dimension && tempboard[row][col+1]==1) {  //right cell
			ref.row=row;
			ref.col=col+1;
			inf=Explore(array,tempboard,Dimension,ref);
		}
		if(row+1<Dimension && col-1>=0 && tempboard[row+1][col-1]==1) {  //bottom left cell
			ref.row=row+1;
			ref.col=col-1;
			inf=Explore(array,tempboard,Dimension,ref);
		}
		if(row+1<Dimension && tempboard[row+1][col]==1) {  //down cell
			ref.row=row+1;
			ref.col=col;
			inf=Explore(array,tempboard,Dimension,ref);
		}
		if(row+1<Dimension && col+1<Dimension && tempboard[row+1][col+1]==1) {  //bottm right cell
			ref.row=row+1;
			ref.col=col+1;
			inf=Explore(array,tempboard,Dimension,ref);
		}
	}

	public static int hiddenNeighbors(int[][] array,int Dimension,cell c) {
		int row=c.row;
		int col=c.col;
		int count=0;
		if(row-1>=0 && col-1>=0 && array[row-1][col-1]==-1) {  //top left cell
			count+=1;
		}
		if(row-1>=0 && array[row-1][col]==-1) { //up cell
			count+=1;
		}
		if(row-1>=0 && col+1<Dimension && array[row-1][col+1]==-1) {  //top right cell
			count+=1;
		}
		if(col-1>=0 && array[row][col-1]==-1) {  //left cell
			count+=1;
		}
		if(col+1<Dimension && array[row][col+1]==-1) {  //right cell
			count+=1;
		}
		if(row+1<Dimension && col-1>=0 && array[row+1][col-1]==-1) {  //bottom left cell
			count+=1;
		}
		if(row+1<Dimension && array[row+1][col]==-1) {  //down cell
			count+=1;
		}
		if(row+1<Dimension && col+1<Dimension && array[row+1][col+1]==-1) {  //bottm right cell
			count+=1;
		}
		return count; //hidden neighbors //hidden neighbors
	}

	public static void updateKnowledge(int[][] tempboard,int Dimension,int type,cell c) {
		int row=c.row;
		int col=c.col;
		tempboard[row][col]=1; //because the safe cell is now explored, it will be converted from 2 to 1
		if(row-1>=0 && col-1>=0 && tempboard[row-1][col-1]==-1) {  //top left cell
			tempboard[row-1][col-1]=type;
		}
		if(row-1>=0 && tempboard[row-1][col]==-1) { //up cell
			tempboard[row-1][col]=type;
		}
		if(row-1>=0 && col+1<Dimension && tempboard[row-1][col+1]==-1) {  //top right cell
			tempboard[row-1][col+1]=type;
		}
		if(col-1>=0 && tempboard[row][col-1]==-1) {  //left cell
			tempboard[row][col-1]=type;
		}
		if(col+1<Dimension && tempboard[row][col+1]==-1) {  //right cell
			tempboard[row][col+1]=type;
		}
		if(row+1<Dimension && col-1>=0 && tempboard[row+1][col-1]==-1) {  //bottom left cell
			tempboard[row+1][col-1]=type;
		}
		if(row+1<Dimension && tempboard[row+1][col]==-1) {  //down cell
			tempboard[row+1][col]=type;
		}
		if(row+1<Dimension && col+1<Dimension && tempboard[row+1][col+1]==-1) {  //bottm right cell
			tempboard[row+1][col+1]=type;
		} //if all cells are identified as safe or unsafe, then we update the board
		
	}

	public static cell nextCell(int[][] tempboard,int Dimension) {
		cell temp=new cell();
		temp.row=-1;
		temp.col=-1;
		Random rand = new Random();
		//In the for loop, we are checking if there is any hidden cell left
		//If a hidden cell is found, then pick random cell until the random cell is one the remaining cells
		for(int i=0;i<Dimension;i++) {
			for(int j=0;j<Dimension;j++) {
				if(tempboard[i][j]==-1) { // -1 means that there is a hidden cell
					temp.row=rand.nextInt(Dimension);
					temp.col=rand.nextInt(Dimension);
					while(tempboard[temp.row][temp.col]!=-1) { //generating random cells until it is on of the remaining hidden cell
						temp.row=rand.nextInt(Dimension);
						temp.col=rand.nextInt(Dimension);
					}
					return temp; //returning that hidden cell
				}
			}
		} 
		//If there is no hidden cell left and the double for loop ends then -1 is returned 
		return temp; 
	}

	public static void inferenceFill(cell ptr,int[][] tempboard,int Dimension,int type,int n) {
		int row = ptr.row;
		int col = ptr.col;
		if(type == 1) {
			//discard left cells
			if(col+1<Dimension && tempboard[row][col+1] == -1) { //we only fill those cells which are unidentified
				tempboard[row][col+1]=n; //right
			}
			if(row+1<Dimension && col+1<Dimension && tempboard[row+1][col+1]==-1) {
				tempboard[row+1][col+1]=n; //lower right
			}
			if(row-1>=0 && tempboard[row-1][col] == -1) {
				tempboard[row-1][col]=n; //up
			}
			if(row+1<Dimension && tempboard[row+1][col]==-1) {
				tempboard[row+1][col]=n; //down
			}
			if(col+1<Dimension && row-1>=0 && tempboard[row-1][col+1]==-1) {
				tempboard[row-1][col+1]=n; //upper right
			}
		}
		else if(type == 2) {
			//discard right cells
			if(row-1>=0 && col-1>=0 && tempboard[row-1][col-1]==-1) {
				tempboard[row-1][col-1]=n; //upper left
			}
			if(col-1>=0 && tempboard[row][col-1]==-1) {
				tempboard[row][col-1]=n; //left;
			}
			if(row+1<Dimension && col-1>=0 && tempboard[row+1][col-1]==-1) {
				tempboard[row+1][col-1]=n; //lower left
			}
			if(row-1>=0 && tempboard[row-1][col] == -1) {
				tempboard[row-1][col]=n; //up
			}
			if(row+1<Dimension && tempboard[row+1][col]==-1) {
				tempboard[row+1][col]=n; //down
			}
		}
		else if(type == 3) {
			//discard upper cells
			if(col-1>=0 && tempboard[row][col-1]==-1) {
				tempboard[row][col-1]=n; //left;
			}
			if(row+1<Dimension && col-1>=0 && tempboard[row+1][col-1]==-1) {
				tempboard[row+1][col-1]=n; //lower left
			}
			if(row+1<Dimension && tempboard[row+1][col]==-1) {
				tempboard[row+1][col]=n; //down
			}
			if(row+1<Dimension && col+1<Dimension && tempboard[row+1][col+1] == -1) {
				tempboard[row+1][col+1]=n; //lower right
			}
			if(col+1<Dimension && tempboard[row][col+1] == -1) { //we only fill those cells which are unidentified
				tempboard[row][col+1]=n; //right
			}
		}
		else if(type==4) {
			//discard down cells
			if(col+1<Dimension && tempboard[row][col+1] == -1) { //we only fill those cells which are unidentified
				tempboard[row][col+1]=n; //right
			}
			if(row-1>=0 && tempboard[row-1][col]==-1) {
				tempboard[row-1][col]=n; //left;
			}
			if(row-1>=0 && col-1>=0 && tempboard[row-1][col-1]==-1) {
				tempboard[row-1][col-1]=n; //upper left
			}
			if(col+1<Dimension && row-1>=0 && tempboard[row-1][col+1]==-1) {
				tempboard[row-1][col+1]=n; //upper right
			}
			if(row-1>=0 && tempboard[row-1][col] == -1) {
				tempboard[row-1][col]=n; //up
			}
		}
	}

	public static void deduce(int[][] array,int[][] tempboard,int Dimension,int row, int col,int num,int type) {
		//row and col are the coordinates of the target cell
		//num is the number of mines that are inferenced
		//type refers to whether the combination is at the left(1), right(2), up(3) or down(4) of the target cell 
		//
		cell ref = new cell();
		ref.row=row;
		ref.col=col;
		int check;
		if(row>=0 && row<Dimension && col>=0 && col<Dimension) { //checking if the cell exists
			if(tempboard[row][col]==3) { //providing inference only to safe cells
				//if cell is newly explored then we check its neighbors then we explore it
				NewExploration(array,tempboard,Dimension,ref);
				tempboard[row][col]=2;
				check = Explore(array,tempboard,Dimension,ref);
				//if the cell already has all neighbors revealed then we dont need inference
				if(check==0) {
					return;
				}
			}
			else if(tempboard[row][col]==2) {
				//if cell is unexplored then we explore it first
				check = Explore(array,tempboard,Dimension,ref);
				//if the cell already has all neighbors revealed then we dont need inference
				if(check == 0) {
					return;	
				}
			}
			if(tempboard[row][col]==1) {
				//now we reach to explored safe cells so we provide the inference here
				if((array[row][col]-identifyMines(tempboard,Dimension,ref)-num)<=0) {
					//we can conclude that the remaining cells are safe because mines exist only in the combination
					inferenceFill(ref,tempboard,Dimension,type,3);
					
				}
			}
			
		}
	}

	public static void inferenceFill2(int Dimension,int[][] tempboard,cell p1,cell p2,cell ptr) {
		int row = ptr.row;
		int col = ptr.col;
		if(row-1>=0 && col-1>=0 && (row-1!=p1.row && col-1!=p1.col) && (row-1!=p2.row && col-1!=p2.col) && tempboard[row-1][col-1]==-1) {
			tempboard[row-1][col-1]=3;
		}
		if(row-1>=0 && (row-1!=p1.row && col!=p1.col) && (row-1!=p2.row && col!=p2.col) && tempboard[row-1][col]==-1) {
			tempboard[row-1][col]=3;
		}
		if(row-1>=0 && col+1<Dimension && (row-1!=p1.row && col+1!=p1.col) && (row-1!=p2.row && col+1!=p2.col) && tempboard[row-1][col+1]==-1) {
			tempboard[row-1][col+1]=3;
		}
		if(col-1>=0 && (row!=p1.row && col-1!=p1.col) && (row!=p2.row && col-1!=p2.col) && tempboard[row][col-1]==-1) {
			tempboard[row][col-1]=3;
		}
		if(col+1<Dimension && (row!=p1.row && col+1!=p1.col) && (row!=p2.row && col+1!=p2.col) && tempboard[row][col+1]==-1) {
			tempboard[row][col+1]=3;
		}
		if(row+1<Dimension && col-1>=0 && (row+1!=p1.row && col-1!=p1.col) && (row+1!=p2.row && col-1!=p2.col) && tempboard[row+1][col-1]==-1) {
			tempboard[row+1][col-1]=3;
		}
		if(row+1<Dimension && (row+1!=p1.row && col!=p1.col) && (row+1!=p2.row && col!=p2.col) && tempboard[row+1][col]==-1) {
			tempboard[row+1][col]=3;
		}
		if(row+1<Dimension && col+1<Dimension && (row+1!=p1.row && col+1!=p1.col) && (row+1!=p2.row && col+1!=p2.col) && tempboard[row+1][col+1]==-1) {
			tempboard[row+1][col+1]=3;
		}
	}

	public static void deduce2(int[][] array, int[][] tempboard,int Dimension, int row,int col, cell p1, cell p2) {
		cell ref = new cell();
		ref.row = row;
		ref.col = col;
		int check;
		if(row>=0 && row<Dimension && col>=0 && col<Dimension) {
			if(tempboard[row][col]==3) {
				//if cell is newly explored then we check its neighbors then we explore it
				NewExploration(array,tempboard,Dimension,ref);
				tempboard[row][col]=2;
				check = Explore(array,tempboard,Dimension,ref);
				//if the cell already has all neighbors revealed then we dont need inference
				if(check==0) {
					return;
				}
			}
			else if(tempboard[row][col]==2) {
				//if cell is unexplored then we explore it first
				check = Explore(array,tempboard,Dimension,ref);
				//if the cell already has all neighbors revealed then we dont need inference
				if(check == 0) {
					return;	
				}
			}
			if(tempboard[row][col]==1) {
				//now we reach to an explored cell so we provide inference here
				if(array[row][col]-identifyMines(tempboard,Dimension,ref)-1==0) {
					//All other mines are safe because he only mine was present in this combination
					inferenceFill2(Dimension,tempboard,p1,p2,ref);
					
				}
			}

		}
	}

	public static void inferencesMaker(int[][] array,int[][] tempboard,int Dimension,cell ptr,int n) {
		int row = ptr.row;
		int col = ptr.col;
		if(row-1>=0 && col-1>=0 && tempboard[row-1][col-1]==-1 && tempboard[row-1][col]==-1 && col+1<Dimension && tempboard[row-1][col+1]==-1) {
			//a combination of upper 3 cells is made and they contain atleast n mine
			//Now we will provide this information to the relative cell(a cell that has all these cells as neighbors)
			//target cell would be 2 cells upward which
			deduce(array,tempboard,Dimension,row-2,col,n,4);
		}
		if(row-1>=0 && col-1>=0 && tempboard[row-1][col-1]==-1 && tempboard[row][col-1]==-1 && row+1<Dimension && tempboard[row+1][col-1]==-1) {
			//a combination of left 3 cells is made and they contain atleast n mine
			//target cell would be left 2 cells
			deduce(array,tempboard,Dimension,row,col-2,n,2);
		}
		if(row-1>=0 && col+1<Dimension && tempboard[row-1][col+1]==-1 && tempboard[row][col+1]==-1 && row+1<Dimension && tempboard[row+1][col+1]==-1) {
			//a combination of right 3 cells is made and they contain atleast n mines
			//target cell would be right 2 cells
			deduce(array,tempboard,Dimension,row,col+2,n,1);
		}
		if(row+1<Dimension && col-1>=0 && tempboard[row+1][col-1]==-1 && tempboard[row+1][col]==-1 && col+1<Dimension && tempboard[row+1][col+1]==-1) {
			//a combination of down 3 cells is made and they contain atleast n mines
			//target cell would be down 2 cells
			deduce(array,tempboard,Dimension,row+2,col,n,3);
		}
	}

	public static void inferencesMaker2(int[][] array, int[][] tempboard,int Dimension,cell ptr) {
		int row = ptr.row;
		int col = ptr.col;
		cell point1 = new cell();
		cell point2 = new cell();
		if(row-1>=0 && col-1>=0 && tempboard[row-1][col-1]==-1 && tempboard[row-1][col]==-1) {
			//A combination of upper left cell and upper cell is made which contains atleast 1 mine
			//This info will be provided to target cell for inference
			//target cell would be 2 cells up and 1 cell left
			point1.row = row-1;
			point1.col = col-1;
			point2.row = row-1;
			point2.col = col;
			deduce2(array,tempboard,Dimension,row-2,col-1,point1,point2);
		}
		if(row-1>=0 && col+1<Dimension && tempboard[row-1][col]==-1 && tempboard[row-1][col+1]==-1) {
			//A combination of upper and upper right cell is made which contains atleast 1 mine
			//The target cell would be 2 cells up and 1 right
			point1.row = row-1;
			point1.col = col;
			point2.row = row-1;
			point2.col = col+1;
			deduce2(array,tempboard,Dimension,row-2,col+1,point1,point2);
		}
		if(row-1>=0 && col-1>=0 && tempboard[row-1][col-1]==-1 && tempboard[row][col-1]==-1) {
			//A combination of upper left and left cell is made 
			//The target cell would be 1 up and 2 left cells
			point1.row = row-1;
			point1.row = col-1;
			point2.row = row;
			point2.col = col-1;
			deduce2(array,tempboard,Dimension,row-1,col-2,point1,point2);
		}
		if(row+1<Dimension && col-1>=0 && tempboard[row][col-1]==-1 && tempboard[row+1][col-1]==-1) {
			//A combination of left cell and lower left cell is made
			//The target cell would be 2 left and 1 down cell
			point1.row = row;
			point1.row = col-1;
			point2.row = row+1;
			point2.col = col-1;
			deduce2(array,tempboard,Dimension,row+1,col-2,point1,point2);
		}
		if(row+1<Dimension && col-1>=0 && tempboard[row+1][col]==-1 && tempboard[row+1][col-1]==-1) {
			//A combination of lower and lower left cell is made
			//The target cell would be 2 down and 1 left cell
			point1.row = row+1;
			point1.row = col;
			point2.row = row+1;
			point2.col = col-1;
			deduce2(array,tempboard,Dimension,row+2,col-1,point1,point2);
		}
		if(row+1<Dimension && col+1<Dimension && tempboard[row+1][col]==-1 && tempboard[row+1][col+1]==-1) {
			//A combination of lower and lower right cell is made
			//The target cell would be 2 down and 1 right cell
			point1.row = row+1;
			point1.row = col;
			point2.row = row+1;
			point2.col = col+1;
			deduce2(array,tempboard,Dimension,row+2,col+1,point1,point2);
		}
		if(row+1<Dimension && col+1<Dimension && tempboard[row+1][col+1]==-1 && tempboard[row][col+1]==-1) {
			//A combination of lower right and  right cell is made
			//The target cell would be 1 down and 2 right cell
			point1.row = row+1;
			point1.row = col+1;
			point2.row = row;
			point2.col = col+1;
			deduce2(array,tempboard,Dimension,row+1,col+2,point1,point2);
		}
		if(row-1>=0 && col+1<Dimension && tempboard[row][col+1]==-1 && tempboard[row-1][col+1]==-1) {
			//A combination of right and  upper right cell is made
			//The target cell would be 1 up and 2 right cell
			point1.row = row;
			point1.row = col+1;
			point2.row = row-1;
			point2.col = col+1;
			deduce2(array,tempboard,Dimension,row-1,col+2,point1,point2);
		}
	}

	public static void printArray(int Dimension, int[][] array) {   //print a 2d array
		for(int i=0;i<Dimension;i++) {
			for(int j=0;j<Dimension;j++) {
				System.out.print(array[i][j] + "  ");
			}
			System.out.println("   ");
		} 
	}

	public static int basicAlgo(int[][] array, int Dimension,int mines) {
		//we are creating a temporary playing board in which : 
		//0 will indicate a mine, 1 will indicate a safe explored cell
		//-1 will indicate an unidentified cell and 2 will indicate a safe unexplored cell


		int mineCount=0; //counter to check how many mines have been clicked
		int numMines; //counter for checking surrounding mines
		int[][] tempboard = new int[Dimension][Dimension]; //creating a temporary 2d array
		fill(tempboard,Dimension); //filling with -1s 
		cell ptr = new cell(); //a temporary cell which will be indicate the next cell to be explored
		while(true) { 
			ptr = checkArraySafe(tempboard,Dimension); //checking if there is an unexplored safe cell
			if(ptr.row!=-1 && ptr.col!=-1) {  //true if there is an unexplored safe cell so we will explore this cell
				numMines=array[ptr.row][ptr.col];  //number of surrounding mines in this cell
				int totalNeigh = totalNeighbors(Dimension,ptr);
				if((numMines)-identifyMines(tempboard,Dimension,ptr)==hiddenNeighbors(tempboard,Dimension,ptr)) { 
					//every hidden neighbor is a mine
					updateKnowledge(tempboard,Dimension,0,ptr); 
				}
				if((totalNeigh-numMines)-indentifySafe(tempboard,Dimension,ptr)==hiddenNeighbors(tempboard,Dimension,ptr)) {
					//every hidden neighbor is safe
					updateKnowledge(tempboard,Dimension,2,ptr);
				}
				tempboard[ptr.row][ptr.col]=1; //the cell is explored so it will be converted from 2 to 1
			}
			else {
				//no safe cell is left so we have to randomly pick
				ptr=nextCell(tempboard,Dimension);
				if(ptr.row==-1 && ptr.col==-1) { //this means that all cells are completed
					return (mines-mineCount);
				}
				if(array[ptr.row][ptr.col]==9) { 
					//System.out.println(ptr.row + ", " + ptr.col);
					mineCount+=1;
					tempboard[ptr.row][ptr.col]=0; //we clicked a mine cell so we add it in knowledge base
				}
				else {
					tempboard[ptr.row][ptr.col]=2; //we clicked a safe mine so we add 2(unexplored safe cell)
				}
			}
			
		}
	}

	public static int ImprovedAlgo(int[][] array, int Dimension, int mines) {
		//Just like in basic algorithm
		//we are creating a temporary playing board in which : 
		//0 will indicate a mine, 1 will indicate a safe explored cell
		//-1 will indicate an unidentified cell and 2 will indicate a safe unexplored cell
		//Introducing 2 new types : -2 will indicate a newly founded mine cell and 3 will indicate a newly founded safe cell
		int mineCount=0;
		int tempboard[][] = new int[Dimension][Dimension]; //temporary playing board
		fill(tempboard,Dimension); //filling it with -1's 
		int numMines; //to checking the count of neighboring mines
		int inf; //to check whether the exploration of a cell deduced any mines
		int hidden; //to check the number of hidden neighbors
		cell ptr = new cell(); //it will indicate the next cell to be explored
		while(true) {
			ptr=checkArraySafe(tempboard,Dimension); //checking if there is a unexplored safe cell
			if(ptr.row!=-1) { //true if there is an unexplored safe cell
				inf = Explore(array,tempboard,Dimension,ptr); //exploring the cell and return 1 if any cells were identified
				if(inf==0) { //inf 0 means that no decisive mines or safes were found in the cell exploration 
					
					//now we have to make combinations and provide inferences to neighbors
					//checking how many mines are left for how many neighbors
					if((hiddenNeighbors(tempboard,Dimension,ptr))-(array[ptr.row][ptr.col]-identifyMines(tempboard,Dimension,ptr))==2) {
						//Difference of 2 means that every combination of 3 mines has atleast 1 mine in it
						//Combination of 2 is not possible
						inferencesMaker(array,tempboard,Dimension,ptr,1);
					} 
					else if((hiddenNeighbors(tempboard,Dimension,ptr))-(array[ptr.row][ptr.col]-identifyMines(tempboard,Dimension,ptr))==1) {
						//Difference of 1 means that every combination of 3 mines has atleast 2 mines it it
						inferencesMaker(array,tempboard,Dimension,ptr,2);
						//Previously we made combinations of 3, now we will make combinations of 2
						inferencesMaker2(array,tempboard,Dimension,ptr);
					}
				}
			}
			else {
				//no safe cell is left so we have to randomly pick
				ptr=nextCell(tempboard,Dimension);
				if(ptr.row==-1 && ptr.col==-1) { //this means that all cells are completed
					//printArray(Dimension,tempboard);
					return (mines-mineCount);
				}
				if(array[ptr.row][ptr.col]==9) { 
					//System.out.println(ptr.row + ", " + ptr.col);
					mineCount+=1;
					tempboard[ptr.row][ptr.col]=-2; //we clicked a mine cell so we add it in knowledge base
				}
				else {
					tempboard[ptr.row][ptr.col]=3; //we clicked a safe cell so we add 3(unexplored safe cell)
				}
				
			}
			//now I will check if there are any newly founded cells
			ptr=checkArraynew(tempboard,Dimension,3); //checking for newly founded safe cell
			while(ptr.row!=-1) { //0 means that there is no newly founded safe cell
				//check its neighbors for improvement
				NewExploration(array,tempboard,Dimension,ptr);
				tempboard[ptr.row][ptr.col]=2; //because operation is done on newly founded safe cell, it is no longer new but still unexplored
				ptr=checkArraynew(tempboard,Dimension,3); //checking if any more newly founded safe cells left
			}	
			ptr=checkArraynew(tempboard,Dimension,-2); //checking for newly founded mine cells
			while(ptr.row!=-1) {
				//check its neighbors for improvement 
				NewExploration(array,tempboard,Dimension,ptr);
				tempboard[ptr.row][ptr.col]=0; //because operation is performed on this newly founded mine cell, it is no longer new
				ptr=checkArraynew(tempboard,Dimension,-2); //checking if any more newly founded mine cells are left
			}

			
		}
	}

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		DecimalFormat df2 = new DecimalFormat("#.##");
		int mines=50;
		int base=0;
		int improve=0;
		double density=0;
		int[][] check;
		int Dimension=100;
		
		while(mines<9900) {
			for(int i=0; i<10; i++) { //iterating the algorithms 10 times to get a good score at a given mine density
				check = mineArray(Dimension,mines);
				//printArray(Dimension,check);
				density=(double)mines/10000;
				base = base + basicAlgo(check,Dimension,mines); //base is the number of mines safely identified
				improve = improve + ImprovedAlgo(check,Dimension,mines); //improve is the number of mines safely identified
			}
			System.out.println("Mine density is " + density);
			System.out.println("Average score for basic algorithm is " + df2.format((double)base/11/mines)); //dividing by 11 to get average
			System.out.println("Average score for improved algorithm is " + df2.format((double)improve/11/mines)); //dividing by mines to get final score
			System.out.println();
			mines=mines+500;
			base=0;
			improve=0;
		}
        runGC();
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Time elapsed is " + (double)elapsedTime/1000 + " seconds");
	}

}