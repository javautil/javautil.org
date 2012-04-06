package org.javautil.oracle.metadata;

import java.util.ArrayList;
import java.util.Collection;

public class DdSource {
    private ArrayList<String> lines;
    
    public void addLine(String line) {
	lines.add(line);
    }
    
    public Collection<String> getLines() {
	return lines;
    }
    
}
