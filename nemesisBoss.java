
//Imports needed for this class
import java.util.Random;

//nlueVirus Class
public class nemesisBoss 
{
	//Fields
	private int xPos_N, yPos_N, width_N, height_N;
	private Random rand = new Random();

	//Constructor
	public nemesisBoss()
	{
		//Initialize all variable
		
		xPos_N = 0;
		yPos_N = 0;
		width_N = 0;
		height_N = 0;
	}

	//Methods
	//Set x for Nemesis
	public void setX_N (int x_n)
	{
		xPos_N = x_n;
	}
	
	//Set y for Nemesis
	public void setY_N (int y_n)
	{
		yPos_N = y_n;
	}
	
	//Get the x for Nemesis
	public int getX_N ()
	{
		return xPos_N;
	}
	
	//Get the y for Nemesis
	public int getY_N ()
	{
		return yPos_N;
	}
	
	//Get the height for the Nemesis  
	public int getHeight_N()
	{
		return height_N;
	}

	//Get the width for the Nemesis
	public int getWidth_N()
	{
		return width_N;
	}
	
	//Get the movement of the Nemesis across the pixels in the frame
	public void move(int pixels)
	{
		xPos_N -= pixels;
	}

	//Set the location of the Nemesis randomly
	public void setLocation(int iconWidth, int iconHeight) {
		xPos_N = rand.nextInt(1, iconWidth - 80);
		yPos_N = rand.nextInt(1, iconHeight - 80);
	}
}