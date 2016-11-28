package seeder;

import database.DatabaseConnector;
import main.Helpers;
import main.Property;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;

/**
 * Loads database data into database table.
 */
public class Loader extends Property {

    /**
     * Make instance of database connector.
     */
    protected DatabaseConnector connector = new DatabaseConnector();

    /**
     * Database connection instance.
     */
    private final Connection connection;

    /**
     * Total number of customers per each district.
     */
    private int customers = 1; // 3000;

    /**
     * Initial number of warehouses in company.
     */
    private int warehouses = 1;

    /**
     * Total number of items in database.
     */
    private int items = 100000;

    /**
     * Total number of orders per each customer.
     */
    private int ordersPerCustomer = 1; // Helpers.getRandomInteger(1, 10);

    /**
     * Each regional warehouse covers 10 districts.
     */
    private int districtsPerWarehouse = 10;

    /**
     * Each district serves 3,000 customers.
     */
    private int customersPerDistrict = 3;

    /**
     * Histories per each customer.
     */
    private int historiesPerCustomer = Helpers.getRandomInteger(1, 10);

    /**
     * Number of order lines in each order.
     */
    private int orderLinesPerOrder = Helpers.getRandomInteger(5, 15);

    /**
     * Number of new orders per order.
     */
    private int newOrdersPerOrder = Helpers.getRandomInteger(0, 1);

    /**
     * Create new instance of loader.
     */
    public Loader() {
        // initialSeed properties...
        super();

        this.connection = this.connector.connect();
    }

    /**
     * Seed the database with initial data.
     */
    public void initialSeed() {
        // load the items...
        this.loadItems();

        // load the warehouses...
        this.loadWarehouses();

        // relate all items with all warehouses...
        this.loadStocks();
    }

    /**
     * Load items in database.
     */
    private void loadItems() {
        // create items...
        for (int i_id = 1; i_id <= this.items; i_id++) {
            // fill in the fields...
            int i_im_id = Helpers.getRandomInteger(1, 10000);
            String i_name = Helpers.getRandomStringAlphabetic(14, 24);
            double i_price = Helpers.getRandomNumber(1, 100);
            String i_data = Helpers.getRandomStringWithOriginal(26, 50, 0.1);

            // insert item...
            connector.insert("insert into item values (" + i_id + ", " + i_im_id + ", '" + i_name + "', " + i_price + ", '" + i_data + "')");
        }

        System.out.println("Items loaded...");
    }

    /**
     * Load warehouses in database.
     */
    private void loadWarehouses() {
        double w_ytd = 3000000;

        // create warehouses...
        for (int w_id = 1; w_id <= this.warehouses; w_id++) {
            // fill in the fields...
            String w_name = Helpers.getRandomStringAlphabetic(6, 10);
            String w_street_1 = Helpers.getRandomStringAlphabetic(10, 20);
            String w_street_2 = Helpers.getRandomStringAlphabetic(10, 20);
            String w_city = Helpers.getRandomStringAlphabetic(10, 20);
            String w_state = Helpers.getRandomStringAlphabetic(2, 2);
            String w_zip = Helpers.getRandomStringAlphabetic(9, 9);
            double w_tax = Helpers.getRandomNumber(0, 0.2);

            // insert warehouse...
            connector.insert("insert into warehouse values (" + w_id + ", '" + w_name + "', '" + w_street_1 + "', '" + w_street_2 + "', '" + w_city + "', '" + w_state + "', '" + w_zip + "', " + w_tax + ", " + w_ytd + ")");

            // load districts for this warehouse...
            this.loadDistricts(w_id);
        }

        System.out.println("Warehouses loaded...");
    }

    /**
     * Relate all warehouses to all items initially.
     */
    public void loadStocks() {
        int s_ytd = 0;
        int s_order_cnt = 0;
        int s_remote_cnt = 0;

        // create stock...
        for (int s_i_id = 1; s_i_id <= this.items; s_i_id++) {
            // fill in the fields...
            for (int s_w_id = 1; s_w_id <= this.warehouses; s_w_id++) {
                int s_quantity = Helpers.getRandomInteger(10, 100);
                String s_dist_01 = Helpers.getRandomStringAlphabetic(24, 24);
                String s_dist_02 = Helpers.getRandomStringAlphabetic(24, 24);
                String s_dist_03 = Helpers.getRandomStringAlphabetic(24, 24);
                String s_dist_04 = Helpers.getRandomStringAlphabetic(24, 24);
                String s_dist_05 = Helpers.getRandomStringAlphabetic(24, 24);
                String s_dist_06 = Helpers.getRandomStringAlphabetic(24, 24);
                String s_dist_07 = Helpers.getRandomStringAlphabetic(24, 24);
                String s_dist_08 = Helpers.getRandomStringAlphabetic(24, 24);
                String s_dist_09 = Helpers.getRandomStringAlphabetic(24, 24);
                String s_dist_10 = Helpers.getRandomStringAlphabetic(24, 24);
                String s_data = Helpers.getRandomStringWithOriginal(26, 50, 0.1);

                // insert stock...
                connector.insert("insert into stock values (" +
                        s_i_id + ", " +
                        s_w_id + ", " +
                        s_quantity + ", " +
                        "'" + s_dist_01 + "', " +
                        "'" + s_dist_02 + "', " +
                        "'" + s_dist_03 + "', " +
                        "'" + s_dist_04 + "', " +
                        "'" + s_dist_05 + "', " +
                        "'" + s_dist_06 + "', " +
                        "'" + s_dist_07 + "', " +
                        "'" + s_dist_08 + "', " +
                        "'" + s_dist_09 + "', " +
                        "'" + s_dist_10 + "', " +
                        s_ytd + ", " +
                        s_order_cnt + ", " +
                        s_remote_cnt + ", " +
                        "'" + s_data + "'" +
                        ")");
            }
        }

        System.out.println("Stocks loaded...");
    }

