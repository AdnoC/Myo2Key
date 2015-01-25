package myo2key.filter;
import myo2key.Myo2KeyMapping;

/**
 * An interface representing part of a filter. Multiple of them can be combined to create
 * complex filters.
 *
 * @author Adam Cutler
 * @version 
 */
public interface MappingFilterComponent {

  /**
   * Checks a mapping to see if it passes this filter
   * @param map The mapping to check
   * @return 
   */
  public boolean checkFilter(Myo2KeyMapping map);
}
