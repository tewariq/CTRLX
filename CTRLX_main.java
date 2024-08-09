
 * Program Description: In Code Nexus, you are the main character who has entered a supercomputer
   under siege by the villainous AI, Nemesis. With its eyes set on conquering both the digital and 
   real worlds, Nemesis has nearly taken full control of the computer. Armed with only your mouse, 
   you must click to defeat hordes of virus goons and navigate the complex networks of Code Nexus. 
   Along the way, you'll find an invaluable ally in Gemini, a virtual assistant dedicated to helping
   you dismantle Nemesis's plans. Together, you'll battle through increasing challenges, and with 
   he help of each other,  be able to ultimately face the formidable AI in a fight to save both 
   realms from certain doom.

 * Game Details: In Code Nexus, players input their name, which is processed and displayed as their 
   username. IF statements manage gameplay scenarios, deciding actions in methods like mouseClicked 
   and ActionPerformed. Random numbers respawn viruses and Nemesis at random coordinates and determine 
   username probabilities. The game uses for loops to store story images and while loops for game 
   backgrounds. String class methods manipulate user input, and try-catch statements handle sounds,
   input, and fonts. Two arrays store story and background images. Custom methods handle sound 
   effects and virus respawn positions. Additional classes include one for the player and one for 
   viruses. JOptionPane dialogs inform the player of key messages. A JFrame uses graphics, animation,
   and collision detection for dynamic gameplay, with sound effects for background music, virus deaths,
   and player deaths.
 */

//Import java modules into program
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

//Main class for CTRLX game with JPanel, ActionListener, KeyListener, MouseListener, MouseMotionListener
public class CTRLX_main extends JPanel implements MouseMotionListener, MouseListener, KeyListener, ActionListener
{
	//Added this to get rid of yellow underline
	private static final long serialVersionUID = 1L;

	//Fields
	//Create frame used in this program
	JFrame frame = new JFrame();

	//Declare integer variable for screen number
	private int screen;

	//Create image for first background, main screen background
	private ImageIcon background1 = new ImageIcon ("Front Page/Computer_Image.png");

	//Create images for the three buttons on the main screen; play, controls, and exit
	private ImageIcon play_button = new ImageIcon ("Front Page/Button_Play.png");
	private ImageIcon exit_button = new ImageIcon ("Front Page/Button_Exit.png");
	private ImageIcon controls_button = new ImageIcon ("Front page/Button_Controls.png");

	//Create images to be used for the controls page
	private ImageIcon controls_background = new ImageIcon ("Controls Page/Controls_Background.gif");
	private ImageIcon back_button = new ImageIcon ("Controls Page/Back_Button.png");

	//Create an array of ImageIcons for the images used for the story
	//	private ImageIcon story_1 = new ImageIcon ("Story/Story_1.png");
	//	private ImageIcon story_2 = new ImageIcon ("Story/Story_2.png");
	//	private ImageIcon story_3 = new ImageIcon ("Story/Story_3.png");
	//	private ImageIcon story_4 = new ImageIcon ("Story/Story_4.png");
	//	private ImageIcon story_5 = new ImageIcon ("Story/Story_5.png");
	//	private ImageIcon story_6 = new ImageIcon ("Story/Story_6.png");
	//	private ImageIcon story_7 = new ImageIcon ("Story/Story_7.png");
	private ImageIcon[] storyImages = new ImageIcon[7];

	//Create an array of ImageIcons for the images used for the game (as backgrounds)
	//	private ImageIcon game1 = new ImageIcon ("game1.png");
	//	private ImageIcon game2 = new ImageIcon ("game2.png");
	//	private ImageIcon game3 = new ImageIcon ("game3.png");
	//	private ImageIcon game4 = new ImageIcon ("game4.png");
	//	private ImageIcon game5 = new ImageIcon ("game5.png");
	//	private ImageIcon game6 = new ImageIcon ("game6.png");
	private ImageIcon[] gameImages = new ImageIcon[6];

	private String[] feedback = new String[] {"Good", "Bad", "Ok"};
	private int response;
	
	//Create image for Gemini, the virtual assistant
	private ImageIcon gemini = new ImageIcon ("Gemini_AI.png");

	//Create a string for the name the user will input later
	private String name;

	//Declare class object variable for player
	private CTRLX_Player player;

	//Create image for player
	private ImageIcon playerImg;

	//Create booleans for player directions
	private boolean up, down, left, right;

	//Create integer variables (to be set in the constructor)
	private int player_speed, player_health;

	//Create class object variable for blue virus
	private blueVirus bVirus;
	
	//Create class object variable for Nemesis
	private nemesisBoss nemesisBoss;


	//Create integer variables for x and y coordinates of three different blue viruses
	private int x_bv1, x_bv2, x_bv3;
	private int y_bv1, y_bv2, y_bv3;

	//Create integer variables for x and y coordinates of nemesis boss
	private int nemesisX;
	private int nemesisY;
    private int nemesis_dx;
    private int nemesis_dy;

	//Create images for three different blue viruses
	private ImageIcon bv1, bv2, bv3;

	//Create integer variable, set in constructor, and adjust in the program as a countdown for the player to know how many viruses left to defeat
	private int goon_deaths;

	private int nemesisHealth;

	//All rectangles used in program
	private Rectangle2D Screen9Portal;
	@SuppressWarnings("unused")
	private Rectangle2D bv1Mask, bv2Mask, bv3Mask;
	private Rectangle2D playerMask;
	private Rectangle2D nemesisMask;

	//Nemesis Image
	private ImageIcon nemesis = new ImageIcon ("nemesis.png");

	//Sounds
	private static Clip main_song, vDeath, pDeath;
	private FloatControl fc, fc2, fc3;

	//Timers, one for automated switching of screens (for story only), one timer for player
	private Timer playerT;
	private Timer screenT;

	//These are the X and Y Coordinates for the 3 viruses and they represent the speed 
	private int bv1_dx, bv1_dy;
	private int bv2_dx, bv2_dy;
	private int bv3_dx, bv3_dy;


	//Random class, make integer to hold random number
	private Random rand = new Random();
	private int randName;

	//Use "i" for while loop
	private int i = 0;

	//Constructor
	public CTRLX_main()
	{
		//Instantiate class object variables
		player = new CTRLX_Player();
		bVirus = new blueVirus();
		nemesisBoss = new nemesisBoss(); 

		//Set player speed to 15;
		player_speed = 15;

		//Set initial player health to 100
		player_health = 100;

		//Set initial number of goons to defeat to 10
		goon_deaths = 10;

		//Set Nemesis boss health
		nemesisHealth = 300;
		
		//Initial speed of Nemesis
		nemesis_dx = 7; 
        nemesis_dy = 7;

		//Set initial screen to 0
		screen = 0;

		//Create a for loop to store 7 images for story line into the storyImages array
		for (int i = 0; i < storyImages.length; i++)
		{
			storyImages[i] = new ImageIcon ("Story/Story_" + (i+1) + ".png");
		}

		//Create a while loop to store 6 images for the game backgrounds into the gameImages array
		while (i < gameImages.length)
		{
			gameImages[i] = new ImageIcon ("game" + (i+1) + ".png");
			i++;
		}

		//Give randName variable a value between from 1 to 7 (1 - (8-1))
		randName = rand.nextInt(1, 8);

		//Add the MouseListener
		addMouseListener(this);

		//Add the KeyListener
		addKeyListener(this);

		//Add the MouseMotionListener
		addMouseMotionListener(this);

		//Set the Focusable to true
		setFocusable(true);

		//Request the focus
		requestFocus();

		//Try and catch for main music which will run throughout the game
		try 
		{
			File soundFile = new File("main_song.wav");
			AudioInputStream audioIn1 = AudioSystem.getAudioInputStream(soundFile);
			main_song = AudioSystem.getClip();
			main_song.open(audioIn1);
			main_song.loop(Clip.LOOP_CONTINUOUSLY);
			fc = (FloatControl)main_song.getControl(FloatControl.Type.MASTER_GAIN);
			fc.setValue(-11.0f);
			main_song.start(); 
		}
		catch(Exception e2)
		{
			//Tell user the exception
			JOptionPane.showMessageDialog(null, "File not Found");
		}

		//Set up the JFrame
		frame.setContentPane(this);
		frame.setSize(810, 820);
		frame.setTitle("CTRLX");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

		//Get images from their respective classes
		playerImg = player.getImage(0);
		bv1 = bVirus.getImage(0);
		bv2 = bVirus.getImage(0);
		bv3 = bVirus.getImage(0);
		
		//Virus 1
		bv1_dx = 2;
		bv1_dy = 2;
				
		//Virus 2
		bv2_dx = 2;
		bv2_dy = 2;

		//virus 3
		bv3_dx = 2;
		bv3_dy = 2;
				
		//Nemesis boss
		nemesisX = 2;
		nemesisY = 2;
		
		// Initialize all random locations of each blue virus just before the screen opens
		//Virus 1
		bVirus.setLocation(gameImages[1].getIconWidth() - 100, gameImages[1].getIconHeight() - 40);
		x_bv1 = bVirus.getX_B();
		y_bv1 = bVirus.getY_B();

		//Virus 2
		bVirus.setLocation(gameImages[1].getIconWidth() - 100, gameImages[1].getIconHeight() - 40);
		x_bv2 = bVirus.getX_B();
		y_bv2 = bVirus.getY_B();

		//Virus 3
		bVirus.setLocation(gameImages[1].getIconWidth() - 100, gameImages[1].getIconHeight() - 40); 
		x_bv3 = bVirus.getX_B();
		y_bv3 = bVirus.getY_B();

		//Nemesis Boss
		nemesisBoss.setLocation(gameImages[5].getIconWidth() - 100, gameImages[5].getIconHeight() - 40); 
		nemesisX = nemesisBoss.getX_N();
		nemesisY = nemesisBoss.getY_N();

		//Set screen timer
		screenT = new Timer(6700, this);
		//		screenT = new Timer(200, this);

		//Set player timer
		playerT = new Timer(33, this);
		//		playerT = new Timer(30, this);
	}

