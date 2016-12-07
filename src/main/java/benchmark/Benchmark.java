package benchmark;

import main.Helpers;
import transactions.*;

public class Benchmark extends Runner {

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
            // randomly select a transaction type...
            int transactionTypeDistribution = Helpers.getRandomNumberWithWeightedProbability(45, 43, 4, 4, 4);

            // 1. select a transaction type...
            Transaction transaction = new NewOrderTransaction();

            // 4. enter the required numbers in the input (keying time)...
            int keyingTime = 18;

            // decide which transaction to choose...
            switch (transactionTypeDistribution) {
                case 0:
                    // default transaction...
                    break;
                case 1:
                    transaction = new PaymentTransaction();
                    keyingTime = 3;
                    break;
                case 2:
                    transaction = new OrderStatusTransaction();
                    keyingTime = 2;
                    break;
                case 3:
                    transaction = new DeliveryTransaction();
                    keyingTime = 2;
                    break;
                case 4:
                    transaction = new StockLevelTransaction();
                    keyingTime = 2;
                    break;
            }

            // run the transaction...
            transaction.process();

            // 2. wait for the i/o screen to display...
            // ...WE DO NOT NEED THIS IN THIS PROJECT...

            // 3. measure the menu response time...
            // response time is measured as experienced by the emulated user...
            int menuResponseTime = 0;

            // 5. wait for the output i/o to display the required numbers...
            // ...WE DO NOT NEED THIS IN THIS PROJECT...

            // 6. measure the transaction response time...
            // response time is measured as experienced by the emulated user...
            long transactionResponseTime = transaction.elapsed();

            // 7. wait for the defined think time...
            double r = Helpers.getRandomNumber(0, 1);
            double thinkTime = Math.log(r) * (- 1) * mu;

            // mu value must be mean value for thinking time...
            if (iteration % 10 == 0) {
                // also mu must reset every 10 iterations...
                mu = 5;
            } else {
                // mu is the mean value of thinking time of user...
                mu = (mu * (iteration % 10 + 1) + thinkTime) / (iteration % 10 + 2);
            }

            // now calculate the total time...
            double totalResponseTime = keyingTime + menuResponseTime + transactionResponseTime + thinkTime;
        }

        // benchmark run finished so you can stop the clock..
        this.stopClock();

        // return time elapsed...
        return this.elapsed();
    }

}
