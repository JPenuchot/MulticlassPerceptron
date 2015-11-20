package perceptron;

import java.util.ArrayList;

public class Image {
	public static void main(String args[]){
		System.out.println("Wait for it...");
		
		String path="..."; // contient le chemin d'accès
		String labelDB="iris.data";
		CsvFile db = new CsvFile(path,labelDB);
		 
		// Charger une donnée
		ArrayList<float[]> Data = new ArrayList<float[]>();
		Data.add(Data2Perceptron(db.getData(0));
		 
		// Charger un label
		int label = Data2Perceptron(db.getLabelInt(0));
	}
}
