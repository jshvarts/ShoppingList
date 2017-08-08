package com.jshvarts.shoppinglist.common.domain.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Unit tests for {@link ItemsSpecification}.
 */
public class ItemsSpecificationTest {

    private final static int TEST_MAX_COUNT = 1;

    ItemsSpecification testSubject;

    @Before
    public void setUp() throws Exception {
        testSubject = new ItemsSpecification(TEST_MAX_COUNT);
    }

    @Test
    public void getMaxCount_returnsMaxCount() throws Exception {
        // WHEN
        int result = testSubject.getMaxCount();

        // THEN
        assertThat(result, is(TEST_MAX_COUNT));
    }
}