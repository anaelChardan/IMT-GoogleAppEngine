package com.zenika.zencontact.persistence;

import com.zenika.zencontact.domain.User;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public static List<User> USERS = new ArrayList<User>(10);

    static {

        USERS.add(User.create()
                .id(123L)
                .firstName("Bruce")
                .lastName("Wayne")
                .email("bruce@wayne.com")
                .password("bruce")
                .lastConnectionDate(DateTime.parse("2015-02-19T14:34:20.000+02:00").toDate()));
        USERS.add(User.create()
                .id(1L)
                .firstName("Peter")
                .lastName("Parker")
                .email("peter.parker@spider.com")
                .password("spider")
                .lastConnectionDate(DateTime.parse("2015-03-12T09:33").toDate()));
        USERS.add(User.create()
                .id(2L)
                .firstName("Tony")
                .lastName("Stark")
                .email("tony.stark@starkinternational.com")
                .password("iron")
                .lastConnectionDate(DateTime.parse("2015-04-04T09:33").toDate()));
    }
}
