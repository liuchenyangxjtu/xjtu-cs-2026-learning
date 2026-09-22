#include <stdio.h>

int main() {
    int n = 0;      /* 总人数 */
    int k = 0;     /* 每数到第 k 个人淘汰 */
    printf("请输入总人数以及第几人淘汰：");
	scanf("%d %d",&n,&k);     
    int people[1000];

    /* 初始化：people[0]=1, people[1]=2, ... */
    int i = 0;
    for (i = 0; i < n; i++) {
        people[i] = i + 1;
    }

    int count = n;   /* 当前还剩几个人 */
           /* 从哪个下标开始报数 */
    while (count > 1) {
        /* 从 i 开始数 k 个人，落到的下标是 (i + k - 1) % count */
        i = (i + k - 1) % count;

        /* 淘汰 people[i]：把后面的人整体往前搬一格 */
        int j = i;
        for (j = i; j < count - 1; j++) {
            people[j] = people[j + 1];
        }
        count--;

        /* 搬完之后，原来 i+1 位置的人落到了 i 位置，
           所以下一轮直接从 i 开始数，正好对 */
    }

    printf("最后剩下的是第 %d 号\n", people[0]);
    return 0;
}

