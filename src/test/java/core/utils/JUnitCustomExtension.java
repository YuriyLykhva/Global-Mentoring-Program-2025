package core.utils;

import core.annotations.JiraId;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class JUnitCustomExtension implements Extension, BeforeEachCallback, AfterEachCallback {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestListener.class);
    public SlackService slackService = new SlackService();

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Method testMethod = context.getRequiredTestMethod();
        String methodName = testMethod.getName();
        var jiraAnnotation = testMethod.getAnnotation(JiraId.class);
        String testCaseId = jiraAnnotation != null ? jiraAnnotation.value() : "";
        LOGGER.info("Test Started: {}", methodName);
        JiraIntegration.updateTestStatus("Running", testCaseId, "");
        slackService.postNotification(String.format("Test execution of '%s' (JIRA: %s) started", methodName, testCaseId));
    }


    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        Method testMethod = context.getRequiredTestMethod();
        String methodName = testMethod.getName();
        var jiraAnnotation = testMethod.getAnnotation(JiraId.class);
        String testCaseId = jiraAnnotation != null ? jiraAnnotation.value() : "";
        String testResult = context.getExecutionException().isPresent() ? "Failed" : "Passed";
        if (testResult.equals("Passed")) {
            LOGGER.info("Test Passed: {}", methodName);
            JiraIntegration.updateTestStatus("Passed", testCaseId, "");
        } else {
            LOGGER.error("Test Failed: {}", testResult);
            JiraIntegration.updateTestStatus("Failed", testCaseId, String.format("Test execution of the '%s' failed!", methodName));
        }
        slackService.postNotification(String.format("Test execution of '%s' (JIRA: %s) finished with result: %s", methodName, testCaseId, testResult));
    }
}
