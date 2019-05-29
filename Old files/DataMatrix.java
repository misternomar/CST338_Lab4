
class DataMatrix implements BarcodeIO
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
   
   public boolean scan(BarcodeImage bc)
   {
      try
      {
         image = bc.clone();
      }
      //Eclipse is giving me an error here, not sure what to do to fix it yet
      catch (CloneNotSupportedException e)‏
      {
         
      }
      cleanImage();
      this.actualWidth = computeSignalWidth();
      this.actualHeight = computeSignalHeight();
      return true;
   }
   
   public boolean readText(String text)
   {
      this.text = text;
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
      return true;
   }
   
   public void displayTextToConsole()
   {
      
   }
   
   public void displayImageToConsole()
   {
      
   }
   
   private int computeSignalWidth()
   {
      //Assuming that image has been cleaned up, start at the bottom left corner
      //and find the width of the image using the bottom solid line
      int ctr = 0;
      while (image.getPixel(BarcodeImage.MAX_WIDTH - 1, ctr) != false)
      {
         ctr++;
      }
      return --ctr;
   }
   
   private int computeSignalHeight()
   {
      //Assuming that image has been cleaned up, start at the bottom left corner
      //and find the height of the image using the left solid line
      int ctr = BarcodeImage.MAX_HEIGHT;
      while (image.getPixel(ctr, 0) != false)
      {
         ctr++;
      }
      return --ctr;
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
      //this method is wrong. It needs to not look at the text, but look at 
      //the image. This needs to be reworked.
      int bottomRowIndex = 0, leftColumnIndex = 0;
      
      //make an iterateable text array that contains each row of characters
      String[] textArray = text.split("\n");
      for (int row = textArray.length - 1; row >= 0; row--)
      {
         for (int column = 0; column < textArray.length - 1; column++)
         {
            if (this.image.getPixel(row, column) == true)
            {
               bottomRowIndex = row;
               leftColumnIndex = column;
            }
         }
      }
      //Use helper method to do the shifting, then overwrite the instance of
      //image with the cleaned version
      this.image = shiftImageToCorner(image, bottomRowIndex, leftColumnIndex);
   }
   
   private BarcodeImage shiftImageToCorner(BarcodeImage imageToShift,
         int bottomRowOfValidImage, int leftMostColumnOfValidImage)
   {
      //Create new image with max size that is empty.
      BarcodeImage newImage = new BarcodeImage();
      //Find row/column offsets to map image to new image in correct location
      int rowOffset = BarcodeImage.MAX_HEIGHT - bottomRowOfValidImage;
      int columnOffset = leftMostColumnOfValidImage;
      
      //Iterate through full-size empty image from bottom-to-top, left-to-right
      //and use offset to map old image to lower left corner of new.
      for (int row = BarcodeImage.MAX_HEIGHT - 1; row - rowOffset >= 0; row--)
      {
         for (int column = BarcodeImage.MAX_WIDTH - 1;
               column - columnOffset >= 0; column--)
         {
            newImage.setPixel(row, column,
                 imageToShift.getPixel(row - rowOffset, column - columnOffset));
         }
      }
      return newImage;
   }
}