package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Listener implements ActionListener {

    public Listener() {}

    public void actionPerformed (ActionEvent e) {
        String action= e.getActionCommand();
        System.out.println("Faire des" + action);
    }
    
}