	//Main Method
	public static void main(String[] args) 
	{

		new CTRLX_main();
	}

	//Paint Method
	public void paint(Graphics g)
	{
		//Initialize paint method
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;

		//For main page
		if (screen == 0)
		{
			//Draw to the user; the background image, and the three buttons; play, controls, exit
			g2.drawImage(background1.getImage(), 0, 0, this);
			g2.drawImage(play_button.getImage(), (this.getWidth() / 2 - play_button.getIconWidth() / 2) + 10, 280, this);
			g2.drawImage(controls_button.getImage(), (this.getWidth() / 2 - controls_button.getIconWidth() / 2) + 10, 370, this);
			g2.drawImage(exit_button.getImage(), (this.getWidth() / 2 - exit_button.getIconWidth() / 2) + 10, 460, this);
		}

		//For controls page
		if (screen == 1)
		{
			//Draw to the user; the controls background image, and the back button
			g2.drawImage(controls_background.getImage(), 0, 0, this);
			g2.drawImage(back_button.getImage(), 50, 50, this);
		}

		//For first story screen
		if(screen == 2)
		{
			//Draw to the user; the first storyline image
			g2.drawImage(storyImages[0].getImage(), 0, 0, this);
		}

		//For second story screen
		if(screen == 3)
		{
			//Draw to the user; the second storyline image
			g2.drawImage(storyImages[1].getImage(), 0, 0, this);
		}

		//For third story screen
		if(screen == 4)
		{
			//Draw to the user; the third storyline image
			g2.drawImage(storyImages[2].getImage(), 0, 0, this);
		}

		//For fourth story screen
		if(screen == 5)
		{
			//Draw to the user; the fourth storyline image
			g2.drawImage(storyImages[3].getImage(), 0, 0, this);
		}

		//FFOr fifth story screen
		if(screen == 6)
		{
			//Draw to the user; the fifth storyline image
			g2.drawImage(storyImages[4].getImage(), 0, 0, this);
		}

		//For sixth story screen
		if(screen == 7)
		{
			//Draw to the user; the sixth storyline image
			g2.drawImage(storyImages[5].getImage(), 0, 0, this);
		}

		//For seventh story screen
		if(screen == 8)
		{
			//Draw to the user; the seventh storyline image
			g2.drawImage(storyImages[6].getImage(), 0, 0, this);
		}

		//Present first screen of the game
		if (screen == 9)
		{
			//Draw background image
			g2.drawImage(gameImages[0].getImage(), 0, 0, this);

			//Make player mask
			playerMask = new Rectangle2D.Double(player.getX_P(), player.getY_P(), playerImg.getIconWidth(),playerImg.getIconHeight());
			//			g2.draw(playerMask);

			//Draw player image
			g2.drawImage(playerImg.getImage(), player.getX_P(), player.getY_P(), this);

			//Start player timer
			playerT.start();

			//Set text color
			Color fColor = new Color(0, 0, 0);

			//Try and catch for font 
			try
			{
				Font f = Font.createFont(Font.TRUETYPE_FONT, new File ("Font1.ttf")).deriveFont(30f);
				g2.setFont(f);
				g2.setColor(fColor);
				Screen9Portal = new Rectangle2D.Double(575,240,120,180);
				//				g2.draw(Screen9Portal);
			}
			catch (IOException | FontFormatException e)
			{
				//Tell the user the exception
				JOptionPane.showMessageDialog(null, "There is an error with the font", "FONT ERROR", JOptionPane.ERROR_MESSAGE);
			}

			///Tell user player health, at 50 size font
			g2.drawString("Health: " + player_health, 10, 670);

			//Try and catch for font
			try
			{
				Font f = Font.createFont(Font.TRUETYPE_FONT, new File ("Font1.ttf")).deriveFont(20f);
				g2.setFont(f);
				g2.setColor(fColor);
			}
			catch (IOException | FontFormatException e)
			{
				//Tell the user the exception
				JOptionPane.showMessageDialog(null, "There is an error with the font", "FONT ERROR", JOptionPane.ERROR_MESSAGE);
			}

			//Tell the user to enter the portal, in size 20 font
			g2.drawString("<- Enter", 695, 283);

		}

		//Present second screen of the game
		if (screen == 10)
		{

			//Draw background image
			g2.drawImage(gameImages[1].getImage(), 0, 0, this);

			//Make player mask
			playerMask = new Rectangle2D.Double(player.getX_P(), player.getY_P(), playerImg.getIconWidth(), playerImg.getIconHeight());
			//			g2.draw(playerMask);

			//Draw player image
			g2.drawImage(playerImg.getImage(), player.getX_P(), player.getY_P(), this);

			//Check if goon_deaths is not 0, if it is not, then keep the virus images on the screen, if goon_deaths is 0
			if (goon_deaths != 0)
			{

				//Draw all three viruses
				g2.drawImage(bv1.getImage(), x_bv1, y_bv1, this);
				g2.drawImage(bv2.getImage(), x_bv2, y_bv2, this);
				g2.drawImage(bv3.getImage(), x_bv3, y_bv3, this);

				//Create masks for all three viruses
				bv1Mask = new Rectangle2D.Double(x_bv1 + 5, y_bv1 + 5, bv1.getIconWidth() - 5, bv1.getIconHeight() - 5);
				bv2Mask = new Rectangle2D.Double(x_bv2 + 5, y_bv2 + 5, bv2.getIconWidth() - 5, bv2.getIconHeight() - 5);
				bv3Mask = new Rectangle2D.Double(x_bv3 + 5, y_bv3 + 5, bv3.getIconWidth() - 5, bv3.getIconHeight() - 5);
				//				g2.draw(bv1Mask);
				//				g2.draw(bv2Mask);
				//				g2.draw(bv3Mask);
			}

			//Start player timer
			playerT.start();

			//Set text color to white
			Color fColor = new Color(255, 255, 255);

			//Try and catch for font
			try
			{
				Font f = Font.createFont(Font.TRUETYPE_FONT, new File ("Font1.ttf")).deriveFont(30f);

				g2.setFont(f); //Set the font
				g2.setColor(fColor); //Set the font color

			}
			catch (IOException | FontFormatException e)
			{
				//SHow user the exception
				JOptionPane.showMessageDialog(null, "There is an error with the font", "FONT ERROR", JOptionPane.ERROR_MESSAGE);
			}		

			//Show the user the player health and the amount of goons left
			g2.drawString("Health: " + player_health, 10, 670);
			g2.drawString("Goons Left: " + goon_deaths, 650, 40);
		}

		//Present third screen of the game
		if (screen == 11)
		{
			//Draw background image
			g2.drawImage(gameImages[2].getImage(), 0, 0, this);

			//Make player mask
			playerMask = new Rectangle2D.Double(player.getX_P(), player.getY_P(), playerImg.getIconWidth(), playerImg.getIconHeight());
			//			g2.draw(playerMask);

			//Draw player image
			g2.drawImage(playerImg.getImage(), player.getX_P(), player.getY_P(), this);

			//Check if goon_deaths is not 0, if it is not, then keep the virus images on the screen, if goon_deaths is 0
			if (goon_deaths != 0)
			{

				//Draw all three viruses
				g2.drawImage(bv1.getImage(), x_bv1, y_bv1, this);
				g2.drawImage(bv2.getImage(), x_bv2, y_bv2, this);
				g2.drawImage(bv3.getImage(), x_bv3, y_bv3, this);

				//Create masks for all three viruses
				bv1Mask = new Rectangle2D.Double(x_bv1 + 5, y_bv1 + 5, bv1.getIconWidth() - 5, bv1.getIconHeight() - 5);
				bv2Mask = new Rectangle2D.Double(x_bv2 + 5, y_bv2 + 5, bv2.getIconWidth() - 5, bv2.getIconHeight() - 5);
				bv3Mask = new Rectangle2D.Double(x_bv3 + 5, y_bv3 + 5, bv3.getIconWidth() - 5, bv3.getIconHeight() - 5);
				//				g2.draw(bv1Mask);
				//				g2.draw(bv2Mask);
				//				g2.draw(bv3Mask);
			}

			//Start player timer
			playerT.start();

			//Set text color to white
			Color fColor = new Color(255, 255, 255);

			//Try and catch for font
			try
			{
				Font f = Font.createFont(Font.TRUETYPE_FONT, new File ("Font1.ttf")).deriveFont(30f);

				g2.setFont(f); //Set the font
				g2.setColor(fColor); //Set the font color

			}
			catch (IOException | FontFormatException e)
			{
				//SHow user the exception
				JOptionPane.showMessageDialog(null, "There is an error with the font", "FONT ERROR", JOptionPane.ERROR_MESSAGE);
			}		

			//Show the user the player health and the amount of goons left
			g2.drawString("Health: " + player_health, 10, 670);
			g2.drawString("Goons Left: " + goon_deaths, 650, 40);
		}	

		//Present fourth screen of the game
		if (screen == 12)
		{
			//Draw background image
			g2.drawImage(gameImages[3].getImage(), 0, 0, this);

			//Make player mask
			playerMask = new Rectangle2D.Double(player.getX_P(), player.getY_P(), playerImg.getIconWidth(), playerImg.getIconHeight());
			//			g2.draw(playerMask);

			//Draw player image
			g2.drawImage(playerImg.getImage(), player.getX_P(), player.getY_P(), this);

			//Check if goon_deaths is not 0, if it is not, then keep the virus images on the screen, if goon_deaths is 0
			if (goon_deaths != 0)
			{

				//Draw all three viruses
				g2.drawImage(bv1.getImage(), x_bv1, y_bv1, this);
				g2.drawImage(bv2.getImage(), x_bv2, y_bv2, this);
				g2.drawImage(bv3.getImage(), x_bv3, y_bv3, this);

				//Create masks for all three viruses
				bv1Mask = new Rectangle2D.Double(x_bv1 + 5, y_bv1 + 5, bv1.getIconWidth() - 5, bv1.getIconHeight() - 5);
				bv2Mask = new Rectangle2D.Double(x_bv2 + 5, y_bv2 + 5, bv2.getIconWidth() - 5, bv2.getIconHeight() - 5);
				bv3Mask = new Rectangle2D.Double(x_bv3 + 5, y_bv3 + 5, bv3.getIconWidth() - 5, bv3.getIconHeight() - 5);
				//				g2.draw(bv1Mask);
				//				g2.draw(bv2Mask);
				//				g2.draw(bv3Mask);
			}

			//Start player timer
			playerT.start();

			//Set text color to white
			Color fColor = new Color(255, 255, 255);

			//Try and catch for font
			try
			{
				Font f = Font.createFont(Font.TRUETYPE_FONT, new File ("Font1.ttf")).deriveFont(30f);

				g2.setFont(f); //Set the font
				g2.setColor(fColor); //Set the font color

			}
			catch (IOException | FontFormatException e)
			{
				//Show user the exception
				JOptionPane.showMessageDialog(null, "There is an error with the font", "FONT ERROR", JOptionPane.ERROR_MESSAGE);
			}		

			//Show the user the player health and the amount of goons left
			g2.drawString("Health: " + player_health, 10, 670);
			g2.drawString("Goons Left: " + goon_deaths, 650, 40);
		}	

		//Present fifth screen of the game
		if (screen == 13)
		{
			//Draw background image
			g2.drawImage(gameImages[4].getImage(), 0, 0, this);

			//Set text color to white
			Color fColor = new Color(255, 255, 255);

			//Try and catch for the font
			try
			{
				Font f = Font.createFont(Font.TRUETYPE_FONT, new File ("Font1.ttf")).deriveFont(30f);
				g2.setFont(f);
				g2.setColor(fColor);

			}
			catch (IOException | FontFormatException e)
			{
				//Show use the exception
				JOptionPane.showMessageDialog(null, "There is an error with the font", "FONT ERROR", JOptionPane.ERROR_MESSAGE);
			}		
		}

		if (screen == 14)
		{
			//Draw background image
			g2.drawImage(gameImages[5].getImage(), 0, 0, this);

			//Draw player image
			g2.drawImage(playerImg.getImage(), player.getX_P(), player.getY_P(), this);

			//Draw Nemesis image
			g2.drawImage(nemesis.getImage(), nemesisX, nemesisY, this);

			//Make player mask
			playerMask = new Rectangle2D.Double(player.getX_P(), player.getY_P(), playerImg.getIconWidth(), playerImg.getIconHeight());
			//			g2.draw(playerMask);

			//Nemesis Mask
			nemesisMask = new Rectangle2D.Double(nemesisX + 5, nemesisY + 5, nemesis.getIconWidth() - 5, nemesis.getIconHeight() - 5);
//			g2.draw(nemesisMask);

			//Set text color to white
			Color fColor = new Color(255, 255, 255);

			//Try and catch for the font
			try
			{
				Font f = Font.createFont(Font.TRUETYPE_FONT, new File ("Font1.ttf")).deriveFont(30f);
				g2.setFont(f);
				g2.setColor(fColor);

			}
			catch (IOException | FontFormatException e)
			{
				//Show use the exception
				JOptionPane.showMessageDialog(null, "There is an error with the font", "FONT ERROR", JOptionPane.ERROR_MESSAGE);
			}		

			//Show user their health
			g2.drawString("Health: " + player_health, 10, 670);
			g2.drawString("Nemesis Health: " + nemesisHealth, 485, 40);

		}

		//Repaint the paint
		repaint();
	}

