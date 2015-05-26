import java.awt. Image;
import java.awt. Graphics;
import java.awt.Color;
import java.awt.event. ActionListener;
import java.awt.event. ActionEvent;
import java.net. URL;
import javax.swing. ImageIcon;
import javax.swing. JApplet;
import javax.swing. JComponent;
import javax.swing. JFrame;
import javax.swing. JScrollPane;
import javax.swing. JTextArea;
import javax.swing. JTextField;
import java.util. Date;

/**
 * Class zuulApplet - write a description of the class here
 * 
 * @author (your name) 
 * @version (a version number)
 */
public class LyleApplet 
extends JApplet
implements ActionListener
{
    private JTextArea output;
    private JTextField input;
    private JScrollPane scrollPane;
    Game game;
    String history;
    Image image;

     /**
     * Called by the browser or applet viewer to inform this JApplet that it
     * has been loaded into the system. It is always called before the first 
     * time that the start method is called.
     */
    public void init()
    {
        output = new JTextArea(1, 1);
//         output = new JTextArea("", 1, 1,
//             JTextArea.SCROLLBARS_VERTICAL_ONLY);
        output.setEditable(false);
        
        scrollPane = new JScrollPane(output);
        scrollPane.setHorizontalScrollBarPolicy(
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);
        
        input = new JTextField();
        input.addActionListener(this);
        add(input);
    }

    /**
     * Called by the browser or applet viewer to inform this JApplet that it 
     * should start its execution. It is called after the init method and 
     * each time the JApplet is revisited in a Web page. 
     */
    public void start()
    {
        setLayout(null);
        setBackground(Color.WHITE);
        scrollPane.setBounds(10, getHeight()/2, getWidth()-20, getHeight()/2-40);
        input.setBounds(10, getHeight()-30, getWidth()-20, 20);
        game = new Game();
        history = game.readMessages();
        output.setText(history);
        input.requestFocusInWindow();
        
//        String filename = game.getImage();
        Class<? extends JApplet> appClass = getClass();
//        URL url = appClass.getResource(filename);
//        ImageIcon icon = new ImageIcon(url);
//        image = icon.getImage();  
        repaint();
    }
    
    /** 
     * Called by the browser or applet viewer to inform this JApplet that
     * it should stop its execution. It is called when the Web page that
     * contains this JApplet has been replaced by another page, and also
     * just before the JApplet is to be destroyed. 
     */
    public void stop()
    {
        // provide any code that needs to be run when page
        // is replaced by another page or before JApplet is destroyed 
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == input)
        {
            String inputLine = input.getText();
            input.setText("");
            
            history += "> " + inputLine + "\n";
            game.processInput(inputLine);
            history += game.readMessages();
            int window = Math.max(history.length() - 5000, 0);
            int length = Math.min(history.length(), 5000);
            output.setText(history.substring(window, length));
            output.setCaretPosition(length);
        
//            String filename = game.getImage();
            Class<? extends JApplet> appClass = getClass();
//            URL url = appClass.getResource(filename);
//            ImageIcon icon = new ImageIcon(url);
//            image = icon.getImage();  
            repaint();
        }
    }

    /**
     * Paint method for applet.
     * 
     * @param  g   the Graphics object for this applet
     */
    public void paint(Graphics g)
    {
        g.fillRect(0, 0, getWidth(), getHeight());
        output.repaint();
        input.repaint();
        
        // image original height and width
        int ow = image.getWidth(null);
        int oh = image.getHeight(null);
        
        // fit the image to the window
        int fw = Math.min(ow, getWidth() - 50);
        int fh = Math.min(oh, getHeight()/2 - 50);
        
        // scale the image to correct proportion
        int w =  Math.min(fw, ow * fh / oh);
        int h =  Math.min(fh, oh * fw / ow);
        
        // center the image in at the top of the screen
        int x = (getWidth() - w) / 2;
        int y = (getHeight()/2 - h) / 2;
        
        g.drawImage(image, x, y, w, h, this);
    }

    /**
     * Called by the browser or applet viewer to inform this JApplet that it
     * is being reclaimed and that it should destroy any resources that it
     * has allocated. The stop method will always be called before destroy. 
     */
    public void destroy()
    {
        // provide code to be run when JApplet is about to be destroyed.
    }


    /**
     * Returns information about this applet. 
     * An applet should override this method to return a String containing 
     * information about the author, version, and copyright of the JApplet.
     *
     * @return a String representation of information about this JApplet
     */
    public String getAppletInfo()
    {
        // provide information about the applet
        return "Title:   \nAuthor:   \nA simple applet example description. ";
    }


    /**
     * Returns parameter information about this JApplet. 
     * Returns information about the parameters than are understood by this JApplet.
     * An applet should override this method to return an array of Strings 
     * describing these parameters. 
     * Each element of the array should be a set of three Strings containing 
     * the name, the type, and a description.
     *
     * @return a String[] representation of parameter information about this JApplet
     */
    public String[][] getParameterInfo()
    {
        // provide parameter information about the applet
        String paramInfo[][] = {
                 {"firstParameter", "1-10", "description of first parameter"},
                 {"status", "boolean", "description of second parameter"},
                 {"images",   "url",     "description of third parameter"}
        };
        return paramInfo;
    }
    
    public static void play() {
        int width = 500;
        int height = 600;
        LyleApplet applet = new LyleApplet();
        
//         String windowTitle = applet.getClass().getName();
//         System.out.println(windowTitle + " created " + new Date());
        JFrame frame = new JFrame("zuul");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height + 20);
        applet.setSize(width, height);
        frame.add(applet);
        applet.init();      // simulate browser call(1)
        applet.start();      // simulate browser call(2)
        frame.setVisible(true);
    } 
    
    public static void main(String[] argv) 
    { 
        play(); 
    }
}
