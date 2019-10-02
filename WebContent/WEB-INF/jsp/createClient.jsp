<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Création d'un client</title>
        <link type="text/css" rel="stylesheet" href="style.css" />
    </head>
    <body>
    	<c:import url="/WEB-INF/jsp/include/menu.jsp"></c:import>
        <div>
            <form method="POST" action="createClient" enctype="multipart/form-data" >
                <c:import url="/WEB-INF/jsp/include/menuClient.jsp"></c:import>
                <input type="submit" value="Valider"  />
                <input type="reset" value="Remettre à zéro" /> <br />
            </form>
        </div>
        <p>${!empty form.resultat ? form.resultat : ''  }</p>
    </body>
</html>