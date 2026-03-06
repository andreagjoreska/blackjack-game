# Jeu de Blackjack - Blackjack Game

## 🇬🇧 English

### Overview
A fully interactive **Blackjack** game built in Java with a Swing GUI, developed as a group project at the University of Caen Normandy. The project implements **MVC architecture** alongside five design patterns (State, Strategy, Proxy, Adapter, Observer), featuring a complete rule set including Split, Insurance, and Double, as well as a robot opponent with four selectable strategies.

**Group:** Andrea Gjoreska, Mehmet Tuna Ozkalkanli, Mila Bucevska

### Project Structure
```
src/
├── blackjack/
│   ├── model/
│   │   ├── Game.java               # Core game logic - turn flow, payouts, state management
│   │   ├── Player.java             # Player - balance, bets, multiple hands (split support)
│   │   ├── PlayerHand.java         # Hand - card values, blackjack/bust checks
│   │   ├── DealerHand.java         # Dealer hand logic
│   │   ├── Deck.java               # Deck (via DeckProxy)
│   │   ├── Card.java               # Card - suit and value
│   │   ├── DeckProxy.java          # Proxy - controls deck access, auto-resets empty deck
│   │   ├── StatisticsManager.java  # Tracks wins, losses, blackjacks, balance history
│   │   ├── PlayerStatisticsAdapter.java  # Adapter - adds stats tracking to Player
│   │   ├── states/                 # State pattern - BettingState, DealingState,
│   │   │                           #   PlayingState, DealerTurnState, PayoutState
│   │   └── strategy/               # Strategy pattern - NormalStrategy, ConservativeStrategy,
│   │                               #   AggressiveStrategy, RandomStrategy
│   ├── view/
│   │   ├── View.java               # Main game window
│   │   ├── Menu.java               # Configuration screen (balance, robot strategy)
│   │   ├── ViewPacketVisible.java  # Visible card display (with active-hand indicator)
│   │   ├── ViewPacketHidden.java   # Hidden deck display
│   │   └── Rules.java              # Rules screen
│   └── controleur/
│       └── Controller.java         # MVC bridge - ActionListener, MouseListener, KeyListener
└── tests/                          # Unit and Swing component tests
```

### Technologies Used
- **Java** - Core language
- **Swing** - Full graphical interface (JButton, JLabel, JPanel, CardLayout)
- **MVC architecture** - Clean separation of model, view, and controller
- **5 design patterns** - State, Strategy, Proxy, Adapter, Observer
- **OOP** - Abstract classes, interfaces, inheritance (State hierarchy, Strategy hierarchy)

### Design Patterns

| Pattern | Where | Purpose |
|---|---|---|
| **State** | `states/` - BettingState, DealingState, PlayingState, DealerTurnState, PayoutState | Manages game phase transitions; invalid transitions prevented via `canTrans()` |
| **Strategy** | `strategy/` - Normal, Conservative, Aggressive, Random | Swappable robot behaviour without modifying `Player`; new strategies added by implementing `Strategy` |
| **Proxy** | `DeckProxy` | Controls deck access, counts distributed cards, auto-recreates deck when empty |
| **Adapter** | `PlayerStatisticsAdapter` | Adds real-time statistics tracking to `Player` without modifying the class |
| **Observer** | `EcouteurModele` / `fireChangement()` | View auto-refreshes on every model change; loose coupling between model and view |

### Features
- Full Blackjack rule set: **Hit**, **Stand**, **Double**, **Split**, **Insurance**
- Split support - two hands displayed side by side with sequential play management
- Insurance system - available when dealer shows an Ace; pays 2:1 if dealer has Blackjack
- Four robot strategies selectable at game start: Normal (mathematically optimal), Conservative, Aggressive, Random
- Statistics summary at game end: balance history, total wins/losses, number of Blackjacks
- Keyboard shortcuts: `h` = hit, `s` = split, `t` = stand, `d` = double, `i` = insurance
- Configurable starting balance (default 1000)

### How to Run
```
ant run in the blackjack folder
```

### Authors
Group project - L3, University of Caen Normandy, 2024–2025  
Course: *Méthodes de conception* - Supervisor: Christophe Charrier

---

## 🇫🇷 Français

