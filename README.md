# INFO1113 Assignment: chessset
- [INFO1113 Assignment: chessset](#info1113-assignment-chessset)
  - [Basics **by this noon**](#basics-by-this-noon)
    - [Background](#background)
    - [Update dynamics](#update-dynamics)
    - [Class structure](#class-structure)
    - [Advice](#advice)
    - [Debugging](#debugging)
  - [Processing intro](#processing-intro)
    - [Links](#links)
    - [Java-ish](#java-ish)
    - [`line()`: drawing a line](#line-drawing-a-line)

## Basics **by this noon**

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


## Processing intro

### Links
https://www.toptal.com/game/ultimate-guide-to-processing-the-fundamentals

### Java-ish
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

### `line()`: drawing a line
![uPic_2023Y-04M-13D_23H-12MIN-40S_Kpdaxf](https://raw.githubusercontent.com/Brandon-Lu737/Shared_Public_Pic_Hosting/master/uPic_2023Y-04M-13D_23H-12MIN-40S_Kpdaxf.jpg)