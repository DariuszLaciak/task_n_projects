package pl.edu.pk.laciak.helpers;

import java.util.Comparator;
import java.util.Date;

import pl.edu.pk.laciak.DTO.Comments;

public class CommentByDateComparator implements Comparator<Comments> {

	@Override
	public int compare(Comments arg0, Comments arg1) {
		return arg1.getDate().compareTo(arg0.getDate());
	}

}