### Présentation
Un jeu de **Blackjack** interactif en Java avec interface graphique Swing, réalisé en groupe à l'Université de Caen Normandie. Le projet implémente l'**architecture MVC** et cinq patrons de conception (State, Strategy, Proxy, Adapter, Observer), avec un jeu de règles complet incluant Split, Assurance et Double, ainsi qu'un adversaire robot avec quatre stratégies sélectionnables.

**Groupe :** Andrea Gjoreska, Mehmet Tuna Ozkalkanli, Mila Bucevska

### Structure du projet
```
src/
├── blackjack/
│   ├── model/
│   │   ├── Game.java               # Logique principale - déroulement, paiements, états
│   │   ├── Player.java             # Joueur - solde, mises, mains multiples (split)
│   │   ├── PlayerHand.java         # Main - calcul de valeur, vérification blackjack/bust
│   │   ├── DealerHand.java         # Logique de la main du croupier
│   │   ├── Deck.java               # Paquet (via DeckProxy)
│   │   ├── Card.java               # Carte - couleur et valeur
│   │   ├── DeckProxy.java          # Proxy - contrôle l'accès, réinitialise le paquet vide
│   │   ├── StatisticsManager.java  # Victoires, défaites, blackjacks, historique du solde
│   │   ├── PlayerStatisticsAdapter.java  # Adapter - ajoute les stats au Player
│   │   ├── states/                 # State - BettingState, DealingState,
│   │   │                           #   PlayingState, DealerTurnState, PayoutState
│   │   └── strategy/               # Strategy - NormalStrategy, ConservativeStrategy,
│   │                               #   AggressiveStrategy, RandomStrategy
│   ├── vue/
│   │   ├── View.java               # Fenêtre principale de jeu
│   │   ├── Menu.java               # Écran de configuration (solde, stratégie du robot)
│   │   ├── ViewPacketVisible.java  # Affichage des cartes visibles (indicateur de main active)
│   │   ├── ViewPacketHidden.java   # Affichage du paquet restant
│   │   └── Rules.java              # Écran des règles
│   └── controleur/
│       └── Controller.java         # Pont MVC - ActionListener, MouseListener, KeyListener
└── tests/                          # Tests unitaires et tests des composants Swing
```

### Technologies utilisées
- **Java** - Langage principal
- **Swing** - Interface graphique complète (JButton, JLabel, JPanel, CardLayout)
- **Architecture MVC** - Séparation claire du modèle, de la vue et du contrôleur
- **5 patrons de conception** - State, Strategy, Proxy, Adapter, Observer
- **POO** - Classes abstraites, interfaces, héritage (hiérarchie State, hiérarchie Strategy)

### Patrons de conception

| Patron | Où | Rôle |
|---|---|---|
| **State** | `states/` - BettingState, DealingState, PlayingState, DealerTurnState, PayoutState | Gère les transitions de phase ; transitions invalides bloquées par `canTrans()` |
| **Strategy** | `strategy/` - Normal, Conservative, Aggressive, Random | Comportement du robot interchangeable sans modifier `Player` |
| **Proxy** | `DeckProxy` | Contrôle l'accès au paquet, compte les cartes distribuées, réinitialise le paquet vide |
| **Adapter** | `PlayerStatisticsAdapter` | Ajoute le suivi des statistiques au `Player` sans modifier la classe |
| **Observer** | `EcouteurModele` / `fireChangement()` | La vue se rafraîchit automatiquement à chaque changement du modèle |

### Fonctionnalités
- Jeu de règles complet : **Hit**, **Stand**, **Double**, **Split**, **Insurance**
- Gestion du Split - deux mains affichées côte à côte avec jeu séquentiel
- Système d'assurance - disponible quand le croupier montre un As ; paie 2:1 si le croupier a un Blackjack
- Quatre stratégies robot sélectionnables au démarrage : Normale (optimale mathématiquement), Conservative, Agressive, Aléatoire
- Récapitulatif de statistiques en fin de partie : historique du solde, victoires/défaites, nombre de Blackjacks
- Raccourcis clavier : `h` = hit, `s` = split, `t` = stand, `d` = double, `i` = insurance
- Solde initial configurable (1000 par défaut)

### Exécution
```ant run dans le repertoire blackjack
```

### Auteurs
Projet de groupe - L3, Université de Caen Normandie, 2024–2025  
UE : *Méthodes de conception* - Chargé de TP : Christophe Charrier
