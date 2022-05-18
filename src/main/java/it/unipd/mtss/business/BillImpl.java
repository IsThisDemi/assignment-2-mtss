////////////////////////////////////////////////////////////////////
// [RICCARDO] [MARTINELLO] [2009086]
// [SAMUELE] [RIZZATO] [1226307]
////////////////////////////////////////////////////////////////////
package it.unipd.mtss.business;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.ItemType;
import it.unipd.mtss.model.User;
import java.util.List;

public class BillImpl implements Bill{
    
    @Override
    public double getOrderPrice(List<EItem> itemsOrdered, User user) throws BillException {
        double total = 0;
        int processori = 0;
        EItem ProcessoreMenoCostoso = null;
        
        if(itemsOrdered == null) {
            throw new BillException("Lista nulla");
        }

        if(itemsOrdered.isEmpty()) {
            throw new BillException("Lista ordini vuota");
        }
        
        //Calcolo prezzo totale
        for (EItem item : itemsOrdered) {
            total += item.getPrice();
            if (item.getType() == ItemType.Processor) {
                processori++;

                if ((ProcessoreMenoCostoso == null) || (ProcessoreMenoCostoso.getPrice() > item.getPrice())) {
                    ProcessoreMenoCostoso = item;
                }
            }
        }

        // sconto se più di 5 processori
        if (processori > 5) {
            total -= ProcessoreMenoCostoso.getPrice() * 0.5;
        }
        
        return total;
    }
}