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

import javax.swing.JFrame;
import org.math.plot.*;
import java.awt.Color;

public class MultiClass {	
	//	Amount of classes
	private int iClassAmt;
	//	Size of a parameter
	private int iParamSize;
	//	Learning rate
	public double dLearningRate = 1.;
	//	Learning rate multiplier
	public double dLRMultiplier = 1.;
	
	//	Maximum amount of iterations.
	int iMaxIterations = 5000;
	//	Maximum tolerated error rate before iMaxIterations iterations were executed.
	double dEpsilon = .001;
	
	//	Neurons' parameters
	double fmatW[][];
	
	//	Train/Test data ArrayLists
	ArrayList<double[]> avdTrainData = new ArrayList<double[]>();
	ArrayList<double[]> avdTestData = new ArrayList<double[]>();
	
	//	Train/Test labels ArrayList
	ArrayList<Integer> aiTrainLabels = new ArrayList<Integer>();
	ArrayList<Integer> aiTestLabels = new ArrayList<Integer>();
	
	//	True positive and false negative curves for each neuron
	ArrayList<double[][]>  apdTruePos = new ArrayList<double[][]>();
	ArrayList<double[][]>  apdFalseNeg = new ArrayList<double[][]>();
	
	/*	Initializes a multiclass perceptron.
	 * All the input vectors must have '1' as first value. The parameter size doesn't include this value.
	 */
	public MultiClass(int ClassAmt, int ParamSize){
		iClassAmt = ClassAmt;
		iParamSize = ParamSize;
		
		fmatW = new double[iClassAmt][iParamSize + 1];
	}
	
	/*	Adds a data vector to the avdTrainData ArrayList.
	 * The data vector's size must be equal to iParamSize.
	 */
	public void addTrainData(double vdData[]){
		if(vdData.length != iParamSize)
			return;
		
		double vdRes[] = new double[iParamSize + 1];
		vdRes[0] = 1;
		for(int i = 0; i < vdData.length; i++)
			vdRes[i+1] = vdData[i];
		
		avdTrainData.add(vdRes);
	}
	
	/*	Adds a data vector to the avdTestData ArrayList.
	 * The data vector's size must be equal to iParamSize.
	 */
	public void addTestData(double vdData[]){
		if(vdData.length != iParamSize)
			return;
		
		double vdRes[] = new double[iParamSize + 1];
		vdRes[0] = 1;
		for(int i = 0; i < vdData.length; i++)
			vdRes[i+1] = vdData[i];
		
		avdTestData.add(vdRes);
	}
	
	/*	Adds an array of data to the training dataset.
	 * Data vectors' sizes must be equal to iParamSize.
	 */
	public void addTrainData(ArrayList<double[]> avdData){
		for(double[] vdData: avdData){
			addTrainData(vdData);
		}
	}
	
	/*	Adds an array of data to the test dataset.
	 * Data vectors' sizes must be equal to iParamSize.
	 */
	public void addTestData(ArrayList<double[]> avdData){
		for(double[] vdData: avdData){
			addTestData(vdData);
		}
	}
	
	/*	Adds a label to the aiTrainLabels ArrayList. */
	public void addTrainLabel(Integer iLabel){
		aiTrainLabels.add(iLabel);
	}
	
	/*	Adds a label to the aiTestLabels ArrayList. */
	public void addTestLabel(Integer iLabel){
		aiTestLabels.add(iLabel);
	}
	
	/*	Adds an array of labels to the training dataset. */
	public void addTrainLabels(ArrayList<Integer> aiLabels){
		for(Integer iLabel: aiLabels){
			addTrainLabel(iLabel);
		}
	}
	
	/*	Adds an array of labels to the test dataset. */
	public void addTestLabels(ArrayList<Integer> aiLabels){
		for(Integer iLabel: aiLabels){
			addTestLabel(iLabel);
		}
	}
	
	/*	Returns the synaptic response for a given neuron/class. */
	public double synapticResponse(double[] dvParam, int iNeuronNumber){	return VectUtils.dotProduct(dvParam, fmatW[iNeuronNumber]);	}
	
