import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Логин");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (Objects.requireNonNull(DatabaseHelper.validateUser(username, password)).getRole() > 0) {
                    // Successful login, show the main application frame
                    showMainApplicationFrame(username, password);
                } else {
                    // Failed login, show CAPTCHA
                    showCaptchaDialog();
                }
            }
        });

        panel.add(new JLabel("Логин:"));
        panel.add(usernameField);
        panel.add(new JLabel("Пароль:"));
        panel.add(passwordField);
        panel.add(loginButton);

        add(panel);
        setVisible(true);
    }

    private void showCaptchaDialog() {
        String captcha = CaptchaGenerator.generateCaptcha();
        String userInput = JOptionPane.showInputDialog("Введите CAPTCHA: " + captcha);

        if (userInput != null && userInput.equals(captcha)) {
            JOptionPane.showMessageDialog(null, "Неверный логин или пароль, попробуйте ещё раз.");
        } else {
            JOptionPane.showMessageDialog(null, "CAPTCHA пройдена неверно.");
        }
    }

    private void showMainApplicationFrame(String username, String password) {
        User user = DatabaseHelper.validateUser(username, password);

        if (user != null) {
            new MainApplicationFrame(user);
            dispose(); // Закрываем окно авторизации
        } else {
            // Логика обработки неуспешной авторизации
            showCaptchaDialog();
        }
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame();
            }
        });
    }
}
