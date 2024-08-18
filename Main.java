import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Megam (Ty Strong)
 */
public class Main {
    // Constants for IP and Port input text placeholders
    private static final String ipFieldPlaceholderText = "IP Address";
    private static final String portNumberPlaceholderText = "Port Number";

    private static JTextArea chatArea;
    private static JTextField messageField;
    private static PrintWriter out;
    private static BufferedReader in;
    private static boolean isServer;
    private static Connection connection;
    /**
     * System.out.println is too damn long.
     * That is the only purpose of this function.
     * @param msg
     */
    public static void log(String msg) {
        System.out.println(msg);
    }

    public static void setOutputStream(PrintWriter writer) {
        out = writer;
    }

    public static void setInputStream(BufferedReader reader) {
        in = reader;
    }

    public static void displayMessage(String msg) {
        chatArea.append(msg + "\n");
    }

    private static void sendMessage () {
        String message = messageField.getText();
        out = connection.out;
        if(!message.isEmpty() && out != null) {
            out.println(message);
            out.flush();
            if(isServer) {
                displayMessage("Server sent: " + message);
            } else {
                displayMessage("Client sent: " + message);
            }
            messageField.setText("");
        }
    }

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame();
        mainFrame.setSize(800,800);
        mainFrame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(3,2,10,10));

        JLabel welcomeMessage = new JLabel("Welcome to ChatApp");
        topPanel.add(welcomeMessage);

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
        topPanel.add(ipField);

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
        topPanel.add(portField);

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
                connection = new Connection(ip, port);

            }
        });
        topPanel.add(connectButton);

        JButton hostButton = new JButton("Host");
        hostButton.setBounds(40,320,200,200);
        hostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = ipField.getText();
                String port = portField.getText();
                // start new hosting listen for connections
                connection = new Connection(ip, port, true);
            }
        });
        topPanel.add(hostButton);

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
        topPanel.add(exitButton);

        // Chat area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setBounds(400,400,400,400);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        // Message field at bottom
        JPanel messagePanel = new JPanel(new BorderLayout());
        messageField = new JTextField();
        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        messagePanel.add(messageField, BorderLayout.CENTER);
        messagePanel.add(sendButton, BorderLayout.EAST);

        mainFrame.add(topPanel, BorderLayout.NORTH);
        mainFrame.add(scrollPane, BorderLayout.CENTER);
        mainFrame.add(messagePanel, BorderLayout.SOUTH);

        mainFrame.setVisible(true);

    }
}
