package Couplers.FeatureEnvy;
// from https://help.semmle.com/wiki/display/JAVA/Feature+envy
// Also a data class
public class Item {
    float price, tax, discount;
    boolean onSale;

    public float getPrice(){
        return price;
    }

    public float getSaleDiscount() {
        return discount;
    }

    public float getTax(){
        return tax;
    }

    public boolean isOnSale() {
        return onSale;
    }
}
