package unicom.file;

import java.io.File;
/*
 * 
 * 删除特定目录的文件
 * */
public class deleteFile {

	/**
	 * @param args
	 */
	public static void deleteFile(String path)
	{
		File dir=new File(path);
		if(dir.isDirectory())
		{
			int  i=0;
			for(String f:dir.list())
			{
				File file=new File(path+f);
//				File dest=new File(path+i+".txt");
//				file.renameTo(dest);
//				i++;
				//dest.delete();
				file.delete();
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String []pathArr={"e:\\test\\","e:\\move\\"};
		for(String path:pathArr)
		{
			deleteFile(path);
		}
		

	}

}
