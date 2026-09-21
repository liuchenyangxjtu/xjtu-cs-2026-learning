#include<stdio.h>

int main()
{
	int total=0;
	int hour=0;
	int minute=0;
	int second=0;

	printf("请输入一个秒数：");
	scanf("%d",&total);

	/*易错点1：必须先算小时，再用余数算分钟。顺序反了分钟数就错*/
	hour=total/3600;
	minute=total%3600/60;
	second=total%60;

	/*易错点2：整数除法直接丢小数。3661/3600=1，不是1.017，这正是我们要的*/
	/*易错点3：%只能用在整数上，写成total%60.0会编译报错*/
	/*易错点4：%和/一样高，从左往右算，total%3600/60就是(total%3600)/60*/

	printf("%d秒 = %d小时%d分%d秒\n",total,hour,minute,second);

	return 0;
}
