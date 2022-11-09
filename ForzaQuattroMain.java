class ForzaQuattroMain {
    
    final static String nL = System.getProperty("line.separator");
    
    public final static void cls()  {
        try {
            final String os = System.getProperty("os.name");
            
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else    {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)   {
            //  Handle any exceptions.
            System.out.print('\u000C');
        }
    }
    
    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)  {
            Thread.currentThread().interrupt();
        }
        
    }
    
    public static int disponibilitaColonna( int[][] matr, int c ){
        
        if( c >= matr[0].length || c < 0 )  {
            return -2;
        }
        
        for( int x = 0; x < matr.length; x++ )  {
            if( matr[x][c] == 1 || matr[x][c] == 2 ){
                return x-1;        
            } else if( x == matr.length - 1 ) {
                return x;
            }
        }
        
        return -1;
    }
    
    // Generazione
    public static void fillMtx(int mtx[][]) {
        for( int y = 0; y<mtx[0].length; y++)    {
            for( int x = 0; x<mtx.length; x++)   {
                mtx[x][y] = 0;   
            }   
        }
    }
    
    public static void mossaNPC(int mtx[][] )   {
        int pos;
            
        do {
            pos = (int) ((Math.random()*(mtx.length-1))+1);
        }while( disponibilitaColonna(mtx,pos) == -1);
        
        int riga = disponibilitaColonna( mtx, pos );
        mtx[riga][pos] = 2;
        
    }
    
    // Print
    public static void stampaCampo(int mtx[][]) {
        cls();
        int i, k;
        String sepLine;
        
        //Top
        sepLine = "+";
        
        System.out.print(" ");
        for(k = 0; k<mtx[0].length; k++)   {
            System.out.print(" " + k + "  "); 
            sepLine += "---+";    
        } 
        
        System.out.println();
        System.out.print(sepLine);
        System.out.println();
        
        // Body
        for( i = 0; i<mtx.length; i++ )    {
            for( k = 0; k<mtx[0].length; k++ )   {
                if(mtx[i][k] == 1)  {
                    System.out.print("| O ");    
                } else if(mtx[i][k] == 2)   {
                    System.out.print("| X ");    
                } else  {
                    System.out.print("|   ");      
                }
            } 
            
            System.out.print("|");
            System.out.println();
            System.out.print(sepLine);
            System.out.println();
        }
    }
    
    public static boolean checkWin(int mtx[][])
    {
        int count;
        int type = 0;
        
        for(int y = 0; y < mtx[0].length; y++)
        {
            for(int x = 0; x < mtx.length; x++)
            {
                if(mtx[x][y] != 0)
                {
                    if(mtx[x][y] == 1)
                    {
                        type = 1;
                    }
                    else if(mtx[x][y] == 2)
                    {
                        type = 2;
                    }
                    
                    count = 1;
                    
                    for(int i = 1; i < 4; i++) // orizzontali
                    {
                        if(x + i < mtx.length)
                        {
                            if(mtx[x+i][y] == type)
                            {
                                count++;
                            }
                            else
                            {
                                break;
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
                            else
                            {
                                break;
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
                        if(x + i < mtx.length && y - i >= 0)
                        {
                            if(mtx[x+i][y-i] == type)
                            {
                                count++;
                            }
                            else
                            {
                                break;
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
                        if(x - i >= 0 && y - i >= 0)
                        {
                            if(mtx[x-i][y-i] == type)
                            {
                                count++;
                            }
                            else
                            {
                                break;
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
    
    //aggiungere che riceve variabile "colore" inidicante se è il giocatore 1 o 2
    public static int mossaGiocatore( int m[][], int colonna, int giocatore ) {
        int riga = disponibilitaColonna( m, colonna );
        
        if( riga != -1 )  {
            m[riga][colonna] = giocatore;
        } else {
            return 0;
        }
        
        return 1;
    }
    
    public static void turnoGiocatore(int giocatore, int m[][])
    {
        int colonna;
        int controllo;
        
        do{
            System.out.print("Inserire colonna: ");
            colonna = Leggi.unInt();
            controllo = mossaGiocatore(m, colonna, giocatore);
        }while( controllo == 0 );
        
    }
    
    public static void main(String[] args) {
        int[][] m = new int [6][7];
        int colonna, turno, controllo;
        char continua;
        
        while( true )   {
            fillMtx(m);
            turno = -1;
            
            //aggiungere selezione modalità
            
            System.out.print("Inserire la modalita di gioco (true: PvP, false: PvE)." + nL);
            boolean pvp = Leggi.unBoolean();
            cls();
            
            do {
                turno++;
                System.out.println("TURNO NUMERO " + turno);
                stampaCampo(m);
                if( turno % 2 == 0 )    {
                    System.out.println("TURNO GIOCATORE 1:");
                    
                    turnoGiocatore(1,m);
                    
                    //tramite else if decidere se giocare contro altro giocatore o contro PC
                } else if(pvp == true) {
                    System.out.println("TURNO GIOCATORE 2:");
                    turnoGiocatore(2,m); 
                } else {
                    System.out.println("TURNO NPC:");
                    mossaNPC(m);
                    
                }
                wait(700);
                stampaCampo(m);
                //System.out.print("premere un tasto e inviare per continuare ");
                //continua = Leggi.unChar();
                cls();
            }while( checkWin(m) == false );
            
            turno = (turno % 2) + 1;
            
            System.out.println("GIOCATORE " + turno + ", HAI VINTO!!!");
            System.out.println("Inserire 1 per continuare, ELSE per chiudere il gioco");
            continua = Leggi.unChar();
            if( continua != '1' )
                break;
        }
    }
}
