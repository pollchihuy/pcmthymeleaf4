package edu.paul.pcmthymeleaf4.security;

import java.util.function.Function;

public class BcryptImpl {

    private static final BcryptCustom bcrypt = new BcryptCustom(11);

    public static String hash(String password) {
        return bcrypt.hash(password);
    }

    public static boolean verifyAndUpdateHash(String password,
                                              String hash,
                                              Function<String, Boolean> updateFunc) {
        return bcrypt.verifyAndUpdateHash(password, hash, updateFunc);
    }

    public static boolean verifyHash(String password , String hash)
    {
        return bcrypt.verifyHash(password,hash);
    }
    
    public static void main(String[] args) {
        String strUserName = "paul123Bagas@123";
        System.out.println(hash(strUserName));
        System.out.println(verifyHash("paul123Bagas@123","$2a$11$RXJm7NfQT1vELjPYSlaP.OhiCmQiU/8eAeJJ0Z9JqPGQPb4r/hPkO"));
    }
}