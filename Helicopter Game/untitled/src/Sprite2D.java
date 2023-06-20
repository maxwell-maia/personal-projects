import java.awt.*;
public class Sprite2D
{

    // member data of a 2D sprite
    // protected: all objects that extend 2D sprite can access x,y
    // outside objects can't access x, y
    protected double x, y;

    protected Image myImage;

    protected static int winWidth;

    protected static int winHeight;


    public Sprite2D(Image i)
    {
        myImage = i;
    }

    public void setPosition(double xx, double yy)
    {
        // change position
        x = xx;
        y = yy;
    }

    public void paint(Graphics g)
    {
        // draw sprite
        g.drawImage(myImage, (int)x, (int)y, null);
    }

    public static void setWinWidth(int w)
    {
        winWidth = w;
    }

    public static void setWinHeight(int h)
    {
        winHeight = h;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }
}
