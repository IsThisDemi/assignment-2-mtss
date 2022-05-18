////////////////////////////////////////////////////////////////////
// [RICCARDO] [MARTINELLO] [2009086]
// [SAMUELE] [RIZZATO] [1226307]
////////////////////////////////////////////////////////////////////
package it.unipd.mtss.business;

import java.util.List;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.User;

public interface Bill {
    double getOrderPrice(List<EItem> itemsOrdered, User user)
        throws BillException;
    
}