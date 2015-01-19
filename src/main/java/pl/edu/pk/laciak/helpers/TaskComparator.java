package pl.edu.pk.laciak.helpers;

import java.util.Comparator;

import pl.edu.pk.laciak.DTO.Task;

public class TaskComparator implements Comparator<Task> {

	@Override
	public int compare(Task o1, Task o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
