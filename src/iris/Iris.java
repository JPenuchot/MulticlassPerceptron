package iris;

import java.io.IOException;
import java.util.ArrayList;

import csv.CsvFile;
import perceptron.MultiClass;

public class Iris {
	public static void main(String args[]) throws IOException{
		System.out.println("Wait for it...");
		
		
		//	Init de la DB
		String path=""; // contient le chemin d'acc�s
		String labelDB="iris.data";
		
		CsvFile db = new CsvFile(path, labelDB);
		 
		//	Chargement des donn�es dans un ArrayList
		
		//	Chargement des labels dans un HashMap
		
		//	Conversion des labels de string en int par tableau de correspondance
		
		//
	}
}
