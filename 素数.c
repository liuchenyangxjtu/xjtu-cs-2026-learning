#include <stdio.h>
int main(){
	int x = 0;
	int i = 1;
	int right =1;
	scanf("%d",&x);
	
	for (i=2 ; i*i<=x ; i++ ){
		if(x % i == 0){
			right = 0;
			break;
		}
	}
	
	if(right==1){
		printf("It is 素数");
	}else if(right == 0){
		printf("It isn't 素数");
	}
	return 0;
	 
}
