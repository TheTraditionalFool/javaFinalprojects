/* Stephen Swick
 * 12/6/16
 * CodeBreaker Project for MCS 141
 * 
 */
import java.util.Scanner;
import java.io.*;


public class CodeBreaker {
  public static void main(String [] args) throws IOException{
    String primeInput;
    String decodeInput;
    StringBuilder input;
    StringBuilder output = new StringBuilder();
    int [] primeLower = new int[128]; //declare array to hold prime lowercase frequency
    int [] primeUpper = new int[128]; //declare array to hold prime uppercase frequency
    int [] decodeLower = new int[128]; //declare array to hold decode lowercase frequency
    int [] decodeUpper = new int[128]; //declare array to hold decode uppercase frequency
    Scanner scan = new Scanner(System.in); //create scanner to take user input
    System.out.println("Input priming read file name:"); //ask user to input file of priming read
    primeInput = scan.nextLine();
    File primeFile = new File(primeInput);
    primeLower = readLower(primeFile); //get array for lowercase assign it to array created
    primeUpper = readUpper(primeFile);//get array for uppercase assign it to array created
    System.out.println("Input filename that you want to decode:"); // ask user to to input filename of file wanting to decode
    decodeInput = scan.nextLine();
    File decodeFile = new File(decodeInput);
    decodeLower = readLower(decodeFile); //get array for lowercase assign it to array created
    decodeUpper = readUpper(decodeFile); //get array for uppercase assign it to array created
    int [] codeLower = new int[128];
    int [] codeUpper = new int[128];
    codeLower = codeBreak(primeLower, decodeLower); //get lowercase key
    codeUpper = codeBreak(primeUpper, decodeUpper); // get uppercase key
    FileOutputStream out = new FileOutputStream("output.txt", false); //create output file
    PrintWriter write = new PrintWriter(out);
    Scanner read = new Scanner(decodeFile); //read file you want to decode again
    int decode;
    /* Here is the piece of code where it will begin building the string for the output file.
     * It will run through the file you want to decode and for each character it will check
     * if its a lowercase or uppercase char.  Then based off the char it will choose the appropiate
     * key array and using that char as the memory location for the key array will get the value of
     * that memory location within the array which is supposed to be the ascii code for the character
     * that it should be.  Change that int into a char and append the output string to it.
     * NOTE if its neither a uppercase or lowercase aka a comma or something it should just append it to
     * the output string.
     */
    while( read.hasNext()){
      input = new StringBuilder( read.nextLine());
      for(int i = 0; i < input.length(); i++){
        if(((int)input.charAt(i)>= 97 && (int)input.charAt(i)<= 122 )){
          decode = codeLower[(int)input.charAt(i)];
          output.append((char)decode);
        }
        else if (((int)input.charAt(i)>= 65 && (int)input.charAt(i)<= 90)){
          decode = codeUpper[(int)input.charAt(i)];
          output.append((char)decode);
        }
        else{
          output.append(input.charAt(i));
        }
      }
      output.append("\r\n"); //manually add in newline char
    }
   write.println(output); //write the string in the file
   write.close(); //close to make sure everything goes in the file
    
  }
  /* This static method will read both files and count the frequency of letters as they appear.
   * This static method is the same as the readUpper one just that it will return an array keeping track
   * of the letter frequency amd retirm am array with LOWER case ascii values.
   * I made sure that this method keeps track of only the lower case letters by including an if statement.
   * The way the array is designed is that the position within the array is the ascii code of the letter
   * for example, int[97] will be the location for the ascii code 97 which is lowercase 'a' and it will hold
   * the value of how many times 'a' showed up in the file
   */
  public static int [] readLower(File text) throws IOException{
    int [] letters = new int[128];
    String input;
    Scanner read = new Scanner(text);
    while( read.hasNext()){
      input = read.nextLine();
      input = input.toLowerCase();
      for (int i = 0; i < input.length(); i++) {
        if(((int)input.charAt(i)>= 97 && (int)input.charAt(i)<= 122 ))
          letters[(int) input.charAt(i)]++;
      }
      
    }
    
    return letters;
  }
  /* This static method will read both files and count the frequency of letters as they appear.
   * This static method is the same as the readLower one just that it will return an array keeping track
   * of the letter frequency and return an array with the UPPERCASE ascii values.
   * I made sure that this method keeps track of only the upper case letters by including an if statement.
   * The way the array is designed is that the position within the array is the ascii code of the letter
   * for example, int[65] will be the location for the ascii code 65 which is lowercase 'A' and it will hold
   * the value of how many times 'A' showed up in the file
   * NOTE: the array returned for readUpper and readLower should hold the same values for 'A and a' just
   * at different locations for the ascii code.
   */
  public static int [] readUpper(File text) throws IOException{
    int [] letters = new int[128];
    String input;
    Scanner read = new Scanner(text);
    while( read.hasNext()){
      input = read.nextLine();
      input = input.toUpperCase();
      for (int i = 0; i < input.length(); i++) {
        if(((int)input.charAt(i)>= 65 && (int)input.charAt(i)<= 90))
          letters[(int) input.charAt(i)]++;
      }
      
    }
    
    return letters;
  }
  /* This method is the algorithm that will produce the key to break the code
   * takes in two array parameters, the priming read array and the array of the file you want to decode.
   * First it will make a copy of the two arrays then it will loop through the array for the information.
   * Then the copies will be looped through and keep track of the highest frequency.
   * Once the loop is finished looping the values are kept in int variables.
   * The construction of the code array then begins and the location of the code array gets the memory
   * location for the highest frequency letter in the priming array.
   * After the coppy arrays get their values changed to -1 at those memory locations to assure that
   * they wont get chosen again.
   * When this method is done you will have the key to break the code.
   * This method is ran twice for the lowercase key and uppercase key.
   */
  public static int [] codeBreak(int [] prime, int [] decode){
    int [] primeCopy = new int[prime.length];
    int [] decodeCopy = new int[decode.length];
    System.arraycopy(prime, 0, primeCopy, 0, prime.length);
    System.arraycopy(decode, 0, decodeCopy, 0, decode.length);
    
    for(int i = 0; i < decode.length; i++ ) {
      int decodeTrack = 0;
      int primeTrack = 0;
      for(int j = 60; j<decodeCopy.length; j++){
        if(decodeCopy[decodeTrack]<decodeCopy[j]){
          decodeTrack = j;
        }
      }
      for(int k =60; k<primeCopy.length; k++){
        if(primeCopy[primeTrack]<primeCopy[k]){
          primeTrack = k;
        }
      }
      if (decode[decodeTrack] > 0){
        decode[decodeTrack] = primeTrack;
      }
      decodeCopy[decodeTrack] = -1;
      primeCopy[primeTrack] = -1;
    }
    return decode;
  }
  
}
