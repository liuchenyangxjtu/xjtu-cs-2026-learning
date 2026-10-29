import deque.ArrayDeque61B;

import deque.Deque61B;
import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

//     @Test
//     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
//     void noNonTrivialFields() {
//         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
//                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
//                 .toList();
//
//         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
//     }
    /* ==================== 一、不需要扩容/缩容 ==================== */

    // flag: add_first_from_empty
    @Test
    public void addFirstFromEmptyTest() {
        Deque61B<Integer> d = new ArrayDeque61B<>();
        d.addFirst(10);
        assertThat(d.toList()).containsExactly(10).inOrder();
        assertThat(d.size()).isEqualTo(1);
        assertThat(d.isEmpty()).isFalse();
    }

    // flag: add_last_from_empty
    @Test
    public void addLastFromEmptyTest() {
        Deque61B<Integer> d = new ArrayDeque61B<>();
        d.addLast(10);
        assertThat(d.toList()).containsExactly(10).inOrder();
        assertThat(d.size()).isEqualTo(1);
        assertThat(d.isEmpty()).isFalse();
    }

    // flag: add_first_nonempty
    @Test
    public void addFirstNonEmptyTest() {
        Deque61B<Integer> d = new ArrayDeque61B<>();
        d.addLast(1);
        d.addLast(2);
        d.addFirst(0);
        assertThat(d.toList()).containsExactly(0, 1, 2).inOrder();
        assertThat(d.size()).isEqualTo(3);
    }

    // flag: add_last_nonempty
    @Test
    public void addLastNonEmptyTest() {
        Deque61B<Integer> d = new ArrayDeque61B<>();
        d.addFirst(2);
        d.addFirst(1);
        d.addLast(3);
        assertThat(d.toList()).containsExactly(1, 2, 3).inOrder();
        assertThat(d.size()).isEqualTo(3);
    }

    // flag: add_first_after_remove_to_empty
    @Test
    public void addFirstAfterRemoveToEmptyTest() {
        Deque61B<Integer> d = new ArrayDeque61B<>();
        d.addLast(1);
        d.addLast(2);
        d.addLast(3);
        d.removeFirst();
        d.removeFirst();
        d.removeFirst();
        assertThat(d.isEmpty()).isTrue();
        assertThat(d.size()).isEqualTo(0);

        d.addFirst(42);
        assertThat(d.toList()).containsExactly(42).inOrder();
        assertThat(d.size()).isEqualTo(1);
    }

    // flag: add_last_after_remove_to_empty
    @Test
    public void addLastAfterRemoveToEmptyTest() {
        Deque61B<Integer> d = new ArrayDeque61B<>();
        d.addFirst(3);
        d.addFirst(2);
        d.addFirst(1);
        d.removeLast();
        d.removeLast();
        d.removeLast();
        assertThat(d.isEmpty()).isTrue();
        assertThat(d.size()).isEqualTo(0);

        d.addLast(42);
        assertThat(d.toList()).containsExactly(42).inOrder();
        assertThat(d.size()).isEqualTo(1);
    }

    // flag: remove_first
    @Test
    public void removeFirstTest() {
        Deque61B<String> d = new ArrayDeque61B<>();
        d.addLast("a");
        d.addLast("b");
        d.addLast("c");

        assertThat(d.removeFirst()).isEqualTo("a");
        assertThat(d.toList()).containsExactly("b", "c").inOrder();
        assertThat(d.size()).isEqualTo(2);

        assertThat(d.removeFirst()).isEqualTo("b");
        assertThat(d.removeFirst()).isEqualTo("c");
        assertThat(d.isEmpty()).isTrue();
    }

    // flag: remove_last
    @Test
    public void removeLastTest() {
        Deque61B<String> d = new ArrayDeque61B<>();
        d.addLast("a");
        d.addLast("b");
        d.addLast("c");

        assertThat(d.removeLast()).isEqualTo("c");
        assertThat(d.toList()).containsExactly("a", "b").inOrder();
        assertThat(d.size()).isEqualTo(2);

        assertThat(d.removeLast()).isEqualTo("b");
        assertThat(d.removeLast()).isEqualTo("a");
        assertThat(d.isEmpty()).isTrue();
    }

    // flag: remove_first_to_empty
    @Test
    public void removeFirstToEmptyTest() {
        Deque61B<Integer> d = new ArrayDeque61B<>();
        d.addLast(1);
        d.addLast(2);
        d.addLast(3);

        assertThat(d.removeFirst()).isEqualTo(1);
        assertThat(d.removeFirst()).isEqualTo(2);
        assertThat(d.isEmpty()).isFalse();

        assertThat(d.removeFirst()).isEqualTo(3);
        assertThat(d.isEmpty()).isTrue();
        assertThat(d.size()).isEqualTo(0);
        assertThat(d.toList()).isEmpty();
    }

    // flag: remove_last_to_empty
    @Test
    public void removeLastToEmptyTest() {
        Deque61B<Integer> d = new ArrayDeque61B<>();
        d.addLast(1);
        d.addLast(2);
        d.addLast(3);

        assertThat(d.removeLast()).isEqualTo(3);
        assertThat(d.removeLast()).isEqualTo(2);
        assertThat(d.isEmpty()).isFalse();

        assertThat(d.removeLast()).isEqualTo(1);
        assertThat(d.isEmpty()).isTrue();
        assertThat(d.size()).isEqualTo(0);
        assertThat(d.toList()).isEmpty();
    }

    // flag: remove_first_to_one
    @Test
    public void removeFirstToSizeOneTest() {
        Deque61B<Integer> d = new ArrayDeque61B<>();
        d.addLast(1);
        d.addLast(2);
        d.addLast(3);

        assertThat(d.removeFirst()).isEqualTo(1);
        assertThat(d.removeFirst()).isEqualTo(2);
        assertThat(d.size()).isEqualTo(1);
        assertThat(d.toList()).containsExactly(3).inOrder();
        assertThat(d.isEmpty()).isFalse();
    }

    // flag: remove_last_to_one
    @Test
    public void removeLastToSizeOneTest() {
        Deque61B<Integer> d = new ArrayDeque61B<>();
        d.addLast(1);
        d.addLast(2);
        d.addLast(3);

        assertThat(d.removeLast()).isEqualTo(3);
        assertThat(d.removeLast()).isEqualTo(2);
        assertThat(d.size()).isEqualTo(1);
        assertThat(d.toList()).containsExactly(1).inOrder();
        assertThat(d.isEmpty()).isFalse();
    }

    // flag: get_valid
    @Test
    public void getValidIndexTest() {
        Deque61B<String> d = new ArrayDeque61B<>();
        d.addLast("a");
        d.addLast("b");
        d.addLast("c");

        assertThat(d.get(0)).isEqualTo("a");
        assertThat(d.get(1)).isEqualTo("b");
        assertThat(d.get(2)).isEqualTo("c");
    }

    // flag: get_oob_large
    @Test
    public void getOutOfBoundsLargeTest() {
        Deque61B<String> d = new ArrayDeque61B<>();
        d.addLast("a");
        d.addLast("b");
        d.addLast("c");

        assertThat(d.get(3)).isNull();
        assertThat(d.get(28723)).isNull();
    }

    // flag: get_oob_neg
    @Test
    public void getOutOfBoundsNegativeTest() {
        Deque61B<String> d = new ArrayDeque61B<>();
        d.addLast("a");
        d.addLast("b");
        d.addLast("c");

        assertThat(d.get(-1)).isNull();
        assertThat(d.get(-100)).isNull();
    }

    // flag: size
    @Test
    public void sizeTest() {
        Deque61B<String> d = new ArrayDeque61B<>();
        assertThat(d.size()).isEqualTo(0);

        d.addLast("a");
        assertThat(d.size()).isEqualTo(1);

        d.addFirst("b");
        assertThat(d.size()).isEqualTo(2);

        d.addLast("c");
        assertThat(d.size()).isEqualTo(3);

        d.removeFirst();
        assertThat(d.size()).isEqualTo(2);

        d.removeLast();
        assertThat(d.size()).isEqualTo(1);
    }

    // flag: size_after_remove_to_empty
    @Test
    public void sizeAfterRemoveToEmptyTest() {
        Deque61B<String> d = new ArrayDeque61B<>();
        d.addFirst("a");
        d.addLast("b");
        d.addLast("c");
        assertThat(d.size()).isEqualTo(3);

        d.removeFirst();
        d.removeLast();
        d.removeFirst();
        assertThat(d.size()).isEqualTo(0);
        assertThat(d.isEmpty()).isTrue();
    }

    // flag: size_after_remove_from_empty
    @Test
    public void sizeAfterRemoveFromEmptyTest() {
        Deque61B<String> d = new ArrayDeque61B<>();
        assertThat(d.size()).isEqualTo(0);

        assertThat(d.removeFirst()).isNull();
        assertThat(d.size()).isEqualTo(0);

        assertThat(d.removeLast()).isNull();
        assertThat(d.size()).isEqualTo(0);
        assertThat(d.isEmpty()).isTrue();
    }

    // flag: is_empty_true
    @Test
    public void isEmptyTrueTest() {
        Deque61B<String> d = new ArrayDeque61B<>();
        assertThat(d.isEmpty()).isTrue();
        assertThat(d.size()).isEqualTo(0);
    }

    // flag: is_empty_false
    @Test
    public void isEmptyFalseTest() {
        Deque61B<String> d = new ArrayDeque61B<>();
        d.addFirst("a");
        assertThat(d.isEmpty()).isFalse();

        d.addLast("b");
        assertThat(d.isEmpty()).isFalse();

        d.removeFirst();
        d.removeLast();
        assertThat(d.isEmpty()).isTrue();
    }

    // flag: to_list_empty
    @Test
    public void toListEmptyTest() {
        Deque61B<String> d = new ArrayDeque61B<>();
        assertThat(d.toList()).isEmpty();
        assertThat(d.toList().size()).isEqualTo(0);
    }

    // flag: to_list_nonempty
    @Test
    public void toListNonEmptyTest() {
        Deque61B<Integer> d = new ArrayDeque61B<>();
        d.addLast(1);
        d.addLast(2);
        d.addFirst(0);
        d.addLast(3);

        assertThat(d.toList()).containsExactly(0, 1, 2, 3).inOrder();
        assertThat(d.toList().size()).isEqualTo(4);
    }

    // 综合：最多只有 4 个元素，不会触发扩容
    @Test
    public void integrationMixedOperationsTest() {
        Deque61B<Integer> d = new ArrayDeque61B<>();

        assertThat(d.isEmpty()).isTrue();
        assertThat(d.removeFirst()).isNull();
        assertThat(d.removeLast()).isNull();

        d.addLast(1);
        d.addFirst(0);
        d.addLast(2);
        d.addFirst(-1);
        assertThat(d.toList()).containsExactly(-1, 0, 1, 2).inOrder();
        assertThat(d.size()).isEqualTo(4);
        assertThat(d.get(0)).isEqualTo(-1);
        assertThat(d.get(3)).isEqualTo(2);

        assertThat(d.removeFirst()).isEqualTo(-1);
        assertThat(d.removeLast()).isEqualTo(2);
        assertThat(d.toList()).containsExactly(0, 1).inOrder();
        assertThat(d.size()).isEqualTo(2);

        d.addLast(9);
        assertThat(d.get(2)).isEqualTo(9);
        assertThat(d.removeFirst()).isEqualTo(0);
        assertThat(d.removeLast()).isEqualTo(9);
        assertThat(d.size()).isEqualTo(1);
        assertThat(d.get(1)).isNull();

        assertThat(d.removeFirst()).isEqualTo(1);
        assertThat(d.isEmpty()).isTrue();
        assertThat(d.toList()).isEmpty();
    }

    /* ================================================================
     * 二、以下 5 个测试需要扩容/缩容（resize）。
     * 先把上面 23 个跑绿，再实现 resize；
     * 如果暂时不想看到它们报错，选中这一段按 Ctrl+/ 整体注释掉即可。
     * ================================================================ */

    /** 造一个内容为 [0, 1, ..., n-1] 的 deque（只被下面 4 个 resize 测试用到）。 */
    private static Deque61B<Integer> dequeOf(int n) {
        Deque61B<Integer> d = new ArrayDeque61B<>();
        for (int i = 0; i < n; i++) {
            d.addLast(i);
        }
        return d;
    }

    // flag: add_first_trigger_resize
    // 底层数组初始长度 8：装满 8 个后再 addFirst 必须扩容
    @Test
    public void addFirstTriggerResizeTest() {
        Deque61B<Integer> d = dequeOf(8);
        d.addFirst(-1);
        assertThat(d.size()).isEqualTo(9);
        assertThat(d.get(0)).isEqualTo(-1);
        assertThat(d.get(8)).isEqualTo(7);
        assertThat(d.toList()).containsExactly(-1, 0, 1, 2, 3, 4, 5, 6, 7).inOrder();
    }

    // flag: add_last_trigger_resize
    @Test
    public void addLastTriggerResizeTest() {
        Deque61B<Integer> d = dequeOf(8);
        d.addLast(8);
        assertThat(d.size()).isEqualTo(9);
        assertThat(d.get(0)).isEqualTo(0);
        assertThat(d.get(8)).isEqualTo(8);
        assertThat(d.toList()).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8).inOrder();
    }

    // flag: remove_first_trigger_resize
    // 先扩容到 16（加 12 个），再删到只剩 2 个 → 使用率 ≤ 25%，必须缩容
    @Test
    public void removeFirstTriggerResizeTest() {
        Deque61B<Integer> d = dequeOf(12);

        for (int i = 0; i < 10; i++) {
            assertThat(d.removeFirst()).isEqualTo(i);
        }
        assertThat(d.size()).isEqualTo(2);
        assertThat(d.toList()).containsExactly(10, 11).inOrder();
    }

    // flag: remove_last_trigger_resize
    @Test
    public void removeLastTriggerResizeTest() {
        Deque61B<Integer> d = dequeOf(12);

        for (int i = 11; i >= 2; i--) {
            assertThat(d.removeLast()).isEqualTo(i);
        }
        assertThat(d.size()).isEqualTo(2);
        assertThat(d.toList()).containsExactly(0, 1).inOrder();
    }

    // flag: resize_up_and_resize_down
    @Test
    public void resizeUpAndResizeDownTest() {
        Deque61B<Integer> d = new ArrayDeque61B<>();
        for (int i = 0; i < 20; i++) {
            d.addLast(i);
        }
        assertThat(d.size()).isEqualTo(20);
        assertThat(d.get(0)).isEqualTo(0);
        assertThat(d.get(19)).isEqualTo(19);

        for (int i = 19; i >= 1; i--) {
            assertThat(d.removeLast()).isEqualTo(i);
        }
        assertThat(d.size()).isEqualTo(1);
        assertThat(d.toList()).containsExactly(0).inOrder();
    }
}
