import java.util.Random;
import java.util.Scanner;

public class SequencialMatriz {

	private static int m1[][]; //Matriz 1
	private static int m2[][]; //Matriz 2
	private static int m3[][]; //Matriz 3
    //m3 = m1 * m2
	private static int l1; //Total linhas m1
	private static int c1; //Total colunas m1
	private static int l2; //Total linhas m2
	private static int c2; //Total colunas m2
	private static int l3; //Total linhas m3
	private static int c3; //Total colunas m3


	public SequencialMatriz(){
		
	}

	public static void iniciaMatrizes(int _l1, int _c1, int _l2, int _c2){ //Inicia as matrizes de forma randomica
		l1 = _l1;
		c1 = _c1;
		l2 = _l2;
		c2 = _c2;
		l3 = _l1;
		c3 = _c2;
		m1 = new int[l1][c1];
		m2 = new int[l2][c2];
		m3 = new int[l3][c3];

		for(int i=0; i<l1; ++i){
			for(int j=0; j<c1; ++j){
				m1[i][j] = new Random().nextInt(10);
			}
		}

		for(int i=0; i<l2; ++i){
			for(int j=0; j<c2; ++j){
				m2[i][j] = new Random().nextInt(10);
			}
		}

		for(int i=0; i<l3; ++i){
			for(int j=0; j<c3; ++j){
				m3[i][j] = 0;
			}
		}
	}

	public static void imprimeMatriz(){ //Imprime as matrizes geradas
		System.out.println("M1: ");
		for(int i=0; i<l1; ++i){
			for(int j=0; j<c1; ++j){
				System.out.print(m1[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("M2: ");
		for(int i=0; i<l2; ++i){
			for(int j=0; j<c2; ++j){
				System.out.print(m2[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("M3: ");
		for(int i=0; i<l3; ++i){
			for(int j=0; j<c3; ++j){
				System.out.print(m3[i][j] + " ");
			}
			System.out.println();
		}	
	} 

	
	public static void multiplica(){//Realiza o somatario de produto entre linhas e colunas
		for(int i=0; i<l1; ++i){
            for(int j=0; j<c2; ++j){
                for(int k=0; k<c1; ++k){
                    m3[i][j] += m1[i][k]*m2[k][j];
                }
            }
        }
	}

	public static void main(String[] args) throws InterruptedException{
		Scanner teclado = new Scanner(System.in);
		int l1, l2, c1, c2;
		long tempo1, tempo2;
		
		System.out.println("Entre com a quantidade de linhas da m1: ");
		l1 = teclado.nextInt();
		System.out.println("Entre com a quantidade de colunas da m1: ");
		c1 = teclado.nextInt();
		System.out.println("Entre com a quantidade de linhas da m2: ");
		l2 = teclado.nextInt();
		System.out.println("Entre com a quantidade de colunas da m2: ");
		c2 = teclado.nextInt();
		teclado.close();


		SequencialMatriz.iniciaMatrizes(l1, c1, l2, c2); //Inicialização das matrizes

		tempo1 = System.nanoTime();

        SequencialMatriz.multiplica();

		tempo2 = System.nanoTime();
        System.out.println();
        System.out.print(l1+"x"+c1 + " * " + l2+ "x"+c2+": ");
        //System.out.print("\tResultado = " + resultado);
		System.out.println("\tTempo = " + String.valueOf((tempo2 - tempo1)/1000000) + " ms");
		System.out.println();

		//Imprime resultados
		//SequencialMatriz.imprimeMatriz();
	}

}