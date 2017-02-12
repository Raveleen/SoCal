package com.raveleen.entities.enums;

/**
 * Created by Святослав on 02.01.2017.
 */
public enum UserRole {
    ADMIN, USER, MODERATOR;

    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}
