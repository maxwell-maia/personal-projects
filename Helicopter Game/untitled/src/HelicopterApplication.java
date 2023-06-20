import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;

public class HelicopterApplication extends JFrame implements Runnable, KeyListener
{
    private static String workingDirectory;

    private static boolean isInitialised = false;

    private static final Dimension WindowSize = new Dimension(1280, 720);

    private BufferStrategy strategy;

    private Graphics offscreenGraphics;

    private Helicopter helicopter;

    private Image helicopterImage;

    private Image backgroundImage;

    public HelicopterApplication()
    {
        // displays the window centre screen
        Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int x = screensize.width/2 - WindowSize.width/2;
        int y = screensize.height/2 - WindowSize.height/2;
        setBounds(x, y, WindowSize.width, WindowSize.height);
        setVisible(true);
        setTitle("My Game");

        // load image from disk
        ImageIcon icon = new ImageIcon(workingDirectory + "\\resources\\images\\helicopter.png");
        helicopterImage = icon.getImage();

        icon = new ImageIcon(workingDirectory + "\\resources\\images\\forest_1280_720.png");
        backgroundImage = icon.getImage();

        // create the helicopter
        helicopter = new Helicopter(helicopterImage);

        // tell all sprites the window width and height
        Sprite2D.setWinWidth(WindowSize.width);
        Sprite2D.setWinHeight(WindowSize.height);

        // create and start the animation thread
        Thread t = new Thread(this);
        t.start();

        // send keyboard events arriving into this JFrame to its own event handlers
        addKeyListener(this);

        // initialise double-buffering
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        offscreenGraphics = strategy.getDrawGraphics();

        isInitialised = true;
    }

    // thread's entry point
    public void run()
    {

        while (true)
        {
            // 1: sleep for 1/50 seconds
            try
            {
                Thread.sleep(20);
            }
            catch (InterruptedException e)
            {

            }

            // 2: animate game object
            helicopter.move();

            // 3: force an application repaint
            this.repaint();
        }
    }

    // Keyboard event-handler functions
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_A)
        {
            helicopter.setXSpeed(-4);
        }
        else if (e.getKeyCode() == KeyEvent.VK_D)
        {
            helicopter.setXSpeed(4);
        }
        else if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            helicopter.applyThrottle();
        }
    }

    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D)
        {
            helicopter.setXSpeed(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            helicopter.releaseThrottle();
        }
    }

    public void keyTyped(KeyEvent e)
    {

    }

    public void paint(Graphics g)
    {
        if (!isInitialised)
        {
            return;
        }

        // draw to offscreen buffer
        g = offscreenGraphics;

        // clear the canvas with a big black rectangle
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WindowSize.width, WindowSize.height);

        // paint the background image
        g.drawImage(backgroundImage, 0, 0, null);

        // redraw game objects
        helicopter.paint(g);

        // flip the buffers
        // the image in offscreen goes onscreen
        // this is double buffering
        // the next frame is drawn fully in an offscreen buffer
        // and shown when the frame is drawn
        strategy.show();
    }

    public static void main(String[] args)
    {
        workingDirectory = System.getProperty("user.dir");
        HelicopterApplication h = new HelicopterApplication();
        System.out.println("Welcome to my Helicopter game");
        System.out.println("Hold Space to fly");
        System.out.println("A and D keys move left and right");
        System.out.println("Have fun flying around");
    }
}
