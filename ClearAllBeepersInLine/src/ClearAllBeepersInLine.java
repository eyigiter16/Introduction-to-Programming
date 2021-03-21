/* STUDENT NAME:Ekrem Yigiter
 * File: ClearAllBeepersInLine.java
 * 
 * Karel clears all beepers on the way and goes back to her initial position.
 */

import stanford.karel.*;

public class ClearAllBeepersInLine extends Karel {

	public void run() {
		//Your code starts here
while (frontIsClear()) {
	collectBeepers();
	goToBeepers();
	goBack();
}
turnAround();
		//Your code ends here
	}
	
	//Your helper methods go here (if needed):
	//Your code starts here
private void collectBeepers() {
	while (beepersPresent()) {
		pickBeeper();
	}
}
private void goToBeepers() {
	if (frontIsClear()) {
		if (noBeepersPresent()) {
			move();
		}
	}
}
private void goBack() {
	while (beepersPresent()) {
		collectBeepers();
	}
	if (frontIsBlocked()) {
			turnAround();
			while (frontIsClear()) {
				move();
			}
		}
	}
private void turnAround() {
	turnLeft();
	turnLeft();
}
	//Your code ends here
}