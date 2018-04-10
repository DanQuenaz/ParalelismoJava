import java.math.BigInteger;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

/*
 * Algoritmo Paralelo em Dados
 * Calculo do somatorio (1! + 2! + 3! + ... + n!) + (2^1 + 2^2 + ... + 2^n)
 */
public class DadosTaylor extends Thread {
	private BigInteger inicio; //inicio do intervalo de processamento
	private BigInteger fim; //fim do intervalo de processamento
	private BigDecimal resultado; //resultado do processamento
    private int x;

	/*
	 * Construtor padrao
	 */
	public DadosTaylor(int x){
		this.inicio = BigInteger.ZERO;
		this.fim = BigInteger.ZERO;
		resultado = BigDecimal.ZERO;
        this.x = x;
	}

	/*
	 * Define o inicio e o fim do intervalo de dados para processamento
	 */
	public void DefineIntervalo(BigInteger inicio, BigInteger fim){
		this.inicio = inicio;
		this.fim = fim;
		resultado = BigDecimal.ZERO;
	}

	/*
	 * Algoritmo executado pela thread
	 * @see java.lang.Thread#run()
	 */

   

	public void run() {
        resultado = BigDecimal.ZERO;
        BigDecimal pot  = BigDecimal.ZERO;			
		BigDecimal potc  = BigDecimal.ZERO;
        BigDecimal pots  = BigDecimal.ZERO;
        BigDecimal pote  = BigDecimal.ZERO;
        BigInteger fat  = BigInteger.ONE;
		BigInteger n2  = BigInteger.ONE;
		
		for(BigInteger i = inicio; i.compareTo(fim) != 0; i = i.add(BigInteger.ONE)) {
			pot = new BigDecimal( Util.Potencia(new BigInteger("-1"), i) );
            n2 = i.multiply( BigInteger.valueOf(2) );

            potc = pot.divide( new BigDecimal( Util.Fatorial( n2 ) ), 1000, RoundingMode.HALF_UP );
            potc = potc.multiply( new BigDecimal( Util.Potencia( BigInteger.valueOf(this.x), n2 ) ) );

            resultado = resultado.add( potc  );

            pots = pot.divide( new BigDecimal( Util.Fatorial( n2.add(BigInteger.ONE) ) ), 1000, RoundingMode.HALF_UP );
            pots = pots.multiply( new BigDecimal( Util.Potencia( BigInteger.valueOf(this.x), n2.add(BigInteger.ONE) ) ) );

            resultado = resultado.add( pots  );

            pote = new BigDecimal( Util.Potencia(BigInteger.valueOf(this.x), i) );
            fat = Util.Fatorial(i);
            pote = pote.divide( new BigDecimal(fat), 1000, RoundingMode.HALF_UP );

            resultado = resultado.add( pote  );
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
		int numThreads;
		BigInteger n,  passo, tamanho, posicao;
        BigDecimal resultado;
		long tempo1, tempo2;
        int x;

		System.out.println("Entre com o valor de x: ");
		x = teclado.nextInt();
        
		System.out.println("Entre com a quantidade de threads: ");
		numThreads = teclado.nextInt();
		teclado.close();

        n = new BigInteger("1000");
		passo = new BigInteger("2000"); 
		
		DadosTaylor[] threads = new DadosTaylor[numThreads]; 
		
		//Calcula o somatorio para os valores 2000, 4000, 6000, 8000, 10000
		
        
        tamanho = n.divide( BigInteger.valueOf(numThreads) );
        
        posicao = BigInteger.ZERO;
        resultado = BigDecimal.ZERO;

        //cria as threads
        for(int j = 0; j < numThreads; j++) 
            threads[j] = new DadosTaylor(x);
        
        tempo1 = System.nanoTime();
        
        //divide os dados entre as (numThreads - 1) threads
        for(int j = 0; j < numThreads - 1; j++) {
            threads[j].DefineIntervalo(posicao, posicao.add(tamanho));
            posicao = posicao.add(tamanho);
        }
        //atribui o restante dos dados para a ultima thread 
        //resolve a questao da divisao inteira
        threads[numThreads - 1].DefineIntervalo(posicao, n);
    
        //inicia as threads
        for(int j = 0; j < numThreads; j++)
            threads[j].start();
        
        //define um ponto de sincronismo (barreira) 
        //aguarda o termino do processamento de todas as threads
        for(int j = 0; j < numThreads; j++)
            threads[j].join();
        
        //junta os resultados das threads
        for(int j = 0; j < numThreads; j++)
            resultado = resultado.add(threads[j].Resultado());
        
        tempo2 = System.nanoTime();
        
        System.out.print("N = " + n.toString());
        //System.out.print("\tResultado = " + resultado);
        System.out.println("\tTempo = " + String.valueOf((tempo2 - tempo1)/1000000) + " ms");
        System.out.println("Resultado: " + resultado);
		
	}

}