	//ActionPerfomed Method
	public void actionPerformed(ActionEvent e) 
	{
		//Continue changing backgrounds starting screen 2 and end at screen 8, if more or equal to screen 2, then repaint to the next screen, if screen is more than or equal 8 it will stop the screen timer and stop repainting the background
		if (e.getSource() == screenT)
		{	
			if(screen >= 2);
			{
				screen++;
				repaint();
			}
			if (screen >= 8)
			{
				screenT.stop();
			}
		}

		// If this condition is met, the function of this is to update the positions, boundary check and player movement		
		if (e.getSource() == playerT)
		{
			// Update virus positions and multiply the speed by 2 
			//Virus 1
			x_bv1 += bv1_dx * 2; 
			y_bv1 += bv1_dy * 2; 

			//Virus 2
			x_bv2 += bv2_dx * 2; 
			y_bv2 += bv2_dy * 2; 

			//Virus 3
			x_bv3 += bv3_dx * 2; 
			y_bv3 += bv3_dy * 2; 

			//Check boundaries for Blue Virus 1
			if (x_bv1 < 0 || x_bv1 > getWidth() - bv1.getIconWidth()) bv1_dx = -bv1_dx;
			if (y_bv1 < 0 || y_bv1 > getHeight() - bv1.getIconHeight()) bv1_dy = -bv1_dy;

			//Check boundaries for Blue Virus 2
			if (x_bv2 < 0 || x_bv2 > getWidth() - bv2.getIconWidth()) bv2_dx = -bv2_dx;
			if (y_bv2 < 0 || y_bv2 > getHeight() - bv2.getIconHeight()) bv2_dy = -bv2_dy;

			//Check boundaries for Blue Virus 3
			if (x_bv3 < 0 || x_bv3 > getWidth() - bv3.getIconWidth()) bv3_dx = -bv3_dx;
			if (y_bv3 < 0 || y_bv3 > getHeight() - bv3.getIconHeight()) bv3_dy = -bv3_dy;

			//Update positions for the nemesis boss
			nemesisX += nemesis_dx * 2; 
			nemesisY += nemesis_dy * 2; 
	
			//Check boundaries for the nemesis boss			
	        if (nemesisX < 0 || nemesisX > getWidth() - nemesis.getIconWidth()) 
	        {
	            nemesis_dx = -nemesis_dx;
	        }
	        
	        if (nemesisY < 0 || nemesisY > getHeight() - nemesis.getIconHeight()) 
	        {
	            nemesis_dy = -nemesis_dy;
	        }
	        
			//If this condition is met, the following code will be responsible for player movement (Up, Down, Right, Left)
			//All these movements take speed into account
			if  (screen == 9)
			{
				//Responsible for player movement up
				if (up)
				{
					playerImg = player.getImage(0);

					if (player.getY_P() <= 0)
					{
						player.moveUp(0);
					}
					else
					{
						player.moveUp(player_speed);
					}
				}

				//Responsible for player movement left
				if (left)
				{
					playerImg = player.getImage(3);

					if (player.getX_P() <= 0)
					{
						player.moveLeft(0);
					}
					else 
					{
						player.moveLeft(player_speed);
					}
				}

				//Responsible for player movement down
				if (down)
				{
					playerImg = player.getImage(2);
					if (player.getY_P() + player.getHeight() + 60 >= this.getHeight()) 
					{
						player.moveDown(0);
					}
					else 
					{
						player.moveDown(player_speed);
					}
				}

				//Responsible for player movement right		
				if (right)
				{
					playerImg = player.getImage(1);

					if (player.getX_P() + player.getWidth() + 45 >= this.getWidth()) 
					{
						player.moveRight(0);
					}
					else 
					{
						player.moveRight(player_speed);
					}
				}
			}

			//Check for intersections on screen 9
			if (screen == 9)
			{
				//Make a player mask rectangle
				playerMask.setRect(player.getX_P(), player.getY_P(), playerImg.getIconWidth(), playerImg.getIconHeight());

				//Check for intersection
				if (playerMask.intersects(Screen9Portal))
				{
					//Print JOptionPane.showMessageDialog lines to the user, introducing them to Genesis
					JOptionPane.showMessageDialog(null, "Welcome to Code Nexus...", "", JOptionPane.PLAIN_MESSAGE);			
					JOptionPane.showMessageDialog(null, "This is your virtual assistant Gemini.\nI am the remaining component of the Code Nexus which is able to help.\nThe rest has been taken over by the grim, evil and all mighty Nemesis.", "", JOptionPane.PLAIN_MESSAGE ,gemini);			

					//Try-catch to make sure user doesn't break the code and cause errors
					try
					{
						//Ask user  to input the name they would like to use for the game
						name = (String) JOptionPane.showInputDialog(null, "What is your name Hero?\nWe need to give a username for the computer", "Enter Name", JOptionPane.PLAIN_MESSAGE, gemini, null, null);

						//Use the random number to assign number with different variants of username assignments
						if (randName == 1) 
						{
							name = "_" + name + "123";
						}
						else if (randName == 2)
						{
							name = "GoonDeleter" + name;
						}
						else if (randName == 3)
						{
							name = "31R_" + name + "_1)!";
						}
						else if (randName == 4)
						{
							name = "Xxx_" + name + "_xxX";
						}
						else if (randName == 5)
						{
							name = name + "._.";
						}
						else if (randName == 6)
						{
							name = "4rost" + name + "_ps";
						}
						else if (randName == 7)
						{
							name = "Nemesis.Slayer_" + name;
						}
					}

					catch (Exception e1)
					{
						//Show exception to user
						JOptionPane.showMessageDialog(null, "Error: Please enter name correctly");
					}

					//Continue with the shoMessageDialogs
					JOptionPane.showMessageDialog(null, "Thank you for coming " + name + ".\nWe really need you to help us.", "", JOptionPane.PLAIN_MESSAGE ,gemini);			
					JOptionPane.showMessageDialog(null, "After teleporting through this portal, you will come across Nemesis' virus goons!", "", JOptionPane.PLAIN_MESSAGE ,gemini);			
					JOptionPane.showMessageDialog(null, "You have to use your left-click on your mouse, and click on the following goons to CTRL + X them.\nIn short, you must delete them from the computer", "", JOptionPane.PLAIN_MESSAGE ,gemini);			
					JOptionPane.showMessageDialog(null, "Each time the goon touches you, it will deal 5 damage to you.\nSo be careful!", "", JOptionPane.PLAIN_MESSAGE ,gemini);			
					JOptionPane.showMessageDialog(null, "You have the delete 10 goons in the stage.\nAs you delete the goons, the counter in the top right will decrease to tell you how many goons are left to delete.", "", JOptionPane.PLAIN_MESSAGE ,gemini);			
					JOptionPane.showMessageDialog(null, "You will go through three stages of fighting off the goons.\nEach with increasing difficulty.", "", JOptionPane.PLAIN_MESSAGE ,gemini);
					JOptionPane.showMessageDialog(null, "Good luck " + name + "!", "", JOptionPane.PLAIN_MESSAGE ,gemini);			

					//Stop the player timer
					playerT.stop();

					//Stop all player movements
					up = false;
					down = false;
					right = false;
					left = false;

					//After the JOptionPanes, change screen variable value to 10, showing screen 10
					screen = 10;
				}
			}

			//Check what to do in this section of ActionPerformed for screen 10, make player move and boundary check the player to stay within the frame, make code for if player intersects with virus (what happens), and if player dies what happens
			if (screen == 10)
			{
				//Responsible for player movement up
				if (up)
				{
					playerImg = player.getImage(0);

					if (player.getY_P() <= 180)
					{
						player.moveUp(0);
					}
					else
					{
						player.moveUp(player_speed);
					}
				}

				//Responsible for player movement left
				if (left)
				{
					playerImg = player.getImage(3);

					if (player.getX_P() <= 0)
					{
						player.moveLeft(0);
					}
					else 
					{
						player.moveLeft(player_speed);
					}
				}

				//Responsible for player movement down
				if (down)
				{
					playerImg = player.getImage(2);
					if (player.getY_P() + player.getHeight() + 60 >= this.getHeight()) 
					{
						player.moveDown(0);
					}
					else 
					{
						player.moveDown(player_speed);
					}
				}

				//Responsible for player movement right
				if (right)
				{
					playerImg = player.getImage(1);

					if (player.getX_P() + player.getWidth() + 45 >= this.getWidth()) 
					{
						player.moveRight(0);
					}
					else 
					{
						player.moveRight(player_speed);
					}
				}

				//If the following condition passes, continue the code
				if (goon_deaths != 0)
				{
					//Make the mask rectangles for the player, and the 
					Rectangle2D playerRect = new Rectangle2D.Double(player.getX_P(), player.getY_P(), playerImg.getIconWidth(), playerImg.getIconHeight());
					Rectangle2D bv1Rect = new Rectangle2D.Double(x_bv1 + 5, y_bv1 + 5, bv1.getIconWidth() - 5, bv1.getIconHeight() - 5);
					Rectangle2D bv2Rect = new Rectangle2D.Double(x_bv2 + 5, y_bv2 + 5, bv2.getIconWidth() - 5, bv2.getIconHeight() - 5);
					Rectangle2D bv3Rect = new Rectangle2D.Double(x_bv3 + 5, y_bv3 + 5, bv3.getIconWidth() - 5, bv3.getIconHeight() - 5);

					//Check if the player intersects/collision is detected with the mask rectangle for virus 1
					if (playerRect.intersects(bv1Rect)) 
					{
						//Take 5 away from the player_health
						player_health -= 5;
						//Randomly place virus 1 across the frame after intersection
						x_bv1 = (int) (Math.random() * (getWidth() - bv1.getIconWidth()));
						y_bv1 = (int) (Math.random() * (getHeight() - bv1.getIconHeight()));

						//Check if the player health is 0, which means player is dead then...
						if (player_health == 0) 
						{

							//Play player death sound
							PDeathSound();

							//Tell user that they have died and will have to leave the game
							JOptionPane.showMessageDialog(null, "You have failed to defeat Nemesis.\nWe will wait for your return " + name + ".\nBetter luck next time hero.", "Game Over", JOptionPane.PLAIN_MESSAGE, gemini);

							//Stop the player time
							playerT.stop();

							//Exit the system
							System.exit(0);
						}
					}

					//Check if the player intersects/collision is detected with the mask rectangle for virus 2
					if (playerRect.intersects(bv2Rect)) 
					{
						//Take 5 away from the player_health
						player_health -= 5;
						//Randomly place virus 2 across the frame after intersection						
						x_bv2 = (int) (Math.random() * (getWidth() - bv2.getIconWidth()));
						y_bv2 = (int) (Math.random() * (getHeight() - bv2.getIconHeight()));

						//Check if the player health is 0, which means player is dead then...
						if (player_health == 0) 
						{

							//Play player death sound
							PDeathSound();

							//Tell user that they have died and will have to leave the game
							JOptionPane.showMessageDialog(null, "You have failed to defeat Nemesis.\nWe will wait for your return " + name + ".\nBetter luck next time hero.", "Game Over", JOptionPane.PLAIN_MESSAGE, gemini);

							//Stop the player time
							playerT.stop();

							//Exit the system
							System.exit(0);
						}
					}

					//Check if the player intersects/collision is detected with the mask rectangle for virus 3
					if (playerRect.intersects(bv3Rect)) 
					{
						//Take 5 away from the player_health
						player_health -= 5;
						//Randomly place virus 3 across the frame after intersection						
						x_bv3 = (int) (Math.random() * (getWidth() - bv3.getIconWidth()));
						y_bv3 = (int) (Math.random() * (getHeight() - bv3.getIconHeight()));

						//Check if the player health is 0, which means player is dead then...
						if (player_health == 0) 
						{

							//Play player death sound
							PDeathSound();

							//Tell user that they have died and will have to leave the game
							JOptionPane.showMessageDialog(null, "You have failed to defeat Nemesis.\nWe will wait for your return " + name + ".\nBetter luck next time hero.", "Game Over", JOptionPane.PLAIN_MESSAGE, gemini);

							//Stop the player time
							playerT.stop();

							//Exit the system
							System.exit(0);
						}
					}
				}
			}

			//Check what to do in this section of ActionPerformed for screen 11, make player move and boundary check the player to stay within the frame, make code for if player intersects with virus (what happens), and if player dies what happens
			if (screen == 11)
			{
				//Responsible for player movement up
				if (up)
				{
					playerImg = player.getImage(0);

					if (player.getY_P() <= 0)
					{
						player.moveUp(0);
					}
					else
					{
						player.moveUp(player_speed);
					}

				//Responsible for player movement left
				}
				if (left)
				{
					playerImg = player.getImage(3);

					if (player.getX_P() <= 0)
					{
						player.moveLeft(0);
					}
					else 
					{
						player.moveLeft(player_speed);
					}
				}
				
				//Responsible for player movement down
				if (down)
				{
					playerImg = player.getImage(2);
					if (player.getY_P() + player.getHeight() + 60 >= this.getHeight()) 
					{
						player.moveDown(0);
					}
					else 
					{
						player.moveDown(player_speed);
					}

				}
				
				//Responsible for player movement right
				if (right)
				{
					playerImg = player.getImage(1);

					if (player.getX_P() + player.getWidth() + 45 >= this.getWidth()) 
					{
						player.moveRight(0);
					}
					else 
					{
						player.moveRight(player_speed);
					}
				}

				//If the following condition passes, continue the code
				if (goon_deaths != 0)
				{
					//Make the mask rectangles for the player
					Rectangle2D playerRect = new Rectangle2D.Double(player.getX_P(), player.getY_P(), playerImg.getIconWidth(), playerImg.getIconHeight());
					Rectangle2D bv1Rect = new Rectangle2D.Double(x_bv1 + 5, y_bv1 + 5, bv1.getIconWidth() - 5, bv1.getIconHeight() - 5);
					Rectangle2D bv2Rect = new Rectangle2D.Double(x_bv2 + 5, y_bv2 + 5, bv2.getIconWidth() - 5, bv2.getIconHeight() - 5);
					Rectangle2D bv3Rect = new Rectangle2D.Double(x_bv3 + 5, y_bv3 + 5, bv3.getIconWidth() - 5, bv3.getIconHeight() - 5);

					//Check if the player intersects/collision is detected with the mask rectangle for virus 1
					if (playerRect.intersects(bv1Rect)) 
					{
						//Take 5 away from the player_health
						player_health -= 5;
						//Randomly place virus 1 across the frame after intersection
						x_bv1 = (int) (Math.random() * (getWidth() - bv1.getIconWidth()));
						y_bv1 = (int) (Math.random() * (getHeight() - bv1.getIconHeight()));

						//Check if the player health is 0, which means player is dead then...
						if (player_health == 0) 
						{

							//Play player death sound
							PDeathSound();

							//Tell user that they have died and will have to leave the game
							JOptionPane.showMessageDialog(null, "You have failed to defeat Nemesis.\nWe will wait for your return " + name + ".\nBetter luck next time hero.", "Game Over", JOptionPane.PLAIN_MESSAGE, gemini);

							//Stop the player time
							playerT.stop();

							//Exit the system
							System.exit(0);
						}
					}

					//Check if the player intersects/collision is detected with the mask rectangle for virus 2
					if (playerRect.intersects(bv2Rect)) 
					{
						//Take 5 away from the player_health
						player_health -= 5;
						//Randomly place virus 1 across the frame after intersection
						x_bv2 = (int) (Math.random() * (getWidth() - bv2.getIconWidth()));
						y_bv2 = (int) (Math.random() * (getHeight() - bv2.getIconHeight()));

						//Check if the player health is 0, which means player is dead then...
						if (player_health == 0) 
						{

							//Play player death sound
							PDeathSound();

							//Tell user that they have died and will have to leave the game
							JOptionPane.showMessageDialog(null, "You have failed to defeat Nemesis.\nWe will wait for your return " + name + ".\nBetter luck next time hero.", "Game Over", JOptionPane.PLAIN_MESSAGE, gemini);

							//Stop the player time
							playerT.stop();

							//Exit the system
							System.exit(0);
						}
					}
					
					//Check if the player intersects/collision is detected with the mask rectangle for virus 3
					if (playerRect.intersects(bv3Rect)) 
					{
						//Take 5 away from the player_health
						player_health -= 5;
						//Randomly place virus 3 across the frame after intersection
						x_bv3 = (int) (Math.random() * (getWidth() - bv3.getIconWidth()));
						y_bv3 = (int) (Math.random() * (getHeight() - bv1.getIconHeight()));

						//Check if the player health is 0, which means player is dead then...
						if (player_health == 0) 
						{

							//Play player death sound
							PDeathSound();

							//Tell user that they have died and will have to leave the game
							JOptionPane.showMessageDialog(null, "You have failed to defeat Nemesis.\nWe will wait for your return " + name + ".\nBetter luck next time hero.", "Game Over", JOptionPane.PLAIN_MESSAGE, gemini);

							//Stop the player time
							playerT.stop();

							//Exit the system
							System.exit(0);
						}
					}
				}
			}

			//Check what to do in this section of ActionPerformed for screen 12, make player move and boundary check the player to stay within the frame, make code for if player intersects with virus (what happens), and if player dies what happens
			if (screen == 12)
			{
				//Responsible for player movement up
				if (up)
				{
					playerImg = player.getImage(0);

					if (player.getY_P() <= 0)
					{
						player.moveUp(0);
					}
					else
					{
						player.moveUp(player_speed);
					}
				}
				
				//Responsible for player movement left
				if (left)
				{
					playerImg = player.getImage(3);

					if (player.getX_P() <= 0)
					{
						player.moveLeft(0);
					}
					else 
					{
						player.moveLeft(player_speed);
					}
				}
				
				//Responsible for player movement down
				if (down)
				{
					playerImg = player.getImage(2);
					if (player.getY_P() + player.getHeight() + 60 >= this.getHeight()) 
					{
						player.moveDown(0);
					}
					else 
					{
						player.moveDown(player_speed);
					}
				}
				
				//Responsible for player movement right
				if (right)
				{
					playerImg = player.getImage(1);

					if (player.getX_P() + player.getWidth() + 45 >= this.getWidth()) 
					{
						player.moveRight(0);
					}
					else 
					{
						player.moveRight(player_speed);
					}
				}

				if (goon_deaths != 0)
				{
					//Make the mask rectangles for the player, and the virus
					Rectangle2D playerRect = new Rectangle2D.Double(player.getX_P(), player.getY_P(), playerImg.getIconWidth(), playerImg.getIconHeight());
					Rectangle2D bv1Rect = new Rectangle2D.Double(x_bv1 + 5, y_bv1 + 5, bv1.getIconWidth() - 5, bv1.getIconHeight() - 5);
					Rectangle2D bv2Rect = new Rectangle2D.Double(x_bv2 + 5, y_bv2 + 5, bv2.getIconWidth() - 5, bv2.getIconHeight() - 5);
					Rectangle2D bv3Rect = new Rectangle2D.Double(x_bv3 + 5, y_bv3 + 5, bv3.getIconWidth() - 5, bv3.getIconHeight() - 5);

					//Check if the player intersects/collision is detected with the mask rectangle for virus 1
					if (playerRect.intersects(bv1Rect)) 
					{
						//Take 5 away from the player_health
						player_health -= 5;
						//Randomly place virus 1 across the frame after intersection
						x_bv1 = (int) (Math.random() * (getWidth() - bv1.getIconWidth()));
						y_bv1 = (int) (Math.random() * (getHeight() - bv1.getIconHeight()));

						//Check if the player health is 0, which means player is dead then...
						if (player_health == 0) 
						{

							//Play player death sound
							PDeathSound();

							//Tell user that they have died and will have to leave the game
							JOptionPane.showMessageDialog(null, "You have failed to defeat Nemesis.\nWe will wait for your return " + name + ".\nBetter luck next time hero.", "Game Over", JOptionPane.PLAIN_MESSAGE, gemini);

							//Stop the player time
							playerT.stop();

							//Exit the system
							System.exit(0);
						}
					}

					//Check if the player intersects/collision is detected with the mask rectangle for virus 2
					if (playerRect.intersects(bv2Rect)) 
					{
						//Take 5 away from the player_health
						player_health -= 5;
						//Randomly place virus 1 across the frame after intersection
						x_bv2 = (int) (Math.random() * (getWidth() - bv2.getIconWidth()));
						y_bv2 = (int) (Math.random() * (getHeight() - bv2.getIconHeight()));

						//Check if the player health is 0, which means player is dead then...
						if (player_health == 0) 
						{

							//Play player death sound
							PDeathSound();

							//Tell user that they have died and will have to leave the game
							JOptionPane.showMessageDialog(null, "You have failed to defeat Nemesis.\nWe will wait for your return " + name + ".\nBetter luck next time hero.", "Game Over", JOptionPane.PLAIN_MESSAGE, gemini);

							//Stop the player time
							playerT.stop();

							//Exit the system
							System.exit(0);
						}
					}
					
					//Check if the player intersects/collision is detected with the mask rectangle for virus 3
					if (playerRect.intersects(bv3Rect)) 
					{
						//Take 5 away from the player_health
						player_health -= 5;
						//Randomly place virus 3 across the frame after intersection
						x_bv3 = (int) (Math.random() * (getWidth() - bv3.getIconWidth()));
						y_bv3 = (int) (Math.random() * (getHeight() - bv1.getIconHeight()));

						//Check if the player health is 0, which means player is dead then...
						if (player_health == 0) 
						{

							//Play player death sound
							PDeathSound();

							//Tell user that they have died and will have to leave the game
							JOptionPane.showMessageDialog(null, "You have failed to defeat Nemesis.\nWe will wait for your return " + name + ".\nBetter luck next time hero.", "Game Over", JOptionPane.PLAIN_MESSAGE, gemini);

							//Stop the player time
							playerT.stop();

							//Exit the system
							System.exit(0);
						}
					}
				}
			}
			
			//This block of code works if the screen is 14 
			if (screen == 14)
			{
				//Make the mask rectangles for the player, and the boss
				Rectangle2D playerRect = new Rectangle2D.Double(player.getX_P(), player.getY_P(), playerImg.getIconWidth(), playerImg.getIconHeight());
				Rectangle2D nemesisRect = new Rectangle2D.Double(nemesisX + 5, nemesisY + 5, nemesis.getIconWidth() - 5, nemesis.getIconHeight() - 5);
				
				
				//Responsible for player movement up
				if (up)
				{
					playerImg = player.getImage(0);

					if (player.getY_P() <= 0)
					{
						player.moveUp(0);
					}
					else
					{
						player.moveUp(player_speed);
					}
				}

				//Responsible for player movement left
				if (left)
				{
					playerImg = player.getImage(3);

					if (player.getX_P() <= 0)
					{
						player.moveLeft(0);
					}
					else 
					{
						player.moveLeft(player_speed);
					}
				}

				//Responsible for player movement down
				if (down)
				{
					playerImg = player.getImage(2);
					if (player.getY_P() + player.getHeight() + 60 >= this.getHeight()) 
					{
						player.moveDown(0);
					}
					else 
					{
						player.moveDown(player_speed);
					}
				}

				//Responsible for player movement right		
				if (right)
				{
					playerImg = player.getImage(1);

					if (player.getX_P() + player.getWidth() + 45 >= this.getWidth()) 
					{
						player.moveRight(0);
					}
					else 
					{
						player.moveRight(player_speed);
					}
				}
		
			//Checks to see if the player intersects the nemesis boss
			if (playerRect.intersects(nemesisRect)) 
			{
				//Take 15 away from the player_health
				player_health -= 15;
				//Randomly place virus 3 across the frame after intersection
				nemesisX = (int) (Math.random() * (getWidth() - nemesis.getIconWidth()));
				nemesisY = (int) (Math.random() * (getHeight() - nemesis.getIconHeight()));

				//Check if the player health is 0, which means player is dead then...
				if (player_health <= 0) 
				{
					//Play player death sound
					PDeathSound();

					//Tell user that they have died and will have to leave the game
					JOptionPane.showMessageDialog(null, "You have failed to defeat Nemesis.\nWe will wait for your return " + name + ".\nBetter luck next time hero.", "Game Over", JOptionPane.PLAIN_MESSAGE, gemini);

					//Stop the player time
					playerT.stop();

					//Exit the system
					System.exit(0);
			
				}
			}
			
		}
	}
		repaint();
	}

