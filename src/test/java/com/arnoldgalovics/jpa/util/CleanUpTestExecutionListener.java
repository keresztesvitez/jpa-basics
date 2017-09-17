package com.arnoldgalovics.jpa.util;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class CleanUpTestExecutionListener extends AbstractTestExecutionListener {
    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        TestHelper helper = testContext.getApplicationContext().getBean(TestHelper.class);
        helper.cleanUp();
    }

    @Override
    public int getOrder() {
        return 1;
    }
}