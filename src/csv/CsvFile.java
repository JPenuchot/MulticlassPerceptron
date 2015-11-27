package csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class CsvFile {
	public final static char SEPARATOR = ',';

    private static File file;
    private List<String> lines;
    private List<String[]> data;
    private Hashtable<String, Integer> classe;
    
    private int max_line;

    public List<String> readFile(File file) throws IOException {

        List<String> result = new ArrayList<String>();

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        for (String line = br.readLine(); line != null; line = br.readLine()) {
            result.add(line);
        }

        br.close();
        fr.close();

        return result;
    }

    public CsvFile(File file) throws IOException {
    	CsvFile.file = file;
    	init();
    }
    
    public CsvFile(String path, String filename) throws IOException {
    	CsvFile.file = new File(path+filename);
    	init();
    }
    
    private void init() throws IOException {
        lines = readFile(file);
        
        data = new ArrayList<String[]>(lines.size());
        String sep = new Character(SEPARATOR).toString();
        for (String line : lines) {
            String[] oneData = line.split(sep);
            data.add(oneData);
        }
        max_line = lines.size();
        InitHash();
    }
    
    public int getNumData() {
    	return max_line;
    }
    
    public double[] getData(int index) {
    	assert index>=0 : "the index should be positive";
    	assert index<max_line: "index too high!";
    	
    	String[] target = data.get(index);
    	    	
    	double[] dataline = new double[target.length-1];
    	for(int i=0; i<dataline.length; i++) 
    		dataline[i] = Double.parseDouble(target[i]);
    	
    	return dataline;
    }
    
    public void InitHash() {
    	classe = new Hashtable<String,Integer>();
    	classe.put("Iris-setosa",new Integer(0));
    	classe.put("Iris-versicolor",new Integer(1));
    	classe.put("Iris-virginica",new Integer (2));
    }
    
    public String getLabelStr(int index) {
    	assert index>=0 : "the index should be positive";
    	assert index<max_line: "index too high!";
    	
    	String[] target = data.get(index);
    	String label = target[target.length-1];
    	
    	return label;
    }
    
    public Integer getLabelInt(int index) {
    	assert index>=0 : "the index should be positive";
    	assert index<max_line: "index too high!";
    	
    	String[] target = data.get(index);
    	String label = target[target.length-1];
    	return classe.get(label);
    }
    
    public static void main(String[] args) throws IOException {
    	System.out.println("Hello!");
    	String path="/home/aurelien/work/workspace/VieArtificielle/IRIS/iris.data";
    	File myfile = new File(path);
    	CsvFile mycsv = new CsvFile(myfile);
    	
    	ArrayList<double[]> d = new ArrayList<double[]>();
    	ArrayList<String> s = new ArrayList<String>();
    	ArrayList<Integer> c = new ArrayList<Integer>();
    	d.add(mycsv.getData(59));
    	s.add(mycsv.getLabelStr(59));
    	c.add(mycsv.getLabelInt(59));
    	System.out.println(d.get(0)[0]);
    	System.out.println(s.get(0));
    	System.out.println(c.get(0));
    	
    }
}
