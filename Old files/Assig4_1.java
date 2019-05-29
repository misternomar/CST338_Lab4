/* Team 1
 * Austin Ah Loo
 * Ramon Lucindo
 * Mikie Reed
 * Mitchell Saunders
 * Nick Saunders
 * CST 338 - Module 4: Optical Barcode
 */

import java.util.*;

/**
 * Class that contains all parts of Lab 4 - Optical Barcode
 *
 * @author Team 1
 */
public class test
{
   /**
    * main() 
    *
    * @param args the command line arguments
 * @throws CloneNotSupportedException 
    */
   public static void main(String[] args) throws CloneNotSupportedException
   {
      String[] sImageIn =
      {
         "                                               ",
         "                                               ",
         "                                               ",
         "     * * * * * * * * * * * * * * * * * * * * * ",
         "     *                                       * ",
         "     ****** **** ****** ******* ** *** *****   ",
         "     *     *    ****************************** ",
         "     * **    * *        **  *    * * *   *     ",
         "     *   *    *  *****    *   * *   *  **  *** ",
         "     *  **     * *** **   **  *    **  ***  *  ",
         "     ***  * **   **  *   ****    *  *  ** * ** ",
         "     *****  ***  *  * *   ** ** **  *   * *    ",
         "     ***************************************** ",  
         "                                               ",
         "                                               ",
         "                                               "

      };          
      
      String[] sImageIn_2 =
      {
            "                                          ",
            "                                          ",
            "* * * * * * * * * * * * * * * * * * *     ",
            "*                                    *    ",
            "**** *** **   ***** ****   *********      ",
            "* ************ ************ **********    ",
            "** *      *    *  * * *         * *       ",
            "***   *  *           * **    *      **    ",
            "* ** * *  *   * * * **  *   ***   ***     ",
            "* *           **    *****  *   **   **    ",
            "****  *  * *  * **  ** *   ** *  * *      ",
            "**************************************    ",
            "                                          ",
            "                                          ",
            "                                          ",
            "                                          "

      };
     
      BarcodeImage bc = new BarcodeImage(sImageIn);
      DataMatrix dm = new DataMatrix(bc);
     
      // First secret message
      dm.translateImageToText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();
      
      // second secret message
      bc = new BarcodeImage(sImageIn_2);
      dm.scan(bc);
      dm.translateImageToText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();
      
      // create your own message
      dm.readText("What a great resume builder this is!");
      dm.generateImageFromText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();
   }   
   /**
    * BarcodeIO
    **/
   interface BarcodeIO
   {
      public boolean scan(BarcodeImage bc);
      public boolean readText(String text);
      public boolean generateImageFromText();
      public boolean translateImageToText();
      public void displayTextToConsole();
      public void displayImageToConsole();
   }
   

   /**
    * stores and retrieves 2D data thought
    * of conceptually as an image of a 
    * square or rectangular bar code
   */
   public static class BarcodeImage implements Cloneable
   {
      // dimensions of 2D array
      public static final int MAX_HEIGHT = 30;
      public static final int MAX_WIDTH = 65;
      // image data
      private boolean[][] image_data;

      /**
       * Default constructor
       **/
      public BarcodeImage()
      {
         image_data = new boolean[MAX_HEIGHT][MAX_WIDTH];
      }
      
      /**
       * Constructor for BarcodeImage -
       * Takes a 1D array of Strings and 
       * converts it to the internal 2D 
       * array of booleans.
       */      
      public BarcodeImage(String[] str_data)
      {
        int row, col, displayRow;
        //instantiate the data and set all to false
          image_data = new boolean[MAX_HEIGHT][MAX_WIDTH];
          for (row = 0; row < image_data.length; row++)
          {
             for (col = 0; col < image_data[row].length; col++)
                image_data[row][col] = false;
          }
          
        //sanity check the length of the data, if ok populate the array
          if (checkSize(str_data))
          {
             for (row = 0, displayRow = image_data.length - str_data.length; 
                   displayRow < image_data.length; row++, displayRow++)
             {
                for (col = 0; col < str_data[row].length(); col++)
                {
                   if (str_data[row].charAt(col) == DataMatrix.WHITE_CHAR)
                      setPixel(displayRow, col, false);
                   else
                      setPixel(displayRow, col, true);
                }
             }
          }
      }
      
