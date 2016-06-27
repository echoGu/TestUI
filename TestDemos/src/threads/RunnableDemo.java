package threads;

class MyThread1 implements Runnable
{  
    private int ticket = 10;  
    private String name;  
    
    public void run()
    {  
        for(int i =0; i<500; i++)
        {  
            if(this.ticket > 0)
            {  
                System.out.println(Thread.currentThread().getName() + "卖票---->" + (this.ticket--));  
            }  
        }  
    }  
}  
public class RunnableDemo {  
  
      
    public static void main(String[] args) 
    {  
         MyThread1 mt = new MyThread1();  
         Thread t1 = new Thread(mt, "一号窗口");  
         Thread t2 = new Thread(mt, "二号窗口");  
         Thread t3 = new Thread(mt, "三号窗口");  
         t1.start();  
         t2.start();  
         t3.start();  
    }  
  
}