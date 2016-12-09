package benchmark;

abstract public class Runner {

    /**
     * Number of iterations for benchmark to run.
     */
    int iterations = 10;

    /**
     * Start time of benchmark run.
     */
    protected long start;

    /**
     * End time of benchmark run.
     */
    protected long end;

    /**
     * Each benchmark must have a run method.
     *
     * @return time elapsed for benchmark to run.
     */
    abstract double run();

    /**
     * Start the clock for the benchmark. Must be called before running the benchmark.
     */
    protected void startClock() {
        this.start = System.nanoTime();
    }

    /**
     * Stop the clock for the benchmark. Must be called after running the benchmark.
     */
    protected void stopClock() {
        this.end = System.nanoTime();
    }

    /**
     * Calculate the time elapsed for the benchmark running process.
     *
     * @return value of time elapsed in seconds.
     */
    protected double elapsed() {
        return (this.end - this.start) * Math.pow(10, - 9);
    }
}
