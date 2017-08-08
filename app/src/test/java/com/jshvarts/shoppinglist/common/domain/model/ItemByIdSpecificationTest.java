package com.jshvarts.shoppinglist.common.domain.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Unit tests for {@link @ItemByIdSpecification}.
 */
public class ItemByIdSpecificationTest {

    private final static String TEST_ID = "id";

    ItemByIdSpecification testSubject;

    @Before
    public void setUp() throws Exception {
        testSubject = new ItemByIdSpecification(TEST_ID);
    }

    @Test
    public void getId() throws Exception {
        // WHEN
        String result = testSubject.getId();

        // THEN
        assertThat(result, is(TEST_ID));
    }
}