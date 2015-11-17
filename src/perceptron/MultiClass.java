package perceptron;

import java.util.*;
import java.lang.Math;

public class MultiClass {
	public static void main(String args[]){
		System.out.println("With love from Orsay.");
	}
	
	//	Amount of classes
	private int iClassAmt;
	//	Size of a parameter
	private int iParamSize;
	//	Learning rate
	public double dLearningRate = 1.;
	
	//	Neurons' parameters
	double fmatW[][];
	
	//	Train/Test data ArrayLists
	ArrayList<double[]> avdTrainData;
	ArrayList<double[]> avdTestData;
	
	//	Train/Test labels ArrayList
	ArrayList<Integer> aiTrainLabels;
	ArrayList<Integer> aiTestLabels;
	
	/*	Initializes a multiclass perceptron.
	 * All the input vectors must have '1' as first value. The parameter size doesn't include this value.
	 */
	MultiClass(int ClassAmt, int ParamSize){
		iClassAmt = ClassAmt;
		iParamSize = ParamSize;
		
		fmatW = new double[iClassAmt][iParamSize + 1];
	}
	
	//	Returns the synaptic response for a given neuron/class.
	public double synapticResponse(double[] dvParam, int iNeuronNumber){	return dotProduct(dvParam, fmatW[iNeuronNumber]);	}
	
	//	Executes an epoch
	public double epoch(){
		double dErr = 0.;
				
		for(int i = 0; i < avdTrainData.size(); i++){
			for(int j = 0; j < iClassAmt; j++){
				//	Par rapport au cours :
				//	dGk_ = ~Gk
				//	dGk = Gk
				double dGk = Math.tanh(synapticResponse(avdTrainData.get(i), j));
				double dGk_ = (aiTrainLabels.get(i) == j ? 1. : -1.);
				
				double dErrD = Math.pow((dGk - dGk_), 2);
				dErrD *= .5;
				
				dErr += dErrD;
				
				double vdParam[] = fmatW[j];
				double vdUpdate[] = fmatW[j];
				multVect(vdUpdate, - dLearningRate * (dGk - dGk_) * (1 - (dGk * dGk)));
				addVect(vdParam, vdUpdate);
				
				fmatW[j] = vdParam;
			}
		}
		
		return dErr;
	}
	
	public int classify(double[] dvParam){
		double dvSR[] = synapticResponses(dvParam);
		
		int iRes = 0;
		double dMax = Double.NEGATIVE_INFINITY;
		for(int i = 0; i < dvSR.length; i++){
			if(dvSR[i] > dMax){
				iRes = i;
				dMax = dvSR[i];
			}
		}
		
		return iRes;
	}
	
	public double[] synapticResponses(double[] dvParam){
		double res[] = new double[fmatW.length];
		
		for(int i = 0; i < fmatW.length; i++){
			res[i] = synapticResponse(dvParam, i);
		}
		
		return res;
	}
	
//		Computes A and B's dot product
	public static double dotProduct(double adA[], double adB[]){
		float dRes = 0f;
		for(int i = 0; i < adA.length; i++){
			dRes += adA[i] * adB[i];
		}
		return dRes;
	}
	
	//	afds B to A
	public static void addVect(double adA[], double adB[]){
		for(int i = 0; i < adA.length; i++)
			adA[i] += adB[i];
	}
	
	//	Multiplies A per Fact
	public static void multVect(double adA[], double dFact){
		for(int i = 0; i < adA.length; i++)
			adA[i] *= dFact;
	}

}
