package myo2key.filter;

/**
 * A filter for MyoPriorities.
 * Can filter for: Higher/lower/equal priority Id. Equal priority Id and higher/lower/equal priority
 * value.
 *
 * @author Adam Cutler
 * @version 
 */
public class MappingPriorityFilter implements MappingFilterComponent {

  /**
   * How to compare the priority Id and Value.
   */
  protected CompareType pIdComparison, pValComparison;

  /**
   * The value of the priority Id and Value to compare to.
   */
  protected int pIdValue, pValValue;

  /**
   * An enum to define how two integers are compared.
   */
  public enum CompareType {
    LESS_THAN(-1), EQUAL_TO(0), GREATER_THAN(1), NO_COMPARISON(10);

    private int compareTypeVal;
    CompareType(int v) {
      compareTypeVal = v;
    }
    public boolean compare(int val1, int val2) {
      if(compareTypeVal != 10) {
        return compareTypeVal == Integer.compare(val1, val2);
      } else {
        return true;
      }
    }
  }

  /**
   * Constructor with filters for both priority id and value.
   * @param idC The type of comparison to use with the priority id.
   * @param idV The value of priority id to be compared to.
   * @param valC The type of comparison to use with the priority value.
   * @param valV The value of priority value to be compared to.
   */
  public MappingPriorityFilter(CompareType idC, int idV, CompareType valC, int valV) {
    pIdComparison = idC;
    pIdValue = idV;
    pValComparison = valC;
    pValValue = valV;
  }

  /**
   * Constructor with filters for only priority id.
   * @param idC The type of comparison to use with the priority id.
   * @param idV The value of priority id to be compared to.
   */
  public MappingPriorityFilter(CompareType idC, int idV) {
    this(idC, idV, CompareType.NO_COMPARISON, 0);
  }

  /**
   * Set how this filters priority id.
   * @param ct The type of comparison to use with the priority id.
   * @param val The value of priority id to be compared to.
   */
  public void setpIdComparison(CompareType ct, int val) {
    pIdComparison = ct;
    pIdValue = val;
  }

  /**
   * Set how this filters priority value.
   * @param ct The type of comparison to use with the priority value.
   * @param val The value of priority value to be compared to.
   */
  public void setpValComparison(CompareType ct, int val) {
    pValComparison = ct;
    pValValue = val;
  }

  /**
   * Checks whether or not a mapping passes this filter.
   * @param map The mapping to check.
   * @return Whether or not the mapping passes.
   */
  public boolean checkFilter(Myo2KeyMapping map) {
    MyoMapPriority pri = map.getPriority();
    return pIdComparison.compare(pri.getId(), pIdValue) && 
      pValComparison.compare(pri.getValue(), pValValue);
  }


}
