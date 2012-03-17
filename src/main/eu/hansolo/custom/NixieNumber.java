package eu.hansolo.custom;

/**
 *
 * @author hansolo
 */
public class NixieNumber extends javax.swing.JComponent implements java.awt.event.ComponentListener
{
    private int number;
    private java.awt.image.BufferedImage[] numberStack = new java.awt.image.BufferedImage[10];
    private final float[] GLOW_FRACTIONS =
    {
        0.0f,
        0.5f,
        1.0f
    };
    private final java.awt.Color[] GLOW_COLORS =
    {
        new java.awt.Color(0.647f, 0.3137f, 0.0588f, 0.2f),
        new java.awt.Color(0.9882f, 0.5921f, 0.0f, 0.3f),
        new java.awt.Color(0.647f, 0.3137f, 0.0588f, 0.2f)
    };

    private java.awt.LinearGradientPaint glowGradient;
    private java.awt.geom.Rectangle2D glowBox;

    private static final java.awt.Color[] COLOR_ARRAY =
    {
        new java.awt.Color(1.0f, 0.6f, 0, 0.90f),
        new java.awt.Color(1.0f, 0.4f, 0, 0.80f),
        new java.awt.Color(1.0f, 0.4f, 0, 0.4f),
        new java.awt.Color(1.0f, 0.4f, 0, 0.15f),
        new java.awt.Color(1.0f, 0.4f, 0, 0.10f),
        new java.awt.Color(1.0f, 0.4f, 0, 0.05f)
    };

    private final java.util.ArrayList<java.awt.image.BufferedImage> INACTIVE_NUMBER_ARRAY = new java.util.ArrayList<java.awt.image.BufferedImage>(10);
    private final java.util.ArrayList<java.awt.image.BufferedImage> ACTIVE_NUMBER_ARRAY = new java.util.ArrayList<java.awt.image.BufferedImage>(10);


    public NixieNumber()
    {
        super();
        addComponentListener(this);
        number = -1;        
        setPreferredSize(new java.awt.Dimension(43, 73));
        init();
    }

    private void init()
    {        
        glowGradient = createGlowGradient(getPreferredSize().width);
        glowBox = createGlowBox(getPreferredSize().width);
        createAllNumbers();
        createNumberStack();
        repaint();
    }


    @Override
    protected void paintComponent(java.awt.Graphics g)
    {
        super.paintComponent(g);

        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();

        // Backside glow effect
        if (number > -1)
        {
            g2.setPaint(glowGradient);
            g2.fill(glowBox);
        }

        // Draw nixie number
        for (int i = 0 ; i < 10 ; i++)
        {
            g2.drawImage(this.numberStack[i], (int) (getWidth() * -0.06), 0, null);
        }

        // Draw hatch
        g2.drawImage(createHatchImage(getPreferredSize().width), 0, 0, null);

        // Draw tube
        g2.drawImage(createBackgroundImage(getPreferredSize().width), 0, 0, null);

        g2.dispose();
    }

