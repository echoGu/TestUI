package innerclass;

public class SOuter {
	private int a = 99;
	static int b = 1;
	
//	static class SInner {
	class SInner {
		int b = 2;
		void test() {
			System.out.println("访问外部类中的b： "+ SOuter.b);
			System.out.println("访问外部类中的a： "+ new SOuter().a);
			System.out.println("访问外部类中的a： "+ SOuter.this.a);
			System.out.println("访问外部类中的a： "+ a);
			
			System.out.println("访问内部类中的b： "+ b);
		}
	}

	public static void main(String[] args) {
		SOuter so = new SOuter();
		SInner si = so.new SInner();
//		SInner si = new SInner();
		si.test();

	}

}
