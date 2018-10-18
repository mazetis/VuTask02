package lt.vu.mif.jate.tasks.task02.base;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.RandomUtils;

import javassist.CannotCompileException;
import javassist.NotFoundException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lt.vu.mif.jate.tasks.task02.search.Searchable;
import lt.vu.mif.jate.tasks.task02.util.ClassBuilder;

@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class GeneratorTestBase implements TestBase {

    private static final int NAME_LENGTH = 10;
    private static final int ITEMS_COUNT = RandomUtils.nextInt(500, 1000);

    private static final int VALUE_LENGTH = 10;
    private static final int VALUES_COUNT = RandomUtils.nextInt(100, 300);
    private static final int PROPERTIES_COUNT = 10;
    
    private final String itemClassName = randString(NAME_LENGTH);
    private final Class<?> itemClass;
    private final Set<PropertyDescr> fields = new HashSet<>();
    
    protected final ValuesHolder values = new ValuesHolder();
    protected final Set items = new HashSet();
    
    public GeneratorTestBase() throws CannotCompileException, NotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        
        ClassBuilder cb = new ClassBuilder(itemClassName)
            .setSuper(ItemBase.class)
                .newProperty(Integer.class, "id")
                .build();
        
        while (fields.size() < PROPERTIES_COUNT) {
            PropertyDescr f = new PropertyDescr();
            cb.newProperty(String.class, f.getPropertyName())
                .newFieldAnnotation(Searchable.class)
                    .addMember("field", f.getFieldName())
                    .build()
                .build();
            fields.add(f);
        }
        
        this.itemClass = cb.build();

        for (int i = 0; i < ITEMS_COUNT; i++) {
            
            Object item = itemClass.newInstance();
            PropertyUtils.setSimpleProperty(item, "id", i);
            
            for (PropertyDescr f: fields) {
                PropertyUtils.setSimpleProperty(item, f.propertyName, values.nextAndInc(f));
            }
            
            items.add(item);
        }

        values.removeEmpty();
        
    }
    
    @Getter
    @EqualsAndHashCode(of = {"propertyName"})
    protected class PropertyDescr {
        
        private final String propertyName = randString(NAME_LENGTH);
        private final String fieldName = randString(NAME_LENGTH);

    }
     
    protected class ValuesHolder {
            
        private final Map<String, Map<PropertyDescr, Integer>> values = new HashMap<>();
        private final List<String> keys;
        
        public ValuesHolder() {
            Set<String> keySet = new HashSet<>();
            while (keySet.size() < VALUES_COUNT) {
                keySet.add(randString(VALUE_LENGTH));
            }
            this.keys = new ArrayList<>(keySet);
        }
        
        public String nextAndInc(PropertyDescr f) {
            String v = keys.get(RandomUtils.nextInt(0, VALUES_COUNT));
            int c = 0;
            if (values.containsKey(v)) {
                if (values.get(v).containsKey(f)) {
                    c = values.get(v).get(f);
                }
            } else {
                values.put(v, new HashMap<>());
            }
            values.get(v).put(f, c + 1);
            return v;
        }
        
        public void removeEmpty() {
            List<Integer> toRemove = new ArrayList<>();
            for (int i = 0; i < keys.size(); i++) {
                if (!values.containsKey(keys.get(i))) {
                    toRemove.add(i);
                }
            }
            toRemove.stream().forEach((i) -> keys.remove((int) i));
        }

        public Map<PropertyDescr, Integer> get(String value) {
            return values.get(value);
        }
        
        public String next() {
            return keys.get(RandomUtils.nextInt(0, keys.size()));
        }
        
    }
    
    public static abstract class ItemBase implements Comparable<ItemBase> {

        public abstract Integer getId();
        
        @Override
        public int compareTo(ItemBase o) {
            return getId().compareTo(o.getId());
        }

    }
    
}
