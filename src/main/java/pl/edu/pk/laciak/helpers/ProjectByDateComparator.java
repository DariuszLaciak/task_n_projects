package pl.edu.pk.laciak.helpers;

import java.util.Comparator;

import pl.edu.pk.laciak.DTO.Project;

public class ProjectByDateComparator implements Comparator<Project> {

	@Override
	public int compare(Project arg0, Project arg1) {
		if(arg0.getStartDate().compareTo(arg1.getStartDate()) == 0){
			return arg0.getName().compareTo(arg1.getName());
		}
		return arg0.getStartDate().compareTo(arg1.getStartDate());
	}

}
