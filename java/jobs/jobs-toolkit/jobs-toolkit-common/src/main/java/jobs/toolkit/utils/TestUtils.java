package jobs.toolkit.utils;

public class TestUtils {
	
	public static void showMemoryStatus(){
		Runtime rt = Runtime.getRuntime();
		long total = rt.totalMemory();
		long free = rt.freeMemory();
		long max = rt.maxMemory();
		System.out.println(String.format("The memory status: total=%dM; free=%dM; max=%dM; use=%dM", total/1024/1024, free/1024/1024, max/1024/1024, (total-free)/1024/1024));
	}
}
