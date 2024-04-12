/*
 *  Copyright 2012 Unicommerce Technologies (P) Limited . All Rights Reserved.
 *  UNICOMMERCE TECHONOLOGIES PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *  @version     1.0, Mar 4, 2012
 *  @author singla
 */
package com.dawnbit.common.core.fileparser;

import java.util.Iterator;

/**
 * @author singla
 */
public interface FileParser {
    /**
     * Parses the file and provides an Iterator over rows in the file
     *
     * @return Iterator<Row> iterate over the lines of the file
     */
    public Iterator<Row> parse();
}
