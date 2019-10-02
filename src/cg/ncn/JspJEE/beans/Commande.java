package cg.ncn.JspJEE.beans;

public class Commande {

    private Long id = 0l;

    public Commande( Long id, Client client, String date, Double montant, String modePayment, String statutPayment,
            String modeLIvraison, String statutLIvraison ) {
        super();
        this.id = id;
        this.client = client;
        this.date = date;
        this.montant = montant;
        this.modePayment = modePayment;
        this.statutPayment = statutPayment;
        this.modeLIvraison = modeLIvraison;
        this.statutLIvraison = statutLIvraison;
    }

    private Client client;

    private String date;
    private Double montant;
    private String modePayment;
    private String statutPayment;
    private String modeLIvraison;
    private String statutLIvraison;

    public Commande() {

    }

    public Client getClient() {
        return client;
    }

    public Commande( Client client, String date, Double montant, String modePayment, String statutPayment,
            String modeLIvraison, String statutLIvraison ) {
        super();
        this.client = client;
        this.date = date;
        this.montant = montant;
        this.modePayment = modePayment;
        this.statutPayment = statutPayment;
        this.modeLIvraison = modeLIvraison;
        this.statutLIvraison = statutLIvraison;
    }

    public void setClient( Client client ) {
        this.client = client;
    }

    public String getDate() {
        return date;
    }

    public void setDate( String date2 ) {
        this.date = date2;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant( Double montant ) {
        this.montant = montant;
    }

    public String getModePayment() {
        return modePayment;
    }

    public void setModePayment( String modePayment ) {
        this.modePayment = modePayment;
    }

    public String getStatutPayment() {
        return statutPayment;
    }

    public void setStatutPayment( String statutPayment ) {
        this.statutPayment = statutPayment;
    }

    public String getModeLIvraison() {
        return modeLIvraison;
    }

    public void setModeLIvraison( String modeLIvraison ) {
        this.modeLIvraison = modeLIvraison;
    }

    public String getStatutLIvraison() {
        return statutLIvraison;
    }

    public void setStatutLIvraison( String statutLIvraison ) {
        this.statutLIvraison = statutLIvraison;
    }

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

}
