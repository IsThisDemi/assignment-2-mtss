////////////////////////////////////////////////////////////////////
// [RICCARDO] [MARTINELLO] [2009086]
// [SAMUELE] [RIZZATO] [1226307]
////////////////////////////////////////////////////////////////////
package it.unipd.mtss.business.exception;

public class BillException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public BillException(String msg) {
        super(msg);
    }
}