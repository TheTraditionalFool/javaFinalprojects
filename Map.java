/**
 * Stephen Swick
 * MCS 141 Final project
 * 12/7/16
 * Procedural Generation
 */
import java.util.*;

public class Map {
    private int [][] array;

    public Map (int width, int height){ //Cronstructor for the class Map, takes 2 ints
        array = new int[height][width];
    }

    /*********************************************************************************************
     * This is where all the magic happens.  First it begins by seeding random points
     * on the gride with a random land tile.  Then it will begin going through the array
     * and check if the point on the grid has a value.  Then once it picks a point on the grid
     * it will check if its adjacent slots are within the array values.  Then I made separate
     * method to assist this method.  Previously I did not have the randSet method and my code
     * was 300 lines long.  Anyways the randSet will check what the value of the point is
     * and then it will choose a random value based on the parameters given to the chosen point
     * of the array.  Once the whole array is filled up it goes on the check if there are
     * any special locations using the isSpecial method.
     *********************************************************************************************/
    public void create(){
        Random rand = new Random();
        int size = getWidth()*getHeight();
        for(int i =0 ; i<(0.02*size); i++){
            int randRow = rand.nextInt(getHeight());
            int randWidth = rand.nextInt(getWidth());
            if(array[randRow][randWidth]==0){
                array[randRow][randWidth] = rand.nextInt(5)+1;
            }
        }
        for(int i = 0; i<size; i++) {
            for (int j = 0; j < getHeight(); j++) {
                for (int k = 0; k < getWidth(); k++) {
                    if (array[j][k] > 0) {
                        int choice = rand.nextInt(4)+1;
                        if (choice==1) {
                            if ((j - 1) >= 0) {
                                if (array[j - 1][k] == 0) {
                                    randSet(k, j, 0, -1);
                                }
                            }
                        }
                        if (choice==2) {
                            if ((j + 1) < getHeight()) {
                                if (array[j + 1][k] == 0) {
                                    randSet(k, j, 0, 1);
                                }
                            }
                        }
                        if (choice==3) {
                            if ((k - 1) >= 0) {
                                if (array[j][k - 1] == 0) {
                                    randSet(k, j, -1, 0);
                                }
                            }
                        }
                        if (choice==4) {
                            if ((k + 1) < getWidth()) {
                                if (array[j][k + 1] == 0) {
                                    randSet(k, j, 1, 0);
                                }
                            }
                        }
                    }
                }
            }
        }
        for(int i = 0; i<array.length; i++){
            for(int k = 0; k<array[i].length; k++){
                isSpecial(k,i);
            }
        }

    }

    public int getType(int x, int y){ // returns the value of the array 1-5 which is assosciated with a type
        return array[y][x];
    }

    public int getWidth(){ //return the width of the array
        return array[0].length;
    }

    public int getHeight(){ //return  the heigh of the array
        return array.length;
    }

    /**
     * Checks if the given cell in the array is a special location.
     * Will return a boolean, it does this by first checking if the cell
     * is not a water tile first.  Then after, using the probability parameters
     * given to us it will either change the boolean to true or remain false.
     * This method is looped through for every cell of the array during the
     * create method.
     */
    public boolean isSpecial(int x, int y){
        boolean special = false;
        Random rand = new Random();
        if(array[y][x] == 2){
            int check = rand.nextInt(1000);
            if(check<5){
                special = true;
            }

        }
        else if(array[y][x] == 3){
            int check = rand.nextInt(100);
            if(check<4){
                special = true;
            }
        }
        else if(array[y][x] == 4){
            int check = rand.nextInt(100);
            if(check<2){
                special = true;
            }
        }
        else if(array[y][x] == 5){
            int check = rand.nextInt(1000);
            if(check<5){
                special = true;
            }
        }
        return special;
    }

    /**
     * I created this method to shorten the length of my code. Basically it takes
     * all the parameters given to us for generating the random tile. but it will
     * take in 4 integer parameters. The array memory locations of k and j, and 2 integer
     * paramters that should be either -1,0,1 based off where the new tile location will be.
     *
     */

    public void randSet(int k, int j,int x, int y){
        Random rand = new Random();
        if(array[j][k]==1) {
            int check = rand.nextInt(115);
            if(check < 80 ){
                array[j+y][k+x] = 1;
            }
            else if(check >= 80 && check < 90){
                array[j+y][k+x] = 3;
            }
            else if(check >= 90 && check < 100){
                array[j+y][k+x] = 4;
            }
            else if(check >= 100 && check < 110){
                array[j+y][k+x] = 5;
            }
            else if(check >= 110 && check < 115){
                array[j+y][k+x] = 2;
            }
        }
        else if(array[j][k]==2) {
            int check = rand.nextInt(95);
            if(check < 75){
                array[j+y][k+x] = 2;
            }
            else if(check >= 75 && check < 85){
                array[j+y][k+x] = 5;
            }
            else if(check >= 85 && check < 90){
                array[j+y][k+x] = 1;
            }
            else if(check >= 90 && check < 95){
                array[j+y][k+x] = 3;
            }
        }
        else if(array[j][k]==3) {
            int check = rand.nextInt(110);
            if(check < 75){
                array[j+y][k+x] = 3;
            }
            else if(check >= 75 && check < 85){
                array[j+y][k+x] = 2;
            }
            else if(check >= 85 && check < 95){
                array[j+y][k+x] = 4;
            }
            else if(check >= 95 && check < 105){
                array[j+y][k+x] = 5;
            }
            else if(check >= 105 && check < 110){
                array[j+y][k+x] = 1;
            }
        }
        else if(array[j][k]==4) {
            int check = rand.nextInt(95);
            if(check < 75){
                array[j+y][k+x] = 4;
            }
            else if(check >= 75 && check < 85){
                array[j+y][k+x] = 5;
            }
            else if(check >= 85 && check < 90){
                array[j+y][k+x] = 3;
            }
            else if(check >= 90 && check < 95){
                array[j+y][k+x] = 1;
            }

        }
        else if(array[j][k]==5) {
            int check = rand.nextInt(85);
            if(check < 60){
                array[j+y][k+x] = 5;
            }
            else if(check >= 60 && check < 70){
                array[j+y][k+x] = 2;
            }
            else if(check >= 70 && check < 75){
                array[j+y][k+x] = 1;
            }
            else if(check >= 75 && check < 80){
                array[j+y][k+x] = 3;
            }
            else if(check >= 80 && check < 85){
                array[j+y][k+x] = 4;
            }
        }
    }
}
