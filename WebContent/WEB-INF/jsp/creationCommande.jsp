<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Afffichage de la commande</title>
	<link type="text/css" rel="stylesheet" href="style.css" />
</head>
<body>
	<c:import url="/WEB-INF/jsp/include/menu.jsp"></c:import>
<p><c:out value="${!empty error ? error : '' }"></c:out> </p>

<c:choose>
	<c:when test="${empty sessionScope.listeCmd }">
		<p class="empty">Aucun client crée pour l'instant.</p>
	</c:when>
	<c:otherwise>
		<table>
		     <tr>
		         <th>Client</th>
		         <th>Date</th>
		         <th>Montant</th>
		         <th>Mode de paiement</th>
		         <th>Statut de paiement</th>
		         <th>Mode de livraison</th>
		         <th>Statut de la livraison</th>
		         <th class="action">Supprimer</th>                    
		     </tr>
		     
		     <c:forEach items="${ sessionScope.listeCmd }" var="cmd" varStatus="boucle">
			     <%-- Simple test de parité sur l'index  --%>
			     <tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}">
			        
			         <td>
				         <c:out value="${ cmd.client.nom }"/>
				         <c:out value="${ cmd.client.prenom }"/>
			         </td>
			         <td><c:out value="${ cmd.date }"/></td>
			         <td><c:out value="${ cmd.montant }"/></td>
			         <td><c:out value="${ cmd.modePayment }"/></td>
			         <td><c:out value="${ cmd.statutPayment }"/></td>
			         <td><c:out value="${ cmd.modeLIvraison }"/></td>
			         <td><c:out value="${ cmd.statutLIvraison }"/></td>

			         <%-- Lien vers la servlet de suppression --%>
			         <td class="action">
			             <a href="<c:url value="/deleteCmd"><c:param name="id" value="${ cmd.id }" /></c:url>">
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