#include <stdio.h>

int main(void) {
    int a[8] = {5, 2, 9, 1, 5, 6, 3, 8};
    int n = 8;
    int i = 0;
    int j = 0;
    int k = 0;

    /* 冒泡排序：每一轮把当前最大的"冒"到后面去 */
    for (i = 0; i < n - 1; i++) {          /* 一共要 n-1 轮 */
        for (j = 0; j < n - 1 - i; j++) {  /* 每轮比较到未排序部分的末尾 */
            if (a[j] > a[j + 1]) {             /* 前面比后面大 → 交换 */
                int t = a[j];
                a[j] = a[j + 1];
                a[j + 1] = t;
            }
        }
        /* 打印每轮结束后的结果，看清它是怎么变有序的 */
        printf("第 %d 轮后：", i + 1);
        for (k = 0; k < n; k++) printf("%d ", a[k]);
        printf("\n");
    }

    return 0;
}
