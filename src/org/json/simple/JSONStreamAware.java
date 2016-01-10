package org.json.simple;

import java.io.IOException;
import java.io.Writer;

public abstract interface JSONStreamAware
{
  public abstract void writeJSONString(Writer paramWriter)
    throws IOException;
}


/* Location:              C:\json-simple-1.1.1.jar!\org\json\simple\JSONStreamAware.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */