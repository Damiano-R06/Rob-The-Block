# Rob The Block 🏠💰
### CMPT 276 — Group 20

Ever wanted to be a burglar? Well now's your chance (virtually, of course).
Rob the Block is a 2D arcade-style maze game built with LibGDX where you
sneak through houses, collect coins and treasure, and try not to get caught.
Avoid the sleepwalking homeowners, and whatever you do — don't step on the
dog toy. There are 4 levels, each with a unique house layout and progressively
harder challenges. Good luck out there! 🐕

## How It Works

- **Collect all regular rewards** in the level to unlock the exit door
- **Reach the exit** to advance to the next level
- **Avoid homeowners** — if they catch you, it's game over
- **Watch out for the dog toy** — step on it and the dog wakes up and chases you( get too close and it'll cost you 5 coins, 
  and if your score goes negative, it's game over!)
- **Bonus rewards** are scattered around too — grab them for extra coins!
- Your **score carries over** between levels, so collect as much as you can

## Prerequisites

Make sure you have these installed before anything else:
- **Java JDK 21** or newer 
- **Apache Maven 3.8+** 

To verify your installation:
```bash
java -version
mvn -version
```

## Building the Game

Clone the repo, navigate into the `Game/` folder, then run:

```bash
mvn package
```

This compiles all the source code and bundles everything — including all
LibGDX native dependencies — into a single runnable JAR file at:
target/RobTheBlock.jar


## Running the Game

**Option 1 — Run the JAR (recommended, no Maven needed):**
```bash
java -jar target/RobTheBlock.jar
```

**Option 2 — Run directly through Maven:**
```bash
mvn compile exec:java
```


## Controls

| Key | Action |
|-----|--------|
| W | Move Up |
| S | Move Down |
| A | Move Left |
| D | Move Right |
| R | Restart current level |

Diagonal movement works too ( just press two direction keys at once!)
Controls are also displayed at the top of the screen during gameplay.

## Running the Tests

We have **109 unit tests** covering the core game logic across all major
components — characters, enemies, levels, and objects. We achieved 95%+
line coverage and 85%+ branch coverage across all sections.

The tests cover:
- **Burglar** — movement, scoring, reward collection, collision
- **Enemies** — Dog behavior (sleeping/chasing/returning states),
  Homeowner patrol paths, ChaseAlgorithm logic
- **Level** — reward placement, exit conditions, tile validation
- **Objects** — RegularReward, BonusReward, NormalPunishment, DogToy behavior

To run all tests:
```bash
mvn clean test
```

You'll see each test suite run with a full summary at the end.
All 109 tests should pass! ✅


## Generating Javadocs

Want to dig into the codebase? Generate the full documentation with:

```bash
mvn javadoc:javadoc
```

Then open this file in your browser to explore all classes and methods:
target/site/apidocs/index.html


## Build + Test in One Go

```bash
mvn clean install
```

This compiles everything, runs all 109 tests, and packages the JAR in one shot.


## Project Structure
Game/src/main/java/robtheblock/
├── camera/          # Camera system that follows the player
├── characters/      # Burglar (player), Position, Direction
│   └── enemies/     # Dog, Homeowner, Enemy base class, ChaseAlgorithm
├── level/           # Level logic, Tile system, PatrolFactory
│   ├── data/        # Wall map data for each level
│   └── levels/      # Level1, Level2, Level3, Level4
├── objects/         # Rewards, Punishments, DogToy, GameObject
├── rendering/       # GameDraw, TextureRenderer, UI
├── Game.java        # Main game loop and state management
├── InputKeys.java   # Keyboard input handler
└── Main.java        # Entry point


## Troubleshooting

- **Game won't launch?** Make sure you're running the command from inside
  the `Game/` folder, not the root of the repo.
- **Maven not found?** Double check Maven is installed and added to your PATH.
- **Java version issues?** This project requires JDK 21. Older versions won't work.
- **Tests failing?** Run `mvn clean test` (the `clean` matters — stale build
  files can sometimes cause issues).