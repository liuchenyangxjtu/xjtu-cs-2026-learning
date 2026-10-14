#include <stdio.h>
#include <stdlib.h>
#include <time.h>
int main(){
	int a;
	int b;
	int count = 0;
	srand(time(0));
	a = rand();
	a = a%100+1;
	printf("I get a number,(1-100),you guess it\n");
	do{
	count++;
	printf("give me a number\n");
	scanf("%d",&b);
	if(b>a){
		printf("your number is big\n");
	}
	else if(b<a){
		printf("your number is small\n");
	}
	}while(b!=a);
	printf("you win\n");
	printf("you use %d count\n",count);
	
	return 0;
}
