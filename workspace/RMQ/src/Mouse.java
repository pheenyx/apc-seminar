import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Mouse {

    static int mousex = -1;
    static int mousey = -1;

    /**
     * the main function.
     * 
     * @param args
     *            commandline args
     */
    public static void main(final String[] args) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                System.in));
        try {
            while (true) {
                String line = reader.readLine();

                if (line.startsWith("END")) {
                    break;
                }

                // first line n
                String[] strnm = line.split(" ");

                int n = Integer.parseInt(strnm[0]);
                int m = Integer.parseInt(strnm[1]);

                // second line h
                int[][] a = new int[n][m];
                for (int i = 0; i < n; ++i) {
                    line = reader.readLine();
                 
                    for (int j = 0; j < line.length(); ++j) {
                        if (line.charAt(j) ==  '.')
                            a[i][j] = 0;
                        if (line.charAt(j) == 'X')
                            a[i][j] = -1;
                        if (line.charAt(j) == 'M') {
                            a[i][j] = 1;
                            mousex = i;
                            mousey = j;
                        }
                        if (line.charAt(j) == '*')
                            a[i][j] = 4711;
                    }
                }
                //print(a);
                generate(a, n, m);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Wegesuche im Labyrinth mittels Backtracking von Feldposition (i,j)
    static boolean search(int L[][], int i, int j) {
        int n = L.length; // Zahl der Zeilen
        int m = L[0].length; // Zahl der Spalten

        int c = L[i][j]; // das Zeichen an der aktuellen Feldposition

        if (c == 4711)
            return true; // Ziel erreicht
        if (c == -1 || c == 7)
            return false; // Mauer oder bereits besuchtes Feld

        L[i][j] = 7; // markiere das aktuelle Feld als besucht
        if (i + 1 < n) // unterer Rand des Labyrinths erreicht?
            if (search(L, i + 1, j)) { // suche rekursiv von dem darunter
                                       // liegenden Feld weiter
                L[i][j] = 11; // markiere das aktuelle Feld, wenn es auf dem
                              // gefundenen Weg zu Ziel liegt
                return true;
            }
        if (i - 1 >= 0) // oberer Rand des Labyrinths erreicht?
            if (search(L, i - 1, j)) { // suche rekursiv von dem darüber
                                       // liegenden Feld weiter
                L[i][j] = 11; // markiere das aktuelle Feld, wenn es auf dem
                              // gefundenen Weg zu Ziel liegt
                return true;
            }
        if (j + 1 < m) // rechter Rand des Labyrinths erreicht?
            if (search(L, i, j + 1)) { // suche rekursiv von dem rechts daneben
                                       // liegenden Feld weiter
                L[i][j] = 11;
                return true;
            }
        if (j - 1 >= 0) // linker Rand des Labyrinths erreicht?
            if (search(L, i, j - 1)) { // suche rekursiv von dem links daneben
                                       // liegenden Feld weiter
                L[i][j] = 11;
                return true;
            }
        // der Weg von der aktuellen Feldposition konnte nicht zum Zielfeld
        // fortgesetzt werden,
        // ohne vorher bereits besuchte Felder zu benutzen
        return false;
    }

    public static void generate(int[][] a, int n, int m) {
        //print(a);
        if (!search(a, mousex, mousey))
            System.out.println("Fehler: kein weg");
        a[mousex][mousey] = -11;
        //print(a);
        int tmpi = mousex;
        int tmpj = mousey;
        int counter = 0;
        while (true) {
            boolean entscheidung = false;
            int newi = -1;
            int newj = -1;
            if (tmpi + 1 < n) {
                if (a[tmpi + 1][tmpj] == 0 || a[tmpi + 1][tmpj] == 7)
                    entscheidung = true;
                if (a[tmpi + 1][tmpj] == 11) {
                    newi = tmpi + 1;
                    newj = tmpj;
                    a[tmpi + 1][tmpj] = -11;
                }
                if (a[tmpi + 1][tmpj] == 4711)
                    break;
            }
            if (tmpi - 1 >= 0) {
                if (a[tmpi - 1][tmpj] == 0 || a[tmpi - 1][tmpj] == 7)
                    entscheidung = true;
                if (a[tmpi - 1][tmpj] == 11){
                    newi = tmpi - 1;
                    newj = tmpj;
                    a[tmpi - 1][tmpj] = -11;
                }
                if (a[tmpi - 1][tmpj] == 4711)
                    break;
            }
            if (tmpj + 1 < m) {
                if (a[tmpi][tmpj + 1] == 0 || a[tmpi][tmpj + 1] == 7)
                    entscheidung = true;
                if (a[tmpi][tmpj + 1] == 11){
                    newj = tmpj + 1;
                    newi = tmpi;
                    a[tmpi][tmpj + 1] = -11;
                }
                if (a[tmpi][tmpj + 1] == 4711)
                    break;
            }
            if (tmpj - 1 >= 0) {
                if (a[tmpi][tmpj - 1] == 0 || a[tmpi][tmpj - 1] == 7)
                    entscheidung = true;
                if (a[tmpi][tmpj - 1] == 11){
                    newj = tmpj - 1;
                    newi = tmpi;
                    a[tmpi][tmpj - 1] = -11;
                }
                if (a[tmpi][tmpj - 1] == 4711)
                    break;
            }
            if (entscheidung)
                counter++;
            if (newi > -1)
                tmpi = newi;
                tmpj = newj;
            if (newj > -1)
                tmpj = newj;
                tmpi = newi;
            //System.out.println(newi +" "+newj);
            //break;
        }
        System.out.println(counter);
    }
    
    public static void print(int[][] a){
        for (int i = 0; i<a.length; ++i){
            for (int j = 0; j<a[0].length; ++j){
                System.out.print(a[i][j]+ "\t");
            }
            System.out.println();
        }
    }
}