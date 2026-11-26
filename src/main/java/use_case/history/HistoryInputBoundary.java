package use_case.history;

/**
 * Defines the input boundary for the history use case.
 */
public interface HistoryInputBoundary {

  /**
   * Retrieves pantry history for the requested date range.
   *
   * @param inputData the requested date range
   */
  void execute(HistoryInputData inputData);

}
