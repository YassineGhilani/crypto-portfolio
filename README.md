## Présentation

Merci pour votre attention. Ce test m'a permis de me mettre à jour sur Java Spring étant un profil PHP mais j'ai pu surmonter ce défis!

remarques: j'ai respecté les consignes dans le readme du projet original du répo @olky_public 

## ExchangeRateService

Port: 8081

Pour l'endpoint de ce projet GET /exchange-rate?symbol={symbol}&base={base}, vous pouvez tester avec un exemple comme:

http://localhost:8081/exchange-rate?currency=bitcoin&base=usd (GET)
résultat:
![alt text](screen1.png)

   ## Problème rencontré:
   Malgré que j'ai configuré CORS pour être consommé par l'application PortfolioService, pour ne pas y passer beaucoup de temps j'ai opté pour une solution alternative. Après plusieurs recherches j'ai trouvé que le souci ne provient pas de la connexion elle même mais de la réception de la requête via l'autre application et le soucis est résolu
## PortfolioService

J'ai utilisé H2 comme SGBD.
Port: 8082

Remarque: Les applications ExchangeRateService et PortfolioService sont connectées.

J'ai créé dans le dossier main les entités Holding, et Portfolio qui sont liées via une relation ManytoOne et OnetoMany respectivement. En plus j'ai utilisé le JpaRepository pour gérer le CRUD.

PortfolioService regroupe les fonctions utilisées par les Endpoints.

http://localhost:8082/portfolios (POST)
![alt text](screen2.png)

http://localhost:8082/portfolios/1 (GET)
![alt text](screen3.png)

http://localhost:8082/portfolios/1/holdings (POST)
![alt text](screen4.png)

http://localhost:8082/portfolios/1/valuation?base=usd (GET)
![alt text](image.png)

//ici j'ai ajouté une crypto que j'ai nommée "TEST"
http://localhost:8082/portfolios/1/holdings/TEST (DELETE)
![alt text](image-1.png)

Pour les tests j'ai créé les différents scénarios que j'ai pu imaginé et ils sont tous passé en vert (success ).