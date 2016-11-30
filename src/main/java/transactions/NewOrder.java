package transactions;

import main.Helpers;

import java.util.Calendar;
import java.util.Date;

/**
 * The New-Order business transaction consists of entering a complete order through a single database transaction. It
 * represents a mid-weight, read-write transaction with a high frequency of execution and stringent response time
 * requirements to satisfy on-line users. This transaction is the backbone of the workload. It is designed to place a
 * variable load on the system to reflect on-line database activity as typically found in production environments.
 */
public class NewOrder extends Transaction {

    /**
     * Probability of transaction to be failed.
     */
    protected double failureProbability = 0.01;

    /**
     * Warehouse number for this order. Equal to 1.
     */
    protected int w_id = 1;

    /**
     * District number for this order. Randomly selected from [1 ... 10].
     */
    protected int d_id = Helpers.getRandomInteger(1, 10);

    /**
     * The number of items in the order, is randomly selected within [5 .. 15].
     */
    protected int ol_cnt = Helpers.getRandomInteger(5, 15);

    /**
     * The order entry date.
     */
    protected Date o_entry_d = new java.sql.Date((Calendar.getInstance()).getTimeInMillis());

    /**
     * This variable is set to a null value.
     */
    protected int o_carrier_id = 0;

    /**
     * If the order includes only home order-lines, then this variable is set to 1, otherwise set to 0.
     */
    protected int o_all_local = 1;

    /**
     * New order successful transaction.
     */
    protected void succeedTransaction() {
        // select warehouse with input warehouse id...
        this.doSelect("warehouse", new String[]{"w_tax"}, new String[]{"w_id"});

        // select district with input district id...
        this.doSelect("district", new String[]{"d_tax", "d_next_o_id"}, new String[]{"d_id"});

        // select customer with input customer id...
        this.doSelect("customer", new String[]{"c_discount", "c_last", "c_credit"}, new String[]{"c_w_id", "c_d_id", "c_id"});

        // insert into order table...
        this.doInsert("order", 8);

        // insert into new_order table...
        this.doInsert("new_orders", 3);

        // for each order_line count...
        for (int i = 1; i <= ol_cnt; i++) {
            // select one item from items table...
            this.doSelect("item", new String[]{"i_id"}, new String[]{"i_price", "i_name", "i_data"});

            // select one item from stocks table...
            this.doSelect("stocks", new String[]{"s_i_id", "s_w_id"}, new String[]{"s_quantity", "s_dist_xx", "s_data"});

            // calculate ol_quantity * i_price...
            this.doTimesCalculation(2);

            // search i_data and s_data for "original" string and fill in brand_generic...
            this.doStringSearch();
            this.doStringSearch();

            // insert into order line table...
            this.doInsert("order_line", 10);
        }
    }

    /**
     * New order failed transaction.
     */
    protected void failTransaction() {

    }
}
