package perceptron;

public class VectUtils {

	/*	Adds B to A. */
	public static void addVect(double adA[], double adB[]){
		for(int i = 0; i < adA.length; i++)
			adA[i] += adB[i];
	}

	/*	Computes A and B's dot product. */
	public static double dotProduct(double adA[], double adB[]){
		float dRes = 0f;
		for(int i = 0; i < adA.length; i++){
			dRes += adA[i] * adB[i];
		}
		return dRes;
	}

	public static void main(String args[]){	System.out.println("With love from Orsay.");	}

	/*	Multiplies A per Fact. */
	public static void multVect(double adA[], double dFact){
		for(int i = 0; i < adA.length; i++)
			adA[i] *= dFact;
	}
	
}
