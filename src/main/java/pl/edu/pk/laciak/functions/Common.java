package pl.edu.pk.laciak.functions;

public class Common {

	public static String makeHeader(int val, String header){
		return "<h"+val+">"+header+"</h"+val+">";
	}
	
	public static String makeInputText(String id, String label, String value){
		return "<div class='inputs'><label class='l_input'>"+label+":</label><input type='text' id='"+id+"' value='"+value+"' size='30'></input></div>";
	}
	
	public static String makeInputPassword(String id, String label){
		return "<div class='inputs'><label class='l_input'>"+label+":</label><input type='password' id='"+id+"' size='30'></input></div>";
	}
	
	public static String makeButton(String value,String onclick){
		return "<button type='button' id='submit' onclick='"+onclick+"'>"+value+"</button>";
	}
	
	public static String makeLink(String page, String name){
		return "<a href='"+page+"'>"+name+"</a>";
	}
}

