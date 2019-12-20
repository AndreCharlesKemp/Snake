/*
	Abutton.java
*/

import java.awt.*;
import java.awt.event.*;

public class Abutton {
    public static final
    int BUTTON_WIDTH = 72,        //	To define a "standard" size button
            BUTTON_HEIGHT = 30;

//
//	Constructors
//

    //	Instantiates a button from scratch.
    public Abutton() {
        this("?????", Color.black, 100, 100);
    }

    //	Instantiates a button of a "standard" size,
    //		given the rest of its components.
    public Abutton(String someLabel, Color someColor,
                   int someX, int someY) {
        this(someLabel, someColor, someX, someY,
                BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    //	Instantiates a button given all its components.
    public Abutton(String someLabel, Color someColor,
                   int someX, int someY,
                   int someWidth, int someHeight) {
        setup(someLabel, someColor, someX, someY,
                someWidth, someHeight);
    }

    //
    //	Initializes the components of a button.
    //
    public void setup(String someLabel, Color someColor,
                      int someX, int someY,
                      int someWidth, int someHeight) {
        theLabel = someLabel;
        theColor = someColor;
        x = someX;
        y = someY;
        width = someWidth;
        height = someHeight;
        up = true;
    }

    //
    //	Flips the up/down state of a button (for 3-D effects)
    //
    public void flip() {
        up = !up;
    }

    //
    //	Determines if a given point is inside a button.
    //	Note:  The boundary is considered within (inside) the button.
    //
    public boolean isInside(int someX, int someY) {
        return ((someX >= x) && (someX <= x + width)
                && (someY >= y) && (someY <= y + height));
    }

    //
    //	Draws a button
    //
    public void paint(Graphics pane) {
        final int DELTA = 2;                //	To define the space between the
        //		frame and the inside of a button

        pane.setColor(Color.black);            //	Drawing the button frame
        pane.drawRect(x, y, width, height);    //		in black,

        pane.setColor(theColor);            //	and the inside of the button
        pane.fill3DRect(x + DELTA, y + DELTA,    //	in the button color
                width - (2 * DELTA - 1),
                height - (2 * DELTA - 1),
                up);

        pane.setColor(Color.black);            //	Finally, we put the label in black
        //		... and nicely centered
        int labelWidth = pane.getFontMetrics().stringWidth(theLabel);
        int labelHeight = pane.getFontMetrics().getAscent();
        pane.drawString(theLabel,
                x + (width - labelWidth) / 2,
                y + (height + labelHeight) / 2);
    }

    public String getLabel() {
        return theLabel;
    }

    //
//	The instance variables
//
    private String theLabel;                //	To hold the label,
    private Color theColor;                    //		the color,
    private int x, y,                        //		the location, and
            width, height;                //		the size of the button

    private boolean up;                        //	To achieve 3-D effects

}    //	end Abutton