package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Apstraktna klasa koja služi kao nadklasa za razne geometrijske oblike.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public abstract class GeometrijskiLik {
	/**
	 * Govori nam pripada li zadana tocka nekom geometrijskom liku
	 * @param x Lokacija točke u odnosu na x-os.
	 * @param y Lokacija točke u odnosu na y-os.
	 * @return Boolean koji predstavlja postojanje predane tocke u odredjenom geometrijskom liku.
	 */
	public abstract boolean sadrziTocku (int x, int y);
	
	/**
	 * Na "platnu" crta lik tako da pali točke koje pripadaju tom liku. 
	 * Prolazi kroz sve tocke na platnu i provjerava sadrzi li lik zadanu tocku.
	 * @param slika "Platno" na kojem crtamo tako da palimo i gasimo tocke na zadanim lokacijama.
	 */
	public void popuniLik (Slika slika) {
		for (int y = 0, visina = slika.getVisina(), sirina = slika.getSirina(); y < visina; y++) {
			for (int x = 0; x < sirina; x++) {
				if (sadrziTocku(x, y)) {
					slika.upaliTocku(x, y);
				}
			}
		}
	}
}