      public boolean getPixel(int row, int col)
      {
         try 
         {
            return image_data[row][col];   
         }
         catch (ArrayIndexOutOfBoundsException e)
         {
            return false;
         }
      }
      
      public boolean setPixel(int row, int col, boolean value)
      {
         try 
         {
            image_data[row][col] = value;
         }
         catch (ArrayIndexOutOfBoundsException e)
         {
            return false;
         }
         return true;
      }

      /*
      public boolean setPixel(int row, int col, boolean value)
      {
         if (checkSize(row, col))
         {
            this.image_data[row][col] = value;
            return true;
         }
         return false;
      }
      
      public boolean getPixel(int row, int col)
      {
         if (checkSize(row, col))
         {
            return this.image_data[row][col];
         }
         return false;
      }
      */
     
      /*
      private boolean checkSize(int row, int col)
      {
         //Checks for bounds of rows and col being within Max height/width
         return (row < MAX_HEIGHT && row >= 0 && col < MAX_WIDTH && col >= 0 );
      }

     
      private boolean checkSize(String[] data)
      {
         return false;
      }
      */
      
      private boolean checkSize(String[] data)
      {
         //important method to make sure the string is not too long
         if (data == null || data.length > MAX_HEIGHT)
            return false;
         for (int k = 0; k < data.length; k++)
         {
            if (data[k] == null || data[k].length() > MAX_WIDTH)
               return false;
         }
         return true;
      }
      
      /*
      public BarcodeImage clone()
      {  
         BarcodeImage barcodeImage = new BarcodeImage();
         //barcodeImage = (BarcodeImage) super.clone();
         for (int row = MAX_HEIGHT - 1; row >= 0; row--)
         {
            for (int column = 0; column < MAX_WIDTH; column++)
            {
               barcodeImage.setPixel(row, column, this.getPixel(row, column));
            }
         }
         return barcodeImage;
      }
      */
      public Object clone() throws CloneNotSupportedException
      {
         //cloning in the standard way
         int row, col;
         BarcodeImage newObject = (BarcodeImage)super.clone();
         
         //the image data needs to be moved over manually
         newObject.image_data = new boolean[MAX_HEIGHT][MAX_WIDTH];
         for ( row = 0; row < MAX_HEIGHT; row++ )
            for ( col = 0; col < MAX_WIDTH; col++ )
               newObject.image_data[row][col] = this.image_data[row][col];
         
         return newObject;
      }
   }
   
   /**
    * Phase 3 : DataMatrix 
    *    Implement of BarcodeIO interface
    *    cleans, translates, generates, 
    *    displays
    * 
    */
   public static class DataMatrix implements BarcodeIO
   {
      public static final char BLACK_CHAR = '*';
      public static final char WHITE_CHAR = ' ';
      private BarcodeImage image;
      private String text = "";
      private int actualWidth = 0, actualHeight = 0;

      
      //default constructor. 
      //Sets DataMatrix to be blank
      public DataMatrix()
      {
         this.image = new BarcodeImage();
      }
      
      //Sets DataMatric with image data
      public DataMatrix(BarcodeImage image)
      {
         scan(image);
      }
      
      //String constructor.
      //sets DataMatrx with string data
      public DataMatrix(String text)
      {
         readText(text);
      }

      public boolean readText(String text)
      {
         this.text = text;
         return true;
      }

