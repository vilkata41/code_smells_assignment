package Couplers.FeatureEnvy;
// from https://elearning.industriallogic.com/gh/submit?Action=PageAction&album=recognizingSmells&path=recognizingSmells/featureEnvy/featureEnvyExample&devLanguage=Java
// Customer is performing repeated accesses on Phone's data to format the number

public class Customer{
private Phone mobilePhone;

public String getMobilePhoneNumber() {
        return "(" +
        mobilePhone.getAreaCode() + ") " +
        mobilePhone.getPrefix() + "-" +
        mobilePhone.getNumber();
        }

}
