package server;

import java.util.ArrayList;

public class AuthService {
    private class Entry {
        private String login;
        private String password;

        public Entry(String login, String password) {
            this.login = login;
            this.password = password;
        }
    }

    private ArrayList<Entry> entry_Arr;

    public AuthService() {
        this.entry_Arr = new ArrayList<>();
        entry_Arr.add(new Entry("login1", "pass1"));
        entry_Arr.add(new Entry("login2", "pass2"));
        entry_Arr.add(new Entry("login3", "pass3"));
    }

    public String checkEntryInfo(String login, String password) {
        for (Entry x: entry_Arr) {
            if (x.login.equals(login) && x.password.equals(password)) {
                return "OK";
            }
        }
        return null;
    }
}
