#include <stdio.h>
#include <math.h>                                /*一定要有 */
int main(){
	int a, b,c,p;
	double s;                                    /*用double */
	printf("请输入三边长："); 
	scanf("%d %d %d",&a,&b,&c);
	p=(a+b+c)/2.0;                               /*除2.0 */
	s=sqrt(p*(p-a)*(p-b)*(p-c));
	printf("三角形面积是：%lf",s);               /*lf */
	
	return 0;
	
}
