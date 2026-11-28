package interface_adapter.dashboard;

import java.util.ArrayList;
import java.util.List;

public class DashboardViewModel {
    public static final String VIEW_NAME = "dashboard";

    private final List<DashboardViewModelListener> listeners = new ArrayList<>();
    private DashboardState state = new DashboardState();

    public DashboardState getState() {
        return state;
    }

    public void setState(DashboardState state) {
        this.state = state;
    }

    public void addListener(DashboardViewModelListener listener) {
        listeners.add(listener);
    }

    public void notifyStateChanged() {
        for (DashboardViewModelListener listener : listeners) {
            listener.stateChanged();
        }
    }
}
