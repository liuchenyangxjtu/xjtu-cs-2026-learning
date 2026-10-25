import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque61B.
    /* ============================================================
     * 一、add 相关
     * ============================================================ */

    // 场景：空 deque 上调用 addFirst
    // flag：add_first_from_empty
    // 断言：toList = ["only"]、size = 1、isEmpty = false
    // 要害：空表时 sentinel.next 就是 sentinel 自己（自环），
    //      插入逻辑必须在这个"首尾同体"的特例下也正确
    @Test
    public void addFirstFromEmptyTest() {
        Deque61B<String> d = new LinkedListDeque61B<>();
        d.addFirst("only");
        assertThat(d.toList()).containsExactly("only").inOrder();
        assertThat(d.size()).isEqualTo(1);
        assertThat(d.isEmpty()).isFalse();
    }

    // 场景：空 deque 上调用 addLast
    // flag：add_last_from_empty
    // 断言：同上
    // 要害：空表时 sentinel.prev 也是 sentinel，与 addFirst 对称但走另一条指针
    @Test
    public void addLastFromEmptyTest() {
        Deque61B<String> d = new LinkedListDeque61B<>();
        d.addLast("only");
        assertThat(d.toList()).containsExactly("only").inOrder();
        assertThat(d.size()).isEqualTo(1);
        assertThat(d.isEmpty()).isFalse();
    }

    // 场景：deque 已有 [1, 2]，再 addFirst(0)
    // flag：add_first_nonempty
    // 断言：变成 [0, 1, 2]
    // 要害：不能覆盖原来的链表头，旧头节点的 prev 必须被更新
    @Test
    public void addFirstNonEmptyTest() {
        Deque61B<Integer> d = new LinkedListDeque61B<>();
        d.addLast(1);
        d.addLast(2);
        d.addFirst(0);
        assertThat(d.toList()).containsExactly(0, 1, 2).inOrder();
        assertThat(d.size()).isEqualTo(3);
    }

    // 场景：deque 已有 [1, 2]，再 addLast(3)
    // flag：add_last_nonempty
    // 断言：变成 [1, 2, 3]
    // 要害：对称的坑——旧尾节点的 next、sentinel.prev 都要更新
    @Test
    public void addLastNonEmptyTest() {
        Deque61B<Integer> d = new LinkedListDeque61B<>();
        d.addFirst(2);
        d.addFirst(1);
        d.addLast(3);
        assertThat(d.toList()).containsExactly(1, 2, 3).inOrder();
        assertThat(d.size()).isEqualTo(3);
    }

    /* ============================================================
     * 二、删空之后再添加
     * ============================================================ */

    // 场景：add 3 个 → removeFirst 三次删空 → 再 addFirst(42)
    // flag：add_first_after_remove_to_empty
    // 断言：删空后 isEmpty=true、size=0；再加之后 toList=[42]、size=1
    // 要害：这是"删除后指针没复位"的重灾区。
    //      删掉最后一个元素时，sentinel.next/prev 必须重新指回 sentinel 自己，
    //      否则下一次 add 会接到一个已经游离的节点上
    @Test
    public void addFirstAfterRemoveToEmptyTest() {
        Deque61B<Integer> d = new LinkedListDeque61B<>();
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

    // 场景：与上一条镜像，用 addFirst 建表、removeLast 删空，再 addLast(42)
    // flag：add_last_after_remove_to_empty
    // 要害：验证"另一侧"的指针归位是否也正确（有些实现只修好了一边）
    @Test
    public void addLastAfterRemoveToEmptyTest() {
        Deque61B<Integer> d = new LinkedListDeque61B<>();
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

    /* ============================================================
     * 三、remove 相关
     * ============================================================ */

    // 场景：[a, b, c] 连续 removeFirst 三次
    // flag：remove_first
    // 断言：每次返回被删的值；每次删完后剩余列表顺序正确；最后变空
    // 要害：返回值、剩余结构、尾部状态三件事一起查
    @Test
    public void removeFirstTest() {
        Deque61B<String> d = new LinkedListDeque61B<>();
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

    // 场景：[a, b, c] 连续 removeLast 三次
    // flag：remove_last
    // 断言：从尾部开始删，顺序与 removeFirst 相反
    // 要害：与 removeFirst 对称，抓"只修好一侧"的 bug
    @Test
    public void removeLastTest() {
        Deque61B<String> d = new LinkedListDeque61B<>();
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

    // 场景：空 deque 上删除
    // flag：无（但 remove_first / remove_last 的语义基础）
    // 断言：返回 null（而不是抛异常或崩溃）；状态不变
    // 要害：接口 javadoc 明确写"if it exists, 否则返回 null"
    @Test
    public void removeFromEmptyReturnsNullTest() {
        Deque61B<String> d = new LinkedListDeque61B<>();
        assertThat(d.removeFirst()).isNull();
        assertThat(d.removeLast()).isNull();
        assertThat(d.isEmpty()).isTrue();
        assertThat(d.size()).isEqualTo(0);
        assertThat(d.toList()).isEmpty();
    }

    // 场景：3 个元素删到空，重点在"最后一次删除"
    // flag：remove_first_to_empty
    // 断言：删到剩 1 个时 isEmpty 仍为 false；删掉最后 1 个后变空
    // 要害：中间状态也要断言——只查最终空表，会漏掉"删到 1 个就提前变空"的 bug
    @Test
    public void removeFirstToEmptyTest() {
        Deque61B<Integer> d = new LinkedListDeque61B<>();
        d.addLast(1);
        d.addLast(2);
        d.addLast(3);

        assertThat(d.removeFirst()).isEqualTo(1);
        assertThat(d.removeFirst()).isEqualTo(2);
        assertThat(d.isEmpty()).isFalse();

        assertThat(d.removeFirst()).isEqualTo(3); // 删掉最后一个元素，变空
        assertThat(d.isEmpty()).isTrue();
        assertThat(d.size()).isEqualTo(0);
        assertThat(d.toList()).isEmpty();
    }

    // 场景：同上的镜像版本（从尾部删到空）
    // flag：remove_last_to_empty
    // 要害：删空这一步最容易出现 sentinel 指针没归位
    @Test
    public void removeLastToEmptyTest() {
        Deque61B<Integer> d = new LinkedListDeque61B<>();
        d.addLast(1);
        d.addLast(2);
        d.addLast(3);

        assertThat(d.removeLast()).isEqualTo(3);
        assertThat(d.removeLast()).isEqualTo(2);
        assertThat(d.isEmpty()).isFalse();

        assertThat(d.removeLast()).isEqualTo(1); // 删掉最后一个元素，变空
        assertThat(d.isEmpty()).isTrue();
        assertThat(d.size()).isEqualTo(0);
        assertThat(d.toList()).isEmpty();
    }

    // 场景：3 个元素，删两次后剩 1 个（即"删掉倒数第二个"那一次是重点）
    // flag：remove_first_to_one
    // 断言：剩下 [3]、size=1、isEmpty=false
    // 要害：只剩 1 个元素时，头尾指针指向同一个节点，
    //      不少实现在这里会走错分支（把"1 个"当成"0 个"）
    @Test
    public void removeFirstToSizeOneTest() {
        Deque61B<Integer> d = new LinkedListDeque61B<>();
        d.addLast(1);
        d.addLast(2);
        d.addLast(3);

        assertThat(d.removeFirst()).isEqualTo(1); // 剩 [2, 3]
        assertThat(d.removeFirst()).isEqualTo(2); // 剩 [3]，即"倒数第二个"那次删除
        assertThat(d.size()).isEqualTo(1);
        assertThat(d.toList()).containsExactly(3).inOrder();
        assertThat(d.isEmpty()).isFalse();
    }

    // 场景：镜像版本，从尾部删到只剩 1 个
    // flag：remove_last_to_one
    // 断言：剩下 [1]、size=1
    @Test
    public void removeLastToSizeOneTest() {
        Deque61B<Integer> d = new LinkedListDeque61B<>();
        d.addLast(1);
        d.addLast(2);
        d.addLast(3);

        assertThat(d.removeLast()).isEqualTo(3); // 剩 [1, 2]
        assertThat(d.removeLast()).isEqualTo(2); // 剩 [1]，即"倒数第二个"那次删除
        assertThat(d.size()).isEqualTo(1);
        assertThat(d.toList()).containsExactly(1).inOrder();
        assertThat(d.isEmpty()).isFalse();
    }

    /* ============================================================
     * 四、get / getRecursive
     * ============================================================ */

    // 场景：3 元素 deque，取合法下标 0/1/2
    // flag：get_valid
    // 断言：分别返回 a/b/c（下标从 0 开始）
    // 要害：迭代版本必须真的走 i 步，不能永远返回第一个
    @Test
    public void getValidIndexTest() {
        Deque61B<String> d = new LinkedListDeque61B<>();
        d.addLast("a");
        d.addLast("b");
        d.addLast("c");

        assertThat(d.get(0)).isEqualTo("a");
        assertThat(d.get(1)).isEqualTo("b");
        assertThat(d.get(2)).isEqualTo("c");
    }

    // 场景：下标超出范围（刚好等于 size，以及一个很大的数）
    // flag：get_oob_large
    // 断言：都返回 null —— 不能抛 IndexOutOfBoundsException
    // 要害：接口 javadoc 写的是"Returns null if index is out of bounds"
    @Test
    public void getOutOfBoundsLargeTest() {
        Deque61B<String> d = new LinkedListDeque61B<>();
        d.addLast("a");
        d.addLast("b");
        d.addLast("c");

        assertThat(d.get(3)).isNull();
        assertThat(d.get(28723)).isNull();
    }

    // 场景：负下标
    // flag：get_oob_neg
    // 断言：返回 null
    // 要害：负下标如果被当成"循环索引"，可能悄悄返回尾部元素，很难发现
    @Test
    public void getOutOfBoundsNegativeTest() {
        Deque61B<String> d = new LinkedListDeque61B<>();
        d.addLast("a");
        d.addLast("b");
        d.addLast("c");

        assertThat(d.get(-1)).isNull();
        assertThat(d.get(-100)).isNull();
    }

    // 场景：空表上 get
    // flag：无（get_oob_* 的额外边界）
    // 断言：任何下标都返回 null
    // 要害：空表时 sentinel.next 是哨兵本身，循环/递归的退出条件必须能处理
    @Test
    public void getFromEmptyReturnsNullTest() {
        Deque61B<String> d = new LinkedListDeque61B<>();
        assertThat(d.get(0)).isNull();
        assertThat(d.get(-1)).isNull();
        assertThat(d.get(9999)).isNull();
    }

    // 场景：递归版本取合法下标
    // flag：get_recursive_valid
    // 断言：与 get 一致
    // 要害：递归版要保证 base case 和步进方向都对
    @Test
    public void getRecursiveValidIndexTest() {
        Deque61B<String> d = new LinkedListDeque61B<>();
        d.addLast("a");
        d.addLast("b");
        d.addLast("c");

        assertThat(d.getRecursive(0)).isEqualTo("a");
        assertThat(d.getRecursive(1)).isEqualTo("b");
        assertThat(d.getRecursive(2)).isEqualTo("c");
    }

    // 场景：递归版本 + 超大下标
    // flag：get_recursive_oob_large
    // 断言：返回 null
    // 要害：如果递归没有边界检查，超大数据会直接 StackOverflowError
    @Test
    public void getRecursiveOutOfBoundsLargeTest() {
        Deque61B<String> d = new LinkedListDeque61B<>();
        d.addLast("a");
        d.addLast("b");
        d.addLast("c");

        assertThat(d.getRecursive(3)).isNull();
        assertThat(d.getRecursive(28723)).isNull();
    }

    // 场景：递归版本 + 负下标
    // flag：get_recursive_oob_neg
    // 断言：返回 null
    // 要害：负数会让递归永远走不到 base case（i 越减越小）→ 栈溢出
    @Test
    public void getRecursiveOutOfBoundsNegativeTest() {
        Deque61B<String> d = new LinkedListDeque61B<>();
        d.addLast("a");
        d.addLast("b");
        d.addLast("c");

        assertThat(d.getRecursive(-1)).isNull();
        assertThat(d.getRecursive(-100)).isNull();
    }

    // 场景：空表上调用递归 get
    // flag：无（额外边界）
    // 断言：返回 null 而不是无限递归
    @Test
    public void getRecursiveFromEmptyReturnsNullTest() {
        Deque61B<String> d = new LinkedListDeque61B<>();
        assertThat(d.getRecursive(0)).isNull();
        assertThat(d.getRecursive(-1)).isNull();
    }

    /* ============================================================
     * 五、size
     * ============================================================ */

    // 场景：从 0 涨到 3，删两次后降回 1
    // flag：size
    // 断言：每一步的 size 都正确
    // 要害：常见 bug 是 size 只在 add 时 ++，remove 时忘了 --
    @Test
    public void sizeTest() {
        Deque61B<String> d = new LinkedListDeque61B<>();
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

    // 场景：加到 3 个再全部删掉
    // flag：size_after_remove_to_empty
    // 断言：删空后 size 为 0（而不是残留 3）
    @Test
    public void sizeAfterRemoveToEmptyTest() {
        Deque61B<String> d = new LinkedListDeque61B<>();
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

    // 场景：对空 deque 调用 remove（返回 null）之后 size
    // flag：size_after_remove_from_empty
    // 断言：size 仍是 0，不能变成 -1
    // 要害：空表删除如果无脑 size--，就会变成负数
    @Test
    public void sizeAfterRemoveFromEmptyTest() {
        Deque61B<String> d = new LinkedListDeque61B<>();
        assertThat(d.size()).isEqualTo(0);

        assertThat(d.removeFirst()).isNull();
        assertThat(d.size()).isEqualTo(0);

        assertThat(d.removeLast()).isNull();
        assertThat(d.size()).isEqualTo(0);
        assertThat(d.isEmpty()).isTrue();
    }

    /* ============================================================
     * 六、isEmpty
     * ============================================================ */

    // 场景：全新的空 deque
    // flag：is_empty_true
    // 断言：isEmpty 为 true，size 为 0
    // 要害：抓"永远返回 false"的占位实现
    @Test
    public void isEmptyTrueTest() {
        Deque61B<String> d = new LinkedListDeque61B<>();
        assertThat(d.isEmpty()).isTrue();
        assertThat(d.size()).isEqualTo(0);
    }

    // 场景：加了元素后、又删空后
    // flag：is_empty_false
    // 断言：有元素时为 false；删空后变回 true
    // 要害：两个方向的切换都要查，只查 false 漏掉"删空后没变回 true"
    @Test
    public void isEmptyFalseTest() {
        Deque61B<String> d = new LinkedListDeque61B<>();
        d.addFirst("a");
        assertThat(d.isEmpty()).isFalse();

        d.addLast("b");
        assertThat(d.isEmpty()).isFalse();

        d.removeFirst();
        d.removeLast();
        assertThat(d.isEmpty()).isTrue();
    }

    /* ============================================================
     * 七、toList
     * ============================================================ */

    // 场景：空 deque
    // flag：to_list_empty
    // 断言：返回空列表，长度为 0
    // 要害：遍历哨兵时若判断条件是 p != null 就会死循环/多算；
    //      正确做法是走到 sentinel 就停
    @Test
    public void toListEmptyTest() {
        Deque61B<String> d = new LinkedListDeque61B<>();
        assertThat(d.toList()).isEmpty();
        assertThat(d.toList().size()).isEqualTo(0);
    }

    // 场景：交替 addFirst / addLast 之后
    // flag：to_list_nonempty
    // 断言：[0, 1, 2, 3] 顺序完全正确
    // 要害：containsExactly(...).inOrder() 同时查了集合、数量、顺序
    @Test
    public void toListNonEmptyTest() {
        Deque61B<Integer> d = new LinkedListDeque61B<>();
        d.addLast(1);
        d.addLast(2);
        d.addFirst(0);
        d.addLast(3);

        List<Integer> result = d.toList();
        assertThat(result).containsExactly(0, 1, 2, 3).inOrder();
        assertThat(result.size()).isEqualTo(4);
    }

    // 场景：拿到 toList 的返回值之后去改它
    // flag：无（额外约束，接口注释要求 "a new list copy"）
    // 断言：改副本不影响 deque 本身
    // 要害：如果 toList 直接把内部结构暴露出去，这条会红
    @Test
    public void toListReturnsIndependentCopyTest() {
        Deque61B<String> d = new LinkedListDeque61B<>();
        d.addLast("a");

        List<String> copy = d.toList();
        copy.add("injected"); // 改动副本不应影响 deque 本身

        assertThat(d.toList()).containsExactly("a").inOrder();
        assertThat(d.size()).isEqualTo(1);
    }

    /* ============================================================
     * 八、综合测试
     * ============================================================ */

    // 场景：把 add/remove/get/size/isEmpty/toList 混在一起跑一长串
    // flag：无（不对应单个 flag，但能一次抓出大量状态机错误）
    // 断言：每一步都检查当前完整状态（我在注释里标了列表内容）
    // 要害：真实 bug 往往只在"多种操作交替"时才暴露，
    //      单个方法各自测通、组合起来却错，是链表项目最常见的情况
    @Test
    public void integrationMixedOperationsTest() {
        Deque61B<Integer> d = new LinkedListDeque61B<>();

        assertThat(d.isEmpty()).isTrue();
        assertThat(d.removeFirst()).isNull();
        assertThat(d.removeLast()).isNull();

        d.addLast(1);   // [1]
        d.addFirst(0);  // [0, 1]
        d.addLast(2);   // [0, 1, 2]
        d.addFirst(-1); // [-1, 0, 1, 2]
        assertThat(d.toList()).containsExactly(-1, 0, 1, 2).inOrder();
        assertThat(d.size()).isEqualTo(4);
        assertThat(d.get(0)).isEqualTo(-1);
        assertThat(d.get(3)).isEqualTo(2);
        assertThat(d.getRecursive(2)).isEqualTo(1);

        assertThat(d.removeFirst()).isEqualTo(-1); // [0, 1, 2]
        assertThat(d.removeLast()).isEqualTo(2);   // [0, 1]
        assertThat(d.toList()).containsExactly(0, 1).inOrder();
        assertThat(d.size()).isEqualTo(2);

        d.addLast(9);                              // [0, 1, 9]
        assertThat(d.toList()).containsExactly(0, 1, 9).inOrder();

        assertThat(d.removeFirst()).isEqualTo(0);  // [1, 9]
        assertThat(d.removeLast()).isEqualTo(9);   // [1]
        assertThat(d.size()).isEqualTo(1);
        assertThat(d.get(0)).isEqualTo(1);
        assertThat(d.get(1)).isNull();

        assertThat(d.removeFirst()).isEqualTo(1);
        assertThat(d.isEmpty()).isTrue();
        assertThat(d.toList()).isEmpty();
    }
}