# Connect Four

A modern implementation of the classic Connect Four game with customizable board sizes and an elegant user interface.

## Game Description

Connect Four is a two-player strategy game where players take turns dropping colored discs into a vertically suspended grid. The objective is to be the first to form a line of four discs of your color horizontally, vertically, or diagonally.

## Game Features

* Two-player gameplay with Navy (X) and Turquoise (O) discs
* Configurable board sizes (8x5, 10x6, 12x7)
* Visual player turn indicators
* Win detection with animated golden highlighting
* Modern, flat UI design with hover effects
* Automatic new game option after win/draw

## Technical Implementation

### Architecture
Built using Model-View-Controller (MVC) architecture:
* **Model**: Handles game logic and state
* **View**: Custom-drawn components using Java2D
* **Controller**: Manages user interactions and updates

### Key Components

#### Board Configuration
* Dynamic resizing while maintaining UI proportions
* Maintains circular piece aspect ratio
* Multiple standard board size support

#### Visual Design
* Modern flat UI design
* Circular disc slots with smooth edges
* Animated winning highlights with golden glow effect
* Responsive hover effects on interface elements

### Core Classes

#### Field Class
* Manages individual board positions
* Handles player symbols and colors
* Provides state checking functionality

#### Board Class
* Implements game logic and move validation
* Win condition detection
* Game state management

#### GameBoardGUI Class
* Handles user interactions
* Updates visual game state
* Manages animations and highlights

#### ConnectFourGUI Class
* Creates and manages the main game interface
* Handles menu system
* Manages board size changes

#### CircleButton Class
* Custom-drawn circular buttons
* Handles highlighting and animations
* Manages visual states

## How to Play

1. Players take turns clicking on a column to drop their disc
2. Discs fall to the lowest available position in the chosen column
3. First player to align four discs wins
4. Game ends in a draw if the board fills up without a winner

## Test Cases

The game has been thoroughly tested with:
* Basic gameplay mechanics
* Win conditions (horizontal, vertical, diagonal)
* Draw conditions
* Invalid move prevention
* UI responsiveness
* Animation smoothness
* Menu functionality

## System Requirements

* Java Runtime Environment (JRE)
* Graphical display support
* Minimum resolution to accommodate largest board size (12x7)

## Future Enhancements

Potential areas for future development:
* Network multiplayer support
* AI opponent
* Custom board sizes
* Score tracking
* Game replay functionality

## License
 This project is licensed under the Apache License.
 
## Author
Joseph Paul Koyi