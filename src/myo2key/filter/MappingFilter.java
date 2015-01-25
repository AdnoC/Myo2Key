package myo2key.filter;

import java.util.ArrayList;

/**
 * A class to build filters to use when searching Myo2KeyMappings
 *
 * @author Adam Cutler
 * @version 
 */
public class MappingFilter {

  /**
   * List of all parts that make up this filter.
   */
  private ArrayList<MappingFilterComponent> filterComponents;

  /**
   * Contructor.
   */
  public MappingFilter() {
    filterComponents = new ArrayList<MappingFilterComponent>();
  }
}
