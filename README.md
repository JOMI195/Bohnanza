![Github Titlebanner](https://github.com/JOMI195/Bohnanza/assets/57303615/9865f1ab-1742-4d8c-8375-c3a7397867fa)

# Bohnanza Game

<p align="left">
  <img alt="Static Badge" src="https://hits.dwyl.com/JOMI195/Bohnanza.svg?style=flat-square">
  <img alt="Static Badge" src="https://img.shields.io/badge/FUN-100_%25-blue">
  <a href="https://app.codacy.com/gh/JOMI195/Bohnanza/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade"><img src="https://app.codacy.com/project/badge/Grade/b881c589a0184f27be3bcf5c08aca157?branch=main"/></a>
  <a href="https://coveralls.io/github/JOMI195/Bohnanza?branch=development">
    <img src="https://coveralls.io/repos/github/JOMI195/Bohnanza/badge.svg?branch=main" alt="Coverage Status">
  </a>
  <img src="https://img.shields.io/github/actions/workflow/status/JOMI195/Bohnanza/scala.yml" alt="GitHub Actions Workflow Status">
  <img alt="Static Badge" src="https://img.shields.io/badge/License-MIT-red">
</p>

This is an unofficial Scala version of the BOHNANZA game for Software Engineering classes at HTWG Konstanz.

Bohnanza is a card game that revolves around trading and planting beans. Each player has a hand of bean cards, which they must plant in the order they are drawn. Players can trade beans with each other to manage their fields more effectively. The goal is to collect sets of the same type of bean and then harvest them to earn coins. The player with the most coins at the end of the game wins. The game is known for its unique rule that players cannot rearrange the order of cards in their hands, which adds a strategic layer to trading and planting decisions.

## Top Game Features

* Interactive GUI, Text-based UI (TUI)
* Singleplayer/Multiplayer mode
* Great looking Artwork

## Table of content
- [Bohnanza Game](#bohnanza-game)
  * [Top Game Features](#top-game-features)
  * [Game Roadmap](#game-roadmap)
  * [Screenshots](#screenshots)
  * [Installation](#installation)
    + [Prerequisites](#prerequisites)
    + [Setup](#setup)

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
![Screenshot 2024-06-07 165321](https://github.com/JOMI195/Bohnanza/assets/57303615/4f6a1cbc-505c-41bb-9f9e-9b6be469e813)
![Screenshot 2024-06-07 165401](https://github.com/JOMI195/Bohnanza/assets/57303615/f4bf07e2-4b5a-4a91-8ab2-f7d8e92d1015)
![Screenshot 2024-06-07 170130](https://github.com/JOMI195/Bohnanza/assets/57303615/4bb86868-a50f-48a1-8eb8-aa9de905c530)
![Screenshot 2024-06-07 170006](https://github.com/JOMI195/Bohnanza/assets/57303615/ec973a11-b712-46b3-b1b5-bbb1dbf59b4b)
![Screenshot 2024-06-07 165954](https://github.com/JOMI195/Bohnanza/assets/57303615/20dbf9af-9493-4680-b598-803aabd44889)

## Installation
### Prerequisites
- Docker: Install Docker from Docker's official website.
- Docker Compose: Ensure Docker Compose is installed. It usually comes bundled with Docker Desktop.
- VcXsrv: Install VcXsrv, an X-server for Windows, from VcXsrv's official website. This could differ to other os systems.

### Setup
1. Clone the repository.
```bash
git clone https://github.com/JOMI195/Bohnanza.git
```

2. Start VcXsrv (X-Server) for GUI to host forwarding.

3. Build and Run with Docker Compose:
```bash
docker-compose run -rm --service-ports bohnanza-game
```
