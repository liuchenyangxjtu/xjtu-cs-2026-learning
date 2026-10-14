#include <stdio.h>
int main(){
	int grade = 0;
	printf("please write the grade :");
	scanf("%d",&grade);
	grade/=10;
	switch(grade){
		/*注意case 后要空格*/ 
		case 10:
			printf("A\n");
			break;
		case 9:
			printf("B\n");
			break;
		case 8:
			printf("C\n");
			break;
		case 7:
			printf("D\n");
			break;
		case 6:
			printf("E\n");
			break;
		default:
			printf("F\n");
			break;
	}
}

