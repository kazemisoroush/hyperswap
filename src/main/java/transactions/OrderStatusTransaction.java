package transactions;

import exceptions.IncorrectQueryExecution;
import main.Helpers;

import java.util.ArrayList;

/**
 * The Order-Status business transaction queries the status of a customer's last order. It represents a mid -weight
 * read-only database transaction with a low frequency of execution and response time requirement to satisfy on-line
 * users. In addition, this table includes non-primary key access to the `customer` table.
 */
public class OrderStatusTransaction extends Transaction {

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
     * Actions needed to emulate the successful transaction.
     */
    protected void succeedTransaction() {
        ArrayList<ArrayList<String>> result;

        try {
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

            // select one row from orders table...
            result = this.model.select("o_id", "o_entry_d", "o_carrier_id")
                               .from("orders")
                               .where("o_w_id", "=", this.w_id + "")
                               .where("o_d_id", "=", this.d_id + "")
                               .where("o_id", "=", this.c_id + "").get();

            int o_id = Integer.parseInt(result.get(0).get(0));

            // select rows from order-line table...
            result = this.model.select("ol_i_id", "ol_supply_w_id", "ol_quantity", "ol_amount", "ol_delivery_d")
                               .from("order_line")
                               .where("ol_w_id", "=", this.w_id + "")
                               .where("ol_d_id", "=", this.d_id + "")
                               .where("ol_o_id", "=", o_id + "").get();

        } catch (
                IncorrectQueryExecution e) {
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
        OrderStatusTransaction transaction = new OrderStatusTransaction();

        transaction.process();
    }
}
