package com.teleinfgroup;

import com.teleinfgroup.ErrorDetectionAlgorithms.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;


public class MainView {

    private static Map<String, CRCType> CRCTypes = null;
    private static String chosenAlgorithm;

    private static void init() {
        CRCTypes = new HashMap<>();
        CRCTypes.put("CRC-12", new CRCType("CRC-12", 0x80f, 12));
        CRCTypes.put("CRC-16", new CRCType("CRC-16", 0x8005, 16));
        CRCTypes.put("CRC-16-REVERSE", new CRCType("CRC-16-REVERSE", 0x4003, 16));
        CRCTypes.put("CRC-32", new CRCType("CRC-32", 0x4C11DB7, 32));
        CRCTypes.put("SDLC", new CRCType("SDLC", 0x1021, 16));
        CRCTypes.put("SDLC-REVERSE", new CRCType("SDLC-REVERSE", 0x811, 16));
        CRCTypes.put("CRC-ITU", new CRCType("CRC-ITU", 0x1021, 16));
        CRCTypes.put("ATM", new CRCType("ATM", 0x7, 8));
    }

    private static JFrame frame;
    private JPanel mainJPanel;
    private JTextField messageTextField;
    private JButton encode;
    private JRadioButton ASCI;
    private JRadioButton binary;
    private JLabel encodedMessage;
    private JRadioButton CRCRadioButton;
    private JRadioButton hamming74RadioButton;
    private JRadioButton parityControlRadioButton;
    private JTextField bitsCountTextField;
    private JCheckBox randomCountCheckBox;
    private JTextField bitsPositionsTextField;
    private JCheckBox randomPositionCheckBox;
    private JButton disturbAndDecode;
    private JTextArea disturbedMessage;
    private JLabel correctedMessage;
    private JLabel decodedMessage;
    private JLabel disturbedBits;
    private JLabel detectedBits;
    private JLabel correctedBits;
    private JLabel error;
    private JComboBox crcComboBox;
    private JButton generateMessage;
    private JTextField messageLenght;
    private ButtonGroup radioButtonGroup;
    private static Message message;
    private static Message messageInBinary;

    public MainView() {
        bitsCountTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        bitsPositionsTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE || c == ',')) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        messageLenght.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        encode.addActionListener(e -> {
            String text = messageTextField.getText();
            encodedMessage.setForeground(Color.black);
            message = new Message(text, false);
            messageInBinary = new Message(text, true);
            try {
                if (ASCI.isSelected() && isASCI(text)) {
                    message.setMessage(text);
                    encode(message);
                    encodedMessage.setText(message.getEncodedMessage());
                } else if (binary.isSelected() && isBinary(text)) {
                    message = new Message(text, true);
                    message.setMessage(text);
                    encode(message);
                    encodedMessageSt = message.getEncodedMessage();

                }
                enableChange(true);
            } catch (NullPointerException exception) {
                enableChange(false);
                encodedMessage.setForeground(Color.red);
                encodedMessage.setText("Error: incorrect message!");
            }
        });

        disturbAndDecode.addActionListener(e -> {
            int bitsCount;
            Set<Integer> bitsPositions = new LinkedHashSet<>();
            String[] bitsPositionString;
            try {
                if (!randomPositionCheckBox.isSelected() && bitsPositionsTextField.getText().isEmpty()) {
                    bitsPositionString = bitsPositionsTextField.getText().split(",");
                    for (String str : bitsPositionString) {
                        bitsPositions.add(Integer.parseInt(str));
                    }
                    message.sendMessage(bitsPositions);
                } else if (!randomCountCheckBox.isSelected() && bitsCountTextField.getText().isEmpty()) {
                    bitsCount = Integer.parseInt(bitsCountTextField.getText());
                    message.sendMessage(bitsCount);
                } else {
                    message.sendMessage();
                }

                String sentMessageSt = message.getSentMessage();
                disturbedMessage.setText("");
                int signLength = frame.getWidth() - frame.getWidth()/4;
                int first = 0, last = signLength;
                while(last < sentMessageSt.length()){
                    disturbedMessage.append(sentMessageSt.substring(first, last) + "\n");
                    first += signLength;
                    last += signLength;
                }
                disturbedMessage.append(sentMessageSt.substring(first) + "\n");

            } catch (NumberFormatException ex) {

            }
        });

        randomCountCheckBox.addActionListener(e -> {
            if (randomCountCheckBox.isSelected())
                bitsCountTextField.setEnabled(false);
            else {
                bitsCountTextField.setEnabled(true);
                bitsPositionsTextField.setEnabled(false);
                randomPositionCheckBox.setSelected(true);
            }
        });

        randomPositionCheckBox.addActionListener(e -> {
            if (randomPositionCheckBox.isSelected())
                bitsPositionsTextField.setEnabled(false);
            else {
                bitsPositionsTextField.setEnabled(true);
                bitsCountTextField.setEnabled(false);
                randomCountCheckBox.setSelected(true);
            }
        });

        generateMessage.addActionListener(e -> {
            encodedMessage.setForeground(Color.black);
            try {
                if (ASCI.isSelected()) {
                    messageTextField.setText(generateMessage(Integer.parseInt(messageLenght.getText()), false));
                } else {
                    if (binary.isSelected()) {
                        messageTextField.setText(generateMessage(Integer.parseInt(messageLenght.getText()), true));
                    }
                }
            } catch (NumberFormatException ex) {
                encodedMessage.setForeground(Color.red);
                encodedMessage.setText("Error: set message lenght!");
            }
        });
    }

    private String generateMessage(int messageLenght, boolean ifBinary) {
        StringBuilder builder = new StringBuilder();
        if (ifBinary) {
            builder.append(1);
            messageLenght = messageLenght - 1;
            while (messageLenght-- != 0) {
                builder.append((int) (Math.random() * 2));
            }
        } else {
            String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
            while (messageLenght-- != 0) {
                int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
                builder.append(ALPHA_NUMERIC_STRING.charAt(character));
            }
        }
        return builder.toString();
    }

    private void enableChange(boolean b) {
        disturbAndDecode.setEnabled(b);
        randomCountCheckBox.setEnabled(b);
        if (!randomCountCheckBox.isSelected())
            bitsCountTextField.setEnabled(b);
        randomPositionCheckBox.setEnabled(b);
        if (!randomPositionCheckBox.isSelected())
            bitsPositionsTextField.setEnabled(b);
    }

    private void encode(Message message) {
        if (CRCRadioButton.isSelected()) {
            ErrorDetectionAlgorithm CRC16 = new CRC(CRCTypes.get(crcComboBox.getSelectedItem().toString()));
            CRC16.encodeMsg(message);
            chosenAlgorithm = crcComboBox.getSelectedItem().toString();
        } else if (hamming74RadioButton.isSelected()) {
            ErrorDetectionAlgorithm hamming74 = new Hamming74();
            hamming74.encodeMsg(message);
            chosenAlgorithm = "Hamming74";
        } else if (parityControlRadioButton.isSelected()) {
            ErrorDetectionAlgorithm Parity = new ParityControl();
            Parity.encodeMsg(message);
            chosenAlgorithm = "ParityControl";
        }
    }


    public static void main(String[] args) {
        init();
        JFrame frame = new JFrame("App");
        frame.setContentPane(new MainView().mainJPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static boolean isBinary(String text) {
        if (text.matches("[01]+") && !text.startsWith("0")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isASCI(String name) {
        return name.matches("[a-zA-Z]+");
    }

}

