package net.formula97.springapp.skeltonapp;

import org.junit.Rule;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Value;

public class BaseTestCase {

    /**
     * Connection port during testing.
     */
    @Value("${local.server.port}")
    protected int port;

    /**
     * Mock object Rule.
     */
    @Rule
    public final MockitoRule mockRule = MockitoJUnit.rule();

}
