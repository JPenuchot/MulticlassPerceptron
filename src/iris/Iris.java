/*	Made by :
 * 
 * 		- Théophile Walter
 * 		- Jules Pénuchot
 * 
 * Both from Paris-Sud University in Orsay, France
 */

package iris;

import java.io.IOException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import csv.CsvFile;
import perceptron.MultiClass;

public class Iris {
	public static void main(String args[]) throws IOException{
		//	Init de la DB
		String path="./data/"; // contient le chemin d'accès
		String labelDB="iris.data";
		CsvFile db = new CsvFile(path, labelDB);
				
		MultiClass mcPerceptron = new MultiClass(3, 4);
		
		//	Path de la sortie
		String sOutputPath = "./output/iris/";
		
		for(int i = 0; i < db.getNumData() - 1; i++){
			mcPerceptron.addTrainData(db.getData(i));
			mcPerceptron.addTrainLabel(db.getLabelInt(i).intValue());
			mcPerceptron.addTestData(db.getData(i));
			mcPerceptron.addTestLabel(db.getLabelInt(i).intValue());
		}
		
		mcPerceptron.trainModel(2000, 5., .0005, 1.);
		
		System.out.println("Test error rate : " + mcPerceptron.test(0));
						
		HashMap<String, ArrayList<double[]>> hmPointClasses = new HashMap<String, ArrayList<double[]>>();
		
		//	Classement des points
		hmPointClasses.put("Unclassed", new ArrayList<double[]>());
		hmPointClasses.put("Iris-setosa", new ArrayList<double[]>());
		hmPointClasses.put("Iris-versicolor", new ArrayList<double[]>());
		hmPointClasses.put("Iris-virginica", new ArrayList<double[]>());
				
		for(int i = 0; i < db.getNumData() - 1; i++){
			System.out.println("Label : " + db.getLabelInt(i).intValue() + "; Guess : " + mcPerceptron.classify(db.getData(i)));
		}
		
		for(int i = 0; i < db.getNumData() - 1; i++){
			if(db.getLabelInt(i) != mcPerceptron.classify(db.getData(i))){
				hmPointClasses.get("Unclassed").add(db.getData(i));
			}
			else{
				hmPointClasses.get(db.getLabelStr(i)).add(db.getData(i));
			}
		}
		
		//	Ecriture du fichier contenant les points et les classes attribuées par le perceptron
		FileOutputStream fosOut = new FileOutputStream(sOutputPath + "res.dat");
		PrintStream psPrint = new PrintStream(fosOut);
		
		for(String sKey: hmPointClasses.keySet()){
			for(int i = 0; i < hmPointClasses.get(sKey).size(); i++){
				psPrint.println(hmPointClasses.get(sKey).get(i)[0] + " "+ hmPointClasses.get(sKey).get(i)[1] + " "
						+ hmPointClasses.get(sKey).get(i)[2] + " " + hmPointClasses.get(sKey).get(i)[3] + " "
						+ sKey);
			}
			psPrint.println();
			psPrint.println();
		}
		
		psPrint.close();
		fosOut.close();
	}
}
