import deque.Maximizer61B;
import deque.ArrayDeque61B;

import org.junit.jupiter.api.*;

import java.util.Comparator;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class Maximizer61BTest {
    private static class StringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }

//    @Test
//    public void basicTest() {
//        ArrayDeque61B<String> ad = new ArrayDeque61B<>();
//        ad.addFirst("");
//        ad.addFirst("2");
//        ad.addFirst("fury road");
//        assertThat(Maximizer61B.max(ad, new StringLengthComparator())).isEqualTo("fury road");
//    }
// flag: max_nonempty / max_default
@Test
public void maxNonEmptyTest() {
    List<Integer> xs = List.of(3, 17, 5, -2);
    assertThat(Maximizer61B.max(xs)).isEqualTo(17);
}

    // flag: max_nonempty —— 单元素边界
    @Test
    public void maxSingleElementTest() {
        assertThat(Maximizer61B.max(List.of(42))).isEqualTo(42);
    }

    // flag: max_default —— 全负数，抓"用 0 当初始最大值"的 bug
    @Test
    public void maxAllNegativeTest() {
        assertThat(Maximizer61B.max(List.of(-5, -9, -1))).isEqualTo(-1);
    }

    // flag: max_different_comp —— 用你已有的 StringLengthComparator
    @Test
    public void maxWithStringLengthComparatorTest() {
        List<String> words = List.of("a", "bbb", "cc");
        assertThat(Maximizer61B.max(words, new StringLengthComparator())).isEqualTo("bbb");
    }

    // flag: max_different_comp —— 反向比较器，证明实现真的用了 comp
    @Test
    public void maxWithReversedComparatorTest() {
        assertThat(Maximizer61B.max(List.of(1, 2, 3), Comparator.reverseOrder())).isEqualTo(1);
    }

    // flag: max_empty —— 两个重载都应返回 null
    @Test
    public void maxEmptyTest() {
        List<Integer> empty = List.of();
        assertThat(Maximizer61B.max(empty)).isNull();
        assertThat(Maximizer61B.max(empty, Comparator.<Integer>naturalOrder())).isNull();
    }
}
