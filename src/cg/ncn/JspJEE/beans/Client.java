package cg.ncn.JspJEE.beans;

public class Client {

    private Long   id = 0l;
    private String nom;
    private String prenom;
    private String adresse;
    private String numero;
    private String email;
    private String image;

    public Client() {
    }

    public Client( String nom, String prenom, String adresse, String numero, String email, String image ) {
        super();
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.numero = numero;
        this.email = email;
        this.image = image;
    }

    public Client( Long id, String nom, String prenom, String adresse, String numero, String email,
            String image ) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.numero = numero;
        this.email = email;
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public void setNom( String nom ) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom( String prenom ) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse( String adresse ) {
        this.adresse = adresse;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero( String numero ) {
        this.numero = numero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage( String image ) {
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Client [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", adresse=" + adresse + ", numero="
                + numero + ", email=" + email + ", image=" + image + "]";
    }

}
