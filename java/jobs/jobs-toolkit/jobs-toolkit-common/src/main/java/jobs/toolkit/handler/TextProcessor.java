package jobs.toolkit.handler;

/**
 * Created by jobs on 2015/8/26.
 */
public interface TextProcessor<T> {
    public T process(String text, Object ...args);
}
