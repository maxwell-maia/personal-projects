import java.awt.Image;

public class Helicopter extends Sprite2D
{
    private double xSpeed = 0;

    private double engineSpeed = 0;

    private double gravityBase = 0.15;

    private double gravityAcc = 0.15;

    private double terminalVel = 6;

    private boolean throttle = false;

    private double maxThrust = -5;

    private boolean grounded = false;

    public Helicopter(Image i)
    {
        // send image to Sprite2D
        // invoke constructor of superclass Sprite2D
        super(i);

        x = winWidth / 2;
        y = 500;
    }

    public void setXSpeed(double dx)
    {
        xSpeed = dx;
    }

    public void applyThrottle()
    {
        throttle = true;
    }
    public void releaseThrottle()
    {
        throttle = false;
    }

    // move the helicopter
    // called every frame
    public void move()
    {
        // check if no longer grounded
        if (y != (winHeight - 200))
        {
            grounded = false;
        }

        // apply current movement
        // x
        x += xSpeed;

        // y
        // I tried a simple solution where ySpeed would gradually increase when the throttle was held
        // and gradually decrease when the throttle was not held
        // but the helicopter "felt too lightweight" when flying
        // so, I simulated helicopter engine speed and gravity separately
        // I want the helicopter flying to feel "floaty" and the helicopter should feel like it has some weight to it

        //Note: Upwards is negative

        // helicopter engine
        // makes the flying feel floaty because it's downward acceleration
        // when letting go of the throttle is not as great as that caused by gravity

        // throttle pressed
        if(throttle)
        {
            // if grounded ySpeed is 0
            if(grounded)
            {
                engineSpeed = 0;
            }

            // accelerate upwards
            engineSpeed -= 0.1;

            // no faster than the maximum thrust
            if (engineSpeed <= maxThrust)
            {
                engineSpeed = maxThrust;
            }

            // currently only speed has changed
            // the displacements are applied in the next if statement
        }
        else
        {
            // when the throttle is released

            // accelerate downwards
            engineSpeed += 0.1;

            // currently only speed has changed
            // the displacements are applied in the next if statement

            // when engineSpeed is above 0
            // the displacement of the helicopter will be handled by gravity

            // but keep accelerating the engine speed downwards
            // up to a maximum of 5
            if (engineSpeed >= 5)
            {
                engineSpeed = 5;
            }
            // this will act as a buffer
            // when the falling helicopter begins to accelerate upwards
            // it takes longer to overcome gravity, the longer it has fallen for

            // this makes the helicopter feel like it has some weight to it
        }

        // helicopter engine displacement
        if (engineSpeed <= 0)
        {
            // apply the displacement caused by the engine
            y += engineSpeed;
        }

        // gravity
        // when the engineSpeed is less than 0, gravity does not handle helicopter displacement
        if (engineSpeed < 0)
        {
            // be ready to start downwards acceleration from base acceleration
            gravityAcc = gravityBase;

            // displacement due to gravity is not applied
            // the engine is currently handling helicopter displacement
        }
        else
        {
            // when the engineSpeed is greater than 0
            // gravity handles helicopter displacement

            // if using throttle when displacement is handled by gravity
            if (throttle)
            {
                // slow down the fall
                gravityAcc -= 0.1;
            }
            else
            {
                // no throttle is applied.
                // accelerate gravity
                gravityAcc += gravityBase;
            }

            // don't accelerate gravity further than terminal velocity
            if (gravityAcc >= terminalVel)
            {
                gravityAcc = terminalVel;
            }

            // apply displacement due to gravity
            y += gravityAcc;
        }

        // grounded
        if (y > winHeight - 200)
        {
            y = winHeight - 200;
            grounded = true;
        }
    }

}
