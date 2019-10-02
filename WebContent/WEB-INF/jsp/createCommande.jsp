<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Création d'une commande</title>
        <link type="text/css" rel="stylesheet" href="style.css" />
    </head>
    <body>
    	<c:import url="/WEB-INF/jsp/include/menu.jsp"></c:import>
        <div>
            <form method="POST" action="createCommande">
            	<div class="choice_zone">
                    <label >Nouveau Client ? <span class="requis">*</span></label>
                    <input type="radio" id="new" name="typeClient" value="new" />
                    <span>Oui</span>
                    <input type="radio" id="old" name="typeClient" value="old" checked="checked" />
                    <span>Non</span>
                    <br />
               </div>
               
               <select id="multi" name="Clients">
               		<option value="">Choisissez un client</option>
               		<c:forEach  items="${ sessionScope.listeClients }" var="data" >
               			<option value="${ data.id }">${ data.prenom }</option>
               		</c:forEach>
               </select>
            
					<c:import url="/WEB-INF/jsp/include/menuClient.jsp"></c:import>
					
                <fieldset>
                    <legend>Informations commande</legend>
                    
                    <label for="dateCommande">Date <span class="requis">*</span></label>
                    <input type="text" id="dateCommande" name="dateCommande" value="" size="20" maxlength="20" disabled />
                    <br />
                    
                    <label for="montantCommande">Montant <span class="requis">*</span></label>
                    <input type="text" id="montantCommande" name="montantCommande" value="" size="20" maxlength="20" />
                    <br />
                    
                    <label for="modePaiementCommande">Mode de paiement <span class="requis">*</span></label>
                    <input type="text" id="modePaiementCommande" name="modePaiementCommande" value="" size="20" maxlength="20" />
                    <br />
                    
                    <label for="statutPaiementCommande">Statut du paiement</label>
                    <input type="text" id="statutPaiementCommande" name="statutPaiementCommande" value="" size="20" maxlength="20" />
                    <br />
                    
                    <label for="modeLivraisonCommande">Mode de livraison <span class="requis">*</span></label>
                    <input type="text" id="modeLivraisonCommande" name="modeLivraisonCommande" value="" size="20" maxlength="20" />
                    <br />
                    
                    <label for="statutLivraisonCommande">Statut de la livraison</label>
                    <input type="text" id="statutLivraisonCommande" name="statutLivraisonCommande" value="" size="20" maxlength="20" />
                    <br />
                </fieldset>
                <input type="submit" value="Valider"  />
                <input type="reset" value="Remettre à zéro" /> <br />
            </form>
        </div>
        <p>${!empty form.resultat ? form.resultat : ''  }</p>
    </body>
    
    <script src="public/js/ncnNameSpace.js" ></script>
    <script type="text/javascript">
    
    	var btnOld = cg_ncn.get.elmtById("old"),
    	 	btnNew = document.getElementById("new"),
    	 	bloc_client = document.getElementById("bloc_client"),
    	 	multi = document.getElementById("multi");
    	
    	//by default 
    	bloc_client.style.display = 'none';    	

    	btnOld.onclick = function(e){
    		bloc_client.style.display = 'none';
    		multi.style.display = 'block';
    	}
    	
    	btnNew.onclick = function(e){
    		bloc_client.style.display = 'block';
    		multi.style.display = 'none';
    	}
    
    </script>
</html>





