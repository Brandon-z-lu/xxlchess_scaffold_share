# INFO1113 Assignment: XXLChess

[toc]

## TODO

- [X] AI basic
- [X] Combination done!
- [x] Correct orientation of pawns and beselected highlight and promotion and first 2 moves
- [x] Add the promotion and 
- [ ] Revamp the AIShow to show all to implement "checkmate": new and small functions would be great
- [ ] Explore javadoc
- [ ] Explore java test suite
- [ ] AI point system
  
## FAQ

### Orientation

> it's not invalid - the purpose of having the level file is so any arrangement of pieces can be used as a starting position on the board (for instance, to solve chess puzzles)

The layout file says which pieces should be placed where and what their colour is. They don't have to always be at the bottom or at the top. That's just the default sample config. Imagine a layout which is already in the endgame. In order to determine pawn movement, we always make the human player's pawns move upwards, to maintain the best user experience, regardless of whether the user is black or white. The computer player's pawns therefore move downwards.

Your idea about switching the board around if the human player is black is actually a good idea and admittedly I didn't consider this myself so neglected to include it in the specification. **Unfortunately it's too late to change now that students have already started working on the assignment. But it could have been something like this:**

> If the player colour is white, the layout txt file is displayed in the orientation it is given (top-down left-right) where the top right corner is the same as in the file and on the screen. If however the player colour is black, then rotate the layout by 180 degrees so that the top-right corner of the layout file is now the bottom-left corner of the display on the board.
This would have added additional complexity and maybe confused some students though (along with the design considerations that go along with having to make a layout from black's perspective in the txt file that gets flipped on the screen).

### Piece movement

I noticed that in the assignment specification, it said 
> For the purposes of pawn movement, “forward” is considered going up the board for the human player, and going down the board for the computer player. 

Is that means whatever the layout and player's color, the player's pawn always moves upward even if the config file set a player's pawn at the top of the board at the beginning?

**yes. If you want to make a board layout where the human player plays as black, then in the level.txt file, make the pieces on the bottom uppercase and the ones on the top lowercase.**

### How do I use JSON?

Processing has JSON parsing built in. You will want to look at the processing.data package in the javadoc for more information.

### Can I use other libraries such as JavaFX?

No, as one of the main ideas of this assignment is we are providing you with a library that has an excellent Javadoc, minimal online resources and is unfamiliar to everyone, which is something that happens fairly frequently in the workplace.


## Processing intro

### Links
https://www.toptal.com/game/ultimate-guide-to-processing-the-fundamentals

### High-level intro

#### Java-ish
```
public void setup() {
  // setup codes goes here
}
public void draw() {
  // draw codes goes here
}
```
These code blocks will be converted into something like this:

```
public class ExampleFrame extends Frame {

     public ExampleFrame() {
         super("Embedded PApplet");

         setLayout(new BorderLayout());
         PApplet embed = new Embedded();
         add(embed, BorderLayout.CENTER);

         embed.init();
     }
}

public class Embedded extends PApplet {
   
    public void setup() {
      // setup codes goes here
    }
    public void draw() {
      // draw codes goes here
    }
    
}
```
You can see that the processing code block was wrapped with a class that extends from Java’s PApplet. Therefore, all the classes you define in your processing code, if any, will be treated as inner classes.

#### `line()`: drawing a line
![uPic_2023Y-04M-13D_23H-12MIN-40S_Kpdaxf](https://raw.githubusercontent.com/Brandon-Lu737/Shared_Public_Pic_Hosting/master/uPic_2023Y-04M-13D_23H-12MIN-40S_Kpdaxf.jpg)

### Walkthrough
#### How to load images
E.g. We now want to load a dinosaur sprite.

First, at the beginning the of class
```
private PImage sprite
```

In the `setup()`,

```
# Loading the sprite
this.sprite = this.loadImage("src/main/resources/dino1.img") 
```
Then in `draw()`,
we can call the `image()`
```
# Drawig the sprite
this.image(this.image, 0, 0); 
# The `image` is a part of the `PApplet` class
```
#### Then go to create the `Dino.java`

For the constructor,
```
package ...;

import processing.core.PImage;

public class Dino {
  private int x;
  ...

  private PImage sprite;

  public Dino(int x, int y, PImage sprite) {
    this.x = x;
    this.y = y;
    this.sprite = sprite;
  }
  
  // Handles logic
  public void tick() {

  }

  // Handles graphics
  public void draw(PApplet app) {
    app.image(this.sprite, this.x, this.y)
  }

}

```
#### Create instance

We also create an instance of it in the `setup()`.

Then do `this.dino.tick()` and `.draw()` in `draw()`

#### In `public void draw()`

We can `import PApplet` and pass the `App app` into the `public void draw(Papplet app)
```
public void draw(PApplet app) {
  app.image(this.sprite, this.x, this.y);
}
```
Because we're just passing in the memory address but not the actual `object`, this still makes sense to pass it in for every frame you draw.

#### Handling logic: jump method
We create `public void jump()` inside the `Dino` class:
```
public void jump() {
  // Set up a ground variable and a y velocity in the Dino constructor
  // this.ground = y;
  // this.yVel = 0;

  this.yVel = 10;

}
```
In the `tick()` method.

**`tick()` is used to refresh some attributes every time a new frame is called.**
```
public void tick() {
  this.y += this.yVel;
}
```
Note that we notice that it's not refreshing
```
public void draw() {
  ...
  // To ensure full coverage:
  this.rect(-1, -1 WIDTH + 2, HEIGHT + 2);
  ...
}
```
To account for gravity
```
public void tick() {
  this.y -= this.yVel;

  if (this.y > this.ground) {
    this.y = this.ground;
    this.yVel = 0;
  } else {
    this.yVel--;
  }

}
```
Now we want to incorporate the `jump()` into the commands.

We can use `mouseClicked()`, `mouseExit()`, `mousePressed()` (then move the cursor down).

We now update in the `App.java`
```
public void mousePressed() {
  // Calling the `jump()` method
  this.dino.jump();
  /*
   * This basically updates the yVel variable 
   * and allow for the new dino to be updated
   */ 
}
```
We can refine it by introducing some error handling, which will be omitted here.
```
Only allow the jump when it hits the ground
```

#### Construct a test case for it
Create a `DinoTest.java` in the `Test` folder.
```
package ...;

import org.junit.Test;
import static org.junit.Assert.*;

public class DinoTest {
  // Test annotation
  @Test
  public void constructor() {
    assertNotNull(new Dino(30, 200, null));
  }
}
```
You can view the `test result` in a website format `index.html`.

To make a another one:
```
@Test
public jump?
```
We can return `bool` inside the jump method. And we use this return value to decide if it was successfully run.
```
@Test
public void jumpTest() {
  Dino dino = new Dino(30, 200, null);
  assertTrue(dino.jump());
}
/* 
 * Note that we revise the return type of the `jump()` method
 */
```

To use the `jacoco`: we can go to that folder.

We can see if the methods are tested. Seeing the test coverage.

We can this **if the lines are *missed*.**

We want to **have as high test coverage as possible** (at least 90%. 98% for Google).


## Build structure

### `build.gradle`
- plugin
  - `java`
  - `application`
  - `jacoco`: to run test cases
- repositories
  - `jcenter()`
- dependencies
  - `com.google.guava` google standard library
  - `junit`: to test `java` code
  - `org.processing:core`: processing dependency
- application
  - define the `main` class


## `gradle commands`
- `gradle run`
- `gradle test`
- `gradle jacocoTestReport`

## Type Casting: super-class and sub-class (inheritance)

### https://stackoverflow.com/questions/4862960/explicit-casting-from-super-class-to-sub-class

> By using a cast you're essentially telling the compiler "trust me. I'm a professional, I know what I'm doing and I know that although you can't guarantee it, I'm telling you that this `animal` variable is definitely going to be a `dog`."

> Since the `animal` isn't actually a `dog` (it's an animal, you could do` Animal animal = new Dog()`; and it'd be a `dog`) the VM throws an exception at runtime because you've violated that trust (you told the compiler everything would be ok and it's not!)

> The compiler is a bit `smarter` than just blindly accepting everything, if you try and cast objects in different inheritence hierarchies (cast a Dog to a String for example) then the compiler will throw it back at you because it knows that could never possibly work.

> Because you're essentially just stopping the compiler from complaining, every time you cast **it's important to check that you won't cause a ClassCastException by using instanceof in an if statement (or something to that effect.)**

### Type casting source:

https://www.baeldung.com/java-type-casting

### Arrays.copyOf(this.coordinates, this.coordinates.length);

### `public void pieceClicked(App app)` just because missing one variable. And the terminal doesn't give any hints as to what's wrong



# Arch-TODO
### Background
  - [X] Board 
  - [X] Timer

### Update dynamics
  - [ ] Prev state
    - [ ] Time
    - [ ] Pieces
  - [ ] Next state
    - [ ] Time +1 s use the `modulus` tool
    - [ ] Pieces: move to new places
  - [ ] Sidebar
    - [ ] Chess clock
    - [ ] Win/lose status
  - [ ] Pieces
    - [ ] Inheriting from the last stable `state`
    - [ ] **Classes for different movements**
      - [ ] diagonal
      - [ ] horonzontal/vertical: use an extra argument to select the right mode
      - [ ] King's move
    - [ ] **All behaviors for all kinds of elements**: `private static` passing in arguments like what we did with the `DrawBackground`
      - [ ] Highlight
      - [ ] Moving: mouse click
    - [ ] Detect after the `click` state, moving the pieces and updating the pieces state
  - [ ] Human
    - [ ] Look at `Pieces`
  - [ ] AI
    - [ ] Look at `Pieces`

### Class structure
- background
- state `to be drawn`
  - prev
  - current
  - next
- sidebar
  - update time from `prev`
- pieces
  - [ ] **Classes for different movements**
    - [ ] diagonal
    - [ ] horonzontal/vertical: use an extra argument to select the right mode
    - [ ] King's move
  - update from `prev`
  - elements
    - highlight
    - moving
  - refresh after the `click` state
- human * 2
  - human A
  - human B
- ai
  - analyze all possible solutions
  - calculate points
  - compare points and decide
  - refresh after the decision

![uPic_2023Y-04M-12D_00H-49MIN-12S_t0cia3](https://raw.githubusercontent.com/Brandon-Lu737/Shared_Public_Pic_Hosting/master/uPic_2023Y-04M-12D_00H-49MIN-12S_t0cia3.png)

### Advice
- Has a `GameObj` class to display them

### Debugging
- might: issues with `DrawBackground.java` for missing some importing statements; I don't really know whether or not I should import them either

