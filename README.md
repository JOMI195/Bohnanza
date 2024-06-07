![Github Titlebanner](https://github.com/JOMI195/Bohnanza/assets/57303615/9865f1ab-1742-4d8c-8375-c3a7397867fa)

# Bohnanza Game

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/2dccba7e1def4590881bb6f0b6ceac5e)](https://app.codacy.com/gh/JOMI195/Bohnanza?utm_source=github.com&utm_medium=referral&utm_content=JOMI195/Bohnanza&utm_campaign=Badge_Grade)

<p align="left">
  <img alt="Static Badge" src="https://img.shields.io/badge/FUN-100_%25-blue">
  <a href="https://coveralls.io/github/JOMI195/Bohnanza?branch=development">
    <img src="https://coveralls.io/repos/github/JOMI195/Bohnanza/badge.svg?branch=development" alt="Coverage Status">
  </a>
  <img src="https://img.shields.io/github/actions/workflow/status/JOMI195/Bohnanza/scala.yml" alt="GitHub Actions Workflow Status">
</p>

This is an unofficial Scala version of the BOHNANZA game for Software Engineering classes at HTWG Konstanz.

Bohnanza is a card game that revolves around trading and planting beans. Each player has a hand of bean cards, which they must plant in the order they are drawn. Players can trade beans with each other to manage their fields more effectively. The goal is to collect sets of the same type of bean and then harvest them to earn coins. The player with the most coins at the end of the game wins. The game is known for its unique rule that players cannot rearrange the order of cards in their hands, which adds a strategic layer to trading and planting decisions.

## Top Game Features

* Interactive GUI, Text-based UI (TUI)
* Singleplayer/Multiplayer mode
* Great looking Artwork

## Game Roadmap
- [x] Playable with up to 4 players
- [x] Player can have several beanfields
- [x] Player has coins
- [x] Bean cards can be drawn from the deck to the players hand
- [x] Bean cards can be drawn from the deck to the turnoverfield
- [x] Bean cards can be planted from the players hand or the turnoverfield on the players beanfields
- [x] Bean cards can be harvested from the players beanfield and turned into coins
- [ ] Player can buy the third beanfield
- [ ] Game ending
- [ ] Trading between Players

## Screenshots

## Installation
### Prerequisites
- JDK 11
- sbt

### Setup
1. Clone the repository
```bash
git clone https://github.com/JOMI195/Bohnanza.git
```
2. Run the Project
```bash
sbt run
```
