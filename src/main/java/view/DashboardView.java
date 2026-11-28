package view;

import interface_adapter.dashboard.DashboardState;
import interface_adapter.dashboard.DashboardViewModel;
import interface_adapter.dashboard.DashboardViewModelListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

public class DashboardView extends JPanel implements DashboardViewModelListener {
    private final DashboardViewModel dashboardViewModel;
    private final JPanel cardGrid = new JPanel(new GridLayout(0, 2, 12, 12));
    private final JButton addItemButton = new JButton("Add Item");

    public DashboardView(DashboardViewModel dashboardViewModel) {
        this.dashboardViewModel = dashboardViewModel;

        setLayout(new BorderLayout(0, 12));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        final JPanel heading = new JPanel(new FlowLayout(FlowLayout.LEFT));
        heading.add(new JLabel("In-Pantry"));
        heading.add(addItemButton);
        addItemButton.setEnabled(false);
        add(heading, BorderLayout.NORTH);

        final JPanel scrollContent = new JPanel(new BorderLayout());
        scrollContent.add(cardGrid, BorderLayout.NORTH);
        add(new JScrollPane(scrollContent), BorderLayout.CENTER);

        dashboardViewModel.addListener(this);
        stateChanged();
    }

    @Override
    public void stateChanged() {
        final List<DashboardState.PantryRow> rows =
                dashboardViewModel.getState().getPantryRows();
        cardGrid.removeAll();

        if (rows.isEmpty()) {
            cardGrid.add(new JLabel("Your pantry is empty."));
        } else {
            for (DashboardState.PantryRow row : rows) {
                cardGrid.add(createItemCard(row));
            }

            if (rows.size() % 2 != 0) {
                cardGrid.add(new JPanel());
            }
        }

        cardGrid.revalidate();
        cardGrid.repaint();
    }

    private JPanel createItemCard(DashboardState.PantryRow row) {
        final JPanel card = new JPanel(new BorderLayout(0, 6));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));

        final JPanel details = new JPanel(new BorderLayout(8, 4));
        details.add(new JLabel(row.getName()), BorderLayout.CENTER);
        details.add(new JLabel(row.getQuantityText()), BorderLayout.EAST);
        details.add(new JLabel(row.getExpiryText()), BorderLayout.SOUTH);
        card.add(details, BorderLayout.NORTH);

        final JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        actions.add(createDisabledButton("Edit"));
        actions.add(createDisabledButton("Used"));
        actions.add(createDisabledButton("Disposed"));
        actions.add(createDisabledButton("Delete"));
        card.add(actions, BorderLayout.SOUTH);

        return card;
    }

    private JButton createDisabledButton(String label) {
        final JButton button = new JButton(label);
        button.setEnabled(false);
        return button;
    }
}
