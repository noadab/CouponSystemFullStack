package com.CouponSystemSpring.entities;

public enum Category {

		Food("Food"), 
		Electricity("Electricity"),
		Restaurant("Restaurant"),
		Vacation("Vacation");
		
		private String category;
		
		private Category(String category) {
			this.category=category;
		}

		public String getCategory() {
			return category;
		}

		public static boolean isItCategory(String category) {
			for (Category currCategory : Category.values()) {
				if (currCategory.getCategory().equals(category)) {
					return true;
				}
			}
			return false;
		}
		
		public static void printAllCategory() {
			for (Category category : Category.values()) {
				System.out.print(category + " | ");
			}
		}
}
