package image;

import mnisttools.MnistReader;
import perceptron.MultiClass;
import image.ImageUtils;

public class Image {
	public static void main(String args[]){		
		//	Création et init de la DB
		String sPath = "./", sImages = sPath + "train-images-idx3-ubyte", sLabels = sPath + "train-labels-idx1-ubyte";
		MnistReader mrDB = new MnistReader(sLabels, sImages);
		
		int iMaxIdx = 1000;
		
		//	Création et init du perceptron multiclasse (10 classes, et on prend le vecteur résultant de la paramétrisation
		//	de la première image comme référence concernant la taille des paramètres).
		MultiClass mcPerceptron = new MultiClass(10, ImageUtils.img2BinVect(mrDB.getImage(1)).length);
		
		for(int i = 1; i <= iMaxIdx; i++){
			mcPerceptron.addTrainData(ImageUtils.img2BinVect(mrDB.getImage(i), 10.));
			mcPerceptron.addTrainLabel(mrDB.getLabel(i));
		}
		
		//	Moulinette
		mcPerceptron.trainModel();
	}
}
