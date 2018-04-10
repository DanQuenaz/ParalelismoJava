import java.math.BigInteger;

/*
 * Algoritmo Sequencial  
 * Calculo do somatorio (1! + 2! + 3! + ... + n!) + (2^1 + 2^2 + ... + 2^n)
 */
public class Sequencial {
	
	/*
	 * Somatorio (1! + 2! + 3! + ... + n!) + (2^1 + 2^2 + ... + 2^n)
	 */
	public BigInteger Somatorio(BigInteger n) {		
		BigInteger soma = BigInteger.ZERO;
		
		for(BigInteger i = BigInteger.ONE; i.compareTo(n) != 0; i = i.add(BigInteger.ONE)) {
			soma = soma.add(Util.Fatorial(i));
			soma = soma.add(Util.Potencia2(i));
		}
		
		return soma;
	}
	
	public static void main(String[] args) {
		BigInteger n, resultado, passo;
		long tempo1, tempo2; 
		Sequencial alg = new Sequencial();
		
		n = BigInteger.ZERO;
		passo = new BigInteger("2000");
		
		//Calcula o somatorio para os valores 2000, 4000, 6000, 8000, 10000
		for(int i = 0; i < 5; i++){
			n = n.add(passo)
					;
			tempo1 = System.nanoTime(); 
			resultado = alg.Somatorio( n );
			tempo2 = System.nanoTime(); 
			
			System.out.print("N = " + n.toString());
			//System.out.print("\tResultado = " + resultado.toString());
			System.out.println("\tTempo = " + String.valueOf((tempo2 - tempo1)/1000000) + " ms");
		}
		
	}

}
