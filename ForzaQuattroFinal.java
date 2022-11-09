class ForzaQuattroFinal {
    final static String nL = System.getProperty("line.separator"); // semplice comando che assegna alla "costante" nL la stringa per andare a capo nell'OS

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
            System.out.print('\u000C');// comando utilizzato su BlueJ per pulire lo schermo ( trovato su stackoverflow :> )
        }
    }

    public static void wait(int ms) {//altro gentile contributo di stackover flow "blocca il processo" per i millisecondi passati
        try {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)  {
            Thread.currentThread().interrupt();
        }

    }

    public static int disponibilitaColonna( int[][] matr, int colonna ){
        if( colonna >= matr[0].length || colonna < 0 )  {
            return -2;      //se la colonna selezionata è fuori dalla matrice, ritorna il valore -2 per una questione di controllo e debug
        }

        for( int riga = 0; riga < matr.length; riga++ )  {
            if( matr[riga][colonna] != 0 ){
                return riga-1;      //una volta trovata una pedina, si ritorna la riga sopra, ovvero quella vuota
            } else if( riga == matr.length - 1 ) {
                return riga;        //se si arriva alla fine della matrice, la riga disponibile sarà l'ultima
            }
        }

        return -1;
    }

    // Generazione
    public static void riempiCampo(int matr[][]) {
        for( int riga = 0; riga<matr.length; riga++)    {
            for( int colonna = 0; colonna<matr[0].length; colonna++)   {
                matr[riga][colonna] = 0;   // semplice doppio ciclo che accede a tutte le celle della matrice e le imposta a 0
            }   
        }
    }

    public static void mossaNPC(int matr[][] )   {
        int colonna;

        do {
            colonna = (int) ((Math.random()*(matr.length-1))+1);// genera un numero random tra 1 e la dimension (x) della matrice
        }while( disponibilitaColonna( matr,colonna ) == -1 );

        int riga = disponibilitaColonna( matr, colonna );
        matr[riga][colonna] = 2;// assegna il valore alla cella disponibile nella colonna selezionata
    }

    // Print
    public static void stampaCampo(int matr[][]) {
        cls();
        int riga, colonna;
        String sepLine;

        //Top
        sepLine = "+";
        System.out.print(" ");
        for( colonna = 0; colonna < matr[0].length; colonna++ )   {
            System.out.print(" " + colonna + "  "); // ciclo che crea la stringa orizzontale per il campo
            sepLine += "---+";    
        } 

        System.out.println();
        System.out.print(sepLine);
        System.out.println();

        // Body
        for( riga = 0; riga < matr.length; riga++ )    {
            for( colonna = 0; colonna < matr[0].length; colonna++ )   { // controllo a casi e semplice stampa in base al valore della cella della matrice
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

    public static boolean checkWin(int mtx[][])
    {
        int count;
        int type = 0;

        for(int y = 0; y < mtx[0].length; y++)  {
            for(int x = 0; x < mtx.length; x++) {
                if(mtx[x][y] != 0)  {
                    
                    /*
                     
                     In questa prima parte viene fatto il controllo per scegliere che giocatore controllare e 
                     nel resto del codice viene semplicemente eseguito un piccolo ciclo di controllo in 4 varie
                     direzioni che inizia dal valore uno perche sappiamo gia di essere su una casella di valore
                     specificato.
                      
                    */
                    
                    if(mtx[x][y] == 1)  {
                        type = 1; 
                    }
                    else if(mtx[x][y] == 2) {
                        type = 2;
                    }

                    count = 1;

                    //orizzontali
                    for(int i = 1; i < 4; i++)  {
                        if(x + i < mtx.length)  {
                            if(mtx[x+i][y] == type) {
                                count++;
                            } else  {
                                break;
                            }
                        }
                    }

                    if(count == 4)  {
                        return true;
                    }

                    count = 1;

                    //verticali
                    for(int i = 1; i < 4; i++)  {
                        if(y + i < mtx[0].length)   {
                            if(mtx[x][y+i] == type) {
                                count++;
                            } else  {
                                break;
                            }
                        }
                    }

                    if(count == 4)  {
                        return true;
                    }

                    count = 1;

                    //diagonale dx
                    for(int i = 1; i < 4; i++)  {
                        if(x + i < mtx.length && y - i >= 0)    {
                            if(mtx[x+i][y-i] == type)   {
                                count++;
                            }  else {
                                break;
                            }
                        }
                    }

                    if(count == 4)  {
                        return true;
                    }

                    count = 1;

                    //diagonale sx
                    for(int i = 1; i < 4; i++)  {
                        if(x - i >= 0 && y - i >= 0)    {
                            if(mtx[x-i][y-i] == type)   {
                                count++;
                            } else  {
                                break;
                            }
                        }
                    }

                    if(count == 4)  {
                        return true;
                    }
                }
            }  
        }

        return false;
    }

    //aggiungere che riceve variabile "colore" inidicante se è il giocatore 1 o 2
    public static int mossaGiocatore( int[][] matr, int colonna, int giocatore ) {
        int riga = disponibilitaColonna( matr, colonna );

        if( riga != -1 && riga != -2)  {
            matr[riga][colonna] = giocatore;
        } else {
            return 0;   //se la colonna è piena, dovrai reinserire la colonna in cui giocare (gestito in turnoGiocatore())
        }

        return 1;
    }

    public static void turnoGiocatore(int giocatore, int[][] matr )
    {
        int colonna;
        int controllo;

        do{
            System.out.print("Inserire colonna: ");
            colonna = Leggi.unInt();
            controllo = mossaGiocatore( matr, colonna, giocatore );
        }while( controllo == 0 );
    }

    public static void main(String[] args) {
        int[][] matr = new int [6][7];
        int colonna, turno, controllo;
        char continua;

        while( true )   {
            riempiCampo(matr);
            turno = -1;     //turno parte da -1 perchè, per motivi pratici, deve essere incrementato all'inizio

            System.out.print("Inserire la modalita di gioco (true: PvP, false: PvE)." + nL);
            boolean pvp = Leggi.unBoolean();
            cls();

            do {
                turno++;
                System.out.println("TURNO NUMERO " + turno);
                stampaCampo(matr);
                if( turno % 2 == 0 )    {
                    System.out.println("TURNO GIOCATORE 1:");
                    turnoGiocatore(1,matr);
                } else if(pvp == true) {
                    System.out.println("TURNO GIOCATORE 2:");
                    turnoGiocatore(2,matr); 
                } else {
                    System.out.println("TURNO NPC:");
                    mossaNPC(matr);
                }
                //wait(700);      //pausa di riflessione cosmica
                stampaCampo(matr);
                System.out.print("premere un tasto e inviare per continuare ");
                //continua = Leggi.unChar();
                cls();
                
            }while( checkWin(matr) == false && (turno+1) < (matr.length * matr[0].length) );
            
            if((turno+1) >= (matr.length * matr[0].length) ) // controllo se il turno e >= al numero di caselle disponibili che se vero si traduce in un pareggio
            {
                System.out.println("PAREGGIO L");
                System.out.println("Inserire 1 per continuare, ELSE per chiudere il gioco");
                continua = Leggi.unChar();
                if( continua != '1' )
                    break; 
            }
            else
            {
                turno = (turno % 2) + 1;
    
                System.out.println("GIOCATORE " + turno + ", HAI VINTO!!!");
                System.out.println("Inserire 1 per continuare, ELSE per chiudere il gioco");
                continua = Leggi.unChar();
                if( continua != '1' )
                    break;
            }
        }
    }
}