//Imports needed for this class
import java.util.Random;
import javax.swing.ImageIcon;

//blueVirus Class
public class blueVirus 
{
	//Fields
	private int xPos_B, yPos_B, width_B, height_B;
	private ImageIcon BV_Left;
	private Random rand = new Random();

	//Constructor
	public blueVirus()
	{
		//Initialize all variables
		xPos_B = 0;
		yPos_B = 0;
		width_B = 0;
		height_B = 0;

		//Initialize image for virus
		BV_Left = new ImageIcon("Virus_BLeft.png");
	}

	//Methods
	//Set x for virus
	public void setX_B (int x_b)
	{
		xPos_B = x_b;
	}
	
	//Set y for virus
	public void setY_B (int y_b)
	{
		yPos_B = y_b;
	}
	
	//Get the x for virus
	public int getX_B ()
	{
		return xPos_B;
	}
	
	//Get the y for virus
	public int getY_B ()
	{
		return yPos_B;
	}
	
	//Get image method for virus, should return BV_Left
	public ImageIcon getImage(int direction_b)
	{	
		return BV_Left;
	}
	
	//Get the height for the virus  
	public int getHeight_B()
	{
		return height_B;
	}

	//Get the width for the virus
	public int getWidth_B()
	{
		return width_B;
	}
	
	//Get the movement of the virus across the pixels in the frame
	public void move(int pixels)
	{
		xPos_B -= pixels;
	}

	//Set the location of the virus randomly
	public void setLocation(int iconWidth, int iconHeight) {
		xPos_B = rand.nextInt(1, iconWidth - 80);
		yPos_B = rand.nextInt(1, iconHeight - 80);
	}
}