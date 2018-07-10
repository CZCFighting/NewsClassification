package training;

public class TrainingText {

	public static void main(String []args) {
//		IDFTraining training = new IDFTraining();
//		training.initIDF();
		TFIDFCalculation calculation = new TFIDFCalculation();
		calculation.calTFIDF();
	}
}
