package core.utils;

import core.annotations.JiraId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Objects;

public class TestListener implements ITestListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        var methodName = result.getMethod().getMethodName();
        LOGGER.info("Test Started: {}", methodName);
        var jiraIdAnnotation = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(JiraId.class);
        //if no annotation -> no Jira integration
        if(Objects.isNull(jiraIdAnnotation)){
            return;
        }
        var testCaseId = jiraIdAnnotation.value();
        JiraIntegration.updateTestStatus("Running", testCaseId, String.format("Test execution of the '%s' method is started.", methodName));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOGGER.info("Test Passed: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOGGER.error("Test Failed: {}", result.getMethod().getMethodName(), result.getThrowable());
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
}

