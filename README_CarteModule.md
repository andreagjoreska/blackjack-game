# Jeu de Cartes - Card Module

## 🇬🇧 English

### Overview
A reusable **card deck module** built in Java, developed as a group project at the University of Caen Normandy. This module serves as the foundation for the Blackjack project, providing clean, observable card and deck structures with standard operations (shuffle, cut, deal, random draw).

**Group:** Andrea Gjoreska, Mehmet Tuna Ozkalkanli, Mila Bucevska

### Project Structure
```
src/
├── model/
│   ├── Carte.java          # Card - suit and value attributes
│   ├── Paquet.java         # Deck - extends AbstractModeleEcoutable, all deck operations
│   └── Factory.java        # Factory - builds 32-card, 52-card, or Blackjack decks
├── util/observer/
│   ├── EcouteurModele.java         # Observer interface
│   └── AbstractModeleEcoutable.java # Abstract class managing subscriber list
├── tests/                  # Unit tests for core model methods
└── Main.java               # Demo - showcases all deck operations
```

### Technologies Used
- **Java** - Core language
- **Observer design pattern** - `AbstractModeleEcoutable` + `EcouteurModele` interface for automatic change notification
- **Factory design pattern** - `Factory` class builds different deck types (32, 52, Blackjack)
- **JUnit** - Unit testing

### Features
- `Factory` builds three deck types: 32-card, 52-card, and Blackjack-specific
- `Paquet.shuffle()` - randomises the deck using `Collections.shuffle()` and notifies observers
- `Paquet.cut()` - cuts the deck at a random central point, simulating a manual cut
- `Paquet.distribuer()` - removes the top card and places it on a target deck
- `Paquet.draw()` - draws a random card by index
- Observer model: `Paquet` notifies all registered listeners on every modification, ready for GUI or game integration

### How to Run
```
ant run in the card folder
```

### Authors
Group project - L3, University of Caen Normandy, 2025–2026  
Course: *Méthodes de conception* - Supervisor: Christophe Charrier

---

## 🇫🇷 Français

### Présentation
Un **module de gestion de cartes** réutilisable en Java, réalisé en groupe à l'Université de Caen Normandie. Ce module sert de base au projet Blackjack, en fournissant des structures de cartes et de paquets claires et observables, avec les opérations standard (mélange, coupe, distribution, tirage aléatoire).

**Groupe :** Andrea Gjoreska, Mehmet Tuna Ozkalkanli, Mila Bucevska

### Structure du projet
```
src/
├── model/
│   ├── Carte.java          # Carte - couleur et valeur
│   ├── Paquet.java         # Paquet - hérite de AbstractModeleEcoutable, toutes les opérations
│   └── Factory.java        # Factory - construction de paquets de 32, 52 ou Blackjack
├── util/observer/
│   ├── EcouteurModele.java          # Interface observateur
│   └── AbstractModeleEcoutable.java # Classe abstraite gérant la liste des abonnés
├── tests/                  # Tests unitaires des méthodes de base
└── Main.java               # Démonstration de toutes les opérations sur un paquet
```

### Technologies utilisées
- **Java** - Langage principal
- **Patron Observateur** - `AbstractModeleEcoutable` + interface `EcouteurModele` pour la notification automatique des changements
- **Patron Factory** - la classe `Factory` construit différents types de paquets (32, 52, Blackjack)
- **JUnit** - Tests unitaires

### Fonctionnalités
- `Factory` construit trois types de paquets : 32 cartes, 52 cartes, et Blackjack
- `Paquet.shuffle()` - mélange le paquet avec `Collections.shuffle()` et notifie les observateurs
- `Paquet.cut()` - coupe le paquet à un point central aléatoire, simulant une coupe manuelle
- `Paquet.distribuer()` - retire la première carte et la place sur un paquet cible
- `Paquet.draw()` - tire une carte au hasard par index
- Modèle Observateur : `Paquet` notifie tous les écouteurs enregistrés à chaque modification, prêt pour une intégration dans une interface graphique ou un jeu

### Exécution
```
ant run dans le repertoire carte
```

### Auteurs
Projet de groupe - L3, Université de Caen Normandie, 2025–2026  
UE : *Méthodes de conception* - Chargé de TP : Christophe Charrier
