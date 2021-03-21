
/* Student Name: 
 * File Name: Comp130_HW1_S19.java
 * Karel is traveling in space and collecting gems and food.
 * This assignment consists of 4 main parts. In part 1, Karel needs to collect stones
 * from the mini versions of planet, place them to her spaceship and eat red space
 * foods on her way. In part 2, she needs to beam into the surface of a planet
 * and find the tunnel by digging the floor. In Part 3, she needs to find a way to
 * go through the tunnel; ascend and descend the pyramid to go inside it and get
 * the stone from the sea creature. In Part 4, she has to bounce back to her planet.
 * 
 */
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import stanford.karel.SuperKarel;

public class Comp130_HW1_S19 extends SuperKarel {

	private static final long serialVersionUID = 1L;

	public void run() {
		playThemeSong(THEME_SONG);// You can delete this line if you don't want to play the theme song.
		// Your code starts here.
		while (notFacingNorth()) {
			turnRight();
		}
		while (frontIsClear()) {
			move();
		}
		turnLeft();
		searchThroughTheSpace();
		putAndDivide();
		goToTeleportation();
		teleport();
		dig();
		goThroughTheTunnel();
		goToTheGate();
		getTheStone();
		exitThePyramid(); 
		goBackToThePlanet();
		// Your code ends here.
	}

	/** Helper Methods */
	// Your code starts here.
	private void searchThroughTheSpace() { 
		while (!cornerColorIs(CYAN)) { //search until the ship.
			if (noBeepersPresent() && !cornerColorIs(RED) && frontIsClear()) { //given conditions on the pdf.
				move();
			}else if (noBeepersPresent() && cornerColorIs(RED)) {
				paintCorner(DARK_GRAY);
			}else if (beepersPresent() && cornerColorIs(YELLOW)) {
				pickBeeper();
			}else if (beepersPresent() && !cornerColorIs(RED) && frontIsClear()) {
				move();
			}
			if (frontIsBlocked() && facingWest()) {
				turnLeft();
				move();
				turnLeft();
			}else if (frontIsBlocked() && facingEast()) {
				turnRight();
				move();
				turnRight();
			}
		}
	}
	private void putAndDivide() {
		move();
		if (facingEast()) { //since the robot does not encounter with 
			turnRight();    //the ship in the same direction for all the times
		}else if (facingWest()) {
			turnLeft();
		}
		move();
		while (beepersInBag()) {
			putBeeper();
		}
		while (beepersPresent()) {	
			if (beepersPresent()) {
				putBeeperToLeft();
			}
			if (beepersPresent()) {
				putBeeperIntoMiddle();
			}
			if (beepersPresent()) {
				putBeeperToRight();
			}
		}
	}
	private void putBeeperToLeft() {
		pickBeeper();
		while (!cornerColorIs(GRAY)) {
			move();
		}
		turnRight();
		move();
		move();
		putBeeper();
		turnAround();
		move();
		move();
		turnLeft();
		while (!cornerColorIs(DARK_GRAY)) {
			move();
		}
		turnAround();
	}
	private void putBeeperIntoMiddle() {
		pickBeeper();
		while (!cornerColorIs(GRAY)) {
			move();
		}
		putBeeper();
		turnAround();
		while (!cornerColorIs(DARK_GRAY)) {
			move();
		}
		turnAround();
	}
	private void putBeeperToRight() {
		pickBeeper();
		while (!cornerColorIs(GRAY)) {
			move();
		}
		turnLeft();
		move();
		move();
		putBeeper();
		turnAround();
		move();
		move();
		turnRight();
		while (!cornerColorIs(DARK_GRAY)) {
			move();
		}
		turnAround();
	}
	private void goToTeleportation() { //go to second leg
		while (!cornerColorIs(GRAY)) {
			move();
		}
	}
	private void teleport() {
		move();
		while (cornerColorIs(CYAN)) {
			paintCorner(BLUE);
			paintCorner(CYAN);
			move();
		}
	}
	private void dig() {
		while (!cornerColorIs(GREEN)) { //since the ground colour is different in every planet
			paintCorner(GRAY);			//it goes until the tunnel
			move();
		}
	}
	private void goThroughTheTunnel() {
		while (cornerColorIs(GREEN)) {
			if (frontIsClear()) {
				move();
			}else if (frontIsBlocked() && leftIsBlocked()) {
				turnRight();
			}else if (frontIsBlocked() && rightIsBlocked()) {
				turnLeft();
			}else if (frontIsBlocked() && rightIsBlocked() && leftIsBlocked()) {
				move();
			}
		}
	}
	private void goToTheGate() {
		goUp();
		goDown();
		turnAround();
		move();
	}
	private void goUp() {
		while (frontIsClear()) {
			move();
		}
		while (frontIsBlocked()) {
			turnLeft();
			move();
			turnRight();
			move();
		}
	}
	private void goDown() {
		while (rightIsBlocked() && frontIsClear()) {
			move();
			turnRight();
			move();
			turnLeft();
		}
	}
	private void getTheStone() {
		while (!cornerColorIs(RED)) {
			move();
		}
		turnRight();
		move();
		turnLeft();	//check the left hand first
		move();
		turnAround();
		if (beepersPresent()) { //skips right hand
			pickBeeper();
			move();
			move();
		}else {		//check the right hand
			move();
			move();
			pickBeeper();
		}	
	}
	private void exitThePyramid() {
		turnRight();
		move();
		turnLeft();
		while (frontIsClear()) {	//go until the wall
			move();
		}
	}
	private void goBackToThePlanet() {
		turnLeft();
		while (!cornerColorIs(DARK_GRAY)) {	//since colour choices are different for different planets
			move();							//it goes until the space
		}
		while (!cornerColorIs(PINK)) {		//then to the planet
			move();
		}
		turnLeft();
		while (!cornerColorIs(ORANGE)) {	//andits original place
			move();
		}
	}
	// Your code ends here.

	/** ----- Do not change anything below here. ----- */

	/**
	 * This method plays the given audio file file continously in a loop.
	 * 
	 * @param fileLocation
	 *            Location of the audio file.
	 */
	private void playThemeSong(String fileLocation) {
		try {
			inputStream = AudioSystem.getAudioInputStream(new File(fileLocation));
			clip = AudioSystem.getClip();
			clip.open(inputStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/* Constant Instant Variable*/
	private static final String THEME_SONG = "jupiter.wav";
	/*Class Instance Variables*/
	private Clip clip;
	private AudioInputStream inputStream;
}
