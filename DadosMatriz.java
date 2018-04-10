import java.util.Random;
import java.util.Scanner;

public class DadosMatriz extends Thread {

	private int m1[][];
	private int m2[][];
	private int m3[][];
	private int l1;
	private int c1;
	private int l2;
	private int c2;
	private int l3;
	private int c3;

	public DadosMatriz(int l1, int c1, int l2, int c2){
		this.l1 = l1;
		this.c1 = c1;
		this.l2 = l2;
		this.c2 = c2;
		this.l3 = l1;
		this.c3 = c2;
		this.m1 = new int[l1][c1];
		this.m2 = new int[l2][c2];
		this.m3 = new int[l3][c3];
	}

	private void preencheMatriz(){
		for(int i=0; i<l1; ++i){
			for(int j=0; j<c1; ++j){
				this.m1[i][j] = new Random().nextInt(10);
			}
		}
	}

	private void imprimeMatriz(){
		for(int i=0; i<l1; ++i){
			for(int j=0; j<c1; ++j){
				System.out.print(m1[i][j] + " ");
			}
			System.out.println();
		}	
	} 

	public void run(){
		preencheMatriz();
		imprimeMatriz();
	}

	public static void main(String[] args) throws InterruptedException{
		DadosMatriz m1 = new DadosMatriz(3,3,3,3);

		m1.start();
		m1.join();



	}

}