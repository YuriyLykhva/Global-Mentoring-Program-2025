package core.utils;

import core.annotations.JiraId;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;

public class JUnitCustomExtension implements Extension, BeforeEachCallback, AfterEachCallback {
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        var jiraAnnotation = context.getTestMethod().get().getAnnotation(JiraId.class);
        int a = 0;
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        var jiraAnnotation = context.getTestMethod().get().getAnnotation(JiraId.class);
        String testResult = context.getExecutionException().isPresent() ? "Failed" : "Passed";
        int a = 0;
    }
}