	//MouseDragged method
	public void mouseDragged(MouseEvent e) 
	{		

	}

	//MouseMoved method
	public void mouseMoved(MouseEvent e) 
	{

	}

	//MouseClicked method
	public void mouseClicked(MouseEvent e) 
	{
		//Initiate a variable for x and y, which get the x and y coordinate of where the mouse is clicked
		int x = e.getX();
		int y = e.getY();

		//CHekc what the user is clicking on in screen 0 (main page)
		if (screen == 0) 
		{
			//Check if controls button was clicked
			if (x > 295 && x < 295 + controls_button.getIconWidth() &&
					y > 370 && y < 370 + controls_button.getIconHeight()) 
			{
				//if clicked, make screen to 1, and change frame size and location
				screen = 1;
				frame.setSize(921, 691);
				frame.setLocationRelativeTo(null);
			}

			//Check if exit button was clicked
			if (x > 295 && x < 295 + exit_button.getIconWidth() &&
					y > 460 && y < 460 + exit_button.getIconHeight()) 
			{
				//Display JOptionPane to ask confirmation if the user would like to quit
				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the game?", "Exit Page", JOptionPane.YES_NO_OPTION);

				//If they click yes, then exit the system
				if (choice == JOptionPane.YES_OPTION)
				{
					System.exit(0);
				}
			}

			//Check if play button is clicked
			if (x > 295 && x < 295 + play_button.getIconWidth() &&
					y > 280 && y < 280 + play_button.getIconHeight()) 
			{
				//If clicked, change screen to 2, change frame size, change frame location, and start the screenT timer to start story screen
				screen = 2;
				frame.setSize(storyImages[0].getIconWidth() + 15, storyImages[0].getIconHeight() + 38);
				frame.setLocationRelativeTo(null); 
				screenT.start();
			}
		}

		//Check if the back button the controls page is clicked
		if (screen == 1) {
			if (x > 50 && x < 50 + back_button.getIconWidth() &&
					y > 50 && y < 50 + back_button.getIconHeight()) 
			{
				//If the back button is clicked, set the screen back to 0 (main page), reset the frame size, and the location oft he screen
				screen = 0;
				frame.setSize(810, 820);
				frame.setLocationRelativeTo(null);
			}
		}

		//Check if the screen is clicked on to change the screen
		if (screen == 8) {
			if (x > 0 && x < 1230 && y > 0 && y < 820) 
			{
				//If clicked, set the screen to 9, change the frame size, and change the frame location
				screen = 9;
				frame.setSize(1080, 720);
				frame.setLocationRelativeTo(null);
			}
		}

		//Check for the viruses clicked on screen 10
		if (screen == 10) 
		{
			//Check if one of virus 1 is clicked
			if (x > x_bv1 && x < x_bv1 + bv1.getIconWidth() &&
					y > y_bv1 && y < y_bv1 + bv1.getIconHeight()) 
			{
				//If a virus is clicked, take one away from the goon_deaths variable, play the virus death sound effect, and respawn the virus in a random location on the frame
				goon_deaths--;
				VDeathSound();	
				respawnBV1();

				//Check if goon_deaths is 0
				if (goon_deaths == 0) 
				{
					//Stop the player timer
					playerT.stop();

					//Stop all player movements
					up = false;
					down = false;
					right = false;
					left = false;

					//Tell the user that they passed level 1
					JOptionPane.showMessageDialog(null, "Congratulations! You passed stage 1!\nStage 2 will be a bit harder, so let me increase your health by 20.\nIf you have more than a 100 health that's even better! ", "Stage 1 Complete", JOptionPane.PLAIN_MESSAGE, gemini);

					//Change screen to 11, make goon_death = 15, and add 20 to the player_health
					screen = 11;
					goon_deaths = 15;
					player_health = player_health + 20;

					// Update virus positions and multiply the speed by (7/2)
					//Virus 1
					bv1_dx = (7/2);
					bv1_dy = (7/2);

					//Virus 3
					bv2_dx = (7/2);
					bv2_dy = (7/2);

					//Virus 3
					bv3_dx = (7/2);
					bv3_dy = (7/2);
				}
			}

			//Check if one of virus 2 is clicked
			if (x > x_bv2 && x < x_bv2 + bv2.getIconWidth() &&
					y > y_bv2 && y < y_bv2 + bv2.getIconHeight()) 
			{
				//If a virus is clicked, take one away from the goon_deaths variable, play the virus death sound effect, and respawn the virus in a random location on the frame
				goon_deaths--;
				VDeathSound();	
				respawnBV2();

				//Check if goon_deaths is 0
				if (goon_deaths == 0) 
				{
					//Stop the player timer
					playerT.stop();

					//Stop all player movements
					up = false;
					down = false;
					right = false;
					left = false;

					//Tell the user that they passed level 1
					JOptionPane.showMessageDialog(null, "Congratulations! You passed stage 1!\nStage 2 will be a bit harder, so let me increase your health by 20.\nIf you have more than a 100 health that's even better! ", "Stage 1 Complete", JOptionPane.PLAIN_MESSAGE, gemini);

					//Change screen to 11, make goon_death = 15, and add 20 to the player_health
					screen = 11;
					goon_deaths = 15;
					player_health = player_health + 20;

					// Update virus positions and multiply the speed by (7/2)
					//Virus 1
					bv1_dx = (7/2);
					bv1_dy = (7/2);

					//Virus 2
					bv2_dx = (7/2);
					bv2_dy = (7/2);

					//Virus 3
					bv3_dx = (7/2);
					bv3_dy = (7/2);
				}
			}

			//Check if one of virus 3 is clicked
			if (x > x_bv3 && x < x_bv3 + bv3.getIconWidth() &&
					y > y_bv3 && y < y_bv3 + bv3.getIconHeight()) 
			{
				//If a virus is clicked, take one away from the goon_deaths variable, play the virus death sound effect, and respawn the virus in a random location on the frame
				goon_deaths--;
				VDeathSound();	
				respawnBV3();

				//Check if goon_deaths is 0
				if (goon_deaths == 0) 
				{
					//Stop the player timer
					playerT.stop();

					//Stop all player movements
					up = false;
					down = false;
					right = false;
					left = false;

					//Tell the user that they passed level 1
					JOptionPane.showMessageDialog(null, "Congratulations! You passed stage 1!\nStage 2 will be a bit harder, so let me increase your health by 20.\nIf you have more than a 100 health that's even better! ", "Stage 1 Complete", JOptionPane.PLAIN_MESSAGE, gemini);

					//Change screen to 11, make goon_death = 15, and add 20 to the player_health
					screen = 11;
					goon_deaths = 15;
					player_health = player_health + 20;

					// Update virus positions and multiply the speed by (7/2)
					//Virus1
					bv1_dx = (7/2);
					bv1_dy = (7/2);

					//Virus 2
					bv2_dx = (7/2);
					bv2_dy = (7/2);

					//Virus 3
					bv3_dx = (7/2);
					bv3_dy = (7/2);
				}
			}
		}

		//Check for the viruses clicked on screen 11
		if (screen == 11) 
		{
			//Check if one of virus 1 is clicked
			if (x > x_bv1 && x < x_bv1 + bv1.getIconWidth() &&
					y > y_bv1 && y < y_bv1 + bv1.getIconHeight()) 
			{
				//If a virus is clicked, take one away from the goon_deaths variable, play the virus death sound effect, and respawn the virus in a random location on the frame
				goon_deaths--;
				VDeathSound();	
				respawnBV1();

				//Check if goon_deaths is 0
				if (goon_deaths == 0) 
				{

					//Stop the player timer
					playerT.stop();

					//Stop all player movements
					up = false;
					down = false;
					right = false;
					left = false;

					//Tell the user that they passed level 2
					JOptionPane.showMessageDialog(null, "WOAH!\n You made it past level 2.\nLevel 3 is going to be the hardest, there are gonna be more goons to delete.\nLet me give you 30 more health just in case.\nGood Luck " + name + "!", "Stage 2 Complete", JOptionPane.PLAIN_MESSAGE, gemini);

					//Change screen to 12, make goon_death = 20, and add 30 to the player_health
					screen = 12;
					goon_deaths = 20;
					player_health = player_health + 30;

					// Update virus positions and multiply the speed by (9/2)
					//Virus 1
					bv1_dx = (9/2);
					bv1_dy = (9/2);

					//Virus 2
					bv2_dx = (9/2);
					bv2_dy = (9/2);

					//Virus 3
					bv3_dx = (9/2);
					bv3_dy = (9/2);
				}
			}

			//Check if one of virus 2 is clicked
			if (x > x_bv2 && x < x_bv2 + bv2.getIconWidth() &&
					y > y_bv2 && y < y_bv2 + bv2.getIconHeight()) 
			{
				//If a virus is clicked, take one away from the goon_deaths variable, play the virus death sound effect, and respawn the virus in a random location on the frame
				goon_deaths--;
				VDeathSound();	
				respawnBV2();

				//Check if goon_deaths is 0
				if (goon_deaths == 0) 
				{

					//Stop the player timer
					playerT.stop();

					//Stop all player movements
					up = false;
					down = false;
					right = false;
					left = false;

					//Tell the user that they passed level 2
					JOptionPane.showMessageDialog(null, "WOAH!\n You made it past level 2.\nLevel 3 is going to be the hardest, there are gonna be more goons to delete.\nLet me give you 30 more health just in case.\nGood Luck " + name + "!", "Stage 2 Complete", JOptionPane.PLAIN_MESSAGE, gemini);

					//Change screen to 12, make goon_death = 20, and add 30 to the player_health
					screen = 12;
					goon_deaths = 20;
					player_health = player_health + 30;

					// Update virus positions and multiply the speed by (9/2)
					//Virus 1
					bv1_dx = (9/2);
					bv1_dy = (9/2);
					
					//Virus 2
					bv2_dx = (9/2);
					bv2_dy = (9/2);

					//Virus 3
					bv3_dx = (9/2);
					bv3_dy = (9/2);
				}
			}

			//Check if one of virus 3 is clicked
			if (x > x_bv3 && x < x_bv3 + bv3.getIconWidth() &&
					y > y_bv3 && y < y_bv3 + bv3.getIconHeight()) 
			{
				//If a virus is clicked, take one away from the goon_deaths variable, play the virus death sound effect, and respawn the virus in a random location on the frame
				goon_deaths--;
				VDeathSound();	
				respawnBV3();

				//Check if goon_deaths is 0
				if (goon_deaths == 0) 
				{
					//Stop the player timer
					playerT.stop();

					//Stop all player movements
					up = false;
					down = false;
					right = false;
					left = false;

					//Tell the user that they passed level 2
					JOptionPane.showMessageDialog(null, "WOAH!\n You made it past level 2.\nLevel 3 is going to be the hardest, there are gonna be more goons to delete.\nLet me give you 30 more health just in case.\nGood Luck " + name + "!", "Stage 2 Complete", JOptionPane.PLAIN_MESSAGE, gemini);

					//Change screen to 12, make goon_death = 20, and add 30 to the player_health
					screen = 12;
					goon_deaths = 20;
					player_health = player_health + 30;

					// Update virus positions and multiply the speed by (9/2)
					//Virus 1
					bv1_dx = (9/2);
					bv1_dy = (9/2);

					//Virus 2
					bv2_dx = (9/2);
					bv2_dy = (9/2);

					//Virus 3
					bv3_dx = (9/2);
					bv3_dy = (9/2);
				}
			}
		}

		//Check for the viruses clicked on screen 12
		if (screen == 12) 
		{
			//Check if one of virus 1 is clicked
			if (x > x_bv1 && x < x_bv1 + bv1.getIconWidth() &&
					y > y_bv1 && y < y_bv1 + bv1.getIconHeight()) 
			{
				//If a virus is clicked, take one away from the goon_deaths variable, play the virus death sound effect, and respawn the virus in a random location on the frame
				goon_deaths--;
				VDeathSound();	
				respawnBV1();

				if (goon_deaths == 0) 
				{
					//Stop player timer
					playerT.stop();

					//Stop all player movements
					up = false;
					down = false;
					right = false;
					left = false;

					//Tell the user thats they passed level 3
					JOptionPane.showMessageDialog(null, "You defeated all the goons and made it out alive!\nIts time you face Nemesis now.\nThis is going to get intense!\nLet me transfer you 50 health, you are going to need it.", "Stage 3 Complete", JOptionPane.PLAIN_MESSAGE, gemini);

					//Change screen to 13, add 50 to the player_health, and play the messages for the 13th screen from the self-made screen13Messages() method
					screen = 13;
					player_health = player_health + 50;
					screen13Message();
					screen = 14;
				}
			}

			//Check if one of virus 2 is clicked
			if (x > x_bv2 && x < x_bv2 + bv2.getIconWidth() &&
					y > y_bv2 && y < y_bv2 + bv2.getIconHeight()) 
			{
				//If a virus is clicked, take one away from the goon_deaths variable, play the virus death sound effect, and respawn the virus in a random location on the frame
				goon_deaths--;
				VDeathSound();	
				respawnBV2();

				if (goon_deaths == 0) 
				{
					//Stop player timer
					playerT.stop();

					//Stop all player movements
					up = false;
					down = false;
					right = false;
					left = false;

					//Tell the user thats they passed level 3
					JOptionPane.showMessageDialog(null, "You defeated all the goons and made it out alive!\nIts time you face Nemesis now.\nThis is going to get intense!\nLet me transfer you 50 health, you are going to need it.", "Stage 3 Complete", JOptionPane.PLAIN_MESSAGE, gemini);

					//Change screen to 13, add 50 to the player_health, and play the messages for the 13th screen from the self-made screen13Messages() method
					screen = 13;
					player_health = player_health + 50;
					screen13Message();
					screen = 14;
				}
			}

			//Check if one of virus 3 is clicked
			if (x > x_bv3 && x < x_bv3 + bv3.getIconWidth() &&
					y > y_bv3 && y < y_bv3 + bv3.getIconHeight()) 
			{
				//If a virus is clicked, take one away from the goon_deaths variable, play the virus death sound effect, and respawn the virus in a random location on the frame
				goon_deaths--;
				VDeathSound();	
				respawnBV3();

				if (goon_deaths == 0) 
				{
					//Stop player timer
					playerT.stop();

					//Stop all player movements
					up = false;
					down = false;
					right = false;
					left = false;

					//Tell the user thats they passed level 3
					JOptionPane.showMessageDialog(null, "You defeated all the goons and made it out alive!\nIts time you face Nemesis now.\nThis is going to get intense!\nLet me transfer you 50 health, you are going to need it.", "Stage 3 Complete", JOptionPane.PLAIN_MESSAGE, gemini);

					//Change screen to 13, add 50 to the player_health, and play the messages for the 13th screen from the self-made screen13Messages() method
					screen = 13;
					player_health = player_health + 50;
					screen13Message();
					screen = 14;
				}
			}
		}
		
		if (screen == 14) 
		{
			//Check if one of virus 1 is clicked
			if (x > nemesisX && x < nemesisX + nemesis.getIconWidth() &&
					y > nemesisY && y < nemesisY + nemesis.getIconHeight()) 
			{
				//If a virus is clicked, take one away from the goon_deaths variable, play the virus death sound effect, and respawn the virus in a random location on the frame
				nemesisHealth -= 20;
				VDeathSound();	
				respawnNemesis();

				//passing condition
				if (nemesisHealth <= 0) 				
				{
					//Stop player timer
					playerT.stop();

					//Stop all player movements
					up = false;
					down = false;
					right = false;
					left = false;

					//Tell the user thats they passed the boss fight
					JOptionPane.showMessageDialog(null, "YOU DID IT! \nNemesis has been defeated.\nThank you hero " + name + "\nThe world is now saved all thanks to you!", "Boss Battle Complete", JOptionPane.PLAIN_MESSAGE, gemini);

					//Change screen to conclusions
					screen = 13;
					conclusionMessage();
					
					if (response == 0) 
					{
						System.exit(0);
					}
					else if (response == 1)
					{
						System.exit(0);
					}
					else if (response == 2)
					{
						System.exit(0);
					}
		
				}
			}
		}
	}

