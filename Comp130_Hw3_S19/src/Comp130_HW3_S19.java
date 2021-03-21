
/* Author Name:Ekrem Yiðiter
 * This program implements a SOS application where
 * users are randomly placed on a map with x, y coordinates.
 * The program randomly send a SOS signal for one of the users.
 * It find 2 other users closest to the help seeker.
 * The program directs the people who decides to help to the
 * help seeker by moving them on the map.
 */
import java.awt.Color;
import acm.graphics.*;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class Comp130_HW3_S19 extends GraphicsProgram {

	public void run() {
		this.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		// Your code starts here
		add(mapLayout());
		for (int i = 1; i <= 5; i++) {
			switch (i) {
			case 1:
				user1 = createUser();
				user1.setFilled(true);
				user1.setFillColor(Color.BLUE);
				add(user1);
				GLabel one = new GLabel("1", user1.getX(), user1.getY());
				add(one);
				break;
			case 2:
				user2 = createUser();
				while (checkLoc(user1,user2)) {
					user2 = createUser();
				}
				user2.setFilled(true);
				user2.setFillColor(Color.BLUE);
				add(user2);
				GLabel two = new GLabel("2", user2.getX(), user2.getY());
				add(two);
				break;
			case 3:
				user3 = createUser();
				while (checkLoc(user3,user1) || checkLoc(user3,user2)) {
					user3 = createUser();
				}
				user3.setFilled(true);
				user3.setFillColor(Color.BLUE);
				add(user3);
				GLabel three = new GLabel("3", user3.getX(), user3.getY());
				add(three);
				break;
			case 4:
				user4 = createUser();
				while (checkLoc(user4,user1) || checkLoc(user4,user2) || checkLoc(user4,user3)) {
					user3 = createUser();
				}
				user4.setFilled(true);
				user4.setFillColor(Color.BLUE);
				add(user4);
				GLabel four = new GLabel("4", user4.getX(), user4.getY());
				add(four);
				break;
			case 5:
				user5 = createUser();
				while (checkLoc(user5,user1) || checkLoc(user5,user2) || checkLoc(user5,user3) || checkLoc(user5,user4)) {
					user3 = createUser();
				}
				user5.setFilled(true);
				user5.setFillColor(Color.BLUE);
				add(user5);
				GLabel five = new GLabel("5", user5.getX(), user5.getY());
				add(five);
				break;
			default:
				println("There is someting switch with rgen!!!");
				break;
			}
		}
		pickInNeedOfHelp();
		findClosest(redUser);
		askForHelp();
		printStatus();
		// Your code ends Here
	}

	/*
	 * GIVEN METHODS: There are methods provided for each part. Itâ€™s up to you
	 * whether or not to use these methods. You may implement methods by yourself in
	 * the Helper Methods section. If you decide not to use some methods provided,
	 * please make sure that every part is handled with at least one method.
	 */

	/* PART 1 */
	/**
	 * This method creates a compound consisting of streets and avenues
	 * 
	 * @return map created
	 */
	private GCompound mapLayout() {
		GCompound map = new GCompound();
		int width = 0;
		int height = 0;
		while (getHeight() > height) {
			while (getWidth() > width) {
				width += STREET_SIZE;
				GLine vertical = new GLine(width, 0, width, getHeight());
				map.add(vertical);
			}
			height += STREET_SIZE;
			GLine horizontal = new GLine(0, height, getWidth(), height);
			map.add(horizontal);
		}
		return map;
	}
	/* PART 2 */

	/**
	 * This method creates an circle to simulate the user. The method arbitrarily
	 * decides on the location of the circle.
	 * 
	 * @return GOval object which represent the user created
	 */
	private GOval createUser() {
		int x = rgen.nextInt(0, 10);
		int y = rgen.nextInt(0, 6);
		GOval users = new GOval(x * STREET_SIZE - USER_RADIUS / 2, y * STREET_SIZE - USER_RADIUS / 2, USER_RADIUS,
				USER_RADIUS);
		return users;
	}

	/* PART 3 */

	// Step 1:
	/**
	 * This method arbitrarily decided on which user needs help and color it to RED
	 * 
	 * @return GOval object which is selected as the user who needs help
	 */
	private GOval pickInNeedOfHelp() {
		switch (rgen.nextInt(1, 5)) {
		case 1:
			redUser = user1;
			user1 = redUser;
			break;
		case 2:
			redUser = user2;
			user2 = redUser;
			break;
		case 3:
			redUser = user3;
			user3 = redUser;
			break;
		case 4:
			redUser = user4;
			user4 = redUser;
			break;
		case 5:
			redUser = user5;
			user5 = redUser;
			break;
		default:
			redUser = user1;
			break;
		}
		redUser.setFillColor(Color.RED);
		return redUser;

	}

	// Step 2:
	/**
	 * This method find the closest persons who can help a help seeker.
	 * 
	 * @param helpSeeker the user who is identified as the help seeker
	 */
	private void findClosest(GOval helpSeeker) {
		double x = redUser.getX();
		double y = redUser.getY();
		int borderSize = USER_RADIUS;
		GRect radar = new GRect(x, y, borderSize, borderSize);
		radar.setColor(Color.RED);
		add(radar);
		while (!squareChecker(radar)) {
			borderSize += 40;
			x -= 20;
			y -= 20;
			radar = new GRect(x, y, borderSize, borderSize);
			radar.setColor(Color.RED);
			add(radar);
			pause(PAUSE_TIME);
		}

	}

	// Step 3:
	/**
	 * The method which asks for help from the users who are closest to the help
	 * seeker
	 */
	private void askForHelp() {
		int seeker = 0;
		if (user1 == redUser) {
			seeker = 1;
		} else if (user2 == redUser) {
			seeker = 2;
		} else if (user3 == redUser) {
			seeker = 3;
		} else if (user4 == redUser) {
			seeker = 4;
		} else if (user5 == redUser) {
			seeker = 5;
		}
		println("The help seeker " + seeker + " is at " + redUser.getX() + " " + redUser.getY());
		boolean answer1 = readBoolean(help1 + " - Someone is in need of yout help, can you help (enter true for Yes, false for No)?");
		while (answer1 != true && answer1 != false) {
			answer1 = readBoolean(help1 + " - Someone is in need of yout help, can you help (enter true for Yes, false for No)?");
		}
		if (answer1 == true) {
			helper1.setFillColor(Color.GREEN);
			moveHelper(helper1);
			println("Thanks for helping out!");
			trueFalse=true;
		} else if (answer1 == false) {
			helper1.setFillColor(Color.BLUE);
		}
		boolean answer2 = readBoolean(help2 + " - Someone is in need of yout help, can you help (enter true for Yes, false for No)?");
		while (answer2 != true && answer2 != false) {
			answer2 = readBoolean(help2 + " - Someone is in need of yout help, can you help (enter true for Yes, false for No)?");
		}
		if (answer2 == true) {
			helper2.setFillColor(Color.GREEN);
			moveHelper(helper2);
			println("Thanks for helping out!");
			trueFalse=true;
		} else if (answer2 == false) {
			helper2.setFillColor(Color.BLUE);
		}

	}

	/* PART 4 */
	/**
	 * This method moves the user who accepted to help in an animation
	 * 
	 * @param helper GOval object which is identified as the user who is willing to
	 *               help out.
	 */
	private void moveHelper(GOval helper) {
		double seekX = redUser.getX();
		double seekY = redUser.getY();
		double helpX = helper.getX();
		double helpY = helper.getY();
		while (helpX != seekX || helpY != seekY) {
			while (helpY != seekY) {
				if (seekY < helpY) {
					helper.move(0, -1);
					helpY = helper.getY();
					pause(PAUSE_TIME);
				} else if (seekY > helpY) {
					helper.move(0, 1);
					helpY = helper.getY();
					pause(PAUSE_TIME);
				}
			}
			if (helpX < seekX) {
				helper.move(1, 0);
				helpX = helper.getX();
				pause(PAUSE_TIME);
			} else if (helpX > seekX) {
				helper.move(-1, 0 );
				helpX = helper.getX();
				pause(PAUSE_TIME);
			}
		}
		helper.setFillColor(Color.RED);
	}

	/* PART 5 */
	/**
	 * The method which prints whether a help is provided to the help seeker or not.
	 */
	private void printStatus() {
		if (trueFalse) {
			println("The person has been helped!");
		}else {
			println("Nobody was available to help.");
			redUser.setFillColor(Color.black);
		}
	}

	private boolean squareChecker(GRect a) {
		double left = a.getX();
		double right = left + a.getWidth();
		double up = a.getY();
		double down = up + a.getHeight();

		double x = user1.getX();
		double y = user1.getY();
		if (x < right && x > left && y > up && y < down && user1 != redUser && user1 != helper1 && user1 != helper2) {
			if (helper1 == null) {
				user1.setFillColor(Color.YELLOW);
				helper1 = user1;
				user1 = helper1;
				help1 = "User 1";
				counter++;
			} else if (helper2 == null) {
				user1.setFillColor(Color.YELLOW);
				helper2 = user1;
				user1 = helper2;
				help2 = "User 1";
				counter++;
			}
		}
		x = user2.getX();
		y = user2.getY();
		if (x < right && x > left && y > up && y < down && user2 != redUser && user2 != helper1 && user2 != helper2) {
			if (helper1 == null) {
				user2.setFillColor(Color.YELLOW);
				helper1 = user2;
				user2 = helper1;
				help1 = "User 2";
				counter++;
			} else if (helper2 == null) {
				user2.setFillColor(Color.YELLOW);
				helper2 = user2;
				user2 = helper2;
				help2 = "User 2";
				counter++;
			}
		}
		x = user3.getX();
		y = user3.getY();
		if (x < right && x > left && y > up && y < down && user3 != redUser && user3 != helper1 && user3 != helper2) {
			if (helper1 == null) {
				user3.setFillColor(Color.YELLOW);
				helper1 = user3;
				user3 = helper1;
				help1 = "User 3";
				counter++;
			} else if (helper2 == null) {
				user3.setFillColor(Color.YELLOW);
				helper2 = user3;
				user3 = helper2;
				help2 = "User 3";
				counter++;
			}
		}
		x = user4.getX();
		y = user4.getY();
		if (x < right && x > left && y > up && y < down && user4 != redUser && user4 != helper1 && user4 != helper2) {
			if (helper1 == null) {
				user4.setFillColor(Color.YELLOW);
				helper1 = user4;
				user4 = helper1;
				help1 = "User 4";
				counter++;
			} else if (helper2 == null) {
				user4.setFillColor(Color.YELLOW);
				helper2 = user4;
				user4 = helper2;
				help2 = "User 4";
				counter++;
			}
		}
		x = user5.getX();
		y = user5.getY();
		if (x < right && x > left && y > up && y < down && user5 != redUser && user5 != helper1 && user5 != helper2) {
			if (helper1 == null) {
				user5.setFillColor(Color.YELLOW);
				helper1 = user5;
				user5 = helper1;
				help1 = "User 5";
				counter++;
			} else if (helper2 == null) {
				user5.setFillColor(Color.YELLOW);
				helper2 = user5;
				user5 = helper2;
				help2 = "User 5";
				counter++;
			}
		}
		return counter == 2;

	}
	private boolean checkLoc(GOval a, GOval b) {
		return a.getX()==b.getX() && a.getY()==b.getY();
	}

	// Standard Java entry point /
	// This method can be eliminated in most Java environments
	public static void main(String[] args) {
		new Comp130_HW3_S19().start(args);
	}

	// Given Variables
	private GOval user1;
	private GOval user2;
	private GOval user3;
	private GOval user4;
	private GOval user5;
	RandomGenerator rgen = new RandomGenerator().getInstance();

	// CONSTANTS THAT SHOULD BE USED
	private static final int APPLICATION_WIDTH = 1000; // width of the application
	private static final int APPLICATION_HEIGHT = 732; // height of the application
	private static final int NUMBER_OF_HELPERS = 2; // number of users who will be selected to help another user
	private static final int NUMBER_OF_USERS = 5; // number of users who will be shown in the map
	private static final int STREET_SIZE = 100; // the width and height of the streets
	private static final int USER_RADIUS = 20; // the radius of the circle that will be used to represent user
	private static final int PAUSE_TIME = 40; // pause time that will be used in the animation

	/* Additional Variables and Constants */
	// Your code starts here
	private GOval redUser;
	private GOval helper1;
	private GOval helper2;
	private static int counter = 0;
	private static String help1 = "";
	private static String help2 = "";
	private boolean trueFalse = false;

	// Your code ends here
}