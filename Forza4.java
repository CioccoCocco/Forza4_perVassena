class Forza4 {
    final String nL = System.getProperty("line.separator");
    
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
    
    public static int mossaNPC(int mtx[][], int turno )   {
        int pos;
            
        do {
            pos = (int) ((Math.random()*6)+1);
        }while( disponibilitaColonna(mtx,pos) == -1 );
        
        int riga = disponibilitaColonna( mtx, pos );
        mtx[riga][pos] = 2;
        
        if( controlloVincita( mtx, riga, pos, 2) == true )  {
            return 2;
        }
        
        return 1;
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
    
    public static int orizzontali( int[][]m, int i, int k, int colore, int segno, int cnt )  {
        if( i == m.length-1 || i < 0 ) {
            //sto uscendo dalla matrice
            return cnt;
        } else if( m[i][k] != colore || cnt == 4 ) {
            //ho trovato tutte le pedine di cui ho bisogno
            return cnt;
        } else {
            return orizzontali( m, i+segno, k, colore, segno, ++cnt );
        }
    }
    
    public static int verticali( int[][]m, int i, int k, int colore, int segno, int cnt )  {
        if( k == m[0].length-1 || k < 0 ) {
            return cnt;
        } else if( m[i][k] != colore || cnt == 4 ) {
            return cnt;
        } else {
            return verticali( m, i, k+segno, colore, segno, ++cnt );
        }
    }
    
    public static int diagonali( int[][]m, int i, int k, int colore, int segno1, int segno2, int cnt )  {
        if( i == m.length-1 || k == m[0].length-1 || i < 0 || k < 0 ) {
            return cnt;
        } else if( m[i][k] != colore || cnt == 4 ) {
            return cnt;
        } else {
            return diagonali( m, i+segno1, k+segno2, colore, segno1, segno2, ++cnt );
        }
    }
    
    public static boolean controlloVincita( int[][] m, int i, int k, int colore ) {
        if( orizzontali( m, i, k, colore, 1, 1 ) + orizzontali( m, i, k, colore, -1, 0 ) > 4 )  {
            return true;
        }
        
        if( verticali( m, i, k, colore, 1, 1 ) + verticali( m, i, k, colore, -1, 0 ) > 4 )  {
            return true;
        }
        
        if( diagonali( m, i, k, colore, 1, 1, 1 ) + diagonali( m, i, k, colore, -1, -1, 0 ) > 4 )  {
            return true;
        }
        
        if( diagonali( m, i, k, colore, 1, -1, 1 ) + diagonali( m, i, k, colore, -1, 1, 0 ) > 4 )  {
            return true;
        }
        
        return false;        
    }
    
    public static int mossaGiocatore( int m[][], int colonna ) {
        int riga = disponibilitaColonna( m, colonna );
        
        if( riga != -1 )  {
            m[riga][colonna] = 1;
        } else {
            return 0;
        }
        
        if( controlloVincita( m, riga, colonna, 1 ) == true )   {
            return 2;
        }
        
        return 1;
    }
    
    public static void main(String[] args) {
        int[][] m = new int [6][7];
        int colonna, turno, controllo;
        char continua;
        
        while( true )   {
            fillMtx(m);
            turno = -1;
            
            do {
                turno++;
                System.out.println("TURNO NUMERO " + turno);
                stampaCampo(m);
                if( turno % 2 == 0 )    {
                    System.out.println("TURNO GIOCATORE:");
                    do  {
                        System.out.print("Inserire colonna: ");
                        colonna = Leggi.unInt();
                        controllo = mossaGiocatore(m, colonna);
                    }while( controllo == 0 );
                } else {
                    System.out.println("TURNO NPC:");
                    controllo = mossaNPC( m, turno );
                }
                wait(1500);
                stampaCampo(m);
                System.out.print("premere un tasto e inviare per continuare ");
                continua = Leggi.unChar();
                cls();
            }while( controllo != 2 );
            
            turno = (turno % 2) + 1;
            
            System.out.println("GIOCATORE " + turno + ", HAI VINTO!!!");
            System.out.println("Inserire 1 per continuare, ELSE per chiudere il gioco");
            continua = Leggi.unChar();
            if( continua != '1' )
                break;
        }
    }
}