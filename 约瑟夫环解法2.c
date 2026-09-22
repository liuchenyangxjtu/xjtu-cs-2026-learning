#include <stdio.h>

int main()
{ int m, n, i, k, c;
  printf("请输入人数和计数终值：");
  scanf("%d%d", &m, &n);
  int f[1000];
  for (i = 0; i <= m - 1; i++){
    f[i] = i + 1;   } /*存储每个人的编号*/
  c = m;   /*当前圈中人数*/
  k = 0;   /*数器清0*/
  i = 0;   /*从第1个人开始计数*/
  printf("出列顺序为: \n");
  while (c > 0)
  { if (f[i] != 0)
    k++;
    if (k == n)
    { printf("%-5d", f[i]);   /*输出出列者编号*/
      f[i] = 0;   /*清除出列者编号*/
      c--;   /*当前圈中人数减1*/
      k = 0;   /*计数器复位*/
    }
    i = (i + 1) % m;
  }
  return 0;
}
