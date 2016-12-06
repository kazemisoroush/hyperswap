package benchmark;

abstract public class Runner {

    int iterations = 10;

    protected long start;

    protected long end;

    abstract long run();

    protected void startClock() {
        this.start = System.nanoTime();
    }

    protected void endClock() {
        this.end = System.nanoTime();
    }

    protected long elapsed() {
        return this.end - this.start;
    }
}
