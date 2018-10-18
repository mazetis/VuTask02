package lt.vu.mif.jate.tasks.task02.store;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import lt.vu.mif.jate.tasks.task02.search.SearchManager;
import lt.vu.mif.jate.tasks.task02.store.model.Book;
import lt.vu.mif.jate.tasks.task02.store.model.Item;
import lt.vu.mif.jate.tasks.task02.store.model.Movie;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Top level domain store class.
 *
 * @author valdo
 */
@Getter
public class Store extends SearchManager<Item> {

    /**
     * List of fields used in store
     */
    public static final String TITLE_FIELD = "TITLE";
    public static final String SUBTITLE_FIELD = "SUBTITLE";
    public static final String DESCRIPTION_FIELD = "DESCRIPTION";
    public static final String CATEGORY_FIELD = "CATEGORY";
    public static final String AUTHOR_FIELD = "AUTHOR";
    public static final String PUBLISHER_FIELD = "PUBLISHER";
    public final Collection<Item> items = new HashSet<>();

    /**
     * Load objects from the input stream.
     *
     * @param is input stream.
     */
    public final void load(final InputStream is) {
        BufferedReader buf;
        List<String> matches = new ArrayList<>();
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        buf = new BufferedReader(isr);
        String str;
        Pattern regex = Pattern.compile("\\{(.*?)\\}");
        StringBuilder strbuilder = new StringBuilder();

        try {
            while ((str = buf.readLine()) != null) {
                strbuilder.append(str);
            }
        } catch (IOException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        }

        Matcher m = regex.matcher(strbuilder);
        while (m.find()) {
            matches.add(m.group(1));
        }

        //Read book details
        if (matches.get(0).contains("isbn")) {
            Pattern title
                    = Pattern.compile("\"title\": \"(.*?)\"");
            Pattern isbn
                    = Pattern.compile("\"isbn\": \"(.*?)\"\\,\\s\\s");
            Pattern description
                    = Pattern.compile("\"description\": \"(.*?)\"\\,\\s\\s");
            Pattern publisher
                    = Pattern.compile("\"publisher\": \"(.*?)\"\\,\\s\\s");
            Pattern subtitle
                    = Pattern.compile("\"subtitle\": \"(.*?)\"\\,\\s\\s");
            Pattern ratingsCount
                    = Pattern.compile("\"ratingsCount\":\\s(.*?)\\,");
            Pattern pageCount
                    = Pattern.compile("\"pageCount\":\\s(.*?)\\,");
            Pattern averageRating
                    = Pattern.compile("\"averageRating\":\\s(.*?)(?:\\,|\\s)");
            Pattern authors
                    = Pattern.compile("\"authors\":\\s\\[\\s\\s\\s(.*?)\\s\\s\\](?:\\,|\\s)");
            Pattern categories
                    = Pattern.compile("\"categories\":\\s\\[\\s\\s\\s(.*?)\\s\\](?:\\,|\\s)");
            Pattern inCategories
                    = Pattern.compile("\"(.*?)\"");

            for (String match : matches) {
                Matcher titl = title.matcher(match);
                Matcher isb = isbn.matcher(match);
                Matcher des = description.matcher(match);
                Matcher pub = publisher.matcher(match);
                Matcher sub = subtitle.matcher(match);
                Matcher rc = ratingsCount.matcher(match);
                Matcher pc = pageCount.matcher(match);
                Matcher ar = averageRating.matcher(match);
                Matcher auth = authors.matcher(match);
                Matcher cat = categories.matcher(match);

                String titleAdd = null;
                BigInteger isbnAdd = BigInteger.ZERO;
                String descAdd = null;
                String pubAdd = null;
                String subtitleAdd = null;
                Integer rcAdd = null;
                Integer pcAdd = null;
                Double arAdd = null;
                String catAdd = null;
                List<String> authorsList = new ArrayList<>();

                if (titl.find()) {
                    titleAdd = StringEscapeUtils.unescapeJava(titl.group(1));
                }
                if (isb.find()) {
                    String isbnString = isb.group(1);
                    isbnAdd = new BigInteger(isbnString);
                }
                if (des.find()) {
                    descAdd = StringEscapeUtils.unescapeJava(des.group(1));
                }
                if (pub.find()) {
                    pubAdd = StringEscapeUtils.unescapeJava(pub.group(1));
                }
                if (sub.find()) {
                    subtitleAdd = StringEscapeUtils.unescapeJava(sub.group(1));
                }
                if (rc.find()) {
                    String rcString = rc.group(1);
                    rcAdd = Integer.parseInt(rcString);
                }
                if (pc.find()) {
                    String pcString = pc.group(1);
                    pcAdd = Integer.parseInt(pcString);
                }
                if (ar.find()) {
                    String arString = ar.group(1);
                    arAdd = Double.parseDouble(arString);
                }
                if (cat.find()) {
                    catAdd = StringEscapeUtils.unescapeJava(cat.group(1));
                }
                while (auth.find()) {
                    String foundAuthors = auth.group(1);
                    Matcher ic = inCategories.matcher(foundAuthors);
                    while (ic.find()) {
                        authorsList.add(StringEscapeUtils.unescapeJava(ic.group(1)));
                    }
                }

                //Create Book Object
                Book.Builder builder = new Book.Builder(isbnAdd, titleAdd, descAdd);
                builder.publisher(pubAdd);
                builder.pages(pcAdd);
                builder.subtitle(subtitleAdd);
                if (arAdd != null && rcAdd != null) {
                    builder.rating(arAdd, rcAdd);
                }
                if (catAdd != null) {
                    builder.category(catAdd.trim()
                            .substring(1, catAdd.trim().length() - 1));
                }
                for (String authorAdd : authorsList) {
                    builder.author(authorAdd);
                }
                Book book = builder.build();
                getSearchItems().add(book);
            }
        } else {

            //Read Movie details
            Pattern movieId
                    = Pattern.compile("\"id\":\\s(.*?)(?:\\,|\\s)");
            Pattern name
                    = Pattern.compile("\"name\": \"(.*?)\"");
            Pattern longDesc
                    = Pattern.compile("\"longDescription\": \"(.*?)\"\\,\\s\\s");
            Pattern catPath
                    = Pattern.compile("\"categoryPath\": \"(.*?)\"");
            Pattern custRating
                    = Pattern.compile("\"customerRating\": \"(.*?)\"");
            Pattern numReviews
                    = Pattern.compile("\"numReviews\":\\s(.*?)(?:\\,|\\s)");

            for (String match : matches) {
                Matcher id = movieId.matcher(match);
                Matcher nam = name.matcher(match);
                Matcher ld = longDesc.matcher(match);
                Matcher cp = catPath.matcher(match);
                Matcher cr = custRating.matcher(match);
                Matcher nr = numReviews.matcher(match);

                BigInteger idAdd = BigInteger.ZERO;
                String namAdd = null;
                String ldAdd = null;
                List<String> categoriesAdd = new ArrayList<>();
                Double crAdd = null;
                Integer nrAdd = null;
                if (nam.find()) {
                    namAdd = StringEscapeUtils.unescapeJava(nam.group(1));
                }
                if (id.find()) {
                    String idString = id.group(1);
                    idAdd = new BigInteger(idString);
                }
                if (ld.find()) {
                    ldAdd = StringEscapeUtils.unescapeJava(ld.group(1));
                }
                if (cp.find()) {
                    String categoryString = StringEscapeUtils.unescapeJava(cp.group(1));
                    String[] categorySplit = categoryString.split("/");
                    for (String category : categorySplit) {
                        categoriesAdd.add(category);
                    }
                }
                if (cr.find()) {
                    String crString = cr.group(1);
                    crAdd = Double.parseDouble(crString);
                }
                if (nr.find()) {
                    String nrString = nr.group(1);
                    nrAdd = Integer.parseInt(nrString);
                }

                //Create Movie Object
                Movie.Builder builder = new Movie.Builder(idAdd, namAdd, ldAdd);
                for (String category : categoriesAdd) {
                    builder.category(category);
                }
                if (crAdd != null && nrAdd != null) {
                    builder.rating(crAdd, nrAdd);
                }
                Movie movie = builder.build();
                getSearchItems().add(movie);
            }
        }
    }

    @Override
    protected Collection<Item> getSearchItems() {
        return items;
    }

    public Collection<Item> getItems() {
        return items;
    }
}
