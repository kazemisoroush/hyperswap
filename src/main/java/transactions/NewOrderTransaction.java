package transactions;

import exceptions.IncorrectQueryExecution;
import main.Helpers;

import java.util.ArrayList;
import java.util.Calendar;

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
    protected int c_id = Helpers.getRandomInteger(1, 3);

    /**
     * The number of items in the order, is randomly selected within [5 .. 15].
     */
    protected int ol_cnt = Helpers.getRandomInteger(5, 15);

    /**
     * The order entry date.
     */
    protected String o_entry_d = new java.sql.Date((Calendar.getInstance()).getTimeInMillis()).toString();

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
            result = this.select("c_discount", "c_last", "c_credit").from("customer")
                         .where("c_w_id", "=", this.w_id + "").where("c_d_id", "=", this.d_id + "").where("c_id", "=", this.c_id + "").get();

            double c_discount = Double.parseDouble(result.get(0).get(0));
            String c_last = result.get(0).get(1);
            String c_credit = result.get(0).get(2);

            // insert into order table...
            int o_id = this.insert().into("orders")
                           .values("default", this.d_id + "", this.w_id + "", this.c_id + "", "'" + this.o_entry_d + "'", this.o_carrier_id + "", ol_cnt + "", "1")
                           .save();

            // insert into new_order table...
            this.insert().into("new_order")
                .values(o_id + "", this.d_id + "", this.w_id + "")
                .save();

            double amountSum = 0;

            // for each order_line count...
            for (int ol_number = 1; ol_number <= ol_cnt; ol_number++) {
                // item id is chosen with non-uniform random function...
                int i_id = Helpers.getNonUniformRandomInteger(8191, 1, 100000);

                // select one item from items table...
                result = this.select("i_price", "i_name", "i_data").from("item")
                             .where("i_id", "=", i_id + "").get();

                double i_price = Double.parseDouble(result.get(0).get(0));
                String i_name = result.get(0).get(1);
                String i_data = result.get(0).get(2);

                // make this according to the warehouse number...
                String s_dist_column = "s_dist_" + Helpers.parWithZero(this.d_id, 2);

                // select one item from stocks table...
                result = this.select("s_quantity", s_dist_column, "s_data")
                             .from("stock")
                             .where("s_i_id", "=", i_id + "").where("s_w_id", "=", this.w_id + "").get();

                int s_quantity = Integer.parseInt(result.get(0).get(0));
                String s_dist = result.get(0).get(1);
                String s_data = result.get(0).get(2);

                // order line quantity is randomly selected between [1 ... 10]...
                int ol_quantity = Helpers.getRandomInteger(1, 10);

                // do an update on stock...
                this.update("stock")
                    .set("s_order_cnt", "s_order_cnt + 1")
                    .set("s_ytd", "s_ytd + " + ol_quantity)
                    .where("s_i_id", "=", i_id + "").where("s_w_id", "=", this.w_id + "").save();

                // do this calculation...
                double ol_amount = ol_quantity * i_price * (1 + w_tax + d_tax) * (1 - c_discount);

                // search i_data and s_data for "original" string and fill in brand_generic...
                String brand_generic;

                if (i_data.toLowerCase().contains("original") && s_data.toLowerCase().contains("original")) {
                    brand_generic = "B";
                } else {
                    brand_generic = "G";
                }

                // insert into order line table...
                this.insert().into("order_line")
                    .values(o_id + "", this.d_id + "", this.w_id + "", ol_number + "", i_id + "", this.w_id + "", "null", ol_quantity + "", ol_amount + "", "'" + s_dist + "'")
                    .save();

                // sum the amounts...
                amountSum += ol_amount;
            }

            // calculate the total order amount...
            double totalOrderAnount = amountSum * (1 - c_discount) * (1 + w_tax + d_tax);

        } catch (IncorrectQueryExecution e) {
        }
    }

    /**
     * New order failed transaction.
     */
    protected void failTransaction() {
        try {
            this.select("w_tax")
                .from("warehouse")
                .where("w_id", "=", this.w_id + "").get();

            // select district with input district id...
            this.select("d_tax", "d_next_o_id").from("district")
                .where("d_id", "=", this.d_id + "").get();

            // select customer with input customer id...
            this.select("c_discount", "c_last", "c_credit").from("customer")
                .where("c_w_id", "=", this.w_id + "").where("c_d_id", "=", this.d_id + "").where("c_id", "=", this.c_id + "").get();

            // for each order_line count...
            for (int i = 1; i <= ol_cnt; i++) {
                // item id is chosen with non-uniform random function...
                int i_id = Helpers.getNonUniformRandomInteger(8191, 1, 100000);

                // select one item from items table...
                this.select("i_price", "i_name", "i_data").from("item")
                    .where("i_id", "=", i_id + "").get();
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
        double failureProbability = 0.01;

        // check if transaction is going to fail or not...
        return Helpers.getRandomBooleanWithProbability(failureProbability);
    }

    /**
     * Execute this transaction for testing purposes.
     *
     * @param arguments from console.
     */
    public static void main(String[] arguments) {
        NewOrderTransaction transaction = new NewOrderTransaction();

        transaction.process();
    }
}
