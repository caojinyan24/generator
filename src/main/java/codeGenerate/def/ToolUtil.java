package codeGenerate.def;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;

public class ToolUtil {
	
	
	public static void writeFile(File file,String mesInfo) throws IOException{
		if(file == null) {
			throw new IllegalStateException("logFile can not be null!");
		}
		Writer txtWriter = new FileWriter(file,true);
		txtWriter.write(mesInfo+"\n");
		txtWriter.flush();
	}

}
