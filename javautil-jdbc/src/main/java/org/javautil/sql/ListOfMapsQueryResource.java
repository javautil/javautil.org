package org.javautil.sql;

import org.javautil.dataset.Dataset;
import org.javautil.dataset.ListOfMapsDataset;

/**
 * QueryResource that returns as a List of Maps.
 * 
 * Works well with GSON.
 * 
 * @author jjs@javautil.org
 */
public class ListOfMapsQueryResource extends  BaseQueryResourceImpl 
{


	@SuppressWarnings("unchecked")
	public ListOfMapsDataset getDataset() {
	
		final Dataset dataset = 	getResultSetDatasetInternal();
		ListOfMapsDataset listOfMaps = null;
		try {
			// the list of maps renders nicely in gson
			listOfMaps = new ListOfMapsDataset(dataset, dataset.getMetadata());
		} finally {
			if (dataset != null) {
				dataset.close();
			}
		}
		return listOfMaps;
	}

	

}
