package pl.edu.pk.laciak.helpers;

import java.util.Comparator;

import pl.edu.pk.laciak.DTO.Notes;

public class ProjectNotesComparator implements Comparator<Notes> {

	@Override
	public int compare(Notes o1, Notes o2) {
		if(o1.getPs_note() == null && o1.getPt_note() == null){
			return -1;
		}
		else if(o2.getPs_note() == null && o2.getPt_note() == null){
			return 1;
		}
		else {
			if(o1.getPt_note() == null){
				return -1;
			}
			else if(o2.getPt_note() == null){
				return 1;
			}
			else {
				return Long.compare(o1.getId(), o2.getId());
			}
		}
	}

}
