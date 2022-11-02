package org.example;

public class Main {
    public static void main(String[] args) {

        RegForm form = new RegForm(null);
        User user = form.user;
        if (user != null) {
            System.out.println("Registration of " + user.name + "successful ");
        } else {
            System.out.println("Registration conceled");
        }
    }
}