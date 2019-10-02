<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Afffichage du client</title>
	<link type="text/css" rel="stylesheet" href="style.css" />
</head>
<body>
	<%-- importation du menu --%>
	<c:import url="/WEB-INF/jsp/include/menu.jsp"></c:import>
	
	<p><c:out value="${ form.resultat   }"></c:out> </p>

	<c:choose>
		<c:when test="${empty sessionScope.listeClients }">
			<p class="empty">Aucun client crée pour l'instant.</p>
		</c:when>
		<c:otherwise>
			<table>
			     <tr>
			         <th>Nom</th>
			         <th>Prénom</th>
			         <th>Adresse</th>
			         <th>Téléphone</th>
			         <th>Email</th>
			         <th>Image</th>
			         <th class="action">Supprimer</th>                    
			     </tr>
			     
			     <c:forEach items="${ sessionScope.listeClients }" var="client" varStatus="boucle">
				     <%-- Simple test de parité sur l'index  --%>
				     <tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}">
				        
				         <td><c:out value="${ client.nom }"/></td>
				         <td><c:out value="${ client.prenom }"/></td>
				         <td><c:out value="${ client.adresse }"/></td>
				         <td><c:out value="${ client.numero }"/></td>
				         <td><c:out value="${ client.email }"/></td>
				         <td>
				             <a href="<c:url value="/upload/${ client.image }"></c:url>">
				                 Afficher l'image
				             </a>
				         </td>
				         
				         <%-- Lien vers la servlet de suppression --%>
				         <td class="action">
				             <a href="<c:url value="/deleteClient"><c:param name="id" value="${ client.id }" /></c:url>">
				                 <img src="<c:url value="/public/img/delete.png"/>" alt="Supprimer" />
				             </a>
				         </td>
				     </tr>
			     </c:forEach>
			 </table>
		</c:otherwise>
	</c:choose>
</body>
</html>