import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Ashim Dhakal, Szcheng Chen
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /*
     * Test cases for constructors
     */

    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.constructorTest();
        Map<String, String> mExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /*
     * Test cases for kernel methods
     */

    @Test
    public final void testAddEmpty() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.constructorTest();
        Map<String, String> mExpected = this.createFromArgsRef("red", "red");
        /*
         * Call method under test
         */
        m.add("red", "red");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddNonEmptyOne() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("blue", "blue");
        Map<String, String> mExpected = this.createFromArgsRef("red", "red",
                "blue", "blue");
        /*
         * Call method under test
         */
        m.add("red", "red");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddNonEmptyMoreThanOne() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("blue", "blue", "green",
                "green");
        Map<String, String> mExpected = this.createFromArgsRef("red", "red",
                "blue", "blue", "green", "green");
        /*
         * Call method under test
         */
        m.add("red", "red");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveLeavingEmpty() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("red", "red");
        Map<String, String> mExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        Pair<String, String> x = m.remove("red");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals("red", x.key());
        assertEquals("red", x.value());
    }

    @Test
    public final void testRemoveLeavingNonEmptyOne() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("red", "red", "blue",
                "blue");
        Map<String, String> mExpected = this.createFromArgsRef("blue", "blue");
        /*
         * Call method under test
         */
        Pair<String, String> x = m.remove("red");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals("red", x.key());
        assertEquals("red", x.value());
    }

    @Test
    public final void testRemoveLeavingNonEmptyMoreThanOne() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("red", "red", "blue",
                "blue", "green", "green");
        Map<String, String> mExpected = this.createFromArgsRef("blue", "blue",
                "green", "green");
        /*
         * Call method under test
         */
        Pair<String, String> x = m.remove("red");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals("red", x.key());
        assertEquals("red", x.value());
    }

    @Test
    public final void testRemoveAny() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("red", "red", "green",
                "green", "blue", "blue");
        Map<String, String> mExpected = this.createFromArgsRef("red", "red",
                "green", "green", "blue", "blue");

        /*
         * Call method under test
         */
        Pair<String, String> pair = m.removeAny();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, mExpected.hasKey(pair.key()));
        mExpected.remove(pair.key());
        assertEquals(m, mExpected);
    }

    @Test
    public final void testHasKey() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("red", "red", "blue",
                "blue");
        Map<String, String> mExpected = this.createFromArgsRef("red", "red",
                "blue", "blue");

        /*
         * Call method under test
         */
        boolean contains = m.hasKey("red");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(contains, true);
    }

    @Test
    public final void testHasNoKey() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("red", "red", "blue",
                "blue");
        Map<String, String> mExpected = this.createFromArgsRef("red", "red",
                "blue", "blue");

        /*
         * Call method under test
         */
        boolean contains = m.hasKey("yellow");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(contains, false);
    }

    @Test
    public final void testSizeEmpty() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        int i = m.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(0, i);
    }

    @Test
    public final void testSizeNonEmptyOne() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("red", "red");
        Map<String, String> mExpected = this.createFromArgsRef("red", "red");
        /*
         * Call method under test
         */
        int i = m.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(1, i);
    }

    @Test
    public final void testSizeNonEmptyMoreThanOne() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("red", "red", "blue",
                "blue");
        Map<String, String> mExpected = this.createFromArgsRef("red", "red",
                "blue", "blue");
        /*
         * Call method under test
         */
        int i = m.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(2, i);
    }

}
