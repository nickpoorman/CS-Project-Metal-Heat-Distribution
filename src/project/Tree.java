package project;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Tree implements Runnable {

	final int rows;
	final int columns;
	GUI gui;
	final long[][] cellsA;
	final long[][] cellsB;

	public Tree(GUI gui, int columns, int rows) {
		this.gui = gui;
		this.rows = rows;
		this.columns = columns;
		cellsA = new long[rows][columns];
		cellsB = new long[rows][columns];
		// setInitialTemp(cellsA);
		// setInitialTemp(cellsB);
		// long initTemp = 134217728;
		// for(int row = 0; row < cellsA.length; row++){
		// for(int column = 0; column < cellsA[0].length; column++){
		// cellsA[row][column] = initTemp;
		// cellsB[row][column] = initTemp;
		// }
		// }
		updateGUI(cellsA);
	}

	@Override
	public void run() {
		boolean readA = true;
		// final double metal1Constant = 0.75;
		// final double metal2Constant = 1.0;
		// final double metal3Constant = 1.25;
		// final double[] metalConstants = new double[] { 0.75, 1.0, 1.25 };
		final double[] metalConstants = new double[] { 1.0, 1.0, 1.0 };

		// long halfTemp = Long.MAX_VALUE / 2;
		// 4294967296L

		cellsA[0][0] = Long.MAX_VALUE >> 2;
		cellsA[rows - 1][columns - 1] = Long.MAX_VALUE >> 2;
		cellsB[0][0] = Long.MAX_VALUE >> 2;
		cellsB[rows - 1][columns - 1] = Long.MAX_VALUE >> 2;

		final int[][][] percentageOfMetals = new int[rows][columns][3];
		// System.out.println("Going to generate random percentages");
		generateRandomPercentageOfMetals(percentageOfMetals);
		// System.out.println("Done generating random percentages");

		// final double[][] percentageOfMetal2 = new double[x][y];
		// final double[][] percentageOfMetal3 = new double[x][y];
		ForkJoinPool pool = new ForkJoinPool();
		// long[][] cellsA, long[][] cellsB, int xMin, int yMin, int xMax, int
		// yMax
		// for (int runNum = 0; runNum < 100000; runNum++) {
		//int times = 0;
		//int refreshEvery = 0;
		 for (;;) {
		//for (int itr = 0; itr < 30000; itr++) {
			// try {
			// Thread.sleep(2000);
			// } catch (InterruptedException e) {
			//
			// }
			// System.out.println("Getting ready to run in the pool");
			pool.invoke(new Node(cellsA, cellsB, 0, 0, (rows - 1), (columns - 1), readA, metalConstants,
					percentageOfMetals));
			// System.out.println("FINISHED AN ITERATION");
			if (readA) {
				// if (times % refreshEvery == 0) {
				updateGUI(cellsB);
				// }
				readA = false;
				// int count = 0;
				// for (int i = 0; i < cellsB.length; i++) {
				// for (int j = 0; j < cellsB[0].length; j++) {
				// //tmp[i][j] = bla;
				// //bla++;
				// System.out.printf("%22d", cellsB[i][j]);
				// if (++count % cellsB[0].length == 0) {
				// System.out.printf("%n");
				// }
				// }
				// }
			} else {
				// if (times % refreshEvery == 0) {
				updateGUI(cellsA);
				// }
				readA = true;
				// int count = 0;
				// for (int i = 0; i < cellsA.length; i++) {
				// for (int j = 0; j < cellsA[0].length; j++) {
				// //tmp[i][j] = bla;
				// //bla++;
				// System.out.printf("%22d", cellsA[i][j]);
				// if (++count % cellsA[0].length == 0) {
				// System.out.printf("%n");
				// }
				// }
				// }
			}
			//times++;

		}

	}

	public static void setInitialTemp() {
		//long temp = (Long.MAX_VALUE - 1000);
		// long temp = 0;
		// for (int row = 0; row < cells.length; row++) {
		// for (int column = 0; column < cells[0].length; column++) {
		// cells[row][column] = temp;
		// }
		// }

	}

	public static void generateRandomPercentageOfMetals(int[][][] percentageOfMetals) {
		Random r = new Random();

		for (int row = 0; row < percentageOfMetals.length; row++) {
			for (int column = 0; column < percentageOfMetals[0].length; column++) {
				// System.out.println("generating percentage");
				int totalPercentLeft = 100;
				for (int a = 0; a < percentageOfMetals[0][0].length; a++) {

					 //percentageOfMetals[row][column][a] = 33;

					if (a == 0) {
						int d = r.nextInt(101);
						percentageOfMetals[row][column][a] = d;
						totalPercentLeft = totalPercentLeft - d;
						// System.out.println("end 1st: " + d);
					} else if (a == 1) {
						//int d = 200;
						//while (d > totalPercentLeft) {
							int d = r.nextInt(totalPercentLeft + 1);
						//}
						percentageOfMetals[row][column][a] = d;
						totalPercentLeft = totalPercentLeft - d;
						// System.out.println("end 2nd: " + d);
					} else {
						percentageOfMetals[row][column][a] = totalPercentLeft;
						// System.out.println("end 3rd: " + totalPercentLeft);
					}
				}
			}
		}
	}

	// public void updateGUI(final long[][] pixels) {
	// // Thread t = new Thread(new Runnable(){
	// // public void run(){
	// // gui.pixelCanvas.setPixels(pixels);
	// // //for(;;){
	// // //update the gui with the right array
	// // gui.pixelCanvas.repaint();
	// // //}
	// // }
	// // });
	//
	// if (gui.pixelCanvas.pixelsA == null) {
	// gui.pixelCanvas.setPixels(pixels);
	// }
	//
	// javax.swing.SwingUtilities.invokeLater(new Runnable() {
	//
	// public void run() {
	// // for (;;) {
	// // gui.pixelCanvas.setPixels(pixels);
	// // gui.pixelCanvas.setAllPixelsRandom();
	// gui.pixelCanvas.repaint();
	// // }
	// }
	// });
	//
	// }

	public void updateGUI(final long[][] pixels) {
		// Thread t = new Thread(new Runnable(){
		// public void run(){
		// gui.pixelCanvas.setPixels(pixels);
		// //for(;;){
		// //update the gui with the right array
		// gui.pixelCanvas.repaint();
		// //}
		// }
		// });

		//if (gui.pixelCanvas.pixelsA == null) {
			
		//}		

		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				gui.pixelCanvas.setPixels(pixels);
				// for (;;) {
				// gui.pixelCanvas.setPixels(pixels);
				// gui.pixelCanvas.setAllPixelsRandom();
				gui.pixelCanvas.repaint();
				// }
			}
		});

	}

}
