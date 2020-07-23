package testcase;

import base.SetUp;

public class TestCase extends SetUp {
	public static void main(String[] args) {
		String nid = "7333139397";
		String dob = "1977/10/16";
		SetUp setup = new SetUp();
		setup.setUp();
		if (setup.getSearchDetail.verifyUserDetail(nid, dob)) {
			String imagePath = setup.getSearchDetail.getUserProfileDetail(nid);
			System.out.println(imagePath);
		} else {
			System.out.println("Invalid detail....");
		}
	}
}
