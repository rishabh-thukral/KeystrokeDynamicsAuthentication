package Prediction;

import javax.swing.JOptionPane;

import org.apache.commons.math3.analysis.function.Gaussian;

import AnamolusDetectionML.ModelFitting;
import DataCollection.Collect;
import DataCollection.Node;

public class Predict {
	Node data[];
	double mean[];
	double sd[];
	public Predict(){
		//Collect data from Collect class
		data = Collect.majorLearning();
		ModelFitting model = new ModelFitting(data);
		model.fitModel();
		mean = model.getMean();
		System.out.println("Means");
		for(int i=0;i<4;i++){
			System.out.print(mean[i]+ " ");
		}System.out.println();
		sd = model.getSD();
		System.out.println("sd");
		for(int i=0;i<4;i++){
			System.out.print(sd[i]+ " ");
		}System.out.println();
	}
	//test[] are features of test example 
	public boolean verifyAnamoly(double test[],double epsilon){
		Gaussian distribution;
		double probability = 1;
		for(int i=0;i<4;i++){
			//System.out.print(sd[i]+ " ");
			try{
				distribution = new Gaussian(mean[i], sd[i]);
				probability = probability*distribution.value(test[i]);
			}catch(org.apache.commons.math3.exception.NotStrictlyPositiveException e){
			}
		}
		System.out.println(test[0]+ " " + probability);
		return probability<epsilon;
	}
	public static void main(String args[]){
		double test[] = new double[4];
		Predict obj = new Predict();
		Node verification = Collect.minorTest();
		test[0] = verification.timePerWord;
		test[1] = verification.timePerChar;
		test[2] = verification.editDistance;
		test[3] = verification.backspaceCount;
		if(obj.verifyAnamoly(test, 0.00000001)){
			JOptionPane.showMessageDialog(null, "Authorisation Failed", "Result", JOptionPane.ERROR_MESSAGE);
		}else{
			JOptionPane.showMessageDialog(null, "Authorisation Successful", "Result", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