      public boolean scan(BarcodeImage bc)
      {
         try
         {
            this.image = (BarcodeImage)bc.clone();
         } catch (Exception CloneNotSupportedException)
         {
         }
         try {
         cleanImage();
      } catch (CloneNotSupportedException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
         return true;
      }

      public boolean generateImageFromText()
      {
         //Supposed to be called after readable text has been mutated 
         //via readText() and will generate a barcode image from it
         int strLength = this.text.length();
         String[] str_data = new String[BarcodeImage.MAX_HEIGHT];
         String defaultWhiteSpace = getStringOfMaxWidth(String.
                            valueOf(this.WHITE_CHAR), BarcodeImage.MAX_WIDTH);
         
         //Fill every element of the array with the max amount of white space
         Arrays.fill(str_data, defaultWhiteSpace);
         
         String startString = getStringOfMaxWidth(String.
               valueOf(DataMatrix.BLACK_CHAR) + " ", (strLength + 1) / 2);
         startString = startString.substring(0, strLength);
         String stopString = getStringOfMaxWidth(String.
               valueOf(DataMatrix.BLACK_CHAR), strLength);
         
         
         str_data[0] = startString;
         //Iterate through all 8 powers of 2, starting with 7 and go to 0
         //Note: 8 - powerOf2 statement has had an offset of 1 to allow for
         //the startString (defined above) to be added and allow loop to work.
         for (int powerOf2 = 7; powerOf2 >=0; powerOf2--)
         {
            //Start every barcode image line with a black char
            str_data[8 - powerOf2] = String.valueOf(DataMatrix.BLACK_CHAR);
            
            //Iterate though the entire width of the barcode
            for (int i = 1; i < BarcodeImage.MAX_WIDTH; i++)
            {
               //If the index of the string is still within the length of the
               //text we're converting, then append either a white or black 
               //character. Uses generateBitMarker private method for help.
               if (i < strLength + 1)
               {
                  str_data[8 - powerOf2] += 
                        generateBitMarker(this.text.charAt(i - 1), 7 - powerOf2);
               }
               else
               {
                  str_data[8 - powerOf2] += DataMatrix.WHITE_CHAR;
               }
            }
         }
         str_data[9] = stopString;
         //Calls scan, which will mutate the image as well as clean it up.
         scan(new BarcodeImage(str_data));
         return true;
      }
     
      private String getStringOfMaxWidth(String singleCharToRepeat,
                                                               int numOfTimes)
      {
         //Helper method that will provide a repeated string a specific 
         //number of times.
         String output = "";
         for (int i = 0; i < numOfTimes; i++)
         {
            output += singleCharToRepeat;
         }
         return output;
      } 
      
      private char generateBitMarker(char letter, int zeroThroughSeven)
      {
         //When provided with a char and an integer, will return either a black
         //or white character depending on the nature of the binary rep of
         //that ascii character.
         //The integer represents powers of 2 (0=1, 1=2, 2=4, 3=8, 4=16,
         //5=32, 6=64, and 7=128)
         char output = this.WHITE_CHAR;
         char zeroOrOne;
         //call helper method to provide the actual binary string of the ascii
         //character (even though it is a String)
         zeroOrOne = asciiToBinary(String.valueOf(letter)).
                                                charAt(zeroThroughSeven);
         //If the value is 1, or true, then return a black character
         if (zeroOrOne == '1')
         {
            output = this.BLACK_CHAR;
         }
         return output;
      }
      
      private String asciiToBinary(String asciiChar)
      {
         //Helper method composed to provide a binary string that represents
         //a single ascii character (represented as a single character String)
         byte[] bytes = asciiChar.getBytes();
         StringBuilder binary = new StringBuilder();
         for (byte b : bytes)
         {
            int val = b;
            for (int i = 0; i < 8; i++)
            {
               binary.append((val & 128) == 0 ? 0 : 1);
               val <<= 1; //left bit shift, equivenant to val *= 2^1
            }
         }
         return binary.toString();
      }

      public boolean translateImageToText()
      {
         String text = "";
         
         for (int row = 0; row <= BarcodeImage.MAX_HEIGHT - 1; row++)
         {
            for (int column = 0; column < BarcodeImage.MAX_WIDTH; column++)
            {
               if (this.image.getPixel(row, column) == true)
               {
                  text += BLACK_CHAR;
               }
               else
               {
                  text += WHITE_CHAR;
               }  
            }
            text += "\n";
         }
         this.text = text;
         System.out.println(text);
         return true;
      }

      public void displayTextToConsole()
      {
         System.out.println(this.text);
      }

      public void displayImageToConsole()
      {
         String output = getRepeatedString('_', this.actualWidth + 2) + "\n";
         
         for (int row = this.actualHeight; row <= BarcodeImage.MAX_HEIGHT - 1;
               row++)
         {
            output += "|";
            for (int column = 0; column <= this.actualWidth; column++)
            {
               if (this.image.getPixel(row, column) == true)
               {
                  output += BLACK_CHAR;
               }
               else
               {
                  output += WHITE_CHAR;
               }  
            }
            output += "|\n";
         }
         output += getRepeatedString('_', this.actualWidth + 2) + "\n";
         System.out.println(output);
      }
      
      private String getRepeatedString(char charToRepeat, int numOfTimes)
      {
         char[] chars = new char[numOfTimes];
         Arrays.fill(chars, charToRepeat);
         String result = new String(chars);
         return result;
      }

      private char readCharFromCol(int col)
      {
         return 0;
      }
      /*
       * With writeCharToCol be used?
       * 
       */
      private boolean writeCharToCol(int col, int code)
      {
         return false;
      }

      private int computeSignalWidth()
      {
         //Assuming that image has been cleaned up, start at the bottom left 
         //corner and find the width of the image using the bottom solid line
         int ctr = 0;
         while (image.getPixel(BarcodeImage.MAX_HEIGHT - 1, ctr) != false)
         {
            ctr++;
         }
         return --ctr;
      }

      private int computeSignalHeight()
      {
         //Assuming that image has been cleaned up, start at the bottom left 
         //corner and find the height of the image using the left solid line
         int ctr = BarcodeImage.MAX_HEIGHT - 1;
         while (image.getPixel(ctr, 0) != false)
         {
            ctr--;
         }
         return ++ctr;
      }
      
      public int getActualWidth()
      {
         return this.actualWidth;
      }

      public int getActualHeight()
      {
         return this.actualHeight;
      }

      private void cleanImage() throws CloneNotSupportedException
      {
         displayRawImage();
         moveImageToLowerLeft();
         displayRawImage();
         this.actualWidth = computeSignalWidth();
         this.actualHeight = computeSignalHeight();
      }

      private void moveImageToLowerLeft() throws CloneNotSupportedException
      {
         int leftSpineColumn = getStartingColumnIndex();
         int bottomSpineRow = getLastRow(leftSpineColumn);
         shiftImage(bottomSpineRow, leftSpineColumn);
      }

      private int getStartingColumnIndex()
      {
         for (int row = 0; row < BarcodeImage.MAX_HEIGHT - 1; row++)
         {
            for (int col = 0; col < BarcodeImage.MAX_WIDTH - 1; col++)
            {
               if (image.getPixel(row, col))
               {
                  return col;
               }
            }
         }
         return 0;
      }

      private int getLastRow(int leftSpineColumn)
      {
         boolean inSpine = false;
         for (int row = 0; row < BarcodeImage.MAX_HEIGHT - 1; row++)
         {
            if (image.getPixel(row, leftSpineColumn))
            {
               inSpine = true;
            } else if (inSpine)
            {
               return --row;
            }
         }
         return 0;
      }

      private void shiftImage(int bottomSpineRow, int leftSpineColumn) throws CloneNotSupportedException
      {
         BarcodeImage lowerLeft = new BarcodeImage();
         int maxHeight = BarcodeImage.MAX_HEIGHT - 1;
         int diffToBottomRow = maxHeight - bottomSpineRow;
         for (int row = maxHeight; row >= 0; row--)
         {
            for (int col = 0; col < BarcodeImage.MAX_WIDTH; col++)
            {
               lowerLeft.setPixel(row, col, this.image.getPixel(
                         row - diffToBottomRow, col + leftSpineColumn));
            }
         }
         this.image = (BarcodeImage) lowerLeft.clone();
      }

      public void displayRawImage()
      {
         String rawImage = "";
         for (int row = 0; row < BarcodeImage.MAX_HEIGHT; row++)
         {
            rawImage += "|";
            for (int col = 0; col < BarcodeImage.MAX_WIDTH; col++)
            {
               if (image.getPixel(row, col))
               {
                  rawImage += "*";
               } else
               {
                  rawImage += " ";
               }
            }
            rawImage += "|\n";
         }
         System.out.print(rawImage);
         System.out.println("--------------------------------------------");
      }
   }
        
}

/**********OutPut***********************************************
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 *******************************************/
