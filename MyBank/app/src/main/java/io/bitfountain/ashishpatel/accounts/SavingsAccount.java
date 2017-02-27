package io.bitfountain.ashishpatel.accounts;

/**
 * Created by ashishpatel on 2015-12-28.
 */

import android.util.Log;

import io.bitfountain.ashishpatel.accounts.BankAccount;

public class SavingsAccount extends BankAccount {

    private static final String TAG = "SavingsAccount";

    @Override
    public void withdraw(double amount) {
        if (numberOfWithdrawals() >= 3) {
            return;
        }

        //call on the super class
        super.withdraw(amount);
        Log.d(TAG, "withdrawing from savings");
    }
}
