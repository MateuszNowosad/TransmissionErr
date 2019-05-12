package com.teleinfgroup;

import com.teleinfgroup.ErrorDetectionAlgorithms.*;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;


public class MainView {

    private static Map<String, CRCType> CRCTypes = null;
    private static String chosenAlgorithm;
    private static int disturbedBitsCounter;
    private static boolean ifBitsDisturbed;

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
    private JTextArea messageTextField;
    private JButton encode;
    private JRadioButton ASCII;
    private JRadioButton binary;
    private JTextArea encodedMessage;
    private JRadioButton CRCRadioButton;
    private JRadioButton hamming74RadioButton;
    private JRadioButton parityControlRadioButton;
    private JTextField bitsCountTextField;
    private JCheckBox randomCountCheckBox;
    private JTextField bitsPositionsTextField;
    private JCheckBox randomPositionCheckBox;
    private JButton disturbAndDecode;
    private JTextArea disturbedMessage;
    private JTextPane correctedMessage;
    private JTextArea decodedMessage;
    private JLabel disturbedBits;
    private JLabel detectedBits;
    private JLabel redundantData;
    private JComboBox crcComboBox;
    private JButton generateMessage;
    private JTextField messageLenght;
    private JLabel realSentData;
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
            messageTextField.setForeground(Color.black);
            String encodedMessageSt = "";
            encodedMessage.setText("");
            try {
                if (isASCII(text)) {
                    message = new Message(text, false);
                    message.setMessage(text);
                    encode(message);
                    encodedMessageSt = message.getEncodedMessage();
                    enableChange(true);
                    encodedMessage.setText(encodedMessageSt);
                } else if (isBinary(text)) {
                    message = new Message(text, true);
                    message.setMessage(text);
                    encode(message);
                    encodedMessageSt = message.getEncodedMessage();
                    enableChange(true);
                    encodedMessage.setText(encodedMessageSt);
                } else {
                    enableChange(false);
                    messageTextField.setForeground(Color.red);
                    messageTextField.setText("Error: incorrect message!");
                }

            } catch (NullPointerException exception) {
                enableChange(false);
                messageTextField.setForeground(Color.red);
                messageTextField.setText("Error: incorrect message!");
            }
        });

        disturbAndDecode.addActionListener(e -> {
            int bitsCount;
            Set<Integer> bitsPositions = new LinkedHashSet<>();
            String[] bitsPositionString;
            try {
                if (!randomPositionCheckBox.isSelected() && !bitsPositionsTextField.getText().isEmpty()) {
                    bitsPositionString = bitsPositionsTextField.getText().split(",");
                    for (String str : bitsPositionString) {
                        if (!str.equals(""))
                            bitsPositions.add(Integer.parseInt(str));
                    }
                    disturbedBitsCounter = bitsPositions.size();
                    ifBitsDisturbed = true;
                    message.sendMessage(bitsPositions);
                } else if (!randomCountCheckBox.isSelected() && !bitsCountTextField.getText().isEmpty()) {
                    bitsCount = Integer.parseInt(bitsCountTextField.getText());
                    ifBitsDisturbed = true;
                    disturbedBitsCounter = bitsCount;
                    message.sendMessage(bitsCount);
                } else {
                    ifBitsDisturbed = false;
                    disturbedBitsCounter = 0;
                    message.sendMessage();
                }
                decodeMessage();
                setTextFields();
            } catch (NumberFormatException ex) {

            } catch (BadLocationException ex) {
                ex.printStackTrace();
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
            messageTextField.setForeground(Color.black);
            try {
                if (ASCII.isSelected()) {
                    messageTextField.setText(generateMessage(Integer.parseInt(messageLenght.getText()), false));
                } else {
                    if (binary.isSelected()) {
                        messageTextField.setText(generateMessage(Integer.parseInt(messageLenght.getText()), true));
                    }
                }
            } catch (NumberFormatException ex) {
                messageTextField.setForeground(Color.red);
                messageTextField.setText("Error: set message length!");
            }
        });
    }

    private void setTextFields() throws BadLocationException {
        disturbedMessage.setText(message.getSentMessage());
        disturbedBits.setText(String.valueOf(disturbedBitsCounter));
        correctedMessage.setText("");
        realSentData.setText(String.valueOf(message.getDecodedMessage().length()));
        redundantData.setText(String.valueOf(message.getRedundantDataPositions().size()));

        Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

        Style red = correctedMessage.addStyle("red", def);
        StyleConstants.setForeground(red, Color.red);

        Style black = correctedMessage.addStyle("black", def);
        StyleConstants.setForeground(black, Color.black);

        Style green = correctedMessage.addStyle("green", def);
        StyleConstants.setForeground(green, Color.green);

        Style blue = correctedMessage.addStyle("blue", def);
        StyleConstants.setForeground(blue, Color.blue);

        Document doc = correctedMessage.getDocument();

        if (chosenAlgorithm.equals("Hamming74")) {

            String correctedMessageSt = correctBits();
            decodedMessage.setText(message.getDecodedMessage());
            int first = 0, last, detected = 0;

            TreeSet<Integer> correctedBitsPositions = message.getErrorsPosition();

            if (ifBitsDisturbed) {
                for (Integer i : message.getDisturbedBitsPositions()) {
                    last = i;
                    doc.insertString(doc.getLength(), correctedMessageSt.substring(first, last), black);
                    if (correctedBitsPositions.contains(i)) {
                        detected++;
                        doc.insertString(doc.getLength(), correctedMessageSt.substring(last, last + 1), green);
                    } else {
                        doc.insertString(doc.getLength(), correctedMessageSt.substring(last, last + 1), red);
                    }
                    first = i + 1;
                }


                doc.insertString(doc.getLength(), correctedMessageSt.substring(first), black);
            } else {
                correctedMessage.setText(correctedMessageSt);
            }
            detectedBits.setText(String.valueOf(detected));


        } else {
            detectedBits.setText("---------------------------");
            int first = 0, last;
            String messageSt = message.getSentMessage();

            for (Integer i : message.getErrorsPosition()) {
                last = i;
                doc.insertString(doc.getLength(), messageSt.substring(first, last), black);
                doc.insertString(doc.getLength(), messageSt.substring(last, last + 1), red);
                first = i + 1;
            }
            doc.insertString(doc.getLength(), messageSt.substring(first), black);
            decodedMessage.setText(message.getDecodedMessage());
        }
    }

    private String correctBits() {
        String encodedMessage = message.getEncodedMessage();
        StringBuilder stringBuilder = new StringBuilder(encodedMessage);
        for (int i : message.getErrorsPosition()) {
            stringBuilder.replace(i, i + 1, stringBuilder.charAt(i) == '1' ? "0" : "1");
        }
        return stringBuilder.toString();
    }

    private void decodeMessage() {
        switch (chosenAlgorithm) {
            case "Hamming74":
                ErrorDetectionAlgorithm hamming74 = new Hamming74();
                hamming74.decodeMsg(message);
                break;
            case "ParityControl":
                ErrorDetectionAlgorithm Parity = new ParityControl();
                Parity.decodeMsg(message);
                break;
            default:
                ErrorDetectionAlgorithm CRC16 = new CRC(CRCTypes.get(chosenAlgorithm));
                CRC16.decodeMsg(message);
                break;
        }
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
        frame = new JFrame("Transmission Errors");
        frame.setContentPane(new MainView().mainJPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setMinimumSize(new Dimension(650, 600));
        frame.setPreferredSize(new Dimension(650, 600));
        frame.setVisible(true);
        frame.setResizable(true);
    }

    public static boolean isBinary(String text) {
        if (text.matches("[01]+") && !text.startsWith("0")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isASCII(String name) {
        return name.matches("[a-zA-Z]+");
    }

}

