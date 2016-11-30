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
     * One percent of these transactions must fail.
     */
    protected boolean failed = Helpers.getRandomBooleanWithProbability(1 / 100);

    public NewOrder() {
        // ...START THE TRANSACTION...
        this.startTransaction();

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

            this.doTimesCalculation();
            // ol_i_id = Helpers.getNonUniformRandomInteger(8191, 1, 100000);
            // ol_supply_w_id = w_id with probability of 1% otherwise choose randomly from other warehouses...
            // ol_quantity = Helpers.getRandomInteger(1, 10);
        }

        // ...FINISH THE TRANSACTION...
        this.succeedTransaction();
    }
}
