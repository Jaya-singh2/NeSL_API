package com.spring.nesl_api.payload.request;

public class LoanRequest {
    private String transId;
    private String loanno;


    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getLoanno() {
        return loanno;
    }

    public void setLoanno(String loanno) {
        this.loanno = loanno;
    }
}
