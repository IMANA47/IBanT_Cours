# G-Student - Gestion des Étudiants

Application JavaFX de gestion des étudiants, des matières et des notes.

## 📋 Description

G-Student est une application desktop permettant de gérer :
- Les étudiants (ajout, modification, suppression)
- Les matières avec leurs coefficients
- Les notes des étudiants par matière
- Les statistiques (moyennes, graphiques)

## 🏗️ Architecture

Le projet suit une architecture en couches :

- **Entities** (`org.gstudent.entities`) : Modèles de données (Etudiant, Matiere, Composer, Utilisateur)
- **Services** (`org.gstudent.service`) : Logique métier et accès aux données
- **Controllers** (`org.gstudent.controller`) : Contrôleurs JavaFX pour l'interface utilisateur
- **Config** (`org.gstudent.config`) : Configuration de la base de données
- **Exception** (`org.gstudent.exception`) : Gestion des exceptions personnalisées
- **Util** (`org.gstudent.util`) : Utilitaires (validation)

## 🛠️ Technologies

- **Java** : 17
- **JavaFX** : 17.0.12
- **Base de données** : H2 Database 2.2.224
- **Logging** : SLF4J 2.0.9
- **Build** : Maven

## 📦 Installation

### Prérequis

- Java 17 ou supérieur
- Maven 3.6+

### Compilation

```bash
mvn clean compile
```

### Exécution

```bash
mvn javafx:run
```

## 🔐 Authentification

Par défaut, l'application utilise les identifiants suivants :
- **Login** : admin
- **Mot de passe** : admin

⚠️ **Attention** : En production, il est impératif de hasher les mots de passe et de ne pas les stocker en clair.

## 💾 Base de données

La base de données H2 est stockée dans le répertoire `./data/gstudent_db` du projet.

### Schéma de la base

- **utilisateur** : Utilisateurs de l'application (login, password, role)
- **etudiant** : Informations sur les étudiants
- **matiere** : Matières avec coefficients
- **Composer** : Table de liaison entre étudiants et matières avec les notes

## 🚀 Fonctionnalités

### Gestion des Étudiants
- Ajouter un étudiant (nom, prénom, email)
- Modifier les informations d'un étudiant
- Supprimer un étudiant
- Lister tous les étudiants

### Gestion des Matières
- Ajouter une matière avec son coefficient
- Modifier une matière
- Supprimer une matière
- Lister toutes les matières

### Gestion des Notes
- Attribuer une note à un étudiant pour une matière
- Modifier une note
- Supprimer une note
- Visualiser toutes les notes

### Statistiques
- Calcul de la moyenne générale
- Moyenne par matière
- Graphique de répartition des notes par tranches

## ✅ Validation

L'application inclut une validation des données :
- **Email** : Format valide requis
- **Noms** : 2 à 50 caractères alphabétiques
- **Coefficients** : Entre 0 et 20
- **Notes** : Entre 0 et 20

## 📝 Logging

Les logs sont configurés via `simplelogger.properties` :
- Niveau INFO par défaut
- Niveau DEBUG pour les packages `org.gstudent`
- Les logs incluent la date et l'heure

## 🔧 Configuration

### Configuration de la base de données

La configuration se trouve dans `org.gstudent.config.ConnexionBD` :
- URL : `jdbc:h2:./data/gstudent_db`
- User : `root`
- Password : `root123#`

## 📂 Structure du projet

```
src/
├── main/
│   ├── java/
│   │   └── org/
│   │       └── gstudent/
│   │           ├── config/
│   │           │   └── ConnexionBD.java
│   │           ├── controller/
│   │           │   ├── BaseController.java
│   │           │   ├── ComposerController.java
│   │           │   ├── EtudiantController.java
│   │           │   ├── LoginController.java
│   │           │   ├── MainController.java
│   │           │   ├── MatiereController.java
│   │           │   └── StatsController.java
│   │           ├── entities/
│   │           │   ├── Composer.java
│   │           │   ├── Etudiant.java
│   │           │   ├── Matiere.java
│   │           │   └── Utilisateur.java
│   │           ├── exception/
│   │           │   └── DaoException.java
│   │           ├── service/
│   │           │   ├── ComposerService.java
│   │           │   ├── EtudiantService.java
│   │           │   ├── MatiereService.java
│   │           │   └── UtilisateurService.java
│   │           ├── util/
│   │           │   └── ValidationUtils.java
│   │           └── Main.java
│   └── resources/
│       ├── css/
│       ├── org/
│       │   └── gstudent/
│       │       └── views/
│       │           ├── composer-view.fxml
│       │           ├── etudiant-view.fxml
│       │           ├── login-view.fxml
│       │           ├── main-view.fxml
│       │           ├── matiere-view.fxml
│       │           └── stats-view.fxml
│       └── simplelogger.properties
└── test/
    └── java/
```

## 🔄 Transactions

L'application utilise une gestion explicite des transactions :
- `auto-commit` est désactivé
- Chaque opération de modification effectue un `commit` explicite
- En cas d'erreur, un `rollback` est effectué

## 🐛 Dépannage

### Les données ne sont pas sauvegardées

Vérifiez que :
1. Le répertoire `data/` existe et est accessible en écriture
2. Les logs ne montrent pas d'erreurs de connexion
3. La base de données n'est pas verrouillée par une autre instance

### Erreur de connexion

Vérifiez que :
1. H2 Database est correctement configuré dans le pom.xml
2. Les identifiants de connexion sont corrects
3. Le port n'est pas déjà utilisé

## 📈 Améliorations futures

- [ ] Ajouter des tests unitaires
- [ ] Implémenter le hashage des mots de passe
- [ ] Ajouter une couche DAO avec interfaces
- [ ] Utiliser l'injection de dépendances
- [ ] Ajouter des exports (PDF, Excel)
- [ ] Implémenter la recherche avancée
- [ ] Ajouter des rôles et permissions
- [ ] Internationalisation (i18n)

## 👥 Auteurs

Projet réalisé dans le cadre du cours de Java.

## 📄 Licence

Ce projet est à usage éducatif.
