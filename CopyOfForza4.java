class CopyOfForza4 {
    public static int orizzontali( int[][]m, int i, int k, int colore, int segno, int cnt )  {
        if( i == m.length || i < 0 ) {
            return cnt;
        } else if( m[i][k] != colore || cnt == 4 ) {
            return cnt;
        } else {
            return orizzontali( m, i+segno, k, colore, segno, ++cnt );
        }
    }
    
    public static int verticali( int[][]m, int i, int k, int colore, int segno, int cnt )  {
        if( k == m[0].length || k < 0 ) {
            return cnt;
        } else if( m[i][k] != colore || cnt == 4 ) {
            return cnt;
        } else {
            return verticali( m, i, k+segno, colore, segno, ++cnt );
        }
    }
    
    public static int diagonali( int[][]m, int i, int k, int colore, int segno1, int segno2, int cnt )  {
        if( i == m.length || k == m[0].length || i < 0 || k < 0 ) {
            return cnt;
        } else if( m[i][k] != colore || cnt == 4 ) {
            return cnt;
        } else {
            return diagonali( m, i+segno1, k+segno2, colore, segno1, segno2, ++cnt );
        }
    }
    
    public static boolean circostanti( int[][] m, int i, int k, int colore ) {
        int cnt = 0;
        
        cnt = orizzontali( m, i, k, colore, 1, cnt );
        if( cnt == 4 )  {
            return true;
        }
        
        cnt = 0;
        cnt = orizzontali( m, i, k, colore, -1, cnt );
        if( cnt == 4 )  {
            return true;
        }
        
        cnt = 0;
        cnt = verticali( m, i, k, colore, 1, cnt );
        if( cnt == 4 )  {
            return true;
        }
        
        cnt = 0;
        cnt = verticali( m, i, k, colore, -1, cnt );
        if( cnt == 4 )  {
            return true;
        }
        
        cnt = 0;
        cnt = diagonali( m, i, k, colore, 1, 1, cnt );
        if( cnt == 4 )  {
            return true;
        }
        
        cnt = 0;
        cnt = diagonali( m, i, k, colore, 1, -1, cnt );
        if( cnt == 4 )  {
            return true;
        }
        
        cnt = 0;
        cnt = diagonali( m, i, k, colore, -1, -1, cnt );
        if( cnt == 4 )  {
            return true;
        }
        
        cnt = 0;
        cnt = diagonali( m, i, k, colore, -1, 1, cnt );
        if( cnt == 4 )  {
            return true;
        }
        
        return false;        
    }
    
    public static boolean controlloVincita( int[][] m, int colore )    {
        for( int i = 0; i < m.length; i++ ) {
            for( int k = 0; k < m[0].length; k++ )  {
                if( m[i][k] == colore ) {
                    if( circostanti( m, i, k, colore ) == true )   {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}