import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;

/**
 * @author Megam (Ty Strong)
 */
public class Main {
    // Constants for IP and Port input text placeholders
    private static final String ipFieldPlaceholderText = "IP Address";
    private static final String portNumberPlaceholderText = "Port Number";
    /**
     * System.out.println is too damn long.
     * That is the only purpose of this function.
     * @param msg
     */
    public static void log(String msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame();
        JPanel mainPanel = new JPanel();
        JLabel welcomeMessage = new JLabel("Welcome to ChatApp");
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(40,180,200,200);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log("Button pressed");
                log("Exit application");
                System.exit(0);
            }
        });
        JTextField ipField = new JTextField(ipFieldPlaceholderText);
        ipField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(ipField.getText().equals(ipFieldPlaceholderText)) {
                    ipField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(ipField.getText().isEmpty()){
                    ipField.setText(ipFieldPlaceholderText);
                }
            }
        });
        JTextField portField = new JTextField(portNumberPlaceholderText);
        portField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(portField.getText().equals(portNumberPlaceholderText)) {
                    portField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(portField.getText().isEmpty()) {
                    portField.setText(portNumberPlaceholderText);
                }
            }
        });
        JButton connectButton = new JButton("Connect");
        connectButton.setBounds(40,300, 200,200);
        // new actionLister can be replaced with lambda, but I prefer this.
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = ipField.getText();
                String port = portField.getText();
                log(ip);
                log(port);
                // Connect to Server client
                Connection.connectToHost(ip,port);
            }
        });
        JButton hostButton = new JButton("Host");
        hostButton.setBounds(40,320,200,200);
        hostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = ipField.getText();
                String port = portField.getText();
                // start new hosting listen for connections
                Connection.startHosting(ip,port);
            }
        });
        mainPanel.add(welcomeMessage);
        mainPanel.add(exitButton);
        mainPanel.add(ipField);
        mainPanel.add(portField);
        mainPanel.add(connectButton);
        mainPanel.add(hostButton);
        mainPanel.setBounds(40,80,20,200);
        mainPanel.setBackground(Color.gray);
        mainFrame.add(mainPanel);
        mainFrame.setSize(800,800);
        mainFrame.setVisible(true);
    }
}
