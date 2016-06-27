package collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LinkedListTest {
	public static void main(String[] args) {

        List<String> a=new ArrayList<String>();
        a.add("a");
        a.add("b");
        a.add("c");
//        System.out.println(a);
        
        List<String> b=new ArrayList<String>();
        b.add("d");
        b.add("e");
        b.add("f");
        b.add("g");
//        System.out.println(b);
        
        //ListIterator在Iterator基础上添加了add(),previous()和hasPrevious()方法
        ListIterator<String> aIter=a.listIterator();
        //普通的Iterator�?�有三个方法，hasNext()，next()和remove()
        Iterator<String> bIter=b.iterator();
        
        //b归并入a当中，间隔交�?�得�?�入b中的元素
        while(bIter.hasNext())
        {
            if(aIter.hasNext())
                aIter.next();
            aIter.add(bIter.next());
        }
        System.out.println(a);
        
        //在b中�?隔两个元素删除一个
        bIter=b.iterator();
        
        while(bIter.hasNext())
        {
            bIter.next();
            if(bIter.hasNext())
            {
                bIter.next();//remove跟next是�?对出现的，remove总是删除�?�?
                bIter.remove();
            }
        }
        System.out.println(b);
        
        //删除a中所有的b中的元素
        a.removeAll(b);
        System.out.println(a);
    }
}
