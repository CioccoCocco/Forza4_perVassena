/*
 
 mtx[x][y]
 mtx.length = x;
 mtx[0].length = y;
 
*/

import java.lang.Math;

class ForzaQuattro
{
    //+++
    
    final String nL = System.getProperty("line.separator");
    
    public final static void cls()
    {
        try
        {
            final String os = System.getProperty("os.name");
            
            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
            System.out.print('\u000C');
        }
    }
    
    public static boolean checkWin(int mtx[][], int type)
    {
        int count;
        
        for(int y = 0; y < mtx[0].length; y++)
        {
            for(int x = 0; x < mtx.length; x++)
            {
                if(mtx[x][y] == type)
                {
                    count = 1;
                    
                    for(int i = 1; i < 4; i++) // orizzontali
                    {
                        if(x + i < mtx.length)
                        {
                            if(mtx[x+i][y] == type)
                            {
                                count++;
                            }
                        }
                    }
                    
                    if(count == 4)
                    {
                        return true;
                    }
                    
                    count = 1;
                    
                    for(int i = 1; i < 4; i++) // verticali
                    {
                        if(y + i < mtx[0].length)
                        {
                            if(mtx[x][y+i] == type)
                            {
                                count++;
                            }
                        }
                    }
                    
                    if(count == 4)
                    {
                        return true;
                    }
                    
                    count = 1;
                    
                    for(int i = 1; i < 4; i++) // diagonale dx
                    {
                        if(x + i < mtx.length || y - i < mtx[0].length)
                        {
                            if(mtx[x+i][y-i] == type)
                            {
                                count++;
                            }
                        }
                    }
                    
                    if(count == 4)
                    {
                        return true;
                    }
                    
                    count = 1;
                    
                    for(int i = 1; i < 4; i++) // diagonale sx
                    {
                        if(x - i < mtx.length || y - i < mtx[0].length)
                        {
                            if(mtx[x-i][y-i] == type)
                            {
                                count++;
                            }
                        }
                    }
                    
                    if(count == 4)
                    {
                        return true;
                    }
                    
                }
            }  
        }
        
        return false;
    }
    
    public static void wait(int ms)
    {
        
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        
    }
    
    // Controllo
    
    static int NPClastX;
    //static int turno;
    
    public static int blockWinner(int mtx[][], int type)
    {
        int count;
        
        for(int y = 0; y < mtx[0].length; y++)
        {
            for(int x = 0; x < mtx.length; x++)
            {
                if(mtx[x][y] == type)
                {
                    int i;
                    
                    count = 1;
                    
                    for(i = 1; i < 2; i++) // orizzontali
                    {
                        if(x + i < mtx.length)
                        {
                            if(mtx[x+i][y] == type)
                            {
                                count++;
                            }
                        }
                    }
                    
                    if(count == 3)
                    {
                        return (x+i+1);
                    }
                    
                    count = 1;
                    
                    for(i = 1; i < 3; i++) // verticali
                    {
                        if(y + i < mtx[0].length)
                        {
                            if(mtx[x][y+i] == type)
                            {
                                count++;
                            }
                        }
                    }
                    
                    if(count == 3)
                    {
                        return x;
                    }
                    
                    count = 1;
                    
                    for(i = 1; i < 3; i++) // diagonale dx
                    {
                        if(x + i < mtx.length || y - i < mtx[0].length)
                        {
                            if(mtx[x+i][y-i] == type)
                            {
                                count++;
                            }
                        }
                    }
                    
                    if(count == 3)
                    {
                        return (x+i+1);
                    }
                    
                    count = 1;
                    
                    for(i = 1; i < 3; i++) // diagonale sx
                    {
                        if(x - i < mtx.length || y - i < mtx[0].length)
                        {
                            if(mtx[x-i][y-i] == type)
                            {
                                count++;
                            }
                        }
                    }
                    
                    if(count == 3)
                    {
                        return (x-i-1);
                    }
                    
                }
            }  
        }
        
        return -1;
    }
    
    public static int bestMoveAuxCheck(int mtx[][], int ck, int spawnX, int spawnY)
    {
        int temp;
        
        if(ck == 1)
        {
            temp = blockWinner(mtx,ck);
            if(temp < mtx[0].length && temp > 0)
            {
                return temp;
            }
        }
        else if(ck == 2)
        {
            
            int pos;
            
              do
              {
                 
                  pos = (int) ((Math.random()*7)-3);
                  
              }while(disponibilitaColonna(mtx,pos) == -1);
              
              return pos;
            
        }
        
        return -1;
    }
    
    public static int bestMove(int mtx[][])
    {
        int x,y,temp;
        
        for(y = 0; y<mtx[0].length; y++)
        {
            for(x = 0; x<mtx.length; x++)
            {
                temp = bestMoveAuxCheck(mtx,mtx[x][y],x,y);
                
                if(temp != -1 && temp < mtx.length)
                {
                    return temp;
                }
            }   
        }   
        
        return -1;
    }
    
    // Generazione
    
    public static void fillMtx(int mtx[][])
    {
       
        int x,y;
        
        for(y = 0; y<mtx[0].length; y++)
        {
            for(x = 0; x<mtx.length; x++)
            {
                mtx[x][y] = 0;   
            }   
        }
        
        mtx[0][0] = 1; 
        mtx[1][0] = 2; 
    }
    
    public static int mossaNPC(int mtx[][], int mode)
    {
          
        int pos;
        
          if(mode == 0 || turno == 0)
          {
              do
              {
                 
                  pos = (int) (Math.random()*mtx.length);
                  
              }while(mtx[pos][0] != 0);
              
              NPClastX = pos;
              return pos;
          }
          else if(mode == 1)
          {
               NPClastX = bestMove(mtx);
               return NPClastX;
          }
         
        return -1;
    }
    
    // Print
    
    public static void stampaCampo(int mtx[][])
    {
        cls();
        int x,y;
        String sepLine;
        
        //Top
        sepLine = "+";
        
        for(x = 0; x<mtx.length; x++)
        {
             sepLine += "---+";    
        } 
        
        System.out.print(sepLine);
        System.out.println();
        
        // Body
        
        for(y = 0; y<mtx[0].length; y++)
        {
            for(x = 0; x<mtx.length; x++)
            {
                if(mtx[x][y] == 1)
                {
                    System.out.print("| O ");    
                }
                else if(mtx[x][y] == 2)
                {
                    System.out.print("| X ");    
                }
                else
                {
                    System.out.print("|   ");      
                }
                
            } 
            
            System.out.print("|");
            System.out.println();
            System.out.print(sepLine);
            System.out.println();
            
        }
        
    }
    
    // ===================================
    
    public static void main(String args[])
    {
        
        int[][] mtx = new int[27][11];
        
        fillMtx(mtx);
        stampaCampo(mtx);
        
    }
    
}