package feildmaster.AdvancedPail.Monitors;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class CPUMonitor {

    private static final ThreadMXBean thread = ManagementFactory.getThreadMXBean();
    private static final Runtime runtime = Runtime.getRuntime();

    public static long memoryUsed() {
        return runtime.totalMemory() - runtime.freeMemory();
    }

    public static long memoryTotal() {
        return runtime.totalMemory();
    }

    public static int threadsUsed() {
        return thread.getThreadCount();
    }

    public static long memoryMax() {
        return runtime.maxMemory();
    }
}
