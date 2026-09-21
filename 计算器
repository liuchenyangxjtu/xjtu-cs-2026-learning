#include<stdio.h>

int main()
{
	double a=0;
	double b=0;
	char op=0;

	printf("输入两个数和一个运算符，中间用空格分开\n");
	printf("比如 12 * 5   （支持 + - * /）\n");
	scanf("%lf %c %lf",&a,&op,&b);

	switch(op){
		case '+':
			printf("%.2f + %.2f = %.2f\n",a,b,a+b);
			break;
		case '-':
			printf("%.2f - %.2f = %.2f\n",a,b,a-b);
			break;
		case '*':
			printf("%.2f * %.2f = %.2f\n",a,b,a*b);
			break;
		case '/':
			/*除之前先看除数是不是0*/
			if(b==0){
				printf("除数不能为0\n");
			}
			else{
				printf("%.2f / %.2f = %.2f\n",a,b,a/b);
			}
			break;
		default:
			printf("不支持的运算符：%c\n",op);
			break;
	}

	return 0;
}

/*易错点1：%c前面那个空格不能删！上一次scanf读完会剩一个换行符，
            %c会把它读走，op就变成换行符，永远走default*/
/*易错点2：每个case后面都要break，忘了会"穿透"，一路往下执行完所有分支*/
/*易错点3：double输入用%lf，输出用%f，这两个不一样，很容易混*/
/*易错点4：case后面只能写整数或字符常量，不能写区间，判断">=60"只能用if*/
