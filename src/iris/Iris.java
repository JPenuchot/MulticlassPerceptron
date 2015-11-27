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
		String path="./"; // contient le chemin d'accès
		String labelDB="iris.data";
		
		CsvFile db = new CsvFile(path, labelDB);
				
		MultiClass mcPerceptron = new MultiClass(3, 4);
		 
		//	Chargement des données dans un ArrayList
		for(int i = 0; i < db.getNumData() - 1; i++){
			mcPerceptron.addTrainData(db.getData(i));
			mcPerceptron.addTrainLabel(db.getLabelInt(i).intValue());
		}
		
		mcPerceptron.trainModel(20, Double.NEGATIVE_INFINITY);
		
		for(int i = 0; i < db.getNumData() - 1; i++){
			
		}
	}
}
