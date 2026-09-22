#include<stdio.h>

int main()
{int a[8]={5,2,9,1,5,6,3,8};
int n = 8;

/*1.bian li shu zu*/
int i=0;
printf("原数组=");
for(i=0;i<n;i++){
	printf("%d",a[i]);
}
printf("\n");

/*2. sum */
int sum =0;
int x =0;
for(x=0;x<n;x++){
	sum+=a[x];
}
printf("总和=%d\n",sum);

/*3. max */
int max =a[0];
int y = 0;
for(y=0;y<n;y++){
	if(a[y]>max){
		max=a[y];
	}
}
printf("最大值=%d\n",max);

return 0;
}
