<div id="menu">
		<h2 class="mail">Menu de Gestion</h2>
		<ul>
			<li> <a href="createClient"  title="Creer un client" >CreationClient</a></li>
			<li> <a href="createCommande" title="creer une commande" >CreationCommande</a></li>
			<li> <a href="listeClient"  title="voir les clients" >Liste des Clients</a></li>
			<li> <a href="listeCommande" title="voir les commmandes" >Liste des commandes</a></li>
			<li> <a href="logOut" title="Deconnecter vous" >Déconnexion </a></li>
		</ul>
		<p>Vous êtes connecter en tant que : <span class="mail"><c:out value="${ sessionScope.sessionUser.email }"></c:out></span></p>
</div>
