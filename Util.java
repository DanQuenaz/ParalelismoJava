import java.math.BigInteger;

/*
 * Funcoes matematicas utilitarias
 */
public class Util {

	/*
	 * Funcao para calcular o fatorial de um numero qualquer n
	 */
	public static BigInteger Fatorial(BigInteger n) {
		BigInteger soma = BigInteger.ONE;
		
		//calculo de 0!
		if (n.compareTo(BigInteger.ZERO) == 0) return soma; 
		
		//calculo de n!
		for(BigInteger i = BigInteger.ONE; i.compareTo(n.add(BigInteger.ONE)) != 0; i = i.add(BigInteger.ONE))
			soma = soma.multiply(i);
		
		return soma;
	}
	
	/*
	 * Funcao para calcular a potencia de 2^n
	 */
	public static BigInteger Potencia(BigInteger x, BigInteger n) {
		BigInteger soma = BigInteger.ONE;
		
		//calculo de 2^0 
		if (n.compareTo(BigInteger.ZERO) == 0) return soma;
		if( n.compareTo(BigInteger.ONE) == 0 ) return x;
		
		//calculo de 2^n 
		for(BigInteger i = BigInteger.ZERO; i.compareTo(n) != 0; i = i.add(BigInteger.ONE))
			soma = soma.multiply(x);
		
		return soma;
	} 

}
