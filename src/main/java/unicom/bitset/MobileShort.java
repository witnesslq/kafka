package unicom.bitset;

public class MobileShort {

	/**
	 * @param args
	 * 
	 * 186  1
	 * 185  2
	 * 156  3
	 * 155  4
	 * 132  5
	 * 131  6
	 * 
	 */
	private String  mobilePre[]={"186","185","156","155","132","131","130","176","175","145"};
	public int changePre(String pre)
	{
		int index=0;
		boolean flag=false;
		for(String a:mobilePre)
		{
			index++;
			if(a.equals(pre))
			{
				flag=true;
				break;
			}
		}
		if(!flag) 
		{
			index=0;//表示没有找到前缀匹配。
		} 
		return index;
	}
	public String getShortMobile(String mobile)
	{
		String shortMobile=null;
		String pre=mobile.substring(0, 3);
		int index=changePre(pre);
		if(0!=index)
		{
			shortMobile=index+mobile.substring(3, 11);
		}
		return shortMobile;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MobileShort mobileShort=new MobileShort();
		String s=mobileShort.getShortMobile("13011701625");
		System.out.println(s);
	}

}
