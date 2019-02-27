import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReadFile implements CsvFile{

    public final static char SEPARATOR = ';';

    private File file;
    public  List<String> lines;
    public List<String[] > data;

   public ReadFile(File f){
	   this.file = f;
       init();
   }

    private void init() {
        lines = CsvFileHelper.readFile(file);

        data = new ArrayList<String[] >(lines.size());
        String sep = new Character(SEPARATOR).toString();
        for (String line : lines) {
            String[] oneData = line.split(sep);
            data.add(oneData);
        }
        System.out.println("");
    }



	@Override
	public File getFile() {
		// TODO Auto-generated method stub
		return file;
	}



	@Override
	public List<String[]> getData() {
		// TODO Auto-generated method stub
		return data;
	}
	
	public HashMap<String, Integer> nombreVilleParRegion(){
		
		HashMap<String,Integer> res = new HashMap<>();
		for(String[] s : this.data){
			if(res.containsKey(s[0])){
				res.replace(s[0], res.get(s[0])+ 1);
			}else{
				res.put(s[0], 1);
			}
		}
		
		
		return res;
	}
	
	public HashMap<String, Integer> nombreVilleParDepartement(){
		
		HashMap<String,Integer> res = new HashMap<>();
		for(String[] s : this.data){
			if(res.containsKey(s[1])){
				res.replace(s[1], res.get(s[1])+ 1);
			}else{
				res.put(s[1], 1);
			}
		}
		
		
		return res;
	}
	/** paris 7e */
	public String villeAvecLaPlusForteMoyenne(){
		String ville="";
		int sup=0;
		for(String[] s : data){
			int moyenne = Integer.valueOf(s[6]);
			if(moyenne > sup){
				ville = s[3] + "  " + s[2] ; 
			}
		}
		
		return ville;
	}
	
	public HashMap<String,Integer> nbTotalAssParVille(){
		HashMap<String,Integer> res = new HashMap<>();
		for(String[] s : this.data){
			if(res.containsKey(s[3])){
				res.replace(s[3], res.get(s[0])+ Integer.valueOf(Integer.valueOf(s[4])));
			}else{
				res.put(s[3], Integer.valueOf(s[4]));
			}
		}
		return res;
	}
	
	
	public static void main(String[] arg){
		File f = new File("/home/l3miage/quentin/NAW/isfcom2017.csv");
		ReadFile rF = new ReadFile(f);
		System.out.println(rF.nombreVilleParRegion().toString());
		System.out.println(rF.nombreVilleParDepartement().toString());
		System.out.println("Ville avec la plus forte moyenne " + rF.villeAvecLaPlusForteMoyenne());
		/*for(String[] s : rF.getData()){
			System.out.println(s[0]);
		}*/
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}