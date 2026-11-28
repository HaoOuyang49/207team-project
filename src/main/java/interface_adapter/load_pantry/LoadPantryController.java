package interface_adapter.load_pantry;

import use_case.load_pantry.LoadPantryInputBoundary;

public class LoadPantryController {
    private final LoadPantryInputBoundary loadPantryInteractor;

    public LoadPantryController(LoadPantryInputBoundary loadPantryInteractor) {
        this.loadPantryInteractor = loadPantryInteractor;
    }

    public void execute() {
        loadPantryInteractor.execute();
    }
}
