package com.company;
import java.awt.*;
import com.company.hsa.Console;
import java.io.*;
import java.util.*;

public class Pawn {
    Color color;
    int startingIndex;//what pawn number it is (between 1-4)
    String pawnNumber;//same thing as previous variable but as a string
    int counter;
    int isOut;//attribute that stores whether a player is in home base or not (1=inside home base), any other number means out. (Boolean gave us errors)
    int homeBaseIndex;
    boolean hasBeat;
    int toMove;//how much you have to move the pawn in the safety zone regardless of color.
    int posHomeBase;//is the current pawn's position value of the square just before the colored end zone
    int endZoneIndex;//used as index value for endzone pawns

    boolean reachedEndZone;//boolean stores whether pawn has reached endzone or not(complete safety)

    boolean isSafe=false;//describes if its safe

    //Constructor: starting index is the pawnNumber, Color is the pawn's color, pawnNumber is the same as starting index but a string
    public Pawn(int startingIndex, Color color, String pawnNumber){
        this.color = color;
        this.startingIndex = startingIndex;
        this.pawnNumber = pawnNumber;
        isOut = 1;//default value set to say that pawn is in home base
        reachedEndZone=false;//pawn by default has not reached endzone (total safety)
    }

    //method is called when pawn has reached endzone
    public void reachedEndZone(){
        reachedEndZone=true;
    }
    //method which returns whether pawn has gotten into endzone
    public boolean hasItReached(){
        return reachedEndZone;
    }
    //method to draw pawn in the endzone
    public void drawEndZone(Console c){
        //endZoneIndex value acts as an index to access coordinates from the Board class' endX and endY array
        //the index value is recalculated based on color as the first 4 indexes are red coordinates and the next 4 are yellow etc
        if(color.getRGB() ==Color.red.getRGB()){
            endZoneIndex=0+startingIndex-1;
        } else if(color.getRGB() ==Color.green.getRGB()){
            endZoneIndex=4+startingIndex-1;
        }
        else if(color.getRGB() ==Color.yellow.getRGB()){
            endZoneIndex=8+startingIndex-1;
        } else if(color.getRGB() ==Color.blue.getRGB()){
            endZoneIndex=12+startingIndex-1;
        }
        //draws out the pawn in the end zone based on coordinates from board class
        c.setColor(color);
        c.fillOval(Board.endX[endZoneIndex],Board.endY[endZoneIndex],20,20);
        c.setColor(Color.BLACK);
        c.drawOval(Board.endX[endZoneIndex],Board.endY[endZoneIndex],20,20);
        c.drawString(pawnNumber, Board.endX[endZoneIndex]-3+(20/2),Board.endY[endZoneIndex]+ 3 + (20/2) );
    }

    //home base is the counter value of the block just before your colored end zone
    //this method changes the attribute based on the color
    //we tried to do this in the constructor but it was not fixable.
    public void changeHomeBase(){
        if(color.getRGB() ==Color.red.getRGB()){
            posHomeBase=51;
        } else if(color.getRGB() ==Color.green.getRGB()){
            posHomeBase=12;
        }
        else if(color.getRGB() ==Color.yellow.getRGB()){
            posHomeBase=25;
        } else if(color.getRGB() ==Color.blue.getRGB()){
            posHomeBase=38;
        }
    }
    //returns toMove
    public int returnInsideSafeZone(){
        return toMove;
    }

    //method called to draw the pawn in its colored end zone. Only called when pawn has reached the end zone and has to be drawn Inside endzone
    public void drawPawnHome(Console c){
        int moveInside;//index value that will be accessed depending on how much pawn has to move
        //this index value changes depending on the color to access the correct index of coordinate values in board class.
        if(color.getRGB() == Color.red.getRGB()){
            //draws the pawn in the colored end zone
            moveInside = toMove-1;
            c.setColor(color);
            c.fillOval(Board.homeX[moveInside],Board.homeY[moveInside],20,20);
            c.setColor(Color.BLACK);
            c.drawOval(Board.homeX[moveInside],Board.homeY[moveInside],20,20);
            c.drawString(pawnNumber, Board.homeX[moveInside]-3+(20/2),Board.homeY[moveInside]+ 3 + (20/2) );
        } else if(color.getRGB() == Color.green.getRGB()){
            moveInside=toMove+5-1;
            c.setColor(color);
            c.fillOval(Board.homeX[moveInside],Board.homeY[moveInside],20,20);
            c.setColor(Color.BLACK);
            c.drawOval(Board.homeX[moveInside],Board.homeY[moveInside],20,20);
            c.drawString(pawnNumber, Board.homeX[moveInside]-3+(20/2),Board.homeY[moveInside]+ 3 + (20/2) );
        }else if(color.getRGB()==Color.yellow.getRGB()){
            moveInside=toMove+10-1;
            c.setColor(color);
            c.fillOval(Board.homeX[moveInside],Board.homeY[moveInside],20,20);
            c.setColor(Color.BLACK);
            c.drawOval(Board.homeX[moveInside],Board.homeY[moveInside],20,20);
            c.drawString(pawnNumber, Board.homeX[moveInside]-3+(20/2),Board.homeY[moveInside]+ 3 + (20/2) );
        } else if(color.getRGB()==Color.blue.getRGB()){
            moveInside=toMove+15-1;
            c.setColor(color);
            c.fillOval(Board.homeX[moveInside],Board.homeY[moveInside],20,20);
            c.setColor(Color.BLACK);
            c.drawOval(Board.homeX[moveInside],Board.homeY[moveInside],20,20);
            c.drawString(pawnNumber, Board.homeX[moveInside]-3+(20/2),Board.homeY[moveInside]+ 3 + (20/2) );
        }
    }

