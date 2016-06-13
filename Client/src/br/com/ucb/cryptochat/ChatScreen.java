package br.com.ucb.cryptochat;

import br.com.ucb.cryptochat.interfaces.Server;
import br.com.ucb.cryptochat.model.Client;
import br.com.ucb.cryptochat.model.Message;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ChatScreen implements Observer {

    /* View Attributes */

    private JFrame chatFrame;
    private JPanel chatPanel;

    private JList clientsListView;
    private JScrollPane scrollPane;
    private JTextArea receivedMessagesTextArea;
    private JLabel typeMessageLabel;
    private JLabel setUserNameLabel;

    private JTextField inputMessageTextField;
    private JTextField inputUsernameTextField;

    private JButton sendButton;
    private JButton setUsernameButton;
    private JButton reloadClientsButton;

    /* Adapter Attributes */

    private DefaultListModel clientListModel;

    /* Model Attributes */

    private Socket socket;
    private Server server;
    private Client currentClient;
    private Client recipient;
    private List<Client> clients;

    public ChatScreen(Socket socket, Client client, Server server) {
        this.socket = socket;
        this.server = server;
        this.currentClient = client;
        this.clients = new LinkedList<>();

        configureComponents();
        configureActions();
        showScreen();
    }

    public void configureComponents() {
        // Configure Window Frame
        this.chatFrame = new JFrame("CryptoChat");
        this.chatPanel = new JPanel(new FlowLayout());

        // Configure Clients List View
        this.clientsListView = new JList();
        this.clientListModel = new DefaultListModel();
        this.clientsListView.setModel(this.clientListModel);

        // Configure Received Messages TextArea
        this.receivedMessagesTextArea = new JTextArea(10, 30);
        this.receivedMessagesTextArea.setEditable(false);
        this.scrollPane = new JScrollPane(this.receivedMessagesTextArea);

        // Configure Input Labels
        this.typeMessageLabel = new JLabel("Digite uma mensagem...");
        this.inputMessageTextField = new JTextField(10);

        this.setUserNameLabel = new JLabel("Defina seu username...");
        this.inputUsernameTextField = new JTextField(10);

        // Configure Buttons
        this.sendButton = new JButton("Enviar");
        this.reloadClientsButton = new JButton("Recarregar Usuarios");
        this.setUsernameButton = new JButton("Alterar Username");

        // Add Components to the View
        this.chatFrame.setContentPane(this.chatPanel);

        this.chatPanel.add(this.clientsListView);
        this.chatPanel.add(this.scrollPane);
        this.chatPanel.add(this.typeMessageLabel);
        this.chatPanel.add(this.inputMessageTextField);
        this.chatPanel.add(this.sendButton);
        this.chatPanel.add(this.setUserNameLabel);
        this.chatPanel.add(this.inputUsernameTextField);
        this.chatPanel.add(this.setUsernameButton);
        this.chatPanel.add(this.reloadClientsButton);
    }

    public void configureActions() {
        // Configure Action for Send Button
        this.sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (isMessageValidForSend()) {
                        // Sending Message over RMI
                        server.sendMessage(new Message(inputMessageTextField.getText(), recipient, currentClient));
                        inputMessageTextField.setText("");
                    }
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // Configure Action for Reloaded Clients List
        this.reloadClientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get All Clients over RMI.
                    clients.clear();
                    clientListModel.clear();

                    clients.addAll(server.getsClients());
                    for (Client client : clients) {
                        if (currentClient.equals(client)) {
                            clientListModel.addElement("You: " + client.getUsername());
                        } else {
                            clientListModel.addElement(client.getUsername());
                        }
                    }
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // Configure Action for Selecting Recipient
        this.clientsListView.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                recipient = clients.get(clientsListView.getSelectedIndex());
            }
        });

        this.setUsernameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (inputUsernameTextField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(chatFrame, "Want to chance the username? Tell me first!", "ZZzzZzZZZzzz", JOptionPane.WARNING_MESSAGE);
                    } else {
                        server.updateUsername(currentClient, inputUsernameTextField.getText());
                        reloadClientsButton.doClick();
                    }
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private Boolean isMessageValidForSend() {
        if (this.inputMessageTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this.chatFrame, "Please, don't do this. \nNormal people usually write a text before send, you know, common sense.", "What!?", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (this.recipient == null) {
            JOptionPane.showMessageDialog(this.chatFrame, "..If you think that I can read minds, I have a 'Good News' for you: I Don't.\n To whom I have to send this message ?", "Yeah! Right!", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public void showScreen() {
        // Configure Windows to be Display
        this.chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.chatFrame.setSize(500, 300);
        this.chatFrame.setLocationByPlatform(true);
        this.chatFrame.setLocationRelativeTo(null);
        this.chatFrame.setVisible(true);

        this.reloadClientsButton.doClick();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Message) {
            receivedMessagesTextArea.append(((Message) arg).getSender().getUsername() + " says: " + ((Message) arg).getMessage() + "\n");
        }
    }

}
