package feildmaster.PailPlus.Monitors;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import org.bukkit.scheduler.BukkitScheduler;

public class CPUMonitor {

    private static final ThreadMXBean thread = ManagementFactory.getThreadMXBean();
    private static final Runtime runtime = Runtime.getRuntime();
    private static final BukkitScheduler schedule = Util.getServer().getScheduler();

    public static long memoryUsed() {
        return runtime.totalMemory() - runtime.freeMemory();
    }
    public static long memoryTotal() {
        return runtime.totalMemory();
    }
    public static long memoryMax() {
        return runtime.maxMemory();
    }

    public static int threadsUsed() {
        return thread.getThreadCount();
    }

    public static int tasks() {
        return schedule.getPendingTasks().size()+schedule.getActiveWorkers().size();
    }
}
