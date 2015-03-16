package pl.edu.pk.laciak.helpers;

import java.util.Comparator;

import pl.edu.pk.laciak.DTO.Files;

public class FilesByDateComparator implements Comparator<Files> {

	@Override
	public int compare(Files arg0, Files arg1) {
		return arg1.getDate().compareTo(arg0.getDate());
	}

}
