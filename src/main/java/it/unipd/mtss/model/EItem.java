////////////////////////////////////////////////////////////////////
// [RICCARDO] [MARTINELLO] [2009086]
// [SAMUELE] [RIZZATO] [1226307]
////////////////////////////////////////////////////////////////////
package it.unipd.mtss.model;

public class EItem {
    ItemType itemType;
    String name;
    double price;

    public EItem(ItemType itemType, String name, double price){
        if(itemType == null){
            throw new IllegalArgumentException("Tipo non presente");
        }
        if(name == null){
            throw new IllegalArgumentException("Nome non presente");
        }
        if(price <= 0){
            throw new IllegalArgumentException("Prezzo non valido");
        }
        this.itemType = itemType;
        this.name = name;
        this.price = price;
    }

    public double getPrice(){
        return price;
    }

    public ItemType getType(){
        return itemType;
    }

    public String getName(){
        return name;
    }
}