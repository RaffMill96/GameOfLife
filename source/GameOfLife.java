import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class GameOfLife extends PApplet {

//Game of Life
int[][] cells;
int[][] cellsBuffer;
int cellSize = 5;
int gen = 0;

int dead = color(0);
int alive = color(200,0,100);

PFont genTxt;

int interval = 100;
int lastRecTime = 0;

boolean pause = true;
float state;

public void setup()
{
  
  
  cells = new int[width/cellSize][height/cellSize];
  cellsBuffer = new int[width/cellSize][height/cellSize];

  stroke(40);
  
  
  genTxt = createFont("Arial", 15, true); // Arial, 30 point, anti-aliasing on
 
  for(int x = 0; x < width/cellSize; x++)
  {
    for(int y = 0; y < height/cellSize; y++)
    {
      state = 0;
      cells[x][y] = PApplet.parseInt(state);
    }
  }
  background(0);
}

public void draw()
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
      gen++;
      //println(gen);
      
      textFont(genTxt, 15);
      fill(255);
      text("Generation: " +gen, 10, 20);
    
      lastRecTime = millis()*4;
    }
    else 
    {
      textFont(genTxt, 15);
      fill(255);
      text("Generation: " + gen, 10, 20);
    }
  }
  
  if(pause && mousePressed)
  {
      int xCellOver = PApplet.parseInt(map(mouseX, 0, width, 0, width/cellSize));
    xCellOver = constrain(xCellOver, 0, width/cellSize-1);
    int yCellOver = PApplet.parseInt(map(mouseY, 0, height, 0, height/cellSize));
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
  
public void iteration() 
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

public void keyPressed()
{
  if(key == ' ') // Pause
  {
    pause = !pause;
  }
  
  if(key == 'c' || key == 'C') // Clear all cells
  {
    gen = 0;
    for (int x=0; x<width/cellSize; x++) {
      for (int y=0; y<height/cellSize; y++) {
        state = 0;
        cells[x][y] = PApplet.parseInt(state); // Save state of each cell
      }
    }
    pause = true;
  }
  
  if(key == 'r' || key == 'R')
  { 
    gen = 0;
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
        cells[x][y] = PApplet.parseInt(state); // Save state of each cell
        
      }
    }
    pause = true;
  }
  
}
  public void settings() {  size(1200, 700);  noSmooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "GameOfLife" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
