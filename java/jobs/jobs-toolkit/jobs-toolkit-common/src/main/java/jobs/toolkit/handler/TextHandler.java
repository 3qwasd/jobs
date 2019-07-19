package jobs.toolkit.handler;

/**
 * Created by jobs on 2015/8/26.
 */
@FunctionalInterface
public interface TextHandler {
    public void handle(String text, int lineNum);
}
