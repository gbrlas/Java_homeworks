package hr.fer.zemris.java.tecaj.hw4.grafika.demo;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.tecaj.hw4.collections.SimpleHashtable;
import hr.fer.zemris.java.tecaj.hw4.grafika.Elipsa;
import hr.fer.zemris.java.tecaj.hw4.grafika.GeometrijskiLik;
import hr.fer.zemris.java.tecaj.hw4.grafika.Kruznica;
import hr.fer.zemris.java.tecaj.hw4.grafika.Kvadrat;
import hr.fer.zemris.java.tecaj.hw4.grafika.Linija;
import hr.fer.zemris.java.tecaj.hw4.grafika.Pravokutnik;
import hr.fer.zemris.java.tecaj.hw4.grafika.StvarateljLika;
import hr.fer.zemris.java.tecaj_3.prikaz.PrikaznikSlike;
import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Program koji proizvodi podrzane geometrijske likove te ih iscrtava.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Crtalo {
	
	/**
	 * Glavna metoda koja prima 3 argumenta: put do datoteke sa definicijama geometrijskih likova, sirinu 
	 * te visinu slike koja se treba stvoriti.
	 * @param args Put do datoteke, sirina slike, visina slike.
	 */
	public static void main (String[] args) {
		int index = 0;
		
		try {
			SimpleHashtable stvaratelji = podesi(Linija.class, Pravokutnik.class, Kvadrat.class, Elipsa.class, Kruznica.class); 
			String[] definicije = Files.readAllLines(Paths.get(args[0]), StandardCharsets.UTF_8).toArray(new String[0]);
		
		
		GeometrijskiLik[] likovi = new GeometrijskiLik[definicije.length];
		
		for (String objekt : definicije) {
			String lik = objekt.substring(0, objekt.indexOf(" "));
			String parametri = objekt.substring(objekt.indexOf(" "));
			StvarateljLika stvaratelj = (StvarateljLika) stvaratelji.get(lik);
			likovi[index] = stvaratelj.stvoriIzStringa(parametri);
			index++;
		}
		
		Slika slika = new Slika(Integer.parseInt(args[1].trim()), Integer.parseInt(args[2].trim()));
		
		for (GeometrijskiLik lik : likovi) {
			lik.popuniLik(slika);
		}
		
		PrikaznikSlike.prikaziSliku(slika);
		
		} catch (Exception exception) {
			System.err.println("Greska u " + (index + 1) + ". liniji datoteke: " + "\"" + exception.getMessage().trim() + "\"");
		}
	}
	
	@SafeVarargs
	/**
	 * Stvara hash tablicu na temelju razreda koji se mogu stvarati/iscrtavati.
	 * @param razredi Svi razredi koji nasljedjuju geometrijski lik te se mogu nacrtati.
	 * @return Hash tablica koja ima naziv lika kao kljuc te stvaratelj zadanog lika kao vrijednost.
	 */
	private static SimpleHashtable podesi (Class<? extends GeometrijskiLik> ...razredi) {
		SimpleHashtable stvaratelji = new SimpleHashtable();
		
		for (Class<? extends GeometrijskiLik> razred : razredi) {
			try {
				Field field = razred.getDeclaredField("STVARATELJ");
				StvarateljLika stvaratelj = (StvarateljLika) field.get(null);
				stvaratelji.put(stvaratelj.nazivLika(), stvaratelj);
			} catch (Exception exception) {
				throw new RuntimeException("Nije moguće doći do stvaratelja za razred " + razred.getName() + ".", exception);
			}
		}
		
		return stvaratelji;
	}
}
