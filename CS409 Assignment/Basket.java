package Couplers.FeatureEnvy;
// from https://help.semmle.com/wiki/display/JAVA/Feature+envy
public class Basket {
        // Basket is envious of item
        public float getTotalPrice(Item i) {
            float price = i.getPrice() + i.getTax();
            if (i.isOnSale())
                price = price - i.getSaleDiscount() * price;
            return price;
        }
}