	//Create method to make death sound for viruses
	private void VDeathSound()
	{
		//Try catch for virus death noise sound effect
		try 
		{
			File soundFile2 = new File("virus_death.wav");
			AudioInputStream audioIn2 = AudioSystem.getAudioInputStream(soundFile2);
			vDeath = AudioSystem.getClip();
			vDeath.open(audioIn2);
			fc2 = (FloatControl)vDeath.getControl(FloatControl.Type.MASTER_GAIN);
			fc2.setValue(-8.0f);
			vDeath.start(); 
		}
		catch(Exception e2)
		{
			//Show the exception that the file was not found
			JOptionPane.showMessageDialog(null, "File not Found");
		}
	}

	//Create method to make death sound for player
	private void PDeathSound()
	{
		//try catch for player death noise sound effect
		try 
		{
			File soundFile3 = new File("player_death.wav");
			AudioInputStream audioIn3 = AudioSystem.getAudioInputStream(soundFile3);
			pDeath = AudioSystem.getClip();
			pDeath.open(audioIn3);
			fc3 = (FloatControl)pDeath.getControl(FloatControl.Type.MASTER_GAIN);
			fc3.setValue(-8.0f);
			pDeath.start(); 
		}
		catch(Exception e3)
		{
			//Show the exception that the file was not found
			JOptionPane.showMessageDialog(null, "File not Found");
		}
	}

