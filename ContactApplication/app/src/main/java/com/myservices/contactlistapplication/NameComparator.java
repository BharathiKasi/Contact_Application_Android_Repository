package com.myservices.contactlistapplication;

import java.util.Comparator;

public class NameComparator implements Comparator<ContactListPojo> {
    @Override
    public int compare(ContactListPojo contactListPojo, ContactListPojo t1) {
        return contactListPojo.getName().compareTo(t1.getName());

    }
}
