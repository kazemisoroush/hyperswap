package transactions;

public class TransactionalFunctions {

    /**
     * Actions required to select from a table.
     *
     * @param from            which we are selecting from.
     * @param columns         which are being selected.
     * @param whereConditions in the query.
     */
    protected void doSelect(String[] from, String[] columns, String[] whereConditions) {
        // ...
    }

    /**
     * Actions required to insert row into a table.
     *
     * @param into         which we are inserting into.
     * @param columnsCount which are being inserted.
     */
    protected void doInsert(String into, int columnsCount) {
        // ...
    }

    /**
     * Actions required to select from a table.
     *
     * @param from            which we are selecting from.
     * @param whereConditions which are being selected.
     */
    protected void doDelete(String from, String[] whereConditions) {
        // ...
    }

    /**
     * Actions required to update a row in table.
     *
     * @param from            which we are selecting from.
     * @param columns         which are being updated.
     * @param whereConditions in the query.
     */
    protected void doUpdate(String from, String[] columns, String[] whereConditions) {
        // ...
    }

    /**
     * Actions required to increment column in a table.
     *
     * @param from            which we are incrementing from.
     * @param columns         which are being incremented.
     * @param whereConditions in the query.
     */
    protected void doIncrement(String from, String[] columns, String[] whereConditions) {
        // ...
    }

    /**
     * Do a times / division calculation within transaction.
     *
     * @param numbersCount which are been calculated.
     */
    protected void doTimesCalculation(int numbersCount) {
        // ...
    }

    /**
     * Do a add / subtract calculation within transaction.
     *
     * @param numbersCount which are been calculated.
     */
    protected void doAddCalculation(int numbersCount) {
        // ...
    }

    /**
     * Do a string search.
     */
    protected void doStringSearch() {
        // ...
    }
}
