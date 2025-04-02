package Entities;



/**
 *
 */
public abstract class Boisson {

	/**
	 * Default constructor
	 */
	public Boisson() {
	}

	private String nom;
	private Double contenance;
	private Double prix;
	private Double degreAlcool;
	private Double degreSucre;

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

}