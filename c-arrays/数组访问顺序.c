#include<stdio.h>
#include<time.h>

#define N 8000

/*大数组要放函数外面，不然栈会溢出*/
static int a[N][N];

int main()
{
	clock_t t0=0;
	clock_t t1=0;
	double t_row=0;
	double t_col=0;
	double s1=0;
	double s2=0;
	int i=0;
	int j=0;

	printf("数组大小：%d x %d，约 %d MB\n",N,N,N*N*4/1024/1024);

	printf("正在填数据...\n");
	for(i=0;i<N;i++){
		for(j=0;j<N;j++){
			a[i][j]=i+j;
		}
	}

	/*第一种：按行访问 a[0][0] a[0][1] a[0][2] ...*/
	printf("按行访问中...\n");
	t0=clock();
	for(i=0;i<N;i++){
		for(j=0;j<N;j++){
			s1+=a[i][j];
		}
	}
	t1=clock();
	t_row=(double)(t1-t0)/CLOCKS_PER_SEC;

	/*第二种：按列访问 a[0][0] a[1][0] a[2][0] ...*/
	printf("按列访问中...\n\n");
	t0=clock();
	for(j=0;j<N;j++){
		for(i=0;i<N;i++){
			s2+=a[i][j];
		}
	}
	t1=clock();
	t_col=(double)(t1-t0)/CLOCKS_PER_SEC;

	printf("按行访问：%.3f 秒\n",t_row);
	printf("按列访问：%.3f 秒\n",t_col);
	printf("按列比按行慢 %.1f 倍\n\n",t_col/t_row);

	printf("两次的累加结果：%.0f 和 %.0f\n",s1,s2);
	printf("结果一样，说明加法次数完全相同，只有访问顺序不同\n");

	return 0;
}

/*易错点1：累加变量必须用double。8000x8000=6400万个元素，总和约5119亿，
            超过int上限(约21亿)，用int会溢出，而且程序不报错*/
/*易错点2：C89没有long long，所以只能用double*/
/*易错点3：大数组一定要放函数外面。放main里面是局部变量，栈装不下244MB，程序直接崩*/
/*易错点4：N要明显大于CPU的cache，差异才看得出来。如果不明显，把8000改成6000或3000试试*/

