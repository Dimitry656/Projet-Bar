package java.view;

import entities.*;
import model.ModelBoisson;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// cette classe permet d'int�ragir avec l'utilisateur pour toute saisie ou affichage concernant 
// ici on cr�e un objet ModelBoisson pour int�ragir avec la table des boissons du mod�le
// cette fa�on de faire s'inspire du mod�le MVC, et permet une bonne structuration du mod�le.
public class InterfaceBoisson {

public void ajouterNouvelleBoisson(){
	// (String nom, double prix, double contenance, double degreSucre)
	Scanner scanner = new Scanner(System.in);
	ModelBoisson model=new ModelBoisson();
	System.out.println("Entrez le nom de la nouvelle boisson");
	String nom=scanner.next();
	
	Boolean existe=model.existe(nom);
	if (existe){System.out.println("Boisson d�j� existante");return ;}
	System.out.println("Entrez le prix");
	double prix=scanner.nextDouble();
	System.out.println("Entrez contenance");
	double contenance=scanner.nextDouble();
	System.out.println("Entrez degreSucre");
	double degreSucre=scanner.nextDouble();
	System.out.println("Entrez degre alcool");
	double degreAl=scanner.nextDouble();
	
		model.ajouterBoisson(new Boisson(nom, prix, contenance,  degreSucre,degreAl));
	
	
}

public void ajouterStockBoisson(){
	// (String nom, double prix, double contenance, double degreSucre)
	Scanner scanner = new Scanner(System.in);
	ModelBoisson model=new ModelBoisson();
	System.out.println("Entrez le nom de la boisson");
	String nom=scanner.next();
	
	Boolean existe=model.existe(nom);
	if (!existe){System.out.println("Boisson n'existe pas ");return ;}
	System.out.println("Entrez la quantite");
	double qte=scanner.nextDouble();
	model.ajouterBoisson(nom,(int)qte);
	
		
	
	
}
public void afficheStock(){
	HashMap<Boisson,Integer>  stockBoissons=ModelBoisson.getStockBoissons();
	for (Map.Entry<Boisson, Integer> entry : stockBoissons.entrySet()) {
	   System.out.println(entry.getKey().getNom()+":"+entry.getValue());}}


public void supprimerBoisson(){}

}
