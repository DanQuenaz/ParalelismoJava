import java.math.BigInteger;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

/*
 * Algoritmo Paralelo em Dados
 * Calculo do somatorio (1! + 2! + 3! + ... + n!) + (2^1 + 2^2 + ... + 2^n)
 */
public class ControleTaylor extends Thread {			
	private BigInteger n; //numero de elementos para processamento
	private BigDecimal resultado; //resultado do processamento
	private int tarefa; //tarefa realizada pela thread
	private int x;
	/*
	 * Construtor padrao 
	 */
	public ControleTaylor(BigInteger n, int tarefa, int x){
		this.n = n;
		this.tarefa = tarefa;
		resultado = BigDecimal.ZERO;
		this.x = x;
	}
		
	/*
	 * Algoritmo executado pela thread
	 * @see java.lang.Thread#run()
	 */
	public void run() {				
		resultado = BigDecimal.ZERO;
		
		
		if (tarefa == 0) {//cos(x)
			BigDecimal pot  = BigDecimal.ZERO;
			BigInteger n2  = BigInteger.ONE;
			
			for(BigInteger i = BigInteger.ZERO; i.compareTo(n) != 0; i = i.add(BigInteger.ONE)){
				pot = new BigDecimal( Util.Potencia(new BigInteger("-1"), i) );
				n2 = i.multiply( BigInteger.valueOf(2) );
				pot = pot.divide( new BigDecimal( Util.Fatorial( n2 ) ), 1000, RoundingMode.HALF_UP );
				pot = pot.multiply( new BigDecimal( Util.Potencia( BigInteger.valueOf(this.x), n2 ) ) );

				resultado = resultado.add( pot  );
			}
		
		} else if(tarefa == 1){ //sin(x)
			BigDecimal pot  = BigDecimal.ZERO;
			BigInteger n2  = BigInteger.ONE;
			
			for(BigInteger i = BigInteger.ZERO; i.compareTo(n) != 0; i = i.add(BigInteger.ONE)){
				pot = new BigDecimal( Util.Potencia(new BigInteger("-1"), i) );
				n2 = i.multiply( BigInteger.valueOf(2) );
				pot = pot.divide( new BigDecimal( Util.Fatorial( n2.add(BigInteger.ONE) ) ), 1000, RoundingMode.HALF_UP );
				pot = pot.multiply( new BigDecimal( Util.Potencia( BigInteger.valueOf(this.x), n2.add(BigInteger.ONE) ) ) );

				resultado = resultado.add( pot  );
			}	
		
		}else{//e^x
			BigDecimal pot  = BigDecimal.ZERO;
			BigInteger fat  = BigInteger.ONE;
			
			for(BigInteger i = BigInteger.ZERO; i.compareTo(n) != 0; i = i.add(BigInteger.ONE)){
				pot = new BigDecimal( Util.Potencia(BigInteger.valueOf(this.x), i) );
				fat = Util.Fatorial(i);
				pot = pot.divide( new BigDecimal(fat), 1000, RoundingMode.HALF_UP );

				resultado = resultado.add( pot  );
			}	
		}
		
	}
	
	/*
	 * Retorna o resultado do processamento
	 */
	public BigDecimal Resultado() {
		return resultado;
	}
	
	public static void main(String[] args) throws InterruptedException {
		Scanner teclado = new Scanner(System.in);
		BigInteger n, passo;
		BigDecimal resultado;
		long tempo1, tempo2; 
		int x;

		System.out.println("Entre com o valor de x: ");
		x = teclado.nextInt();
		
		n = new BigInteger("1000");
		passo = new BigInteger("2000");
				
		ControleTaylor thread0, thread1, thread2;
		
		resultado = BigDecimal.ZERO;
	
		//cria as threads
		thread0 = new ControleTaylor(n, 0, x);
		thread1 = new ControleTaylor(n, 1, x);
		thread2 = new ControleTaylor(n, 2, x);

		tempo1 = System.nanoTime();

		//inicia as threads
		thread0.start();
		thread1.start();
		thread2.start();
				
		//define um ponto de sincronismo (barreira) 
		//aguarda o termino do processamento de todas as threads
		thread0.join();
		thread1.join();
		thread2.join();
		
		//junta os resultados das threads
		resultado = resultado.add(thread0.Resultado());
		resultado = resultado.add(thread1.Resultado());
		resultado = resultado.add(thread2.Resultado());
		
		tempo2 = System.nanoTime();
		
		System.out.print("N = " + n.toString());
		//System.out.print("\tResultado = " + resultado);
		System.out.println("\tTempo = " + String.valueOf((tempo2 - tempo1)/1000000) + " ms  ");
		System.out.println("Resultado: " + resultado);
		
	}

}
