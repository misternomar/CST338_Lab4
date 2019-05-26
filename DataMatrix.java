
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
      this.image = image;
      //call scan();
   }
   
   public DataMatrix(String text)
   {
      readText(text);
   }
   
   public boolean scan(BarcodeImage bc)
   {
      image = bc.clone();
      cleanImage(); //call this at some point
      return true;
   }
   
   public boolean readText(String text)
   {
      this.text = text;
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
   
   private int computeSignalWidth()
   {
      return 0;
   }
   
   private int computeSignalHeight()
   {
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
      BarcodeImage newImage = new BarcodeImage();
      int bottomRowIndex, leftColumnIndex;
      
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
      
      //now to shift the entire image to the bottom left
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
   }
}