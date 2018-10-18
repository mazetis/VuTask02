package lt.vu.mif.jate.tasks.task02.base;

import java.math.BigInteger;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Base test class.
 * @author valdo
 */
public interface TestBase {

    public static final BigInteger ID = BigInteger.ONE;
    public static final String TITLE = "some title";
    public static final String SUBTITLE = "some subtitle";
    public static final String DESCRIPTION = "some description";
    public static final String CATEGORY1 = "category1";
    public static final String CATEGORY2 = "category2";
    public static final String PUBLISHER = "some publisher";
    public static final String AUTHOR1 = "some author";
    public static final String AUTHOR2 = "another author";
    public static final int PAGES = 123;
    public static final double INITIAL_RATING = 3.0;
    public static final int INITIAL_RATING_COUNT = 5;
    
    public final static String BOOKS_RESOURCE = "/lt/vu/mif/jate/tasks/task02/data/books01.dat";
    public final static String MOVIES_RESOURCE = "/lt/vu/mif/jate/tasks/task02/data/movies01.dat";
    
    public static final String BOOK_JSON = "{\n" +
                                            "  \"publisher\": \"MIT Press\", \n" +
                                            "  \"isbn\": \"9780262680820\", \n" +
                                            "  \"description\": \"This text provides a step-by-step introduction to the entire field of computer music techniques. Written for nontechnical as well as technical readers, it uses hundreds of charts, diagrams, screen images, and photographs as well as clear explanations to present basic concepts and terms.\", \n" +
                                            "  \"publishedDate\": \"1996-01\", \n" +
                                            "  \"title\": \"The Computer Music Tutorial\", \n" +
                                            "  \"pageCount\": 1234, \n" +
                                            "  \"authors\": [\n" +
                                            "   \"Curtis Roads\"\n" +
                                            "  ], \n" +
                                            "  \"ratingsCount\": 19, \n" +
                                            "  \"categories\": [\n" +
                                            "   \"Art\"\n" +
                                            "  ], \n" +
                                            "  \"averageRating\": 4.5\n" +
                                            " }";
    
    public static final String MOVIE_JSON = "{\n" +
                                            "  \"customerRating\": \"5.0\", \n" +
                                            "  \"name\": \"Joy Ride (Widescreen)\", \n" +
                                            "  \"numReviews\": 3, \n" +
                                            "  \"longDescription\": \"It's all fun and games whene two brothers (Paul Walker and Steve Zahn) take off cross-country to bring home a pretty college friend (Leelee Sobieski). But the jokes end when a prank backfires and they find themselves stalked by a vengeful trucker who won't give up his relentless chase until somebody pays with their life.&lt;br&gt;&lt;br&gt; Alternate Ending, Audio Commentary, Deleted Scenes, Storyboard Comparisons, Trailers.&lt;br&gt;&lt;br&gt; &quot;Rusty Nail&quot; Audio.\", \n" +
                                            "  \"categoryPath\": \"Movies/Action & Adventure/Art\", \n" +
                                            "  \"id\": 10793915\n" +
                                            " }";
    
    public default String randString(int len) {
        return RandomStringUtils.randomAlphabetic(len).toLowerCase();
    }

    public default boolean randBoolean() {
        return new Random().nextBoolean();
    }
    
}
