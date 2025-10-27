/*
 * A single-file Java Quiz Application with a modern GUI.
 *
 * To run:
 * 1. Save this code as ModernQuizApp.java
 * 2. Open a terminal
 * 3. Run: java ModernQuizApp.java
 * (Requires JDK 11 or later)
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ModernQuizApp {

    // --- Data: Questions and Answers ---
    // We hard-code the questions here to keep it a single file.
    // Structure: [Question, Option A, Option B, Option C, Option D, Correct Option Index (0-3)]
    private final String[][] questionData = {
        {
            "What is the capital of France?",
            "Berlin", "Madrid", "Paris", "Rome",
            "2"
        },
        {
            "Which keyword is used to create an object in Java?",
            "new", "create", "object", "make",
            "0"
        },
        {
            "What does 'OOP' stand for?",
            "Object-Oriented Programming", "Open Office Protocol", "Original-Origin Policy", "Other-Object-Project",
            "0"
        },
        {
            "Which of these is NOT a primitive data type in Java?",
            "int", "String", "boolean", "float",
            "1"
        },
        {
            "What method is the entry point for a Java application?",
            "start()", "run()", "main()", "init()",
            "2"
        }
    };

    // --- State Variables ---
    private int currentQuestionIndex = 0;
    private int score = 0;

    // --- GUI Components ---
    private JFrame frame;
    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup optionGroup;
    private JButton nextButton;

    /**
     * Constructor: Sets up the entire GUI.
     */
    public ModernQuizApp() {
        // 1. Create the main window (JFrame)
        frame = new JFrame("Modern Quiz App");
        frame.setSize(550, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(20, 20)); // Gaps between components
        frame.getContentPane().setBackground(Color.WHITE); // Set background

        // 2. Create the question label
        questionLabel = new JLabel("Question text will go here");
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(questionLabel, BorderLayout.NORTH);

        // 3. Create a panel to hold the radio buttons
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 10, 10)); // 4 rows, 1 col, gaps
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Padding
        optionsPanel.setBackground(Color.WHITE);

        optionButtons = new JRadioButton[4];
        optionGroup = new ButtonGroup(); // Ensures only one button is selected

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton("Option " + (i + 1));
            optionButtons[i].setFont(new Font("Segoe UI", Font.PLAIN, 14));
            optionButtons[i].setBackground(Color.WHITE);
            optionButtons[i].setFocusable(false); // Nicer UI feel
            
            optionGroup.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }
        
        frame.add(optionsPanel, BorderLayout.CENTER);

        // 4. Create the "Next" button
        nextButton = new JButton("Next Question");
        nextButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nextButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Create a panel to hold the button and give it padding
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // Bottom padding
        buttonPanel.add(nextButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // 5. Add logic (an "Action Listener") to the button
        nextButton.addActionListener(this::handleNextButtonAction);

        // 6. Load the first question
        loadQuestion(currentQuestionIndex);

        // 7. Make the window visible
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    }

    /**
     * Loads a question and its options into the GUI components.
     */
    private void loadQuestion(int index) {
        String[] q = questionData[index];

        // Set question text (using <html> for word wrap)
        questionLabel.setText("<html><div style='text-align: center;'>" + q[0] + "</div></html>");

        // Set options text
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(q[i + 1]);
        }

        // Clear the previous selection
        optionGroup.clearSelection();
    }

    /**
     * This method is called when the "Next" button is clicked.
     */
    private void handleNextButtonAction(ActionEvent e) {
        // Find which option was selected
        int selectedIndex = -1;
        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i].isSelected()) {
                selectedIndex = i;
                break;
            }
        }

        // Check if an answer was selected
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an answer!", "Wait!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Check if the answer is correct
        int correctIndex = Integer.parseInt(questionData[currentQuestionIndex][5]);
        if (selectedIndex == correctIndex) {
            score++;
        }

        // Move to the next question
        currentQuestionIndex++;

        // Check if the quiz is over
        if (currentQuestionIndex < questionData.length) {
            loadQuestion(currentQuestionIndex);
            if (currentQuestionIndex == questionData.length - 1) {
                nextButton.setText("Finish Quiz"); // Change button text on last question
            }
        } else {
            showFinalScore();
        }
    }

    /**
     * Displays the final score in a dialog box.
     */
    private void showFinalScore() {
        String message = String.format(
            "Quiz Finished!\nYour Score: %d out of %d",
            score, 
            questionData.length
        );
        
        JOptionPane.showMessageDialog(frame, message, "Final Score", JOptionPane.INFORMATION_MESSAGE);
        
        // End the application
        frame.dispose(); // Close the window
        System.exit(0);
    }


    /**
     * The main entry point for the application.
     */
    public static void main(String[] args) {
        // --- Set the "Nimbus" Look and Feel ---
        // This makes the GUI components look more modern.
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, just use the default.
        }

        // Run the GUI code on the Event Dispatch Thread (standard practice)
        SwingUtilities.invokeLater(ModernQuizApp::new);
    }
}