	/*	Trains the model. */
	public void trainModel(){
		apdTruePos.clear();
		apdFalseNeg.clear();
		
		//	Initializing apdTruePos and apdFalseNeg
		int i = 0;
		for (; i < iClassAmt; i++){
			apdTruePos.add(new double[iMaxIterations + 1][2]);
			apdFalseNeg.add(new double[iMaxIterations + 1][2]);
		}
		
		//	Execute the epochs
		i = 0;
		for(; i <= iMaxIterations && epoch(i) > dEpsilon; i++);

		//	Rezise arrays according to the number of epoch performed
		//	This prevents the last point of the line to be linked to (0,0)
		for (int c = 0; c < iClassAmt; c++) {
			boolean bTruePosResized = false, bFalseNegResized = false;
			for (int p = 0; p <= iMaxIterations; p++) {
				if (!bTruePosResized && apdTruePos.get(c)[p][0] == 0.0 && apdTruePos.get(c)[p][1] == 0.0 && p > 0) {
					apdTruePos.set(c, Arrays.copyOf(apdTruePos.get(c), p));
					bTruePosResized = true;
				}
				if (!bFalseNegResized && apdFalseNeg.get(c)[p][0] == 0.0 && apdFalseNeg.get(c)[p][1] == 0.0 && p > 0) {
					apdFalseNeg.set(c, Arrays.copyOf(apdFalseNeg.get(c), p));
					bFalseNegResized = true;
				}
			}
		}
		
		//	Create display for true positives
		Plot2DPanel plot2DTruePos = new Plot2DPanel();
		JFrame frameTruePos = new JFrame("True Positives");
		frameTruePos.setSize(600, 600);
		frameTruePos.setContentPane(plot2DTruePos);
		frameTruePos.setVisible(true);
		
		// Create display for false negatives
		Plot2DPanel plot2DFalseNeg = new Plot2DPanel();
		JFrame frameFalseNeg = new JFrame("False Negatives");
		frameFalseNeg.setSize(600, 600);
		frameFalseNeg.setContentPane(plot2DFalseNeg);
		frameFalseNeg.setVisible(true);
		
		//  One curve per neuron
		for (i = 0; i < iClassAmt; i++){
			Color c = new Color((int) Math.floor(Math.random()*256), (int) Math.floor(Math.random()*256), (int) Math.floor(Math.random()*256));
			plot2DTruePos.addLinePlot( Integer.toString(i), c, apdTruePos.get(i));
			plot2DFalseNeg.addLinePlot( Integer.toString(i), c, apdFalseNeg.get(i));
		}
		
		return;
	}
	
	/*	Executes an epoch. */
	double epoch(int iItNumber){
		double dErr = 0.;
		double dRate = dLearningRate;
				
		for(int i = 0; i < avdTrainData.size(); i++){
			int iLabel = 0;
			double dMaxResp = Double.NEGATIVE_INFINITY;
			
			for(int iCurrentClass = 0; iCurrentClass < iClassAmt; iCurrentClass++){
				//	Par rapport au cours :
				//	dGk_ = ~Gk
				//	dGk = Gk
				double dGk = Math.tanh(synapticResponse(avdTrainData.get(i), iCurrentClass));
				double dGk_ = (aiTrainLabels.get(i) == iCurrentClass ? 1. : -1.);
				
				//	Error update
				dErr += Math.pow((dGk - dGk_), 2.) / 2.;
				
				//	Model update
				double vdParam[] = fmatW[iCurrentClass].clone();
				double vdUpdate[] = avdTrainData.get(i).clone();
				VectUtils.multVect(vdUpdate, - dRate * (dGk - dGk_));
				VectUtils.addVect(vdParam, vdUpdate);
				
				fmatW[iCurrentClass] = vdParam;
								
				//	Learning rate update
				dRate *= dLRMultiplier;
				
				//	Getting label
				if(dMaxResp < dGk){
					dMaxResp = dGk;
					iLabel = iCurrentClass;
				}
			}
			
			//	TODO : Curve update
			
			//System.out.println("Result : " + iLabel + "; Actual label : " + aiTrainLabels.get(i));
			
			if(iLabel == aiTrainLabels.get(i)){
				apdTruePos.get(iLabel)[iItNumber][0] = iItNumber;
				apdTruePos.get(iLabel)[iItNumber][1]++;
			}
			else{
				apdFalseNeg.get(aiTrainLabels.get(i))[iItNumber][0] = iItNumber;
				apdFalseNeg.get(aiTrainLabels.get(i))[iItNumber][1]++;
			}
			
			//	----
		}
		System.out.println("It. " + iItNumber + "; Error : " + dErr);
		return dErr;
	}
	
	/*	Runs tests on the test dataset and returns the error rate. */
	public double test(){
		double dErr = 0.;
		
		for(int i = 0; i < avdTestData.size(); i++){
			for(int j = 0; j < iClassAmt; j++){
				//	Par rapport au cours :
				//	dGk_ = ~Gk
				//	dGk = Gk
				double dGk = Math.tanh(synapticResponse(avdTestData.get(i), j));
				double dGk_ = (aiTestLabels.get(i) == j ? 1. : -1.);
				
				double dErrD = Math.pow((dGk - dGk_), 2) / 2.;
				
				dErr += dErrD;
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
