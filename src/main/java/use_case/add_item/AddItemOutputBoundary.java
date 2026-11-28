package use_case.add_item;

public interface AddItemOutputBoundary {
    void prepareSuccessView();

    void prepareFailView(String errorMessage);
}
