package pl.edu.pk.laciak.helpers;

import java.util.Comparator;

import pl.edu.pk.laciak.DTO.Project_task;

public class ProjectTaskComparator implements Comparator<Project_task> {

	@Override
	public int compare(Project_task o1, Project_task o2) {
		return Long.compare(o2.getId(), o1.getId());
	}

}
