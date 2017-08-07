package com.wuye.wuye.page.login.model;


import com.wuye.wuye.net.base.BaseResult;

import java.io.Serializable;

/**
 * Created by shucheng.qu on 2017/6/5.
 */

public class LoginResult extends BaseResult {

    public LoginData data;

    public static class LoginData implements Serializable{
        public String id;
        public String userName;
        public String userPasswd;
        public String userPhone;
        public String passPortid;
        public long issueDate;
        public long effDate;
        public long expDate;
        public long leaveDate;
        public String userCertid;
        public int userBalance;
        public int userBalanceExp;
        public String payPasswd;
    }
}
