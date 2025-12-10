package customer;

public class CustomerSession {
    public static String customerID;

    public static void setCustomerID(String id) {
        customerID = id;
    }

    public static String getCustomerID() {
        return customerID;
    }

    public static void clear() {
        customerID = null;
    }
}
