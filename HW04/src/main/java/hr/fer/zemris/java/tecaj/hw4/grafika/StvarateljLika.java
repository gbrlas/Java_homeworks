package hr.fer.zemris.java.tecaj.hw4.grafika;

/**
 * Sucelje koje definira funkcije kojima se stvaraju i crtaju geometrijski likovi.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public interface StvarateljLika {
	/**
	 * Vraca naziv geometrijskog lika u kojoj je ova klasa ugnjezdjena.
	 * @return Naziv geometrijskog lika.
	 */
	String nazivLika();
	/**
	 * Stvara novi geometrijski lik iz zadanih parametara.
	 * @param parametri Parametri zadani preko stringa iz kojih se stvara novi geometrijski lik.
	 * @return Novo stvoreni geometrijski lik.
	 */
	GeometrijskiLik stvoriIzStringa (String parametri);
}
