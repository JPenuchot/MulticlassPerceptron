package image;

public class ImageUtils {
	public static double dDefaultThreshold = 100.;
	
	public static double[] img2BinVect(int[][] image, double seuil){
		double[] x  = new double[image.length*image[0].length+1];
		x[0] = 1;
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				if (image[i][j] > seuil)
					x[image.length*i+j+1] = 1.;
				else
					x[image.length*i+j+1] = 0.;
			}
		}
		return x;
	}
	
	public static double[] img2BinVect(int[][] image){
		return img2BinVect(image, dDefaultThreshold);
	}
	
	public static int[][] vect2Img(double[] fVect, int iWidth, int iHeight){
		int aiRes[][] = new int[iWidth][iHeight];
		
		for(int i = 0; i < iWidth; i++)
			for(int j = 0; j < iHeight; j++)
				aiRes[i][j] = (int)(fVect[iHeight * i + j + 1] * 255.);
		
		return aiRes;
	}
}
