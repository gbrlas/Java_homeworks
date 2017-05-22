package hr.fer.zemris.java.tecaj.hw1;

/**
 * Provides basic list functions.
 * 
 * @author Goran Brlas - 0036476746
 *
 */
public class ProgramListe {
	
	/**
	 * An element of the list. Contains a string and a reference to the next element in the list.
	 * 
	 * @author Goran Brlas
	 *
	 */
	static class CvorListe {
		CvorListe sljedeci;
		String podatak;
	}
	
	/**
	 * Method called when starting the program.
	 * @param args Command line arguments.
	 */
	public static void main (String[] args)	{
		CvorListe cvor = null;
		
		cvor = ubaci(cvor, "Jasna");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci(cvor, "Ivana");
		
		System.out.println("Ispisujem listu uz originalan poredak: ");
		ispisiListu(cvor);
		
		cvor = sortirajListu(cvor);
		
		System.out.println("Ispisujem listu nakon sortiranja: ");
		ispisiListu(cvor);
		
		int vel = velicinaListe(cvor);
		System.out.println("Lista sadrzi elemenata: " + vel);
	}
	
	/**
	 * Calculates list length.
	 * @param cvor First element of the list.
	 * @return Returns list length.
	 */
	static int velicinaListe (CvorListe cvor) {
		int size = 0;
		
		while (cvor != null) {
			size++;
			cvor = cvor.sljedeci;
		}
		
		return size;
	}
	
	/**
	 * Inserts new element at the end of the list.
	 * @param prvi First element in the list.
	 * @param podatak Parameter of the new list element.
	 * @return
	 */
	static CvorListe ubaci (CvorListe prvi, String podatak) {
		CvorListe novi = new CvorListe();
		CvorListe temp = prvi;
		
		novi.podatak = podatak;
		novi.sljedeci = null;
		
		if (prvi == null)
			prvi = novi;
		else {
			while (temp.sljedeci != null)
				temp = temp.sljedeci;
			
			temp.sljedeci = novi;
		}
		
		return prvi;
	}
	
	/**
	 * Prints out all the elements in the list.
	 * @param cvor First element of the list.
	 */
	static void ispisiListu (CvorListe cvor) {
		
		while (cvor != null) {
			System.out.print(cvor.podatak + " ");
			cvor = cvor.sljedeci;
		}
		
		System.out.println();
	}
	
	/**
	 * Sorts the list.
	 * @param cvor First element in the list.
	 * @return Returns first element of the sorted list.
	 */
	static CvorListe sortirajListu(CvorListe cvor) {
		CvorListe max, min, mid;
		
		min = mid = max = null;
		
		if (cvor.podatak.compareTo(cvor.sljedeci.podatak) <= 0) {
			if (cvor.podatak.compareTo(cvor.sljedeci.sljedeci.podatak) <= 0) {
				min = cvor;
				if (cvor.sljedeci.podatak.compareTo(cvor.sljedeci.sljedeci.podatak) <= 0) {
					mid = cvor.sljedeci;
					max = cvor.sljedeci.sljedeci;
				} else {
					mid = cvor.sljedeci.sljedeci;
					max = cvor.sljedeci;
				}
			}
		} else {
			if (cvor.sljedeci.podatak.compareTo(cvor.sljedeci.sljedeci.podatak) <= 0) {
				min = cvor.sljedeci;
				if (cvor.podatak.compareTo(cvor.sljedeci.sljedeci.podatak) <= 0) {
					mid = cvor;
					max = cvor.sljedeci.sljedeci;
				} else {
					mid = cvor.sljedeci.sljedeci;
					max = cvor;
				}
			}
		}
		
		max.sljedeci = null;
		mid.sljedeci = max;
		min.sljedeci = mid;
		
		return min;		
	}
}
