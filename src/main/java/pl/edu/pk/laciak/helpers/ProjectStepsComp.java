package pl.edu.pk.laciak.helpers;

import java.util.Comparator;

import pl.edu.pk.laciak.DTO.Project_step;


public class ProjectStepsComp implements Comparator<Project_step> {

	@Override
	public int compare(Project_step arg0, Project_step arg1) {
		return Integer.compare(arg0.getNumber(), arg1.getNumber());
	}

}
