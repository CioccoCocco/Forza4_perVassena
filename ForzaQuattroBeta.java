class ForzaQuattroBeta {
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
    
    public static int disponibilitaColonna( int[][] matr, int colonna ){
        if( colonna >= matr[0].length || colonna < 0 )  {
            return -2;
        }
        
        for( int riga = 0; riga < matr.length; riga++ )  {
            if( matr[riga][colonna] == 1 || matr[riga][colonna] == 2 ){
                return riga-1;        
            } else if( riga == matr.length - 1 ) {
                return riga;
            }
        }
        
        return -1;
    }
    
    // Generazione
    public static void riempiCampo(int matr[][]) {
        for( int riga = 0; riga<matr.length; riga++)    {
            for( int colonna = 0; colonna<matr[0].length; colonna++)   {
                matr[riga][colonna] = 0;   
            }   
        }
    }
    
    public static int mossaNPC(int matr[][] )   {
        int colonna;
            
        do {
            colonna = (int) ((Math.random()*(matr.length-1))+1);
        }while( disponibilitaColonna( matr,colonna ) == -1 );
        
        int riga = disponibilitaColonna( matr, colonna );
        matr[riga][colonna] = 2;
        
        if( controlloVincita( matr, riga, colonna, 2 ) == true )   {
            return 2;
        }
                    
        return 1;
    }
    
    // Print
    public static void stampaCampo(int matr[][]) {
        //cls();
        int riga, colonna;
        String sepLine;
        
        //Top
        sepLine = "+";
        System.out.print(" ");
        for( colonna = 0; colonna < matr[0].length; colonna++ )   {
            System.out.print(" " + colonna + "  "); 
            sepLine += "---+";    
        } 
        
        System.out.println();
        System.out.print(sepLine);
        System.out.println();
        
        // Body
        for( riga = 0; riga < matr.length; riga++ )    {
            for( colonna = 0; colonna < matr[0].length; colonna++ )   {
                if(matr[riga][colonna] == 1)  {
                    System.out.print("| O ");    
                } else if(matr[riga][colonna] == 2)   {
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
    
    public static int orizzontali( int[][] matr, int riga, int colonna, int colore, int segno, int cnt )  {
        if( colonna == matr[0].length-1 || colonna < 0 ) {
            //sto uscendo dalla matrice
            return cnt;
        } else if( matr[riga][colonna] != colore || cnt == 4 ) {
            //ho trovato tutte le pedine di cui ho bisogno
            return cnt;
        } else {
            //System.out.print("TROVATA ORIZZONTALE ");
            return orizzontali( matr, riga, colonna+segno, colore, segno, ++cnt );
        }
    }
    
    public static int verticali( int[][] matr, int riga, int colonna, int colore, int segno, int cnt )  {
        if( riga == matr.length-1 || riga < 0 ) {
            return cnt;
        } else if( matr[riga][colonna] != colore || cnt == 4 ) {
            return cnt;
        } else {
            //System.out.print("TROVATA VERTICALE ");
            return verticali( matr, riga+segno, colonna, colore, segno, ++cnt );
        }
    }
    
    public static int diagonali( int[][] matr, int riga, int colonna, int colore, int segno1, int segno2, int cnt )  {
        if( riga == matr.length-1 || colonna == matr[0].length-1 || riga < 0 || colonna < 0 ) {
            return cnt;
        } else if( matr[riga][colonna] != colore || cnt == 4 ) {
            return cnt;
        } else {
            //System.out.print("TROVATA DIAGONALE ");
            return diagonali( matr, riga+segno1, colonna+segno2, colore, segno1, segno2, ++cnt );
        }
    }
    
    public static boolean controlloVincita( int[][] matr, int riga, int colonna, int colore ) {
        if( orizzontali( matr, riga, colonna, colore, 1, 1 ) + orizzontali( matr, riga, colonna, colore, -1, 0 ) > 4 )  {
            return true;
        }
        
        if( verticali( matr, riga, colonna, colore, 1, 1 ) + verticali( matr, riga, colonna, colore, -1, 0 ) > 4 )  {
            return true;
        }
        
        if( diagonali( matr, riga, colonna, colore, 1, 1, 1 ) + diagonali( matr, riga, colonna, colore, -1, -1, 0 ) > 4 )  {
            return true;
        }
        
        if( diagonali( matr, riga, colonna, colore, 1, -1, 1 ) + diagonali( matr, riga, colonna, colore, -1, 1, 0 ) > 4 )  {
            return true;
        }
        
        return false;        
    }
    
    //aggiungere che riceve variabile "colore" inidicante se è il giocatore 1 o 2
    public static int mossaGiocatore( int[][] matr, int colonna, int giocatore ) {
        int riga = disponibilitaColonna( matr, colonna );
        
        if( riga != -1 )  {
            matr[riga][colonna] = giocatore;
        } else {
            return 0;
        }
        
        //passare a controlloVincita la variabile colore anzichè 1
        if( controlloVincita( matr, riga, colonna, giocatore ) == true )   {
            return 2;
        }
        
        return 1;
    }
    
    public static int turnoGiocatore(int giocatore, int[][] matr )
    {
        int colonna;
        int controllo;
        
        do{
            System.out.print("Inserire colonna: ");
            colonna = Leggi.unInt();
            controllo = mossaGiocatore( matr, colonna, giocatore );
        }while( controllo == 0 );
        
        return controllo;
    }
    
    public static void main(String[] args) {
        int[][] matr = new int [6][7];
        int colonna, turno, controllo;
        char continua;
        
        while( true )   {
            riempiCampo(matr);
            turno = -1;
            
            //aggiungere selezione modalità
            
            System.out.print("Inserire la modalita di gioco (true: PvP, false: PvE)." + nL);
            boolean pvp = Leggi.unBoolean();
            cls();
            
            do {
                turno++;
                System.out.println("TURNO NUMERO " + turno);
                stampaCampo(matr);
                if( turno % 2 == 0 )    {
                    System.out.println("TURNO GIOCATORE 1:");
                    controllo = turnoGiocatore(1,matr);
                } else if(pvp == true) {
                    System.out.println("TURNO GIOCATORE 2:");
                    controllo = turnoGiocatore(2,matr); 
                } else {
                    System.out.println("TURNO NPC:");
                    controllo = mossaNPC(matr);
                }
                wait(700);
                stampaCampo(matr);
                System.out.print("premere un tasto e inviare per continuare ");
                continua = Leggi.unChar();
                cls();
            }while( controllo != 2);
            
            turno = (turno % 2) + 1;
            
            System.out.println("GIOCATORE " + turno + ", HAI VINTO!!!");
            System.out.println("Inserire 1 per continuare, ELSE per chiudere il gioco");
            continua = Leggi.unChar();
            if( continua != '1' )
                break;
        }
    }
}