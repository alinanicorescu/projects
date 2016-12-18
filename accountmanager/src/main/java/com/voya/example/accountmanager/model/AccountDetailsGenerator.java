package com.voya.example.accountmanager.model;

/**
 * Created by alinanicorescu on 18/12/2016.
 */
public final class AccountDetailsGenerator {

    public static String generateAccountCodeForUser(String username) {
        return username + "abc";
    }

    public static String generateAccountDescriptionForUser(String username) {
        return username + "desc";
    }
}
