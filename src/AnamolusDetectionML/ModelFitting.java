package AnamolusDetectionML;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import DataCollection.Node;

public class ModelFitting {
	double X[][];//Features
	double mean[];
	double sd[] ;
	public ModelFitting(Node data[]){
		//m = data.length
		//n = 4 features
		//n = 0~time taken per word in ms.
		//n = 1~time taken per char in ms.
		//n = 2~Edit Distance of Actual String and User Input.
		//n = 3~Backspace Hits Count.
		X = new double[data.length][4];
		for(int i=0;i<data.length;i++){
			X[i][0] = data[i].timePerWord;
			X[i][1] = data[i].timePerChar;
			X[i][2] = data[i].editDistance;
			X[i][3] = data[i].backspaceCount;
		}
		
		
		
//		X = new double[3][4];
//		double Xp[][]={
//				{187.5 , 37.45 , 2 , 1},
//				{153.84 , 30.76 , 1, 1},
//				{200 , 40 , 2 , 2}
//		};
//		X = Xp;
		
		
		
		mean = new double[4];
		sd =new double[4];
	}
	public void fitModel(){
		Array2DRowRealMatrix x = new Array2DRowRealMatrix(X);
		DescriptiveStatistics stats = new DescriptiveStatistics();
		for(int i=0;i<4;i++){//changes here 
			for(int j=0;j<3;j++){// changes here
				stats.addValue(X[j][i]);
			}
			mean[i] = stats.getMean();
			sd[i] = stats.getStandardDeviation();
			stats.clear();
		}
	}
	public double[] getMean(){
		return mean;
	}
	public double[] getSD(){
		return sd;
	}
}
