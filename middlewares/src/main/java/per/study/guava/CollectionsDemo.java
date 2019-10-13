package per.study.guava;

import com.google.common.base.*;
import com.google.common.collect.*;
import com.google.common.io.Files;
import com.google.common.io.Resources;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author sapphire
 */
public class CollectionsDemo {

    public static void newCollections() {
        /**
         * 普通Collection的创建
         */
        List<String> list = Lists.newArrayList();
        Set<String> set = Sets.newHashSet();
        Map<String, String> map = Maps.newHashMap();

        /**
         * 不变Collection的创建
         * 在多线程操作下，是线程安全的
         * 所有不可变集合会比可变集合更有效的利用资源
         * 中途不可改变
         */
        ImmutableList<String> iList = ImmutableList.of("a", "b", "c");
        ImmutableSet<String> iSet = ImmutableSet.of("e1", "e2");
        ImmutableMap<String, String> iMap = ImmutableMap.of("k1", "v1", "k2", "v2");

    }

    /**
     * <p>
     * MultiSet: 无序+可重复   count()方法获取单词的次数  增强了可读性+操作简单
     * 创建方式:  Multiset<String> set = HashMultiset.create();
     * <p>
     * Multimap: key-value  key可以重复
     * 创建方式: Multimap<String, String> teachers = ArrayListMultimap.create();
     * <p>
     * BiMap: 双向Map(Bidirectional Map) 键与值都不能重复
     * 创建方式:  BiMap<String, String> biMap = HashBiMap.create();
     * <p>
     * Table: 双键的Map Map--> Table-->rowKey+columnKey+value  //和sql中的联合主键有点像
     * 创建方式: Table<String, String, Integer> tables = HashBasedTable.create()
     */
    public static void newMulti() {
        // jdk
        Map<String, List<Integer>> oldMap = new HashMap<String, List<Integer>>();
        List<Integer> oldList = new ArrayList<Integer>();
        oldList.add(1);
        oldList.add(2);
        oldMap.put("aa", oldList);
        // 字符串输出
        System.out.println(Joiner.on("-").join(oldList));

        System.out.println(oldMap.get("aa"));//[1, 2]
        // guava
        Multimap<String, Integer> map = ArrayListMultimap.create();
        map.put("aa", 1);
        map.put("aa", 2);
        // 字符串输出
        System.out.println(Joiner.on(",").withKeyValueSeparator("=").join(map.asMap()));
        System.out.println(map.get("aa"));  //[1, 2]
    }

    public static void stringToList() {
        // java
        String words = "1-2-3-4-5-6";
        List<String> list = new ArrayList<String>();
        String[] strs = words.split("-");
        Collections.addAll(list, strs);
        System.out.println(list);
        // guava
        List<String> list1 = Splitter.on("-").splitToList(words);
        // 去除空格
        list1 = Splitter.on("-").omitEmptyStrings().trimResults().splitToList(words);

        System.out.println(list1); //[1, 2, 3, 4, 5, 6]

        String str = "xiaoming=11,xiaohong=23";
        Map<String, String> map = Splitter.on(",").withKeyValueSeparator("=").split(str);
        System.out.println(map);

        //多个字符串切割
        String input = "aa.dd,,ff,,.";
        List<String> list2 = Splitter.onPattern("[.|,]").omitEmptyStrings().splitToList(input);
        System.out.println(list2);
    }

    public static void charMatch() {
        // 判断匹配结果
        boolean result = CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z')).matches('K'); //true
        // 保留数字文本  CharMatcher.digit() 已过时   retain 保留
        //String s1 = CharMatcher.digit().retainFrom("abc 123 efg"); //123
        String s1 = CharMatcher.inRange('0', '9').retainFrom("abc 123 efg"); // 123
        // 删除数字文本  remove 删除
        // String s2 = CharMatcher.digit().removeFrom("abc 123 efg");    //abc  efg
        String s2 = CharMatcher.inRange('0', '9').removeFrom("abc 123 efg"); // abc  efg
        System.out.println(result);
        System.out.println(s1);
        System.out.println(s2);
    }

