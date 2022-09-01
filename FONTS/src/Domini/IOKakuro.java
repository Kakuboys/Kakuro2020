package Domini; 

import CapaOrtogonal.Casella;
import CapaOrtogonal.CasellaBlanca;
import CapaOrtogonal.CasellaNegra;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class IOKakuro {
    private int filas;
    private int columnas;
    private Casella[][] taulell;
    public IOKakuro(){}

    //Pre: ni taulell ni els seus elements son nulls
    //Post: escriu per consola el taulell taulell, en el format indicat a l'enunciat
    //Post: escriu un taulell per consola
    public void printSolucio(Casella[][] taulell) {
        this.filas=taulell.length;
        this.columnas=taulell[0].length;
        for(int i = 0; i < filas ; ++i ) {
            for(int j = 0; j < columnas ; ++j ) {
                if(taulell[i][j] instanceof CasellaBlanca) {
                    if (((CasellaBlanca) taulell[i][j]).getNum()==0) System.out.print("?");
                    else System.out.print(((CasellaBlanca) taulell[i][j]).getNum());
                }
                else {
                    int valorFila= ((CasellaNegra)taulell[i][j]).getSumaFila();
                    int valorCol= ((CasellaNegra)taulell[i][j]).getSumaColumna();
                    if(valorCol==0 && valorFila==0)  System.out.print("*");
                    else if (valorCol==0) System.out.print("F" + valorFila );
                    else if (valorFila==0) System.out.print("C" + valorCol );
                    else System.out.print("C" + valorCol +  "F" + valorFila  );

                }
                if(j== columnas - 1) System.out.print("\n");
                else System.out.print(",");
            }
        }

    }

    //Pre: L'argument fitxer ha de ser un nom que es trobi a la carpeta resources
    //Post: fica a taulell un taulell amb les columnes, files i amb atribut taulell del kakuro passat per fitxer
    //Descripció: Llegeix el fitxer, i crea un taulell amb els atributs obtinguts a partir de la lectura del fitxer
    public void llegirKakuro(String fitxer) {

        String path = "./resources/" + fitxer;
        File f = new File(path);

        Scanner sc = null;
        try {
            sc = new Scanner(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String fc= sc.nextLine();
        String[] primers = fc.split(",");
        filas = Integer.parseInt(primers[0]);
        columnas = Integer.parseInt(primers[1]);
        taulell = new Casella[filas][columnas];	//una mica d'ambiguitat pero que no hauria de molestar
        for(int i = 0; i < filas ; ++i ) {
            String[] filaSencera=sc.nextLine().split(",",columnas);
            for(int j = 0; j < columnas;  ++j) {
                String  c = filaSencera[j];
                if (c.equals("?")) {  //blanca

                    taulell[i][j] = new CasellaBlanca();
                    ((CasellaBlanca)taulell[i][j]).setNum(0); //casellablanca == 0

                }
                else if(c.equals("*")){  //negra sense numeros
                    taulell[i][j]= new CasellaNegra();
                    ((CasellaNegra)taulell[i][j]).setSumaFila(0);
                    ((CasellaNegra)taulell[i][j]).setSumaColumna(0);

                }
                else { // casella rara (aka negra amb numero/s)
                    taulell[i][j] = new CasellaNegra();
                    int it = 0;
                    if (c.charAt(it) == 'C') {
                        String numero = "";
                        it++;
                        char temp = c.charAt(it);
                        while(temp != 'F' && it <c.length()) {	//estem agafant el numero
                            numero += Character.toString(temp);

                            it++;
                            if (it < c.length())
                                temp = c.charAt(it);
                        }
                        int result = 0;
                        int numero_length = numero.length();
                        for (int l = 0; l < numero_length; l++)
                            result = result * 10 + numero.charAt(l) - '0';

                        ((CasellaNegra)taulell[i][j]).setSumaColumna(result);

                    }
                    else ((CasellaNegra)taulell[i][j]).setSumaColumna(0);
                    if (it<c.length()) { //entra si és FXX o CXXFXX
                        String numero = "";
                        it++;
                        char temp = c.charAt(it);
                        while(it < c.length()) {	//estem agafant el numero
                            numero += Character.toString(temp);

                            it++;
                            if (it < c.length())
                                temp = c.charAt(it);
                        }
                        int result = 0;
                        int numero_length = numero.length();
                        for (int l = 0; l < numero_length; l++)
                            result = result * 10 + numero.charAt(l) - '0';

                        ((CasellaNegra)taulell[i][j]).setSumaFila(result);
                    }
                    else ((CasellaNegra)taulell[i][j]).setSumaFila(0);
                }

            }
        }
        sc.close();
    }

    //Pre: ni taulell ni els seus elements son nulls. i la ruta./resources/ existeix
    //Post: escriu en el fitxer filename.txt el taulell taulell, en el format indicat a l'enunciat
    //Post: escriu un taulell en el fitxer filename.txt
    public void printSolucio2(Casella[][] taulell) {
        try {
            this.filas = taulell.length;
            this.columnas = taulell[0].length;
            String f = this.filas + "";
            String c = this.columnas + "";

            String ruta = "./resources/filename.txt";
            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(f + "," + c + "\n");

            for(int i = 0; i < filas ; ++i ) {
                for(int j = 0; j < columnas ; ++j ) {
                    if(taulell[i][j] instanceof CasellaBlanca) {
                        if (((CasellaBlanca) taulell[i][j]).getNum()==0) bw.write("?");
                        else  {
                            String h = ((CasellaBlanca) taulell[i][j]).getNum() + "";
                            bw.write(h);
                        }
                    }
                    else {
                        int valorFila = ((CasellaNegra)taulell[i][j]).getSumaFila();
                        int valorCol = ((CasellaNegra)taulell[i][j]).getSumaColumna();
                        String vF = valorFila + "";
                        String vC = valorCol + "";
                        if(valorCol==0 && valorFila==0)  bw.write("*");
                        else if (valorCol == 0) bw.write("F" + vF);
                        else if (valorFila == 0) bw.write("C" + vC);
                        else bw.write("C" + vC + "F" + vF);

                    }
                    if(j== columnas - 1) bw.write("\n");
                    else  bw.write(",");
                }
            }
            bw.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Pre: -
    //Post: retorna filas
    //Descripcio: getter de filas
    public int getFilas() {
        return filas;
    }

    //Pre: -
    //Post: retorna columnas
    //Descripcio: getter de columnas
    public int getColumnas() {
        return columnas;
    }

    //Pre: -
    //Post: retorna taulell
    //Descripcio: getter de taulell
    public Casella[][] getTaulell() {
        return taulell;
    }
}

