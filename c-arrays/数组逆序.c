#include<stdio.h>

#define N 8

int main()
{
	int a[N]={5,2,9,1,5,6,3,8};
	int i=0;
	int t=0;
	int k=0;

	printf("倒序前：");
	for(i=0;i<N;i++){
		printf("%d ",a[i]);
	}
	printf("\n");

	/*从两头往中间换，一次换一对*/
	for(i=0;i<N/2;i++){
		t=a[i];
		a[i]=a[N-1-i];
		a[N-1-i]=t;
	}

	printf("倒序后：");
	for(k=0;k<N;k++){
		printf("%d ",a[k]);
	}
	printf("\n");

	return 0;
}

/*易错点1：循环只走到N/2。写成i<N的话，换到一半又换回来，等于白做*/
/*易错点2：配对的另一个下标是N-1-i，不是N-i。最后一个元素下标是N-1*/
/*易错点3：交换必须借临时变量t。直接写a[i]=a[N-1-i];a[N-1-i]=a[i]; 第一步就把值覆盖没了*/
/*易错点4：用#define N 8比到处写8好，以后改元素个数只改这一处*/
