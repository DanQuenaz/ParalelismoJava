import java.util.Random;
import java.util.Scanner;

public class DadosMatriz extends Thread {

	private static int m1[][];
	private static int m2[][];
	private static int m3[][];
	private static int l1;
	private static int c1;
	private static int l2;
	private static int c2;
	private static int l3;
	private static int c3;

	private int l_ini;
	private int l_fim;
	private int c_ini;
	private int c_fim;

	public DadosMatriz(int li, int lf, int ci, int cf){
		this.l_ini = li;
		this.l_fim = lf;
		this.c_ini = ci;
		this.c_fim = cf;
	}

	public String getIntervalos(){
		return  this.l_ini+":"+this.l_fim+" - "+this.c_ini+":"+this.c_fim;
	}

	public static void iniciaMatrizes(int _l1, int _c1, int _l2, int _c2){
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

	public static void imprimeMatriz(){
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

	public static void defineIntervalos(DadosMatriz[] threads, int nThreads){
		if(nThreads <= l1){
			int passo = l1/nThreads;
			int li = 0; 
			int lf = passo-1;

			for(int i=0; i<nThreads-1; ++i){
				threads[i] = new DadosMatriz(li, lf, 0, c2-1);
				li+=passo; 
				lf+=passo;
			}

			threads[nThreads-1] = new DadosMatriz(li, lf+l1%nThreads, 0, c2-1);
		}else{
			int passo = (c2*l1)/nThreads;
			int linha = 0;
			int ci = 0;
			int cf = passo-1;

			for(int i=0; i<nThreads-1; ++i){
				threads[i] = new DadosMatriz(linha, linha, ci, cf);
				linha++;
				if(linha >= l1){
					linha=0;
					ci+=passo;
					cf+=passo;
					if(cf>=c2){
						ci=0; cf=passo-1;
					}
				}
			}

			threads[nThreads-1] = new DadosMatriz(linha, linha, ci, cf+(c2*l1)%nThreads);
		}
	}

	public void run(){
		for(int i=l_ini; i<=l_fim; ++i){
			for(int j=c_ini; j<=c_fim; ++j){
				for(int k=0; k<c1; ++k){
					m3[i][j] += m1[i][k]*m2[k][j];
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

		DadosMatriz[] threads = new DadosMatriz[nThreads];

		DadosMatriz.iniciaMatrizes(l1, c1, l2, c2);
		DadosMatriz.defineIntervalos(threads, nThreads);

		for(int i=0; i<nThreads; ++i){
			System.out.println("Trhead "+i+": " + threads[i].getIntervalos());
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