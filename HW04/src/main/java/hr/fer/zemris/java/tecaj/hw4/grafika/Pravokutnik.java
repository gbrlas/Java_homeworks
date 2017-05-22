package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Klasa koja predstavlja pravokutnik.
 * Nasljedjuje hr.fer.zemris.java.tecaj.hw4.grafika.GeometrijskiLik.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Pravokutnik extends GeometrijskiLik {
	/**
	 * Lokacija gornjeg lijevog vrha u odnosu na x-os.
	 */
	private int x;
	/**
	 * Lokacija gornjeg lijevog vrha u odnosu na y-os.
	 */
	private int y;
	/**
	 * Sirina pravokutnika.
	 */
	private int sirina;
	/**
	 * Visina pravokutnika.
	 */
	private int visina;
	
	/**
	 * Sucelje stvaratelja koje stvara novu elipsu.
	 */
	public static final StvarateljLika STVARATELJ = new PravokutnikStvaratelj ();
	
	
	/**
	 * Klasa koja sluzi za stvaranje novih pravokutnika.
	 * Implementira hr.fer.zemris.java.tecaj.hw4.grafika.StvarateljLika.
	 * 
	 * @author Goran Brlas
	 * @version 1.0
	 *
	 */
	private static class PravokutnikStvaratelj implements StvarateljLika {

		@Override
		public String nazivLika() {
			return "PRAVOKUTNIK";
		}

		@Override
		public GeometrijskiLik stvoriIzStringa(String parametri) {
			String temp = parametri.trim();
			String[] temp2 = temp.split("\\s+");
			
			if (temp2.length != 4) {
				throw new IllegalArgumentException(parametri);
			}
			
			try {
				int x1 = Integer.parseInt(temp2[0]);
				int y1 = Integer.parseInt(temp2[1]);
				int sirina = Integer.parseInt(temp2[2]);
				int visina = Integer.parseInt(temp2[3]);
				
				return new Pravokutnik(x1, y1, sirina, visina);
				
			} catch (Exception exception) {
				throw new IllegalArgumentException(parametri);
			}
		}
		
		
	}
	
	/**
	 * Konstruktor koji stvara novi pravokutnik iz zadanih parametara.
	 * @param x Lokacija gornjeg lijevog vrha u odnosu na x-os.
	 * @param y Lokacija gornjeg lijevog vrha u odnosu na y-os.
	 * @param sirina Sirina pravokutnika.
	 * @param visina Visina pravokutnika.
	 */
	public Pravokutnik (int x, int y, int sirina, int visina) {
		if (sirina < 0 || visina < 0) {
			throw new IllegalArgumentException("All arguments must be positive!");
		}
		
		this.x = x;
		this.y = y;
		this.sirina = sirina;
		this.visina = visina;
	}
	
	@Override
	public boolean sadrziTocku (int x, int y) {
		if (x < this.x || x >= this.x + sirina) {
			return false;
		}
		if (y < this.y || y >= this.y + visina) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public void popuniLik (Slika slika) {
		
		for (int y = this.y, yEnd = this.y + visina; y < yEnd; y++) {
			for (int x = this.x, xEnd = this.x + sirina; x < xEnd; x++) {
				if (x >= slika.getSirina() || y >= slika.getVisina() || x < 0 || y < 0) {
					continue;
				} else {
					slika.upaliTocku(x, y);
				}
			}
		}
	}
}
