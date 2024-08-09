

//Imports needed for this class
import javax.swing.ImageIcon;

//CTRLX_Player Class
public class CTRLX_Player 
{
	//Fields
	private int xPos_P, yPos_P, width_P, height_P, direction_P;
	private ImageIcon P_Right, P_Left, P_Up, P_Down;

	//Constants for the direction
	public final static int NORTH = 0;
	public final static int EAST = 1;
	public final static int SOUTH = 2;
	public final static int WEST = 3;

	//Constructor
	public CTRLX_Player()
	{
		//Initialize all integer variables
		xPos_P = 0;
		yPos_P = 0;
		width_P = 0;
		height_P = 0;
		direction_P = 0;
		
		//Initialize images for player
		P_Right = new ImageIcon ("Player Images/P_Right.png");
		P_Left = new ImageIcon ("Player Images/P_Left.png");
		P_Up = new ImageIcon ("Player Images/P_Front.png");
		P_Down = new ImageIcon("Player Images/P_Back.png");

	}
	
	//Methods	
	//Set the x for the player
	public void setX_P (int x)
	{
		xPos_P = x;
	}
	
	//Set the y for the player
	public void setY_P (int y)
	{
		yPos_P = y;
	}
	
	//Get the x for the player
	public int getX_P ()
	{
		return xPos_P;
	}
	
	//Get the y for the player
	public int getY_P ()
	{
		return yPos_P;
	}
	
	//Get the movement of the player across the pixels in the frame
	//Right movement
	public void moveUp (int pixels)
	{
		yPos_P -= pixels;
		direction_P = NORTH;
	}
	
	//Down movement
	public void moveDown (int pixels)
	{
		yPos_P += pixels;
		direction_P = SOUTH;
	}
	
	//Left movement
	public void moveLeft (int pixels)
	{
		xPos_P -= pixels;
		direction_P = WEST;
	}
	
	//Right movement
	public void moveRight (int pixels)
	{
		xPos_P += pixels;
		direction_P = EAST; 
	}

	//Get the image of the player based on direction moved
	public ImageIcon getImage(int direction)
	{
		if (direction_P == EAST)
		{
			return P_Right;
		}
		
		else if (direction_P == WEST)
		{
			return P_Left;
		}
		
		else if (direction_P == SOUTH)
		{
			return P_Down;
		}
		
		else
		{
			return P_Up;
		}
	}
	
	//Get height of the player
	public int getHeight()
	{
		return height_P;
	}
	
	//Get width of the player
	public int getWidth()
	{
		return width_P;
	}
}