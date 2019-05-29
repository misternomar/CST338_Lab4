/* Team 1
 * Austin Ah Loo
 * Ramon Lucindo
 * Mikie Reed
 * Mitchell Saunders
 * Nick Saunders
 * CST 338 - Module 4: Optical Barcode
 */

import java.lang.Math;
import java.util.Arrays;


public class Working_Assig4
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
      
      /*
      String[] sImageIn =
           {
                  "                                          ",
                  "                                          ",
                  "                                          ",
                  "* * * * * * * * * * * * * * * * * * * * * ",
                  "*                                       * ",
                  "****** **** ****** ******* ** *** *****   ",
                  "*     *    ****************************** ",
                  "* **    * *        **  *    * * *   *     ",
                  "*   *    *  *****    *   * *   *  **  *** ",
                  "*  **     * *** **   **  *    **  ***  *  ",
                  "***  * **   **  *   ****    *  *  ** * ** ",
                  "*****  ***  *  * *   ** ** **  *   * *    ",
                  "***************************************** "
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
                  "**************************************    "
            };
        */
         
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
   
   public static class BarcodeImage implements Cloneable
   {
      public static final int MAX_HEIGHT = 30;
      public static final int MAX_WIDTH = 65;
      private boolean[][] image_data;
      
      public BarcodeImage()
      {
       //chain the constructors, sending this on up with a dummy string array
         this(new String[0]); 
      }
      
      public BarcodeImage(String[] data)
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
         if (checkSize(data))
         {
            for (row = 0, displayRow = image_data.length - data.length; 
                  displayRow < image_data.length; row++, displayRow++)
            {
               for (col = 0; col < data[row].length(); col++)
               {
                  if (data[row].charAt(col) == DataMatrix.WHITE_CHAR)
                     setPixel(displayRow, col, false);
                  else
                     setPixel(displayRow, col, true);
               }
            }
         }
      }
      
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
      
      public void displayToConsole()
      {
         int row, col;
         char temp;
         
         // this method takes much from the TwoDimImage class
         //top border first
         System.out.println();
         for ( col = 0; col < MAX_WIDTH + 2; col++ )
            System.out.print("-");
         System.out.println();
         
         // then fill in the middle
         for (row = 0; row < MAX_HEIGHT; row++)
         {
            System.out.print("|");
            for (col = 0; col < MAX_WIDTH; col++)
            {
               temp = DataMatrix.boolToChar(image_data[row][col]);
               System.out.print(temp);
            }
            System.out.println("|");
         }
         
         // bottom
         for ( col = 0; col < MAX_WIDTH + 2; col++ )
            System.out.print("-");
         System.out.println();
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
      private String text;
      private int actualWidth, actualHeight, signalWidth, signalHeight;
      
      public DataMatrix()
      {
         //chaining constructors, i consider the third one to be the "highest"
         this("");
      }
      
      public DataMatrix(String text)
      {
         this(new BarcodeImage());
         if (!readText(text))
            readText("");
      }
      
      public DataMatrix(BarcodeImage image)
      {
         readText("");
         
         if (!scan(image))
            scan(new BarcodeImage());
      }
      
      
      public boolean scan(BarcodeImage image)
      {
         //i clone the BarcodeImage and catch an exception
         try
         {
            this.image = (BarcodeImage)image.clone();
         }
         catch (CloneNotSupportedException e)
         {
            return false;
         }
         cleanImage();
         
         //i added two members for ease of use later, signalWidth and Height
         signalWidth = computeSignalWidth();
         signalHeight = computeSignalHeight();
         actualWidth = signalWidth - 2;
         actualHeight = signalHeight - 2;
         
         return true;
      }
      
      public int getActualWidth() { return actualWidth; }
      public int getActualHeight() { return actualHeight; }
      
      
      public boolean readText(String text)
      {
         //mutator for text
         if (text.length() > BarcodeImage.MAX_WIDTH - 2)
            return false;
         this.text = text;
         return true;
      }
      
      public boolean generateImageFromText()
      {
         //Supposed to be called after readable text has been mutated 
         //via readText() and will generate a barcode image from it
         int strLength = this.text.length();
         String[] str_data = new String[BarcodeImage.MAX_HEIGHT];
         String defaultWhiteSpace = getStringOfMaxWidth(String.
                            valueOf(DataMatrix.WHITE_CHAR), BarcodeImage.MAX_WIDTH);
         
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
         char output = DataMatrix.WHITE_CHAR;
         char zeroOrOne;
         //call helper method to provide the actual binary string of the ascii
         //character (even though it is a String)
         zeroOrOne = asciiToBinary(String.valueOf(letter)).
                                                charAt(zeroThroughSeven);
         //If the value is 1, or true, then return a black character
         if (zeroOrOne == '1')
         {
            output = DataMatrix.BLACK_CHAR;
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
         int row, col, digit;
         char temp;
         
         //reset the text field then populate it
         readText("");
         
         for (col = 1; col < signalWidth - 1; col++)
         {
            temp = 0;
            for (row = BarcodeImage.MAX_HEIGHT - 2, digit = 0; 
                  row > BarcodeImage.MAX_HEIGHT - signalHeight; row--, digit++)
            {
               //based on the position of a "true" add the corresponding digit
               // (2^x) to the char's value
               if (image.getPixel(row, col) == true)
                  temp += (int)Math.pow(2, digit);
            }
            text += temp;
         }
         return true;
      }
      
      public void displayTextToConsole()
      {
         System.out.println(text);
      }
      
      public void displayImageToConsole()
      {
         int row, col;
         char temp;
         
         // similar to the display method in BarcodeImage, but don't show 
         //extra space
         System.out.println();
         for ( col = 0; col < signalWidth + 2; col++ )
            System.out.print("-");
         System.out.println();
         
         for (row = BarcodeImage.MAX_HEIGHT - signalHeight; 
               row < BarcodeImage.MAX_HEIGHT; row++)
         {
            System.out.print("|");
            for (col = 0; col < signalWidth; col++)
            {
               temp = boolToChar(image.getPixel(row, col));
               System.out.print(temp);
            }
            System.out.println("|");
         }
         
         for ( col = 0; col < signalWidth + 2; col++ )
            System.out.print("-");
         System.out.println();
      }
      
      private int computeSignalHeight()
      {
         //use the left column to determine height
         int height = 0;
         for (int row = 0; row < BarcodeImage.MAX_HEIGHT; row++)
         {
            if (image.getPixel(row, 0) == true)
               height++;
         }
         return height;
      }
      
      private int computeSignalWidth()
      {
         //use the bottom border to determine width
         int width = 0;
         for (int col = 0; col < BarcodeImage.MAX_WIDTH; col++)
         {
            if (image.getPixel(BarcodeImage.MAX_HEIGHT - 1, col) == true)
               width++;
         }
         return width;
      }
      
      //a static helper that i used elsewhere
      public static char boolToChar(boolean bool)
      {
         if (bool == true)
            return BLACK_CHAR;
         else
            return WHITE_CHAR;
      }
      
      private void cleanImage()
       {
          //displayRawImage();
          moveImageToLowerLeft();
          //displayRawImage();
          this.actualWidth = computeSignalWidth();
          this.actualHeight = computeSignalHeight();
       }

       private void moveImageToLowerLeft()
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

       private void shiftImage(int bottomSpineRow, int leftSpineColumn)
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
          try {
         image = (BarcodeImage) lowerLeft.clone();
      } catch (CloneNotSupportedException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
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