	// Mouse Pressed Methood
	public void mousePressed(MouseEvent e) 
	{

	}

	//Mouse Released methood
	public void mouseReleased(MouseEvent e) 
	{

	}

	//Mouse Pressed Methood
	public void mouseEntered(MouseEvent e) 
	{

	}


	//Mouse Exited Methood
	public void mouseExited(MouseEvent e) 
	{

	}

	//Key Typed Methood
	public void keyTyped(KeyEvent e)
	{

	}

	//KeyPressed method
	public void keyPressed(KeyEvent e)
	{
		//if W is pressed, boolean up turns to true and the player will move up
		if (e.getKeyCode() == KeyEvent.VK_W) 
		{
			up = true;
		}

		//if A is pressed, boolean left turns to true and the player will move left
		if (e.getKeyCode() == KeyEvent.VK_A) 
		{
			left = true;
		}

		//if S is pressed, boolean down turns to true and the player will move down
		if (e.getKeyCode() == KeyEvent.VK_S) 
		{
			down = true;
		}

		//if D is pressed, boolean right turns to true and the player will move right
		if (e.getKeyCode() == KeyEvent.VK_D) 
		{
			right = true;
		}
	}

	//KeyReleased method
	public void keyReleased(KeyEvent e)
	{
		//if W is released, boolean up turns to false and the player will stop moving up
		if (e.getKeyCode() == KeyEvent.VK_W) 
		{
			up = false;
		}

		//if A is released, boolean left turns to false and the player will stop moving left
		if (e.getKeyCode() == KeyEvent.VK_A) 
		{
			left = false;
		}

		//if S is released, boolean down turns to false and the player will stop moving down
		if (e.getKeyCode() == KeyEvent.VK_S) 
		{
			down = false;
		}

		//if D is released, boolean right turns to false and the player will stop moving right
		if (e.getKeyCode() == KeyEvent.VK_D) 
		{
			right = false;
		}
	}

