package com.sc.reminder.ui.contacts;

import java.util.Comparator;

import com.sc.reminder.model.remind.Users;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator implements Comparator<Users> {

	public int compare(Users o1, Users o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
