import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class DirWalker {
	static ArrayList<String> allCSVFile = new ArrayList<String>();

	public void walk( String path ) {
		SimpleLogging log = new SimpleLogging();
		File root = new File( path );
		File[] list = root.listFiles();

		try {
			if (list == null) 
			{
				throw new IOException("FILE NOT EXISTS");
			}
		}catch(IOException e)
		{
			log.logging(e.getMessage());
			return;
		}


		for ( File f : list ) {
			if ( f.isDirectory() || !(f.getAbsolutePath().contains("csv"))) {
				walk( f.getAbsolutePath() );
			}
			else{          	
				String absolutePath = f.getAbsoluteFile().toString();
				allCSVFile.add(absolutePath); 
			}
		}
	}




	public static void createDirWalker() {

		DirWalker fw = new DirWalker();
		fw.walk("C:\\Users\\User\\Documents\\SMU\\MCDA5510\\MCDA5510_Assignments\\Sample Data" );
		SimpleCsvParser.parseFile(allCSVFile);

	}
}
