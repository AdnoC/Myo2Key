package myo2key.filter;
import myo2key.Myo2KeyMapping;
import myo2key.MyoMapSource;

public class MappingSourceFilter implements MappingFilterComponent {
  protected Class<? extends MyoMapSource> filterClass;

  public MappingSourceFilter(Class<? extends MyoMapSource> cl) {
    filterClass = cl;
  }

  public MappingSourceFilter() {
    this(MyoMapSource.class);
  }

  public void setClass(Class<? extends MyoMapSource> cl) {
    filterClass = cl;
  }

  public boolean checkFilter(Myo2KeyMapping map) {
    for(MyoMapSource mms : map.getTriggers()) {
      if(filterClass.isInstance(mms)) {
        return true;
      }
    }
    return false;
  }
}
