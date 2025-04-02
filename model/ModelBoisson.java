package model;

import entities.*;

import java.util.HashMap;
import java.util.Map;
public class ModelBoisson {

	public static HashMap<Boisson,Integer> stockBoissons=new HashMap<>();
	
	
	public boolean existe(String nom ){
		if (stringToBoisson(nom)==null) return false;
		return stringToBoisson(nom).getNom().equals(nom);
		
	}
	
	public Boisson stringToBoisson(String nom){
		for (Map.Entry<Boisson, Integer> entry : stockBoissons.entrySet()) {
		   if  (entry.getKey().getNom().equals(nom)) return entry.getKey();}
		return null;
	}
	public void ajouterBoisson(String nom, int qte){
		Boisson boisson=stringToBoisson(nom);
		if (boisson==null) {return;}
		int qteStock=stockBoissons.get(boisson);
		stockBoissons.put(boisson, qteStock+qte);
		
		
	}
	
	public void ajouterBoisson(Boisson boisson){
		Boisson b=stringToBoisson(boisson.getNom());
		if (b!=null) {return;}
		stockBoissons.put(boisson, 0);
		
	}
	public void retirBoisson(String nom, int qte){
		Boisson boisson=stringToBoisson(nom);
		if (boisson==null) {return;}
		int qteStock=stockBoissons.get(boisson);
		stockBoissons.put(boisson, qteStock-qte);
		}
	
	
	public static HashMap<Boisson, Integer> getStockBoissons() {
		return stockBoissons;
	}

	public static void setStockBoissons(HashMap<Boisson, Integer> stockBoissons) {
		ModelBoisson.stockBoissons = stockBoissons;
	}

	public Boolean estDisponible(String nom,int qte){
		
		Boisson boisson=stringToBoisson(nom);
		if (boisson==null) {return false;}
		return  stockBoissons.get(boisson) <qte;
		
	}
}
