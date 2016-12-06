package benchmark;

import main.Helpers;
import transactions.*;

public class TrainBenchmark extends Runner {

    /**
     * Run the training benchmark to make the transactional logs.
     *
     * @return time elapsed for running the benchmark.
     */
    public long run() {
        // start the clock...
        this.startClock();

        // mean value for think time...
        double mu = 5;

        // first run the benchmark and extract the transaction logs ...
        for (int iteration = 0; iteration <= this.iterations; iteration++) {
            // 1. select a transaction type...
            int numberOfTransaction = Helpers.getRandomNumberWithWeightedProbability(45, 43, 4, 4, 4);
            Transaction transaction;

            switch (numberOfTransaction) {
                case 0:
                    transaction = new NewOrderTransaction();
                    break;
                case 1:
                    transaction = new PaymentTransaction();
                    break;
                case 2:
                    transaction = new OrderStatusTransaction();
                    break;
                case 3:
                    transaction = new DeliveryTransaction();
                    break;
                case 4:
                    transaction = new StockLevelTransaction();
                    break;
                default:
                    transaction = new NewOrderTransaction();
            }

            transaction.process();

            // 2. TODO: wait for the i/o screen to display...
            // 3. TODO: measure the menu response time...
            // 4. TODO: enter the required numbers in the input...
            // 5. TODO: wait for the output i/o to display the required numbers...
            // 6. TODO: measure the transaction response time...

            // 7. wait for the defined think time...
            double r = Helpers.getRandomNumber(0, 1);
            double thinkTime = Math.log(r) * (- 1) * mu;

            if (iteration % 10 == 0) {
                mu = 5;
            } else {
                // mu is the mean value of thinking time of user...
                mu = (mu * (iteration % 10 + 1) + thinkTime) / (iteration % 10 + 2);
            }
        }

        // benchmark run finished so you can stop the clock..
        this.endClock();

        // return time elapsed...
        return this.elapsed();
    }

}
