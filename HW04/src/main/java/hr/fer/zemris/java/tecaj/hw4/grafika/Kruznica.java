package hr.fer.zemris.java.tecaj.hw4.grafika;

/**
 * Klasa koja predstavlja kruznicu.
 * Nasljedjuje hr.fer.zemris.java.tecaj.hw4.grafika.Elipsa.
 * 
 * @author Goran Brlas
 * @version 1.0
 * 
 */
public class Kruznica extends Elipsa {
	
	/**
	 * Konstruktor koji primljene parametre prosljedjuje konstruktoru elipse (mala i velika poluos u ovom
	 * slucaju jednake su polumjeru kruznice).
	 * @param x Lokacija sredista u odnosu na x-os.
	 * @param y Lokacija sredista u odnosu na y-os.
	 * @param r Polumjer kruznice.
	 */
	public Kruznica (int x, int y, int r) {
		super(x, y, r, r);
	}
	
	/**
	 * Sucelje stvaratelja koje stvara novu kruznicu.
	 */
	public static final StvarateljLika STVARATELJ = new KruznicaStvaratelj ();
	
	/**
	 * Klasa koja sluzi za stvaranje novih kruznica.
	 * Implementira hr.fer.zemris.java.tecaj.hw4.grafika.StvarateljLika.
	 * 
	 * @author Goran Brlas
	 * @version 1.0
	 *
	 */
	private static class KruznicaStvaratelj implements StvarateljLika {

		@Override
		public String nazivLika() {
			return "KRUG";
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
				int r1 = Integer.parseInt(temp2[2]);
				
				return new Kruznica(x1, y1, r1);
			
			} catch (Exception exception) {
				throw new IllegalArgumentException(parametri);
			}
		}
		
	}
}
