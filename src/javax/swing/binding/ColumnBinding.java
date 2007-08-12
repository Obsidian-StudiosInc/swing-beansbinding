/*
 * Copyright (C) 2007 Sun Microsystems, Inc. All rights reserved. Use is
 * subject to license terms.
 */

package javax.swing.binding;

import javax.beans.binding.*;

/**
 * @author Shannon Hickey
 */
class ColumnBinding<E, ET> extends Binding<E, ET, Object, Object> {

    private int column;

    public ColumnBinding(String name, int column, Property<E, ET> columnSource, Property columnTarget) {
        super(null, columnSource, null, columnTarget);
        this.column = column;
    }
    
    int getColumn() {
        return column;
    }
    
    void setColumn(int column) {
        this.column = column;
    }

}