/*
 * Stratio Meta
 *
 * Copyright (c) 2014, Stratio, All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */

package com.stratio.meta.deep.functions;

import java.io.Serializable;

import org.apache.spark.api.java.function.Function;

import com.stratio.deep.entity.Cells;

public class Between extends Function<Cells, Boolean> implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -4498262312538738011L;

    /**
     * Name of the field of the cell to compare.
     */
    private String field;

    /**
     * Lower bound
     */
    private Object lowerBound;

    /**
     * Upper bound
     */
    private Object upperBound;

    /**
     * In apply in filter to a field in a Deep Cell.
     * 
     * @param field
     *            Name of the field to check.
     * @param inIDs
     *            List of values of the IN clause.
     */
    public Between(String field, Object lowerBound, Object upperBound) {
        this.field = field;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Boolean call(Cells cells) {

        Boolean isValid = false;
        Object cellValue = cells.getCellByName(field).getCellValue();

        if (cellValue != null) {
            isValid = (((Comparable) lowerBound).compareTo(cellValue) <= 0)
                    && (((Comparable) upperBound).compareTo(cellValue) >= 0);
        }

        return isValid;
    }
}
