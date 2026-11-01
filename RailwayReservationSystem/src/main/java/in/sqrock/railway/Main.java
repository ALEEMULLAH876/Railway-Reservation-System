package in.sqrock.railway;

import in.sqrock.railway.config.Database;
import in.sqrock.railway.ui.LoginFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) { }
            if (!Database.testConnection()) {
                JOptionPane.showMessageDialog(null,
                    "Cannot connect to MySQL. Check db.properties and make sure MySQL is running.",
                    "Database connection", JOptionPane.ERROR_MESSAGE);
            }
            new LoginFrame().setVisible(true);
        });
    }
}
