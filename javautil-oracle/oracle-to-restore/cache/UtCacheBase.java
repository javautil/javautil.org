package com.dbexperts.oracle.cache;


/**
Version:   $Id: UtCacheBase.java,v 1.1 2011/08/31 21:09:57 jjs Exp $
*/
/**
  * This class contains a temporal representation of the data persisted in a tuple of ut_cache
  * Persistence management is supported in class ...
  */
public class UtCacheBase
{




 // class attributes
   /** the container for the data persisted in OBJECT_NAME */
	private String objectName=null;               // varchar2(32)
   /** the container for the data persisted in CHANGE_TIME */
   private java.sql.Timestamp changeTime=null;               // date
   /** the container for the data persisted in CACHE_CHANGE_NBR */
   private Integer cacheChangeNbr=null;               // number(9,0)
    public Integer getCacheChangeNbr()
    {
        return cacheChangeNbr;
    }

    public java.sql.Timestamp getChangeTime()
    {
        return changeTime;
    }



    public String getObjectName()
    {
        return objectName;
    }

    public void setCacheChangeNbr(final Integer val)
    {
        cacheChangeNbr=val;
    }

    public void setChangeTime(final java.sql.Timestamp val)
    {
        changeTime=val;
    }



      public void setObjectName(final String val)
    {
        objectName=val;
    }

    public String toXml()
    {
       final StringBuffer buff = new StringBuffer();
       buff.append("<objectName>");
       buff.append(objectName);
       buff.append("</objectName>");
       buff.append("<changeTime>");
       buff.append(changeTime);
       buff.append("</changeTime>");
       buff.append("<cacheChangeNbr>");
       buff.append(cacheChangeNbr);
       buff.append("</cacheChangeNbr>");
        return new String(buff);
    }
}
