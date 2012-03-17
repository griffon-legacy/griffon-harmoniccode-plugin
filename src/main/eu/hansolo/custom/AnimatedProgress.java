package eu.hansolo.custom;



/**
 *
 * @author hansolo
 */
public class AnimatedProgress extends javax.swing.JComponent implements java.awt.event.ActionListener
{    
    // <editor-fold defaultstate="collapsed" desc="Variable declaration">
    private int barWidth;
    private int barHeight;
    private int cornerRadius;
    private boolean roundedCorners;
    private double maxValue;
    private double value;
    private double percentage;
    private boolean valueVisible;
    private boolean percentageVisible;
    private boolean tilted;
    private boolean infinite;
    private String infiniteText;
    private boolean infiniteTextVisible;
    private java.awt.Color infiniteTextColor;
    private double factor;
    private float posX;
    private float posY;
    private float PERIOD;
    private float cyclePosX;
    private java.awt.Color color;
    private java.awt.Color cycleFirstColor;
    private java.awt.Color cycleSecondColor;
    private java.awt.Color[] cycleColors;
    private java.awt.Shape roundRectVariableBar;
    private java.awt.Shape roundRectFixedBar;
    private java.awt.geom.Area variableBar;
    private java.awt.geom.Area fixedBar;
    private java.awt.geom.Line2D upperDarkLine;    
    private java.awt.Color OUTER_SHADOW_1_COLOR;    
    private java.awt.Color FRAME_COLOR;
    private float[] DIST_TOP_LIGHTS;
    private java.awt.Color[] COLORS_TOP_LIGHTS;
    private java.awt.LinearGradientPaint topLightGradient;
    private float[] DIST_BOTTOM_LIGHTS;
    private java.awt.Color[] COLORS_BOTTOM_LIGHTS;
    private java.awt.LinearGradientPaint bottomLightGradient;
    private float[] DIST_CENTER_SHADOW;
    private java.awt.Color[] COLORS_CENTER_SHADOW;
    private java.awt.LinearGradientPaint centerShadowGradient;
    private java.awt.LinearGradientPaint cyclicJava6;
    private float[] DIST_STANDARD;
    private float[] DIST_TILTED;
    private float[] DIST_FINISHED;
    private java.awt.geom.Point2D startPoint;
    private java.awt.geom.Point2D endPoint;
    private java.awt.geom.Area topLightArea;
    private java.awt.geom.Area bottomLightArea;
    private java.awt.Shape centerShadowRoundedRect;
    private java.awt.Font STD_FONT;
    private java.awt.Color foregroundColor;
    private java.awt.geom.Rectangle2D textBoundary;
    private java.awt.FontMetrics metrics;    
    private final javax.swing.Timer CYCLE_TIMER;
    private int x = 0;
    private double cycleLimit;
    private boolean fadeOut = true;
    private int alpha = 255;    
    private transient final java.awt.event.ComponentAdapter COMPONENT_LISTENER;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructor">
    /** Creates new form AnimatedProgressBar */
    public AnimatedProgress()
    {
        COMPONENT_LISTENER = new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent event)
            {        
                barWidth = getSize().width - 2;
                barHeight = getSize().height - 6;
                setBarWidth(getSize().width);
                recalc();

                prepareForms();
            }           
        };
        addComponentListener(COMPONENT_LISTENER);        
        CYCLE_TIMER = new javax.swing.Timer(40, this);
        initComponents();
        prepareForms();
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Initialization">
    private void initComponents()
    {        
        barWidth = 100;
        barHeight = 16;
        cornerRadius = 0;
        roundedCorners = false;

        maxValue = 100;
        value = 0;
        percentage = 0;
        valueVisible = false;
        percentageVisible = false;
        tilted = false;
        
        infinite = false;
        infiniteText = "";
        infiniteTextVisible = false;
        infiniteTextColor = new java.awt.Color(100,100,100,255);

        factor = 1;

        posX = 2;
        posY = 2;
        PERIOD = 10;

        cyclePosX = posX;
        color = new java.awt.Color(88,154,227,225);
        cycleFirstColor = new java.awt.Color(88,154,227,255);
        cycleSecondColor = new java.awt.Color(50,130,222,255);

        cycleColors = new java.awt.Color[] {cycleFirstColor, cycleFirstColor, cycleSecondColor, cycleSecondColor};

        OUTER_SHADOW_1_COLOR = new java.awt.Color(0,0,0,50);

        FRAME_COLOR = java.awt.Color.GRAY;

        DIST_TOP_LIGHTS = new float[]{
            0.0f,
            0.01f,
            0.45f,
            0.55f,
            0.95f,
            1.0f
        };

        COLORS_TOP_LIGHTS = new java.awt.Color[]{
            new java.awt.Color(200,200,200,50),
            new java.awt.Color(255,255,255,50),
            new java.awt.Color(255,255,255,100),
            new java.awt.Color(255,255,255,100),
            new java.awt.Color(255,255,255,50),
            new java.awt.Color(255,255,255,0)
        };

        DIST_BOTTOM_LIGHTS = new float[]{
            0.0f,
            0.5f,
            0.99f,
            1.0f
        };

        COLORS_BOTTOM_LIGHTS = new java.awt.Color[]{
            new java.awt.Color(255,255,255,0),
            new java.awt.Color(255,255,255,75),
            new java.awt.Color(255,255,255,100),
            new java.awt.Color(255,255,255,150)
        };

        DIST_CENTER_SHADOW = new float[]{
            0.0f,
            0.3f,
            0.5f,
            1.0f
        };

        COLORS_CENTER_SHADOW = new java.awt.Color[]{
            new java.awt.Color(50,50,50,0),
            new java.awt.Color(70,70,70,50),
            new java.awt.Color(70,70,70,30),
            new java.awt.Color(50,50,50,0)
        };

        DIST_STANDARD = new float[]{
            0.0f,
            0.01f,
            0.99f,
            1.0f
        };

        DIST_TILTED = new float[]{
            0.0f,
            0.4f,
            0.5f,
            1.0f
        };

        DIST_FINISHED = new float[]{
            0.0f,
            0.1f,
            0.9f,
            1.0f
        };

        STD_FONT = new java.awt.Font(java.awt.Font.SANS_SERIF, 0, (int) (barHeight - barHeight * 0.3));
        foregroundColor = java.awt.Color.DARK_GRAY;

        if (getGrayScaleIntensity(this.cycleFirstColor) > 128)
        {
            foregroundColor = java.awt.Color.DARK_GRAY;
        }
        else
        {
            foregroundColor = java.awt.Color.WHITE;
        }
        
        startPoint = new java.awt.geom.Point2D.Float(0, 0);
        endPoint = new java.awt.geom.Point2D.Float(0, 0);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Visualization">
    @Override
    protected void paintComponent(java.awt.Graphics g)
    {        
        final java.awt.Graphics2D G2 = (java.awt.Graphics2D) g.create();

        G2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        // Paint cyclic gradient bar
        if (CYCLE_TIMER.isRunning())
        {
            if (tilted)
            {
                startPoint.setLocation(cyclePosX, posY); 
                endPoint.setLocation((cyclePosX + PERIOD * 1.4142135624), posY + PERIOD / 2 / 1.4142135624);
                cyclicJava6 = new java.awt.LinearGradientPaint(startPoint, endPoint, DIST_TILTED, cycleColors, java.awt.MultipleGradientPaint.CycleMethod.REFLECT);
            }
            else
            {
                startPoint.setLocation(cyclePosX, posY);
                endPoint.setLocation(cyclePosX + PERIOD, posY);
                cyclicJava6 = new java.awt.LinearGradientPaint(startPoint, endPoint, DIST_STANDARD, cycleColors, java.awt.MultipleGradientPaint.CycleMethod.REFLECT);
            }
        }
        else
        {
            startPoint.setLocation(posX, posY);
            endPoint.setLocation(posX, posY + barHeight);
            cyclicJava6 = new java.awt.LinearGradientPaint(startPoint, endPoint, DIST_FINISHED, cycleColors, java.awt.MultipleGradientPaint.CycleMethod.REFLECT);
        }
        G2.setPaint(cyclicJava6);

        roundRectVariableBar = new java.awt.geom.RoundRectangle2D.Double(posX, posY, (value / maxValue * barWidth), barHeight, cornerRadius, cornerRadius);                
        variableBar = new java.awt.geom.Area(roundRectVariableBar);
        fixedBar = new java.awt.geom.Area(roundRectFixedBar);
        fixedBar.intersect(variableBar);
        G2.fill(fixedBar);

        // Draw inner shadow
        G2.setColor(OUTER_SHADOW_1_COLOR);
        G2.draw(fixedBar);

        // Paint center shadow
        G2.setPaint(centerShadowGradient);
        G2.fill(centerShadowRoundedRect);

        // Paint topLight effect
        G2.setPaint(topLightGradient);
        G2.fill(topLightArea);

        // Paint bottomLight effect
        G2.setPaint(bottomLightGradient);
        G2.fill(bottomLightArea);

        // Draw frame
        G2.setColor(FRAME_COLOR);
        G2.draw(roundRectFixedBar);

        G2.setColor(OUTER_SHADOW_1_COLOR);
        G2.draw(upperDarkLine);
        
        // Show value as number
        if (valueVisible)
        {
            G2.setColor(foregroundColor);
            G2.setFont(STD_FONT);
            
            metrics = G2.getFontMetrics();
            textBoundary = metrics.getStringBounds(Integer.toString((int)(value)), G2);
            
            G2.drawString(Integer.toString((int)(value)), (int) (posX + (barWidth - textBoundary.getWidth()) / 2), (int) (posY + (barHeight - textBoundary.getHeight()) / 2 + textBoundary.getHeight()));
        }

        // Show value as percentage
        if (percentageVisible)
        {
            G2.setColor(foregroundColor);
            G2.setFont(STD_FONT);

            metrics = G2.getFontMetrics();
            textBoundary = metrics.getStringBounds(Integer.toString((int) (percentage * 100)) + " %", G2);
                        
            G2.drawString(Integer.toString((int) (percentage * 100)) + " %", (int)(posX + (barWidth - textBoundary.getWidth()) / 2), (int)(posY + (barHeight - textBoundary.getHeight()) / 2 + textBoundary.getHeight()));
        }

        // Show infinite text
        if (infinite && infiniteTextVisible)
        {
            G2.setColor(infiniteTextColor);
            G2.setFont(STD_FONT);

            metrics = G2.getFontMetrics();
            textBoundary = metrics.getStringBounds(infiniteText, G2);

            G2.drawString(infiniteText, (int)(posX + (barWidth - textBoundary.getWidth()) / 2), (int)(posY + (barHeight - textBoundary.getHeight()) / 2 + textBoundary.getHeight()));
        }

        G2.dispose();               
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    /**
     * Set the current value of the bar
     * @param VALUE
     */
    public void setValue(final double VALUE)
    {
        if (!infinite)
        {
            value = VALUE >= maxValue ? (maxValue) : (VALUE < 0 ? 0 : (VALUE));     
            percentage = ((value / maxValue));            
            // Start animation thread if not running
            if (!CYCLE_TIMER.isRunning())
            {
                CYCLE_TIMER.start();            
            }
            else
            {
                if (Double.compare(value, maxValue) >= 0)
                {
                    CYCLE_TIMER.stop();
                }
            }            
        }
    }

    /**
     * Get the current value of the bar
     * @return
     */
    public double getValue()
    {
        return value;
    }
    
    /**
     * Set the maximum value for the bar
     * @param MAX_VALUE 
     */
    public void setMaxValue(final double MAX_VALUE)
    {
        maxValue = MAX_VALUE <= 0 ? 1 : MAX_VALUE;
        factor = calcFactor();
    }

    public double getMaxValue()
    {
        return this.maxValue;
    }

    /**
     * Reset the bar so that you can
     * restart it with different parameters again.
     * Also recreate the forms and the animation thread.
     */
    public void reset()
    {
        setValue(0);
        CYCLE_TIMER.stop();        
        prepareForms();          
    }

    /**
     * Enable or disable a tilted gradient
     * @param tilted
     */
    public void setTilted(boolean tilted)
    {
        this.tilted = tilted;
    }

    public boolean isTilted()
    {
        return this.tilted;
    }

    /**
     * Enable or disable the infinite progress
     * which means it is tilted, there is no
     * value and it is only a 100%
     * @param INFINITE 
     */
    public void setInfinite(final boolean INFINITE)
    {
        if(INFINITE)
        {            
            setValueVisible(false);
            setPercentageVisible(false);
            setMaxValue(100);
            setValue(100);
            CYCLE_TIMER.start();
        }
        else
        {
            CYCLE_TIMER.stop();
            reset();            
        }
        infinite = INFINITE;        
    }

    public boolean isInfinite()
    {
        return this.infinite;
    }

    public String getInfiniteText()
    {
        return infiniteText;
    }

    public void setInfiniteText(final String INFINITE_TEXT)
    {
        infiniteText = INFINITE_TEXT;
    }

    public void setInfiniteTextVisible(final boolean INFINITE_TEXT_VISIBLE)
    {
        infiniteTextVisible = INFINITE_TEXT_VISIBLE;
    }

    public boolean isInfiniteTextVisible()
    {
        return infiniteTextVisible;
    }
    
    /**
     * Enable or disable the visibility of
     * the value as a number in the center of
     * the bar.
     * If enabled the showPercent variable will be
     * disabled.
     * @param VALUE_VISIBLE
     */
    public void setValueVisible(final boolean VALUE_VISIBLE)
    {
        percentageVisible = false;
        valueVisible = VALUE_VISIBLE;
    }

    public boolean isValueVisible()
    {
        return valueVisible;
    }

    /**
     * Enable or disable the visibility of
     * the value as a percentage value in the center of
     * the bar.
     * If enabled the showValue variable will be
     * disabled.
     * @param PERCENTAGE_VISIBLE
     */
    public void setPercentageVisible(final boolean PERCENTAGE_VISIBLE)
    {
        valueVisible = false;
        percentageVisible = PERCENTAGE_VISIBLE;
    }

    public boolean isPercentageVisible()
    {
        return percentageVisible;
    }

    /**
     * Set the current value of the bar
     * as a percentage value (0 - 100).
     * @param PERCENTAGE
     */
    public void setPercentage(final double PERCENTAGE)
    {    
        value = PERCENTAGE >= 1.0 ? (maxValue / 100 * factor) : (PERCENTAGE < 0 ? 0 : (PERCENTAGE * maxValue * factor));        
        percentage = ((value * factor / maxValue));
    }

    public double getPercentage()
    {
        return percentage;
    }
    
    /**
     * Set the radius of the rounded rectangle
     * which is used for the bar
     * @param CORNER_RADIUS
     */
    public void setCornerRadius(final int CORNER_RADIUS)
    {
        cornerRadius = CORNER_RADIUS;
        if (cornerRadius != this.barHeight)
        {
            roundedCorners = false;
        }
        prepareForms();
    }

    /**
     * Get the radius of the rounded rectangle
     * which is used for the bar.
     * @return
     */
    public int getCornerRadius()
    {
        return cornerRadius;
    }

    public void setRoundedCorners(final boolean ROUNDED_CORNERS)
    {
        roundedCorners = ROUNDED_CORNERS;
        if (ROUNDED_CORNERS)
        {
            setCornerRadius(this.barHeight);
        }
        else
        {
            setCornerRadius(0);
        }
    }

    public boolean isRoundedCorners()
    {
        return roundedCorners;
    }

    /**
     * Set the first color of the gradient.
     * @param CYCLE_FIRST_COLOR
     */
    public void setCycleFirstColor(final java.awt.Color CYCLE_FIRST_COLOR)
    {        
        cycleColors[0] = CYCLE_FIRST_COLOR;
        cycleColors[1] = CYCLE_FIRST_COLOR;
    }

    public java.awt.Color getCycleFirstColor()
    {
        return cycleFirstColor;
    }

    /**
     * Set the second color of the gradient.
     * @param CYCLE_SECOND_COLOR
     */
    public void setCycleSecondColor(final java.awt.Color CYCLE_SECOND_COLOR)
    {
        cycleColors[2] = CYCLE_SECOND_COLOR;
        cycleColors[3] = CYCLE_SECOND_COLOR;
    }

    public java.awt.Color getCycleSecondColor()
    {
        return cycleSecondColor;
    }

    /**
     * Set the color of the gradient by using the given
     * color as cycleSecondColor and a darkened version
     * of the given color as cycleFirstColor.
     * @param COLOR
     */
    public void setColor(final java.awt.Color COLOR)
    {        
        color = COLOR;
        cycleColors[0] = COLOR.darker();
        cycleColors[1] = COLOR.darker();
        cycleColors[2] = COLOR;
        cycleColors[3] = COLOR;
        repaint();
    }

    public java.awt.Color getColor()
    {
        return color;
    }

    /**
     * Set the color of the text to the given value
     * @param FOREGROUND_COLOR
     */
    public void setForegroundColor(final java.awt.Color FOREGROUND_COLOR)
    {
        foregroundColor = FOREGROUND_COLOR;
        repaint();
    }

    public java.awt.Color getForegroundColor()
    {
        return foregroundColor;
    }

    public void setInfiniteTextColor(final java.awt.Color INFINITE_TEXT_COLOR)
    {
        infiniteTextColor = INFINITE_TEXT_COLOR;        
        repaint();
    }
    
    public java.awt.Color getInfiniteTextColor()
    {
        return infiniteTextColor;
    }
    
    @Override
    public java.awt.Dimension getMinimumSize()
    {
        return new java.awt.Dimension(102, 22);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Internal">
        /**
     * Set the width of the bar in pixels
     * @param BAR_WIDTH
     */
    private void setBarWidth(final int BAR_WIDTH)
    {        
        barWidth = BAR_WIDTH;
        setPreferredSize(new java.awt.Dimension(BAR_WIDTH + 2, barHeight + 4));

        prepareForms();
    }

    /**
     * Prepare all forms for the given width
     */
    private void prepareForms()
    {
        int border = 4;

        // Middle shadow effect
        centerShadowGradient = new java.awt.LinearGradientPaint(new java.awt.geom.Point2D.Float(posX, posY + (int)(barHeight * 0.2)), new java.awt.geom.Point2D.Float(posX, posY + (int)(barHeight * 0.8)), DIST_CENTER_SHADOW, COLORS_CENTER_SHADOW);
        centerShadowRoundedRect = new java.awt.geom.RoundRectangle2D.Double(posX + 1, (int)(posY + barHeight * 0.2), barWidth - border - 2, barHeight * 0.5, cornerRadius, cornerRadius);

        // TopLight effect
        topLightGradient = new java.awt.LinearGradientPaint(new java.awt.geom.Point2D.Float(posX, posY + 1), new java.awt.geom.Point2D.Float(posX, posY + (int) (barHeight / 2.2f)), DIST_TOP_LIGHTS, COLORS_TOP_LIGHTS);
        java.awt.Shape s1 = new java.awt.geom.RoundRectangle2D.Double(posX, posY + 1, barWidth - border, barHeight, cornerRadius, cornerRadius);
        java.awt.Shape s2 = new java.awt.geom.Rectangle2D.Double(posX, posY + barHeight / 2.0 + 1, barWidth - border, barHeight / 2.0);
        topLightArea = new java.awt.geom.Area(s1);
        java.awt.geom.Area area2 = new java.awt.geom.Area(s2);
        topLightArea.subtract(area2);

        // BottomLight effect
        bottomLightGradient = new java.awt.LinearGradientPaint(new java.awt.geom.Point2D.Float(posX, posY + (int)(barHeight * 0.7)), new java.awt.geom.Point2D.Float(posX, (posY + barHeight)), DIST_BOTTOM_LIGHTS, COLORS_BOTTOM_LIGHTS);
        java.awt.Shape s3 = new java.awt.geom.RoundRectangle2D.Double(posX, posY, barWidth - border, barHeight, cornerRadius, cornerRadius);
        java.awt.Shape s4 = new java.awt.geom.Rectangle2D.Double(posX, posY, barWidth - border, barHeight / 2.0);
        bottomLightArea = new java.awt.geom.Area(s3);
        java.awt.geom.Area area3 = new java.awt.geom.Area(s4);
        bottomLightArea.subtract(area3);

        // Bar
        roundRectVariableBar = new java.awt.geom.RoundRectangle2D.Double(posX, posY, (value / maxValue * barWidth), barHeight, cornerRadius, cornerRadius);
        roundRectFixedBar = new java.awt.geom.RoundRectangle2D.Double(posX, posY, barWidth - border, barHeight, cornerRadius, cornerRadius);
        variableBar = new java.awt.geom.Area(roundRectVariableBar);
        fixedBar = new java.awt.geom.Area(roundRectFixedBar);

        // Upper line
        upperDarkLine = new java.awt.geom.Line2D.Double(posX + cornerRadius / 2.0, posY, posX + barWidth - cornerRadius / 2.0 - border, posY );

        // Recalculate factor between width and maxValue
        factor = (double) barWidth / maxValue;

        repaint();
    }

    private double calcFactor()
    {
        return ((double) barWidth / maxValue);
    }
    
    private void recalc()
    {
        double unscaledValue = value / factor;
        factor = calcFactor();
        setValue(unscaledValue);
    }
    
        /**
     * Redefine the start position of the
     * gradient paint and repaint the bar
     * in dependence of showValue and showPercent variable.
     * If no text is shown in the bar we only need to
     * repaint the bar itself without the frame.
     * @param CYCLE_POS_X
     */
    private void setCyclicStart(final float CYCLE_POS_X)
    {
        cyclePosX = CYCLE_POS_X;
        repaint();
    }

    private int getGrayScaleIntensity(final java.awt.Color COLOR)
    {
        int red = COLOR.getRed();
        int green = COLOR.getGreen();
        int blue = COLOR.getBlue();
        int grayScaleIntensity = (int) (0.299 * red + 0.587 * green + 0.114 * blue + .5);

        return grayScaleIntensity;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Action listener method">
    @Override
    public void actionPerformed(final java.awt.event.ActionEvent EVENT)
    {
        if (tilted)                    
        {
            cycleLimit = PERIOD * 2.8284271247; // 2 * Math.sqrt(2)
        }
        else
        {
            cycleLimit = PERIOD * 2;
        }

        setCyclicStart(PERIOD - x);
        x++;
        if (x >= cycleLimit)
        {
            x = 0;
        }
        if (infinite && infiniteTextVisible)
        {
            if(fadeOut)
            {
                alpha -= 4;
            }
            else
            {
                alpha += 4;
            }
            if (alpha < 0)
            {
                fadeOut = false;
                alpha = 0;
            }
            if (alpha >= 255)
            {
                fadeOut = true;
                alpha = 255;
            }
            infiniteTextColor = new java.awt.Color(infiniteTextColor.getRed(),infiniteTextColor.getGreen(),infiniteTextColor.getBlue(),alpha);
        }                                    
        repaint();
    }
    // </editor-fold>
    
    @Override
    public String toString()
    {
        return "ProgressBar: " + value;
    }
}
