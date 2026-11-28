package app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            final JFrame application = new AppBuilder("pantry.csv")
                    .addDashboardView()
                    .addLoadPantryUseCase()
                    .build();

            application.setVisible(true);
        });
    }
}