    private java.awt.image.BufferedImage createBackgroundImage(final int WIDTH)
    {
        if (WIDTH <= 0)
        {
            return null;
        }

        final java.awt.GraphicsConfiguration GFX_CONF = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        final java.awt.image.BufferedImage IMAGE = GFX_CONF.createCompatibleImage(WIDTH, (int) (1.697674418604651 * WIDTH), java.awt.Transparency.TRANSLUCENT);
        final java.awt.Graphics2D G2 = IMAGE.createGraphics();

        G2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        G2.setRenderingHint(java.awt.RenderingHints.KEY_ALPHA_INTERPOLATION, java.awt.RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        G2.setRenderingHint(java.awt.RenderingHints.KEY_COLOR_RENDERING, java.awt.RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        G2.setRenderingHint(java.awt.RenderingHints.KEY_STROKE_CONTROL, java.awt.RenderingHints.VALUE_STROKE_PURE);

        final int IMAGE_WIDTH = IMAGE.getWidth();
        final int IMAGE_HEIGHT = IMAGE.getHeight();

        final java.awt.geom.GeneralPath TUBE = new java.awt.geom.GeneralPath();
        TUBE.setWindingRule(java.awt.geom.GeneralPath.WIND_EVEN_ODD);
        TUBE.moveTo(IMAGE_WIDTH * 0.0, IMAGE_HEIGHT * 0.3493150684931507);
        TUBE.curveTo(IMAGE_WIDTH * 0.0, IMAGE_HEIGHT * 0.3493150684931507, IMAGE_WIDTH * 0.0, IMAGE_HEIGHT * 0.636986301369863, IMAGE_WIDTH * 0.0, IMAGE_HEIGHT * 0.636986301369863);
        TUBE.lineTo(IMAGE_WIDTH * 0.0, IMAGE_HEIGHT * 0.6986301369863014);
        TUBE.curveTo(IMAGE_WIDTH * 0.0, IMAGE_HEIGHT * 0.6986301369863014, IMAGE_WIDTH * 0.0, IMAGE_HEIGHT * 0.8767123287671232, IMAGE_WIDTH * 0.0, IMAGE_HEIGHT * 0.8767123287671232);
        TUBE.curveTo(IMAGE_WIDTH * 0.0, IMAGE_HEIGHT * 0.9452054794520548, IMAGE_WIDTH * 0.09302325581395349, IMAGE_HEIGHT * 1.0, IMAGE_WIDTH * 0.20930232558139536, IMAGE_HEIGHT * 1.0);
        TUBE.curveTo(IMAGE_WIDTH * 0.20930232558139536, IMAGE_HEIGHT * 1.0, IMAGE_WIDTH * 0.8023255813953488, IMAGE_HEIGHT * 1.0, IMAGE_WIDTH * 0.8023255813953488, IMAGE_HEIGHT * 1.0);
        TUBE.curveTo(IMAGE_WIDTH * 0.9069767441860465, IMAGE_HEIGHT * 1.0, IMAGE_WIDTH * 1.0, IMAGE_HEIGHT * 0.9452054794520548, IMAGE_WIDTH * 1.0, IMAGE_HEIGHT * 0.8767123287671232);
        TUBE.curveTo(IMAGE_WIDTH * 1.0, IMAGE_HEIGHT * 0.8767123287671232, IMAGE_WIDTH * 1.0, IMAGE_HEIGHT * 0.6986301369863014, IMAGE_WIDTH * 1.0, IMAGE_HEIGHT * 0.6986301369863014);
        TUBE.lineTo(IMAGE_WIDTH * 1.0, IMAGE_HEIGHT * 0.636986301369863);
        TUBE.curveTo(IMAGE_WIDTH * 1.0, IMAGE_HEIGHT * 0.636986301369863, IMAGE_WIDTH * 1.0, IMAGE_HEIGHT * 0.3493150684931507, IMAGE_WIDTH * 1.0, IMAGE_HEIGHT * 0.3493150684931507);
        TUBE.curveTo(IMAGE_WIDTH * 1.0, IMAGE_HEIGHT * 0.19863013698630136, IMAGE_WIDTH * 0.813953488372093, IMAGE_HEIGHT * 0.07534246575342465, IMAGE_WIDTH * 0.5697674418604651, IMAGE_HEIGHT * 0.0547945205479452);
        TUBE.curveTo(IMAGE_WIDTH * 0.5697674418604651, IMAGE_HEIGHT * 0.0273972602739726, IMAGE_WIDTH * 0.5348837209302325, IMAGE_HEIGHT * 0.0, IMAGE_WIDTH * 0.5, IMAGE_HEIGHT * 0.0);
        TUBE.curveTo(IMAGE_WIDTH * 0.46511627906976744, IMAGE_HEIGHT * 0.0, IMAGE_WIDTH * 0.43023255813953487, IMAGE_HEIGHT * 0.0273972602739726, IMAGE_WIDTH * 0.43023255813953487, IMAGE_HEIGHT * 0.0547945205479452);
        TUBE.curveTo(IMAGE_WIDTH * 0.18604651162790697, IMAGE_HEIGHT * 0.07534246575342465, IMAGE_WIDTH * 0.0, IMAGE_HEIGHT * 0.19863013698630136, IMAGE_WIDTH * 0.0, IMAGE_HEIGHT * 0.3493150684931507);
        TUBE.closePath();
        final java.awt.geom.Point2D TUBE_START = new java.awt.geom.Point2D.Double(TUBE.getBounds2D().getMinX(), 0);
        final java.awt.geom.Point2D TUBE_STOP = new java.awt.geom.Point2D.Double(TUBE.getBounds2D().getMaxX(), 0);
        final float[] TUBE_FRACTIONS =
        {
            0.0f,
            0.06f,
            0.07f,
            0.35f,
            0.36f,
            0.66f,
            0.67f,
            0.92f,
            1.0f
        };
        final java.awt.Color[] TUBE_COLORS =
        {
            new java.awt.Color(0, 0, 0, 128),         
            new java.awt.Color(0, 0, 0, 77),
            new java.awt.Color(0, 0, 0, 75),
            new java.awt.Color(0, 0, 0, 26),
            new java.awt.Color(0, 0, 0, 26),
            new java.awt.Color(0, 0, 0, 26),
            new java.awt.Color(0, 0, 0, 26),
            new java.awt.Color(0, 0, 0, 130),
            new java.awt.Color(0, 0, 0, 153)
        };
        if (TUBE_START.distance(TUBE_STOP) > 0)
        {
            final java.awt.LinearGradientPaint TUBE_GRADIENT = new java.awt.LinearGradientPaint(TUBE_START, TUBE_STOP, TUBE_FRACTIONS, TUBE_COLORS);
            G2.setPaint(TUBE_GRADIENT);
            G2.fill(TUBE);
        }

        final java.awt.geom.GeneralPath SIDELIGHTEFFECT = new java.awt.geom.GeneralPath();
        SIDELIGHTEFFECT.setWindingRule(java.awt.geom.GeneralPath.WIND_EVEN_ODD);
        SIDELIGHTEFFECT.moveTo(IMAGE_WIDTH * 0.023255813953488372, IMAGE_HEIGHT * 0.3287671232876712);
        SIDELIGHTEFFECT.curveTo(IMAGE_WIDTH * 0.023255813953488372, IMAGE_HEIGHT * 0.3287671232876712, IMAGE_WIDTH * 0.023255813953488372, IMAGE_HEIGHT * 0.8356164383561644, IMAGE_WIDTH * 0.023255813953488372, IMAGE_HEIGHT * 0.8356164383561644);
        SIDELIGHTEFFECT.curveTo(IMAGE_WIDTH * 0.023255813953488372, IMAGE_HEIGHT * 0.910958904109589, IMAGE_WIDTH * 0.11627906976744186, IMAGE_HEIGHT * 0.9657534246575342, IMAGE_WIDTH * 0.2441860465116279, IMAGE_HEIGHT * 0.9657534246575342);
        SIDELIGHTEFFECT.curveTo(IMAGE_WIDTH * 0.2441860465116279, IMAGE_HEIGHT * 0.9657534246575342, IMAGE_WIDTH * 0.2558139534883721, IMAGE_HEIGHT * 0.9657534246575342, IMAGE_WIDTH * 0.26744186046511625, IMAGE_HEIGHT * 0.9657534246575342);
        SIDELIGHTEFFECT.curveTo(IMAGE_WIDTH * 0.1511627906976744, IMAGE_HEIGHT * 0.958904109589041, IMAGE_WIDTH * 0.06976744186046512, IMAGE_HEIGHT * 0.9041095890410958, IMAGE_WIDTH * 0.06976744186046512, IMAGE_HEIGHT * 0.8356164383561644);
        SIDELIGHTEFFECT.curveTo(IMAGE_WIDTH * 0.06976744186046512, IMAGE_HEIGHT * 0.8356164383561644, IMAGE_WIDTH * 0.06976744186046512, IMAGE_HEIGHT * 0.3287671232876712, IMAGE_WIDTH * 0.06976744186046512, IMAGE_HEIGHT * 0.3287671232876712);
        SIDELIGHTEFFECT.curveTo(IMAGE_WIDTH * 0.06976744186046512, IMAGE_HEIGHT * 0.2602739726027397, IMAGE_WIDTH * 0.1511627906976744, IMAGE_HEIGHT * 0.2054794520547945, IMAGE_WIDTH * 0.26744186046511625, IMAGE_HEIGHT * 0.19863013698630136);
        SIDELIGHTEFFECT.curveTo(IMAGE_WIDTH * 0.2558139534883721, IMAGE_HEIGHT * 0.19863013698630136, IMAGE_WIDTH * 0.2441860465116279, IMAGE_HEIGHT * 0.19863013698630136, IMAGE_WIDTH * 0.2441860465116279, IMAGE_HEIGHT * 0.19863013698630136);
        SIDELIGHTEFFECT.curveTo(IMAGE_WIDTH * 0.11627906976744186, IMAGE_HEIGHT * 0.19863013698630136, IMAGE_WIDTH * 0.023255813953488372, IMAGE_HEIGHT * 0.2602739726027397, IMAGE_WIDTH * 0.023255813953488372, IMAGE_HEIGHT * 0.3287671232876712);
        SIDELIGHTEFFECT.closePath();
        final java.awt.geom.Point2D SIDELIGHTEFFECT_START = new java.awt.geom.Point2D.Double(SIDELIGHTEFFECT.getBounds2D().getMinX(), 0);
        final java.awt.geom.Point2D SIDELIGHTEFFECT_STOP = new java.awt.geom.Point2D.Double(SIDELIGHTEFFECT.getBounds2D().getMaxX(), 0);
        final float[] SIDELIGHTEFFECT_FRACTIONS =
        {
            0.0f,
            1.0f
        };
        final java.awt.Color[] SIDELIGHTEFFECT_COLORS =
        {
            new java.awt.Color(255, 255, 255, 127),
            new java.awt.Color(255, 255, 255, 0)
        };
        if (SIDELIGHTEFFECT_START.distance(SIDELIGHTEFFECT_STOP) > 0)
        {
            final java.awt.LinearGradientPaint SIDELIGHTEFFECT_GRADIENT = new java.awt.LinearGradientPaint(SIDELIGHTEFFECT_START, SIDELIGHTEFFECT_STOP, SIDELIGHTEFFECT_FRACTIONS, SIDELIGHTEFFECT_COLORS);
            G2.setPaint(SIDELIGHTEFFECT_GRADIENT);
            G2.fill(SIDELIGHTEFFECT);
        }

        final java.awt.geom.GeneralPath STRIPELIGHTEFFECT = new java.awt.geom.GeneralPath();
        STRIPELIGHTEFFECT.setWindingRule(java.awt.geom.GeneralPath.WIND_EVEN_ODD);
        STRIPELIGHTEFFECT.moveTo(IMAGE_WIDTH * 0.13953488372093023, IMAGE_HEIGHT * 0.2602739726027397);
        STRIPELIGHTEFFECT.lineTo(IMAGE_WIDTH * 0.8604651162790697, IMAGE_HEIGHT * 0.2602739726027397);
        STRIPELIGHTEFFECT.lineTo(IMAGE_WIDTH * 0.8604651162790697, IMAGE_HEIGHT * 0.2671232876712329);
        STRIPELIGHTEFFECT.lineTo(IMAGE_WIDTH * 0.13953488372093023, IMAGE_HEIGHT * 0.2671232876712329);
        STRIPELIGHTEFFECT.lineTo(IMAGE_WIDTH * 0.13953488372093023, IMAGE_HEIGHT * 0.2602739726027397);
        STRIPELIGHTEFFECT.closePath();
        final java.awt.geom.Point2D STRIPELIGHTEFFECT_START = new java.awt.geom.Point2D.Double(STRIPELIGHTEFFECT.getBounds2D().getMinX(), 0);
        final java.awt.geom.Point2D STRIPELIGHTEFFECT_STOP = new java.awt.geom.Point2D.Double(STRIPELIGHTEFFECT.getBounds2D().getMaxX(), 0);
        final float[] STRIPELIGHTEFFECT_FRACTIONS =
        {
            0.0f,
            0.48f,
            0.49f,
            1.0f
        };
        final java.awt.Color[] STRIPELIGHTEFFECT_COLORS =
        {
            new java.awt.Color(255, 255, 255, 0),
            new java.awt.Color(255, 255, 255, 124),
            new java.awt.Color(255, 255, 255, 127),
            new java.awt.Color(255, 255, 255, 0)
        };
        if (STRIPELIGHTEFFECT_START.distance(STRIPELIGHTEFFECT_STOP) > 0)
        {
            final java.awt.LinearGradientPaint STRIPELIGHTEFFECT_GRADIENT = new java.awt.LinearGradientPaint(STRIPELIGHTEFFECT_START, STRIPELIGHTEFFECT_STOP, STRIPELIGHTEFFECT_FRACTIONS, STRIPELIGHTEFFECT_COLORS);
            G2.setPaint(STRIPELIGHTEFFECT_GRADIENT);
            G2.fill(STRIPELIGHTEFFECT);
        }

        final java.awt.geom.GeneralPath NUPSILIGHTREFLEX = new java.awt.geom.GeneralPath();
        NUPSILIGHTREFLEX.setWindingRule(java.awt.geom.GeneralPath.WIND_EVEN_ODD);
        NUPSILIGHTREFLEX.moveTo(IMAGE_WIDTH * 0.45348837209302323, IMAGE_HEIGHT * 0.0410958904109589);
        NUPSILIGHTREFLEX.curveTo(IMAGE_WIDTH * 0.46511627906976744, IMAGE_HEIGHT * 0.0273972602739726, IMAGE_WIDTH * 0.47674418604651164, IMAGE_HEIGHT * 0.02054794520547945, IMAGE_WIDTH * 0.4883720930232558, IMAGE_HEIGHT * 0.0273972602739726);
        NUPSILIGHTREFLEX.curveTo(IMAGE_WIDTH * 0.5, IMAGE_HEIGHT * 0.0273972602739726, IMAGE_WIDTH * 0.5116279069767442, IMAGE_HEIGHT * 0.03424657534246575, IMAGE_WIDTH * 0.5, IMAGE_HEIGHT * 0.04794520547945205);
        NUPSILIGHTREFLEX.curveTo(IMAGE_WIDTH * 0.4883720930232558, IMAGE_HEIGHT * 0.06164383561643835, IMAGE_WIDTH * 0.47674418604651164, IMAGE_HEIGHT * 0.0684931506849315, IMAGE_WIDTH * 0.46511627906976744, IMAGE_HEIGHT * 0.0684931506849315);
        NUPSILIGHTREFLEX.curveTo(IMAGE_WIDTH * 0.45348837209302323, IMAGE_HEIGHT * 0.06164383561643835, IMAGE_WIDTH * 0.4418604651162791, IMAGE_HEIGHT * 0.0547945205479452, IMAGE_WIDTH * 0.45348837209302323, IMAGE_HEIGHT * 0.0410958904109589);
        NUPSILIGHTREFLEX.closePath();
        final java.awt.geom.Point2D NUPSILIGHTREFLEX_START = new java.awt.geom.Point2D.Double( (0.4883720930232558 * IMAGE_WIDTH), (0.0273972602739726 * IMAGE_HEIGHT) );
        final java.awt.geom.Point2D NUPSILIGHTREFLEX_STOP = new java.awt.geom.Point2D.Double( ((0.4883720930232558 + -0.021559325188949815) * IMAGE_WIDTH), ((0.0273972602739726 + 0.039084514368293986) * IMAGE_HEIGHT) );
        final float[] NUPSILIGHTREFLEX_FRACTIONS =
        {
            0.0f,
            1.0f
        };
        final java.awt.Color[] NUPSILIGHTREFLEX_COLORS =
        {
            new java.awt.Color(255, 255, 255, 76),
            new java.awt.Color(255, 255, 255, 0)
        };
        if (NUPSILIGHTREFLEX_START.distance(NUPSILIGHTREFLEX_STOP) > 0)
        {
            final java.awt.LinearGradientPaint NUPSILIGHTREFLEX_GRADIENT = new java.awt.LinearGradientPaint(NUPSILIGHTREFLEX_START, NUPSILIGHTREFLEX_STOP, NUPSILIGHTREFLEX_FRACTIONS, NUPSILIGHTREFLEX_COLORS);
            G2.setPaint(NUPSILIGHTREFLEX_GRADIENT);
            G2.fill(NUPSILIGHTREFLEX);
        }

        final java.awt.geom.GeneralPath TOPLIGHTREFLEX = new java.awt.geom.GeneralPath();
        TOPLIGHTREFLEX.setWindingRule(java.awt.geom.GeneralPath.WIND_EVEN_ODD);
        TOPLIGHTREFLEX.moveTo(IMAGE_WIDTH * 0.19767441860465115, IMAGE_HEIGHT * 0.1506849315068493);
        TOPLIGHTREFLEX.curveTo(IMAGE_WIDTH * 0.19767441860465115, IMAGE_HEIGHT * 0.1095890410958904, IMAGE_WIDTH * 0.3372093023255814, IMAGE_HEIGHT * 0.07534246575342465, IMAGE_WIDTH * 0.5, IMAGE_HEIGHT * 0.07534246575342465);
        TOPLIGHTREFLEX.curveTo(IMAGE_WIDTH * 0.6627906976744186, IMAGE_HEIGHT * 0.07534246575342465, IMAGE_WIDTH * 0.8023255813953488, IMAGE_HEIGHT * 0.1095890410958904, IMAGE_WIDTH * 0.8023255813953488, IMAGE_HEIGHT * 0.1506849315068493);
        TOPLIGHTREFLEX.curveTo(IMAGE_WIDTH * 0.8023255813953488, IMAGE_HEIGHT * 0.18493150684931506, IMAGE_WIDTH * 0.6627906976744186, IMAGE_HEIGHT * 0.2191780821917808, IMAGE_WIDTH * 0.5, IMAGE_HEIGHT * 0.2191780821917808);
        TOPLIGHTREFLEX.curveTo(IMAGE_WIDTH * 0.3372093023255814, IMAGE_HEIGHT * 0.2191780821917808, IMAGE_WIDTH * 0.19767441860465115, IMAGE_HEIGHT * 0.18493150684931506, IMAGE_WIDTH * 0.19767441860465115, IMAGE_HEIGHT * 0.1506849315068493);
        TOPLIGHTREFLEX.closePath();
        final java.awt.geom.Point2D TOPLIGHTREFLEX_START = new java.awt.geom.Point2D.Double(0, TOPLIGHTREFLEX.getBounds2D().getMinY() );
        final java.awt.geom.Point2D TOPLIGHTREFLEX_STOP = new java.awt.geom.Point2D.Double(0, TOPLIGHTREFLEX.getBounds2D().getMaxY() );
        final float[] TOPLIGHTREFLEX_FRACTIONS =
        {
            0.0f,
            0.98f,
            1.0f
        };
        final java.awt.Color[] TOPLIGHTREFLEX_COLORS =
        {
            new java.awt.Color(255, 255, 255, 127),
            new java.awt.Color(255, 255, 255, 1),
            new java.awt.Color(255, 255, 255, 0)
        };
        if (TOPLIGHTREFLEX_START.distance(TOPLIGHTREFLEX_STOP) > 0)
        {
            final java.awt.LinearGradientPaint TOPLIGHTREFLEX_GRADIENT = new java.awt.LinearGradientPaint(TOPLIGHTREFLEX_START, TOPLIGHTREFLEX_STOP, TOPLIGHTREFLEX_FRACTIONS, TOPLIGHTREFLEX_COLORS);
            G2.setPaint(TOPLIGHTREFLEX_GRADIENT);
            G2.fill(TOPLIGHTREFLEX);
        }

        G2.dispose();

        return IMAGE;
    }

    private java.awt.LinearGradientPaint createGlowGradient(final int GLOW_WIDTH)
    {
        final int GLOW_HEIGHT = (int) (1.697674418604651 * GLOW_WIDTH);
        final java.awt.geom.Point2D GLOW_START = new java.awt.geom.Point2D.Float(0, GLOW_HEIGHT * 0.32191781f);
        final java.awt.geom.Point2D GLOW_STOP = new java.awt.geom.Point2D.Float(0, GLOW_HEIGHT * 0.9109589f);
        return new java.awt.LinearGradientPaint(GLOW_START, GLOW_STOP, GLOW_FRACTIONS, GLOW_COLORS);
    }

    private java.awt.geom.Rectangle2D createGlowBox(final int GLOW_WIDTH)
    {
        final int GLOW_HEIGHT = (int) (1.697674418604651 * GLOW_WIDTH);
        return new java.awt.geom.Rectangle2D.Float(GLOW_WIDTH * 0.15116279f, GLOW_HEIGHT * 0.32191781f, GLOW_WIDTH * 0.69767442f, GLOW_HEIGHT * 0.5890411f);
    }

    private java.awt.image.BufferedImage createHatchImage(final int WIDTH)
    {
        if (WIDTH <= 0)
        {
            return null;
        }

        final java.awt.GraphicsConfiguration GFX_CONF = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        final java.awt.image.BufferedImage IMAGE = GFX_CONF.createCompatibleImage(WIDTH, (int) (1.697674418604651 * WIDTH), java.awt.Transparency.TRANSLUCENT);
        final java.awt.Graphics2D G2 = IMAGE.createGraphics();

        G2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        G2.setRenderingHint(java.awt.RenderingHints.KEY_ALPHA_INTERPOLATION, java.awt.RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        G2.setRenderingHint(java.awt.RenderingHints.KEY_COLOR_RENDERING, java.awt.RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        G2.setRenderingHint(java.awt.RenderingHints.KEY_STROKE_CONTROL, java.awt.RenderingHints.VALUE_STROKE_PURE);

        final int IMAGE_WIDTH = IMAGE.getWidth();
        final int IMAGE_HEIGHT = IMAGE.getHeight();

        // Create front hatch
        G2.setColor(new java.awt.Color(0.0f, 0.0f, 0.0f, 0.2f));
        for (int x = (int) (IMAGE_WIDTH * 0.1627907) ; x <= (int)(IMAGE_WIDTH * 0.87209302) ; x += (int)(IMAGE_WIDTH * 0.05813953))
        {
            G2.draw(new java.awt.geom.Line2D.Float(x, (IMAGE_HEIGHT * 0.32191781f), x, (IMAGE_HEIGHT * 0.9109589f)));
        }
        for (int y = (int)(IMAGE_HEIGHT * 0.32191781) ; y <= (int)(IMAGE_HEIGHT * 0.91780822) ; y += (int)(IMAGE_HEIGHT * 0.03424658))
        {
            G2.draw(new java.awt.geom.Line2D.Float((IMAGE_WIDTH * 0.1627907f), y, (IMAGE_WIDTH * 0.86046512f), y));
        }

        // Create number contacts
        java.awt.LinearGradientPaint contactGradient;
        java.awt.geom.Point2D contactStart;
        java.awt.geom.Point2D contactStop;
        final float[] CONTACT_FRACTIONS =
        {
            0.0f,
            0.5f,
            1.0f
        };

        final java.awt.Color[] CONTACT_COLORS =
        {
            new java.awt.Color(0.0f, 0.0f, 0.0f, 0.3f),
            new java.awt.Color(1.0f, 1.0f, 1.0f, 0.3f),
            new java.awt.Color(0.0f, 0.0f, 0.0f, 0.3f)
        };

        for (int x = (int)(IMAGE_WIDTH * 0.20930233) ; x < (int)(IMAGE_WIDTH * 0.77906977) ; x += (int)(IMAGE_WIDTH * 0.06976744))
        {
            contactStart = new java.awt.geom.Point2D.Float(x, 0);
            contactStop = new java.awt.geom.Point2D.Float(x + (IMAGE_WIDTH * 0.03488372f), 0);
            contactGradient = new java.awt.LinearGradientPaint(contactStart, contactStop, CONTACT_FRACTIONS, CONTACT_COLORS);
            G2.setPaint(contactGradient);
            G2.fill(new java.awt.geom.Rectangle2D.Float(x, (IMAGE_HEIGHT * 0.90410959f), (IMAGE_WIDTH * 0.04651163f), (IMAGE_HEIGHT * 0.06849315f)));
        }

        G2.dispose();

        return IMAGE;
    }

    private java.awt.image.BufferedImage createNumber(int number, boolean active, final int WIDTH)
    {
        if (WIDTH <= 0)
        {
            return null;
        }

        final java.awt.GraphicsConfiguration GFX_CONF = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        final java.awt.image.BufferedImage IMAGE = GFX_CONF.createCompatibleImage(WIDTH, (int) (1.697674418604651 * WIDTH), java.awt.Transparency.TRANSLUCENT);
        final java.awt.Graphics2D G2 = IMAGE.createGraphics();

        G2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        G2.setRenderingHint(java.awt.RenderingHints.KEY_ALPHA_INTERPOLATION, java.awt.RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        G2.setRenderingHint(java.awt.RenderingHints.KEY_COLOR_RENDERING, java.awt.RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        G2.setRenderingHint(java.awt.RenderingHints.KEY_STROKE_CONTROL, java.awt.RenderingHints.VALUE_STROKE_PURE);

        final int IMAGE_WIDTH = IMAGE.getWidth();
        final int IMAGE_HEIGHT = IMAGE.getHeight();


        java.awt.geom.GeneralPath numberPath = new java.awt.geom.GeneralPath();

        switch(number)
        {
            case 0:
                numberPath.moveTo(IMAGE_WIDTH * 0.2441860465116279, IMAGE_HEIGHT * 0.5821917808219178);
                numberPath.curveTo(IMAGE_WIDTH * 0.2441860465116279, IMAGE_HEIGHT * 0.4383561643835616, IMAGE_WIDTH * 0.36046511627906974, IMAGE_HEIGHT * 0.3150684931506849, IMAGE_WIDTH * 0.5116279069767442, IMAGE_HEIGHT * 0.3150684931506849);
                numberPath.curveTo(IMAGE_WIDTH * 0.6627906976744186, IMAGE_HEIGHT * 0.3150684931506849, IMAGE_WIDTH * 0.7790697674418605, IMAGE_HEIGHT * 0.4383561643835616, IMAGE_WIDTH * 0.7790697674418605, IMAGE_HEIGHT * 0.5821917808219178);
                numberPath.curveTo(IMAGE_WIDTH * 0.7790697674418605, IMAGE_HEIGHT * 0.726027397260274, IMAGE_WIDTH * 0.6627906976744186, IMAGE_HEIGHT * 0.8493150684931506, IMAGE_WIDTH * 0.5116279069767442, IMAGE_HEIGHT * 0.8493150684931506);
                numberPath.curveTo(IMAGE_WIDTH * 0.36046511627906974, IMAGE_HEIGHT * 0.8493150684931506, IMAGE_WIDTH * 0.2441860465116279, IMAGE_HEIGHT * 0.726027397260274, IMAGE_WIDTH * 0.2441860465116279, IMAGE_HEIGHT * 0.5821917808219178);
                numberPath.closePath();
                break;
            case 1:
                numberPath.moveTo(IMAGE_WIDTH * 0.4883720930232558, IMAGE_HEIGHT * 0.3013698630136986);
                numberPath.curveTo(IMAGE_WIDTH * 0.4883720930232558, IMAGE_HEIGHT * 0.2876712328767123, IMAGE_WIDTH * 0.5, IMAGE_HEIGHT * 0.2808219178082192, IMAGE_WIDTH * 0.5116279069767442, IMAGE_HEIGHT * 0.2808219178082192);
                numberPath.curveTo(IMAGE_WIDTH * 0.5116279069767442, IMAGE_HEIGHT * 0.2808219178082192, IMAGE_WIDTH * 0.5116279069767442, IMAGE_HEIGHT * 0.2808219178082192, IMAGE_WIDTH * 0.5116279069767442, IMAGE_HEIGHT * 0.2808219178082192);
                numberPath.curveTo(IMAGE_WIDTH * 0.5116279069767442, IMAGE_HEIGHT * 0.2808219178082192, IMAGE_WIDTH * 0.5232558139534884, IMAGE_HEIGHT * 0.2876712328767123, IMAGE_WIDTH * 0.5232558139534884, IMAGE_HEIGHT * 0.3013698630136986);
                numberPath.curveTo(IMAGE_WIDTH * 0.5232558139534884, IMAGE_HEIGHT * 0.3013698630136986, IMAGE_WIDTH * 0.5232558139534884, IMAGE_HEIGHT * 0.8493150684931506, IMAGE_WIDTH * 0.5232558139534884, IMAGE_HEIGHT * 0.8493150684931506);
                numberPath.curveTo(IMAGE_WIDTH * 0.5232558139534884, IMAGE_HEIGHT * 0.863013698630137, IMAGE_WIDTH * 0.5116279069767442, IMAGE_HEIGHT * 0.8698630136986302, IMAGE_WIDTH * 0.5116279069767442, IMAGE_HEIGHT * 0.8698630136986302);
                numberPath.curveTo(IMAGE_WIDTH * 0.5116279069767442, IMAGE_HEIGHT * 0.8698630136986302, IMAGE_WIDTH * 0.5116279069767442, IMAGE_HEIGHT * 0.8698630136986302, IMAGE_WIDTH * 0.5116279069767442, IMAGE_HEIGHT * 0.8698630136986302);
                numberPath.curveTo(IMAGE_WIDTH * 0.5, IMAGE_HEIGHT * 0.8698630136986302, IMAGE_WIDTH * 0.4883720930232558, IMAGE_HEIGHT * 0.863013698630137, IMAGE_WIDTH * 0.4883720930232558, IMAGE_HEIGHT * 0.8493150684931506);
                numberPath.curveTo(IMAGE_WIDTH * 0.4883720930232558, IMAGE_HEIGHT * 0.8493150684931506, IMAGE_WIDTH * 0.4883720930232558, IMAGE_HEIGHT * 0.3013698630136986, IMAGE_WIDTH * 0.4883720930232558, IMAGE_HEIGHT * 0.3013698630136986);
                numberPath.closePath();
                break;
            case 2:
                numberPath.moveTo(IMAGE_WIDTH * 0.23255813953488372, IMAGE_HEIGHT * 0.4726027397260274);
                numberPath.lineTo(IMAGE_WIDTH * 0.23255813953488372, IMAGE_HEIGHT * 0.4246575342465753);
                numberPath.lineTo(IMAGE_WIDTH * 0.26744186046511625, IMAGE_HEIGHT * 0.3904109589041096);
                numberPath.lineTo(IMAGE_WIDTH * 0.3023255813953488, IMAGE_HEIGHT * 0.3698630136986301);
                numberPath.lineTo(IMAGE_WIDTH * 0.3488372093023256, IMAGE_HEIGHT * 0.3424657534246575);
                numberPath.lineTo(IMAGE_WIDTH * 0.4069767441860465, IMAGE_HEIGHT * 0.3287671232876712);
                numberPath.lineTo(IMAGE_WIDTH * 0.4883720930232558, IMAGE_HEIGHT * 0.3150684931506849);
                numberPath.lineTo(IMAGE_WIDTH * 0.5581395348837209, IMAGE_HEIGHT * 0.3219178082191781);
                numberPath.lineTo(IMAGE_WIDTH * 0.627906976744186, IMAGE_HEIGHT * 0.3287671232876712);
                numberPath.lineTo(IMAGE_WIDTH * 0.7093023255813954, IMAGE_HEIGHT * 0.3561643835616438);
                numberPath.lineTo(IMAGE_WIDTH * 0.7441860465116279, IMAGE_HEIGHT * 0.3972602739726027);
                numberPath.lineTo(IMAGE_WIDTH * 0.7674418604651163, IMAGE_HEIGHT * 0.4452054794520548);
                numberPath.lineTo(IMAGE_WIDTH * 0.7558139534883721, IMAGE_HEIGHT * 0.4863013698630137);
                numberPath.curveTo(IMAGE_WIDTH * 0.7558139534883721, IMAGE_HEIGHT * 0.4863013698630137, IMAGE_WIDTH * 0.7325581395348837, IMAGE_HEIGHT * 0.5342465753424658, IMAGE_WIDTH * 0.7325581395348837, IMAGE_HEIGHT * 0.5273972602739726);
                numberPath.curveTo(IMAGE_WIDTH * 0.7209302325581395, IMAGE_HEIGHT * 0.5273972602739726, IMAGE_WIDTH * 0.686046511627907, IMAGE_HEIGHT * 0.547945205479452, IMAGE_WIDTH * 0.686046511627907, IMAGE_HEIGHT * 0.547945205479452);
                numberPath.lineTo(IMAGE_WIDTH * 0.6162790697674418, IMAGE_HEIGHT * 0.5616438356164384);
                numberPath.lineTo(IMAGE_WIDTH * 0.5348837209302325, IMAGE_HEIGHT * 0.5821917808219178);
                numberPath.lineTo(IMAGE_WIDTH * 0.45348837209302323, IMAGE_HEIGHT * 0.6027397260273972);
                numberPath.lineTo(IMAGE_WIDTH * 0.36046511627906974, IMAGE_HEIGHT * 0.6438356164383562);
                numberPath.lineTo(IMAGE_WIDTH * 0.3023255813953488, IMAGE_HEIGHT * 0.6986301369863014);
                numberPath.lineTo(IMAGE_WIDTH * 0.2558139534883721, IMAGE_HEIGHT * 0.7397260273972602);
                numberPath.lineTo(IMAGE_WIDTH * 0.2441860465116279, IMAGE_HEIGHT * 0.7945205479452054);
                numberPath.lineTo(IMAGE_WIDTH * 0.23255813953488372, IMAGE_HEIGHT * 0.8424657534246576);
                numberPath.lineTo(IMAGE_WIDTH * 0.7906976744186046, IMAGE_HEIGHT * 0.8424657534246576);
                break;
            case 3:
                numberPath.moveTo(IMAGE_WIDTH * 0.23255813953488372, IMAGE_HEIGHT * 0.3150684931506849);
                numberPath.lineTo(IMAGE_WIDTH * 0.7558139534883721, IMAGE_HEIGHT * 0.3150684931506849);
                numberPath.lineTo(IMAGE_WIDTH * 0.7558139534883721, IMAGE_HEIGHT * 0.3287671232876712);
                numberPath.lineTo(IMAGE_WIDTH * 0.5232558139534884, IMAGE_HEIGHT * 0.5068493150684932);
                numberPath.lineTo(IMAGE_WIDTH * 0.5581395348837209, IMAGE_HEIGHT * 0.5205479452054794);
                numberPath.lineTo(IMAGE_WIDTH * 0.6046511627906976, IMAGE_HEIGHT * 0.5273972602739726);
                numberPath.lineTo(IMAGE_WIDTH * 0.6744186046511628, IMAGE_HEIGHT * 0.5547945205479452);
                numberPath.lineTo(IMAGE_WIDTH * 0.7209302325581395, IMAGE_HEIGHT * 0.5958904109589042);
                numberPath.lineTo(IMAGE_WIDTH * 0.7674418604651163, IMAGE_HEIGHT * 0.6438356164383562);
                numberPath.lineTo(IMAGE_WIDTH * 0.7674418604651163, IMAGE_HEIGHT * 0.6917808219178082);
                numberPath.lineTo(IMAGE_WIDTH * 0.7558139534883721, IMAGE_HEIGHT * 0.7397260273972602);
                numberPath.lineTo(IMAGE_WIDTH * 0.7325581395348837, IMAGE_HEIGHT * 0.7671232876712328);
                numberPath.curveTo(IMAGE_WIDTH * 0.7325581395348837, IMAGE_HEIGHT * 0.7671232876712328, IMAGE_WIDTH * 0.686046511627907, IMAGE_HEIGHT * 0.7945205479452054, IMAGE_WIDTH * 0.6976744186046512, IMAGE_HEIGHT * 0.7945205479452054);
                numberPath.curveTo(IMAGE_WIDTH * 0.6976744186046512, IMAGE_HEIGHT * 0.8013698630136986, IMAGE_WIDTH * 0.6395348837209303, IMAGE_HEIGHT * 0.821917808219178, IMAGE_WIDTH * 0.6395348837209303, IMAGE_HEIGHT * 0.821917808219178);
                numberPath.lineTo(IMAGE_WIDTH * 0.5697674418604651, IMAGE_HEIGHT * 0.8424657534246576);
                numberPath.lineTo(IMAGE_WIDTH * 0.5116279069767442, IMAGE_HEIGHT * 0.8493150684931506);
                numberPath.lineTo(IMAGE_WIDTH * 0.45348837209302323, IMAGE_HEIGHT * 0.8493150684931506);
                numberPath.lineTo(IMAGE_WIDTH * 0.3953488372093023, IMAGE_HEIGHT * 0.8424657534246576);
                numberPath.lineTo(IMAGE_WIDTH * 0.3372093023255814, IMAGE_HEIGHT * 0.821917808219178);
                numberPath.lineTo(IMAGE_WIDTH * 0.29069767441860467, IMAGE_HEIGHT * 0.8082191780821918);
                numberPath.lineTo(IMAGE_WIDTH * 0.23255813953488372, IMAGE_HEIGHT * 0.7671232876712328);
                break;
            case 4:
                numberPath.moveTo(IMAGE_WIDTH * 0.7790697674418605, IMAGE_HEIGHT * 0.684931506849315);
                numberPath.lineTo(IMAGE_WIDTH * 0.2441860465116279, IMAGE_HEIGHT * 0.684931506849315);
                numberPath.curveTo(IMAGE_WIDTH * 0.2441860465116279, IMAGE_HEIGHT * 0.684931506849315, IMAGE_WIDTH * 0.23255813953488372, IMAGE_HEIGHT * 0.6712328767123288, IMAGE_WIDTH * 0.22093023255813954, IMAGE_HEIGHT * 0.678082191780822);
                numberPath.curveTo(IMAGE_WIDTH * 0.22093023255813954, IMAGE_HEIGHT * 0.678082191780822, IMAGE_WIDTH * 0.20930232558139536, IMAGE_HEIGHT * 0.6643835616438356, IMAGE_WIDTH * 0.20930232558139536, IMAGE_HEIGHT * 0.6643835616438356);
                numberPath.lineTo(IMAGE_WIDTH * 0.6046511627906976, IMAGE_HEIGHT * 0.3013698630136986);
                numberPath.lineTo(IMAGE_WIDTH * 0.6046511627906976, IMAGE_HEIGHT * 0.8493150684931506);
                break;
            case 5:
                numberPath.moveTo(IMAGE_WIDTH * 0.7093023255813954, IMAGE_HEIGHT * 0.3219178082191781);
                numberPath.lineTo(IMAGE_WIDTH * 0.313953488372093, IMAGE_HEIGHT * 0.3219178082191781);
                numberPath.lineTo(IMAGE_WIDTH * 0.26744186046511625, IMAGE_HEIGHT * 0.5616438356164384);
                numberPath.lineTo(IMAGE_WIDTH * 0.29069767441860467, IMAGE_HEIGHT * 0.5616438356164384);
                numberPath.lineTo(IMAGE_WIDTH * 0.36046511627906974, IMAGE_HEIGHT * 0.5342465753424658);
                numberPath.lineTo(IMAGE_WIDTH * 0.4418604651162791, IMAGE_HEIGHT * 0.5205479452054794);
                numberPath.lineTo(IMAGE_WIDTH * 0.5465116279069767, IMAGE_HEIGHT * 0.5273972602739726);
                numberPath.lineTo(IMAGE_WIDTH * 0.6162790697674418, IMAGE_HEIGHT * 0.547945205479452);
                numberPath.lineTo(IMAGE_WIDTH * 0.6976744186046512, IMAGE_HEIGHT * 0.589041095890411);
                numberPath.lineTo(IMAGE_WIDTH * 0.7558139534883721, IMAGE_HEIGHT * 0.6506849315068494);
                numberPath.lineTo(IMAGE_WIDTH * 0.7674418604651163, IMAGE_HEIGHT * 0.6986301369863014);
                numberPath.lineTo(IMAGE_WIDTH * 0.7441860465116279, IMAGE_HEIGHT * 0.7465753424657534);
                numberPath.lineTo(IMAGE_WIDTH * 0.7093023255813954, IMAGE_HEIGHT * 0.7945205479452054);
                numberPath.lineTo(IMAGE_WIDTH * 0.6395348837209303, IMAGE_HEIGHT * 0.821917808219178);
                numberPath.lineTo(IMAGE_WIDTH * 0.5465116279069767, IMAGE_HEIGHT * 0.8424657534246576);
                numberPath.lineTo(IMAGE_WIDTH * 0.47674418604651164, IMAGE_HEIGHT * 0.8493150684931506);
                numberPath.lineTo(IMAGE_WIDTH * 0.4069767441860465, IMAGE_HEIGHT * 0.8424657534246576);
                numberPath.lineTo(IMAGE_WIDTH * 0.3372093023255814, IMAGE_HEIGHT * 0.821917808219178);
                numberPath.lineTo(IMAGE_WIDTH * 0.26744186046511625, IMAGE_HEIGHT * 0.7876712328767124);
                numberPath.lineTo(IMAGE_WIDTH * 0.23255813953488372, IMAGE_HEIGHT * 0.7534246575342466);
                break;
            case 6:
                numberPath.moveTo(IMAGE_WIDTH * 0.5232558139534884, IMAGE_HEIGHT * 0.2808219178082192);
                numberPath.lineTo(IMAGE_WIDTH * 0.2441860465116279, IMAGE_HEIGHT * 0.6095890410958904);
                numberPath.moveTo(IMAGE_WIDTH * 0.22093023255813954, IMAGE_HEIGHT * 0.678082191780822);
                numberPath.curveTo(IMAGE_WIDTH * 0.22093023255813954, IMAGE_HEIGHT * 0.5821917808219178, IMAGE_WIDTH * 0.3488372093023256, IMAGE_HEIGHT * 0.5136986301369864, IMAGE_WIDTH * 0.5116279069767442, IMAGE_HEIGHT * 0.5136986301369864);
                numberPath.curveTo(IMAGE_WIDTH * 0.6627906976744186, IMAGE_HEIGHT * 0.5136986301369864, IMAGE_WIDTH * 0.7906976744186046, IMAGE_HEIGHT * 0.5821917808219178, IMAGE_WIDTH * 0.7906976744186046, IMAGE_HEIGHT * 0.678082191780822);
                numberPath.curveTo(IMAGE_WIDTH * 0.7906976744186046, IMAGE_HEIGHT * 0.7671232876712328, IMAGE_WIDTH * 0.6627906976744186, IMAGE_HEIGHT * 0.8424657534246576, IMAGE_WIDTH * 0.5116279069767442, IMAGE_HEIGHT * 0.8424657534246576);
                numberPath.curveTo(IMAGE_WIDTH * 0.3488372093023256, IMAGE_HEIGHT * 0.8424657534246576, IMAGE_WIDTH * 0.22093023255813954, IMAGE_HEIGHT * 0.7671232876712328, IMAGE_WIDTH * 0.22093023255813954, IMAGE_HEIGHT * 0.678082191780822);
                numberPath.closePath();
                break;
            case 7:
                numberPath.moveTo(IMAGE_WIDTH * 0.22093023255813954, IMAGE_HEIGHT * 0.3013698630136986);
                numberPath.lineTo(IMAGE_WIDTH * 0.7906976744186046, IMAGE_HEIGHT * 0.2945205479452055);
                numberPath.lineTo(IMAGE_WIDTH * 0.4883720930232558, IMAGE_HEIGHT * 0.863013698630137);
                break;
            case 8:
                numberPath.setWindingRule(java.awt.geom.GeneralPath.WIND_EVEN_ODD);
                numberPath.moveTo(IMAGE_WIDTH * 0.20930232558139536, IMAGE_HEIGHT * 0.6986301369863014);
                numberPath.curveTo(IMAGE_WIDTH * 0.20930232558139536, IMAGE_HEIGHT * 0.6164383561643836, IMAGE_WIDTH * 0.3372093023255814, IMAGE_HEIGHT * 0.547945205479452, IMAGE_WIDTH * 0.5, IMAGE_HEIGHT * 0.547945205479452);
                numberPath.curveTo(IMAGE_WIDTH * 0.6627906976744186, IMAGE_HEIGHT * 0.547945205479452, IMAGE_WIDTH * 0.7906976744186046, IMAGE_HEIGHT * 0.6164383561643836, IMAGE_WIDTH * 0.7906976744186046, IMAGE_HEIGHT * 0.6986301369863014);
                numberPath.curveTo(IMAGE_WIDTH * 0.7906976744186046, IMAGE_HEIGHT * 0.7876712328767124, IMAGE_WIDTH * 0.6627906976744186, IMAGE_HEIGHT * 0.8561643835616438, IMAGE_WIDTH * 0.5, IMAGE_HEIGHT * 0.8561643835616438);
                numberPath.curveTo(IMAGE_WIDTH * 0.3372093023255814, IMAGE_HEIGHT * 0.8561643835616438, IMAGE_WIDTH * 0.20930232558139536, IMAGE_HEIGHT * 0.7876712328767124, IMAGE_WIDTH * 0.20930232558139536, IMAGE_HEIGHT * 0.6986301369863014);
                numberPath.closePath();
                numberPath.moveTo(IMAGE_WIDTH * 0.26744186046511625, IMAGE_HEIGHT * 0.4178082191780822);
                numberPath.curveTo(IMAGE_WIDTH * 0.26744186046511625, IMAGE_HEIGHT * 0.3493150684931507, IMAGE_WIDTH * 0.37209302325581395, IMAGE_HEIGHT * 0.2876712328767123, IMAGE_WIDTH * 0.5116279069767442, IMAGE_HEIGHT * 0.2876712328767123);
                numberPath.curveTo(IMAGE_WIDTH * 0.6511627906976745, IMAGE_HEIGHT * 0.2876712328767123, IMAGE_WIDTH * 0.7558139534883721, IMAGE_HEIGHT * 0.3493150684931507, IMAGE_WIDTH * 0.7558139534883721, IMAGE_HEIGHT * 0.4178082191780822);
                numberPath.curveTo(IMAGE_WIDTH * 0.7558139534883721, IMAGE_HEIGHT * 0.4863013698630137, IMAGE_WIDTH * 0.6511627906976745, IMAGE_HEIGHT * 0.547945205479452, IMAGE_WIDTH * 0.5116279069767442, IMAGE_HEIGHT * 0.547945205479452);
                numberPath.curveTo(IMAGE_WIDTH * 0.37209302325581395, IMAGE_HEIGHT * 0.547945205479452, IMAGE_WIDTH * 0.26744186046511625, IMAGE_HEIGHT * 0.4863013698630137, IMAGE_WIDTH * 0.26744186046511625, IMAGE_HEIGHT * 0.4178082191780822);
                numberPath.closePath();
                break;
            case 9:
                numberPath.moveTo(IMAGE_WIDTH * 0.5465116279069767, IMAGE_HEIGHT * 0.863013698630137);
                numberPath.lineTo(IMAGE_WIDTH * 0.7906976744186046, IMAGE_HEIGHT * 0.5);
                numberPath.moveTo(IMAGE_WIDTH * 0.23255813953488372, IMAGE_HEIGHT * 0.4589041095890411);
                numberPath.curveTo(IMAGE_WIDTH * 0.23255813953488372, IMAGE_HEIGHT * 0.3698630136986301, IMAGE_WIDTH * 0.36046511627906974, IMAGE_HEIGHT * 0.2945205479452055, IMAGE_WIDTH * 0.5116279069767442, IMAGE_HEIGHT * 0.2945205479452055);
                numberPath.curveTo(IMAGE_WIDTH * 0.6627906976744186, IMAGE_HEIGHT * 0.2945205479452055, IMAGE_WIDTH * 0.7906976744186046, IMAGE_HEIGHT * 0.3698630136986301, IMAGE_WIDTH * 0.7906976744186046, IMAGE_HEIGHT * 0.4589041095890411);
                numberPath.curveTo(IMAGE_WIDTH * 0.7906976744186046, IMAGE_HEIGHT * 0.5547945205479452, IMAGE_WIDTH * 0.6627906976744186, IMAGE_HEIGHT * 0.6301369863013698, IMAGE_WIDTH * 0.5116279069767442, IMAGE_HEIGHT * 0.6301369863013698);
                numberPath.curveTo(IMAGE_WIDTH * 0.36046511627906974, IMAGE_HEIGHT * 0.6301369863013698, IMAGE_WIDTH * 0.23255813953488372, IMAGE_HEIGHT * 0.5547945205479452, IMAGE_WIDTH * 0.23255813953488372, IMAGE_HEIGHT * 0.4589041095890411);
                numberPath.closePath();
                break;
        }

        // Translate 5,5 because of the linewidth of 12px for the glowing effect
        G2.translate(IMAGE_WIDTH * 0.05813953, IMAGE_WIDTH * 0.05813953);

        if (active)
        {
            // Draw active number with glow effect
            float width = IMAGE_WIDTH * 0.13953488f;
            final float STEP = IMAGE_WIDTH * 0.02325581f;

            for (int i = 5 ; i >= 0 ; i--)
            {
                G2.setStroke(new java.awt.BasicStroke(width, java.awt.BasicStroke.CAP_ROUND, java.awt.BasicStroke.JOIN_MITER));
                G2.setColor(COLOR_ARRAY[i]);
                width -= STEP;
                if (width < 0)
                {
                    width = 0;
                }
                G2.draw(numberPath);
            }
        }
        else
        {
            // Draw inactive number
            G2.setColor(new java.awt.Color(0.2f, 0.2f, 0.2f, 0.6f));
            G2.draw(numberPath);
        }

        G2.dispose();

        return IMAGE;
    }

    private void createAllNumbers()
    {
        INACTIVE_NUMBER_ARRAY.clear();
        INACTIVE_NUMBER_ARRAY.add(createNumber(0, false, getPreferredSize().width));
        INACTIVE_NUMBER_ARRAY.add(createNumber(1, false, getPreferredSize().width));
        INACTIVE_NUMBER_ARRAY.add(createNumber(2, false, getPreferredSize().width));
        INACTIVE_NUMBER_ARRAY.add(createNumber(3, false, getPreferredSize().width));
        INACTIVE_NUMBER_ARRAY.add(createNumber(4, false, getPreferredSize().width));
        INACTIVE_NUMBER_ARRAY.add(createNumber(5, false, getPreferredSize().width));
        INACTIVE_NUMBER_ARRAY.add(createNumber(6, false, getPreferredSize().width));
        INACTIVE_NUMBER_ARRAY.add(createNumber(7, false, getPreferredSize().width));
        INACTIVE_NUMBER_ARRAY.add(createNumber(8, false, getPreferredSize().width));
        INACTIVE_NUMBER_ARRAY.add(createNumber(9, false, getPreferredSize().width));

        ACTIVE_NUMBER_ARRAY.clear();
        ACTIVE_NUMBER_ARRAY.add(createNumber(0, true, getPreferredSize().width));
        ACTIVE_NUMBER_ARRAY.add(createNumber(1, true, getPreferredSize().width));
        ACTIVE_NUMBER_ARRAY.add(createNumber(2, true, getPreferredSize().width));
        ACTIVE_NUMBER_ARRAY.add(createNumber(3, true, getPreferredSize().width));
        ACTIVE_NUMBER_ARRAY.add(createNumber(4, true, getPreferredSize().width));
        ACTIVE_NUMBER_ARRAY.add(createNumber(5, true, getPreferredSize().width));
        ACTIVE_NUMBER_ARRAY.add(createNumber(6, true, getPreferredSize().width));
        ACTIVE_NUMBER_ARRAY.add(createNumber(7, true, getPreferredSize().width));
        ACTIVE_NUMBER_ARRAY.add(createNumber(8, true, getPreferredSize().width));
        ACTIVE_NUMBER_ARRAY.add(createNumber(9, true, getPreferredSize().width));

    }

    private void createNumberStack()
    {
        // Order of numbers in stack
        // 1 0 2 3 9 4 8 5 7 6

        if (this.number > -1)
        {
            // 1
            if(this.number == 1)
            {
                numberStack[0] = ACTIVE_NUMBER_ARRAY.get(1);
            }
            else
            {
                numberStack[0] = INACTIVE_NUMBER_ARRAY.get(1);
            }

            // 0
            if(this.number == 0)
            {
                numberStack[1] = ACTIVE_NUMBER_ARRAY.get(0);
            }
            else
            {
                numberStack[1] = INACTIVE_NUMBER_ARRAY.get(0);
            }

            // 2
            if(this.number == 2)
            {
                numberStack[2] = ACTIVE_NUMBER_ARRAY.get(2);
            }
            else
            {
                numberStack[2] = INACTIVE_NUMBER_ARRAY.get(2);
            }

            // 3
            if(this.number == 3)
            {
                numberStack[3] = ACTIVE_NUMBER_ARRAY.get(3);
            }
            else
            {
                numberStack[3] = INACTIVE_NUMBER_ARRAY.get(3);
            }

            // 9
            if(this.number == 9)
            {
                numberStack[4] = ACTIVE_NUMBER_ARRAY.get(9);
            }
            else
            {
                numberStack[4] = INACTIVE_NUMBER_ARRAY.get(9);
            }

            // 4
            if(this.number == 4)
            {
                numberStack[5] = ACTIVE_NUMBER_ARRAY.get(4);
            }
            else
            {
                numberStack[5] = INACTIVE_NUMBER_ARRAY.get(4);
            }

            // 8
            if(this.number == 8)
            {
                numberStack[6] = ACTIVE_NUMBER_ARRAY.get(8);
            }
            else
            {
                numberStack[6] = INACTIVE_NUMBER_ARRAY.get(8);
            }

            // 5
            if(this.number == 5)
            {
                numberStack[7] = ACTIVE_NUMBER_ARRAY.get(5);
            }
            else
            {
                numberStack[7] = INACTIVE_NUMBER_ARRAY.get(5);
            }

            // 7
            if(this.number == 7)
            {
                numberStack[8] = ACTIVE_NUMBER_ARRAY.get(7);
            }
            else
            {
                numberStack[8] = INACTIVE_NUMBER_ARRAY.get(7);
            }

            // 6
            if(this.number == 6)
            {
                numberStack[9] = ACTIVE_NUMBER_ARRAY.get(6);
            }
            else
            {
                numberStack[9] = INACTIVE_NUMBER_ARRAY.get(6);
            }
        }
        else
        {
            System.arraycopy(INACTIVE_NUMBER_ARRAY.toArray(), 0, numberStack, 0, 10);
        }
    }

    public int getNumber()
    {
        return this.number;
    }

    public void setNumber(int number)
    {
        if (number < 0)
        {
            number = -1;
        }
        if (number > 9)
        {
            number = 9;
        }
        this.number = number;
        createNumberStack();
        repaint();
    }

    // ComponentListener methods
    @Override
    public void componentResized(java.awt.event.ComponentEvent event)
    {
        setPreferredSize(event.getComponent().getSize());
        init();
    }

    @Override
    public void componentMoved(java.awt.event.ComponentEvent event)
    {

    }

    @Override
    public void componentShown(java.awt.event.ComponentEvent event)
    {

    }

    @Override
    public void componentHidden(java.awt.event.ComponentEvent event)
    {

    }

    @Override
    public void setSize(java.awt.Dimension dim)
    {
        super.setPreferredSize(new java.awt.Dimension(dim.width, (int)(dim.width * 1.69767442)));
        super.setSize(new java.awt.Dimension(dim.width, (int)(dim.width * 1.69767442)));        
    }

    @Override
    public void setSize(int width, int height)
    {
        super.setPreferredSize(new java.awt.Dimension(width, (int)(width * 1.69767442)));
        super.setSize(new java.awt.Dimension(width, (int)(width * 1.69767442)));        
    }

    @Override
    public String toString()
    {
        return "NixieNumber";
    }


}