    /**
     * Load districts for this warehouse.
     *
     * @param d_w_id warehouse id for these districts.
     */
    public void loadDistricts(int d_w_id) {
        double d_ytd = 30000;
        int d_next_o_id = 3001;

        // create districts...
        for (int d_id = 1; d_id <= this.districtsPerWarehouse; d_id++) {
            // fill in the fields...
            String d_name = Helpers.getRandomStringAlphabetic(6, 10);
            String d_street_1 = Helpers.getRandomStringAlphabetic(10, 20);
            String d_street_2 = Helpers.getRandomStringAlphabetic(10, 20);
            String d_city = Helpers.getRandomStringAlphabetic(10, 20);
            String d_state = Helpers.getRandomStringAlphabetic(2, 2);
            String d_zip = Helpers.getRandomStringAlphabetic(9, 9);
            double d_tax = Helpers.getRandomNumber(0.1, 0.2);

            // insert districts...
            connector.insert("insert into district values (" +
                    d_id + ", " +
                    d_w_id + ", " +
                    "'" + d_name + "', " +
                    "'" + d_street_1 + "', " +
                    "'" + d_street_2 + "', " +
                    "'" + d_city + "', " +
                    "'" + d_state + "', " +
                    "'" + d_zip + "', " +
                    d_tax + ", " +
                    d_ytd + ", " +
                    d_next_o_id +
                    ")");

            // load customers of this district...
            this.loadCustomers(d_id, d_w_id);
        }

        System.out.println("Districts loaded...");
    }

    /**
     * Load customers into database.
     *
     * @param c_d_id district id for these customers.
     * @param c_w_id warehouse id for these customers.
     */
    public void loadCustomers(int c_d_id, int c_w_id) {
        String c_middle = "OE";
        int c_credit_lim = 50000;
        double c_balance = - 10;

        // create customers...
        for (int c_id = 1; c_id <= this.customersPerDistrict; c_id++) {
            // fill in the fields...
            String c_first = Helpers.getRandomStringAlphabetic(8, 16);
            // get last name by feeding a non-uniform random number in [0 .. 999] range...
            String c_last = Helpers.getCustomerLastName(Helpers.getNonUniformRandomInteger(255, 0, 999));
            String c_street_1 = Helpers.getRandomStringAlphabetic(10, 20);
            String c_street_2 = Helpers.getRandomStringAlphabetic(10, 20);
            String c_city = Helpers.getRandomStringAlphabetic(10, 20);
            String c_state = Helpers.getRandomStringAlphabetic(2, 2);
            String c_zip = Helpers.getRandomStringAlphabetic(9, 9);
            String c_phone = Helpers.getRandomStringNumeric(16, 16);
            Date c_since = null;
            String c_credit = Helpers.getRandomCredit(0.5);
            double c_discount = Helpers.getRandomNumber(0, 0.5);
            double c_ytd_payment = 10.0;
            int c_payment_cnt = 1;
            int c_delivery_cnt = 0;
            String c_data = Helpers.getRandomStringWithOriginal(300, 500, 0.1);

            // insert the customer...
            connector.insert("insert into customer values (" +
                    c_id + ", " +
                    c_d_id + ", " +
                    c_w_id + ", " +
                    "'" + c_first + "', " +
                    "'" + c_middle + "', " +
                    "'" + c_last + "', " +
                    "'" + c_street_1 + "', " +
                    "'" + c_street_2 + "', " +
                    "'" + c_city + "', " +
                    "'" + c_state + "', " +
                    "'" + c_zip + "', " +
                    "'" + c_phone + "', " +
                    c_since + ", " +
                    "'" + c_credit + "', " +
                    c_credit_lim + ", " +
                    c_discount + ", " +
                    c_balance + ", " +
                    c_ytd_payment + ", " +
                    c_payment_cnt + ", " +
                    c_delivery_cnt + ", " +
                    "'" + c_data + "'" +
                    ")");

            // load orders for this customer...
            this.loadOrders(c_id, c_d_id, c_w_id);

            // load histories for this customer...
            this.loadHistories(c_id, c_d_id, c_w_id);
        }

        System.out.println("Customers loaded...");
    }

