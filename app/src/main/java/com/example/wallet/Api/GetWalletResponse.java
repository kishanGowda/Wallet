package com.example.wallet.Api;

import java.util.ArrayList;


public class GetWalletResponse {
    public TotalLinksRequested totalLinksRequested;
    public TotalLinksPending totalLinksPending;
    public TotalLinksPaid totalLinksPaid;
    public TotalLinksSettled totalLinksSettled;
    public TotalLinksOverDue totalLinksOverDue;
    public TotalRefunded totalRefunded;
    public TotalLinksCancelled totalLinksCancelled;
    public TotalLinksViewed totalLinksViewed;
    public ArrayList<PendingTransaction> pendingTransactions;
    public ArrayList<PaidTransaction> paidTransactions;
    public boolean validGst;



}
