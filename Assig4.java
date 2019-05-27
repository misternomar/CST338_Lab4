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
    * Starting point for program
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
   /*
    * Phase1
    */
   public interface BarcodeIO
   {
      public boolean scan(BarcodeImage bc);
      public boolean readText(String text);
      public boolean generateImageFromText();
      public boolean translateImageToText();
      public void displayTextToConsole();
      public void displayImageToConsole();
   }
   
   /*
    * Phase2
    */
   public static class BarcodeImage implements Cloneable
   {
      // dimensions of 2D array
      public static final int MAX_HEIGHT = 30;
      public static final int MAX_WIDTH = 65;
      // image data
      private boolean[][] imageData;

      // methods
      public BarcodeImage()
      {
         imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];
      }

      public BarcodeImage(String[] str_data)
      {
         imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];
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
            this.imageData[row][col] = value;
            return true;
         }
         return false;
      }
      
      public boolean getPixel(int row, int col)
      {
         if (checkSize(row, col))
         {
            return this.imageData[row][col];
         }
         return false;
      }
     

      private boolean checkSize(int row, int col)
      {
         return (row < MAX_HEIGHT && col < MAX_WIDTH);
      }

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
      
   /*
    * Phase 3
    */

   public static class DataMatrix implements BarcodeIO
   {
      public static final char BLACK_CHAR = '*';
      public static final char WHITE_CHAR = ' ';
      private BarcodeImage image;
      private String text = "";
      private int actualWidth = 0, actualHeight = 0;

      public DataMatrix()
      {
         this.image = new BarcodeImage();
      }

      public DataMatrix(BarcodeImage image)
      {
         scan(image);
      }

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
         // set actualWidth and actualHeight
         return true;
      }

      public boolean generateImageFromText()
      {
         return true;
      }

      public boolean translateImageToText()
      {
         return true;
      }

      public void displayTextToConsole()
      {

      }

      public void displayImageToConsole()
      {

      }

      private char readCharFromCol(int col)
      {
         return 0;
      }

      private boolean writeCharToCol(int col, int code)
      {
         return false;
      }

      private int computeSignalWidth()
      {
         // determine size based on spine
         return 0;
      }

      private int computeSignalHeight()
      {
         // determine size based on spine
         return 0;
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
               return row;
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
         System.out.println("-------------------------------------------------");
      }
   }

  
   
   
}
