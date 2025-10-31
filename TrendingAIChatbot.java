import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TrendingAIChatbot extends JFrame {

    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private Map<String, String> knowledgeBase;

    public TrendingAIChatbot() {

        // Knowledge base (FAQs)
        knowledgeBase = new HashMap<>();
        knowledgeBase.put("hi", "Hello! I'm your AI assistant.");
        knowledgeBase.put("hello", "Hi there! How can I help you today?");
        knowledgeBase.put("how are you", "I'm doing great, thanks for asking!");
        knowledgeBase.put("what is ai", "AI means Artificial Intelligence, making machines smart!");
        knowledgeBase.put("what is java", "Java is a programming language used worldwide.");
        knowledgeBase.put("who invented java", "James Gosling invented Java in 1995.");
        knowledgeBase.put("what is machine learning", "Machine Learning is a subset of AI allowing systems to learn from data.");
        knowledgeBase.put("bye", "Goodbye! Speak soon!");

        // Window setup
        setTitle("🤖 Trending AI Chatbot 2025");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));

        // Chat area setup
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));
        chatArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        // Input panel setup
        JPanel inputPanel = new JPanel(new BorderLayout(5,5));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(new Color(245, 245, 245));

        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputField.setBorder(new RoundedBorder(10));

        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));
        sendButton.setBackground(new Color(70, 130, 180));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setBorder(new RoundedBorder(10));

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.SOUTH);

        // Event listeners
        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());
    }

    private void sendMessage() {
        String userText = inputField.getText().toLowerCase().trim();
        if (userText.isEmpty()) return;

        appendMessage("You: " + userText, Color.BLUE);

        if (userText.equals("bye")) {
            appendMessage("Bot: " + knowledgeBase.get("bye"), Color.MAGENTA);
            inputField.setEditable(false);
            sendButton.setEnabled(false);
            return;
        }

        String response = findBestMatch(userText);
        appendMessage("Bot: " + response, Color.MAGENTA);
        inputField.setText("");
    }

    private void appendMessage(String message, Color color) {
        chatArea.setForeground(color);
        chatArea.append(message + "\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    private String findBestMatch(String userInput) {
        int minDistance = Integer.MAX_VALUE;
        String bestMatch = null;
        for (String question : knowledgeBase.keySet()) {
            int distance = levenshteinDistance(userInput, question);
            if (distance < minDistance) {
                minDistance = distance;
                bestMatch = question;
            }
        }
        if (minDistance <= 5) return knowledgeBase.get(bestMatch);
        return "Sorry, I don't know that yet!";
    }

    private int levenshteinDistance(String a, String b) {
        int[][] dp = new int[a.length()+1][b.length()+1];
        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i==0) dp[i][j] = j;
                else if (j==0) dp[i][j] = i;
                else dp[i][j] = Math.min(Math.min(dp[i-1][j]+1, dp[i][j-1]+1),
                                        dp[i-1][j-1] + (a.charAt(i-1)==b.charAt(j-1)?0:1));
            }
        }
        return dp[a.length()][b.length()];
    }

    private static class RoundedBorder implements Border {
        private int radius;
        RoundedBorder(int radius){ this.radius = radius; }
        public Insets getBorderInsets(Component c){ return new Insets(radius+1, radius+1, radius+2, radius); }
        public boolean isBorderOpaque(){ return true; }
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height){
            g.setColor(Color.GRAY);
            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TrendingAIChatbot bot = new TrendingAIChatbot();
            bot.setVisible(true);
        });
    }
}
