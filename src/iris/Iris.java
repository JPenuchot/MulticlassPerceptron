package iris;

import java.io.IOException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import csv.CsvFile;
import perceptron.MultiClass;
import perceptron.VectUtils;

public class Iris {
	public static void main(String args[]) throws IOException{
		//	Init de la DB
		String path="./data/"; // contient le chemin d'accès
		String labelDB="iris.data";
		CsvFile db = new CsvFile(path, labelDB);
				
		MultiClass mcPerceptron = new MultiClass(3, 4);
		
		String sOutputPath = "./output/iris/";
		 
		//	Chargement des données dans un ArrayList
		for(int i = 0; i < db.getNumData() - 1; i++){
			mcPerceptron.addTrainData(db.getData(i));
			mcPerceptron.addTrainLabel(db.getLabelInt(i).intValue());
		}
		
		mcPerceptron.trainModel(20, Double.NEGATIVE_INFINITY);
		
		HashMap<String, ArrayList<double[]>> hmPointClasses = new HashMap<String, ArrayList<double[]>>();
		
		//	Classement des points
		hmPointClasses.put("Unclassed", new ArrayList<double[]>());
		hmPointClasses.put("Iris-setosa", new ArrayList<double[]>());
		hmPointClasses.put("Iris-versicolor", new ArrayList<double[]>());
		hmPointClasses.put("Iris-virginica", new ArrayList<double[]>());
		
		for(int i = 0; i < db.getNumData() - 1; i++){
			if(db.getLabelInt(i) != mcPerceptron.classify(db.getData(i))){
				hmPointClasses.get("Unclassed").add(db.getData(i));
			}
			else{
				hmPointClasses.get(db.getLabelStr(i)).add(db.getData(i));
			}
		}
		
		//	Ecriture du fichier de sortie (A envoyer à GNU plot)
	}
}
