package interface_adapter.load_pantry;

import interface_adapter.dashboard.DashboardState;
import interface_adapter.dashboard.DashboardViewModel;
import use_case.load_pantry.LoadPantryOutputBoundary;
import use_case.load_pantry.LoadPantryOutputData;

import java.util.ArrayList;
import java.util.List;

public class LoadPantryPresenter implements LoadPantryOutputBoundary {
    private final DashboardViewModel dashboardViewModel;

    public LoadPantryPresenter(DashboardViewModel dashboardViewModel) {
        this.dashboardViewModel = dashboardViewModel;
    }

    @Override
    public void prepareSuccessView(LoadPantryOutputData outputData) {
        final List<DashboardState.PantryRow> rows = new ArrayList<>();

        for (LoadPantryOutputData.ItemData item : outputData.getItems()) {
            final String quantityText;
            switch (item.getUnit()) {
                case GRAM:
                    quantityText = item.getAmount() + " g";
                    break;
                case MILLILITRE:
                    quantityText = item.getAmount() + " mL";
                    break;
                default:
                    quantityText = Integer.toString(item.getAmount());
                    break;
            }

            final String expiryText = item.getExpiryDate() == null
                    ? "No expiry date"
                    : "Expires " + item.getExpiryDate();

            rows.add(new DashboardState.PantryRow(
                    item.getItemId(), item.getName(), quantityText, expiryText));
        }

        final DashboardState state = dashboardViewModel.getState();
        state.setPantryRows(rows);
        dashboardViewModel.notifyStateChanged();
    }
}
