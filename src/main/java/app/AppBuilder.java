package app;

import data_access.pantry_access.PantryDataAccessObject;
import entity.PantryItemFactory;
import interface_adapter.dashboard.DashboardViewModel;
import interface_adapter.load_pantry.LoadPantryController;
import interface_adapter.load_pantry.LoadPantryPresenter;
import use_case.PantryDataAccessInterface;
import use_case.load_pantry.LoadPantryInteractor;
import view.DashboardView;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

public class AppBuilder {
    private static final String SHOPPING_LISTS = "shopping lists";
    private static final String RECIPES = "recipes";
    private static final String PANTRY_HISTORY = "pantry history";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);
    private final PantryDataAccessInterface pantryDataAccessObject;

    private DashboardViewModel dashboardViewModel;
    private LoadPantryController loadPantryController;

    public AppBuilder(String csvPath) {
        final PantryItemFactory pantryItemFactory = new PantryItemFactory();
        pantryDataAccessObject = new PantryDataAccessObject(csvPath, pantryItemFactory);
    }

    public AppBuilder addDashboardView() {
        dashboardViewModel = new DashboardViewModel();
        final DashboardView dashboardView = new DashboardView(dashboardViewModel);
        cardPanel.add(dashboardView, DashboardViewModel.VIEW_NAME);
        return this;
    }

    public AppBuilder addLoadPantryUseCase() {
        final LoadPantryPresenter presenter = new LoadPantryPresenter(dashboardViewModel);
        final LoadPantryInteractor interactor =
                new LoadPantryInteractor(pantryDataAccessObject, presenter);
        loadPantryController = new LoadPantryController(interactor);
        return this;
    }

    public JFrame build() {
        cardPanel.add(createPlaceholder("Shopping Lists"), SHOPPING_LISTS);
        cardPanel.add(createPlaceholder("Suggest Recipes"), RECIPES);
        cardPanel.add(createPlaceholder("Pantry History"), PANTRY_HISTORY);

        final JLabel title = new JLabel("Pantry Tracker");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 26f));
        title.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        final JPanel shell = new JPanel(new BorderLayout());
        shell.add(title, BorderLayout.NORTH);
        shell.add(createSidebar(), BorderLayout.WEST);
        shell.add(cardPanel, BorderLayout.CENTER);
        shell.setPreferredSize(new Dimension(1000, 650));

        openView(DashboardViewModel.VIEW_NAME, loadPantryController::execute);

        final JFrame application = new JFrame("Pantry Tracker");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.setContentPane(shell);
        application.pack();
        application.setLocationRelativeTo(null);
        return application;
    }

    private JPanel createSidebar() {
        final JPanel buttons = new JPanel(new GridLayout(0, 1, 0, 6));

        buttons.add(createNavigationButton(
                "Dashboard", DashboardViewModel.VIEW_NAME, loadPantryController::execute));
        buttons.add(createNavigationButton("Add/View Shopping Lists", SHOPPING_LISTS));
        buttons.add(createNavigationButton("Suggest Recipes", RECIPES));
        buttons.add(createNavigationButton("View Pantry History", PANTRY_HISTORY));

        final JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        sidebar.add(buttons, BorderLayout.NORTH);
        sidebar.add(new JLabel("PantryTracker"), BorderLayout.SOUTH);
        return sidebar;
    }

    private JButton createNavigationButton(String label, String cardName) {
        return createNavigationButton(label, cardName, () -> {});
    }

    private JButton createNavigationButton(String label, String cardName, Runnable onOpen) {
        final JButton button = new JButton(label);
        button.addActionListener(event -> openView(cardName, onOpen));
        return button;
    }

    private void openView(String cardName, Runnable onOpen) {
        cardLayout.show(cardPanel, cardName);
        onOpen.run();
    }

    private JPanel createPlaceholder(String title) {
        final JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        panel.add(new JLabel(title + " — coming soon"), BorderLayout.NORTH);
        return panel;
    }
}
