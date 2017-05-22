package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Klasa koja predstavlja elipsu.
 * Nasljedjuje hr.fer.zemris.java.tecaj.hw4.grafika.GeometrijskiLik.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Elipsa extends GeometrijskiLik {
	/**
	 * Lokacija sredista u odnosu na x-os.
	 */
	private int x0;
	/**
	 * Lokacija sredista u odnosu na y-os.
	 */
	private int y0;
	/**
	 * Duljina velike poluosi.
	 */
	private int a;
	/**
	 * Duljina male poluosi.
	 */
	private int b;
	
	/**
	 * Sucelje stvaratelja koje stvara novu elipsu.
	 */
	public static final StvarateljLika STVARATELJ = new ElipsaStvaratelj ();
	
	/**
	 * Klasa koja sluzi za stvaranje novih elipsa.
	 * Implementira hr.fer.zemris.java.tecaj.hw4.grafika.StvarateljLika.
	 * 
	 * @author Goran Brlas
	 * @version 1.0
	 *
	 */
	private static class ElipsaStvaratelj implements StvarateljLika {

		@Override
		public String nazivLika() {
			return "ELIPSA";
		}

		@Override
		public GeometrijskiLik stvoriIzStringa(String parametri) throws IllegalArgumentException{
			String temp = parametri.trim();
			String[] temp2 = temp.split("\\s+");
			
			if (temp2.length != 4) {
				throw new IllegalArgumentException("Illegal parameters passed!");
			}
			
			try {
				int x1 = Integer.parseInt(temp2[0]);
				int y1 = Integer.parseInt(temp2[1]);
				int a = Integer.parseInt(temp2[2]);
				int b = Integer.parseInt(temp2[3]);
				
				return new Elipsa(x1, y1, a, b);
				
			} catch (Exception exception) {
				throw new IllegalArgumentException(parametri);
			}
			
			
		}
		
		
	}
	
	/**
	 * Konstruktor koji stvara novu elipsu iz zadanih parametara.
	 * @param x Lokacija sredista u odnosu na x-os.
	 * @param y Lokacija sredista u odnosu na y-os.
	 * @param a Duljina velike poluosi.
	 * @param b Duljina male poluosi.
	 */
	public Elipsa (int x, int y, int a, int b) {
		if (a < 0 || b < 0) {
			throw new IllegalArgumentException("Poluosi moraju biti pozitivne!");
		}
		
		this.x0 = x;
		this.y0 = y; 
		this.a = a;
		this.b = b;
	}
	
	/**
	 * Funkcija koja racuna sadrzi li elipsa zadanu tocku te vraca boolean kao rezultat.
	 */
	public boolean sadrziTocku(int x, int y) {
		boolean test = false;
		
		double prvi = Math.pow((x-x0), 2) / Math.pow(a, 2);
		double drugi = Math.pow((y-y0), 2) / Math.pow(b, 2);
		
		if (prvi + drugi <= 1) {
			test = true;
		}
		
		return test;
	};
	
	@Override
	public void popuniLik (Slika slika) {
		for (int y = y0 - b, yend = y0 + b; y < yend; y++) {
			for (int x = x0 - a, xend = x0 + a; x < xend; x++) {
				if (x < slika.getSirina() && y < slika.getVisina() && sadrziTocku(x, y) && x >= 0 && y >= 0) {
					slika.upaliTocku(x, y);
				}
			}
		}
	}
	
}
