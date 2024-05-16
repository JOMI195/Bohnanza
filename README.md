# Bohnanza
HTWG Constance - Software Engineering - Semester 3

<p align="left">
  <a href="https://coveralls.io/github/JOMI195/Bohnanza?branch=development">
    <img src="https://coveralls.io/repos/github/JOMI195/Bohnanza/badge.svg?branch=development" alt="Coverage Status">
  </a>
  <img src="https://img.shields.io/github/actions/workflow/status/JOMI195/Bohnanza/scala.yml" alt="GitHub Actions Workflow Status">
</p>


<p align="center">
<img alt="bohnanza" src="https://github.com/JOMI195/Bohnanza/assets/57303615/9ff67416-81bc-4e6a-bb5e-08585779e5a7" height="200">
</p>

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

## Running the tests
```bash
sbt test
```
## Code Coverage
We use scoverage for code coverage. The coverage report can be generated using the following command:
```bash
sbt clean coverage test coverageReport
```

## Game instructions
<p align="center">
<img alt="bohnanza-anleitung" src="https://github.com/JOMI195/Bohnanza/assets/57303615/07a90f45-82d3-4ccc-acde-9478fc7e3aee" height="500">
</p>

## TUI
The Game provides a `Tui` (Text User Interface) which is a command-line interface to interact an play the the Bohnanza game in the console.

### TUI-API: Commands
The TUI-API supports the following commands:

1. **draw [playerIndex]**
   - Draws a card for the specified player.
   - **Usage:** `draw [playerIndex]`
   - **Example:** `draw 1`

2. **plant [playerIndex] [beanFieldIndex]**
   - Plants a card in the specified bean field for the specified player.
   - **Usage:** `plant [playerIndex] [beanFieldIndex]`
   - **Example:** `plant 1 2`

3. **harvest [playerIndex] [beanFieldIndex]**
   - Harvests beans from the specified bean field for the specified player.
   - **Usage:** `harvest [playerIndex] [beanFieldIndex]`
   - **Example:** `harvest 1 2`

4. **turn**
   - Draws two cards from the deck and puts them on the TurnOverField.
   - **Usage:** `turn`
   - **Example:** `turn`

5. **take [playerIndex] [cardIndex] [beanFieldIndex]**
   - Takes a card from the TurnOverField and plants it in the specified bean field for the specified player.
   - **Usage:** `take [playerIndex] [cardIndex] [beanFieldIndex]`
   - **Example:** `take 1 0 2`

6. **exit**
   - Exits the game.
   - **Usage:** `exit`

7. **Default**
   - Handles unrecognized commands and shows the possible commands.
