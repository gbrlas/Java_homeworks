package hr.fer.zemris.java.tecaj.hw1;

/**
 * Provides basic binary tree functions.
 * 
 * @author Goran Brlas - 0036476746
 *
 */
public class ProgramStabla {
	
	static class CvorStabla {
		CvorStabla lijevi;
		CvorStabla desni;
		String podatak;
	}
	
	/**
	 * Method called when starting the program.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		CvorStabla cvor = null;
		
		cvor = ubaci(cvor, "Jasna");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci(cvor, "Ivana");
		cvor = ubaci(cvor, "Anamarija");
		cvor = ubaci(cvor, "Vesna");
		cvor = ubaci(cvor, "Kristina");
		
		System.out.println("Ispisujem stablo inorder: ");
		ispisiStablo(cvor);
		
		int vel = velicinaStabla(cvor);
		System.out.println("Stablo sadrzi elemenata: " + vel);
		
		boolean pronadjen = sadrziPodatak(cvor, "Ivana");
		System.out.println("Trazeni podatak je pronadjen: " + pronadjen);
	}
	
	/**
	 * Returns boolean representation of whether the searched for node is part of the tree.
	 * @param korijen Starting root of the tree.
	 * @param podatak String we are searching for in the tree.
	 * @return Returns true if the searched for string is in the tree, otherwise returns false.
	 */
	static boolean sadrziPodatak (CvorStabla korijen, String podatak) {
		if (korijen == null)
			return false;
		else if (korijen.podatak == podatak)
			return true;
		else {
			return (sadrziPodatak(korijen.lijevi, podatak) || sadrziPodatak(korijen.desni, podatak));
		}
	}
	
	/**
	 * Returns the size of the binary tree.
	 * @param cvor Starting root of the tree.
	 * @return Returns the size of the binary tree.
	 */
	static int velicinaStabla (CvorStabla cvor) {
		if (cvor == null)
			return 0;
		else 
			return 1 + velicinaStabla(cvor.lijevi) + velicinaStabla(cvor.desni);	
	}
	
	/**
	 * Adds new nodes to the binary tree.
	 * @param korijen Starting root of the tree.
	 * @param podatak String to be inserted into the tree
	 * @return Returns the starting root of the tree.
	 */
	static CvorStabla ubaci (CvorStabla korijen, String podatak) {
		if (korijen == null) {
			CvorStabla novi = new CvorStabla();
			novi.lijevi = novi.desni = null;
			novi.podatak = podatak;
			return novi;
		} 
		else if (podatak.compareTo(korijen.podatak) <= 0) 
			korijen.lijevi = ubaci(korijen.lijevi, podatak);
		else 
			korijen.desni = ubaci(korijen.desni, podatak);
		
		return korijen;
	}
	
	/**
	 * Traverses the tree in order and prints the string values.
	 * @param cvor Starting root of the tree.
	 */
	static void ispisiStablo (CvorStabla cvor) {
		if (cvor == null)
			return;
		
		ispisiStablo(cvor.lijevi);
		System.out.println(cvor.podatak);
		ispisiStablo(cvor.desni);
	}

}
