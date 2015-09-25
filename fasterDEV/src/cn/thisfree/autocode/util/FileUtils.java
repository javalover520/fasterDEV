package cn.thisfree.autocode.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileUtils {
	public static void copyURLToFile(URL source, File destination)
			throws IOException {
		java.io.InputStream input = source.openStream();
		try {
			FileOutputStream output = openOutputStream(destination);
			try {
				copy(input, output);
			} finally {
				closeQuietly(output);
			}
		} finally {
			closeQuietly(input);
		}
	}

	public static FileOutputStream openOutputStream(File file)
			throws IOException {
		if (file.exists()) {
			if (file.isDirectory())
				throw new IOException("File '" + file
						+ "' exists but is a directory");
			if (!file.canWrite())
				throw new IOException("File '" + file
						+ "' cannot be written to");
		} else {
			File parent = file.getParentFile();
			if (parent != null && !parent.exists() && !parent.mkdirs())
				throw new IOException("File '" + file
						+ "' could not be created");
		}
		return new FileOutputStream(file);
	}

	public static int copy(InputStream input, OutputStream output)
			throws IOException {
		long count = copyLarge(input, output);
		if (count > 2147483647L)
			return -1;
		else
			return (int) count;
	}

	public static long copyLarge(InputStream input, OutputStream output)
			throws IOException {
		byte buffer[] = new byte[4096];
		long count = 0L;
		for (int n = 0; -1 != (n = input.read(buffer));) {
			output.write(buffer, 0, n);
			count += n;
		}

		return count;
	}

	public static void closeQuietly(OutputStream output) {
		try {
			if (output != null)
				output.close();
		} catch (IOException ioe) {
		}
	}

	public static void closeQuietly(InputStream input) {
		try {
			if (input != null)
				input.close();
		} catch (IOException ioe) {
		}
	}
	
	public static void zip(String zipFileName, File inputFile) throws Exception {  
        System.out.println("压缩中...");  
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(  
                zipFileName));  
        BufferedOutputStream bo = new BufferedOutputStream(out);  
        zip(out, inputFile, inputFile.getName(), bo);  
        bo.close();  
        out.close(); // 输出流关闭  
        System.out.println("压缩完成");  
    }  
  
    private static void zip(ZipOutputStream out, File f, String base,  
            BufferedOutputStream bo) throws Exception { // 方法重载  
        if (f.isDirectory()) {  
            File[] fl = f.listFiles();  
            if (fl.length == 0) {  
                out.putNextEntry(new ZipEntry(base + "/")); // 创建zip压缩进入点base  
                System.out.println(base + "/");  
            }  
            for (int i = 0; i < fl.length; i++) {  
                zip(out, fl[i], base + "/" + fl[i].getName(), bo); // 递归遍历子文件夹  
            }  
        } else {  
            out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base  
            System.out.println(base);  
            FileInputStream in = new FileInputStream(f);  
            BufferedInputStream bi = new BufferedInputStream(in);  
            int b;  
            while ((b = bi.read()) != -1) {  
                bo.write(b); // 将字节流写入当前zip目录  
            }  
            bi.close();  
            in.close(); // 输入流关闭  
        }  
    }  
    
    public static void upZip(String inPath,String outPath){
	    try {  
	        ZipInputStream Zin=new ZipInputStream(new FileInputStream(inPath));//输入源zip路径  
	        BufferedInputStream Bin=new BufferedInputStream(Zin);  
	        String Parent=outPath; //输出路径（文件夹目录）  
	        File Fout=null;  
	        ZipEntry entry;  
	        try {  
	            while((entry = Zin.getNextEntry())!=null){ 
	            	if(entry.isDirectory()){
	            		new File(Parent,entry.getName()).mkdirs();
	            		continue;
	            	}
	                Fout=new File(Parent,entry.getName());  
	                if(!Fout.exists()){  
	                    (new File(Fout.getParent())).mkdirs();  
	                }  
	                FileOutputStream out=new FileOutputStream(Fout);  
	                BufferedOutputStream Bout=new BufferedOutputStream(out);  
	                int b;  
	                while((b=Bin.read())!=-1){  
	                    Bout.write(b);  
	                }  
	                Bout.close();  
	                out.close();  
	                System.out.println(Fout+"解压成功");      
	            }  
	            Bin.close();  
	            Zin.close();  
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	    } catch (FileNotFoundException e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    }  
    }
}
