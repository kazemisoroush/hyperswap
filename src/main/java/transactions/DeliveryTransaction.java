package transactions;

import exceptions.IncorrectQueryExecution;
import main.Helpers;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * The Delivery business transaction consists of processing a batch of 10 new (not yet delivered) orders. Each order is
 * processed (delivered) in full within the scope of a read-write database transaction. The number of orders delivered
 * as a group (or batched) within the same database transaction is implementation specific. The business transaction,
 * comprised of one or more (up to 10) database transactions, has a low frequency of execution and must complete within
 * a relaxed response time requirement.
 */
public class DeliveryTransaction extends Transaction {

    /**
     * Warehouse number for this order. Equal to 1.
     */
    protected int w_id = 1;

    /**
     * Carrier id is randomly selected between [1 ... 10].
     */
    protected int o_carrier_id = Helpers.getRandomInteger(1, 10);

    /**
     * The order entry date.
     */
    protected String ol_delivery_d = new java.sql.Date((Calendar.getInstance()).getTimeInMillis()).toString();

    /**
     * Actions needed to emulate the successful transaction.
     */
    protected void succeedTransaction() {
        ArrayList<ArrayList<String>> result;

        try {
            for (int d_id = 1; d_id <= 10; d_id++) {
                // select row from new-order table...
                result = this.model.select("min(no_o_id) as min_o_id")
                                   .from("new_order")
                                   .where("no_w_id", "=", this.w_id + "")
                                   .where("no_d_id", "=", d_id + "").get();

                int min_o_id = Integer.parseInt(result.get(0).get(0));

                // remove the above row from new-order...
                this.model.delete().from("new_order")
                          .where("no_w_id", "=", this.w_id + "")
                          .where("no_d_id", "=", d_id + "").save();

                // select row from order table...
                this.model.select("o_c_id")
                          .from("order")
                          .where("o_w_id", "=", this.w_id + "")
                          .where("o_d_id", "=", d_id + "")
                          .where("o_id", "=", min_o_id + "")
                          .get();

                int o_c_id = Integer.parseInt(result.get(0).get(0));

                // update the carrier id...
                this.model.update("order")
                          .set("o_carrier_id", "o_carrier_id + 1")
                          .where("o_w_id", "=", this.w_id + "")
                          .where("o_d_id", "=", d_id + "")
                          .where("o_id", "=", min_o_id + "")
                          .save();

                // select rows from order line table...
                this.model.select("ol_delivery_d")
                          .from("order_line")
                          .where("ol_w_id", "=", this.w_id + "")
                          .where("ol_d_id", "=", d_id + "")
                          .where("ol_id", "=", min_o_id + "")
                          .get();

                // update delivery dates...
                this.model.update("order_line")
                          .set("ol_delivery_d", this.ol_delivery_d)
                          .where("ol_w_id", "=", this.w_id + "")
                          .where("ol_d_id", "=", d_id + "")
                          .where("ol_id", "=", min_o_id + "")
                          .get();

                // select a row from customer table...
                this.model.select("c_first", "c_middle", "c_last", "c_street_1", "c_street_2", "c_city", "c_state", "c_zip", "c_phone", "c_since", "c_credit", "c_credit_lim", "c_discount", "c_balance")
                          .from("customer")
                          .where("c_w_id", "=", this.w_id + "")
                          .where("c_d_id", "=", d_id + "")
                          .where("c_id", "=", o_c_id + "")
                          .save();

                this.model.update("customer")
                          .set("c_delivery_cnt", "c_delivery_cnt + 1")
                          .where("c_w_id", "=", this.w_id + "")
                          .where("c_d_id", "=", d_id + "")
                          .where("c_id", "=", o_c_id + "")
                          .save();
            }
        } catch (IncorrectQueryExecution e) {
            e.printStackTrace();
        }
    }

    /**
     * Actions needed to emulate the failed transaction.
     */
    protected void failTransaction() {
        // ...
    }

    /**
     * Method which determines if the running transaction is failing.
     *
     * @return boolean value of failed condition.
     */
    protected boolean isTransactionFailed() {
        return false;
    }

    /**
     * Execute this transaction for testing purposes.
     *
     * @param arguments from console.
     */
    public static void main(String[] arguments) {
        DeliveryTransaction transaction = new DeliveryTransaction();

        transaction.process();
    }
}
