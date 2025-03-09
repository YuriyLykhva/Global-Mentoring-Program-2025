package core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Test Started: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test Passed: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test Failed: {}", result.getMethod().getMethodName(), result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Test Skipped: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("Test Execution Started: {}", context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Test Execution Finished: {}", context.getName());
    }
}

