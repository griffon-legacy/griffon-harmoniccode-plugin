/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hansolo.custom;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Transparency;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.AttributedString;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import eu.hansolo.gradients.GradientWrapper;

public class Score extends JComponent {

    private PropertyChangeSupport propertySupport;
    public enum ColorDirection {
        RED_TO_GREEN,
        GREEN_TO_RED
    }
    private final Rectangle       INNER_BOUNDS = new Rectangle(0, 0, 141, 51);
    private int                   horizontalAlignment;
    private int                   verticalAlignment;
    private int                   noOfArrows;
    private int                   score;
    private boolean               textVisible;
    private Color                 textColor;
    private BufferedImage         off_Image;
    private BufferedImage         on_Image;
    private ColorDirection        colorDirection;
    private transient final       ComponentListener COMPONENT_LISTENER = new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent event) {
            final int SIZE   = getWidth() <= getHeight() ? getWidth() : getHeight();
            Container parent = getParent();
            if ((parent != null) && (parent.getLayout() == null)) {
                if (SIZE < getMinimumSize().width || SIZE < getMinimumSize().height) {
                    setSize(getMinimumSize());
                } else {
                    setSize(getWidth(), getHeight());
                }
            } else {
                if (SIZE < getMinimumSize().width || SIZE < getMinimumSize().height) {
                    setPreferredSize(getMinimumSize());
                } else {
                    setPreferredSize(new Dimension(getWidth(), getHeight()));
                }
            }
            calcInnerBounds();
            init(INNER_BOUNDS.width, INNER_BOUNDS.height);
        }
    };


    // ******************** Constructor ***************************************
    public Score() {
        super();
        propertySupport     = new PropertyChangeSupport(this);

        noOfArrows          = 5;
        score               = 0;
        textVisible         = true;
        textColor           = Color.BLACK;
        off_Image           = createImage(INNER_BOUNDS.width, INNER_BOUNDS.height, Transparency.TRANSLUCENT);
        on_Image            = createImage(INNER_BOUNDS.width, INNER_BOUNDS.height, Transparency.TRANSLUCENT);
        colorDirection      = ColorDirection.RED_TO_GREEN;
        horizontalAlignment = SwingConstants.CENTER;
        verticalAlignment   = SwingConstants.CENTER;

        addComponentListener(COMPONENT_LISTENER);

    }


    // ******************** Initialization ************************************
    public final void init(final int WIDTH, final int HEIGHT) {
        if (WIDTH <= 1 || HEIGHT <= 1) {
            return;
        }
        //if (text_Image != null) {
        //    text_Image.flush();
        //}
        //text_Image = create_Text_Image(WIDTH, HEIGHT);
        if (off_Image != null) {
            off_Image.flush();
        }
        off_Image = createOffImage(WIDTH, HEIGHT);
        if (on_Image != null) {
            on_Image.flush();
        }
        on_Image = createOnImage(WIDTH, HEIGHT);
    }


    // ******************** Visualization *************************************
    @Override
    protected void paintComponent(final Graphics G) {
        // Create the Graphics2D object
        final Graphics2D G2 = (Graphics2D) G.create();

        // Set the rendering hints
        G2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		G2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        G2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        G2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);

        // Take insets into account (e.g. used by borders)
        G2.translate(INNER_BOUNDS.x, INNER_BOUNDS.y);

        //G2.drawImage(text_Image, 0, 0, null);
        G2.drawImage(off_Image, 0, 0, null);
        G2.drawImage(on_Image, 0, 0, null);

        // Dispose the temp graphics object
        G2.dispose();
    }


    // ******************** Methods *******************************************
    public int getNoOfArrows() {
        return noOfArrows;
    }

    public void setNoOfArrows(final int NO_OF_ARROWS) {
        noOfArrows = NO_OF_ARROWS < 3 ? 3 : (NO_OF_ARROWS > 10 ? 10 : NO_OF_ARROWS);
        init(INNER_BOUNDS.width, INNER_BOUNDS.height);
        repaint(INNER_BOUNDS);
    }

    public int getScore() {
        return score;
    }

    public void setScore(final int SCORE) {
        score = SCORE < 0 ? 0 : (SCORE > noOfArrows ? noOfArrows : SCORE);
        init(INNER_BOUNDS.width, INNER_BOUNDS.height);
        repaint(INNER_BOUNDS);
    }

    public boolean isTextVisible() {
        return textVisible;
    }

    public void setTextVisible(final boolean TEXT_VISIBLE) {
        textVisible = TEXT_VISIBLE;
        init(INNER_BOUNDS.width, INNER_BOUNDS.height);
        repaint(INNER_BOUNDS);
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(final Color TEXT_COLOR) {
        textColor = TEXT_COLOR;
        init(INNER_BOUNDS.width, INNER_BOUNDS.height);
        repaint(INNER_BOUNDS);
    }

    public ColorDirection getColorDirection() {
        return colorDirection;
    }

    public void setColorDirection(final ColorDirection COLOR_DIRECTION) {
        colorDirection = COLOR_DIRECTION;
        init(INNER_BOUNDS.width, INNER_BOUNDS.height);
        repaint(INNER_BOUNDS);
    }

    public int getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public void setHorizontalAlignment(final int HORIZONTAL_ALIGNMENT) {
        horizontalAlignment = HORIZONTAL_ALIGNMENT;
    }

    public int getVerticalAlignment() {
        return verticalAlignment;
    }

    public void setVerticalAlignment(final int VERTICAL_ALIGNMENT) {
        verticalAlignment = VERTICAL_ALIGNMENT;
    }

    @Override
    public void addPropertyChangeListener(final PropertyChangeListener LISTENER) {
        if (isShowing()) {
            propertySupport.addPropertyChangeListener(LISTENER);
        }
    }

    @Override
    public void removePropertyChangeListener(final PropertyChangeListener LISTENER) {
        propertySupport.removePropertyChangeListener(LISTENER);
    }

    /**
    * Calculates the area that is available for painting the display
    */
    private void calcInnerBounds() {
        final Insets INSETS = getInsets();
        INNER_BOUNDS.setBounds(INSETS.left, INSETS.top, getWidth() - INSETS.left - INSETS.right, getHeight() - INSETS.top - INSETS.bottom);
    }

    /**
     * Returns a rectangle representing the available space for drawing the
     * component taking the insets into account (e.g. given through borders etc.)
     * @return a rectangle that represents the area available for rendering the component
     */
    public Rectangle getInnerBounds() {
        return INNER_BOUNDS;
    }

    @Override
    public Dimension getMinimumSize() {
        /* Return the default size of the component
         * which will be used by ui-editors for initialization
         */
        return new Dimension(141, 51);
    }

	@Override
	public void setPreferredSize(final Dimension DIM) {
	    super.setPreferredSize(DIM);
	    calcInnerBounds();
	    init(INNER_BOUNDS.width, INNER_BOUNDS.height);
	}

	@Override
	public void setSize(final int WIDTH, final int HEIGHT) {
	    super.setSize(WIDTH, HEIGHT);
	    calcInnerBounds();
	    init(INNER_BOUNDS.width, INNER_BOUNDS.height);
	}

	@Override
	public void setSize(final Dimension DIM) {
	    super.setSize(DIM);
	    calcInnerBounds();
	    init(INNER_BOUNDS.width, INNER_BOUNDS.height);
	}

	@Override
	public void setBounds(final Rectangle BOUNDS) {
	    super.setBounds(BOUNDS);
	    calcInnerBounds();
	    init(INNER_BOUNDS.width, INNER_BOUNDS.height);
	}

	@Override
	public void setBounds(final int X, final int Y, final int WIDTH, final int HEIGHT) {
	    super.setBounds(X, Y, WIDTH, HEIGHT);
	    calcInnerBounds();
	    init(INNER_BOUNDS.width, INNER_BOUNDS.height);
	}

    /**
     * Returns a compatible image of the given size and transparency
     * @param WIDTH
     * @param HEIGHT
     * @param TRANSPARENCY
     * @return a compatible image of the given size and transparency
     */
    private BufferedImage createImage(final int WIDTH, final int HEIGHT, final int TRANSPARENCY) {
        final GraphicsConfiguration GFX_CONF = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        if (WIDTH <= 0 || HEIGHT <= 0) {
            return GFX_CONF.createCompatibleImage(1, 1, TRANSPARENCY);
        }
        final BufferedImage IMAGE = GFX_CONF.createCompatibleImage(WIDTH, HEIGHT, TRANSPARENCY);
        return IMAGE;
    }


	// ******************** Image methods *************************************
    public BufferedImage createOffImage(final int WIDTH, final int HEIGHT) {
        if (WIDTH <= 0 || HEIGHT <= 0) {
            return createImage(1, 1, Transparency.TRANSLUCENT);
        }
        final BufferedImage IMAGE = createImage(WIDTH, HEIGHT, Transparency.TRANSLUCENT);
        final Graphics2D G2 = IMAGE.createGraphics();
        G2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        G2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        G2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        G2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        final int             IMAGE_WIDTH  = IMAGE.getWidth();
        final int             IMAGE_HEIGHT = IMAGE.getHeight();
        final int             FONT_SIZE    = (int)(0.05 * IMAGE_WIDTH);
        final Font            FONT         = new Font("sans-serif", Font.PLAIN, FONT_SIZE);
        final int             SPACER       = (int) (IMAGE_WIDTH * 0.04);
        final int             ARROW_WIDTH  = (IMAGE_WIDTH / noOfArrows) + (int) (IMAGE_WIDTH * 0.032);
        final int             ARROW_HEIGHT = textVisible ? IMAGE_HEIGHT - FONT_SIZE : IMAGE_HEIGHT;

        for (int i = score ; i < noOfArrows ; i++) {
            BufferedImage arrow = createArrow(ARROW_WIDTH, ARROW_HEIGHT, Color.GRAY, false);
            G2.drawImage(arrow, i * (ARROW_WIDTH - SPACER), 0, null);
        }

        if (textVisible) {
            G2.setPaint(textColor);
            for (int i = 0 ; i < noOfArrows ; i++) {
                final AttributedString TEXT = new AttributedString(Integer.toString(i + 1));
                TEXT.addAttribute(TextAttribute.FONT, FONT);
                TEXT.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
                G2.setFont(FONT);
                float fontOffsetY = (1.0196078431372548f * IMAGE_HEIGHT) - (new TextLayout(Integer.toString(i + 1), G2.getFont(), G2.getFontRenderContext()).getDescent());
                G2.drawString(TEXT.getIterator(), i * (ARROW_WIDTH - SPACER) + (ARROW_WIDTH / 3), fontOffsetY);
            }
        }

        G2.dispose();
        return IMAGE;
    }

    public BufferedImage createOnImage(final int WIDTH, final int HEIGHT) {
        if (WIDTH <= 0 || HEIGHT <= 0) {
            return createImage(1, 1, Transparency.TRANSLUCENT);
        }
        final BufferedImage IMAGE = createImage(WIDTH, HEIGHT, Transparency.TRANSLUCENT);
        final Graphics2D G2 = IMAGE.createGraphics();
        G2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        G2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        G2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        G2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        final int             IMAGE_WIDTH   = IMAGE.getWidth();
        final int             IMAGE_HEIGHT  = IMAGE.getHeight();
        final int             FONT_SIZE     = (int)(0.05 * IMAGE_WIDTH);
        final Font            FONT          = new Font("sans-serif", Font.PLAIN, FONT_SIZE);
        final int             SPACER        = (int) (IMAGE_WIDTH * 0.04);
        final int             ARROW_WIDTH   = (IMAGE_WIDTH / noOfArrows) + (int) (IMAGE_WIDTH * 0.032);
        final int             ARROW_HEIGHT  = textVisible ? (IMAGE_HEIGHT - FONT_SIZE - (int)(0.005 * IMAGE_WIDTH)) : IMAGE_HEIGHT;
        final float           COLOR_STEP    = 1f / (float) (noOfArrows - 1);
        final Color[]         LOOKUP_COLORS;
        if (colorDirection == ColorDirection.RED_TO_GREEN) {
            LOOKUP_COLORS = new Color[]{
                Color.RED,
                Color.YELLOW,
                Color.GREEN
            };
        } else {
            LOOKUP_COLORS = new Color[]{
                Color.GREEN,
                Color.YELLOW,
                Color.RED
            };
        }
        final GradientWrapper COLOR_LOOKUP = new GradientWrapper(new Point2D.Double(0, 0),
                                                                 new Point2D.Double(100, 0),
                                                                 new float[]{0.0f, 0.5f, 1.0f},
                                                                 LOOKUP_COLORS);

        for (int i = 0 ; i < score ; i++) {
            BufferedImage arrow = createArrow(ARROW_WIDTH, ARROW_HEIGHT, COLOR_LOOKUP.getColorAt(i * COLOR_STEP), true);
            G2.drawImage(arrow, i * (ARROW_WIDTH - SPACER), 0, null);
        }

        G2.dispose();
        return IMAGE;
    }

    public BufferedImage createArrow(final int WIDTH, final int HEIGHT, final Color COLOR, final boolean FILLED) {
        if (WIDTH <= 0 || HEIGHT <= 0) {
            return createImage(1, 1, Transparency.TRANSLUCENT);
        }
        final BufferedImage IMAGE = createImage(WIDTH, HEIGHT, Transparency.TRANSLUCENT);
        final Graphics2D G2 = IMAGE.createGraphics();
        G2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        G2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        G2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        G2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        final int IMAGE_WIDTH = IMAGE.getWidth();
        final int IMAGE_HEIGHT = IMAGE.getHeight();
        final GeneralPath ARROW = new GeneralPath();
        ARROW.setWindingRule(Path2D.WIND_EVEN_ODD);
        if (FILLED) {
            ARROW.moveTo(IMAGE_WIDTH, 0.5135135135135135 * IMAGE_HEIGHT);
            ARROW.lineTo(0.65625 * IMAGE_WIDTH, IMAGE_HEIGHT);
            ARROW.lineTo(0.0, IMAGE_HEIGHT);
            ARROW.lineTo(0.34375 * IMAGE_WIDTH, 0.5135135135135135 * IMAGE_HEIGHT);
            ARROW.lineTo(0.0, 0.0);
            ARROW.lineTo(0.65625 * IMAGE_WIDTH, 0.0);
            ARROW.closePath();
        } else {
            ARROW.moveTo(0.9375 * IMAGE_WIDTH, 0.5135135135135135 * IMAGE_HEIGHT);
            ARROW.lineTo(0.625 * IMAGE_WIDTH, 0.9459459459459459 * IMAGE_HEIGHT);
            ARROW.lineTo(0.09375 * IMAGE_WIDTH, 0.9459459459459459 * IMAGE_HEIGHT);
            ARROW.lineTo(0.40625 * IMAGE_WIDTH, 0.5135135135135135 * IMAGE_HEIGHT);
            ARROW.lineTo(0.09375 * IMAGE_WIDTH, 0.05405405405405406 * IMAGE_HEIGHT);
            ARROW.lineTo(0.625 * IMAGE_WIDTH, 0.05405405405405406 * IMAGE_HEIGHT);
            ARROW.lineTo(0.9375 * IMAGE_WIDTH, 0.5135135135135135 * IMAGE_HEIGHT);
            ARROW.closePath();
            ARROW.moveTo(IMAGE_WIDTH, 0.5135135135135135 * IMAGE_HEIGHT);
            ARROW.lineTo(0.65625 * IMAGE_WIDTH, 0.0);
            ARROW.lineTo(0.0, 0.0);
            ARROW.lineTo(0.34375 * IMAGE_WIDTH, 0.5135135135135135 * IMAGE_HEIGHT);
            ARROW.lineTo(0.0, IMAGE_HEIGHT);
            ARROW.lineTo(0.65625 * IMAGE_WIDTH, IMAGE_HEIGHT);
            ARROW.lineTo(IMAGE_WIDTH, 0.5135135135135135 * IMAGE_HEIGHT);
            ARROW.closePath();
        }
        final Paint ARROW_FILL = new LinearGradientPaint(new Point2D.Double(0.0, 0.0),
                                                         new Point2D.Double(0.0, IMAGE_HEIGHT),
                                                         new float[]{0.0f, 0.5f, 1.0f},
                                                         new Color[]{COLOR.darker().darker(), COLOR, COLOR.darker().darker()});
        G2.setPaint(ARROW_FILL);
        G2.fill(ARROW);
        addInnerShadow(G2, ARROW, Color.BLACK, 0, 0.65f, 4, 0);

        G2.dispose();
        return IMAGE;
    }

    public void addInnerShadow(final Graphics2D G2, final Shape SHAPE, final Color SHADOW_COLOR, final int DISTANCE, final float ALPHA, final int SOFTNESS, final int ANGLE) {
        final float COLOR_CONSTANT = 1f / 255f;
        final float RED = COLOR_CONSTANT * SHADOW_COLOR.getRed();
        final float GREEN = COLOR_CONSTANT * SHADOW_COLOR.getGreen();
        final float BLUE = COLOR_CONSTANT * SHADOW_COLOR.getBlue();
        final float MAX_STROKE_WIDTH = SOFTNESS * 2;
        final float ALPHA_STEP = 1f / (2 * SOFTNESS + 2) * ALPHA;
        final float TRANSLATE_X = (float) (DISTANCE * Math.cos(Math.toRadians(ANGLE)));
        final float TRANSLATE_Y = (float) (DISTANCE * Math.sin(Math.toRadians(ANGLE)));

        // Enable Antialiasing
        G2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Store existing parameters
        final Shape OLD_CLIP = G2.getClip();
        final AffineTransform OLD_TRANSFORM = G2.getTransform();
        final Stroke OLD_STROKE = G2.getStroke();
        final Paint OLD_PAINT = G2.getPaint();

        // Set the color
        G2.setColor(new Color(RED, GREEN, BLUE, ALPHA_STEP));

        // Set the alpha transparency of the whole image
        G2.setComposite(AlphaComposite.SrcAtop);

        // Translate the coordinate system related to the given distance and angle
        G2.translate(TRANSLATE_X, -TRANSLATE_Y);

        G2.setClip(SHAPE);

        // Draw the inner shadow
        for (float strokeWidth = SOFTNESS; strokeWidth >= 1; strokeWidth -= 1) {
            G2.setStroke(new BasicStroke((float) (MAX_STROKE_WIDTH * Math.pow(0.85, strokeWidth))));
            G2.draw(SHAPE);
        }

        // Restore old parameters
        G2.setTransform(OLD_TRANSFORM);
        G2.setClip(OLD_CLIP);
        G2.setStroke(OLD_STROKE);
        G2.setPaint(OLD_PAINT);
    }
}
