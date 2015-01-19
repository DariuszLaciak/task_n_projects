package pl.edu.pk.laciak.helpers;

import java.util.Comparator;

import pl.edu.pk.laciak.DTO.Project;

public class ProjectComparator implements Comparator<Project> {

	@Override
	public int compare(Project o1, Project o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
