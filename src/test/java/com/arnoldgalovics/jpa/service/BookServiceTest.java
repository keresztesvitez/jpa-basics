package com.arnoldgalovics.jpa.service;

import com.arnoldgalovics.jpa.util.CleanUpTestExecutionListener;
import com.arnoldgalovics.jpa.util.ShowTableTestExecutionListener;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners(value = {ShowTableTestExecutionListener.class, CleanUpTestExecutionListener.class}, mergeMode = MERGE_WITH_DEFAULTS)
public class BookServiceTest {

}