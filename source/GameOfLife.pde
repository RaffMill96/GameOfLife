//Game of Life
int[][] cells;
int[][] cellsBuffer;
int cellSize = 10;

color dead = color(0);
color alive = color(200,0,100);

int interval = 100;
int lastRecTime = 0;

boolean pause = true;
float state;

void setup()
{
  size(1200, 700);
  
  cells = new int[width/cellSize][height/cellSize];
  cellsBuffer = new int[width/cellSize][height/cellSize];

  stroke(40);
  noSmooth();
  
  for(int x = 0; x < width/cellSize; x++)
  {
    for(int y = 0; y < height/cellSize; y++)
    {
      state = 0;
      cells[x][y] = int(state);
    }
  }
  background(0);
}

void draw()
{
  for (int x=0; x<width/cellSize; x++) 
  {
    for (int y=0; y<height/cellSize; y++) 
    {
      if (cells[x][y]==1) {
        fill(alive); // Color alive
      }
      else {
        fill(dead); // Color dead
      }
      rect (x*cellSize, y*cellSize, cellSize, cellSize); //shape 
    }
  }
  // Iterate if timer ticks
  if ((millis()*4)-lastRecTime>interval) {
    if (!pause) 
    {
      iteration();
      lastRecTime = millis()*4;
    }
  }
  
  if(pause && mousePressed)
  {
      int xCellOver = int(map(mouseX, 0, width, 0, width/cellSize));
    xCellOver = constrain(xCellOver, 0, width/cellSize-1);
    int yCellOver = int(map(mouseY, 0, height, 0, height/cellSize));
    yCellOver = constrain(yCellOver, 0, height/cellSize-1);

    // Check against cells in buffer
    if (cellsBuffer[xCellOver][yCellOver]==1) { // Cell is alive
      cells[xCellOver][yCellOver]=0; // Kill
      fill(dead); // Fill with kill color
    }
    else { // Cell is dead
      cells[xCellOver][yCellOver]=1; // Make alive
      fill(alive); // Fill alive color
    }
  } 
  else if (pause && !mousePressed) { // And then save to buffer once mouse goes up
    // Save cells to buffer (so we opeate with one array keeping the other intact)
    for (int x=0; x<width/cellSize; x++) {
      for (int y=0; y<height/cellSize; y++) {
        cellsBuffer[x][y] = cells[x][y];
      }
    }
  }
}//End of draw.
  
void iteration() 
{ 
  
 for (int x=0; x<width/cellSize; x++) 
 {
    for (int y=0; y<height/cellSize; y++) 
    {
      cellsBuffer[x][y] = cells[x][y];
    }
  }
 
 for (int x=0; x<width/cellSize; x++) 
 {
   for (int y=0; y<height/cellSize; y++) 
   {
     int n = 0;
     for(int nx=x-1; nx<=x+1; nx++)
     {
       for(int ny=y-1; ny<=y+1; ny++)
       {
         if (((nx>=0)&&(nx < width/cellSize))&&((ny>=0)&&(ny < height/cellSize)))
         {
           if(!((nx==x) && (ny==y)))
           {
             if(cellsBuffer[nx][ny] == 1)
             {  
               n++;
             }
           }
         }
       }
     }
     
     if(cellsBuffer[x][y]==1)
     {
         if(n < 2 || n > 3)
         {
           cells[x][y] = 0;
         }
         if(n == 2 || n == 3)
         {
           cells[x][y] = 1;
         }
     }
     else
     {
       if(n == 3)
       {
         cells[x][y] = 1;
       }
     }
   }  
 } 
}//END of iterate;

void keyPressed()
{
  if(key == ' ') // Pause
  {
    pause = !pause;
  }
  
  if(key == 'c' || key == 'C') // Clear all cells
  {
    for (int x=0; x<width/cellSize; x++) {
      for (int y=0; y<height/cellSize; y++) {
        state = 0;
        cells[x][y] = int(state); // Save state of each cell
      }
    }
  }
  
  if(key == 'r' || key == 'R')
  {
    for (int x=0; x<width/cellSize; x++) {
      for (int y=0; y<height/cellSize; y++) {
        float rand = random(100);
        int prob = 15;
        if(rand > prob)
        {
          state = 0;
        }
        if(rand <= prob)
        {
           state = 1; 
        }
        cells[x][y] = int(state); // Save state of each cell
        
      }
    }
    pause = true;
  }
  
}