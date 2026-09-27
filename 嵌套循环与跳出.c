#include <stdio.h>
int main(){
	int k;
	int a,b,c,x,y,z;
	/*abc是个数xyz是对应的值*/
	printf("请输入总数\n");
	scanf("%d",&k);
	printf("请输入三个子量\n");
	scanf("%d %d %d",&x,&y,&z);
	for(a = 1;a <= k/x;a++){
		for(b = 1;b <= k/y;b++){
			for(c = 1;c <= k/z;c++){
				if(a*x + b*y + c*z == k){
					printf("可以用%d个%d加%d个%d加%d个%d得到%d\n",a,x,b,y,c,z,k);
					/*goto out;*/
				}
			}
		}
	}
	out:
	return 0;
}

