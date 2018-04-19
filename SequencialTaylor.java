import java.math.BigInteger;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;


public class SequencialTaylor{
	private BigInteger inicio; //inicio do intervalo de processamento
	private BigInteger fim; //fim do intervalo de processamento
	private BigDecimal resultado; //resultado do processamento
    private int x;

	/*
	 * Construtor padrao
	 */
	public SequencialTaylor(int x, int inicio, int fim){
		this.inicio = BigInteger.valueOf(inicio);
		this.fim = BigInteger.valueOf(fim);
		resultado = BigDecimal.ZERO;
        this.x = x;
	}


	public void Calcula() {
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
        

        n = new BigInteger("1000");
		passo = new BigInteger("2000"); 
		
        
        posicao = BigInteger.ZERO;
        resultado = BigDecimal.ZERO;

        SequencialTaylor sequencial = new SequencialTaylor(x, 0, 1000);

        
        tempo1 = System.nanoTime();
        
        sequencial.Calcula();
        resultado = sequencial.Resultado();
        
        tempo2 = System.nanoTime();
        
        System.out.print("N = " + n.toString());
        //System.out.print("\tResultado = " + resultado);
        System.out.println("\tTempo = " + String.valueOf((tempo2 - tempo1)/1000000) + " ms");
        System.out.println("Resultado: " + resultado);
		
	}

}