    //method called when pawn has reached colored end zone. to move is how much further in it has to move in the end zone
    public void isSafe(int toMove){
        isSafe = true;
        this.toMove=toMove;
    }
    //returns whether pawn has reached colored end zone
    public boolean isItSafe(){
        return isSafe;
    }

    //draws out the pawn  normally depending on its position and attributes (does not need a dice roll)
    public void makePawn(Console c){
        c.setColor(color);

        //if checks if the pawn is in home base. if Yes, it accesses the homeX and homeY array coordinates and draws it in it shome base
        if(isOut==1){
            if(color.getRGB() == Color.red.getRGB()){
                homeBaseIndex = startingIndex-1;
            } else if(color.getRGB() == Color.green.getRGB()){
                homeBaseIndex = startingIndex+3;
            }else if(color.getRGB()==Color.yellow.getRGB()){
                homeBaseIndex = startingIndex+7;
            } else if(color.getRGB()==Color.blue.getRGB()){
                homeBaseIndex = startingIndex+11;
            }
            //home index =7;
            c.fillOval(Board.baseX[homeBaseIndex],Board.baseY[homeBaseIndex],20,20);
            c.setColor(Color.BLACK);
            c.drawOval(Board.baseX[homeBaseIndex],Board.baseY[homeBaseIndex],20,20);
            c.drawString(pawnNumber, Board.baseX[homeBaseIndex]-3+(20/2),Board.baseY[homeBaseIndex]+ 3 + (20/2) );
        } else if(isOut!=1){//if statement sees if it is out of home base, if yes, draws it on the board
            c.setColor(color);
            c.fillOval(Board.boardPositionX[counter],Board.boardPpositionY[counter],20,20);
            c.setColor(Color.BLACK);
            c.drawOval(Board.boardPositionX[counter],Board.boardPpositionY[counter],20,20);
            c.drawString(pawnNumber, Board.boardPositionX[counter]-3+(20/2),Board.boardPpositionY[counter]+ 3 + (20/2) );
        }
    }

//  public void movePawnHome(int movedInside, Console c){
//
//  }

    //similar to method above except it is always called after a dice roll
    public void movePawn(int diceRoll, Console c){
        //checks if pawn is in home and player rolls a 6
        if(isOut==1 && diceRoll ==6){
            isOut = 2;//means pawn is out of home base

            //counter represents index value to access the boardPosoitionX and Y values. Each pawn has a different place to start from
            //so these if statements change the counter to that position value once a 6 has been rolled and a pawn comes out of home base
            if(color.getRGB() == Color.red.getRGB()){
                counter=1;
            } else if(color.getRGB() == Color.green.getRGB()){
                counter = 14;
            }else if(color.getRGB()==Color.yellow.getRGB()){
                counter = 27;
            } else if(color.getRGB()==Color.blue.getRGB()){
                counter = 40;
            }
            //draws the pawn in its starting place on the board
            c.setColor(color);
            c.fillOval(Board.boardPositionX[counter],Board.boardPpositionY[counter],20,20);
            c.setColor(Color.BLACK);
            c.drawOval(Board.boardPositionX[counter],Board.boardPpositionY[counter],20,20);
            c.drawString(pawnNumber, Board.boardPositionX[counter]-3+(20/2),Board.boardPpositionY[counter]+ 3 + (20/2) );
        } else if(isOut!=1){//to ensure pawn is out of home base
            if(counter+diceRoll>51){//ensures that pawn can come around the board as the board has 51 index positions representing coordinates and then resets back to zero
                counter=(counter+diceRoll)-52;
                c.println(counter);
            } else{//if no anomalies occur, pawn is drawn normally
                counter+= diceRoll;
                c.setColor(color);
                c.fillOval(Board.boardPositionX[counter],Board.boardPpositionY[counter],20,20);
                c.setColor(Color.BLACK);
                c.drawOval(Board.boardPositionX[counter],Board.boardPpositionY[counter],20,20);
                c.drawString(pawnNumber, Board.boardPositionX[counter]-3+(20/2),Board.boardPpositionY[counter]+ 3 + (20/2) );
            }
        }
    }
    //returns index value of the position of pawn
    public int getPosition(){
        return counter;
    }

    //called when a pawn gets beat.
    public void sendHome(){
        isOut=1;//means the pawn is in home and is redrawn
    }

    //returns pawn's color's RGB valye
    public int getColor(){
        return color.getRGB();
    }

    //called when a player's pawn has beaten another pawn
    public void beaten(){
        hasBeat=true;
    }

    //query returns whether a pawn has beat another pawn
    public boolean hasBeat(){
        return hasBeat;
    }


    //returns the player's home base starting position
    public int getHomeBase(){
        return posHomeBase;
    }
}