	//Self-made method to form all the screen13 messages into one
	private void screen13Message() 
	{
		JOptionPane.showMessageDialog(null, "Transporting you to Nemesis' lair.", "Tranport to Nemesis", JOptionPane.PLAIN_MESSAGE, gemini);
		JOptionPane.showMessageDialog(null, "Nemesis has 300 health, but each click will deal 20 damage to him, bnut you take 10 back if he intersects you.", "Tranport to Nemesis", JOptionPane.PLAIN_MESSAGE, gemini);
		JOptionPane.showMessageDialog(null, "The fate of the world is in your hands...", "Transport to Nemesis", JOptionPane.PLAIN_MESSAGE, gemini);
		JOptionPane.showMessageDialog(null, "T-minus 3...", "Transport to Nemesis", JOptionPane.PLAIN_MESSAGE, gemini);
		JOptionPane.showMessageDialog(null, "T-minus 2...", "Transport to Nemesis", JOptionPane.PLAIN_MESSAGE, gemini);
		JOptionPane.showMessageDialog(null, "T-minus 1...", "Transport to Nemesis", JOptionPane.PLAIN_MESSAGE, gemini);
		JOptionPane.showMessageDialog(null, "See you soon hero.", "Transport to Nemesis", JOptionPane.PLAIN_MESSAGE, gemini);
	}

