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
public class Assig4
{
   /**
    * main() 
    *
    * @param args the command line arguments
    */
   public static void main(String[] args)
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
         image_data = new boolean[MAX_HEIGHT][MAX_WIDTH];
         for (int row = 0; row < str_data.length - 1; row++)
         {
            for (int column = 0; column < str_data[row].length() - 1; column++)
            {
               boolean setTrue = (str_data[row].charAt(column) == '*');
               this.setPixel(row, column, setTrue);
            }
         }
      }

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
     
      private boolean checkSize(int row, int col)
      {
         //Checks for bounds of rows and col being within Max height/width
         return (row < MAX_HEIGHT && row >= 0 && col < MAX_WIDTH && col >= 0 );
      }

      /*
       * Will checkSize be used here?
       * 
       * 
       */
      private boolean checkSize(String[] data)
      {
         return false;
      }

      public BarcodeImage clone()
      {
         BarcodeImage barcodeImage = new BarcodeImage();
         for (int row = MAX_HEIGHT - 1; row >= 0; row--)
         {
            for (int column = 0; column < MAX_WIDTH; column++)
            {
               barcodeImage.setPixel(row, column, this.getPixel(row, column));
            }
         }
         return barcodeImage;
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
            this.image = bc.clone();
         } catch (Exception CloneNotSupportedException)
         {
         }
         cleanImage();
         return true;
      }

      public boolean generateImageFromText()
      {
       //Supposed to be called after the text has been mutated via readText();
         BarcodeImage newImage = new BarcodeImage();
         //Assuming that text will be separated by \n, create an array of strings
         String[] textArray = text.split("\n");
         //Iterate through each text line, then each char in the line
         //and if it is not whitespace, then set the pixel as true.
         for (int row = 0; row < textArray.length; row++)
         {
            for (int column = 0; column < textArray[row].length(); column++)
            {
               if (textArray[row].charAt(column) != ' ')
               {
                  newImage.setPixel(row, column, true);
               }
            }
         }
         //now that the image has been created from the text, it is out of place
         //and needs to be shifted. Call cleanImage();
         cleanImage();
         return true;
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

      private void cleanImage()
      {
         displayRawImage();
         moveImageToLowerLeft();
         displayRawImage();
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
         image = lowerLeft.clone();
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
