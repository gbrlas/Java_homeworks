package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Klasa koja predstavlja liniju.
 * Nasljedjuje hr.fer.zemris.java.tecaj.hw4.grafika.GeometrijskiLik.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Linija extends GeometrijskiLik {
	/**
	 * Lokacija pocetne tocke u odnosu na x-os.
	 */
	private int x1;
	/**
	 * Lokacija pocetne tocke u odnosu na y-os.
	 */
	private int y1;
	/**
	 * Lokacija zavrsne tocke u odnosu na x-os.
	 */
	private int x2;
	/**
	 * Lokacija zavrsne tocke u odnosu na y-os.
	 */
	private int y2;
	
	
	/**
	 * Sucelje stvaratelja koje stvara novu liniju.
	 */
	public static final StvarateljLika STVARATELJ = new LinijaStvaratelj ();
	
	/**
	 * Klasa koja sluzi za stvaranje novih linija.
	 * Implementira hr.fer.zemris.java.tecaj.hw4.grafika.StvarateljLika.
	 * 
	 * @author Goran Brlas
	 * @version 1.0
	 *
	 */
	private static class LinijaStvaratelj implements StvarateljLika {

		@Override
		public String nazivLika() {
			return "LINIJA";
		}

		@Override
		public GeometrijskiLik stvoriIzStringa(String parametri) throws IllegalArgumentException {
			String temp = parametri.trim();
			String[] temp2 = temp.split("\\s+");
			
			if (temp2.length != 4) {
				throw new IllegalArgumentException(parametri);
			}
			
			try {
				int x1 = Integer.parseInt(temp2[0]);
				int y1 = Integer.parseInt(temp2[1]);
				int x2 = Integer.parseInt(temp2[2]);
				int y2 = Integer.parseInt(temp2[3]);
				
				return new Linija(x1, y1, x2, y2);
				
			} catch (Exception exception) {
				throw new IllegalArgumentException(parametri);
			}
		}
		
		
	}
	
	/**
	 * Konstruktor koji stvara novu liniju iz zadanih parametara.
	 * @param x1 Lokacija pocetne tocke u odnosu na x-os.
	 * @param y1 Lokacija pocetne tocke u odnosu na y-os.
	 * @param x2 Lokacija zavrsne tocke u odnosu na x-os.
	 * @param y2 Lokacija zavrsne tocke u odnosu na y-os.
	 */
	public Linija (int x1, int y1, int x2, int y2) {		
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}
	
	@Override
	public boolean sadrziTocku (int x, int y) {
		if (x < this.x1 || x >= this.x2) {
			return false;
		}
		if (y < this.y1 || y >= this.y2) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public void popuniLik (Slika slika) {
		if (x1 == x2) {
			if (y1 > y2) {
				int temp = y1;
				y1 = y2;
				y2 = temp;
			}
			for (int y = y1; y < y2; y++) {
				if (y < slika.getVisina() && y >= 0) {
					slika.upaliTocku(x1, y);
				}
			}
		} else if (y1 == y2) {
			if (x1 > x2) {
				int temp = x1;
				x1 = x2;
				x2 = temp;
			}
			for (int x = x1; x < x2; x++) {
				if (x < slika.getSirina() && x >= 0) {
					slika.upaliTocku(x, y1);
				}
			}
		//ako je linija kosa
		} else {
			
			
			
			if (x1  < x2) {
				lijevoDesno(slika);
			} else {
				desnoLijevo(slika);
			}
		}
		
	}
	
	/**
	 * Pomocna funkcija koja iscrtava liniju kojoj je pocetna tocka lijevo od zavrsne i y1 nije jednak y2.
	 * Funkcija implementira Bresenhamov algoritam.
	 * @param slika "Platno" na kojem crtamo geometrijska tijela.
	 */
	private void lijevoDesno (Slika slika) {
		double deltax = x2 - x1;
		double deltay = y2 - y1;
		double error = 0;
		double deltaerr = Math.abs(deltay / deltax);
		int y = y1;
		
		for (int x = x1; x < x2; x++) {
			if (x < slika.getSirina() || y < slika.getVisina()) {
				slika.upaliTocku(x, y);
				error += deltaerr;
				
				while (error > 0.5) {
					slika.upaliTocku(x, y);
					y += Math.signum(y2 - y1);
					error -= 1.0;
				}
			}
		}
	}
	
	/**
	 * Pomocna funkcija koja iscrtava liniju kojoj je pocetna tocka desno od zavrsne i y1 nije jednak y2.
	 * Funkcija implementira Bresenhamov algoritam.
	 * @param slika "Platno" na kojem crtamo geometrijska tijela.
	 */
	private void desnoLijevo (Slika slika) {
		double deltax = x2 - x1;
		double deltay = y2 - y1;
		double error = 0;
		double deltaerr = Math.abs(deltay / deltax);
		int y = y1;
		
		for (int x = x1; x > x2; x--) {
			if (x < slika.getSirina() || y < slika.getVisina()) {
				slika.upaliTocku(x, y);
				error += deltaerr;
				
				while (error > 0.5) {
					slika.upaliTocku(x, y);
					y += Math.signum(y2 - y1);
					error -= 1.0;
				}
			}
		}
	}
	
	
}
