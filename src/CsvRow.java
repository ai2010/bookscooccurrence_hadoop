//package bookshadoop;
import java.util.*;

public class CsvRow {

	public String [] row_p;
	public int numberofcouples;
    public ArrayList<String> couples;
    public String user;


    public CsvRow(String rowText) {
        String row[] = {};
        numberofcouples=0;		
        row = rowText.replace("\'", "").split("\\s*,\\s*", -1);
		row_p = new String[row.length-1];
		System.out.println("line:"+rowText);
		System.arraycopy(row,1,row_p,0,row.length-1);
		System.out.println(Arrays.toString(row_p));
		user = row[0];
	    couples = new ArrayList<String>();
		calcolaCooccurences();
		
		/*
		for(int i=1; i< row.length; i++)
		{
			ratings.add(row[i]);
		}
		*/

    }
	

	public ArrayList<String> calcolaCooccurences()
	{
		for(int i=0; i<row_p.length; i++)
		{
			for(int j=i; j<row_p.length;j++)
			{
				if(row_p[i]!="" && row_p[j]!="")
				{
					couples.add("\'"+row_p[i]+"\'"+"\'"+row_p[j]+"\'");
					numberofcouples++;
				}
			}
		}
		return couples;
	}
	
}
