package com.h2m.carassistance.Utitls;


public class RegularExpression {

    public static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]{1,30}+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]{3,10}+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$";

    public static final String PASSWORD_PATTERN =
            "((?=.*[a-zA-Z0-9]).{5,40})";

    //public static final String NAME_PATTERN = "^[a-zA-Z \\-\\.\']*$";
    //public static final String NAME_PATTERN = "^[A-Za-z0-9-]{3,30}$+(\\.[_A-Za-z0-9-]+)*@{0,30}";
    public static final String NAME_PATTERN = "[a-zA-Z0-9 ]+";

    public static final String Phone_PATERN ="^[0-9]{11,11}$";
}
