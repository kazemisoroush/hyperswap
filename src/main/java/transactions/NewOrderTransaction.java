package transactions;

import exceptions.IncorrectQueryExecution;
import main.Helpers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * The New-Order business transaction consists of entering a complete order through a single database transaction. It
 * represents a mid-weight, read-write transaction with a high frequency of execution and stringent response time
 * requirements to satisfy on-line users. This transaction is the backbone of the workload. It is designed to place a
 * variable load on the system to reflect on-line database activity as typically found in production environments.
 */
public class NewOrderTransaction extends Transaction {

    /**
     * Warehouse number for this order. Equal to 1.
     */
    protected int w_id = 1;

    /**
     * District number for this order. Randomly selected from [1 ... 10].
     */
    protected int d_id = Helpers.getRandomInteger(1, 10);

    /**
     * Customer identifier is selected using non-uniform random function.
     */
    protected int c_id = Helpers.getNonUniformRandomInteger(1023, 1, 3000);

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
        ArrayList<ArrayList<String>> result;

        // select warehouse with input warehouse id...
        try {
            result = this.select("w_tax")
                         .from("warehouse")
                         .where("w_id", "=", this.w_id + "").get();

            double w_tax = Double.parseDouble(result.get(0).get(0));

            // select district with input district id...
            result = this.select("d_tax", "d_next_o_id").from("district")
                         .where("d_id", "=", this.d_id + "").get();

            double d_tax = Double.parseDouble(result.get(0).get(0));
            int d_next_o_id = Integer.parseInt(result.get(0).get(1));

            // select customer with input customer id...
            this.select("c_discount", "c_last", "c_credit").from("customer")
                .where("c_w_id", "=", this.w_id + "").where("c_d_id", "=", this.d_id + "").where("c_id", "=", this.c_id + "").get();

            double c_discount = Double.parseDouble(result.get(0).get(0));
            double c_last = Double.parseDouble(result.get(0).get(1));
            double c_credit = Double.parseDouble(result.get(0).get(2));

            // insert into order table...
            this.insert().into("order").values("").save();
            // this.doInsert("order", 8);

            // insert into new_order table...
            this.insert().into("new_order").values("").save();
            // this.doInsert("new_order", 3);

            // for each order_line count...
            for (int i = 1; i <= ol_cnt; i++) {
                // select one item from items table...
                this.select("i_price", "i_name", "i_data").from("item")
                    .where("i_id", "=", "").get();
                // this.doSelect(new String[]{"item"}, new String[]{"i_id"}, new String[]{"i_price", "i_name", "i_data"});

                // select one item from stocks table...
                this.select("s_quantity", "s_dist_xx", "s_data").from("stock")
                    .where("s_i_id", "=", "").where("s_w_id", "=", "").get();
                // this.doSelect(new String[]{"stocks"}, new String[]{"s_i_id", "s_w_id"}, new String[]{"s_quantity", "s_dist_xx", "s_data"});

                // do this calculation...
                // ol_amount = ol_quantity * i_price * (1 + w_tax + d_tax) * (1 - c_discount);
                // this.doAddCalculation(3); this.doTimesCalculation(4);

                // search i_data and s_data for "original" string and fill in brand_generic...
                // this.doStringSearch(); this.doStringSearch();

                // insert into order line table...
                this.insert().into("order_line").values("");
                // this.doInsert("order_line", 10);
            }
        } catch (IncorrectQueryExecution e) {
        }

    }

    /**
     * This type of transaction fails with probability of one percent.
     *
     * @return boolean condition of failure.
     */
    protected boolean isTransactionFailed() {
        // one percent chance...
        double failureProbability = 0;

        // check if transaction is going to fail or not...
        return Helpers.getRandomBooleanWithProbability(failureProbability);
    }

    /**
     * New order failed transaction.
     */
    protected void failTransaction() {
        // select warehouse with input warehouse id...
        this.doSelect(new String[]{"warehouse"}, new String[]{"w_tax"}, new String[]{"w_id"});

        // select district with input district id...
        this.doSelect(new String[]{"district"}, new String[]{"d_tax", "d_next_o_id"}, new String[]{"d_id"});

        // select customer with input customer id...
        this.doSelect(new String[]{"customer"}, new String[]{"c_discount", "c_last", "c_credit"}, new String[]{"c_w_id", "c_d_id", "c_id"});

        // insert into order table...
        this.doInsert("order", 8);

        // insert into new_order table...
        this.doInsert("new_order", 3);

        // for each order_line count...
        for (int i = 1; i <= ol_cnt; i++) {
            // select one item from items table...
            this.doSelect(new String[]{"item"}, new String[]{"i_id"}, new String[]{"i_price", "i_name", "i_data"});
        }
    }

    public static void main(String[] arguments) {
        NewOrderTransaction transaction = new NewOrderTransaction();

        transaction.process();
    }
}
