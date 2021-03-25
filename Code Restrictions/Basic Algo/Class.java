import java.util.Date;
import java.awt.*;
import java.util.Scanner;
import java.util.Random;
import java.io.*; 
import java.util.Arrays;



public class Class {
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

	public static cell checkArray(int[][] array, int Dimension) { //checking if an identified safe cell is present which has not been explored yet
		cell temp = new cell();
		temp.row=-1;
		temp.col=-1;
		for(int i=0;i<Dimension;i++) {
			for(int j=0;j<Dimension;j++) {
				if(array[i][j]==2) {  // 2 means that cell is safe but not explored yet
					temp.row=i;
					temp.col=j;
					return temp;
				}
			}
		}
		return temp;  //this will return -1 if there is no identified safe cell
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
		int row=c.row;
		int col=c.col;
		int count=0;
		if(row-1>=0 && col-1>=0 && array[row-1][col-1]==0) {  //top left cell
			count+=1;
		}
		if(row-1>=0 && array[row-1][col]==0) { //up cell
			count+=1;
		}
		if(row-1>=0 && col+1<Dimension && array[row-1][col+1]==0) {  //top right cell
			count+=1;
		}
		if(col-1>=0 && array[row][col-1]==0) {  //left cell
			count+=1;
		}
		if(col+1<Dimension && array[row][col+1]==0) {  //right cell
			count+=1;
		}
		if(row+1<Dimension && col-1>=0 && array[row+1][col-1]==0) {  //bottom left cell
			count+=1;
		}
		if(row+1<Dimension && array[row+1][col]==0) {  //down cell
			count+=1;
		}
		if(row+1<Dimension && col+1<Dimension && array[row+1][col+1]==0) {  //bottm right cell
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

	public static void printArray(int Dimension, int[][] array) {   //print a 2d array
		for(int i=0;i<Dimension;i++) {
			for(int j=0;j<Dimension;j++) {
				System.out.print(array[i][j] + "  ");
			}
			System.out.println(" ");
		} 
	}

	public static void basicAlgo(int[][] array, int Dimension,int mines) {

		int mineCount=0; //counter to check how many mines have been clicked
		int numMines; //counter for checking surrounding mines
		int[][] tempboard = new int[Dimension][Dimension]; //creating a temporary 2d array
		fill(tempboard,Dimension); //filling with -1s 
		cell ptr = new cell(); //a temporary cell which will be indicate the next cell to be explored
		while(true) { 
			ptr = checkArray(tempboard,Dimension); //checking if there is an unexplored safe cell
			if(ptr.row!=-1 && ptr.col!=-1) {  //true if there is an unexplored safe cell so we will explore this cell
				numMines=array[ptr.row][ptr.col];  //number of surrounding mines in this cell
				int totalNeigh = totalNeighbors(Dimension,ptr);//total neighbors of this cell
				if((numMines)-identifyMines(tempboard,Dimension,ptr)==hiddenNeighbors(tempboard,Dimension,ptr)) { 
					//every hidden neighbor is a mine
					updateKnowledge(tempboard,Dimension,0,ptr); //type means 1 if safe and 0 if mine and -1 means hidden and 2 means unexplored safe
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
					System.out.println(("Number of mines safely identified are " + (mines-mineCount)));
					
					return;
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

	public static void main(String[] args) {
		int mines=20;
		int Dimension=5;
		int[][] check = mineArray(Dimension,mines);
		//printArray(Dimension,check);
		basicAlgo(check,Dimension,mines);
	}
}