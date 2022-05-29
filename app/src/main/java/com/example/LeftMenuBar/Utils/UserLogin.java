package com.example.LeftMenuBar.Utils;

public class UserLogin {

    private static int loginID;
    private static String loginUsername;
    private static String loginPassword;
    private static String loginCountry;
    private static String loginNumber;
    private static String loginEmail;
    private static String loginFullname;
    public static final String User_is_login = "isSignedIn";

    String userLogin;
    String passLogin;
    String countryLogin;
    String numberLogin;
    String emailLogin;
    String fullnameLogin;



    public UserLogin() {

    }


    public static int getLoginID() { return loginID; }
    public static void setLoginID(int loginID) { UserLogin.loginID = loginID; }

    public static String getLoginUsername() {return loginUsername;}
    public static void setLoginUsername(String loginUsername) {UserLogin.loginUsername = loginUsername;}
    public static String getLoginPassword() { return loginPassword; }
    public static void setLoginPassword(String loginPassword) {UserLogin.loginPassword = loginPassword;}


    public static String getLoginCountry() { return loginCountry; }
    public static void setLoginCountry(String loginCountry) { UserLogin.loginCountry = loginCountry; }
    public static String getLoginNumber() { return loginNumber; }
    public static void setLoginNumber(String loginNumber) { UserLogin.loginNumber = loginNumber; }
    public static String getLoginEmail() { return loginEmail; }
    public static void setLoginEmail(String loginEmail) { UserLogin.loginEmail = loginEmail; }
    public static String getLoginFullname() { return loginFullname; }
    public static void setLoginFullname(String loginFullname) { UserLogin.loginFullname = loginFullname; }



    public UserLogin(String userLogin, String passLogin, String countryLogin, String numberLogin, String emailLogin, String fullnameLogin) {
        this.userLogin = userLogin;
        this.passLogin = passLogin;
        this.countryLogin = countryLogin;
        this.numberLogin = numberLogin;
        this.emailLogin = emailLogin;
        this.fullnameLogin = fullnameLogin;
    }
}
