```
+=========================================================================+
|                          PANOPTES TRACKER                               |
|     Application Java Spring Boot de traçabilité logistique (H2)        |
+=========================================================================+

📦 Version        : 1.0.0
🔧 Stack          : Java 17 | Spring Boot 3 | Spring Data JPA | H2 DB
📁 Packaging      : Maven (jar)
🔗 Licence        : MIT

───────────────────────────────────────────────────────────────────────────
🧭 OBJECTIF
───────────────────────────────────────────────────────────────────────────
> Cette application permet d'enregistrer, consulter et analyser les 
> événements de traçabilité d'un conteneur logistique : SCAN, MOVE, 
> OPEN, CLOSE, ERROR...

Elle simule le cœur d’un système utilisé dans des entrepôts, hubs ou 
infrastructures pour suivre les mouvements des conteneurs.

───────────────────────────────────────────────────────────────────────────
📦 STRUCTURE DU PROJET
───────────────────────────────────────────────────────────────────────────

📁 com.panoptes.tracking
├── 📂 controller         → Contrôleurs REST (API publique)
├── 📂 service            → Logique métier + règles RM1 à RM5
├── 📂 entity             → Entités JPA (Container, EventType…)
├── 📂 repository         → Interfaces Spring Data JPA
├── 📂 dto                → Objets de transfert (Request / Response)
├── 📂 mapper             → Conversion Entity <-> DTO
├── 📂 exception          → Gestion des erreurs (future extension)
└── 📄 TrackingSystemApplication.java

───────────────────────────────────────────────────────────────────────────
🧠 RÈGLES MÉTIER (INTÉGRÉES)
───────────────────────────────────────────────────────────────────────────

| Code | Règle Métier                                                             |
|------|--------------------------------------------------------------------------|
| RM1  | Un conteneur ne peut pas avoir deux événements identiques (type+date)   |
| RM2  | Un événement MOVE nécessite une localisation obligatoire                 |
| RM3  | Un événement ERROR bloque automatiquement le conteneur                  |
| RM4  | Un événement CLOSE marque le conteneur comme livré                      |
| RM5  | Aucun événement ne peut être daté dans le futur éloigné                 |

───────────────────────────────────────────────────────────────────────────
💾 BASE DE DONNÉES : H2 EN MÉMOIRE
───────────────────────────────────────────────────────────────────────────

Console H2 : http://localhost:8080/h2-console  
JDBC URL   : jdbc:h2:mem:trackingdb  
Utilisateur : sa / (mot de passe vide)

Fichier de pré-remplissage : `src/main/resources/data.sql`

───────────────────────────────────────────────────────────────────────────
🚀 DÉMARRAGE RAPIDE
───────────────────────────────────────────────────────────────────────────

1. Cloner ou extraire le projet
2. Lancer l’application :
   ```bash
   ./mvnw spring-boot:run
   ```
3. Appeler l’API :

   ➤ Ajouter un événement
   ```http
   POST /api/events
   {
     "containerCode": "CNT-001",
     "eventType": "MOVE",
     "timestamp": "2025-03-25T10:00:00",
     "locationName": "Hub Sud",
     "metadata": "Départ vers centre logistique"
   }
   ```

   ➤ Obtenir l’historique :
   ```http
   GET /api/events/CNT-001
   ```

───────────────────────────────────────────────────────────────────────────
🔍 FILTRAGE AVANCÉ (future extension)
───────────────────────────────────────────────────────────────────────────
```
GET /api/events/CNT-001/filter?type=MOVE&from=2025-03-25T08:00&to=2025-03-25T12:00
```

Ajout possible :
- Pagination (`?page=0&size=10`)
- Tri (`&sort=timestamp,desc`)

───────────────────────────────────────────────────────────────────────────
🧪 TESTS
──────────────────────────────────────────────────────────────────────────
Tests unitaires (Mockito) :
- `TrackingEventServiceTest.java`

Tests d’intégration (MockMvc) :
- `TrackingEventControllerIT.java`

Lancer tous les tests :
```bash
mvn test
```

───────────────────────────────────────────────────────────────────────────
💡 IDÉES D’EXTENSION
─────────────────────────────────────────────────────────────────────────
- Export CSV
- Swagger UI
- Authentification JWT
- Dashboard avec Angular / Vue.js
- WebSockets pour alertes en temps réel

───────────────────────────────────────────────────────────────────────────
👨‍💻 AUTEUR
───────────────────────────────────────────────────────────────────────────
Projet inspiré d’un besoin réel de traçabilité.
Développé par [Seifeddine daoud] – Licence MIT

```