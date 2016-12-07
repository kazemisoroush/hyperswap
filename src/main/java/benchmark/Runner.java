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
     * Instantiate new instance of benchmark runner.
     */
    public Runner() {
    }

    /**
     * Instantiate new instance of benchmark runner.
     *
     * @param iterations to run the runner.
     */
    public Runner(int iterations) {
        this.iterations = iterations;
    }

    /**
     * Each benchmark must have a run method.
     *
     * @return time elapsed for benchmark to run.
     */
    abstract long run();

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
     * @return value of time elapsed.
     */
    protected long elapsed() {
        return this.end - this.start;
    }
}
