import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class ThreadLockExample {

    public static void main(String[] args) throws InterruptedException
    {
        GUIProgram program = new GUIProgram();
        String fromGUI = program.getStringFromGUI();
        System.out.println(fromGUI);
    }

    public static class GUIProgram {
        public String getStringFromGUI() throws InterruptedException
        {
            GUIProgram self = this;
            JFrame window = new JFrame("Enter text and press Enter");
            Container container = window.getContentPane();

            JTextField textbox = new JTextField();
            textbox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //Ran asynchronously in another thread
                    synchronized (self)
                    {
                        self.notify();
                    }
                    window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
                    window.setVisible(false);
                }
            });

            container.add(textbox);
            window.pack();

            //Set visibile starts up the GUI thread for the window JFrame
            //and all of its graphical elements
            window.setVisible(true);
            synchronized (self) {
                wait();
            }
            return textbox.getText();
        }
    }
}
