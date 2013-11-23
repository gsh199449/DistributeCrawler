#include <iostream.h>
#include <iomanip.h>
class Calendar{
	public:
		/*返回星期几*/
		int week(int year,int month,int day)
		{
			int yearjs , monthjs , monthday = 0;
			int permonthday[12]={31,28,31,30,31,30,31,31,30,31,30,31};
			for(int i=0;i<month-1;i++)
				monthday = monthday + permonthday[i];
			if(year % 4 == 0 && year % 100 == 0 || year % 400 == 0)
			{
				yearjs = 2;
				if(month > 2)
					monthjs = (monthday + 1) % 7;
				else
					monthjs = monthday % 7;
			}
			else
			{
				yearjs = 1;
				monthjs = monthday % 7;
			}
			return ((year + year / 4 + year / 400 - year / 100 - yearjs + monthjs + day ) % 7 ) == 0 ? 7 : ((year + year / 4 + year / 400 - year / 100 - yearjs + monthjs + day ) % 7 );
		}

		int monthday(int year,int month)
		{
			int permonthday[12]={31,28,31,30,31,30,31,31,30,31,30,31};
			if(month != 2)
				return permonthday[month - 1];
			else
			{
				if(year % 4 == 0 && year % 100 == 0 || year % 400 == 0)
					return 29;
				else
					return 28;
			}
		}
		/*打印本月日历*/
		void month(int year,int month)
		{
			cout<<setw(6)<<"Mon"<<setw(6)<<"Tues"<<setw(6)<<"Wed"<<setw(6)<<"Thur"<<setw(6)<<"Fir"<<setw(6)<<"Sat"<<setw(6)<<"Sun"<<endl;
			for(int i=1;i<=monthday(year,month);i++)
			{
				if(i==1)
				{ 
					for(int j = 1 ; j < week(year,month,1) ; j++)
					{
						cout.width(6);
						cout<<setw(6)<<" ";
					}
					cout<<setw(6)<<i;
				}
				else 
				{
					if((i + week(year,month,1) - 2) % 7 == 0)
						cout<<endl;
					cout<<setw(6)<<i;
				}
			}
		}
		/*打印某年日历*/
		void year(int year)
		{
			for(int i=1;i<=12;i++)
			{
				cout<<i<<" 月"<<endl;
				month(year,i);
				cout<<endl<<endl;
			}
		}
};