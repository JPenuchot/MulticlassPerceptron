package image;

import mnisttools.MnistReader;
import perceptron.MultiClass;
import image.ImageUtils;

public class Image {
	public static void main(String args[]){		
		//	Cr�ation et init de la DB
		String sPath = "./", sImages = sPath + "train-images-idx3-ubyte", sLabels = sPath + "train-labels-idx1-ubyte";
		MnistReader mrDB = new MnistReader(sLabels, sImages);
		
		int iMaxIdx = 100;
		
		//	Cr�ation et init du perceptron multiclasse (10 classes, et on prend le vecteur r�sultant de la param�trisation
		//	de la premi�re image comme r�f�rence concernant la taille des param�tres).
		MultiClass mcPerceptron = new MultiClass(10, ImageUtils.img2BinVect(mrDB.getImage(1)).length);
		
		for(int i = 1; i <= iMaxIdx; i++){
			mcPerceptron.addTrainData(ImageUtils.img2BinVect(mrDB.getImage(i), 10.));
			mcPerceptron.addTrainLabel(mrDB.getLabel(i));
		}
		
		//	Moulinette
		mcPerceptron.trainModel();
	}
}