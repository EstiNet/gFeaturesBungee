package org.json.simple.parser;

import java.util.List;
import java.util.Map;

public abstract interface ContainerFactory
{
  public abstract Map createObjectContainer();
  
  public abstract List creatArrayContainer();
}


/* Location:              C:\json-simple-1.1.1.jar!\org\json\simple\parser\ContainerFactory.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */