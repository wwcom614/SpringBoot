package com.ww.base.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;

@Slf4j
public class EncryptUtil {

    public static void main(String[] args) {
        String userName = "ww2";
        String password = "123456";

        Md5Hash md5Hash = new Md5Hash(password, userName);
        log.info("【md5Hash】：{}",String.valueOf(md5Hash));
        //【md5Hash】：fd07753a3caf44588959f81ede2e2d7f

        Sha256Hash sha256Hash = new Sha256Hash(password, userName);
        log.info("【sha256Hash】：{}",String.valueOf(sha256Hash));
        //【sha256Hash】：6272dfad968a3b328bc43907dce57648d850e59400d32cda1130b0e87c594927
    }
}
