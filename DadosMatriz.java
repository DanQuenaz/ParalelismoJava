import java.util.Random;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class DadosMatriz extends Thread {

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

	private Map<Integer, Vector<Integer>> mapI; //HashMap contendo linhas e colunas que a trhead vai operar

	public DadosMatriz(){
		this.mapI = new HashMap<Integer, Vector<Integer>>();
	}

	public void addIntervalo(int linha, int coluna){ //Adiciona par linha-coluna para o conjunto da thread 
		try{ //Tenta adicionar coluna para determinada linha
			mapI.get(linha).add(coluna); 
		}catch(Exception e){ //Se a linha ainda não existe adiciona linha e coluna
			mapI.put(linha, new Vector<Integer>());
			mapI.get(linha).add(coluna);
		}
	}

	public void getIntervalos(){ //Imprime os intervalos calculados
		for (Integer key : this.mapI.keySet()) {
			Vector<Integer> value = this.mapI.get(key);
			System.out.print(key+": ");
			for(Integer index : value){
				System.out.print(index + " ");
			}
			System.out.println();
	 	}
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

	public static void defineIntervalos(DadosMatriz[] threads, int nThreads){ //Distribui linhas e colunas para cada thread
		for(int i=0; i<nThreads; ++i){ //Inicializa estrutura de cada thread
			threads[i] = new DadosMatriz();
		}

		int totalOp = l1 * c2;
		int linha=0;
		int coluna=0;
		int thrd = 0;
		for(int i=0; i<totalOp; ++i){////Distribui linhas e colunas para cada thread
			threads[thrd].addIntervalo(linha, coluna);
			thrd++; if(thrd>=nThreads)thrd=0;
			coluna++;
			if(coluna >= c2){
				coluna = 0;
				linha++;
			}
		}
	}

	public void run(){//Realiza o somatario de produto entre linhas e colunas determinadas para a thread
		for(Integer linha : this.mapI.keySet()){
			Vector<Integer> cols = this.mapI.get(linha);
			for(Integer coluna : cols){
				for(int k=0; k<c1; ++k){
					m3[linha][coluna] += m1[linha][k]*m2[k][coluna];
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException{
		Scanner teclado = new Scanner(System.in);
		int nThreads;
		int l1, l2, c1, c2;
		
		System.out.println("Entre com a quantidade de linhas da m1: ");
		l1 = teclado.nextInt();
		System.out.println("Entre com a quantidade de colunas da m1: ");
		c1 = teclado.nextInt();
		System.out.println("Entre com a quantidade de linhas da m2: ");
		l2 = teclado.nextInt();
		System.out.println("Entre com a quantidade de colunas da m2: ");
		c2 = teclado.nextInt();

		System.out.println("Entre com a quantidade de threads: ");
		nThreads = teclado.nextInt();
		teclado.close();

		DadosMatriz[] threads = new DadosMatriz[nThreads]; //Vetor de threads

		DadosMatriz.iniciaMatrizes(l1, c1, l2, c2); //Inicialização das matrizes
		DadosMatriz.defineIntervalos(threads, nThreads); //Define os intervalos de cada thread

		//Imprime os intervalos gerados
		for(int i=0; i<nThreads; ++i){
			System.out.println("Thread "+ i +": ");
			threads[i].getIntervalos();
			System.out.println();
		}

		for(DadosMatriz index:threads){
			index.start();
		}

		for(DadosMatriz index:threads){
			index.join();
		}

		DadosMatriz.imprimeMatriz();
	}

}