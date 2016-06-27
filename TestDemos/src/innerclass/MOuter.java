package innerclass;

public class MOuter {
	int b = 1;
	
	void show() {
		final int a = 25;
		int b = 13;
		
		class MInner {
			int c = 2;
			void print() {
				System.out.println("访问外部类方法中的常量a： " + a);
//				System.out.println("访问外部类方法中的变量b： " + b);
				System.out.println("访问外部类成员变量b： " + MOuter.this.b);
				System.out.println("访问内部类方法中的变量c： " + c);
			}
			
		}
		
		MInner mi = new MInner();
		mi.print();
	}
	
	public static void main(String[] args) {
		MOuter mo = new MOuter();
		mo.show();
	}

}
