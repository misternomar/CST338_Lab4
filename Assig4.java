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
      System.out.println("hello");
   }
   
   public interface BarcodeIO
   {
      public boolean scan(BarcodeImage bc);
      public boolean readText(String text);
      public boolean generateImageFromText();
      public boolean translateImageToText();
      public void displayTextToConsole();
      public void displayImageToConsole();
   
   }
}
