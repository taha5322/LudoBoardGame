package com.company;
import java.awt.*;
import com.company.hsa.Console;
import java.io.*;
import java.util.*;

import javax.imageio.*;

public class Board {
    static Console c;
    //Used as coordinates when pawn is in play on the board. Index 0 is the square above red's starting position and it goes around
    static int[] boardPositionX = {267,267,267, 267,267,267,297,327,356,386,415,443,443,443,415,386,356,327,297,267,267,267,267,267,267,238,209,209,209,209,209,209,179,149,119,92,63,34,34,
            34,63,92,119,149,179,209,209,209,209,209,209,239};
    //Same thing as above array but for y values
    static int[] boardPpositionY = {35,65,95,122,152,182, 210,210,210,210,210,210,240,268,268,268,268,268,268,298,328,356,386,415,444,444,444,415,386,356,328,298,268,268,268,268,268,268,240,
            210,210,210,210,210,210,182,152,122,95,65,35,35};


    //Coordinate values for storing the home base positions
    static int[] baseX = {348,391,348,391,348,391,348,391, 85,129,85,129 , 85,129,85,129};//0-3 Red:X pixel TL to BR; 4-7 Green TL to BR; 8-11 Yellow: TL to BR; 12-15 Blue TL to BR
    static int[] baseY = {85,85, 130,130,348,348,392,392, 348,348,392,392  ,85,85,130,130};//0-3 Red: X pixel TL to BR; 4-7 Green TL to BR; 8-11 Yello TL to BR; 12-15 Blue TL to BR

    //coordinate values for the colored endzone of each color
    static int[] homeX={240,240,240,240,240,   416,386,356,326,296,   240,240,240,240,240,  65,94,124,153,183}  ;
    static int[] homeY={65,92,122,151,180,     238,238,238,238,238,     415,385,356,327,297,   238,238,238,238,238 };

    //coordinate values for the end zone for each pawn
    static int[] endX={225, 235, 245, 255,   260,260,274,274,    225,235,245,255 ,215,215,205,208};
    static int[] endY={ 207 , 210, 210,207,  232, 241, 215,256,   270,260,260,270,241,232,215,256};

    public static void main(String args[]){
        c=new Console();
        drawBoard(c);
        for(int i=0;i<endX.length;i++){
            c.setColor(Color.red);
            c.fillOval(endX[i],endY[i],20,20);
            c.setColor(Color.BLACK);
            c.drawOval(endX[i],endY[i],20,20);
            c.drawString("1", endX[i]-3+(20/2),endY[i]+ 3 + (20/2) );
        }
    }

    //draws out the ludo board on the screen
    public static void drawBoard(Console c){
        Image img;  //The image to draw

        //Try to import the image from the file
        try{

            img = ImageIO.read(new File("image.png"));
        }
        catch(IOException e)  //File not found
        {
            img = null;
        }
        c.drawImage(img,10,10,null);
    }
    //Generates a random number between 1-6
    public static int rollDie(){
        return (int)((Math.random()*6)+1);
    }
}
