import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class AI_Chatbot {

    static Map<String, String> knowledge = new HashMap<>();

    public static void main(String[] args) {

        // KNOWLEDGE BASE
        knowledge.put("java", "Java is a powerful and widely used object-oriented programming language used for building real-world applications.");
        knowledge.put("oops", "OOP includes concepts like class, object, inheritance, polymorphism, encapsulation, and abstraction.");
        knowledge.put("class", "A class is a blueprint used to create objects.");
        knowledge.put("object", "An object is an instance of a class.");
        knowledge.put("inheritance", "Inheritance allows one class to acquire properties of another.");
        knowledge.put("polymorphism", "Polymorphism allows methods to behave differently based on the object.");
        knowledge.put("encapsulation", "Encapsulation wraps data and methods into one unit.");
        knowledge.put("abstraction", "Abstraction hides implementation details.");

        knowledge.put("exception", "Exception handling is used to handle runtime errors using try-catch blocks.");
        knowledge.put("multithreading", "Multithreading allows a program to run multiple threads simultaneously, improving performance.");
        knowledge.put("thread", "A thread is a lightweight process in Java.");
        knowledge.put("threads", "Threads allow concurrent execution of tasks in Java.");
        knowledge.put("synchronization", "Synchronization controls access to shared resources in multithreading.");

        knowledge.put("array", "An array stores multiple values of the same type.");
        knowledge.put("string", "A String is a sequence of characters.");
        knowledge.put("arraylist", "ArrayList is a dynamic array in Java.");
        knowledge.put("linkedlist", "LinkedList uses nodes for storage.");
        knowledge.put("hashmap", "HashMap stores key-value pairs.");

        knowledge.put("codealpha", "CodeAlpha provides practical internship projects where students build real-world applications.");

        //  FRAME
        JFrame frame = new JFrame("AI Assistant - Developed by Umaknat Sah");
        frame.setSize(520, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //  CHAT AREA
        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        chatArea.setBackground(new Color(18, 18, 18));
        chatArea.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(chatArea);

        // INPUT FIELD
        JTextField inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        inputField.setBackground(new Color(30, 30, 30));
        inputField.setForeground(Color.GRAY);
        inputField.setCaretColor(Color.WHITE);
        inputField.setText("Type your message here...");

        inputField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (inputField.getText().equals("Type your message here...")) {
                    inputField.setText("");
                    inputField.setForeground(Color.WHITE);
                }
            }

            public void focusLost(FocusEvent e) {
                if (inputField.getText().isEmpty()) {
                    inputField.setText("Type your message here...");
                    inputField.setForeground(Color.GRAY);
                }
            }
        });

        JButton sendButton = new JButton("Send");

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        //  WELCOME MESSAGE
        chatArea.append(" AI Assistant: Hello! I'm your assistant.\n");
        chatArea.append(" AI Assistant: Ask me about Java, OOP, multithreading, or programming.\n\n");

        // ACTION
        ActionListener action = e -> {

            String input = inputField.getText().trim();

            if (input.isEmpty() || input.equals("Type your message here...")) return;

            chatArea.append(" You: " + input + "\n");
            inputField.setText("");

            new Thread(() -> {
                try {
                    String response = getResponse(input);

                    SwingUtilities.invokeLater(() -> {
                        chatArea.append(" AI Assistant: " + response + "\n\n");
                        chatArea.setCaretPosition(chatArea.getDocument().getLength());
                    });

                } catch (Exception ex) {
                    chatArea.append(" AI Assistant: Something went wrong.\n\n");
                }
            }).start();
        };

        sendButton.addActionListener(action);
        inputField.addActionListener(action);

        frame.setVisible(true);
    }

    // SMART RESPONSE METHOD
    public static String getResponse(String input) {

        try {

            input = input.toLowerCase();

            //  BASIC RESPONSES
            if (input.contains("hello") || input.contains("hi")) {
                return "Hello! How can I help you?";
            }

            if (input.contains("how are you")) {
                return "I'm doing great! How can I assist you?";
            }
            

            if (input.contains("bye")) {
                return "Goodbye! Keep learning and coding!";
            }

            if (input.contains("help")) {
                return "You can ask me about Java, OOP, collections, exceptions, or multithreading.";
            }

            //  CLEAN INPUT
            String cleaned = input
                    .replace("what is", "")
                    .replace("tell me", "")
                    .replace("something about", "")
                    .replace("define", "")
                    .replace("explain", "")
                    .replace("about", "")
                    .replace("?", "")
                    .trim();

            //  DIRECT MATCH
            for (String key : knowledge.keySet()) {
                if (cleaned.contains(key)) {
                    return knowledge.get(key);
                }
            }

            //  WORD-BY-WORD MATCH 
            String[] words = cleaned.split(" ");
            for (String word : words) {
                if (knowledge.containsKey(word)) {
                    return knowledge.get(word);
                }
            }

            return "I'm not sure about that yet. Try asking something related to Java or programming.";

        } catch (Exception e) {
            return "Error occurred while processing your request.";
        }
    }
}