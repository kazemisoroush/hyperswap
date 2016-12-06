package analyzer;

public class PartitioningAnalyzer implements PerformanceAnalyzer {

    private String[] metrics;

    public PartitioningAnalyzer(String... metrics) {
        this.metrics = metrics;
    }

    public void analyze() {
    }
}
