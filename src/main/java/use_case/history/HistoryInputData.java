package use_case.history;

import java.time.LocalDate;

/**
 * Contains the date range requested by the user
 * for viewing pantry history.
 */
public class HistoryInputData {

  private final LocalDate fromDate;
  private final LocalDate toDate;

  public HistoryInputData(LocalDate fromDate, LocalDate toDate) {
    this.fromDate = fromDate;
    this.toDate = toDate;
  }

  public LocalDate getFromDate() {
    return fromDate;
  }

  public LocalDate getToDate() {
    return toDate;
  }
}