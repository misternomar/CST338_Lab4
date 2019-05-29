class BarcodeImage implements Cloneable
   {
      //dimensions of 2D array
      public static final int MAX_HEIGHT = 30;
      public static final int MAX_WIDTH = 65;
      //image data
      private boolean[][] imageData;
            
      //methods
      public BarcodeImage()
      {
         imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];

      }
      public BarcodeImage(String[] str_data) 
      {
         for(int i = 0; i < MAX_HEIGHT; i++)
            for(int j = 0; j < MAX_WIDTH; j++)
               if (str_data[i] != "0")
                  imageData[i][j] = true;
      }
   
      public boolean getPixel(int row, int col)
      {
         return imageData[row][col];
      }
      
      public boolean setPixel(int row, int col, boolean value)
      {
         imageData[row][col] = value;
         if (value)
            return value;
         else
            return value;
      }
      
      private void checkSize(String[] data)
      {
         
      }
}