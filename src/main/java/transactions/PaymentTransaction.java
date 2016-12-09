package transactions;

import exceptions.IncorrectQueryExecution;
import main.Helpers;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * The Payment business transaction updates the customer's balance and reflects the payment on the district and
 * warehouse sales statistics. It represents a light-weight, read-write transaction with a high frequency of execution
 * and stringent response time requirements to satisfy on-line users. In addition, this transaction includes non-primary
 * key access to the `customer` table.
 */
public class PaymentTransaction extends Transaction {

    /**
     * Warehouse number for this order. Equal to 1.
     */
    protected int w_id = 1;

    /**
     * District number for this order. Randomly selected from [1 ... 10].
     */
    protected int d_id = Helpers.getRandomInteger(1, 10);

    /**
     * The payment amount. Randomly selected from [1 ... 5000].
     */
    protected int h_amount = Helpers.getRandomInteger(1, 5000);

    /**
     * Customer identifier is selected using non-uniform random function.
     */
    protected int c_id = Helpers.getRandomInteger(1, 3);

    /**
     * The payment date.
     */
    protected String h_date = new java.sql.Date((Calendar.getInstance()).getTimeInMillis()).toString();

    /**
     * Actions needed to emulate the successful transaction.
     */
    protected void succeedTransaction() {
        ArrayList<ArrayList<String>> result;

        try {
            // select warehouse with input warehouse id...
            result = this.model.select("w_name", "w_street_1", "w_street_2", "w_city", "w_state", "w_zip")
                               .from("warehouse")
                               .where("w_id", "=", this.w_id + "").get();

            String w_name = result.get(0).get(0);
            String w_street_1 = result.get(0).get(1);
            String w_street_2 = result.get(0).get(2);
            String w_city = result.get(0).get(3);
            String w_state = result.get(0).get(4);
            String w_zip = result.get(0).get(5);

            // ytd is incremented by h_amount...
            this.model.update("warehouse")
                      .set("w_ytd", "w_ytd + " + this.h_amount)
                      .where("w_id", "=", this.w_id + "").save();

            // select district with input district id...
            result = this.model.select("d_name", "d_street_1", "d_street_2", "d_city", "d_state", "d_zip")
                               .from("district")
                               .where("d_id", "=", this.d_id + "")
                               .where("d_w_id", "=", this.w_id + "").get();

            // ytd is incremented by h_amount...
            this.model.update("district")
                      .set("d_ytd", "d_ytd + " + this.h_amount)
                      .where("d_id", "=", this.d_id + "")
                      .where("d_w_id", "=", this.w_id + "").save();

            String d_name = result.get(0).get(0);
            String d_street_1 = result.get(0).get(1);
            String d_street_2 = result.get(0).get(2);
            String d_city = result.get(0).get(3);
            String d_state = result.get(0).get(4);
            String d_zip = result.get(0).get(5);

            boolean selectCustomerById = Helpers.getRandomInteger(0, 100) > 60;

            if (selectCustomerById) {
                // 1. condition in which the customer is selected with customer number. 40% chance...
                // select customer with input customer id...
                result = this.model.select("c_first", "c_middle", "c_last", "c_street_1", "c_street_2", "c_city", "c_state", "c_zip", "c_phone", "c_since", "c_credit", "c_credit_lim", "c_discount", "c_balance")
                                   .from("customer")
                                   .where("c_w_id", "=", this.w_id + "")
                                   .where("c_d_id", "=", this.d_id + "")
                                   .where("c_id", "=", this.c_id + "").get();

            } else {
                // 2. the customer is selected with last name. 60% chance...
                // id or last name...!
                // no difference for this project...
                result = this.model.select("c_first", "c_middle", "c_last", "c_street_1", "c_street_2", "c_city", "c_state", "c_zip", "c_phone", "c_since", "c_credit", "c_credit_lim", "c_discount", "c_balance")
                                   .from("customer")
                                   .where("c_w_id", "=", this.w_id + "")
                                   .where("c_d_id", "=", this.d_id + "")
                                   .where("c_id", "=", this.c_id + "").get();
            }

            String c_credit = result.get(0).get(10);

            // do some updates for the customer...
            this.model.update("customer")
                      .set("c_balance", "c_balance - " + this.h_amount)
                      .set("c_ytd_payment", "c_ytd_payment + " + this.h_amount)
                      .set("c_payment_cnt", "c_payment_cnt + 1")
                      .where("c_w_id", "=", this.w_id + "")
                      .where("c_d_id", "=", this.d_id + "")
                      .where("c_id", "=", this.c_id + "").save();

            // fresh update needed in this condition...
            if ("BC".equals(c_credit)) {
                this.model.update("customer")
                          .set("c_data", "concat(c_data, 'bc')")
                          .where("c_w_id", "=", this.w_id + "")
                          .where("c_d_id", "=", this.d_id + "")
                          .where("c_id", "=", this.c_id + "").save();
            }

            String h_data = w_name + "    " + d_name;

            // insert the history...
            this.model.insert().into("history")
                      .values("default", this.d_id + "", this.w_id + "", this.d_id + "", this.w_id + "", "'" + h_date + "'", h_amount + "", "'" + h_data + "'")
                      .save();
        } catch (IncorrectQueryExecution e) {
            e.printStackTrace();
        }

    }

    /**
     * Actions needed to emulate the failed transaction.
     */
    protected void failTransaction() {
        // ...THIS TYPE OF TRANSACTION NEVER FAILS...
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
        PaymentTransaction transaction = new PaymentTransaction();

        transaction.process();
    }
}
