/*
*Name: Tuong Luu
*Date: 23rd November, 2021
*Desription: Guessing Number
*/

import java.util.*;

public class A3GuessNum {

    //Create instance of Scanner and Random object
    static Scanner input = new Scanner(System.in);
    static Random rand = new Random();

    //Boolean for the game loop
    static boolean running = true;

    static int in, state, diff, score; //int for (user input, state for play() method, difficulty, and score)

    //int for bound
    static int lowBound = 1;
    static int highBound = 101;

    //String for user answer
    static String ans;

    //int for user lives and the initial live user has
    static final int INITIAL_LIVE = 5;
    static int live = INITIAL_LIVE;

    //main method
    public static void main(String[] arg){
        startGame();

        //game loop
        while(running) {
            play();
        }
        input.close();

    } //close main
    

    //method to start the game
    public static void startGame() {
        state = 1; //set state of play() method to 1
        
        System.out.println("Welcome to \"Guess your Number\"!");
        System.out.println("We will have you guess a number that is in the range of 1 to 100.\nYou have " + INITIAL_LIVE + " times to guess.");
        System.out.println("We have two difficulties for you to challange yourself.\n");
        System.out.print("Choose your difficulty (Type \"1\" for Easy or \"2\" for Hard): ");
        checkDiff();
    }

    //method for the gameplay
    public static void play() {
        //check if state is 1 and if there is any live
        int num = -1;
        if(state == 1 && live > 0) {
            num = rand.nextInt(highBound - lowBound) + lowBound; //generate random number
            
            //ask user for input
            System.out.print("Guess a number: ");
            check(); //call check() method

            //loop until user get the right number
            while(in != num && live > 0) {
                //if user's number is less than the generated number
                if(in < num) {
                    if(in < lowBound) {}
                    else {
                        lowBound = in;
                    }
                    System.out.println("\nLives left: " + live);
                    if(highBound == 101) {
                        System.out.println("Current range: (" + lowBound + "-" + (highBound-1) + ")");
                    } else {
                        System.out.println("Current range: (" + lowBound + "-" + highBound + ")");
                    }
                    //if difficulty is "1", then show the hints
                    if(diff == 1) {
                        if(num-in >= 1 && num-in <= 10) {
                            System.out.println("You are very close, just a bit higher");
                        } else if(Math.abs(in-num) > 50){
                            System.out.println("You are FREEZING right now!\nPlease choose a higher number.");
                        } else if(Math.abs(in-num) > 25) {
                            System.out.println("You are a bit cold right now.\nPlease choose a higher numer.");
                        } else if(Math.abs(in-num) > 10) {
                            System.out.println("You are very warm right now.\nPlease choose a higher number");
                        }
                    }
                    System.out.print("Enter a new number: ");
                    check();
                }

                //if user's number is more than the generated number
                else if(in > num) {
                    if(in > highBound) {}
                    else {
                        highBound = in;
                    }
                    System.out.println("\nLives left: " + live);
                    System.out.println("Current range: (" + lowBound + "-" + highBound + ")");
                    if(diff == 1) {
                        if(in-num >= 1 && in-num <= 10) {
                            System.out.println("You are very close, just a bit lower");
                        } else if(Math.abs(in-num) > 50){
                            System.out.println("You are FREEZING right now!\nPlease choose a lower number.");
                        } else if(Math.abs(in-num) > 25) {
                            System.out.println("You are a bit cold right now.\nPlease choose a lower numer.");
                        } else if(Math.abs(in-num) > 10) {
                            System.out.println("You are very warm right now.\nPlease choose a lower number");
                        }
                    }
                    System.out.print("Enter a new number: ");
                    check();
                }
            }

            //if wins and still has lives left (or one left)
            if(in == num && live >= 0) {
                System.out.println("\nCongrats! You are right");
                System.out.println("The number is: " + num);
                System.out.println("Do you want to continue? (Enter \"Yes\" or \"No\")");
                score += 1;
                state = 0;
            }
        }

        //if loses
        if(live == 0 && state == 1 && in != num) {
            System.out.println("\nYou Lose!");
            System.out.println("The number is: " + num);
            System.out.println("Do you want to continue? (Enter \"Yes\" or \"No\")");
            state = 0;
        }

        //ask if user want to continue the game
        if(state == 0) {
            checkContinue();
        }
    }

    //check user input for difficulty
    public static void checkDiff() {
        while(!input.hasNextInt()) {
            System.out.println("This is not a number!");
            System.out.print("Please enter a difficulty based on the number provided: ");
            input.next();
        }
        diff = input.nextInt();
        while(diff > 2 || diff < 1) {
            System.out.println("This is not a correct number!");
            System.out.print("Please enter a difficulty based on the number provided: ");
            checkDiff();
        }
    }

    //check user input for continue
    public static void checkContinue() {
        while(input.hasNextInt() || !(input.hasNext("Yes") || input.hasNext("No"))) {
            System.out.println("Wrong Syntax! Try Again! (Use \"Yes\" or \"No\")");
            input.next();
        }
        ans = input.nextLine();
        if(ans.equals("Yes")) {
            running = true;
            lowBound = 1;
            highBound = 101;
            live = INITIAL_LIVE;
            System.out.println("Loading...\n");
            System.out.print("Choose your difficulty (Type \"1\" for Easy or \"2\" for Hard): ");
            checkDiff();
            state = 1;
        } else if(ans.equals("No")) {
            System.out.println("\nScores: " + score);
            System.out.println("END");
            running = false;
        }
    }

    //check for input validation
    public static void check() {
        int count = 0;
        //check if user didn't type an int
        while(!input.hasNextInt()) {
            System.out.println("That is not a integer!");
            System.out.print("Please enter a number (int): ");
            input.next();
        }
        in = input.nextInt();
        
        //check if the input is in range
        while(in > 100 || in < 1) {
            count++;
            System.out.println("That number is not in range!");
            System.out.print("Please enter a new number (an int in the range of " + (lowBound+1) + "-" + (highBound-1) + "): ");
            check();
        }
        live += count;
        live--;
    }
}