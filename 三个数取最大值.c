#include<stdio.h>
int max(int x,int y,int z)
{
int t;
int k;
if(x>y)
t=x;
else t=y;
if(t>z)
k=t;
else k=z;
return (k);
}

int main()
{
	int max(int x,int y,int z);
	int a,b,c,v;
	scanf("%d,%d,%d",&a,&b,&c);
	v=max(a,b,c);
	printf("max=%d\n",v);
	return 0;
	
}