    public static void filterCollections() {
        //按照条件过滤
        ImmutableList<String> names = ImmutableList.of("begin", "code", "Guava", "Java");
        Iterable<String> fitered = Iterables.filter(names, Predicates.or
                (Predicates.equalTo("Guava"),
                        Predicates.equalTo("Java")));
        System.out.println(fitered); // [Guava, Java]

        //自定义过滤条件   使用自定义回调方法对Map的每个Value进行操作
        ImmutableMap<String, Integer> m = ImmutableMap.of("begin", 12, "code", 15);
        // Function<F, T> F表示apply()方法input的类型，T表示apply()方法返回类型
        Map<String, Integer> m2 = Maps.transformValues(m, new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer input) {
                if (input > 12) {
                    return input;
                } else {
                    return input + 1;
                }
            }
        });
        System.out.println(m2);   //{begin=13, code=15}

        //set 的交集，并集，差集
        HashSet setA = Sets.newHashSet(1, 2, 3, 4, 5);
        HashSet setB = Sets.newHashSet(4, 5, 6, 7, 8);
        Sets.SetView union = Sets.union(setA, setB);
        System.out.println("union:" + union); //union 并集:12345867
        Sets.SetView difference = Sets.difference(setA, setB);
        System.out.println("difference:" + difference); //difference 差集:123
        Sets.SetView intersection = Sets.intersection(setA, setB);
        System.out.println("intersection:" + intersection); //intersection 交集:45
        //map的交集，并集，差集
        HashMap<String, Integer> mapA = Maps.newHashMap();
        mapA.put("a", 1);
        mapA.put("b", 2);
        mapA.put("c", 3);

        HashMap<String, Integer> mapB = Maps.newHashMap();
        mapB.put("b", 20);
        mapB.put("c", 3);
        mapB.put("d", 4);

        MapDifference differenceMap = Maps.difference(mapA, mapB);
        differenceMap.areEqual();
        System.out.println(differenceMap.entriesDiffering());   // {b=(2, 20)}
        System.out.println(differenceMap.entriesOnlyOnLeft());    // {a=1}
        System.out.println(differenceMap.entriesOnlyOnRight());   // {d=4}
        System.out.println(differenceMap.entriesInCommon());    // {c=3}
    }

    /**
     * Preconditions
     * <p>
     * checkArgument(boolean)
     * 检查boolean是否为true，用来检查传递给方法的参数。
     * IllegalArgumentException
     * <p>
     * checkNotNull(T)
     * 检查value是否为null，该方法直接返回value，因此可以内嵌使用checkNotNull。
     * NullPointerException
     * <p>
     * checkState(boolean)
     * 用来检查对象的某些状态。
     * IllegalStateException
     * <p>
     * checkElementIndex(int index, int size)
     * 检查index作为索引值对某个列表、字符串或数组是否有效。   index > 0 && index < size
     * IndexOutOfBoundsException
     * <p>
     * checkPositionIndexes(int start, int end, int size)
     * 检查[start,end]表示的位置范围对某个列表、字符串或数组是否有效
     * IndexOutOfBoundsException
     */
    public static void checkParams() {
        String param1 = "123";
        int param2 = 4;
        if (!Strings.isNullOrEmpty(param1)) {
            if (param2 <= 0) {
                throw new IllegalArgumentException("must be positive: " + param2);
            }
        }
        Preconditions.checkArgument(param2 > 0, "must be positive: %s", param2);
    }

    public static void moreObject() {
        String str = MoreObjects.toStringHelper("Object").add("age", 18).toString();
        System.out.println(str);//输出Object{age=11}
    }

    /**
     * natural()	        对可排序类型做自然排序，如数字按大小，日期按先后排序
     * usingToString()	    按对象的字符串形式做字典排序[lexicographical ordering]
     * from(Comparator)	    把给定的Comparator转化为排序器
     * reverse()	        获取语义相反的排序器
     * nullsFirst()	        使用当前排序器，但额外把null值排到最前面。
     * nullsLast()	        使用当前排序器，但额外把null值排到最后面。
     * compound(Comparator)	合成另一个比较器，以处理当前排序器中的相等情况。
     * lexicographical()	基于处理类型T的排序器，返回该类型的可迭代对象Iterable<T>的排序器。
     * onResultOf(Function)	对集合中元素调用Function，再按返回值用当前排序器排序。
     */
    public static void order() {
        Object o1 = new Object();  //String name  ,Integer age
        Object o2 = new Object();
        Ordering<Object> byOrdering = Ordering.natural()
                .nullsFirst().onResultOf(new Function<Object, String>() {
                    @Override
                    public String apply(Object Object) {
                        return Object.toString();
                    }
                });
        System.out.println(byOrdering.compare(o1, o2));
    }

    public static void runtime() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        // do some thing
        long nanos = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.println(nanos);
    }

    public static void main(String[] args) {
        newCollections();
        newMulti();
        stringToList();
        charMatch();
        filterCollections();
        checkParams();
        moreObject();
        order();
        runtime();
    }
}