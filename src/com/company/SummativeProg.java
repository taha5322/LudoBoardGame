package com.company;
import java.awt.*;
import com.company.hsa.Console;
import java.io.*;
import java.util.*;
import javax.imageio.*;

public class SummativeProg {

    static Console c;
    public static void main(String[] args) throws IOException {
	// write your code here
        c = new Console();
        boolean loop=true;
        boolean startGame;//boolean to check whether game started.
        String playerString;//string to data validate number of players
        int playerNumber;//total num of players
        int colorArrayPlayerNum;//used for output in array
        int playerColor; //used to temporarily store color in array
        String letterColor; //used to temporarily store color choice
        int start;
        int totalColors;//used in the for loop to find out how many colors are there.

        int timesBeatenRed = 0;    //these 4 variables store the number of times each colour has beaten another pawn
        int timesBeatenGreen = 0;  //will be sent to a file later
        int timesBeatenYellow = 0; //
        int timesBeatenBlue = 0;   //
        int roll6Red = 0;       //these 4 variables store the number of times each colour rolled a 6
        int roll6Green = 0;     //
        int roll6Yellow = 0;     //
        int roll6Blue = 0;      //
        int numTurns = 0;       //Stores the total number of turns played throughout the game. will be outputted to a file
        int color;            //stores numerical value for comparing colors in file output

        Pawn[][] playerArray;//an array of pawn arrays. First value is number of players, second is always 4 storing each pawn.
        Color[] colorArray;//stores each player's color chosen. Player 1's color is stored at index 0 etc
        int diceRoll;//current value of the dice roll
        int currentPawn;//this is the pawn that the user wants to move
        int winCounter;//used to see if a pawn has reached endZone (total safety)
        int winningPlayer;//stores the player that has won for the output statement
        String beatenOutput;//Default string that is outputted after each pawn is moved. useful when a pawn has been beat as statements are added to this
        String beatenOutput2;//works the same as beatenOutput

        PrintWriter gameStats = new PrintWriter(new FileWriter("gameStats.txt"));

        StringTokenizer player1;
        StringTokenizer player2;
        StringTokenizer player3;
        StringTokenizer player4;

        winningPlayer=-1;//default initialization to say that there is no player that has won
        int homeToMove=0;//default initialization of how much further a pawn has to be moved in its colored endzone.
        beatenOutput="";//default initialization. Put so that the variable can be accessed after the while loop
        beatenOutput2="";//same as before


        //output statements saying welcome and additional prompts
        c.println("Welcome to ludo");
        c.println("How many players would you like");//.e
        c.setCursor(3, 3);
        playerString=c.readLine();

        //Data validation for total players
        while(true ){
            try{

                playerNumber = Integer.parseInt(playerString);
                if(playerNumber>4||playerNumber<2){//
                    c.println("How many players would you like, Only allowed between 2-4");

                } else if(playerNumber<5&&playerNumber>1){
                    break;
                }
                playerString=c.readLine();
                playerNumber = Integer.parseInt(playerString);
            } catch(NumberFormatException e){
                c.println("Enter a number");
                playerString =c.readLine();
            }
        }

        c.clear();


        colorArray = new Color[playerNumber];
        //Lets the user decide which player should be what color and adds the colors in order to the colorArray above.
        totalColors=0;
        //for loop sequuentially stores player 1's color at the color array' index 0. Player 2's color at index 1 etc
        for(int i=0; i<playerNumber; i++){
            colorArrayPlayerNum = i+1;
            c.println("What color should player " + colorArrayPlayerNum +" be\nEnter 'r' for red, 'g' for green, 'y' for yellow, and 'b' for blue");
            letterColor=c.readLine();
            if(letterColor.equalsIgnoreCase("r")){//.e.
                colorArray[i]=Color.red;
                totalColors+=1;
            } else if(letterColor.equalsIgnoreCase("g")){//.e.
                colorArray[i]=Color.green;
                totalColors+=1;
            } else if(letterColor.equalsIgnoreCase("y")){//.e.
                colorArray[i]=Color.yellow;
                totalColors+=1;
            } else if(letterColor.equalsIgnoreCase("b")){//.e.
                colorArray[i]=Color.blue;
                totalColors+=1;
            }
        }
        playerArray = new Pawn[totalColors][4];//Creates an array of pawn arrays with the firs array for total players and the second as 4 represemting total pawns for each array
        //initializes and makes a new pawn in each index of the player array
        for(int i=0; i<totalColors;i++){
            for(int x=0;x<4;x++){
                playerArray[i][x]= new Pawn(x+1,colorArray[i],Integer.toString(x+1));//making a new pawn object
                playerArray[i][x].changeHomeBase();//initializes the home base value in the pawn class for where the home base is
                //c.println(p);
            }
        }
        c.clear();
        Board.drawBoard(c);//method to draw the board on console
        //for loop draws out all the pawns in their home base positions as game is ready to start
        for(int i=0; i<playerArray.length;i++){
            for(int x=0;x<4;x++){
                playerArray[i][x].makePawn(c);
            }
        }
        //while loop runs as long as loop =true; loop only ends when game is over
        while(loop==true){

            //for loop that runs depending on the total players so that each player gets a turn.
            for(int i =0; i<playerNumber;i++){
                //Tells user whose turn and instructions to follow/
                c.drawString("player "+ (i+1) +"'s turn",500,10);
                c.drawString("Enter r to roll a die",500,30);
                c.setCursor(3,64);

                String yes= c.readLine();//.e
                c.clear();
                Board.drawBoard(c);

                for(int y=0; y<playerArray.length;y++){//draws out all the pawns in their position
                    for(int x=0;x<4;x++){
                        if(playerArray[y][x].isItSafe()==false){//draws pawn out on the board only when it is not in safe zone(meaning out in play or in its home)
                            playerArray[y][x].makePawn(c);
                        } else if(playerArray[y][x].isItSafe()==true&&playerArray[y][x].hasItReached()==false){//only run when pawn is out in play and has reached the colored endZone
                            playerArray[y][x].drawPawnHome(c);
                        } else if(playerArray[y][x].isItSafe()==true&&playerArray[y][x].hasItReached()==true){//only draws it out if it has reached inside the end zone(meaning the pawn has reached total safety)
                            playerArray[y][x].drawEndZone(c);
                        }


                    }
                }

                if(yes.equalsIgnoreCase("r")){//.e.
                    c.setCursor(3,64);
                    diceRoll= Board.rollDie();//rolls a dice between 1-6 from the board class
                    c.clear();
                    Board.drawBoard(c);

                    //does the same thing as above for loop but after the above dice roll
                    for(int y=0; y<playerArray.length;y++){//draws out all the pawns in their position
                        for(int x=0;x<4;x++){
                            if(playerArray[y][x].isItSafe()==false){//checks to see if pawn is not in colored end zone
                                playerArray[y][x].makePawn(c);//if not, it is drawn normally
                            } else if(playerArray[y][x].isItSafe()==true&&playerArray[y][x].hasItReached()==false){//is it has reached colored endzone but not in endzone
                                playerArray[y][x].drawPawnHome(c);//if yes, draws it in colored endzone
                            } else if(playerArray[y][x].isItSafe()==true&&playerArray[y][x].hasItReached()==true){//checks to see if it has reached colored endZone And has reached endzone
                                playerArray[y][x].drawEndZone(c);//if yes, draws it in end zone
                            }


                        }
                    }

                    //tells the user what was rolled, and prompts for which pawn to move
                    c.drawString("You rolled a "+Integer.toString(diceRoll), 500,120);
                    c.drawString("Which pawn would you ",500,50);
                    c.drawString("like to move ",500,70);
                    c.setCursor(5,64);
                    currentPawn = c.readInt();//this is the pawn user choses to move

                    //necessary for file output stats
                    color = playerArray[i][currentPawn-1].getColor();//stores current pawn's color value
                    //adds 1 to total 6's rolledm according to color
                    if(diceRoll ==6){

                        if(color == Color.red.getRGB()){
                            roll6Red+=1;
                        }if(color == Color.green.getRGB()){
                            roll6Green+=1;
                        }if(color == Color.yellow.getRGB()){
                            roll6Yellow+=1;
                        }if(color == Color.blue.getRGB()){
                            roll6Blue+=1;
                        }
                    }

                    // if statement checks if this pawn is in play AND its position is less than that of the home AND this current player has beaten another pawn (Essenitially checks to see if the pawn is allowed to move into the colored endzone
                    if(playerArray[i][currentPawn-1].isItSafe()==false&&playerArray[i][currentPawn-1].getPosition()<=  playerArray[i][currentPawn-1].getHomeBase() && playerArray[i][currentPawn-1].hasBeat()==true){//playerArray[i][currentPawn-1].getHomeBase()
                        //checks to see if the current pawn and its diceroll is within range to move into the endzone. The '-6' is the furthese position you can be behind the endzone and possibly reach
                        if(playerArray[i][currentPawn-1].getPosition() >playerArray[i][currentPawn-1].getHomeBase()-6 && playerArray[i][currentPawn-1].getPosition()+diceRoll < playerArray[i][currentPawn-1].getHomeBase()+7 &&playerArray[i][currentPawn-1].getPosition()+diceRoll >playerArray[i][currentPawn-1].getHomeBase()){
                            //is how further in pawn moves into endzone
                            homeToMove= (playerArray[i][currentPawn-1].getPosition() +diceRoll)-playerArray[i][currentPawn-1].getHomeBase();
                            playerArray[i][currentPawn-1].isSafe(homeToMove);//gives this to isSafe() which changes the pawn's boolean attribute isSafe to true which means it is in colored endzone and the method also changes the toMove attribute which acts as an index to draw the pawn in the colored endzone
                            if(homeToMove==6){
                                //statement runs when pawn has reached end zone fully (completely safe)
                                playerArray[i][currentPawn-1].reachedEndZone();//method changes reachedEndZone attribute to true which then ensures pawn is drawn in the endzone the next time it is drawn
                            }

                        }//statement rewritten due to error
                        else if(playerArray[i][currentPawn-1].getPosition()+diceRoll==18&&playerArray[i][currentPawn-1].getPosition() >25-6){
                            homeToMove= (playerArray[i][currentPawn-1].getPosition() +diceRoll)-playerArray[i][currentPawn-1].getHomeBase();
                            playerArray[i][currentPawn-1].isSafe(homeToMove);
                            if(homeToMove==6){
                                playerArray[i][currentPawn-1].reachedEndZone();
                            }
                        }
                        else{//if the pawn has not reached colored end zone or end zone it's drawn normally.
                            playerArray[i][currentPawn-1].movePawn(diceRoll,c);
                        }
                    }//checks to see if pawn is inside colored endzone AND has not reached endZone.
                    else if(playerArray[i][currentPawn-1].isItSafe()==true&&playerArray[i][currentPawn-1].returnInsideSafeZone()+diceRoll <7){
                        homeToMove=playerArray[i][currentPawn-1].returnInsideSafeZone()+diceRoll;//calculates the total amount it has to move inside the colored endzone
                        playerArray[i][currentPawn-1].isSafe(homeToMove);//gives this to the pawn which changes it homeToMove attribute
                        if(homeToMove==6){//if it has to move 6 inside of end zone, it is in other words reached end zone (total safety)
                            playerArray[i][currentPawn-1].reachedEndZone();//Changes this attribute which is important when drawing the pawn out
                        }
                    }
                    else{
                        playerArray[i][currentPawn-1].movePawn(diceRoll,c);//if it is not in any endzone territory, it is draw Normally
                    }

                    beatenOutput="";//output printed out when a turn is played
                    beatenOutput2="";//same thing as above

                    for(int y=0; y<playerArray.length;y++){//checls to see if any are beat.
                        for(int x=0;x<4;x++){
                            //if statement checks if the currnt player's pawn has landed on any other pawn (beat it), AND its not of the same color(because u cant beat your own pawn)
                            if(playerArray[y][x].getPosition() == playerArray[i][currentPawn-1].getPosition() && playerArray[y][x].getColor() !=  playerArray[i][currentPawn-1].getColor() && playerArray[y][x].getPosition()!=1 && playerArray[y][x].getPosition()!=14&& playerArray[y][x].getPosition()!=27&&  playerArray[y][x].getPosition()!=40&&  playerArray[y][x].getPosition()!=0  ){
                                playerArray[y][x].sendHome();

                                beatenOutput="Player "+(y+1)+"'s pawn no. "+(x+1)+" was taken out by player ";//this is added to the output when a pawn is beat to inform the players whose pawn took out whose. and which pawn number it was
                                beatenOutput2= (i+1)+"'s pawn no. " + currentPawn;

                                color = playerArray[i][currentPawn-1].getColor();
                                if(color == Color.red.getRGB()){
                                    timesBeatenRed += 1;
                                }if(color == Color.green.getRGB()){
                                    timesBeatenGreen += 1;
                                }if(color == Color.yellow.getRGB()){
                                    timesBeatenYellow += 1;
                                }if(color == Color.blue.getRGB()){
                                    timesBeatenBlue += 1;
                                }

                                for(int c=0; c<4;c++){

                                    playerArray[i][c].beaten();
                                }
                            }
                        }
                    }

                }



                c.clear();
                Board.drawBoard(c);
                //draws the output string at the coordinates, the beatenOutput is changed when a player's pawn is beat so these two methods then output that statement on who has been beat
                c.drawString(beatenOutput, 500,130);
                c.drawString(beatenOutput2, 500,140);

                //same for loop as made above and redraws pawns in their positions after the player
                for(int y=0; y<playerArray.length;y++){
                    for(int x=0;x<4;x++){
                        if(playerArray[y][x].isItSafe()==false){
                            playerArray[y][x].makePawn(c);
                        } else if(playerArray[y][x].isItSafe()==true&&playerArray[y][x].hasItReached()==false){
                            playerArray[y][x].drawPawnHome(c);
                        } else if(playerArray[y][x].isItSafe()==true&&playerArray[y][x].hasItReached()==true){
                            playerArray[y][x].drawEndZone(c);
                        }


                    }
                }
                winCounter =0;//counts the total pawns that have reached endZone
                for(int x=0;x<4;x++){
                    if(playerArray[i][x].hasItReached()){//checks to see if this specefic pawn has reached end zone
                        winCounter+=1;//adds 1 if it has
                    }
                }
                if(winCounter==4){//if a total of 4 pawns of this player has reached enzone current player has won
                    winningPlayer=i+1;
                    loop=false;//exits out of these two loops.
                    break;

                }
            }
            numTurns+=1;
        }
        c.println("Player "+winningPlayer+" won the game");//outputs who won the game

        player1 = new StringTokenizer(timesBeatenRed+ " " + numTurns+ " " +roll6Red);
        player2 = new StringTokenizer(timesBeatenGreen+ " " + numTurns+ " " +roll6Green);
        player3 = new StringTokenizer(timesBeatenYellow+ " " +numTurns+ " " +roll6Yellow);
        player4 = new StringTokenizer(timesBeatenBlue+ " " +numTurns+ " " +roll6Blue);

        for(int x = 0; x < 3; x++){   //displays stats of player red to file "gameStats.txt"
            if(roll6Red != 0){
                if(player1.countTokens() == 3){
                    gameStats.println("The number of times player red has beaten another pawn is: ");
                    gameStats.println(player1.nextToken());
                }else if(player1.countTokens() == 2){
                    gameStats.println("The total number of turns played by player red was: ");
                    gameStats.println(player1.nextToken());
                }else if(player1.countTokens() == 1){
                    gameStats.println("The number of times player red rolled a 6 was:  ");
                    gameStats.println(player1.nextToken());
                }
            }
            else if(roll6Red == 0){
                gameStats.println("Player red never played in this game.");
                x = 3;
            }
        }


        for(int x = 0; x < 3; x++){   //displays stats of player green to file "gameStats.txt"
            if(roll6Green != 0){
                if(player2.countTokens() == 3){
                    gameStats.println("The number of times player green has beaten another pawn is: ");
                    gameStats.println(player2.nextToken());
                }else if(player2.countTokens() == 2){
                    gameStats.println("The total number of turns played by player green was: ");
                    gameStats.println(player2.nextToken());
                }else if(player2.countTokens() == 1){
                    gameStats.println("The number of times player green rolled a 6 was:  ");
                    gameStats.println(player2.nextToken());
                }
            }
            else if(roll6Red == 0){
                gameStats.println("Player green never played in this game.");
                x = 3;
            }
        }


        for(int x = 0; x < 3; x++){        //displays stats of player yellow to file "gameStats.txt"
            if(roll6Yellow != 0){
                if(player3.countTokens() == 3){
                    gameStats.println("The number of times player yellow has beaten another pawn is: ");
                    gameStats.println(player3.nextToken());
                }else if(player3.countTokens() == 2){
                    gameStats.println("The total number of turns played by player yellow was: ");
                    gameStats.println(player3.nextToken());
                }else if(player3.countTokens() == 1){
                    gameStats.println("The number of times player yellow rolled a 6 was:  ");
                    gameStats.println(player3.nextToken());
                }
            }
            else if(roll6Yellow == 0){
                gameStats.println("Player yellow never played in this game.");
                x = 3;
            }
        }


        for(int x = 0; x < 3; x++){        //displays stats of player blue to file "gameStats.txt"
            if(roll6Blue != 0){
                if(player4.countTokens() == 3){
                    gameStats.println("The number of times player blue has beaten another pawn is: ");
                    gameStats.println(player4.nextToken());
                }else if(player4.countTokens() == 2){
                    gameStats.println("The total number of turns played by player blue was: ");
                    gameStats.println(player4.nextToken());
                }else if(player4.countTokens() == 1){
                    gameStats.println("The number of times player blue rolled a 6 was:  ");
                    gameStats.println(player4.nextToken());
                }
            }
            else if(roll6Blue == 0){
                gameStats.println("Player blue never played in this game.");
                x = 3;
            }


        }
        gameStats.close();
    }
}
