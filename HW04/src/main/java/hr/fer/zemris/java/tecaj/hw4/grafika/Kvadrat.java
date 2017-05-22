package hr.fer.zemris.java.tecaj.hw4.grafika;

/**
 * Klasa koja prestavlja kvadrat.
 * Nasljedjuje hr.fer.zemris.java.tecaj.hw4.grafika.Pravokutnik.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Kvadrat extends Pravokutnik {
	
	/**
	 * Konstruktor koji primljene parametre prosljedjuje konstruktoru pravokutnika (duljina i sirina u ovom
	 * slucaju su jednake).
	 * @param x Lokacija gornjeg lijevog vrha u odnosu na x-os.
	 * @param y Lokacija gornjeg desnog vrha u odnosu na y-os.
	 * @param duljina Duljina stranice kvadrata.
	 */
	public Kvadrat (int x, int y, int duljina) {
		super(x, y, duljina, duljina);
	}
	
	/**
	 * Sucelje stvaratelja koje stvara novi kvadrat.
	 */
	public static final StvarateljLika STVARATELJ = new KvadratStvaratelj ();
	
	/**
	 * Klasa koja sluzi za stvaranje novih kvadrata.
	 * Implementira hr.fer.zemris.java.tecaj.hw4.grafika.StvarateljLika.
	 * 
	 * @author Goran Brlas
	 * @version 1.0
	 *
	 */
	private static class KvadratStvaratelj implements StvarateljLika {

		@Override
		public String nazivLika() {
			return "KVADRAT";
		}

		@Override
		public GeometrijskiLik stvoriIzStringa(String parametri) throws IllegalArgumentException {
			String temp = parametri.trim();
			String[] temp2 = temp.split("\\s+");
			
			if (temp2.length != 3) {
				throw new IllegalArgumentException(parametri);
			}
			
			try {
				int x1 = Integer.parseInt(temp2[0]);
				int y1 = Integer.parseInt(temp2[1]);
				int duljina = Integer.parseInt(temp2[2]);
				
				return new Kvadrat(x1, y1, duljina);
				
			} catch (Exception exception) {
				throw new IllegalArgumentException(parametri);
			}
		}
		
		
	}
}
