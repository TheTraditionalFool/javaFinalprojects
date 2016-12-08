import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Scanner;


public class MapView extends JFrame
{
    private Map map;

    public MapView(Map m) {
        map = m;
    }

    public void paint(Graphics g)
    {
        super.paint(g);

        int xOffset = 20;
        int yOffset = 40; //title bar
        int pixelWidth = this.getWidth()-2*xOffset;
        int pixelHeight = this.getHeight() - 2*yOffset;
        int width = map.getWidth();
        int height = map.getHeight();
        int xInt = (int)Math.round( (double)pixelWidth/width);
        int yInt = (int)Math.round( (double)pixelHeight/height);
        int interval = Math.min(xInt,yInt);

        g.setColor (Color.BLACK );
        g.fillRect(0,0,pixelWidth+100, pixelHeight+100);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                switch (map.getType(i,j)) {
                    case 1:
                        g.setColor( Color.BLUE );
                        break;
                    case 2:
                        g.setColor( Color.YELLOW );
                        break;
                    case 3:
                        g.setColor( new Color(120,255,120) ); // light green
                        break;
                    case 4:
                        g.setColor( Color.GREEN );
                        break;
                    case 5:
                        g.setColor( Color.GRAY );
                        break;
                    default:
                        g.setColor( Color.WHITE );
                }
                g.fillRect(i*interval +xOffset,j*interval +yOffset,interval,interval);
                if (map.isSpecial(i,j)) {
                    g.setColor( Color.BLACK );
                    g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, interval));
                    g.drawString( "\u2605",i*interval + xOffset,(j+1)*interval + yOffset);
                }
            }
        }
    }

    public static void main (String [] args)
    {
        int mapWidth;
        int mapHeight;
        do {
            mapWidth = getInt("Enter the map width in squares:");
            if (mapWidth < 1) {
                System.out.println("Please enter a value greater than zero.");
            }
        } while (mapWidth < 1);
        do {
            mapHeight = getInt("Enter the map height in squares:");
            if (mapHeight < 1) {
                System.out.println("Please enter a value greater than zero.");
            }
        } while (mapHeight < 1);
        Map map = new Map(mapWidth,mapHeight);
        map.create();
        MapView viewer = new MapView(map); // make graphical object
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize(); //calculate screen size
        double calcWidth = screen.getWidth()*0.75;
        double calcHeight = screen.getHeight()*0.75;
        double screenRatio = calcWidth/calcHeight;
        if (mapWidth < screenRatio*mapHeight) {
            calcWidth = mapWidth*calcHeight/mapHeight;
        }
        else {
            calcHeight = mapHeight*calcWidth/mapWidth;
        }
        viewer.setSize((int)calcWidth,(int)calcHeight); // set size of frame
        viewer.setVisible(true); // make frame visible (calls paint method)
    }

    public static int getInt(String prompt) {
        Scanner scan = new Scanner(System.in);
        System.out.println(prompt);
        while (!scan.hasNextInt()) {
            System.out.println(scan.nextLine() + " is not an integer.\n" + prompt);
        }
        return scan.nextInt();
    }
}