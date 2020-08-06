package com.fantaike.framework.lang;

import java.io.Serializable;

/**
 * 片段
 */
public abstract class Section implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected Section next;
    
    public abstract String getValue();

    public Section getNext() {
        return next;
    }

    public void setNext(Section next) {
        this.next = next;
    }
    
    public boolean hasNext() {
        return this.next != null;
    }

    /**
     * 返回空section
     * @return header
     */
    public static Section emptyHeader() {
        return new Section() {
            private static final long serialVersionUID = 1L;
            @Override
            public String getValue() {
                return "";
            }
            @Override
            public String toString() {
                return "[Empty Section, value=\"\"]";
            }
        };
    }
    
}
