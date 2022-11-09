import java.io.*;

class Leggi {
  private static BufferedReader br = 
    new BufferedReader(new InputStreamReader(System.in));
  private static String s;

  public static boolean unBoolean() {
    input();
    if (s != null && !s.equals("true") 
                  && !s.equals("false"))
      System.err.println("Errore: "+s+" non e' un boolean");
    return s!=null && s.equals("true");
  }
  public static byte unByte() {
    try {
      return Byte.parseByte(input());
    } catch (NumberFormatException e) {
      if (s!=null) System.err.println("Errore: "+s+
                                      " non e' un byte");
      return 0;
    }
  }
  public static short unoShort() {
    try {
      return Short.parseShort(input());
    } catch (NumberFormatException e) {
      if (s!=null) System.err.println("Errore: "+s+
                                      " non e' uno short");
      return 0;
    }
  }
  public static int unInt() {
      return Integer.parseInt(input());
  }
  public static long unLong() {
    try {
      return Long.parseLong(input());
    } catch (NumberFormatException e) {
      if (s!=null) System.err.println("Errore: "+s+
                                      " non e' un long");
      return 0;
    }
  }
  public static float unFloat() {
    try {
      return Float.parseFloat(input());
    } catch (NumberFormatException e) {
      if (s!=null) System.err.println("Errore: "+s+
                                      " non e' un float");
    } catch (NullPointerException e) {}
    return 0;
  }
  public static double unDouble() {
    try {
      return Double.parseDouble(input());
    } catch (NumberFormatException e) {
      if (s!=null) System.err.println("Errore: "+s+
                                      " non e' un double");
    } catch (NullPointerException e) {}
    return 0;
  }
  public static char unChar() {
    try {
      return input().charAt(0);
    } catch (IndexOutOfBoundsException e) {
      // non dovrebbe mai accadere 
      // se non quando c'e' un IOException
    } catch (NullPointerException e) {}
    return ' ';
  }
  public static String unoString() {
    try {
      return br.readLine();
    } catch (IOException e) {
      System.err.println("Errore durante la "+
                         "lettura dell'input!");
      return null;
    }
  }
  private static String input() {
    try {
      s = br.readLine().trim()+" ";
      s = s.substring(0,s.indexOf(" "));
      if (s.length() < 1) {
        System.err.println("Errore: inserire almeno un "+
                           "carattere o uno spazio");
        s = null;
      }
    } catch (IOException e) {
      System.err.println("Errore durante la "+
                         "lettura dell'input!");
      return null;
    }
    return s;
  }
}