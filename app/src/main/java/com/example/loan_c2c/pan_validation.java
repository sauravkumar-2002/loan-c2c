package com.example.loan_c2c;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class pan_validation {

    public static boolean isValidPanCardNo(String panCardNo)
    {
        String regex = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
        Pattern p = Pattern.compile(regex);


        if (panCardNo == null)
        {
            return false;
        }

        Matcher m = p.matcher(panCardNo);

        return m.matches();
    }

}
