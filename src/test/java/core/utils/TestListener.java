package core.utils;

import core.annotations.JiraId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestListener.class);
    public SlackService slackService = new SlackService();

    @Override
    public void onTestStart(ITestResult result) {
        var methodName = result.getMethod().getMethodName();
        String testCaseId = getTestCaseId(result);
        LOGGER.info("Test Started: {}", methodName);
        JiraIntegration.updateTestStatus("Running", testCaseId, "");
        slackService.postNotification(String.format("Test execution of '%s' (JIRA: %s) started", methodName, testCaseId));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        var methodName = result.getMethod().getMethodName();
        String testCaseId = getTestCaseId(result);
        LOGGER.info("Test Passed: {}", methodName);
        JiraIntegration.updateTestStatus("Passed", testCaseId, "");
        slackService.postNotification(String.format("Test execution of '%s' (JIRA: %s) finished with result: %s", methodName, testCaseId, "Passed"));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        var methodName = result.getMethod().getMethodName();
        String testCaseId = getTestCaseId(result);
        LOGGER.error("Test Failed: {}", methodName, result.getThrowable());
        JiraIntegration.updateTestStatus("Failed", testCaseId, String.format("Test execution of the '%s' failed!", methodName));
        slackService.postNotification(String.format("Test execution of '%s' (JIRA: %s) finished with result: %s", methodName, testCaseId, "Failed"));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOGGER.warn("Test Skipped: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onStart(ITestContext context) {
        LOGGER.info("Test Execution Started: {}", context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        LOGGER.info("Test Execution Finished: {}", context.getName());
    }

    private static String getTestCaseId(ITestResult result) {
        var jiraIdAnnotation = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(JiraId.class);
        return jiraIdAnnotation != null ? jiraIdAnnotation.value() : "";
    }
}

