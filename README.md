Battleship
----------

Build an API for simulating a game of ["Battleship"](http://en.wikipedia.org/wiki/Battleship_(game)).

#### Requirements

Maintain the state of a game of "Battleship", including four boards,
two for each player, one for recording the current state of the
players ships, and one for recording the players' attacks. We want to
take care of the basic ability to control the game, and the players
moves whilst keeping of track of this game state.

__Don't__ worry about trying to encode a simulation for the full
rules of "Battleship", we just require the APIs so that someone could
write an automated simulator or AI for a complete game on top.

In battleship, a board consists of a grid of 10 x 10, labelled
vertically with the numbers 1 to 10 (from top to bottom) and labelled
horizontally with the letters a to j (from left to right).

A player knows:

 - where their own ships are

 - where their own previous attacking moves have been made, and their result

 - where their opponent's previous attacking moves have been made and their result

To play the game, each player, plays an attacking move in turn, the
result of this may be a "hit" if the square is occupied by an
opponent's ship, or a "miss" if the square is not. A player wins when
they have "hit" every square occupied by their opponents ships.

Each player should start the game with 5 ships, laid out in
non-overlapping positions on their own board:

| Ship       | Size (in squares) |
| ---------- | ----------------- |
| Carrier    | 1 x 5             |
| Battleship | 1 x 4             |
| Submarine  | 1 x 3             |
| Cruiser    | 1 x 2             |
| Patrol     | 1 x 1             |


The minimal set of operations we want to support:

 - Create an empty board.

 - Place a ship on the board.

 - Create a random board with the ships already placed.

 - Make an attacking move, determining if it is a hit or a miss and
   updating the game state.

 - Determine the current state of the game, finished (and who won),
   in play.


#### Running

Download leiningen (install, make executable and add to PATH): https://github.com/technomancy/leiningen

Run tests: `lein midje`

Create executable jar: `lein uberjar`
