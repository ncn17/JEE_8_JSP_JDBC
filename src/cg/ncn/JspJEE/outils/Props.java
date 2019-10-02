package cg.ncn.JspJEE.outils;

public class Props {

    public static final String BDD_CLIENT     = "listeClients";
    public static final String BDD_COMMANDE   = "listeCmd";
    public static final int    TAILLE_TAMPON  = 10240;
    public static final String FORMAT_DATE    = "dd/MM/yyy HH:mm:ss";
    public static final String PREFIX_IMG     = "ncn_img_0";
    public static final String FILE_PATH      = "/servers/uploads/";
    public final static String MYSQL_USER     = "atir17";
    public final static String MYSQL_PASS     = "XCLERCncn#12345";
    public final static String MYSQL_DRIVER   = "com.mysql.cj.jdbc.Driver";
    public final static String MYSQL_HOST     = "127.0.0.1";
    public final static String MYSQL_PORT     = "3306";
    public final static String MYSQL_BDD_NAME = "JspJEE";

    public final static String getMysqlUrl() {
        return "jdbc:mysql://" + MYSQL_HOST + ":" + MYSQL_PORT + "/" + MYSQL_BDD_NAME;
    }

}