	//self made method to conclude the game using jOptionPane
	private void conclusionMessage()
	{
		JOptionPane.showMessageDialog(null, "You have been of great assistance hero", "Thank you...", JOptionPane.PLAIN_MESSAGE, gemini);
		JOptionPane.showMessageDialog(null, "Code Nexus and the entire world will forever be indebt to you and your act to save the planet from Nemesis", "Thank you...", JOptionPane.PLAIN_MESSAGE, gemini);
		JOptionPane.showMessageDialog(null, "We hope that Nemesis does not attack again.\nBut we will await for you to come back again.", "Thank you...", JOptionPane.PLAIN_MESSAGE, gemini);
		JOptionPane.showMessageDialog(null, "THIS IS NOT THE END OF ME!", "RAH!", JOptionPane.PLAIN_MESSAGE, nemesis);
		JOptionPane.showMessageDialog(null, "CTRL + X... and delete.\nI, Gemini, has removed Nemesis from System32 of the computer!", "Thank you...", JOptionPane.PLAIN_MESSAGE, gemini);
		JOptionPane.showMessageDialog(null, "Signing off...", "Goodbye", JOptionPane.PLAIN_MESSAGE, gemini);
				
		response = JOptionPane.showOptionDialog(null, "Did you enjoy the game?", "Feedback", JOptionPane.WARNING_MESSAGE,0, null, feedback, null);
	}

	//Respawn method for virus 1
	private void respawnBV1() 
	{
		//Random x position, random y-position
		x_bv1 = (int) (Math.random() * (getWidth() - bv1.getIconWidth()));
		y_bv1 = (int) (Math.random() * (getHeight() - bv1.getIconHeight()));
	}

	//Respawn method for virus 2
	private void respawnBV2() 
	{
		//Random x position, random y-position
		x_bv2 = (int) (Math.random() * (getWidth() - bv2.getIconWidth()));
		y_bv2 = (int) (Math.random() * (getHeight() - bv2.getIconHeight()));
	}

	//Respawn method for virus 3
	private void respawnBV3() 
	{
		//Random x position, random y-position
		x_bv3 = (int) (Math.random() * (getWidth() - bv3.getIconWidth()));
		y_bv3 = (int) (Math.random() * (getHeight() - bv3.getIconHeight()));
	}
	
	//Respawn method for nemesis boss
	private void respawnNemesis() 
	{
		//Random x position, random y-position
		nemesisX = (int) (Math.random() * (getWidth() - nemesis.getIconWidth()));
		nemesisY = (int) (Math.random() * (getHeight() - nemesis.getIconHeight()));	
	}
}