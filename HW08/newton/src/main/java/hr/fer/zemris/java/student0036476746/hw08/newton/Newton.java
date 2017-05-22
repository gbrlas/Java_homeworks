package hr.fer.zemris.java.student0036476746.hw08.newton;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Class which represents a Newton-Raphson iteration-based fractal viewer.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Newton {
	/**
	 * Static value of the complex polynomial.
	 */
	public static ComplexPolynomial polynomial;
	/**
	 * Static value of the complex polynomial derivation.
	 */
	public static ComplexPolynomial derived;
	/**
	 * Static value of the rooted complex polynomial.
	 */
	public static ComplexRootedPolynomial rootedPolynomial;
	
	/**
	 * Main method which starts the program.
	 * Takes no arguments.
	 * 
	 * @param args - none.
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer."
						+ "Please enter at least two roots, one root per line."
						+ "Enter \'done\' when done.");
		
		List<Complex> roots = new ArrayList<Complex>();
		
		boolean test = true;
		
		int i = 1;
		while (test) {
			System.out.println("Root " + i + "> ");
			String line = scanner.nextLine();
			test = parseLine(roots, line);
			i++;
		}
		
		if (i < 3) {
			System.out.println("Invalid number of roots provided.");
			System.exit(-1);
		}
		
		Complex[] rootsArray = new Complex[roots.size()];
		for (i = 0; i < roots.size(); i++) {
			rootsArray[i] = roots.get(i);
		}
		
		rootedPolynomial = new ComplexRootedPolynomial(rootsArray);
		polynomial = rootedPolynomial.toComplexPolynom();
		derived = polynomial.derive();
		
		scanner.close();
		
		showParallel();
	}
	
	/**
	 * Parses the entered line.
	 * 
	 * @param roots ArrayList holding all provided root values.
	 * @param line String representation of the entered line.
	 * @return False if user entered 'done', false otherwise.
	 */
	private static boolean parseLine(List<Complex> roots, String line) {
		line = line.trim();
		
		if (line.toLowerCase().equals("done")) {
			return false;
		}
		
		Complex c = parse(line);
		roots.add(c);
		
		return true;
	}
	
	/**
	 * Parse helper which parses the given string into a complex number. String must be passed in a valid format.
	 * @param str String to be parsed into a complex number.
	 * @return New instance of ComplexNumber with the given value.
	 */
	private static Complex parse (String str) {
		
		if (str.length() == 0) {
			throw new IllegalArgumentException("String to be parsed must not be empty!");
		}
		
		
		StringBuilder builder = new StringBuilder();
		double newReal = 0;
		double newImaginary = 0;
		
		String str2 = str.trim();
		str2 = str2.replace(" ", "");
		//if only the real part is given
		if (!str2.contains("i")) {
			newReal = Integer.parseInt(str2);
			return new Complex(newReal, newImaginary);
		}
		
		String temp1 = "";
		String temp2 = "";
		for (int i = 0; i < str2.length(); i++) {
			if (Character.isLetter(str2.charAt(i)) && str2.charAt(i) != 'i') {
				throw new IllegalArgumentException("String to be parsed must have a correct format!");
			}
			if (str2.charAt(i) == 'i') {
				if (i == 0 || builder.charAt(builder.length()-1) == '+'|| builder.charAt(builder.length()-1) == '-') {
					builder.append('1');
				}
				temp2 = builder.toString();
			} else if (str2.charAt(i) == '+' || (str2.charAt(i) == '-' && i != 0)) {
				temp1 = builder.toString();
				builder.setLength(0);
				builder.append(str2.charAt(i));
			} else {
				builder.append(str2.charAt(i));
			}
		}
		
		//check whether the temp strings are empty
		 if (temp1.length() == 0) {
			 newReal = 0;
		 } else {
			 newReal = Double.parseDouble(temp1);
		 }
		 	 
		newImaginary = Double.parseDouble(temp2);
		
		
		return new Complex(newReal, newImaginary);	
			
	}
	
	/**
	 * Opens the window and shows the calculated fractals using parallel calculation.
	 */
	public static void showParallel() {
		
		
		IFractalProducer producer = getParallelFractalProducer();
	
		FractalViewer.show(producer);
	}
	
	/**
	 * Opens the window and shows the calculated fractals using sequential calculation.
	 */
	public static void showSequential() {
		FractalViewer.show(getSequentialFractalProducer());
	}
	
	/**
	 * Method which calculates the Newton-Raphson fractal according to the given parameters.
	 * 
	 * @param reMin Minimum value on the real axis.
	 * @param reMax Maximum value on the real axis.
	 * @param imMin Minimum value on the imaginary axis.
	 * @param imMax Maximum value on the imaginary axis.
	 * @param width Screen width.
	 * @param height Screen height.
	 * @param m Number of divergency calculation attempts.
	 * @param ymin Y- line from which the field is updated (inclusive).
	 * @param ymax Y- line to which the field is updated (inclusive).
	 * @param data Array for storing the result.
	 */
	public static void calculate(double reMin, double reMax, double imMin, double imMax,
			int width, int height, int m, int ymin, int ymax, short[] data) {
		int offset = ymin * width;
		
		for(int y = ymin; y <= ymax; y++) {
			for(int x = 0; x < width; x++) {
				double cre = x * (reMax - reMin) / (width - 1) + reMin;
				double cim = (height - 1 - y) * (imMax - imMin) / (height - 1) + imMin;
				Complex c = new Complex(cre, cim);
				Complex zn = c;
				Complex zn1;
				
				double module;
				int iteration = 4096;
				
				do {
					Complex numerator = polynomial.apply(zn);
					Complex denominator = derived.apply(zn);
					Complex fraction = numerator.divide(denominator);
					zn1 = zn.sub(fraction);
					module = zn1.sub(zn).module();
					zn = zn1;
					iteration--;
					
				} while(iteration >= 0 && module > 0.001);
				
				int index = rootedPolynomial.indexOfClosestRootFor(zn1, 0.002);
				if (index == -1) {
					data[offset++] = 0;
				} else {
					data[offset++] = (short) index;
				}
			}
		}
	}
	
	/**
	 * Returns FractalProducer which sequentially calculates fractal value.
	 * 
	 * @return Sequential generator of the fractal.
	 */
	private static IFractalProducer getSequentialFractalProducer() {
		return new IFractalProducer() {
						
			
			@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
			int width, int height, long requestNo,
			IFractalResultObserver observer) {
		System.out.println("Započinjem izračune...");
		int m = 16*16*16;
		short[] data = new short[width*height];
		calculate(reMin, reMax, imMin, imMax, width, height, m, 0, height-1, data);
		System.out.println("Izračuni gotovi...");
		observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
		System.out.println("Dojava gotova...");
		}
			
		};
	}
	
	/**
	 * Returns FractalProducer which uses paralelization for fractal value calculation.
	 * 
	 * @return Parallel generator of the fractal.
	 */
	private static IFractalProducer getParallelFractalProducer() {
		return new IFractalProducer() {
			ExecutorService pool = Executors.newFixedThreadPool(Runtime
					.getRuntime().availableProcessors(), new DaemonicThreadFactory());
			
			@Override
			public void produce(double reMin, double reMax, double imMin, double imMax,
					int width, int height, long requestNo,
					IFractalResultObserver observer) {
				System.out.println("Započinjem izračune...");						
						
				int jobs = 8 * Runtime.getRuntime().availableProcessors();
				int blok = height / jobs;
				short[] data = new short[width*height];
				
				ArrayList<Future<?>> rezultati = new ArrayList<Future<?>>();
				Posao[] poslovi = new Posao[jobs];
				
				for (int i = 0; i < jobs; i++) {
					if (i == jobs - 1) {
						poslovi[i] = new Posao(reMin, reMax, imMin, imMax, width, height, i*blok, height - 1, data);
					
					} else {
						poslovi[i] = new Posao(reMin, reMax, imMin, imMax, width, height, i*blok, i*blok + blok, data);
					
					}
				}
				
				for (int i = 0; i < jobs; i++) {
					rezultati.add(pool.submit(poslovi[i]));
				}
				
				int j = 1;
				for(Future<?> f : rezultati) {
					System.out.println("Job " + (j++) + " done.");
					while(true) {
						try {
							f.get();
							break;
						} catch (Exception e) {
							continue;
						}
					}
				}
				
				
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(data, (short) (Newton.polynomial.order() + 1), requestNo);
				System.out.println("Dojava gotova...");
			}
			
			
		};
	}
	
	/**
	 * Class which calculates the correct pixel color according to the fractal values.
	 * Implements Runnable.
	 * 
	 * @author Goran Brlas
	 * @version 1.0
	 *
	 */
	public static class Posao implements Runnable {

		/**
		 * Minimum value on the real axis.
		 */
		double reMin;
		/**
		 * Maximum value on the real axis.
		 */
		double reMax;
		/**
		 * Minimum value on the imaginary axis.
		 */
		double imMin;
		/**
		 * Maximum value on the imaginary axis.
		 */
		double imMax;
		/**
		 * Screen width.
		 */
		int width;
		/**
		 * Screen height.
		 */
		int height;
		/**
		 * Y- line from which the field is updated (inclusive).
		 */
		int yMin;
		/**
		 * Y- line to which the field is updated (inclusive).
		 */
		int yMax;
		/**
		 * Array for storing the result.
		 */
		short[] data;
		
		
		/**
		 * Constructor which sets the default job values.
		 * @param reMin Minimum value on the real axis.
		 * @param reMax Maximum value on the real axis.
		 * @param imMin Minimum value on the imaginary axis.
		 * @param imMax Maximum value on the imaginary axis.
		 * @param width Screen width.
		 * @param height Screen height.
		 * @param yMin Y- line from which the field is updated (inclusive).
		 * @param yMax Y- line to which the field is updated (inclusive).
		 * @param data Array for storing the result.
		 */
		public Posao(double reMin, double reMax, double imMin, double imMax,
				int width, int height, int yMin, int yMax, short[] data) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.data = data;
		}


		@Override
		public void run() {
			calculate(reMin, reMax, imMin, imMax, width, height, 4096, yMin, yMax, data);
		}
	}
}
