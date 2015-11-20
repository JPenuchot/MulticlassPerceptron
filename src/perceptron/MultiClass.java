/*	
 * Made by :
 * 
 * 		- Théophile Walter
 * 		- Jules Pénuchot
 * 
 * Both from Paris-Sud University in Orsay, France
 */

package perceptron;

import java.util.*;
import java.lang.Math;
import perceptron.VectUtils;

public class MultiClass {	
	//	Amount of classes
	private int iClassAmt;
	//	Size of a parameter
	private int iParamSize;
	//	Learning rate
	public double dLearningRate = 1.;
	//	Learning rate multiplier
	public double dLRMultiplier = .8;
	
	//	Maximum amount of iterations.
	int iMaxIterations = 100;
	//	Maximum tolerated error rate before iMaxIterations iterations were executed.
	double dEpsilon = .1;
	
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
	
	/*	Returns the synaptic response for a given neuron/class. */
	public double synapticResponse(double[] dvParam, int iNeuronNumber){	return VectUtils.dotProduct(dvParam, fmatW[iNeuronNumber]);	}
	
	/*	Trains the model. */
	public void trainModel(){
		for(int i = 0; i < iMaxIterations && epoch() > dEpsilon; i++);
		return;
	}
	
	/*	Executes an epoch. */
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
				VectUtils.multVect(vdUpdate, - dLearningRate * (dGk - dGk_) * (1 - (dGk * dGk)));
				VectUtils.addVect(vdParam, vdUpdate);
				
				fmatW[j] = vdParam;
				
				dLearningRate *= dLRMultiplier;
				
			}
		}
		
		return dErr;
	}
	
	/*	Classifies given data. */
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
	
	/*	Returns an array containing the synaptic response of each neuron for given data. */
	public double[] synapticResponses(double[] dvParam){
		double res[] = new double[fmatW.length];
		
		for(int i = 0; i < fmatW.length; i++){
			res[i] = synapticResponse(dvParam, i);
		}
		
		return res;
	}
}
