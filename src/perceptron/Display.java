/*	
 * Made by :
 * 
 * 		- Théophile Walter
 * 		- Jules Pénuchot
 * 
 * Both from Paris-Sud University in Orsay, France
 */

package perceptron;
import java.awt.Color;
import javax.swing.JFrame;

// "jmathplot.jar"
import org.math.plot.Plot2DPanel;

public class Display {
	
	private static Plot2DPanel plot2D;
	private static int iLines;

	public static void main(String s[]) throws InterruptedException {
		
		// Create a display
		Display d = new Display();
		d.show();
		
		// Generate curves
		double [][] l1 = new double[100][2], l2 = new double [100][2];
		for (int i = 0; i < 100; i++) {
			l1[i][0] = l2[i][0] = i;
			l1[i][1] = i * i + 5;
			l2[i][1] = i * (i / 2)  - 7;
		}
		d.addCurve(l1, new Color(0,255,0));
		d.addCurve(l2, new Color(255,0,0));
		
	}
	
	public Display () {
		plot2D = new Plot2DPanel();
		iLines = 0;
	}
	
	/* Adding a line to the render */
	public void addCurve(double [][] line, Color c) {
		plot2D.addLinePlot( Integer.toString(iLines), c, line);
		iLines++;
	}
	
	/* Displays the graphical window */
	public void show() {
		show (600, 600);
	}
	
	public void show(int width, int height) {
		JFrame frame = new JFrame("MultiClass Perceptron");
		frame.setSize(width, height);
		frame.setContentPane(plot2D);
		frame.setVisible(true);
	}

}