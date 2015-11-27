package image;

import mnisttools.MnistManager;
import mnisttools.MnistReader;
import perceptron.MultiClass;

import java.io.IOException;

import image.ImageUtils;

public class Image {
	public static void main(String args[]) throws IOException{
		String sOutputPath = "./output/image/";
		//	Cr�ation et init de la DB
		String sPath = "./data/", sImages = sPath + "train-images-idx3-ubyte", sLabels = sPath + "train-labels-idx1-ubyte";
		MnistReader mrDB = new MnistReader(sLabels, sImages);
		
		int iTrainDataAmt = 1000;
		int iTestDataAmt = 1000;
		
		//	Cr�ation et init du perceptron multiclasse (10 classes, et on prend le vecteur r�sultant de la param�trisation
		//	de la premi�re image comme r�f�rence concernant la taille des param�tres).
		MultiClass mcPerceptron = new MultiClass(10, ImageUtils.img2BinVect(mrDB.getImage(1)).length);
		
		for(int i = 1; i <= iTrainDataAmt; i++){
			mcPerceptron.addTrainData(ImageUtils.img2BinVect(mrDB.getImage(i), 10.));
			mcPerceptron.addTrainLabel(mrDB.getLabel(i));
		}
		
		for(int i = iTrainDataAmt; i <= iTrainDataAmt + iTestDataAmt; i++){
			mcPerceptron.addTestData(ImageUtils.img2BinVect(mrDB.getImage(i), 10.));
			mcPerceptron.addTestLabel(mrDB.getLabel(i));
		}
		
		//	Moulinette
		mcPerceptron.trainModel(200, 5.);
		
		int iImgWidth = mrDB.getImage(1).length, iImgHeight = mrDB.getImage(1)[0].length;
		
		for(int i = 0; i < mcPerceptron.getClassAmount(); i++){
			MnistManager.writeImageToPpm(ImageUtils.vect2Img(mcPerceptron.getClassParam(i), iImgWidth, iImgHeight), sOutputPath + "Synapse_" + i + ".ppm");
		}
	}
}
