package org.dbunit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class JUnit5TestCase {
    /**
     * the name of the test case
     */
    private String fName;

    /**
     * No-arg constructor to enable serialization. This method
     * is not intended to be used by mere mortals without calling setName().
     */
    public JUnit5TestCase() {
        fName = null;
    }

    /**
     * Constructs a test case with the given name.
     */
    public JUnit5TestCase(String name) {
        fName = name;
    }

    /**
     * Counts the number of test cases executed by run(JUnit5TestCase result).
     */
    public int countTestCases() {
        return 1;
    }

    /**
     * Creates a default JUnit5TestCase object.
     *
     * @see JUnit5TestCase
     */
    protected JUnit5TestCase createResult() {
        return new JUnit5TestCase();
    }

    /**
     * A convenience method to run this test, collecting the results with a
     * default JUnit5TestCase object.
     *
     * @see JUnit5TestCase
     */
    public JUnit5TestCase run() {
        JUnit5TestCase result = createResult();
        run(result);
        return result;
    }

    /**
     * Runs the test case and collects the results in JUnit5TestCase.
     */
    public void run(JUnit5TestCase result) {
        result.run(this);
    }

    /**
     * Runs the bare test sequence.
     *
     * @throws Throwable if any exception is thrown
     */
    public void runBare() throws Throwable {
        Throwable exception = null;
        setUp();
        try {
            runTest();
        } catch (Throwable running) {
            exception = running;
        } finally {
            try {
                tearDown();
            } catch (Throwable tearingDown) {
                if (exception == null) exception = tearingDown;
            }
        }
        if (exception != null) throw exception;
    }

    /**
     * Override to run the test and assert its state.
     *
     * @throws Throwable if any exception is thrown
     */
    protected void runTest() throws Throwable {
        assertNotNull("TestCase.fName cannot be null", fName); // Some VMs crash when calling getMethod(null,null);
        Method runMethod = null;
        try {
            // use getMethod to get all public inherited
            // methods. getDeclaredMethods returns all
            // methods of this class but excludes the
            // inherited ones.
            runMethod = getClass().getMethod(fName, (Class[]) null);
        } catch (NoSuchMethodException e) {
            fail("Method \"" + fName + "\" not found");
        }
        if (!Modifier.isPublic(runMethod.getModifiers())) {
            fail("Method \"" + fName + "\" should be public");
        }

        try {
            runMethod.invoke(this);
        } catch (InvocationTargetException e) {
            e.fillInStackTrace();
            throw e.getTargetException();
        } catch (IllegalAccessException e) {
            e.fillInStackTrace();
            throw e;
        }
    }

    /**
     * Sets up the fixture, for example, open a network connection.
     * This method is called before a test is executed.
     */
    protected void setUp() throws Exception {
    }

    /**
     * Tears down the fixture, for example, close a network connection.
     * This method is called after a test is executed.
     */
    protected void tearDown() throws Exception {
    }

    /**
     * Returns a string representation of the test case.
     */
    @Override
    public String toString() {
        return getName() + "(" + getClass().getName() + ")";
    }

    /**
     * Gets the name of a TestCase.
     *
     * @return the name of the TestCase
     */
    public String getName() {
        return fName;
    }

    /**
     * Sets the name of a TestCase.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        fName = name;
    }
}
