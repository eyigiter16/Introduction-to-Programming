
/**
 * Author Name:Ekrem Yiðiter
 * This program is a graphics program consisting GUI components and allows 
 * interaction with the user through GUI components.
 * The program reads user names from a file and populates the combo box with the names
 * in the file.
 * The file contains the names of people and their coordinates in X, Y. Once read the 
 * coordinates, the users are simulated with an image on the screen.
 */
import acm.graphics.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import acm.gui.*;
import acm.program.*;
import acm.util.ErrorException;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Comp130_Hw4_S19 extends GraphicsProgram {
	/**
	 * Class Instance Variables and Constants
	 */

	private JLabel title;
	private JLabel infoLabel;
	private JLabel nameLabel;
	private JLabel coordinates;
	private JComboBox<String> aquaSelectCombo;
	private JComboBox<String> fishSelectCombo;

	private DoubleField weightField;
	private DoubleField costField;
	private JTextField nameField;
	private JTextField coordinatesField;
	private JButton selectButton;
	private JButton editCoordinate;
	private JButton scaleButton;
	private JButton deleteButton;
	private JButton updateButton;
	private JSlider slider;
	private JButton newFish;
	private JButton animateButton;
	private JButton saveButton;
	private JButton reportButton;
	
	GRect rect;
	private int defineTheSelected ;
	private static int jcomboSayar = 5;
	boolean animate = false;
	boolean select1 = false;

	private int mouseX;
	private int mouseY;
	private String name = "";
	private String currentJob = "";
	int index = 0;
	boolean check = false;
	ArrayList<String> names;
	ArrayList<GPoint> locations;
	ArrayList<GRect> rects;
	ArrayList<String> infoCol;
	ArrayList<Double> weightList;
	ArrayList<Double> costList;
	ArrayList<Double> totalCostList;
	ArrayList<GImage> fishes;
	ArrayList<String> fishNames;
	ArrayList<String> fishFileNames;
	private static final String FISH_FILE = "fishList.txt";
	private static final String AQUA_FILE = "aquariumCol.txt";
	private static final String SAVE_FILE = "output.txt";
	private static final String REPORT_FILE = "reportFile.txt";

	public void init() {
		setSize(1200, 800);
		playThemeSong(THEME_SONG);// You can delete this line if you don't want to play the theme song.
		GImage back = new GImage("water_underwater.jpg");
		add(back);
		// your code start here
		
		readFishFile(FISH_FILE);
		createFishList();
		readAquaFile(AQUA_FILE);
		createAquaList();
		
		// Your code ends here
		addActionListeners();
		addMouseListeners();
	}

	/**
	 * This is method overwritten to handle the mouse click events
	 */
	public void mouseClicked(MouseEvent e) {
		// your code start here
		if(check) {
			remove(fishes.get(defineTheSelected));
			add(fishes.get(defineTheSelected),e.getX(), e.getY());
			check=false;
			coordinatesField.setText("X:"+ fishes.get(defineTheSelected).getX()+"Y:"+fishes.get(defineTheSelected).getY());
		}
		// Your code ends here
	}

	/**
	 * This is method overwritten to handle the action events
	 */
	public void actionPerformed(ActionEvent e) {
		// your code start here
			if(e.getSource()==newFish) {
				createFishes((String)fishSelectCombo.getSelectedItem());
			}else if (e.getSource()==selectButton) {
				if(select1==true) {
				remove(rect);
				select1 = false;
				}
				defineTheSelected = (int)aquaSelectCombo.getSelectedIndex();
				double height = fishes.get(defineTheSelected).getHeight();
				double width = fishes.get(defineTheSelected).getWidth();
				rect = new GRect(fishes.get(defineTheSelected).getX(),fishes.get(defineTheSelected).getY(),width,height);
				rect.setFilled(true);
				rect.setFillColor(Color.LIGHT_GRAY);
				add(rect);
				select1 = true;
				remove(fishes.get(defineTheSelected));
				add(fishes.get(defineTheSelected));
				name = new String(((String)aquaSelectCombo.getSelectedItem()).toString().substring(2));
				nameField.setText(name);

						costField.setText(costList.get(defineTheSelected).toString());
						coordinatesField.setText("X:"+ fishes.get(defineTheSelected).getX()+"Y:"+fishes.get(defineTheSelected).getY());
						weightField.setText(weightList.get(defineTheSelected).toString());
				
			}else if (e.getSource()==deleteButton) {
				remove(rect);
				aquaSelectCombo.removeItem((String)aquaSelectCombo.getItemAt(defineTheSelected));
				remove(fishes.get(defineTheSelected));
				weightList.set(defineTheSelected, null);
				locations.set(defineTheSelected, null);
			}else if (e.getSource()==editCoordinate) {
				remove(rect);
				check = true;
			}else if (e.getSource()==animateButton) {
				remove(rect);
				
				double dx = (fishes.get(defineTheSelected).getX()) / 1000;
				double dy = (fishes.get(defineTheSelected).getY()) / 1000;
				for (int i = 0; i < 1000; i++) {
					fishes.get(defineTheSelected).move(dx, dy);;
					pause(20);
				}
			}else if (e.getSource()==updateButton) {
				remove(rect);
				updateFishes();
			}else if (e.getSource()==scaleButton) {
				remove(rect);
				double widthFish = fishes.get(defineTheSelected).getWidth();
				double heightFish = fishes.get(defineTheSelected).getHeight();
				weightList.set(defineTheSelected, weightList.get(defineTheSelected));
				if(slider.getValue()==0) {
					fishes.get(defineTheSelected).setSize(widthFish/2, heightFish/2);
					weightList.set(defineTheSelected, weightList.get(defineTheSelected)/2);
					weightField.setText(weightList.get(defineTheSelected).toString());
				}else {
					fishes.get(defineTheSelected).setSize(widthFish*slider.getValue(), heightFish*slider.getValue());
					weightList.set(defineTheSelected, weightList.get(defineTheSelected)*slider.getValue());
					weightField.setText(weightList.get(defineTheSelected).toString());
				}
			}else if (e.getSource()==saveButton) {
				remove(rect);
				saveAquarium();
			}else if (e.getSource()==reportButton) {
				remove(rect);
				generateReport();				
			}
		// Your code ends here

	}

	/**
	 * This method read the FISH_FILE and stores the data
	 */
	private void readFishFile(String fileName) {

		infoCol = new ArrayList<String>();
		try {
			BufferedReader rd = new BufferedReader(new FileReader(fileName));
			while (true) {
				String line = rd.readLine();
				if (line == null)
					break;
				infoCol.add(line);
			}
			rd.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		costList = new ArrayList<Double>();
		fishNames = new ArrayList<String>();
		fishFileNames = new ArrayList<String>();
		for (int i = 0; i<infoCol.size();i++) {
			StringTokenizer tokenizer = new StringTokenizer(infoCol.get(i),";");
			while (tokenizer.hasMoreTokens()) {
				fishNames.add(tokenizer.nextToken());
				fishFileNames.add(tokenizer.nextToken());
				costList.add(Double.parseDouble(tokenizer.nextToken()));
			}
		}
	}
	/**
	 * This method create the combo box with the fish names given in the FISH FILE
	 */
	private void createFishList() {
		// your code start here
		newFish = new JButton("New Fish");
		fishSelectCombo = new JComboBox<String>();
		for(int i = 0; i<fishNames.size();i++) {
			fishSelectCombo.addItem(fishNames.get(i));
		}
		add(fishSelectCombo, EAST);
		add(newFish,EAST);
		// Your code ends here
	}

	/**
	 * This method read the AQUA_FILE and stores the data
	 */
	private void readAquaFile(String fileName) {
		// your code start here
		infoCol.clear();
		try {
			BufferedReader rd = new BufferedReader(new FileReader(fileName));
			while (true) {
				String line = rd.readLine();
				if (line == null)
					break;
				infoCol.add(line);
			}
			rd.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		names =  new ArrayList<String>();
		locations = new ArrayList<GPoint>();
		weightList = new ArrayList<Double>();
		fishes = new ArrayList<GImage>();
		
		for (int i=0; i< infoCol.size(); i++) {
			StringTokenizer tokenizer = new StringTokenizer(infoCol.get(i),"=");
			while (tokenizer.hasMoreTokens()) {
				
				names.add(tokenizer.nextToken());
				GPoint a = new GPoint(Double.parseDouble(tokenizer.nextToken()),Double.parseDouble(tokenizer.nextToken()));
				locations.add(a);
				weightList.add(Double.parseDouble(tokenizer.nextToken()));
				
			}
		}
		for (int i=0; i< names.size(); i++) {
			for (int j=0; j< fishNames.size(); j++) {
				if (names.get(i).equals(fishNames.get(j))) {
					GImage x = new GImage(fishFileNames.get(j));
					fishes.add(x);
					add(x, locations.get(i));
				}
				
			}
		}
		// Your code ends here

	}

	/**
	 * This methods create the combobox with the fish names given in the initial
	 * AQUA FILE
	 */
	private void createAquaList() {
		// your code start here
		title = new JLabel("Fish Collection");
		selectButton = new JButton("Select");
		deleteButton = new JButton("Delete");
		aquaSelectCombo = new JComboBox<String>(); 
		for(int i = 0; i<names.size();i++) {
			int numbers = i+1;
			aquaSelectCombo.addItem(numbers + "-" + names.get(i));
		}
		add(title, WEST);
		add(aquaSelectCombo, WEST);	
		add(selectButton, WEST);
		add(deleteButton, WEST);
		createOtherParameters();
		// Your code ends here
	}
	private void createOtherParameters() {
		infoLabel = new JLabel("Comp130 Aquarium");
		nameLabel = new JLabel("Name");
		nameField = new JTextField();
		coordinates = new JLabel("Coordinates");
		coordinatesField = new JTextField();
		JLabel weight = new JLabel("Weight");
		weightField = new DoubleField();
		JLabel cost = new JLabel("Cost");
		costField = new DoubleField();
		editCoordinate = new JButton("Edit Coordinates");
		animateButton = new JButton("Animate");
		updateButton = new JButton("Update");
		slider = new JSlider(0,2,1);
		scaleButton = new JButton("Scale");
		saveButton = new JButton("Save");
		reportButton = new JButton("Generate Report");
		add(infoLabel,NORTH);
		add(nameLabel,WEST);
		add(nameField,WEST);
		add(coordinates,WEST);
		add(coordinatesField,WEST);
		add(weight,WEST);
		add(weightField,WEST);
		add(cost,WEST);
		add(costField,WEST);
		add(editCoordinate,WEST);
		add(animateButton,WEST);
		add(updateButton,WEST);
		add(slider,WEST);
		add(scaleButton,WEST);
		add(saveButton,SOUTH);
		add(reportButton,SOUTH);
	}

	/**
	 * This methods creates a new fish data in the application by creating a new
	 * fish image, by setting the initial coordinates, weight and getting the
	 * corresponding cost.
	 */
	public void createFishes(String theFish) {
		// your code start here
		for(int i=0; i<fishNames.size();i++) {
			if (fishNames.get(i).equals(theFish)) {
				aquaSelectCombo.addItem(jcomboSayar + "-" + theFish);
				jcomboSayar++;
				names.add(theFish);
				GPoint a = new GPoint(0.0,0.0);
				locations.add(a);
				weightList.add(1.0);
				GImage x = new GImage(fishFileNames.get(i),1.0,1.0);
				fishes.add(x);
				add(x,0.0,0.0);
			}
			
		}
		// Your code ends here
	}

	/**
	 * This methods updates the info about the fishes
	 *
	 */
	public void updateFishes() {
		// your code start here
		PrintWriter wr = null;
		try {
			wr = new PrintWriter(new FileWriter(AQUA_FILE));
			for (int i=0; i<weightList.size(); i++) {
				name = new String(((String)aquaSelectCombo.getItemAt(i)).substring(2));
				wr.println(name+"="+fishes.get(i).getX()+"="+fishes.get(i).getY()+"="+weightList.get(i));
			}
			wr.close();	
		}catch (IOException e) {
			println("Can't write to file.");
		}
		
		// Your code ends here
	}

	/**
	 * This methods creates an REPORT_ILE and stores data about the fishes currently
	 * in the aquarium The data includes the price of all the fish in the aquarium
	 * and total cost of the fish.
	 *
	 */
	private void generateReport() {
		// your code start here
		
		
		PrintWriter wr = null;
		
		
		try {
			wr = new PrintWriter(new FileWriter(REPORT_FILE));
			wr.println("_______REPORT FOR COMP130 AQUARIUM_______");
			for (int i=0; i<weightList.size(); i++) {
				currentJob = new String(((String)aquaSelectCombo.getItemAt(i)).substring(2));
				wr.println("Price of "+currentJob+" is "+weightList.get(i)*((double)costList.get(defineTheSelected))+".");			
			}
			wr.close();	
		} catch (IOException e) {
			println("Can't write to file.");
		}
		
		// Your code ends here
	}

	/**
	 * This methods creates an OUTPUT_FILE and stores data about the fishes
	 * currently in the aquarium The data includes fish name, x and y coordinate and
	 * weight. "=" token is used between each data.
	 */
	private void saveAquarium() {
		// your code start here
		PrintWriter wr = null;
		try {
			wr = new PrintWriter(new FileWriter(SAVE_FILE));
			for (int i=0; i<weightList.size(); i++) {
				name = new String(((String)aquaSelectCombo.getItemAt(i)).substring(2));
				wr.println(name+"="+fishes.get(i).getX()+"="+fishes.get(i).getY()+"="+weightList.get(i));				
			}
			wr.close();
		}catch (IOException e) {
			println("Can't write to file.");
		}
		
		// Your code ends here
	}

	// DO NOT CHANGE ANYTHING FROM THIS POINT ON//
	/* Constant Instant Variable */
	private static final String THEME_SONG = "Underwater_Pool.wav";
	private static final long serialVersionUID = 1L;
	/* Class Instance Variables */
	private Clip clip;
	private AudioInputStream inputStream;

	///////////
	/* Standard Java entry point */
	/* This method can be eliminated in most Java environments */
	public static void main(String[] args) {
		new Comp130_Hw4_S19().start(args);
	}

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
}
