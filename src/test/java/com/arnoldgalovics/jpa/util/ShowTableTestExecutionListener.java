package com.arnoldgalovics.jpa.util;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class ShowTableTestExecutionListener extends AbstractTestExecutionListener {
    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        TestHelper helper = testContext.getApplicationContext().getBean(TestHelper.class);
        helper.dumpTables();
    }

    @Override
    public int getOrder() {
        return 2;
    }
}