    /**
     * Load orders related to a customer.
     *
     * @param o_c_id integer of customer id.
     * @param o_d_id integer of district id.
     * @param o_w_id integer of warehouse id.
     */
    public void loadOrders(int o_c_id, int o_d_id, int o_w_id) {
        // create orders...
        for (int o_id = 1; o_id <= this.ordersPerCustomer; o_id++) {
            // fill in the fields...
            Calendar calendar = Calendar.getInstance();
            Date o_entry_d = new java.sql.Date(calendar.getTimeInMillis());

            int o_carrier_id = Helpers.getRandomInteger(1, 10);
            int o_ol_cnt = Helpers.getRandomInteger(5, 15);
            int o_all_local = 1;

            // insert orders...
            int order_id = connector.insert("insert into orders values (" +
                    "default, " + // o_id + ", " +
                    o_d_id + ", " +
                    o_w_id + ", " +
                    o_c_id + ", " +
                    "'" + o_entry_d + "', " +
                    o_carrier_id + ", " +
                    o_ol_cnt + ", " +
                    o_all_local +
                    ")");

            // load order lines for this order...
            this.loadOrderLines(order_id, o_d_id, o_w_id);

            // load new orders...
            this.loadNewOrders(order_id, o_d_id, o_w_id);
        }

        System.out.println("Orders loaded for customer...");
    }

    /**
     * Load histories for the customer.
     *
     * @param h_c_id customer id for this history.
     * @param h_d_id district id for this history.
     * @param h_w_id warehouse id for this history.
     */
    private void loadHistories(int h_c_id, int h_d_id, int h_w_id) {
        // create customer histories...
        for (int h_id = 1; h_id <= this.historiesPerCustomer; h_id++) {
            // fill in the fields...
            Calendar calendar = Calendar.getInstance();
            Date h_date = new java.sql.Date(calendar.getTimeInMillis());

            double h_amount = 10;
            String h_data = Helpers.getRandomStringAlphabetic(12, 24);

            // insert history...
            connector.insert("insert into history values (" +
                    "default, " + // h_c_id + ", " +
                    h_d_id + ", " +
                    h_w_id + ", " +
                    h_d_id + ", " +
                    h_w_id + ", " +
                    "'" + h_date + "', " +
                    h_amount + ", " +
                    "'" + h_data + "' " +
                    ")");
        }

        System.out.println("Histories loaded for customer...");
    }

    /**
     * Load order lines for specific order.
     *
     * @param ol_o_id order id for this order line.
     * @param ol_d_id district id for this order line.
     * @param ol_w_id warehouse id for this order line.
     */
    private void loadOrderLines(int ol_o_id, int ol_d_id, int ol_w_id) {
        // create order lines...
        for (int ol_id = 1; ol_id <= this.orderLinesPerOrder; ol_id++) {
            // fill in the fields...
            // int ol_number = ol_id;
            int ol_i_id = Helpers.getRandomInteger(1, this.items);
            int ol_supply_w_id = ol_w_id;
            String ol_delivery_d = null;
            int ol_quantity = 5;
            double ol_amount = 0;
            String ol_dist_info = Helpers.getRandomStringAlphabetic(24, 24);

            // insert order line...
            connector.insert("insert into order_line values (" +
                    ol_o_id + ", " +
                    ol_d_id + ", " +
                    ol_w_id + ", " +
                    ol_id + ", " +
                    ol_i_id + ", " +
                    ol_supply_w_id + ", " +
                    ol_delivery_d + ", " +
                    ol_quantity + ", " +
                    ol_amount + ", " +
                    "'" + ol_dist_info + "'" +
                    ")");
        }

        System.out.println("Order lines loaded for each order...");
    }

    /**
     * Load new order for this specific order.
     *
     * @param no_o_id order id for this order line.
     * @param no_d_id district id for this order line.
     * @param no_w_id warehouse id for this order line.
     */
    private void loadNewOrders(int no_o_id, int no_d_id, int no_w_id) {
        // create new orders...
        for (int ol_id = 1; ol_id <= this.newOrdersPerOrder; ol_id++) {
            // insert new order...
            connector.insert("insert into new_orders values (" +
                    no_o_id + ", " +
                    no_d_id + ", " +
                    no_w_id +
                    ")");
        }

        System.out.println("New orders loaded for each order...");
    }
}
