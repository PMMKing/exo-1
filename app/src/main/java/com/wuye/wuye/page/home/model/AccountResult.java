package com.wuye.wuye.page.home.model;


import com.wuye.wuye.net.base.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/6/8.
 */

public class AccountResult extends BaseResult {

    public AccountData data;

    public static class AccountData implements Serializable {
        public double userBalance;
        public double userBalanceExp;
        public double accountBalance;
        public List<Card> xhkCard;
        public List<Card> bankCard;

        public static class Card implements Serializable {
            public String id;
            public String cardType;
            public String cardNum;
            public double cardMoney;
            public String cardBank;
            public double initMoney;
            public String userId;
            public int cardStat;
        }
    }
}
