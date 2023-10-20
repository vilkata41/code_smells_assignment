package Couplers.FeatureEnvy;
// from https://elearning.industriallogic.com/gh/submit?Action=PageAction&album=recognizingSmells&path=recognizingSmells/featureEnvy/featureEnvyExample&devLanguage=Java

public class Phone {
    private final String unformattedNumber;
    public Phone(String unformattedNumber) {
        this.unformattedNumber = unformattedNumber;
    }
    public String getAreaCode() {
        return unformattedNumber.substring(0,3);
    }
    public String getPrefix() {
        return unformattedNumber.substring(3,6);
    }
    public String getNumber() {
        return unformattedNumber.substring(6,10);
    }
}
