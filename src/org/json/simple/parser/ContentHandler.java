package org.json.simple.parser;

import java.io.IOException;

public abstract interface ContentHandler
{
  public abstract void startJSON()
    throws ParseException, IOException;
  
  public abstract void endJSON()
    throws ParseException, IOException;
  
  public abstract boolean startObject()
    throws ParseException, IOException;
  
  public abstract boolean endObject()
    throws ParseException, IOException;
  
  public abstract boolean startObjectEntry(String paramString)
    throws ParseException, IOException;
  
  public abstract boolean endObjectEntry()
    throws ParseException, IOException;
  
  public abstract boolean startArray()
    throws ParseException, IOException;
  
  public abstract boolean endArray()
    throws ParseException, IOException;
  
  public abstract boolean primitive(Object paramObject)
    throws ParseException, IOException;
}


/* Location:              C:\json-simple-1.1.1.jar!\org\json\simple\parser\ContentHandler.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */