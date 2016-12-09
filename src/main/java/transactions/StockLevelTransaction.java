package transactions;

import exceptions.IncorrectQueryExecution;
import main.Helpers;

import java.util.ArrayList;

/**
 * The Stock-Level business transaction determines the number of recently sold items that have a stock level below a
 * specified threshold. It represents a heavy read-only database transaction with a low frequency of execution, a
 * relaxed response time requirement, and relaxed consistency requirements.
 */
public class StockLevelTransaction extends Transaction {

    /**
     * Warehouse number for this order. Equal to 1.
     */
    protected int w_id = 1;

    /**
     * District number for this order. Randomly selected from [1 ... 10].
     */
    protected int d_id = Helpers.getRandomInteger(1, 10);

    /**
     * Threshold for stock quantity.
     */
    protected int low_stock = 0;

    /**
     * Actions needed to emulate the successful transaction.
     */
    protected void succeedTransaction() {
        ArrayList<ArrayList<String>> result;

        try {
            result = this.model.select("d_next_o_id")
                               .from("district")
                               .where("d_w_id", "=", this.w_id + "")
                               .where("d_id", "=", this.d_id + "").get();

            int d_next_o_id = Integer.parseInt(result.get(0).get(0));

            // result = this.model.select("ol_i_id")
            // .from("order_line")
            // .where("ol_w_id", "=", this.w_id + "")
            // .where("ol_d_id", "=", this.d_id + "")
            // .where("ol_o_id", "<", d_next_o_id + "")
            // .where("ol_o_id", ">=", (d_next_o_id - 20) + "").get();

            // int ol_i_id = Integer.parseInt(result.get(0).get(0));

            // result = this.model.select("*")
            // .from("stock")
            // .where("s_i_id", "=", ol_i_id + "")
            // .where("s_w_id", "=", this.w_id + "")
            // .where("s_quantity", "<", this.low_stock + "").get();

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
        StockLevelTransaction transaction = new StockLevelTransaction();

        transaction.process();
    }
}
