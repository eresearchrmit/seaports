/**
 * Copyright (c) 2013, RMIT University, Australia.
 * All rights reserved.
 * 
 * This code is under the BSD license. See 'license.txt' for details.
 * Project hosted at: https://code.google.com/p/climate-smart-seaports/
 */
package war.model;

/**
 * Enumeration representing the different user's role of the Climate Smart Seaport application
 * @author Guillaume Prevost
 */
public enum Role
{
    ADMIN_ROLE("ROLE_ADMIN", 1), 
    USER_ROLE("ROLE_USER", 0);

    private final String roleName;
    private final int order;

    /**
     * Constructor of an enumeration member
     * @param roleName: the name of the enumeration member
     * @param order: the order of the enumeration member
     */
    private Role(final String roleName, final int order)
    {
        this.roleName = roleName;
        this.order = order;
    }

    /**
     * Returns the order of the enumeration member
     * @return the order of the enumeration member
     */
    public int order()
    {
        return order;
    }

    /**
     * Returns the string representation of the enumeration member
     * @return the name of the enumeration member
     */
    public String roleName()
    {
        return roleName;
    }
}