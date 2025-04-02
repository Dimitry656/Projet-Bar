package Entities;



/**
 *
 */
public abstract class Boisson {
	private String nom;
	private Double contenance; // en cl
	private Double prix;
	private Double degreAlcool;
	private Double degreSucre;

	/**
	 * Default constructor
	 */
	public Boisson(String nom, double contenance, Double prix, Double degreAlcool, Double degreSucre) {
		this.nom = nom;
		this.contenance = contenance;
		this.prix = prix;
		this.degreAlcool = degreAlcool;
		this.degreSucre = degreSucre;
	}




	/**
	 * @return double contenance
	 */
	public double getContenance() {
		return this.contenance;
	}

	/**
	 * @return String nom
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * @return Double degreAlcool
	 */
	public Double getDegreAlcool() {
		return this.degreAlcool;
	}

	/**
	 * @return Double degreSucre
	 */
	public Double getDegreSucre() {
		return this.degreSucre;
	}

	/**
	 * @return Double prix
	 */
	public Double getPrix() {
		return this.prix;
	}

	/**
	 * @return double contenance
	 */
	public double setContenance( Double contenance) {
		this.contenance = contenance;
		return this.contenance;
	}

	/**
	 * @return String nom
	 */
	public String setNom(String nom ) {
		this.nom = nom;
		return this.nom;
	}

	/**
	 * @return Double degreAlcool
	 */
	public Double setDegreAlcool(Double degreAlcool) {
		this.degreAlcool = degreAlcool;
		return this.degreAlcool;
	}

	/**
	 * @return double degreSucre

	 */
	public double setDegreSucre(double degreSucre) {
		this.degreSucre =degreSucre;
		return this.degreSucre;
	}

	/**
	 * @return Double prix
	 */
	public Double setPrix(double prix) {
		this.prix =prix;
		return this.prix;
	}

	public double getAlcoolQuantity() {
		return this.degreAlcool*this.contenance;
	}

	public double getSucreQuantity() {
		return this.degreSucre*this.contenance;
	}

}